package com.nagyrobi.core.networking

import com.nagyrobi.core.model.CurrencyDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CurrencyService {

    @GET("/latest")
    fun getCurrency(@Query("base") base: String): Single<CurrencyDTO>
}
