package com.practice.lokaljobs.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.lokaljobs.R
import com.practice.lokaljobs.adapter.SimpleRecyclerViewAdapter
import com.practice.lokaljobs.databinding.LayoutJobItemRecyclerviewBinding
import com.practice.lokaljobs.databinding.LayoutTagTabBinding
import com.practice.lokaljobs.model.JobTag
import com.practice.lokaljobs.model.Result
import com.practice.lokaljobs.utils.CommonUtils
import com.practice.lokaljobs.utils.CommonUtils.getNAIfEmpty
import com.practice.lokaljobs.utils.errorLogs
import com.practice.lokaljobs.utils.popMenu

class PagingAdapter(
    val actionCallback: (type: ActionType, data: Result) -> Unit)
    : PagingDataAdapter<Result, PagingAdapter.ViewHolder>(
    DiffUtilCallBack()
) {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.layout_job_item_recyclerview,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBindData(holder, position)
    }

    private fun onBindData(pageHolder: RecyclerView.ViewHolder, position: Int){
        try {
            val data = getItem(position) as Result
            val bindingItem: LayoutJobItemRecyclerviewBinding =
                DataBindingUtil.bind(pageHolder.itemView)!!
            //TODO: add the binding details
            if(data.id != 0){
                bindingItem.apply {
                    clMainConstraintLayout.visibility = View.VISIBLE
                    ivAdvertisementImageView.visibility = View.GONE
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
                        (adapter as SimpleRecyclerViewAdapter).setItems(data.job_tags)
                        tvShareTextView.setOnClickListener{
                            actionCallback(ActionType.SHARE,data)
                        }
                        ibKebabMenuImageButton.setOnClickListener {
                            popMenu(
                                pageHolder.itemView.context,
                                R.menu.share_popup_menu,
                                ibKebabMenuImageButton
                            ){
                                actionCallback(
                                    when(it){
                                        R.id.menu_share -> ActionType.SHARE
                                        R.id.menu_whatsapp -> ActionType.WHATSAPP
                                        else -> ActionType.NOTHING
                                    },
                                    data
                                )

                            }
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
                        btWhatsappChatButton.setOnClickListener{
                            actionCallback(ActionType.WHATSAPP, data)
                        }
                        btCallButton.setOnClickListener{
                            actionCallback(ActionType.PHONE_CALL, data)
                        }
                    }
                }
                bindingItem.root.setOnClickListener {
                    actionCallback(ActionType.CALL_INTENT,data)
                }
            }else{
                bindingItem.apply {
                    ivAdvertisementImageView.visibility = View.VISIBLE
                    clMainConstraintLayout.visibility = View.GONE
                    root.setOnClickListener {
                        // 5 star - do nothing
                    }
                }
            }
        }catch (e: Exception){
            e.errorLogs("Paging Adapter")
        }
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<Result>(){

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return (oldItem == newItem)
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return (oldItem.id == newItem.id)
        }

    }

}

enum class ActionType(val id: Int){
    CALL_INTENT(1000),
    PHONE_CALL(999),
    SHARE(998),
    WHATSAPP(997),
    NOTHING(-1)
}