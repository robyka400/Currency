package com.nagyrobi.currency.feature.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nagyrobi.core.model.CurrencyDTO
import com.nagyrobi.currency.CurrencyItemBinding
import com.nagyrobi.currency.R

class CurrencyAdapter : ListAdapter<CurrencyDTO, CurrencyAdapter.ViewHolder>(CurrencyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.currency_item_layout, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    class ViewHolder(private val binding: CurrencyItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currencyDTO: CurrencyDTO) {
            binding.currency = currencyDTO
        }
    }

    class CurrencyDiffUtil : DiffUtil.ItemCallback<CurrencyDTO>() {

        override fun areItemsTheSame(oldItem: CurrencyDTO, newItem: CurrencyDTO) = oldItem.base == newItem.base

        override fun areContentsTheSame(oldItem: CurrencyDTO, newItem: CurrencyDTO) = oldItem == newItem

    }
}