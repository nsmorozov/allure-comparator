package com.example.application.views.main

import com.example.application.controller.Comparator
import com.example.application.controller.Downloader
import com.example.application.dto.Diff
import com.example.application.dto.Diff.TestStatus
import com.vaadin.flow.component.Unit
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.renderer.LitRenderer
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route


@PageTitle("Сравнение репортов")
@Route(value = "")
class MainView : VerticalLayout() {

    private val urlField1 = TextField("Ссылка на allure отчет 1")
    private val urlField2 = TextField("Ссылка на allure отчет 2")
    private val downloadBtn = Button("Сравнить")
    private val cleanTableButton = Button("Очистить")
    private var data: Diff? = null


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
            addColumn(
                LitRenderer.of<TestStatus>(LIT_TEMPLATE_HTML)
                    .withProperty("id", TestStatus::status)
                    .withFunction("clickHandler") { status: TestStatus ->
                        val s = data?.getDifferenceList()?.let { Comparator().getDiffDetails(it) }
                        Notification.show("Link was clicked for ${status.name} # + ${status.status} diff = $s" )
                    }
            )
                .setHeader("Разница").setSortable(true)
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
                deleteFiles(REPORT_1_CSV, REPORT_2_CSV)
                download(urlField1.value.replace(INDEX_HTML, "/data/suites.csv"), REPORT_1_CSV)
                download(urlField1.value.replace(INDEX_HTML, "/data/suites.json"), SUITE_1_JSON)
                download(urlField2.value.replace(INDEX_HTML, "/data/suites.csv"), REPORT_2_CSV)
                download(urlField2.value.replace(INDEX_HTML, "/data/suites.json"), SUITE_2_JSON)
            }


            if (Downloader().ifFilesDownloaded(REPORT_1_CSV, REPORT_2_CSV)) {
                data = Comparator().compare(
                REPORT_1_CSV,
                REPORT_2_CSV
            )
                //val details = Comparator().getDiffDetails(data.getDifferenceList())
                gridDiff.setItems(data!!.getDifferenceList())
                gridLeft.setItems(data!!.getLeftEntriesList())
                gridRight.setItems(data!!.getRightEntriesList())
                gridPanel.add(gridLeft, gridRight)
                add(gridDiff, gridPanel)
            } else {
                Notification.show("Не могу скачать репорты для сравнения")
            }
        }

        cleanTableButton.addClickListener {
            remove(
                gridPanel, gridDiff
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
        const val SUITE_1_JSON = "suite1.json"
        const val SUITE_2_JSON = "suite2.json"
        const val INDEX_HTML = "index.html"
        private val LIT_TEMPLATE_HTML = """
            <vaadin-button title="Go to ..."
                           @click="${'$'}{clickHandler}"
                           theme="tertiary-inline small link">
                ${'$'}{item.id}
            </vaadin-button>
            """.trimIndent()
    }
}
