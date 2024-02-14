package com.example.coinchecker.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coinchecker.pojo.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    companion object{
        private var db: AppDataBase? = null
        private const val NAME_DB = "main.db"

        fun getInstance(context: Context): AppDataBase {
            db?.let { return it }
            val instance = Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                NAME_DB
            ).build()
            db = instance

            return instance
        }

    }

    abstract fun coinPriceInfoDao(): CoinInfoDao
}