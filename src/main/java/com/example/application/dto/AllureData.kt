package com.example.application.dto

data class AllureData (
        val status: String,
        val startTime: String,
        val stopTime: String,
        val duration: String,
        val parentSuite: String,
        val suite: String,
        val subSuite: String,
        val classDesc: String,
        val methodDesc: String,
        val name: String,
        val description: String
)

