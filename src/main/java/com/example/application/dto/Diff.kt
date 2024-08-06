package com.example.application.dto

import com.google.common.collect.MapDifference

class Diff(
    private val diff: MapDifference<String, String>,
) {
    fun getDifferenceList(): List<TestStatus> = diff.entriesDiffering().map { TestStatus(it.key, it.value.toString()) }.toList()

    fun getLeftEntriesList(): List<TestStatus> = diff.entriesOnlyOnLeft().map { TestStatus(it.key, it.value.toString()) }.toList()

    fun getRightEntriesList(): List<TestStatus> = diff.entriesOnlyOnRight().map { TestStatus(it.key, it.value.toString()) }.toList()

    class TestStatus(
        val name: String,
        val status: String,
    )

    class DiffInfo(
        val name: String,
        val infoLeft: String,
        val infoRight: String,
    )
}
