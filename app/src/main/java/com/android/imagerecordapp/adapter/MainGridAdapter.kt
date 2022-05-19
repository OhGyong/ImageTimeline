package com.android.imagerecordapp.adapter

import android.annotation.SuppressLint
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.imagerecordapp.data.GridViewData
import com.android.imagerecordapp.data.GridViewDatabase
import com.android.imagerecordapp.databinding.GridViewItemBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class MainGridAdapter(private var list: List<GridViewData>) :
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

        // 컨텍스트 메뉴 띄우기
        @SuppressLint("NotifyDataSetChanged")
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val item = menu!!.add(0,0,0,"삭제")

            // 메뉴의 item 선택 시 해당 이미지 삭제
            item.setOnMenuItemClickListener {
                Toast.makeText(binding.root.context, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()

                val db = Room.databaseBuilder(
                    binding.root.context, GridViewDatabase::class.java, "database"
                ).build()

                GlobalScope.launch {
                    db.gridViewDao().deleteData(binding.gridViewItem!!.imgUri) // 데이터 삭제
                }
                notifyDataSetChanged() // 리사이클러 뷰 업데이트
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