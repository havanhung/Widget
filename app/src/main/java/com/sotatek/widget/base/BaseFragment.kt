package com.sotatek.widget.base

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.sotatek.widget.util.Constant

/**
 * Created by khiemnt on 08/26/2021 at 22:06
 */

typealias BindingInflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(private val bindingInflate: BindingInflate<VB>) :
    Fragment(), BaseView {

    lateinit var binding: VB
    abstract val viewModel: VM
    private var mLoadingDialog: Dialog? = null

    private lateinit var shared: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflate(inflater, container, false)
        shared =
            requireActivity().getSharedPreferences(Constant.CODE_BASE_APP, Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        registerListeners()
        initializeData()
    }


    fun setString(key: String, value: String) {
        val edit = shared.edit()
        edit.putString(key, value)
        edit.apply()
    }

    fun getString(key: String): String? {
        return shared.getString(key, "")
    }

}