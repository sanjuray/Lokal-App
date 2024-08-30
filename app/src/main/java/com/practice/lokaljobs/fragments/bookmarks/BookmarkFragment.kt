package com.practice.lokaljobs.fragments.bookmarks

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.lokaljobs.MainActivity
import com.practice.lokaljobs.R
import com.practice.lokaljobs.adapter.EmptyStateConfig
import com.practice.lokaljobs.adapter.SimpleRecyclerViewAdapter
import com.practice.lokaljobs.base.BaseFragment
import com.practice.lokaljobs.databinding.FragmentBookmarkBinding
import com.practice.lokaljobs.databinding.LayoutJobItemRecyclerviewBinding
import com.practice.lokaljobs.databinding.LayoutTagTabBinding
import com.practice.lokaljobs.fragments.jobs.JobFragment
import com.practice.lokaljobs.model.JobTag
import com.practice.lokaljobs.model.Result
import com.practice.lokaljobs.paging.ActionType
import com.practice.lokaljobs.utils.CommonUtils
import com.practice.lokaljobs.utils.CommonUtils.getNAIfEmpty
import com.practice.lokaljobs.utils.errorLogs
import com.practice.lokaljobs.utils.popMenu

class BookmarkFragment :
    BaseFragment<FragmentBookmarkBinding>(
        R.layout.fragment_bookmark,
        "BookmarkFragment"
){

    companion object{
        fun newInstance(): BookmarkFragment {
            val fragment = BookmarkFragment()
            return fragment
        }
    }

    override fun initViewModel() {
        viewModel.init(requireContext())
    }

    override fun initToolbar() {
        //Not Required here
    }

    override fun initUI() {
        initRecyclerView()
    }

    private fun initRecyclerView(){
        try{
            binding.rvBookmarksRecyclerView.apply {
                val linearLayoutManager = LinearLayoutManager(context)
                layoutManager = linearLayoutManager
                adapter = object: SimpleRecyclerViewAdapter(
                    R.layout.layout_job_item_recyclerview,
                    EmptyStateConfig("No Bookmarks Available","Added Bookmarks are shown here")
                ){
                    override fun onBindData(holder: ViewHolder, data: Any) {
                        val data = data as Result
                        val bindingItem: LayoutJobItemRecyclerviewBinding =
                            DataBindingUtil.bind(holder.itemView)!!
                        bindingItem.apply {
                            tvJobTitleTextView.text = getNAIfEmpty(data.title)
                            tvSalaryTextView.text = getNAIfEmpty(data.primary_details.Salary)
                            tvCompanyNameTextView.text = getNAIfEmpty(data.company_name)
                            tvCompanyLocationTextView.text = getNAIfEmpty(data.primary_details.Place)
                            rvJobTagsRecyclerView.apply {
                                layoutManager = LinearLayoutManager(
                                    rvJobTagsRecyclerView.context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false)
                                adapter = object: SimpleRecyclerViewAdapter(R.layout.layout_tag_tab){

                                    override fun onBindData(holder: ViewHolder, data: Any) {
                                        val jobTag = data as JobTag
                                        val bindingTab: LayoutTagTabBinding =
                                            DataBindingUtil.bind(holder.itemView)!!
                                        bindingTab.tvTabValueTextView.text = jobTag.value
                                    }
                                }
                                //redudant
                                (adapter as SimpleRecyclerViewAdapter).setItems(data.job_tags)
                                tvShareTextView.setOnClickListener{
                                    (context as MainActivity).shareContent("${data.title}\n\n${resources.getString(R.string.lokal_promo)}")
                                }
                                if(CommonUtils.isStringEmptyOrNull(data.custom_link)){
                                    btCallButton.visibility = View.GONE
                                }else{
                                    btCallButton.visibility = View.VISIBLE
                                }
                                if(CommonUtils.isStringEmptyOrNull(data.contact_preference.whatsapp_link)){
                                    btWhatsappChatButton.visibility = View.GONE
                                }else{
                                    btWhatsappChatButton.visibility = View.VISIBLE
                                }
                                ibKebabMenuImageButton.setOnClickListener {
                                    popMenu(
                                        holder.itemView.context,
                                        R.menu.share_popup_menu,
                                        ibKebabMenuImageButton
                                    ){
                                        when(it){
                                            R.id.menu_share -> tvShareTextView.performClick()
                                            R.id.menu_whatsapp -> btWhatsappChatButton.performClick()
                                        }
                                    }
                                }
                                btWhatsappChatButton.setOnClickListener{
                                    (context as MainActivity).openAnyLink(data.contact_preference.whatsapp_link)
                                }
                                btCallButton.setOnClickListener{
                                    (context as MainActivity).callingContent(data.custom_link)
                                }
                            }
                        }
                        bindingItem.root.setOnClickListener {
                            (context as MainActivity).loadFragment(JobFragment.newInstance(data))
                        }
                    }
                }
                val dividerItemDecoration = DividerItemDecoration(
                    context,
                    linearLayoutManager.orientation
                )
                addItemDecoration(dividerItemDecoration)
                viewModel.getBookmarks().observe(viewLifecycleOwner){
                    (adapter as SimpleRecyclerViewAdapter).setItems(it)
                }
            }
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

    override fun initOnClickListeners() {
        //not used here
    }

}

