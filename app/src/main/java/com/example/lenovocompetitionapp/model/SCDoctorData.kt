package com.example.lenovocompetitionapp.model

import org.litepal.crud.LitePalSupport

data class SCDoctorData(
    val SCAdministrativeId: Int,
    val SCHospitalId: Int,
    val SCOutpatientId: Int,
    val adept: String,
    val id: Int,
    val name: String
): LitePalSupport()