package com.example.lenovocompetitionapp.model

data class SCNews(
    val `data`: List<SCNewsData>,
    val message: String,
    val status: Int
)