package com.nagyrobi.core.networking

import com.nagyrobi.core.model.CurrencyType
import com.nagyrobi.core.model.CurrencyDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CurrencyService {

    @GET(REVOLUT_URL)
    fun getCurrency(@Query("base") base: CurrencyType): Single<CurrencyDTO>

    companion object {
        const val REVOLUT_URL = "https://revolut.duckdns.org/latest"
    }
}
