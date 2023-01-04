package com.android.imagerecordapp

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.imagerecordapp.adapter.MainGridAdapter
import com.android.imagerecordapp.data.GridViewData
import com.android.imagerecordapp.data.GridViewDatabase
import com.android.imagerecordapp.databinding.ActivityMainBinding
import java.sql.Date

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel
    private lateinit var mAdapter: MainGridAdapter
    private lateinit var db: GridViewDatabase
    private var imgUrl = ""
    private var date = ""
    private var imageArrayList = arrayListOf<GridViewData>()
    private var page = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // room의 db 초기화
        db = Room.databaseBuilder(
            applicationContext, GridViewDatabase::class.java, "database"
        ).build()

        setClickListener()
        setAdapter()
        observeLiveData()

        mViewModel.getImageListData(db, page)
    }

    private fun observeLiveData() {
        // 이미지 리스트 호출
        mViewModel.imageList.observe(this) {
            println("observe $it")
            imageArrayList = it as ArrayList<GridViewData>
            mAdapter.setData(imageArrayList)


        }

        // 이미지 삽입 후 리스트 호출
        mViewModel.getInsertData.observe(this) {
            imageArrayList = it as ArrayList<GridViewData>
            mAdapter.insertData(imageArrayList)
        }
    }

    private fun setAdapter() {
        mAdapter = MainGridAdapter()
        mBinding.viewGrid.adapter = mAdapter

        // 아이템 롱 클릭으로 삭제
        mAdapter.setOnItemClickListener(object: MainGridAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: GridViewData, pos: Int) {
                imgUrl = data.imgUri
                date = data.date
                v.setOnLongClickListener { false }
                v.setOnCreateContextMenuListener(this@MainActivity)
            }
        })

        mBinding.viewGrid.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val rvPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val totalCount = recyclerView.adapter?.itemCount?.minus(1)

                if(rvPosition == totalCount) {
                    mViewModel.getImageListData(db, ++page)
                }
            }
        })
    }

    private fun setClickListener() {
        // 이미지 추가
        mBinding.imageRecord.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            resultListener.launch(intent)
        }
    }

    /**
     * 갤러리 불러오기
     */
    private val resultListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            // 사진 선택 취소할 경우
            if(it.resultCode == RESULT_CANCELED){
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show()
            }else{
                val uri = it.data!!.data!!

                // 권한 허용으로 에러 처리.
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

                val cursor: Cursor = contentResolver.query(uri,null,null,null,null)!!
                cursor.moveToFirst()
                val path = cursor.getLong(3) // 날짜 정보

                // 데이터 베이스에 사진 정보 저장
                mViewModel.inputImageData(db, Date(path).toString(), uri.toString())
            }
        }

    // 컨텍스트 메뉴 띄우기
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val item = menu!!.add(0,0,0,"삭제")

        // 메뉴의 item 선택 시 해당 이미지 삭제
        item.setOnMenuItemClickListener {
            mViewModel.deleteImageData(db, imgUrl)
            imageArrayList.remove(GridViewData(date , imgUrl))
            mAdapter.removeData(GridViewData(date , imgUrl))
            Toast.makeText(mBinding.root.context, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
            true
        }
    }
}