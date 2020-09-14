package com.achmadfuad.searchuser.presentation.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.achmadfuad.searchuser.framework.view.LifecycleView
import com.achmadfuad.searchuser.presentation.adapter.ItemAdapter
import com.achmadfuad.searchuser.presentation.widget.LoadingView

interface MainView : LifecycleView {
    var itemAdapter: ItemAdapter
    var itemLayoutManager: LinearLayoutManager
    var retryListener: LoadingView.OnRetryListener

    var oneEditorActionListener : TextView.OnEditorActionListener

    fun onClickSearch(view: View)
}