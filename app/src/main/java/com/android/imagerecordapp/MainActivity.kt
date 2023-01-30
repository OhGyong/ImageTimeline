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
import com.android.imagerecordapp.adapter.MainImageAdapter
import com.android.imagerecordapp.data.ImageViewData
import com.android.imagerecordapp.data.ImageViewDatabase
import com.android.imagerecordapp.databinding.ActivityMainBinding
import java.sql.Date

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel
    private lateinit var mAdapter: MainImageAdapter
    private lateinit var db: ImageViewDatabase
    private var imgUrl = ""
    private var date = ""
    private var imageArrayList = arrayListOf<ImageViewData>()
    private var page = 1
    private var dbListSize = 0
    private var isInsert = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // room의 db 초기화
        db = Room.databaseBuilder(
            applicationContext, ImageViewDatabase::class.java, "database"
        ).build()

        setClickListener()
        setAdapter()
        observeLiveData()

        mViewModel.getListSizeData(db)
    }

    private fun observeLiveData() {
        /**
         * 리스트 사이즈 observe
         */
        mViewModel.listSize.observe(this) {
            println("이미지 사이즈 호출 결과 $it")

            if(it == 0) {
                mBinding.tvListEmpty.visibility = View.VISIBLE
                return@observe
            }
            dbListSize = it-1
            mViewModel.getImageListData(db, page)
        }

        /**
         * 이미지 리스트 observe
         */
        mViewModel.imageList.observe(this) {
            println("이미지 호출 결과 $it")

            if(it.isNullOrEmpty()) {
                mBinding.tvListEmpty.visibility = View.VISIBLE
                return@observe
            }

            mBinding.tvListEmpty.visibility = View.GONE
            imageArrayList = it as ArrayList<ImageViewData>

            if(isInsert) {
                mAdapter.insertData(imageArrayList)
                isInsert = false
                return@observe
            }

            if(page == 1){
                mAdapter.setData(imageArrayList)
            }else {
                mAdapter.setPaginationList(it)
            }
        }

        /**
         * 이미지 삽입 observe
         */
        mViewModel.insertObserve.observe(this) {
            println("이미지 삽입 호출 결과")

            page = 1
            isInsert = true
            mViewModel.getListSizeData(db)
        }

        /**
         * 이미지 삭제 observe
         */
        mViewModel.deleteObserve.observe(this) {
            println("이미지 삭제 호출 결과")

            imageArrayList.remove(ImageViewData(date , imgUrl))
            mAdapter.removeData(ImageViewData(date , imgUrl))
            dbListSize--
            Toast.makeText(mBinding.root.context, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()

            if(imageArrayList.isEmpty()) {
                mBinding.tvListEmpty.visibility = View.VISIBLE
            }
        }
    }

    private fun setAdapter() {
        mAdapter = MainImageAdapter()
        mBinding.rvMain.adapter = mAdapter

        /**
         * 이미지 롱 클릭 시 컨텍스트 메뉴 띄우기
         */
        mAdapter.setOnItemLongClickListener(object: MainImageAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: ImageViewData, pos: Int) {
                imgUrl = data.imgUri
                date = data.date
                v.setOnCreateContextMenuListener(this@MainActivity)
            }
        })

        /**
         * 페이징 처리
         */
        mBinding.rvMain.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val rvPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val totalCount = recyclerView.adapter?.itemCount?.minus(1)

                if(rvPosition == totalCount && dbListSize !=totalCount && imageArrayList.isNotEmpty()) {
                    mViewModel.getImageListData(db, ++page)
                }
            }
        })
    }

    private fun setClickListener() {
        // 이미지 추가 버튼
        mBinding.ibAdd.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            imageAddResultListener.launch(intent)
        }
    }

    /**
     * 갤러리 불러오기
     */
    private val imageAddResultListener =
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

    /**
     * 컨텍스트 메뉴로 이미지 삭제
     */
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val item = menu!!.add(0,0,0,"삭제")

        // 메뉴의 item 선택 시 해당 이미지 삭제
        item.setOnMenuItemClickListener {
            mViewModel.deleteImageData(db, imgUrl)
            true
        }
    }
}