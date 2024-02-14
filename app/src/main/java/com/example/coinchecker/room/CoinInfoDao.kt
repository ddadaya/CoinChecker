package com.example.coinchecker.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinchecker.pojo.CoinPriceInfo

@Dao
interface CoinInfoDao {
    @Query("SELECT * FROM full_coin_info ORDER BY lastUpdate DESC")
    fun getCoinsList(): LiveData<List<CoinPriceInfo>>

    @Query("SELECT * FROM full_coin_info WHERE fromSymbol = :fsym LIMIT 1")
    fun getSelectedCoinInfo(fsym: String): LiveData<CoinPriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoinsInfo(coinsList: List<CoinPriceInfo>)
}