package com.example.lenovocompetitionapp.model

data class SCOutpatient(
    val `data`: List<SCOutpatientData>,
    val message: String,
    val status: Int
)