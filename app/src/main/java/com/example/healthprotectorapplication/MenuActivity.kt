package com.example.healthprotectorapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MenuActivity : AppCompatActivity() {
    lateinit var imageButton4: ImageButton
    lateinit var floatingButton : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        imageButton4 = findViewById(R.id.imageBtn4)
        floatingButton = findViewById(R.id.floatingBtn)

        imageButton4.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        floatingButton.setOnClickListener {
            val intent = Intent(this, ChatbotActivity::class.java)
            startActivity(intent)
        }
    }
}