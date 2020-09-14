package com.achmadfuad.searchuser.presentation.viewmodel

import androidx.databinding.ObservableField
import com.achmadfuad.searchuser.domain.entities.UserItemResult
import com.achmadfuad.searchuser.framework.base.BaseViewModel

class ItemViewModel : BaseViewModel() {

    var bName = ObservableField<String>()

    fun resetData() {
        bName.set(null)
    }

    fun setData(data: UserItemResult) {
        bName.set(data.name)

    }
}