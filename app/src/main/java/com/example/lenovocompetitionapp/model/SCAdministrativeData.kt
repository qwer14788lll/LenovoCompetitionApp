package com.example.lenovocompetitionapp.model

import org.litepal.crud.LitePalSupport

data class SCAdministrativeData(
    val SCHospitalId: Int = -1,
    val id: Int = -1,
    val name: String = "error",
    val scOutpatientDataList: List<SCOutpatientData> = ArrayList()
) : LitePalSupport()