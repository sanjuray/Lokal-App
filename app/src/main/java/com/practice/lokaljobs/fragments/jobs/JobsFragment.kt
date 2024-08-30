package com.practice.lokaljobs.fragments.jobs

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.lokaljobs.MainActivity
import com.practice.lokaljobs.R
import com.practice.lokaljobs.base.BaseFragment
import com.practice.lokaljobs.databinding.FragmentJobsBinding
import com.practice.lokaljobs.paging.ActionType
import com.practice.lokaljobs.paging.PagingAdapter
import com.practice.lokaljobs.utils.CommonUtils
import com.practice.lokaljobs.utils.errorLogs
import com.practice.lokaljobs.utils.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class JobsFragment :
    BaseFragment<FragmentJobsBinding>(
        R.layout.fragment_jobs,
        "JobsFragment"
) {

    private var recyclerViewAdapter = PagingAdapter{ type, data ->
        when(type){
            ActionType.CALL_INTENT -> (context as MainActivity).loadFragment(JobFragment.newInstance(data))
            ActionType.PHONE_CALL -> (context as MainActivity).callingContent(data.custom_link)
            ActionType.SHARE -> (context as MainActivity).shareContent("${data.title}\n\n${resources.getString(R.string.lokal_promo)}")
            ActionType.WHATSAPP -> (context as MainActivity).openAnyLink(data.contact_preference.whatsapp_link)
            else -> {}
        }
    }

    companion object{
        fun newInstance(): JobsFragment {
            val fragment = JobsFragment()
            return fragment
        }
    }

    override fun initViewModel() {
        try {
            lifecycleScope.launch {
                viewModel.jobPage.collectLatest {
                    recyclerViewAdapter.submitData(it)
                }
            }
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

    override fun initToolbar() {
        //Not Required
    }

    override fun initUI() {
        try {
            initRecyclerView()
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

    private fun initRecyclerView(){
        try {
            val linearLayoutManager = LinearLayoutManager(context)
            binding.rvJobCardRecyclerView.apply {
                layoutManager = linearLayoutManager
                adapter = recyclerViewAdapter
            }
            val dividerItemDecoration = DividerItemDecoration(
                context,
                linearLayoutManager.orientation
            )
            binding.rvJobCardRecyclerView.addItemDecoration(dividerItemDecoration)
            recyclerViewAdapter.addLoadStateListener { loadState ->
                binding.rvJobCardRecyclerView.isVisible = loadState.refresh is LoadState.NotLoading
                binding.pbLoadingProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                binding.btRetryButton.isVisible = loadState.source.refresh is LoadState.Error
                handleError(loadState)
            }
            binding.btRetryButton.setOnClickListener {
                recyclerViewAdapter.retry()
            }
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }

    }

    override fun initOnClickListeners() {
        CommonUtils.networkLogs(2,"")
    }

    private fun handleError(loadState: CombinedLoadStates){
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error

        errorState?.let {
            showToast(requireContext(),resources.getString(R.string.retry))
        }
    }

}