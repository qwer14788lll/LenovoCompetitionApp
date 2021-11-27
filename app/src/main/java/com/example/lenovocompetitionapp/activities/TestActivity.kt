package com.example.lenovocompetitionapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lenovocompetitionapp.databinding.ActivityTestBinding
import com.example.lenovocompetitionapp.httpservice.SCHospitalService
import com.example.lenovocompetitionapp.httpservice.UserService
import com.example.lenovocompetitionapp.model.SCHospital
import com.example.lenovocompetitionapp.model.User
import com.example.lenovocompetitionapp.util.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class TestActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityTestBinding
    private val user = "fengjia"
    private val pwd = "17520439994"
    //private lateinit var mViewModel: MyViewModel

    companion object {
        private const val param1 = "param1"
        private const val param2 = "param1"
        fun actionStart(context: Context, data1: String, data2: String) {
            val intent = Intent(context, TestActivity::class.java)
            intent.putExtra(param1, data1)
            intent.putExtra(param2, data2)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test)
        //mViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        mBinding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.userButton.setOnClickListener {
            val userService = ServiceCreator.retrofit.create<UserService>()
            val stringBuilder = StringBuilder()
            userService.getLoginUser(user, pwd).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    val body = response.body()
                    if (body != null) {
                        val user = body.data
                        stringBuilder.append("用户编号：").append(user.id).append('\n')
                        stringBuilder.append("用户姓名：").append(user.userName).append('\n')
                        stringBuilder.append("登录名：").append(user.loginName).append('\n')
                        stringBuilder.append("学校编号：").append(user.schoolId).append('\n')
                        stringBuilder.append("手机号码：").append(user.phone).append('\n')
                        stringBuilder.append("电子邮箱：").append(user.email).append('\n')
                        stringBuilder.append("在职岗位：").append(user.position).append('\n')
                    }
                    mBinding.testText.text = stringBuilder.toString()
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    t.printStackTrace()
                    stringBuilder.append(t)
                    mBinding.testText.text = stringBuilder.toString()
                }
            })
        }

        mBinding.hospitalButton.setOnClickListener {
            val scHospitalService = ServiceCreator.retrofit.create<SCHospitalService>()
            val stringBuilder = StringBuilder()
            scHospitalService.getSCHospitalInfo(id = 2).enqueue(object : Callback<SCHospital> {
                override fun onResponse(call: Call<SCHospital>, response: Response<SCHospital>) {
                    val body = response.body()
                    if (body != null) {
                        val list = body.data
                        for (item in list) {
                            stringBuilder.append("医院编号：").append(item.id).append('\n')
                            stringBuilder.append("医院名称：").append(item.name).append('\n')
                            stringBuilder.append("医院等级：").append(item.grade).append('\n')
                            stringBuilder.append("所属地区：").append(item.region).append('\n')
                        }
                    }
                    mBinding.testText.text = stringBuilder.toString()
                }

                override fun onFailure(call: Call<SCHospital>, t: Throwable) {
                    t.printStackTrace()
                    stringBuilder.append(t)
                    mBinding.testText.text = stringBuilder.toString()
                }
            })
        }
    }
}