package io.github.anotherjack.aopdemo

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import io.github.anotherjack.testlib.annotation.ToolbarActivity
import kotlinx.android.synthetic.main.activity_main.*

@ToolbarActivity
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, " --> onCreate")

        beforeShowToast.setOnClickListener {
            Toast.makeText(this,"原始的toast",Toast.LENGTH_SHORT).show()
        }

        handleToastText.setOnClickListener {
            val toast = Toast.makeText(this,"origin",Toast.LENGTH_SHORT)
            toast.setText("没处理的toast")
            toast.show()
        }
    }

}
