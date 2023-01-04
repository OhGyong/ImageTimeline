package com.android.imagerecordapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.imagerecordapp.data.GridViewData
import com.android.imagerecordapp.databinding.GridViewItemBinding

class MainGridAdapter : RecyclerView.Adapter<MainGridAdapter.MainGridViewHolder>() {
    // Activity에서 사용하기 위한 클릭 리스너
    interface OnItemClickListener{
        fun onItemClick(v: View, data: GridViewData, pos: Int)
    }

    private var list = ArrayList<GridViewData>()

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    inner class MainGridViewHolder(private val mBinding: GridViewItemBinding): RecyclerView.ViewHolder(mBinding.root){
        fun bind(viewData: GridViewData) {
            mBinding.gridViewItem = viewData

              if(adapterPosition != RecyclerView.NO_POSITION){
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, viewData, adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainGridViewHolder {
        val binding =
            GridViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainGridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainGridViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    fun setData(imageList: ArrayList<GridViewData>) {
        list.addAll(imageList)
//        notifyItemRangeInserted(0, list.size-1)
        notifyDataSetChanged()
    }

    fun insertData(imageList: ArrayList<GridViewData>) {
        list.clear()
        list.addAll(imageList)
//        notifyItemRangeChanged(0, list.size-1)
        notifyDataSetChanged()
    }

    fun removeData(gridViewData: GridViewData) {
        val index = list.indexOf(gridViewData)
        println(index)
        list.remove(gridViewData)
        notifyItemRemoved(index)
    }
}