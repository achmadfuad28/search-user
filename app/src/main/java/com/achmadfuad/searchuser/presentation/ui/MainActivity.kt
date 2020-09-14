package com.achmadfuad.searchuser.presentation.ui

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.achmadfuad.searchuser.R
import com.achmadfuad.searchuser.databinding.ActivityMainBinding
import com.achmadfuad.searchuser.framework.base.BaseActivity
import com.achmadfuad.searchuser.framework.common.NetworkState
import com.achmadfuad.searchuser.framework.owner.ViewDataBindingOwner
import com.achmadfuad.searchuser.framework.owner.ViewModelOwner
import com.achmadfuad.searchuser.presentation.adapter.ItemAdapter
import com.achmadfuad.searchuser.presentation.view.MainView
import com.achmadfuad.searchuser.presentation.viewmodel.MainViewModel
import com.achmadfuad.searchuser.presentation.widget.LoadingView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(),
    MainView,
    ViewModelOwner<MainViewModel>,
    ViewDataBindingOwner<ActivityMainBinding> {

    override lateinit var binding: ActivityMainBinding
    override val viewModel: MainViewModel by viewModel()

    override lateinit var itemAdapter: ItemAdapter
    override var itemLayoutManager: LinearLayoutManager = LinearLayoutManager(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.let {
            setSupportActionBar(it)
            supportActionBar?.title = getString(R.string.app_name)
        }

        itemAdapter = ItemAdapter {
            viewModel.retry()
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        binding.loadingView.showEmpty(
            getString(R.string.title_initial_state),
            getString(R.string.subtitle_initial_state),
            false
        )

        observeProgressStatus()
    }

    override fun getViewLayoutResId(): Int {
        return R.layout.activity_main
    }

    override var retryListener = object : LoadingView.OnRetryListener {
        override fun onRetry() {
            viewModel.refresh()
        }
    }

    override var oneEditorActionListener = TextView.OnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if(viewModel.userList == null) {
                observeData(viewModel.bTextSearch.get().toString())
            } else {
                viewModel.searchByKeyword(viewModel.bTextSearch.get().toString())
            }
            return@OnEditorActionListener true
        }
        return@OnEditorActionListener false
    }


    override fun onClickSearch(view: View) {
        if(viewModel.userList == null) {
            observeData(viewModel.bTextSearch.get().toString())
        } else {
            viewModel.searchByKeyword(viewModel.bTextSearch.get().toString())
        }
    }

    private fun observeProgressStatus() {
        observeData(viewModel.getInitialState()) { initialState ->
            initialState?.let {
                when (initialState) {
                    NetworkState.LOADING -> {
                        binding.loadingView.showLoading()
                        viewModel.showLoading()
                    }
                    NetworkState.LOADED -> {
                        binding.swipeRefresh.isRefreshing = false
                        viewModel.hideLoading()

                    }
                    NetworkState.EMPTY -> {
                        viewModel.showLoading()
                        binding.loadingView.showEmpty(
                            getString(R.string.title_empty_state),
                            getString(R.string.subtitle_empty_state),
                            false
                        )
                    }
                    else -> {
                        binding.swipeRefresh.isRefreshing = false
                        viewModel.showLoading()
                        initialState.exception?.message?.let {
                            binding.loadingView.showError("", it)
                        }
                    }
                }
            }

            observeData(viewModel.getNetworkState()) { networkState ->
                networkState?.let { state ->
                    itemAdapter.setNetworkState(state)
                    when (state) {
                        NetworkState.LOADING -> {
                        }
                        NetworkState.LOADED -> {
                            binding.swipeRefresh.isRefreshing = false
                        }
                        else -> {
                            if (state.exception != null) {
                                binding.rvUserList.smoothScrollToPosition(itemAdapter.itemCount)
                            }

                            state.exception?.message.let {
                                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                            }

                        }
                    }
                }
            }
        }
    }

    private fun observeData(query: String) {
        observeData(viewModel.getUserList(query)) { members ->
            members?.let {
                itemAdapter.submitList(members)
            }
        }
    }
}

