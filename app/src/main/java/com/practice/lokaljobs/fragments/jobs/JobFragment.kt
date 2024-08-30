package com.practice.lokaljobs.fragments.jobs

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.lokaljobs.MainActivity
import com.practice.lokaljobs.R
import com.practice.lokaljobs.adapter.SimpleRecyclerViewAdapter
import com.practice.lokaljobs.base.BaseFragment
import com.practice.lokaljobs.databinding.FragmentJobBinding
import com.practice.lokaljobs.databinding.LayoutTagTabBinding
import com.practice.lokaljobs.model.JobTag
import com.practice.lokaljobs.model.Result
import com.practice.lokaljobs.utils.CommonUtils
import com.practice.lokaljobs.utils.constants.Constants
import com.practice.lokaljobs.utils.errorLogs
import com.practice.lokaljobs.utils.set

class JobFragment :
    BaseFragment<FragmentJobBinding>(
    R.layout.fragment_job,
"JobFragment"
) {

    private val jobViewModel: JobViewModel by lazy {
        ViewModelProvider(this)[JobViewModel::class.java]
    }

    companion object{
        fun newInstance(data: Result): JobFragment{
            val args = Bundle()
            args.putSerializable(Constants.KEY_JOB_FOR_BUNDLE, data)
            val fragment = JobFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initViewModel() {
        try {
            val data = requireArguments().getSerializable(Constants.KEY_JOB_FOR_BUNDLE) as Result
            jobViewModel.init(data)
            viewModel.init(requireContext())
        }catch (e:Exception){
            e.errorLogs(fragmentName)
        }
    }

    override fun initToolbar() {
        try {
            binding.tbAppToolbar.set(
                requireContext()
            )
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

    override fun initUI() {
        initJobBriefLayout()
        initBookmark()
        initJobHighlightLayout()
        initJobDescription()
        initPostDetails()
        initContact()
    }

    private fun initJobBriefLayout(){
        try{
            binding.jobBriefLayout.apply {
                tvJobTitleTextView.text = jobViewModel.getTitle()
                tvSalaryTextView.text = jobViewModel.getSalary()
                tvCompanyNameTextView.text = jobViewModel.getCompanyName()
                tvCompanyLocationTextView.text = jobViewModel.getCompanyLocation()
                rvJobTagsRecyclerView.apply {
                    rvJobTagsRecyclerView.apply {
                        layoutManager = LinearLayoutManager(
                            rvJobTagsRecyclerView.context,
                            LinearLayoutManager.HORIZONTAL,
                            false)
                        adapter = object: SimpleRecyclerViewAdapter(R.layout.layout_tag_tab){

                            override fun onBindData(holder: ViewHolder, data: Any) {
                                val jobTag = data as JobTag
                                val bindingItem: LayoutTagTabBinding =
                                    DataBindingUtil.bind(holder.itemView)!!
                                bindingItem.tvTabValueTextView.text = jobTag.value
                            }

                        }
                        (adapter as SimpleRecyclerViewAdapter).setItems(jobViewModel.getJobTags())
                    }
                }
            }
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

    private fun initBookmark(){
        binding.apply {
            var isBookmarked = jobViewModel.isJobBookmarked()
            viewModel.isBookmarked(jobViewModel.getJobId()).observe(viewLifecycleOwner){
                if(it.isEmpty()){
                    isBookmarked = true
                }
            }
            jobBriefLayout.cbBookmarkCheckBox.isChecked = isBookmarked
        }
    }

    private fun initJobHighlightLayout(){
        try{
            binding.jobHighlightLayout.apply {
                tvExperienceValueTextView.text = jobViewModel.getExperience()
                tvQualificationValueTextView.text = jobViewModel.getQualification()
                tvGenderValueTextView.text = jobViewModel.getGender()
                tvShiftTimingsValueTextView.text = jobViewModel.getShiftTimings()
            }
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

    private fun initJobDescription(){
        try{
            binding.tvJobDescriptionValueTextView.text = jobViewModel.getJobDescription()
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

    private fun initPostDetails(){
        try {
            val postDateText = "Posted on ${jobViewModel.getPostedDate()}"
            binding.tvPostDateTextView.text = postDateText
            val postViewsCountText = "${jobViewModel.getViews()} views"
            binding.tvViewCountTextView.text = postViewsCountText
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

    private fun initContact(){
        if(CommonUtils.isStringEmptyOrNull(jobViewModel.getWhatsappLink())){
            binding.btWhatsappButton.visibility = View.GONE
            jobViewModel.setWhatsappLink()
        }
        if(CommonUtils.isStringEmptyOrNull(jobViewModel.getPhoneNo())){
            binding.btCallButton.visibility = View.GONE
            jobViewModel.setPhoneNo()
        }
    }

    override fun initOnClickListeners() {
        try{
            binding.jobBriefLayout.cbBookmarkCheckBox.setOnCheckedChangeListener{ _, isChecked ->
                val data = jobViewModel.getResult()
                if(isChecked){
                    data.is_bookmarked = true
                    viewModel.isBookmarked(data.id).observe(viewLifecycleOwner){
                        if(it.isEmpty()){
                            viewModel.addBookmark(data)
                        }
                    }
                }else{
                    data.is_bookmarked = false
                    viewModel.deleteBookmark(data)
                }

            }

            binding.btWhatsappButton.setOnClickListener {
                (context as MainActivity).openAnyLink(jobViewModel.getWhatsappLink())
            }

            binding.btCallButton.setOnClickListener {
                (context as MainActivity).callingContent(jobViewModel.getPhoneNo())
            }
        }catch (e: Exception){
            e.errorLogs(fragmentName)
        }
    }

}