package com.example.coinchecker

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso

class SelectedCoinFragment: Fragment(R.layout.selected_coin) {
    private lateinit var viewModel: CoinViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fromSymbol = arguments?.getString(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProviders.of(this)[CoinViewModel::class.java]
        if (fromSymbol!=null){
            viewModel.getSelectedCoin(fromSymbol).observe(viewLifecycleOwner) {
                view.findViewById<TextView>(R.id.valueView).text = String.format(resources.getString(R.string.symbols_template),it.fromSymbol,it.toSymbol)
                view.findViewById<TextView>(R.id.lastUpdView).text = String.format(resources.getString(R.string.updateTime), it.getFormattedTime())
                view.findViewById<TextView>(R.id.minPerDayView).text = String.format(resources.getString(R.string.min_price_template), it.minPrice)
                view.findViewById<TextView>(R.id.maxPerDayView).text =String.format(resources.getString(R.string.max_price_template), it.maxPrice)
                view.findViewById<TextView>(R.id.avgPriceView).text =String.format(resources.getString(R.string.avg_price_template), it.median)
                view.findViewById<TextView>(R.id.lastTradeView).text =String.format(resources.getString(R.string.last_market_template), it.lastMarket)
                Picasso.get().load(it.getFullUrl()).into(view.findViewById<ImageView>(R.id.logoView))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    companion object{
       private const val EXTRA_FROM_SYMBOL = "EXTRA_FROM_SYMBOL"
        fun newInstance(fromSymbol: String) = SelectedCoinFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_FROM_SYMBOL,fromSymbol)
            }
        }
    }
}