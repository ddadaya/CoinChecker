package com.example.coinchecker.retrofit

import com.example.coinchecker.pojo.CoinInfoListOfData
import com.example.coinchecker.pojo.CoinPriceInfoRaw
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
interface CoinApiService {
    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(API_KEY) apiKey: String = "be10e6d1acefcf793c422f1903e399ee42e5c15e761c834e11e15419c08b2f52",
        @Query(LIMIT) limit: Int = 10,
        @Query(TSYM) tsym: String = "USD"

    ): Single<CoinInfoListOfData>
    @GET("pricemultifull")
    fun getFullCoinInfo(
        @Query(API_KEY) apiKey: String = "be10e6d1acefcf793c422f1903e399ee42e5c15e761c834e11e15419c08b2f52",
        @Query(TSYMBOL) tsyms: String = "USD",
        @Query(FSYMBOL) fsyms: String
    ): Single<CoinPriceInfoRaw>

    companion object{
        private const val API_KEY = "api_key"
        private const val LIMIT = "limit"
        private const val TSYM = "tsym"
        private const val TSYMBOL = "tsyms"
        private const val FSYMBOL = "fsyms"
    }
}