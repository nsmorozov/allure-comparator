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
        //readAllureTestSuite()
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

    fun getDiffDetails(s1: List<Diff.TestStatus>, allureSuiteFile1: String, allureSuiteFile2: String): MutableList<Diff.DiffInfo> {
        val diffList: MutableList<Diff.DiffInfo> =  mutableListOf()
        val mapper = jacksonObjectMapper()
        val left = mapper.readValue<AllureTestSuite>(File(allureSuiteFile1).readText(Charsets.UTF_8))
        val right = mapper.readValue<AllureTestSuite>(File(allureSuiteFile2).readText(Charsets.UTF_8))
        s1.forEach { diff ->
            val l = left.children.first { diff.name.contains(it.name) }
            val r = right.children.first { diff.name.contains(it.name) }
            //Запоминаем название теста и id сьюта, которому принадлежит тест
            diffList.add(Diff.DiffInfo(diff.name, l.children[0].parentUid, r.children[0].parentUid))
        }
        return diffList
    }
}