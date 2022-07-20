package com.sotatek.widget.app.home.components

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sotatek.widget.R
import com.sotatek.widget.data.remote.dto.CoinDto
import com.sotatek.widget.util.Constant

class ListViewWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return ListViewRemoteViewsFactory(this.applicationContext, intent)
    }
}

internal class ListViewRemoteViewsFactory(private val context: Context, intent: Intent) :
    RemoteViewsService.RemoteViewsFactory {
    private var coins: List<CoinDto> = ArrayList()

    override fun onCreate() {
        prepareData()
    }

    private fun prepareData() {
        val pref = context.getSharedPreferences(Constant.CODE_BASE_APP, Context.MODE_PRIVATE)
        val data = pref.getString(Constant.WIDGET1_DATA, null);
        val type = object : TypeToken<List<CoinDto>>() {}.type
        coins = Gson().fromJson(data, type)
        Log.d("khiembeo",coins.size.toString())
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_coin_item)

        // Get the current car
        val coin = coins[position]

        // Fill the list item with data
        remoteViews.setTextViewText(R.id.text_name, coin.coinName)
        remoteViews.setTextViewText(R.id.text_price, coin.price)
        remoteViews.setTextViewText(R.id.text_vol, coin.vol)
        remoteViews.setTextViewText(R.id.text_total_price, coin.priceTotal)
        remoteViews.setTextViewText(R.id.text_percent, coin.percent)
        val percentResource =
            if (coin.percent.contains("+")) R.drawable.bg_border_positive else R.drawable.bg_border_negative
        remoteViews.setInt(R.id.percent, "setBackgroundColor", percentResource)

        val extras = Bundle()
        extras.putSerializable("coin", coin)

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        remoteViews.setOnClickFillInIntent(R.id.activity_chooser_view_content, fillInIntent)
        return remoteViews
    }

    override fun getCount(): Int {
        return coins.size
    }

    override fun onDataSetChanged() {
        prepareData()
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onDestroy() {
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }


}