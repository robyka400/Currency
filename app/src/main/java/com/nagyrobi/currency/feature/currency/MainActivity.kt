package com.nagyrobi.currency.feature.currency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.nagyrobi.currency.MainActivityBinding
import com.nagyrobi.currency.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CurrencyViewModel.Factory

    lateinit var viewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val binding = DataBindingUtil.setContentView<MainActivityBinding>(this,
            R.layout.activity_main
        )
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyViewModel::class.java)
    }
}