package com.example.coinchecker.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Entity(tableName = "full_coin_info")
data class CoinPriceInfo (
    @PrimaryKey
    @SerializedName("FROMSYMBOL")
    @Expose
    val fromSymbol: String,

    @SerializedName("TOSYMBOL")
    @Expose
    val toSymbol: String?,

    @SerializedName("HIGHDAY")
    @Expose
    val maxPrice: String?,

    @SerializedName("LOWDAY")
    @Expose
    val minPrice: String?,

    @SerializedName("MEDIAN")
    @Expose
    val median: Double?,

    @SerializedName("LASTMARKET")
    @Expose
    val lastMarket: String?,

    @SerializedName("PRICE")
    @Expose
    val price: Double?,

    @SerializedName("LASTUPDATE")
    @Expose
    val lastUpdate: Long?,

    @SerializedName("IMAGEURL")
    @Expose
    val imageUrl: String?
) {
    fun getFormattedTime(): String{
        if(lastUpdate == null) return ""
        val stamp = Timestamp(lastUpdate*1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern,Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    fun getFullUrl(): String{
        return "https://cryptocompare.com${imageUrl}"
    }
}
