package com.achmadfuad.searchuser.framework.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.achmadfuad.searchuser.BR
import com.achmadfuad.searchuser.framework.owner.ViewDataBindingOwner
import com.achmadfuad.searchuser.framework.owner.ViewModelOwner
import com.achmadfuad.searchuser.framework.view.BindingViewHolder

abstract class BaseViewHolder<T>(val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    init {
        if (this is ViewDataBindingOwner<*>) {
            setViewBinding(view)
            if (this is ViewModelOwner<*>) {
                binding.setVariable(BR.vm, viewModel)
                binding.executePendingBindings()
            }
            if (this is BindingViewHolder<*>) {
                binding.setVariable(BR.view, this)
            }
        }
    }

    abstract fun bindData(data: T)
}
