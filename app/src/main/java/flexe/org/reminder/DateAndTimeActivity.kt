package flexe.org.reminder

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.date_and_time_setting.*
import kotlinx.android.synthetic.main.new_note.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.hours
import kotlin.time.minutes

class DateTimeActivity : AppCompatActivity() ,View.OnClickListener{
    private var currentDate:String=""
    private var currentTime:String=""

    private val RESULT_OK:Int=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_and_time_setting)


        val  date=findViewById<CalendarView>(R.id.calendarViewDate)
            val time=findViewById<TimePicker>(R.id.timePickerTime)
         time.setIs24HourView(true) //set in format 24

            val sdf=SimpleDateFormat("dd.MM.yyyy HH:mm")
            val formatter=sdf.format(date.date)
            this.currentDate=formatter



        date.setOnDateChangeListener { calendarView, year, month, day ->

            this.currentDate="$day" +"." +"${month+1}"  +"."+"$year"


            //this.currentDate="Test Date"
            Log.e("Set Date",this.currentDate)
        }

        time.setOnTimeChangedListener { timePicker, hour, minute ->
            if (hour<=9){
                this.currentTime="0${hour}"+":"+"${minute}"// write hour in as 01 or 05...
            } else  this.currentTime="${hour}"+":"+"${minute}"

            Log.e("hour24",this.currentTime)
        }

    }

    override  fun onClick(view: View) {
        val intent=Intent(this@DateTimeActivity, MainActivity::class.java)
        intent.putExtra("MyFinalDateTime","Meeting: ${this.currentDate}"+" at "+"${this.currentTime}")
        intent.putExtra("onlyDate",this.currentDate)
        intent.putExtra("onlyTime",this.currentTime)
        setResult(RESULT_OK,intent)
        finish()

    }

    override fun onPause() {
        super.onPause()
        //Log.e("DateAndTimeActivity","onPause()")
    }

    override fun onStop() {
        super.onStop()
        //Log.e("DateAndTimeActivity","onStop()")

    }

    override fun onDestroy() {
        super.onDestroy()
        //Log.e("DateAndTimeActivity","onDestroy()")

    }

    override fun onResume() {
        super.onResume()
        //Log.e("DateAndTimeActivity","onResume()")

    }






    }
