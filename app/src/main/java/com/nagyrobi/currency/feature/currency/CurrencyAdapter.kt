package com.nagyrobi.currency.feature.currency

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nagyrobi.currency.CurrencyItemBinding
import com.nagyrobi.currency.R
import com.nagyrobi.currency.util.SimpleTextWatcher
import com.nagyrobi.currency.util.bindingadapter.getDouble
import com.nagyrobi.currency.util.bindingadapter.setDouble

class CurrencyAdapter(
    private val onItemClickedCallback: (CurrencyItem) -> Unit,
    private val onRateChangedCallback: (CurrencyItem) -> Unit
) :
    ListAdapter<CurrencyItem, CurrencyAdapter.ViewHolder>(CurrencyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.currency_item_layout, parent, false
        ),
        onItemClickedCallback,
        onRateChangedCallback
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position), position == 0)

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) =
        (payloads.getOrNull(0) as? Double)?.let { newRate ->
            holder.updateRate(newRate)

        } ?: super.onBindViewHolder(holder, position, payloads)

    class ViewHolder(
        private val binding: CurrencyItemBinding,
        private val onItemClickedCallback: (CurrencyItem) -> Unit,
        private val onRateChangedCallback: (CurrencyItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val textChangedListener = object : SimpleTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                onRateChangedCallback(binding.currency?.copy(rate = binding.rate.getDouble())!!)
            }
        }

        fun bind(currencyItem: CurrencyItem, shouldBeFocused: Boolean) {
            binding.currency = currencyItem

            if (!shouldBeFocused || binding.rate.text.isNullOrEmpty()) {
                binding.rate.setDouble(currencyItem.rate)
            }
            binding.rate.setOnFocusChangeListener { _, isFocused ->
                if (isFocused) {
                    onItemClickedCallback(currencyItem)
                    binding.rate.addTextChangedListener(textChangedListener)
                }
            }
            if (shouldBeFocused) {
                binding.rate.requestFocus()
                binding.rate.addTextChangedListener(textChangedListener)
            } else {
                binding.rate.removeTextChangedListener(textChangedListener)
            }
        }

        fun updateRate(rate: Double) {
            if (!binding.rate.hasFocus()) {
                binding.rate.setDouble(rate)
            }
        }
    }

    class CurrencyDiffUtil : DiffUtil.ItemCallback<CurrencyItem>() {
        override fun areItemsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem) = oldItem.symbol == newItem.symbol

        override fun areContentsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem) = oldItem == newItem

        override fun getChangePayload(oldItem: CurrencyItem, newItem: CurrencyItem): Bundle? =
            if (!areItemsTheSame(oldItem, newItem)) null else Bundle().apply { putDouble(NEW_RATE, newItem.rate) }

        companion object {
            const val NEW_RATE = "value"
        }
    }
}