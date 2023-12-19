package com.example.application.controller

import com.example.application.dto.AllureDataEntry
import com.example.application.dto.Diff
import com.google.common.collect.Maps
import org.apache.commons.csv.CSVFormat
import java.io.File

class Comparator {

    fun compare(path1: String, path2: String): Diff {
        val suites1 = readCsvFromFile(path1)
        val suites2 = readCsvFromFile(path2)
        val s1 = suites1.associate { "${it.classDesc}.${it.methodDesc}" to it.status }
        val s2 = suites2.associate { "${it.classDesc}.${it.methodDesc}" to it.status }
        return Diff(Maps.difference(s1, s2))
    }

    private fun readCsvFromFile(path: String): List<AllureDataEntry> =
            CSVFormat.Builder.create(CSVFormat.DEFAULT).apply {
                setIgnoreSurroundingSpaces(true)
            }.build().parse(File(path).inputStream().reader())
                    .drop(1) // пропускаем заголовок
                    .map {
                        AllureDataEntry(status = it[0],
                                        startTime = it[1],
                                        stopTime = it[2],
                                        duration = it[3],
                                        parentSuite = it[4],
                                        suite = it[5],
                                        subSuite = it[6],
                                        classDesc = it[7],
                                        methodDesc = it[8],
                                        name = it[9],
                                        description = it[10]
                        )
                    }
}