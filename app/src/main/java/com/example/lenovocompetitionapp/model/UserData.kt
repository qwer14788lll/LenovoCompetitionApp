package com.example.lenovocompetitionapp.model

import java.io.Serializable

/**
 * @param id 用户编号
 * @param userName 用户姓名
 * @param loginName 登录名
 * @param schoolId 学校编号
 * @param phone 手机号码
 * @param email 电子邮箱
 * @param position 在职岗位
 */
data class UserData(
    val id: Int,
    val userName: String,
    val loginName: String,
    val schoolId: Int,
    val phone: String,
    val email: String,
    val position: String
): Serializable