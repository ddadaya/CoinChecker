package com.example.coinchecker

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.coinchecker.adapters.CoinInfoAdapter
import com.example.coinchecker.pojo.CoinPriceInfo


class CoinsActivity: AppCompatActivity() {
    private lateinit var viewModel: CoinViewModel
    private lateinit var rvCoinInfo: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coins_activity)
        rvCoinInfo = findViewById(R.id.rvCoinInfo)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener{
            override fun onCoinClick(coin: CoinPriceInfo) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container,SelectedCoinFragment.newInstance(coin.fromSymbol))
                    .addToBackStack("selectedCoinFragment")
                    .commit()
                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        }
        rvCoinInfo.adapter = adapter

        viewModel = ViewModelProviders.of(this)[CoinViewModel::class.java]
        viewModel.coinsList.observe(this) {
            adapter.coinInfoList = it
        }
    }
}