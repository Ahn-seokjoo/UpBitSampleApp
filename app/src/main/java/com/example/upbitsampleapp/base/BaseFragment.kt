package com.example.upbitsampleapp.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : Fragment(layoutResId) {
    private var _binding: B? = null
    protected val binding: B
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view) ?: throw IllegalStateException("fail to bind")
    }

    override fun onDestroyView() {
        binding.unbind()
        _binding = null
        super.onDestroyView()
    }
}
