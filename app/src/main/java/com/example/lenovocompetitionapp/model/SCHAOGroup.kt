package com.example.lenovocompetitionapp.model

/**
 * @param scHospitalName 医院名称
 * @param scAdministrativeName 科室名称
 * @param scOutpatientName 门诊名称
 * @param scOutpatientId 门诊id（用于预约）
 */
class SCHAOGroup(
    val scHospitalId: Int,
    val scHospitalName: String,
    val scAdministrativeId: Int,
    val scAdministrativeName: String,
    val scOutpatientId: Int,
    val scOutpatientName: String
)