package com.example.lenovocompetitionapp.model

/**
 * @param status 状态
 * @param message 信息描述
 * @param data 数据
 */
data class User(
    val status: Int,
    val message: String,
    val data: UserData
)