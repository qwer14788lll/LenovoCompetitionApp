package com.example.lenovocompetitionapp.model

/**
 * @param status 状态 '200'代表正确,'400'代表错误，'401'代表权限不足
 * @param message 信息描述
 * @param data 数据
 */
data class SCHospital(
    val data: List<SCHospitalData>,
    val message: String,
    val status: Int
)