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

class CurrencyAdapter(
        private val onItemClickedCallback: (CurrencyItem) -> Unit,
        private val onRateChangedCallback: (CurrencyItem) -> Unit
) :
        ListAdapter<CurrencyItem, CurrencyAdapter.ViewHolder>(CurrencyDiffUtil()) {

    private var primaryInputBinding: CurrencyItemBinding? = null
        set(value) {
            field?.rate?.removeTextChangedListener(textChangedListener)
            field = value
            field?.rate?.addTextChangedListener(textChangedListener)
        }
    private val textChangedListener = object : SimpleTextWatcher() {

        override fun afterTextChanged(p0: Editable?) {
            onRateChangedCallback(
                    primaryInputBinding?.currency?.copy(
                            rate = primaryInputBinding?.rate?.getDouble() ?: Double.NaN
                    )!!
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.currency_item_layout, parent, false
            )
    ) {
        primaryInputBinding = it
        onItemClickedCallback(it.currency!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(getItem(position), primaryInputBinding?.rate?.getDouble() != getItem(position).rate)


    class ViewHolder(
            private val binding: CurrencyItemBinding,
            private val onItemClickedCallback: (CurrencyItemBinding) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currencyItem: CurrencyItem, shouldRebind: Boolean) {
            if (shouldRebind) {
                binding.currency = currencyItem
                binding.rate.setOnFocusChangeListener { _, isFocused ->
                    if (isFocused) {
                        onItemClickedCallback(binding)
                    }
                }
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