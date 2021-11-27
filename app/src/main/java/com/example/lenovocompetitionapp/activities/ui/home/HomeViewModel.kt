package com.example.lenovocompetitionapp.activities.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lenovocompetitionapp.httpservice.SCNewsService
import com.example.lenovocompetitionapp.model.SCNews
import com.example.lenovocompetitionapp.util.ServiceCreator
import com.example.lenovocompetitionapp.util.StringHelper
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "工作之余,时间不多，5大模块中，仅医疗模块完成"
    }
    val text = _text

    private val _homeData = MutableLiveData<SCNews>().apply {
        initHomeData()
    }
    val homeData = _homeData

    private val _imgList = MutableLiveData<ArrayList<String>>()
    val imgList = _imgList

    private fun initHomeData() {
        val newsService = ServiceCreator.retrofit.create<SCNewsService>()
        newsService.getAll().enqueue(object : Callback<SCNews> {
            override fun onResponse(call: Call<SCNews>, response: Response<SCNews>) {
                val body = response.body()
                if (body != null) {
                    _homeData.value = body
                    _imgList.value = initImgList(body)
                }
            }

            override fun onFailure(call: Call<SCNews>, t: Throwable) {
                t.printStackTrace()
                Log.i("错误信息", t.toString())
            }
        })
    }

    private fun initImgList(data: SCNews): ArrayList<String> {
        val tempList1 = ArrayList<String>()
        for (item in data.data) {
            tempList1.addAll(StringHelper.toImage(item.content))
        }
        val tempList2 = ArrayList<String>()
        repeat(5) {
            tempList2.add(tempList1[(0 until tempList1.size).random()])
        }
        return tempList2
    }
}