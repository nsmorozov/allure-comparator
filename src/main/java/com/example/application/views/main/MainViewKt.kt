package com.example.application.views.main

import com.example.application.dto.Diff.TestStatus
import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@PageTitle("Сравнение репортов")
@Route(value = "")
class MainView : VerticalLayout() {

    private val urlField1: TextField = TextField("Репорт 1")
    private val urlField2: TextField = TextField("Репорт 2")
    private val downloadBtn: Button = Button("Сравнить")
    private val cleanTableButton: Button = Button("Очистить")

    init {

        val buttonsPanel = HorizontalLayout()

        val gridDiff = Grid(
            TestStatus::class.java, false
        )
        gridDiff.addColumn(TestStatus::name).setHeader("Test name")
        gridDiff.addColumn(TestStatus::status).setHeader("Test diff")

        val gridLeft = Grid(
            TestStatus::class.java, false
        )
        gridLeft.addColumn(TestStatus::name).setHeader("Tests on left")
        gridLeft.addColumn(TestStatus::status).setHeader("Test status")

        val gridRight = Grid(
            TestStatus::class.java, false
        )
        gridRight.addColumn(TestStatus::name).setHeader("Tests on right")
        gridRight.addColumn(TestStatus::status).setHeader("Test status")

        downloadBtn.addClickListener {
//            (new Downloader()).download(urlField1.getValue(), REPORT_1_CSV);
//            (new Downloader()).download(urlField2.getValue(), REPORT_2_CSV);
            val data = com.example.application.controller.Comparator().compare(
                "/Users/n.morozov/IdeaProjects/allure-comparator/src/main/resources/suites1.csv",
                "/Users/n.morozov/IdeaProjects/allure-comparator/src/main/resources/suites2.csv"
            )
            gridDiff.setItems(data.getDifferenceList())
            gridLeft.setItems(data.getLeftEntriesList())
            gridRight.setItems(data.getRightEntriesList())
            add(gridDiff)
            add(gridLeft)
            add(gridRight)
        }
        cleanTableButton.addClickListener {
            remove(
                gridDiff,
                gridLeft,
                gridRight
            )
        }
        buttonsPanel.add(downloadBtn, cleanTableButton)
        isMargin = true
        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, urlField1, urlField2, buttonsPanel)
        add(urlField1, urlField2, buttonsPanel)
    }

    companion object {
        const val REPORT_1_CSV = "report1.csv"
        const val REPORT_2_CSV = "report2.csv"
    }
}
