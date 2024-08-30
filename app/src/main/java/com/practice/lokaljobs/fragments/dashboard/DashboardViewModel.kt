package com.practice.lokaljobs.fragments.dashboard

import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.practice.lokaljobs.base.BaseViewModel
import com.practice.lokaljobs.model.Result
import com.practice.lokaljobs.paging.JobsPagingSource
import com.practice.lokaljobs.utils.errorLogs
import kotlinx.coroutines.launch

class DashboardViewModel: BaseViewModel<Any>() {

    val jobPage = Pager( config = PagingConfig(pageSize = 10),
    pagingSourceFactory = {
        JobsPagingSource(repository.getLokalService())
    }).flow.cachedIn(viewModelScope)

    fun init(context: Context){
        repository.setLokalRoomService(context)
    }

    fun getBookmarks() = repository.getBookmarks().asLiveData(viewModelScope.coroutineContext)

    fun isBookmarked(jobId: Int) = repository.checkBookmarks(jobId).asLiveData(viewModelScope.coroutineContext)

    fun addBookmark(job: Result){
        try{
            viewModelScope.launch {
                repository.addBookmark(job)
            }
        }catch (e: Exception){
            e.errorLogs("DashboardViewModel")
        }
    }

    fun deleteBookmark(job: Result){
        viewModelScope.launch {
            repository.deleteBookmark(job)
        }
    }



}