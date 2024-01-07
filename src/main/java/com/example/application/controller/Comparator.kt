package com.example.application.controller

import com.example.application.dto.AllureDataEntry
import com.example.application.dto.AllureTestSuite
import com.example.application.dto.Diff
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.common.collect.Maps
import org.apache.commons.csv.CSVFormat
import java.io.File

class Comparator {

    fun compare(path1: String, path2: String): Diff {
        readAllureTestSuite()
        val suites1 = readCsvFromFile(path1)
        val suites2 = readCsvFromFile(path2)
        val s1 = suites1.associate { it.name to it.status }
        val s2 = suites2.associate { it.name to it.status }
        return Diff(Maps.difference(s1, s2))
    }

    private fun readCsvFromFile(path: String): List<AllureDataEntry> =
            CSVFormat.Builder.create(CSVFormat.DEFAULT).apply {
                setIgnoreSurroundingSpaces(true)
            }.build().parse(File(path).inputStream().reader())
                    .drop(1) // пропускаем заголовок
                    .map {
                        AllureDataEntry(status = it[0],
                                        name = it[1],
                                        duration = it[2],
                                        description = it[3]
                        )
                    }

    private fun readAllureTestSuite() {
        val mapper = jacksonObjectMapper()
        val jsonString: String = File("/Users/n.morozov/IdeaProjects/allure-comparator/src/main/resources/test.json").readText(Charsets.UTF_8)
        val jsonTextList = mapper.readValue<AllureTestSuite>(jsonString)
        println()
    }
}