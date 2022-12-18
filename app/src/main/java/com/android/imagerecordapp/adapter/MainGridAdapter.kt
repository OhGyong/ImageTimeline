package com.android.imagerecordapp.adapter

import android.annotation.SuppressLint
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.imagerecordapp.MainViewModel
import com.android.imagerecordapp.data.GridViewData
import com.android.imagerecordapp.data.GridViewDatabase
import com.android.imagerecordapp.databinding.GridViewItemBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class MainGridAdapter : RecyclerView.Adapter<MainGridAdapter.MainGridViewHolder>() {

    private var list = ArrayList<GridViewData>()

    // Activity에서 사용하기 위한 클릭 리스너
    interface OnItemClickListener{
        fun onItemClick(v: View, data: GridViewData, pos: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    inner class MainGridViewHolder(private val binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(viewData: GridViewData) {
            binding.gridViewItem = viewData

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


    fun insertData(image: GridViewData) {
        list.add(image)
        notifyItemChanged(list.size)
    }

    fun setData(imageList: ArrayList<GridViewData>) {
        list.clear()
        list.addAll(imageList)
//        notifyItemRangeChanged(0, list.size)
        notifyDataSetChanged()
    }


}