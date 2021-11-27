package com.example.lenovocompetitionapp.util

import android.content.Context
import android.content.Intent

/**
 * 示例代码:startActivity<MainActivity>(this){}
 */
inline fun <reified T> startActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}