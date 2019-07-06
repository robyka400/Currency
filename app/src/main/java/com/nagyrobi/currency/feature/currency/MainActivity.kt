package com.nagyrobi.currency.feature.currency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.nagyrobi.currency.MainActivityBinding
import com.nagyrobi.currency.R
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CurrencyViewModel.Factory

    lateinit var viewModel: CurrencyViewModel

    private var isUpdateDisabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyViewModel::class.java)
        val binding = DataBindingUtil.setContentView<MainActivityBinding>(
            this,
            R.layout.activity_main
        )
        val adapter = CurrencyAdapter()
        binding.recycler.adapter = adapter
        binding.recycler.recycledViewPool.setMaxRecycledViews(adapter.getItemViewType(0), MAX_VIEWS_RECYCLED)

        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                isUpdateDisabled =
                    newState == RecyclerView.SCROLL_STATE_SETTLING || newState == RecyclerView.SCROLL_STATE_DRAGGING
            }
        })
        viewModel.currencies.observe(this, Observer {
            if (!isUpdateDisabled) {
                adapter.submitList(it)
            }
        })

    }

    companion object {
        const val MAX_VIEWS_RECYCLED = 40
    }
}
