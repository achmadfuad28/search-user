package com.achmadfuad.searchuser.framework.owner

import com.achmadfuad.searchuser.framework.base.BaseViewModel

interface ViewModelOwner<T : BaseViewModel> {
    val viewModel: T
}