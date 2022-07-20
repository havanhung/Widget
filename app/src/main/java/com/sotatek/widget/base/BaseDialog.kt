package com.sotatek.widget.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding

/**
 * Created by khiemnt on 08/26/2021 at 22:33
 */

abstract class BaseDialog<VB : ViewBinding>(private val bindingInflate: BindingInflate<VB>) : DialogFragment(),
        BaseView {

    lateinit var binding: VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = bindingInflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        registerListeners()
        initializeData()
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, javaClass.name)
    }
}