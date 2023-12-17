package com.example.application.controller

import com.example.application.dto.AllureData
import org.apache.commons.csv.CSVFormat
import java.io.File

class Comparator {

    fun compare(path1: String, path2: String) {
        val suites1 = readCsvFromFile("C:\\Users\\NMorozov\\IdeaProjects\\allure-comparator\\src\\main\\resources\\suites1.csv")
        val suites2 = readCsvFromFile("C:\\Users\\NMorozov\\IdeaProjects\\allure-comparator\\src\\main\\resources\\suites2.csv")
        val s1 = suites1.associate { "${it.classDesc}.${it.methodDesc}" to it.status }
        val s2 = suites2.associate { "${it.classDesc}.${it.methodDesc}" to it.status }
        val dif = s1 - s2
//        var s1add: Map<String, String> = s1
//        if (s1.size > s2.size) {
//            s2.forEach { (k, v) ->
//               s1add.entries
//            }
//        }
//        println()
    }

    private fun readCsvFromFile(path: String): List<AllureData> =
            CSVFormat.Builder.create(CSVFormat.DEFAULT).apply {
                setIgnoreSurroundingSpaces(true)
            }.build().parse(File(path).inputStream().reader())
                    .drop(1) // drop header
                    .map {
                        AllureData( status = it[0],
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