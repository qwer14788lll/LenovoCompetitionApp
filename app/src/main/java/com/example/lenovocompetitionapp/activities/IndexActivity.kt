package com.example.lenovocompetitionapp.activities

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.lenovocompetitionapp.R
import com.example.lenovocompetitionapp.databinding.ActivityIndexBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class IndexActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIndexBinding
    private var exitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIndexBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_index)
        navView.setupWithNavController(navController)
    }

    /**
     * 拦截系统返回键
     */
    override fun onBackPressed() {
        exit()
    }

    private fun exit() {
        val time: Long = 2000
        if (System.currentTimeMillis() - exitTime > time) {
            //存储此次点击返回键的时间
            exitTime = System.currentTimeMillis()
            Toast.makeText(applicationContext, "快速点击两次，退出当前账号", Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }
}