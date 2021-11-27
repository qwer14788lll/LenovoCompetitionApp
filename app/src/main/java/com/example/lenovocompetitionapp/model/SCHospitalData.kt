package com.example.lenovocompetitionapp.model

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * @param id 医院编号
 * @param name 医院名称
 * @param grade 医院等级
 * @param region 所属地区
 */
data class SCHospitalData(
    val id: Int = -1,
    val name: String = "error",
    val grade: Int = -1,
    val region: String = "error",
    val scOutpatientDataList: List<SCAdministrativeData> = ArrayList()
): LitePalSupport()