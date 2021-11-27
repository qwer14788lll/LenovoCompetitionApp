package com.example.lenovocompetitionapp.model

import org.litepal.crud.LitePalSupport

data class SCVisitData(
    val SCAdministrativeId: Int,
    val SCDoctorId: Int,
    val SCHospitalId: Int,
    val SCOutpatientId: Int,
    val id: Int,
    val reservationNumber: Int,
    val timeType: Int,
    val treatmentDate: Int
):LitePalSupport()