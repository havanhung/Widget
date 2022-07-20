package com.sotatek.widget.app.home

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.sotatek.widget.R
import com.sotatek.widget.app.home.components.Widget1
import com.sotatek.widget.base.BaseFragment
import com.sotatek.widget.data.remote.dto.CoinDto
import com.sotatek.widget.databinding.FragmentWidgetsBinding
import com.sotatek.widget.util.Constant
import com.sotatek.widget.util.WidgetUtil

/**
 * Created by khiemnt on 12/25/2021 at 16:30
 */

class HomeFragment : BaseFragment<FragmentWidgetsBinding, HomeViewModel>(FragmentWidgetsBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()

    override fun initializeViews() {

    }

    override fun registerListeners() {
        binding.apply {
            btnAppWidget1.setOnClickListener { onTouchedAddWidget(it) }
            btnAppWidget2.setOnClickListener { onTouchedAddWidget(it) }
            btnAppWidget3.setOnClickListener { onTouchedAddWidget(it) }
            btnResetWidget1.setOnClickListener {
                changeData()
                WidgetUtil.updateWidget1(requireContext())
            }
        }
    }

    override fun initializeData() {
        prepareData()
    }

    private fun prepareData() {
        val coins: ArrayList<CoinDto> = ArrayList()
        coins.add(CoinDto("BTC", "$20.119,19", "Vol 2,21 B", "20.119,19 $", "+0.17%"))
        coins.add(CoinDto("ETH", "$1.079,94", "Vol 1,74 B", "20.119,19 $", "-1.02%"))
        coins.add(CoinDto("BNB", "$20.119,19", "Vol 2,21 B", "20.119,19 $", "+0.17%"))
        coins.add(CoinDto("IMX", "$20.119,19", "Vol 2,21 B", "20.119,19 $", "+0.17%"))
        coins.add(CoinDto("LINK", "$20.119,19", "Vol 2,21 B", "20.119,19 $", "+0.17%"))
        coins.add(CoinDto("MANA", "$20.119,19", "Vol 2,21 B", "20.119,19 $", "+0.17%"))
        val widget1Data= Gson().toJson(coins)
        setString(Constant.WIDGET1_DATA, widget1Data)
    }

    fun changeData() {
        val coins: ArrayList<CoinDto> = ArrayList()
        coins.add(CoinDto("CELLO", "$20.119,19", "Vol 2,21 B", "20.119,19 $", "+0.17%"))
        coins.add(CoinDto("LUNA", "$1.079,94", "Vol 1,74 B", "20.119,19 $", "-1.02%"))
        coins.add(CoinDto("NEAR", "$20.119,19", "Vol 2,21 B", "20.119,19 $", "+0.17%"))
        coins.add(CoinDto("BUNNI", "$20.119,19", "Vol 2,21 B", "20.119,19 $", "+0.17%"))
        coins.add(CoinDto("MANA", "$20.119,19", "Vol 2,21 B", "20.119,19 $", "+0.17%"))
        val widget1Data= Gson().toJson(coins)
        setString(Constant.WIDGET1_DATA, widget1Data)
    }


    private fun onTouchedAddWidget(view: View) {
        if (Build.VERSION.SDK_INT < 26 || !AppWidgetManager.getInstance(context).isRequestPinAppWidgetSupported) {
            Snackbar.make(view, R.string.add_widget_failed_content, Snackbar.LENGTH_LONG).show()
            return
        }
        var componentName: ComponentName? = null
        when (view.id) {
            R.id.btn_app_widget1 -> {
                componentName = ComponentName(requireContext(), Widget1::class.java)
            }
        }
        componentName?.let {
            pinAppWidget(it)
        }
    }

    private fun pinAppWidget(componentName: ComponentName) {
        if (Build.VERSION.SDK_INT >= 26) {
            val success = try {
                AppWidgetManager.getInstance(requireContext())
                    .requestPinAppWidget(componentName, null, null)
            } catch (e: Exception) {
                false
            }
            if (!success) {
                Snackbar.make(requireView(), R.string.add_widget_failed_content, Snackbar.LENGTH_LONG).show()
            }
        } else {
            Snackbar.make(requireView(), R.string.add_widget_failed_content, Snackbar.LENGTH_LONG).show()
        }
    }
}