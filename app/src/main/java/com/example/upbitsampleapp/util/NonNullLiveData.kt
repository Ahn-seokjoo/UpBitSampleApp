package com.example.upbitsampleapp.util

import androidx.lifecycle.LiveData

open class NonNullLiveData<T : Any>(value: T) : LiveData<T>(value) {
    override fun getValue(): T {
        return super.getValue() as T
    }
}

class NonNullMutableLiveData<T : Any>(value: T) : NonNullLiveData<T>(value) {
    public override fun setValue(value: T) {
        super.setValue(value)
    }

    public override fun postValue(value: T) {
        super.postValue(value)
    }
}
