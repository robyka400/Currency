package com.nagyrobi.currency.feature.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nagyrobi.currency.CurrencyItemBinding
import com.nagyrobi.currency.R

class CurrencyAdapter : ListAdapter<CurrencyItem, CurrencyAdapter.ViewHolder>(CurrencyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.currency_item_layout, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    class ViewHolder(private val binding: CurrencyItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currencyItem: CurrencyItem) {
            binding.currency = currencyItem
        }
    }

    class CurrencyDiffUtil : DiffUtil.ItemCallback<CurrencyItem>() {
        override fun areItemsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem) = oldItem.symbol == newItem.symbol

        override fun areContentsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem) = oldItem == newItem

    }
}