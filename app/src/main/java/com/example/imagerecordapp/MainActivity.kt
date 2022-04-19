package com.example.imagerecordapp

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.imagerecordapp.data.GridViewData
import com.example.imagerecordapp.data.GridViewDatabase
import com.example.imagerecordapp.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Date

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: GridViewDatabase
    private lateinit var viewModel: MainViewModel

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.S)
    private val resultListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            try{
                val uri = it.data!!.data!!

                // 권한 허용으로 에러 처리.
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

                val cursor: Cursor = contentResolver.query(uri,null,null,null,null)!!
                cursor.moveToFirst()
                val path = cursor.getLong(3) // 날짜 정보

                val date = Date(path) // 날짜 타입으로 변환
                println("날짜 : $date")

                GlobalScope.launch(Dispatchers.IO){
                    db.gridViewDao().insertData(GridViewData(date.toString(), uri.toString())) // 데이터 삽입
                }
            }catch (error: Error){
                println("에러")
            }
        }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 바인딩 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뷰모델 초기화
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // room의 db 초기화
        db = Room.databaseBuilder(
            applicationContext, GridViewDatabase::class.java, "database"
        ).build()

        viewModel.imageList.observe(this, {
            binding.viewGrid.adapter = MainGridAdapter(it)
        })

        binding.imageRecord.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            resultListener.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getImageListData(db)
    }
}