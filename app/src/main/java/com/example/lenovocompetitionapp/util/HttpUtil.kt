package com.example.httpdemo.util

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object HttpUtil {

    /**
     * (采用OkHttp框架)根据指定的网络地址字符串和请求方式，获取服务器返回的数据
     * @param stringUrl 网络地址字符串
     * @param method 请求方式
     */
    fun sendHttp(stringUrl: String, method: RequestMethod, callback: okhttp3.Callback) {
        //创建网络连接客户端对象
        val client = OkHttpClient()
        val request: Request
        if (method == RequestMethod.GET) {
            //构建Request对象
            request = Request.Builder()
                .url(stringUrl)
                .build()
        } else {
            //构建需要提交给服务器的参数
            val requestBody = FormBody.Builder()
                .add("id", "1")
                .add("lightSwitch", "1")
                .build()
            request = Request.Builder()
                .post(requestBody)
                .url(stringUrl)
                .build()
        }
        client.newCall(request).enqueue(callback)
    }
}