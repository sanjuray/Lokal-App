package com.practice.lokaljobs.paging.loading_state

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.practice.lokaljobs.R
import com.practice.lokaljobs.databinding.LayoutItemLoaderBinding
import com.practice.lokaljobs.utils.CommonUtils

class LoadStateViewHolder(
    private val binding: LayoutItemLoaderBinding,
    retry: () -> Unit
): RecyclerView.ViewHolder(binding.root) {

    init{
        binding.btRetryButton.setOnClickListener{
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState){
        if(loadState is LoadState.Error){
            binding.tbErrorMessageTextView.text = "Try Again!!"
        }
        binding.pgProgressBar.isVisible = loadState is LoadState.Loading
        binding.btRetryButton.isVisible = loadState !is LoadState.Loading
        binding.tbErrorMessageTextView.isVisible = loadState !is LoadState.Loading
    }
    companion object{
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_loader,parent,false)
            val binding = LayoutItemLoaderBinding.bind(view)
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                CommonUtils.convertDpToPx(400)
            )
            binding.root.layoutParams = params
            return LoadStateViewHolder(binding,retry)
        }
    }

}