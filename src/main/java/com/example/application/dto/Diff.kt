package com.example.application.dto

class Diff(private val diff: Map<String, String>, val r1: List<AllureDataEntry>, val r2: List<AllureDataEntry>) {

    fun asList(): List<TestStatus> = diff.map { TestStatus(it.key, it.value)  }.toList()
    class TestStatus(val name: String, val status: String)
}