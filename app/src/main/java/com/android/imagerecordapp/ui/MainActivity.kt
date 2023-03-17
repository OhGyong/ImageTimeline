package com.android.imagerecordapp.ui

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.android.imagerecordapp.adapter.MainImageAdapter
import com.android.imagerecordapp.data.ImageViewData
import com.android.imagerecordapp.data.ImageViewDatabase
import com.android.imagerecordapp.databinding.ActivityMainBinding
import com.android.imagerecordapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.sql.Date
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel: MainViewModel by viewModels()
    @Inject lateinit var mAdapter: MainImageAdapter
    private var imgUrl = ""
    private var date = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        ImageViewDatabase.getDbInstance(this)

        setClickListener()
        setAdapter()
        observeLiveData()
    }

    private fun observeLiveData() {
        /**
         * 이미지 삽입 observe
         */
        mViewModel.insertObserve.observe(this) {
            println("이미지 삽입 호출 결과")
            mAdapter.refresh()
        }

        /**
         * 이미지 삭제 observe
         */
        mViewModel.deleteObserve.observe(this) {
            mAdapter.refresh()
        }
    }

    private fun setAdapter() {
        mBinding.rvMain.adapter = mAdapter

        /**
         * 이미지 리스트 flow
         */
        lifecycleScope.launch {
            mViewModel.getImageListData().collectLatest {
                mAdapter.submitData(lifecycle, it)
            }
        }

        /**
         * 리스트 없을 시 Empty View 처리
         */
        mAdapter.addLoadStateListener { combinedLoadStates ->
            if(combinedLoadStates.append.endOfPaginationReached) {
                if(mAdapter.itemCount < 1) {
                    mBinding.tvListEmpty.visibility = View.VISIBLE
                }else {
                    mBinding.tvListEmpty.visibility = View.GONE
                }
            }
        }

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
                mViewModel.inputImageData(ImageViewDatabase.imageViewDB!!, Date(path).toString(), uri.toString())
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
            mViewModel.deleteImageData(ImageViewDatabase.imageViewDB!!, imgUrl)
            true
        }
    }
}