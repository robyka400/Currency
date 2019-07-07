package com.nagyrobi.currency.feature.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nagyrobi.currency.CurrencyItemBinding
import com.nagyrobi.currency.R
import com.nagyrobi.currency.util.bindingadapter.setNumber

class CurrencyAdapter(private val onItemClickedCallback: (CurrencyItem) -> Unit) :
    ListAdapter<CurrencyItem, CurrencyAdapter.ViewHolder>(CurrencyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.currency_item_layout, parent, false
        ),
        onItemClickedCallback
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) =
        (payloads.getOrNull(0) as? Double)?.let { newRate ->
            holder.updateRate(newRate)

        } ?: super.onBindViewHolder(holder, position, payloads)

    class ViewHolder(
        private val binding: CurrencyItemBinding,
        private val onItemClickedCallback: (CurrencyItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currencyItem: CurrencyItem) {
            binding.currency = currencyItem
            binding.root.setOnClickListener { onItemClickedCallback(currencyItem) }
        }

        fun updateRate(rate: Double) {
            binding.rate.setNumber(rate)
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