package com.practice.lokaljobs.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.practice.lokaljobs.fragments.dashboard.DashboardViewModel
import com.practice.lokaljobs.utils.errorLogs

abstract class BaseFragment<VB: ViewBinding
        //,VM: BaseViewModel<Any>
         >(
    private val layoutId: Int,
    val fragmentName: String
): Fragment() {

    val viewModel: DashboardViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            SavedStateViewModelFactory(requireActivity().application, requireActivity())
        )[DashboardViewModel::class.java]
    }

    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            binding = DataBindingUtil.inflate(
                inflater,
                layoutId, container, false
            )
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            init()
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

    private fun init(){
        initViewModel()
        initToolbar()
        initUI()
        initOnClickListeners()
    }

    abstract fun initViewModel()

    abstract fun initToolbar()

    abstract fun initUI()

    abstract fun initOnClickListeners()

}

abstract class BaseFragmentWithoutViewModel<VB: ViewBinding>(
    private val layoutId: Int,
    val fragmentName: String
): Fragment() {

    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            binding = DataBindingUtil.inflate(
                inflater,
                layoutId, container, false
            )
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            init()
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

    private fun init(){
        initToolbar()
        initUI()
        initOnClickListeners()
    }

    abstract fun initToolbar()

    abstract fun initUI()

    abstract fun initOnClickListeners()

}

