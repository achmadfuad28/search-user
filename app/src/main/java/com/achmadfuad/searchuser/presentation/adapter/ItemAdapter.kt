package com.achmadfuad.searchuser.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.achmadfuad.searchuser.R
import com.achmadfuad.searchuser.databinding.ItemListBinding
import com.achmadfuad.searchuser.domain.entities.UserItemResult
import com.achmadfuad.searchuser.framework.base.BasePagedListAdapter
import com.achmadfuad.searchuser.framework.base.BaseViewHolder
import com.achmadfuad.searchuser.framework.base.NetworkStateItemPagingViewHolder
import com.achmadfuad.searchuser.framework.owner.ViewDataBindingOwner
import com.achmadfuad.searchuser.framework.widget.WebImageView
import com.achmadfuad.searchuser.presentation.view.ItemView
import com.achmadfuad.searchuser.presentation.viewmodel.ItemViewModel

class ItemAdapter(retryCallback: () -> Unit) : BasePagedListAdapter<UserItemResult>(
    ITEM_COMPARATOR, retryCallback) {

    companion object {
        private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UserItemResult>() {
            override fun areItemsTheSame(oldItem: UserItemResult, newItem: UserItemResult): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: UserItemResult, newItem: UserItemResult): Boolean =
                oldItem == newItem
        }
    }
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_list -> (holder as ViewHolder).bindData(getItem(position)!!)
            R.layout.network_state_item -> (holder as NetworkStateItemPagingViewHolder).bindData(getNetworkState()!!)
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.item_list
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            R.layout.item_list -> ViewHolder.create(parent)
            R.layout.network_state_item -> NetworkStateItemPagingViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    class ViewHolder(
        context: Context,
        view: View
    ) :
        ItemView,
        BaseViewHolder<UserItemResult>(context, view),
        ViewDataBindingOwner<ItemListBinding> {

        override lateinit var binding: ItemListBinding
        private lateinit var data: UserItemResult
        private var viewModel: ItemViewModel? = null

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list, parent, false)
                return ViewHolder(parent.context, view)
            }
        }

        init {
            binding.vm = ItemViewModel()
            binding.view = this
            viewModel = binding.vm
        }

        override fun bindData(data: UserItemResult) {
            this.data = data
            viewModel?.resetData()
            viewModel?.setData(data)
            data.avatar?.let {
                binding.imgOwnerAvatar.setImageUrl(it, WebImageView.TransformType.ROUNDED_CORNER)
            }

        }


    }
}