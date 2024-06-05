package com.example.healthprotectorapplication

import android.os.Build
import android.os.Bundle
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthprotectorapplication.R
import com.example.healthprotectorapplication.databinding.ActivityCalendarBinding
import layout.CalendarAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // binding 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.navigation_icon) // 뒤로가기 버튼 아이콘 설정
        }

        CalendarUtil.selectedDate = LocalDate.now() // 현재 날짜

        setMonthView() // 화면 설정

        // 이전 달 버튼 이벤트
        binding.preBtn.setOnClickListener {
            CalendarUtil.selectedDate = CalendarUtil.selectedDate.minusMonths(1)
            setMonthView()
        }

        // 다음 달 버튼 이벤트
        binding.nextBtn.setOnClickListener {
            CalendarUtil.selectedDate = CalendarUtil.selectedDate.plusMonths(1)
            setMonthView()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView() { // 날짜 화면에 보여주기
        binding.monthYearText.text = monthYearFromDate(CalendarUtil.selectedDate)

        // 날짜 생성해서 리스트에 담기
        val dayList = dayInMonthArray(CalendarUtil.selectedDate)

        // 어댑터 초기화
        val adapter = CalendarAdapter(dayList)

        // 레이아웃 설정 (열 7개)
        var manager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)

        // 레이아웃 적용
        binding.recyclerView.layoutManager = manager

        // 어댑터 적용
        binding.recyclerView.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearFromDate(date: LocalDate): String { // 날짜 타입 설정 (월, 년)
        var formatter = DateTimeFormatter.ofPattern("MM월 yyyy")

        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun yearMonthFromDate(date: LocalDate): String { // 날짜 타입 설정 (월, 년)
        var formatter = DateTimeFormatter.ofPattern("yyyy년 MM월")

        return date.format(formatter)
    }

    // 날짜 생성
    @RequiresApi(Build.VERSION_CODES.O)
    private fun dayInMonthArray(date: LocalDate): ArrayList<LocalDate?> {
        var dayList = ArrayList<LocalDate?>()
        var yearMonth = YearMonth.from(date)

        // 해당 월 마지막 날짜 가져오기 ex) 28, 30, 31
        var lastDay = yearMonth.lengthOfMonth()

        // 해당 월의 첫 번째 날 가져오기 ex) 6월 1일
        var firstDay = CalendarUtil.selectedDate.withDayOfMonth(1)

        // 첫번째 날의 요일 가져오기 ex) 월요일: 1, 일요일: 7
        var dayOfWeek = firstDay.dayOfWeek.value

        for (i in 1..41) {
            if (i <= dayOfWeek || i > (lastDay + dayOfWeek)) {
                dayList.add(null)
            } else {
                // LocalDate.of(년, 월, 일)
                dayList.add(LocalDate.of(CalendarUtil.selectedDate.year,
                    CalendarUtil.selectedDate.monthValue, i - dayOfWeek))
            }
        }
        return dayList
    }
}