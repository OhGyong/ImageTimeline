package com.example.imagerecordapp

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import java.sql.Date

class MainActivity : AppCompatActivity() {

    private lateinit var image: ImageView


    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.S)
    private val resultListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

            val cursor: Cursor = contentResolver.query(it.data!!.data!!,null,null,null,null)!!
            cursor.moveToFirst()
            val path = cursor.getLong(3) // 날짜 정보

            val date = Date((path)) // 날짜 타입으로 변환
            println("날짜 : $date")

            try{
                image.setImageURI(it.data?.data)
            }catch (error: Error){
                println("에러")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.image_gallery)
        val imageBtn = findViewById<ImageButton>(R.id.image_record)

        imageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            resultListener.launch(intent)
        }
    }
}