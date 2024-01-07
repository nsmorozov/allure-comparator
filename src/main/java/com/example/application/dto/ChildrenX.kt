package com.example.application.dto

data class ChildrenX(
    val flaky: Boolean,
    val name: String,
    val parameters: List<Any>,
    val parentUid: String,
    val status: String,
    val time: Time,
    val uid: String
)