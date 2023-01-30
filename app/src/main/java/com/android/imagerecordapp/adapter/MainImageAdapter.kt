package com.android.imagerecordapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.imagerecordapp.data.ImageViewData
import com.android.imagerecordapp.databinding.ImageViewItemBinding

class MainImageAdapter : RecyclerView.Adapter<MainImageAdapter.MainImageViewHolder>() {
    // Activity에서 사용하기 위한 클릭 리스너
    interface OnItemClickListener{
        fun onItemClick(v: View, data: ImageViewData, pos: Int)
    }

    private var list = ArrayList<ImageViewData>()

    private var listener: OnItemClickListener? = null

    fun setOnItemLongClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    inner class MainImageViewHolder(private val mBinding: ImageViewItemBinding): RecyclerView.ViewHolder(mBinding.root){
        fun bind(viewData: ImageViewData) {
            mBinding.imageViewItem = viewData

            if(adapterPosition != RecyclerView.NO_POSITION) {
                itemView.setOnLongClickListener {
                    listener?.onItemClick(itemView, viewData, adapterPosition)
                    false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainImageViewHolder {
        val binding =
            ImageViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainImageViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    fun setData(imageList: ArrayList<ImageViewData>) {
        list.addAll(imageList)
        notifyDataSetChanged()
//        notifyItemRangeInserted(0, list.size-1)
    }

    fun insertData(imageList: ArrayList<ImageViewData>) {
        list.clear()
        list.addAll(imageList)
        notifyDataSetChanged()
//        notifyItemRangeChanged(0, list.size-1)
    }

    fun removeData(imageViewData: ImageViewData) {
        val index = list.indexOf(imageViewData)
        list.remove(imageViewData)
        notifyDataSetChanged()
//        notifyItemRemoved(index)
    }

    fun setPaginationList(imageList: ArrayList<ImageViewData>) {
        val positionStart = imageList.size-1
        list.addAll(imageList)
        notifyDataSetChanged()
//        notifyItemRangeChanged(positionStart, imageList.size)
    }
}