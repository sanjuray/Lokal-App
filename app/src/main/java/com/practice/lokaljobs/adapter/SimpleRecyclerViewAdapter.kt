package com.practice.lokaljobs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.lokaljobs.R
import com.practice.lokaljobs.databinding.LayoutRowEmptyStateBinding
import com.practice.lokaljobs.utils.CommonUtils
import com.practice.lokaljobs.utils.errorLogs

abstract class SimpleRecyclerViewAdapter(
    @LayoutRes private val layoutResource: Int,
    private val emptyStateConfig: EmptyStateConfig ?= null
): RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder>() {

    private val items = ArrayList<Any>()

    fun setItems(newItems: List<Any>) {
        try {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        } catch (e: Exception) {
            e.errorLogs("setItems@SimpleRecyclerViewAdapter")
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return if(getIfToShowEmptyState()){
            1
        }else{
            items.size
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimpleRecyclerViewAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                when(viewType){
                    ItemViewType.EMPTY_STATE.value -> R.layout.layout_row_empty_state
                    ItemViewType.NORMAL_STATE.value -> layoutResource
                    else -> R.layout.layout_empty
                },
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(getTheItemStateType(position)){
            ItemViewType.EMPTY_STATE -> bindDataToEmptyState(holder)
            ItemViewType.NORMAL_STATE -> onBindData(holder, items[position])
        }
    }

    private fun bindDataToEmptyState(holder: ViewHolder) {
        try {
            if(getIfToShowEmptyState()) {
                val bindingRow: LayoutRowEmptyStateBinding =
                    DataBindingUtil.bind(holder.itemView)!!
                bindingRow.apply {
                    tvEmptyStateTitleTextView.text = emptyStateConfig!!.title
                    tvEmptyStateDescriptionTextView.text = emptyStateConfig!!.description
                    if(emptyStateConfig.imageResource != null){
                        ivEmptyStateIcon.setImageResource(emptyStateConfig.imageResource)
                    }
                }
                val params =
                    RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        CommonUtils.convertDpToPx(700)
                    )
                bindingRow.root.layoutParams = params
            }
        } catch (e: Exception) {
            e.errorLogs("bindDataToEmptyState@SimpleRecyclerView")
        }
    }

    abstract fun onBindData(holder: ViewHolder, data: Any)

    private fun getTheItemStateType(position: Int): ItemViewType {
        return if (getIfToShowEmptyState()) {
            ItemViewType.EMPTY_STATE
        }else {
            ItemViewType.NORMAL_STATE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getTheItemStateType(position)){
            ItemViewType.EMPTY_STATE -> ItemViewType.EMPTY_STATE.value
            ItemViewType.NORMAL_STATE -> ItemViewType.NORMAL_STATE.value
        }
    }

    private fun getIfToShowEmptyState(): Boolean {
        return items.isEmpty() && emptyStateConfig != null
    }
}

enum class ItemViewType(val value: Int) {
    NORMAL_STATE(1),
    EMPTY_STATE(0),
}

data class EmptyStateConfig(val title: String, val description: String, val imageResource: Int ?= null)