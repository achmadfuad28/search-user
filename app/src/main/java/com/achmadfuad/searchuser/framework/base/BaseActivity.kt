package com.achmadfuad.searchuser.framework.base

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.achmadfuad.searchuser.BR
import com.achmadfuad.searchuser.framework.annotations.ViewLayout
import com.achmadfuad.searchuser.framework.owner.ViewDataBindingOwner
import com.achmadfuad.searchuser.framework.owner.ViewModelOwner
import com.achmadfuad.searchuser.framework.view.BindingView

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutIfDefined()
    }

    private fun setLayoutIfDefined() {
        val layoutResId = getViewLayoutResId()
        if (layoutResId == View.NO_ID) return

        if (this is ViewDataBindingOwner<*>) {
            setContentViewBinding(this, layoutResId)
            if (this is ViewModelOwner<*>) {
                binding.setVariable(BR.vm, this.viewModel)
            }
            if (this is BindingView) {
                binding.setVariable(BR.view, this)
            }

            binding.lifecycleOwner = this
        } else {
            setContentView(layoutResId)
        }
    }

    protected open fun getViewLayoutResId(): Int {
        val layout = javaClass.annotations.find { it is ViewLayout } as? ViewLayout
        return layout?.value ?: View.NO_ID
    }

    protected fun setHomeAsUpIndicator(@DrawableRes resId: Int) {
        supportActionBar?.setHomeAsUpIndicator(resId)
    }
}