package com.example.healthprotectorapplication

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class ReportActivity : AppCompatActivity() {
    companion object {
        val memos = mutableMapOf<String, String>()
    }

    private var selectedDate: String? = null
    private var editingDate: String? = null // 수정 중인 메모의 날짜

    lateinit var memoTextView : TextView
    lateinit var memoContentTextView : TextView
    lateinit var memoEditText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        memoTextView = findViewById(R.id.memoTextView)
        memoContentTextView = findViewById(R.id.memoContentTextView)
        memoEditText = findViewById(R.id.memoEditText)

        // Intent에서 날짜 정보를 받아옴
        selectedDate = intent.getStringExtra("selectedDate")

        Log.d("ReportActivity", "Selected Date: $selectedDate")
        Log.d("ReportActivity", "Current Memos: $memos")

        val memoContent = memos[selectedDate]

        if (memoContent.isNullOrEmpty()) {
            memoContentTextView.text = "건강 레포트가 없습니다."
            memoEditText.setText("") // 수정 중인 메모 내용 초기화
            editingDate = null // 수정 중인 메모 날짜 초기화
        } else {
            memoContentTextView.text = memoContent
            memoEditText.setText(memoContent) // 수정 중인 메모 내용 설정
            editingDate = selectedDate // 수정 중인 메모의 날짜 설정
        }
        memoEditText.visibility = View.GONE // EditText 초기에는 숨김

        memoContentTextView.setOnClickListener {
            memoContentTextView.visibility = View.GONE
            memoEditText.visibility = View.VISIBLE
            memoEditText.requestFocus()
        }

        memoEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                val content = memoEditText.text.toString()
                memos[selectedDate!!] = content
                Log.d("ReportActivity", "Memo Saved: $content")
                memoContentTextView.text = content
                memoEditText.visibility = View.GONE
                memoContentTextView.visibility = View.VISIBLE
                true
            } else {
                false
            }
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.navigation_icon) // 뒤로가기 버튼 아이콘 설정
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}