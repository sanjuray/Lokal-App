package com.practice.lokaljobs.fragments.dashboard

import com.practice.lokaljobs.MainActivity
import com.practice.lokaljobs.R
import com.practice.lokaljobs.base.BaseFragmentWithoutViewModel
import com.practice.lokaljobs.databinding.FragmentDashboardBinding
import com.practice.lokaljobs.fragments.bookmarks.BookmarkFragment
import com.practice.lokaljobs.fragments.jobs.JobsFragment
import com.practice.lokaljobs.utils.errorLogs
import com.practice.lokaljobs.utils.set

class DashboardFragment :
    BaseFragmentWithoutViewModel<FragmentDashboardBinding>(
    R.layout.fragment_dashboard,
    "DashboardFragment"
){

    companion object{
        private var defaultTab = Navigation.Jobs
        fun newInstance(): DashboardFragment {
            val fragment = DashboardFragment()
            return fragment
        }
    }

    override fun initToolbar() {
        try{
            val title = when(defaultTab){
                Navigation.Jobs -> "Lokal Jobs"
                Navigation.Bookmarks -> "Saved Jobs"
            }
            binding.tbAppToolbar.set(
                requireContext(),
                title,
                false
            )
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

    override fun initUI() {
        try {
            (context as MainActivity).loadFragment(
                when(defaultTab.id){
                    Navigation.Jobs.id -> JobsFragment.newInstance()
                    Navigation.Bookmarks.id -> BookmarkFragment.newInstance()
                    else -> JobsFragment.newInstance()
                },
                binding
            )
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

    override fun initOnClickListeners() {
        initNavigation()
    }

    private fun initNavigation(){
        try {
            binding.bnvQuickNavigationBottomNavigationView.background = null
            binding.bnvQuickNavigationBottomNavigationView.setOnItemSelectedListener { item ->

                defaultTab = if (item.itemId == R.id.jobs) {
                    Navigation.Jobs
                } else {
                    Navigation.Bookmarks
                }
                initToolbar()
                initUI()
                true
            }
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }


}

enum class Navigation(val id: Int){
    Jobs(0),
    Bookmarks(1)
}