package com.example.coinchecker

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.coinchecker.pojo.CoinPriceInfo
import com.example.coinchecker.pojo.CoinPriceInfoRaw
import com.example.coinchecker.retrofit.ApiFactory
import com.example.coinchecker.room.AppDataBase
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application): AndroidViewModel(application) {
    private val db = AppDataBase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val coinsList = db.coinPriceInfoDao().getCoinsList()

    init {
        loadData()
    }

    fun getSelectedCoin(fSym: String): LiveData<CoinPriceInfo>{
        return db.coinPriceInfoDao().getSelectedCoinInfo(fSym)
    }

    private fun loadData(){
        val disposable = ApiFactory.CoinApiService.getTopCoinsInfo(limit = 20)
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") ?: "" }
            .flatMap { ApiFactory.CoinApiService.getFullCoinInfo(fsyms = it) }
            .map { getCoinsList(it) }
            .delay(10,TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertCoinsInfo(it)
                Log.d("RESPONSE AND ADD SUCCESS","SUCCESS")
            },{
                it.message?.let { Log.d("Error - - ", it) }
            })
        compositeDisposable.add(disposable)
    }

    private fun getCoinsList(coinPriceInfoRaw: CoinPriceInfoRaw): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRaw.coinPriceInfoJson ?: return result
        val keys = jsonObject.keySet()
        for(key in keys){
            val coinInfo = jsonObject.getAsJsonObject(key)
            val coinDescription = coinInfo.keySet()
            for (subKey in coinDescription){
                result.add(Gson().fromJson(coinInfo.getAsJsonObject(subKey), CoinPriceInfo::class.java))
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}