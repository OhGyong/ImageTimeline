package com.example.imagerecordapp

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.imagerecordapp.data.GridViewData
import com.example.imagerecordapp.databinding.GridViewItemBinding

class MainGridAdapter(private val list: List<GridViewData>) :
    RecyclerView.Adapter<MainGridAdapter.MainGridViewHolder>() {

    inner class MainGridViewHolder(private val binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        // 컨텍스트 메뉴 리스너를 init 으로 바로 초기화(MainGridViewHolder를 View로써 인자로 사용)
        init{
            binding.root.setOnCreateContextMenuListener(this)
        }

        fun bind(viewData: GridViewData) {
            binding.gridViewItem = viewData

            // 롱터치로 컨텍스트 메뉴 띄우기
            binding.root.setOnLongClickListener {
                false
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val item = menu!!.add(0,0,0,"삭제")
            item.setOnMenuItemClickListener {
                Toast.makeText(binding.root.context, binding.gridViewItem.toString(), Toast.LENGTH_SHORT).show()
                true
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
}