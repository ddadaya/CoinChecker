package com.example.coinchecker.pojo

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinPriceInfoRaw (
    @SerializedName("RAW")
    @Expose
    val coinPriceInfoJson: JsonObject? = null
)