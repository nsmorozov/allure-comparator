package com.example.application.views.main

import com.example.application.controller.Downloader
import com.example.application.dto.Diff.TestStatus
import com.vaadin.flow.component.Unit
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

    private val urlField1: TextField = TextField("Ссылка на allure отчет 1")
    private val urlField2: TextField = TextField("Ссылка на allure отчет 2")
    private val downloadBtn: Button = Button("Сравнить")
    private val cleanTableButton: Button = Button("Очистить")

    init {

        val buttonsPanel = HorizontalLayout()
        val urlFieldsPanel = HorizontalLayout()
        val gridPanel = HorizontalLayout()

        gridPanel.setWidth(100F, Unit.PERCENTAGE)

        val gridDiff = Grid(
            TestStatus::class.java, false
        )
        with(gridDiff) {
            addColumn(TestStatus::name).setHeader("Название теста").setAutoWidth(true).setFlexGrow(0)
            addColumn(TestStatus::status).setHeader("Разница")
        }

        val gridLeft = Grid(
            TestStatus::class.java, false
        )
        with(gridLeft) {
            addColumn(TestStatus::name).setHeader("Тесты слева").setAutoWidth(true).setFlexGrow(0)
            addColumn(TestStatus::status).setHeader("Статус теста")
        }

        val gridRight = Grid(
            TestStatus::class.java, false
        )
        with(gridRight) {
            addColumn(TestStatus::name).setHeader("Тесты справа").setAutoWidth(true).setFlexGrow(0)
            addColumn(TestStatus::status).setHeader("Статус теста")
        }

        downloadBtn.addClickListener {

             with(Downloader()) {
                download(urlField1.value.replace(INDEX_HTML, ""), REPORT_1_CSV)
                download(urlField2.value.replace(INDEX_HTML, ""), REPORT_2_CSV)
            }

            val data = com.example.application.controller.Comparator().compare(
                REPORT_1_CSV,
                REPORT_2_CSV
//                "/Users/n.morozov/IdeaProjects/allure-comparator/src/main/resources/suites1.csv",
//                "/Users/n.morozov/IdeaProjects/allure-comparator/src/main/resources/suites2.csv"
            )

            gridDiff.setItems(data.getDifferenceList())
            gridLeft.setItems(data.getLeftEntriesList())
            gridRight.setItems(data.getRightEntriesList())
            gridPanel.add(gridLeft, gridRight)
            add(gridDiff, gridPanel)
        }

        cleanTableButton.addClickListener {
            remove(
                gridPanel,
            )
        }

        buttonsPanel.add(downloadBtn, cleanTableButton)
        urlFieldsPanel.add(urlField1, urlField2)
        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, urlFieldsPanel, buttonsPanel)
        add(urlFieldsPanel, buttonsPanel)
    }

    companion object {
        const val REPORT_1_CSV = "report1.csv"
        const val REPORT_2_CSV = "report2.csv"
        const val INDEX_HTML = "index.html"
    }
}
