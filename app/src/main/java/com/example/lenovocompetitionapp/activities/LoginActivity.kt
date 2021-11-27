package com.example.lenovocompetitionapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lenovocompetitionapp.databinding.ActivityLoginBinding
import com.example.lenovocompetitionapp.httpservice.UserService
import com.example.lenovocompetitionapp.model.User
import com.example.lenovocompetitionapp.util.ServiceCreator
import kotlinx.coroutines.*
import retrofit2.await
import retrofit2.create

class LoginActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.buttonLogin.setOnClickListener {
            val userName = mBinding.userName.text.toString().trim()
            val userPwd = mBinding.userPwd.text.toString().trim()
            val userId = mBinding.userId.text.toString().trim()
            when {
                userName == "" -> Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show()
                userPwd == "" ->  Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show()
                userId == "" ->  Toast.makeText(this, "请输入用户ID", Toast.LENGTH_SHORT).show()
                else -> {
                    CoroutineScope(Job()).launch {
                        val user = withContext(Dispatchers.IO) { getUser(userName, userPwd) }
                        if (user.message == "SUCCESS") {
                            ServiceCreator.USER_ID = userId
                            val intent = Intent(applicationContext, IndexActivity::class.java)
                            //需要使用Bundle对象来保存可序列化的实体类数据
                            val bundle = Bundle()
                            bundle.putSerializable("userinfo", user.data)
                            intent.putExtras(bundle)
                            startActivity(intent)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(applicationContext, "登录成功", Toast.LENGTH_SHORT).show()
                                if (!mBinding.cbxAutoPwd.isChecked) {
                                    mBinding.userName.setText("")
                                    mBinding.userPwd.setText("")
                                    mBinding.userId.setText("")
                                }
                            }
                        } else {
                            withContext(Dispatchers.Main) { Toast.makeText(applicationContext, user.message, Toast.LENGTH_SHORT).show() }
                        }
                    }
                }
            }
        }
        mBinding.imageExit.setOnClickListener {
            finish()
        }
    }

    private suspend fun getUser(userName: String, userPwd: String): User {
        return ServiceCreator.retrofit.create<UserService>().getLoginUser(userName, userPwd).await()
    }
}