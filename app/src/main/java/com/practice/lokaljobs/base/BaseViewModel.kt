package com.practice.lokaljobs.base

import androidx.lifecycle.ViewModel
import com.practice.lokaljobs.repository.MainRepository

abstract class BaseViewModel<D: Any>(): ViewModel(){
    val repository by lazy {MainRepository()}
}