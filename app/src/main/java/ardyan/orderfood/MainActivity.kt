package ardyan.orderfood

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.method.TextKeyListener.clear
import android.widget.ArrayAdapter
import java.util.Arrays.asList
import android.R.attr.button
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList
import java.util.Arrays.asList




class MainActivity : AppCompatActivity() {


    lateinit var textView: TextView

    lateinit var start: Button
    lateinit var pause:Button
    lateinit var reset:Button
    lateinit var lap:Button

    var MillisecondTime: Long = 0
    var StartTime:Long = 0
    var TimeBuff:Long = 0
    var UpdateTime = 0

    lateinit var handler: Handler

    var Seconds: Int = 0
    var Minutes:Int = 0
    var MilliSeconds:Int = 0

    lateinit var listView: ListView

    var ListElements = arrayOf<String>()
    lateinit var ListElementsArrayList: List<String>

    lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView) as TextView
        start = findViewById(R.id.button) as Button
        pause = findViewById(R.id.button2) as Button
        reset = findViewById(R.id.button3) as Button
        lap = findViewById(R.id.button4) as Button
        listView = findViewById(R.id.listview1) as ListView

        handler = Handler()

        ListElementsArrayList = ArrayList<String>(asList(ListElements))
        adapter = ArrayAdapter(this@MainActivity,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        )

        listView.setAdapter(adapter)

        start.setOnClickListener(object : View.OnClickListener() {
            override fun onClick(view: View) {

                StartTime = SystemClock.uptimeMillis()
                handler.postDelayed(runnable, 0)

                reset.setEnabled(false)

            }
        })

        pause.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                TimeBuff += MillisecondTime

                handler.removeCallbacks(runnable)

                reset.setEnabled(true)

            }
        })

        reset.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                MillisecondTime = 0L
                StartTime = 0L
                TimeBuff = 0L
                UpdateTime = 0
                Seconds = 0
                Minutes = 0
                MilliSeconds = 0

                textView.text = "00:00:00"

                ListElementsArrayList.clear()

                adapter.notifyDataSetChanged()
            }
        })

        lap.setOnClickListener(object : View.OnClickListener() {
            override fun onClick(view: View) {

                ListElementsArrayList.add(textView.text.toString())

                adapter.notifyDataSetChanged()

            }
        })

    }

    var runnable: Runnable = object : Runnable {

        override fun run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime

            UpdateTime = (TimeBuff + MillisecondTime).toInt()

            Seconds = (UpdateTime / 1000).toInt()

            Minutes = Seconds / 60

            Seconds = Seconds % 60

            MilliSeconds = (UpdateTime % 1000).toInt()

            textView.text = ("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds))

            handler.postDelayed(this, 0)
        }

    }
}

