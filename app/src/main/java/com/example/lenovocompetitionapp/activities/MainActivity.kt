package com.example.lenovocompetitionapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.httpdemo.util.HttpUtil
import com.example.httpdemo.util.RequestMethod
import com.example.lenovocompetitionapp.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnStart.setOnClickListener {
            val url = "http://192.168.43.203:8085/dataInterface/UserWorkEnvironmental/updateLightSwitch"
            val okHttpClient = OkHttpClient()
            val requestBody = FormBody.Builder()
                .add("id", "1")
                .add("lightSwitch", "1")
                .build()
            val request = Request.Builder()
                .post(requestBody)
                .url(url)
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    //val body = response.body()?.string()
                    runOnUiThread {
                        mBinding.textView.text = "灯光已开启！"
                    }
                }
            })
        }

        mBinding.btnStop.setOnClickListener {
            val url = "http://192.168.43.203:8085/dataInterface/UserWorkEnvironmental/updateLightSwitch"
            val okHttpClient = OkHttpClient()
            val requestBody = FormBody.Builder()
                .add("id", "1")
                .add("lightSwitch", "0")
                .build()
            val request = Request.Builder()
                .post(requestBody)
                .url(url)
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    //val body = response.body()?.string()
                    runOnUiThread {
                        mBinding.textView.text = "灯光已关闭！"
                    }
                }
            })
        }
    }
}