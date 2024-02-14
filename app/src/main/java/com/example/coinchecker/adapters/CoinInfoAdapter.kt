package com.example.coinchecker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.coinchecker.R
import com.example.coinchecker.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso


class CoinInfoAdapter(private val context: Context): Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {
    var coinInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onCoinClickListener: OnCoinClickListener? = null
    inner class CoinInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val logoIconView: ImageView = itemView.findViewById(R.id.logoIcon)
        val nameOfCoinView: TextView = itemView.findViewById(R.id.nameOfCoin)
        val priceView: TextView = itemView.findViewById(R.id.price)
        val lastUpdateView: TextView = itemView.findViewById(R.id.lastUpdate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coin_item, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun getItemCount() = coinInfoList.size


    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        with(holder){
            with(coin){
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.resources.getString(R.string.updateTime)
                nameOfCoinView.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                priceView.text = price.toString()
                lastUpdateView.text = String.format(lastUpdateTemplate, getFormattedTime())
                Picasso.get().load(getFullUrl()).into(logoIconView)
                itemView.setOnClickListener{
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }
    interface OnCoinClickListener{
        fun onCoinClick(coin: CoinPriceInfo)
    }
}