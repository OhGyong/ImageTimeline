package com.example.imagerecordapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagerecordapp.data.GridViewData
import com.example.imagerecordapp.data.ViewData
import com.example.imagerecordapp.databinding.GridViewItemBinding

class MainGridAdapter(private val list: List<ViewData>) :
    RecyclerView.Adapter<MainGridAdapter.MainGridViewHolder>(){

    inner class MainGridViewHolder(private val binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(viewData: ViewData){
                binding.gridViewItem = viewData
                binding.imageGallery.setImageURI(viewData.imgUri)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainGridViewHolder {
        val binding = GridViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainGridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainGridViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}