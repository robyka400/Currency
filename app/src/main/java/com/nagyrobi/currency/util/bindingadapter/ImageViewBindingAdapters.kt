package com.nagyrobi.currency.util.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("countryCode", requireAll = true)
fun ImageView.loadCountryImage(countryCode: String?) {
    countryCode?.let { country ->
        Glide.with(context)
            .load(getFlagUrl(country))
            .apply(RequestOptions.circleCropTransform())
            .into(this)
    }
}

private fun getFlagUrl(countryCode: String) =
    "https://www.countryflags.io/$countryCode/shiny/64.png"