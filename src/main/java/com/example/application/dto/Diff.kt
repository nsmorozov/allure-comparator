package com.example.application.dto

class Diff(val diff: Map<Any, String>, val r1: List<AllureDataEntry>, val r2: List<AllureDataEntry>) {

    fun asList(): List<TestStatus> = diff.map { TestStatus(it.key.toString(), it.value)  }.toList()
    class TestStatus(val name: String, val status: String)
}