package com.sotatek.widget.app.home.components

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import com.sotatek.widget.MainActivity
import com.sotatek.widget.R
import com.sotatek.widget.util.Constant
import okhttp3.*
import java.io.IOException

class Widget1 : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { appWidgetId ->
            val intent = Intent(context, ListViewWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val remoteViews = RemoteViews(context.packageName, R.layout.widget1).apply {
                setRemoteAdapter(R.id.list_coins, intent)
            }
            setClickable(context, remoteViews)
            fetchData(appWidgetManager, appWidgetId, remoteViews, context)
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    private fun onUpdate(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val thisAppWidgetComponentName = ComponentName(context.packageName, javaClass.name)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName)
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_coins)
        onUpdate(context, appWidgetManager, appWidgetIds)
    }

    fun fetchData(
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        views: RemoteViews,
        context: Context
    ){
        //TODO
//        val url = ""
//        val request = Request.Builder().url(url).build()
//
//        val client = OkHttpClient()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onResponse(call: Call, response: Response) {
//                // successful GET request
//                Log.i("test1", "GET request successful.")
//
//                // converts response into string
//                val body = response.body?.string()
//                val pref = context.getSharedPreferences(Constant.CODE_BASE_APP, Context.MODE_PRIVATE)
//                //set data to sp
//                // makes final call to update the widget
//                appWidgetManager.updateAppWidget(appWidgetId, views)
//
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                // failed GET request
//                Log.i("test1", "Failed to execute GET request.")
//            }
//        })
    }

    private fun setClickable(context: Context, remoteViews: RemoteViews) {
        val mainIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        remoteViews.setOnClickPendingIntent(R.id.text_see_more, pendingIntent)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (context != null) {
            onUpdate(context)
        }
    }

}