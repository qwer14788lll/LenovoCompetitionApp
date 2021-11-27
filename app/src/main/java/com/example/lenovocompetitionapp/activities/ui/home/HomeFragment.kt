package com.example.lenovocompetitionapp.activities.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lenovocompetitionapp.activities.SCHospitalActivity
import com.example.lenovocompetitionapp.databinding.FragmentHomeBinding
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mBinding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, { mBinding.textHome.text = it })
        homeViewModel.imgList.observe(viewLifecycleOwner, { initBanner(it) })
        mBinding.image1.setOnClickListener {
            val intent = Intent(activity, SCHospitalActivity::class.java)
            startActivity(intent)
        }
        return mBinding.root
    }

    private fun initBanner(mData: List<String>) {
        //只是图片轮播————————————————————————
        mBinding.homeBanner.setAdapter(object : BannerImageAdapter<String>(mData) {
            override fun onBindView(holder: BannerImageHolder, data: String, position: Int, size: Int) {
                Glide.with(holder.itemView)
                    .load(data)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                    .into(holder.imageView)
            }
        })?.addBannerLifecycleObserver(this)?.indicator = CircleIndicator(context)
    }
}