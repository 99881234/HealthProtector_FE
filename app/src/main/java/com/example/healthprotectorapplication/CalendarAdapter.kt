package layout

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.healthprotectorapplication.CalendarActivity
import com.example.healthprotectorapplication.CalendarUtil
import com.example.healthprotectorapplication.OnItemListener
import com.example.healthprotectorapplication.R
import com.example.healthprotectorapplication.ReportActivity
import java.time.LocalDate

class CalendarAdapter(private val dayList: ArrayList<LocalDate?>) :
    RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayText: TextView = itemView.findViewById(R.id.dayText)
    }

    // 화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_item, parent, false)

        return ItemViewHolder(view)
    }

    // 데이터 설정
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val day = dayList[holder.adapterPosition] // 날짜 변수에 담기

        if (day == null) {
            holder.dayText.text = ""
            holder.itemView.setBackgroundColor(Color.WHITE) // 날짜가 없을 때 배경 색상
        } else {
            // 해당 일자를 넣는다
            holder.dayText.text = day.dayOfMonth.toString()

            // 배경 색상 초기화
            holder.itemView.setBackgroundColor(Color.WHITE)

            // 현재 날짜 색상 칠하기
            if (day == CalendarUtil.selectedDate) {
                holder.itemView.setBackgroundColor(Color.LTGRAY)
            } else {
                // 해당 날짜에 메모가 있는지 확인
                val formattedDate = day.toString() // LocalDate 객체를 문자열로 변환
                if (ReportActivity.memos.containsKey(formattedDate)) {
                    holder.itemView.setBackgroundColor(Color.YELLOW) // 메모가 있는 날짜의 배경 색상
                }
            }
        }

        // 텍스트 색상 지정 (토, 일)
        if ((position + 1) % 7 == 0) { // 토요일
            holder.dayText.setTextColor(Color.BLUE)
        } else if (position == 0 || position % 7 == 0) { // 일요일
            holder.dayText.setTextColor(Color.RED)
        }

        // 날짜 클릭 이벤트
        holder.itemView.setOnClickListener {
            val iYear = day?.year
            val iMonth = day?.monthValue
            val iDay = day?.dayOfMonth

            val yearMonDay = "$iYear 년 $iMonth 월 $iDay 일"

            Toast.makeText(holder.itemView.context, yearMonDay, Toast.LENGTH_SHORT).show()

            val intent = Intent(holder.itemView.context, ReportActivity::class.java)
            intent.putExtra("selectedDate", day.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}