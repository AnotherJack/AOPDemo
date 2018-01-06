package io.github.anotherjack.aopdemo

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import io.github.anotherjack.testlib.annotation.ToolbarActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest.permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.github.anotherjack.testlib.annotation.RequestPermissions


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

        takePhoto.setOnClickListener {
            takePhoto()
        }
    }

    //模拟拍照场景
    @RequestPermissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private fun takePhoto(){
        Toast.makeText(this,"咔嚓！拍了一张照片！",Toast.LENGTH_SHORT).show()
    }

}
