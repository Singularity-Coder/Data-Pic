package com.singularitycoder.datapic

data class DataPic(
    val id: Long,
    val image: String,
    val title: String,
    val x: String = "",
    val y: String = "",
)