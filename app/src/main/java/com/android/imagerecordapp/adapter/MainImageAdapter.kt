package com.android.imagerecordapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.imagerecordapp.data.ImageViewData
import com.android.imagerecordapp.databinding.ImageViewItemBinding
import javax.inject.Inject

class MainImageAdapter @Inject constructor() : PagingDataAdapter<ImageViewData, MainImageAdapter.MainImageViewHolder>(ARTICLE_DIFF_CALLBACK) {
    // Activity에서 사용하기 위한 클릭 리스너
    interface OnItemClickListener{
        fun onItemClick(v: View, data: ImageViewData, pos: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    inner class MainImageViewHolder(private val mBinding: ImageViewItemBinding): RecyclerView.ViewHolder(mBinding.root){
        fun bind(viewData: ImageViewData) {
            mBinding.imageViewItem = viewData
            mBinding.ctImageView.setOnClickListener {
                listener?.onItemClick(itemView, viewData, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainImageViewHolder {
        val binding =
            ImageViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainImageViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null) {
            holder.bind(item)
        }
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageViewData>() {
            override fun areItemsTheSame(oldItem: ImageViewData, newItem: ImageViewData): Boolean =
                oldItem.imgUri == newItem.imgUri

            override fun areContentsTheSame(oldItem: ImageViewData, newItem: ImageViewData): Boolean =
                oldItem == newItem
        }
    }
}