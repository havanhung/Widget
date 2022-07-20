package com.sotatek.widget.util

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.sotatek.widget.app.home.components.Widget1

object WidgetUtil {
    fun updateWidget1(context: Context) {
        val intent = Intent(context, Widget1::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids = AppWidgetManager.getInstance(context.applicationContext).getAppWidgetIds(
            ComponentName(
                context.applicationContext,
                Widget1::class.java
            )
        )
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context.applicationContext.sendBroadcast(intent)
    }
}