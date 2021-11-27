package com.example.lenovocompetitionapp.model

import org.litepal.crud.LitePalSupport

data class SCOutpatientData(
    val SCAdministrativeId: Int = -1,
    val SCHospitalId: Int = -1,
    val content: Any = "error",
    val id: Int = -1,
    val name: String = "error"
): LitePalSupport()