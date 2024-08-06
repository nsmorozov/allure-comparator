package com.example.application.views.main

import com.example.application.controller.Comparator
import com.example.application.controller.Downloader
import com.example.application.dto.Diff
import com.example.application.dto.Diff.TestStatus
import com.vaadin.flow.component.Unit
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.html.Image
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
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

        val leftContainer = VerticalLayout()
        val rightContainer = VerticalLayout()
        val controlsContainer = HorizontalLayout()
        val gridPanel = HorizontalLayout()

        gridPanel.setWidth(100F, Unit.PERCENTAGE)

        val gridDiff = createDifferenceGrid()
        val gridLeft = createLeftGrid()
        val gridRight = createRightGrid()
        val header = createHeader()

        downloadBtn.addClickListener {
            downloadAllureCsv()
            populateGrids(gridDiff, gridLeft, gridRight, gridPanel)
        }

        cleanTableButton.addClickListener {
            remove(gridPanel, gridDiff)
        }
        leftContainer.alignItems = Alignment.END
        leftContainer.add(urlField1, downloadBtn)
        rightContainer.add(urlField2, cleanTableButton)
        controlsContainer.add(leftContainer, rightContainer)
        setHorizontalComponentAlignment(Alignment.CENTER, controlsContainer, header)
        add(header, controlsContainer)
    }

    private fun createHeader(): HorizontalLayout {
        val header = HorizontalLayout()
        val logo = Image("/images/image.svg", "Logo")
        logo.className = "logo"
        with(header) {
            setWidthFull()
            className = "header"
            isSpacing = false
            add(logo)
        }
        return header
    }

    private fun populateGrids(
        gridDiff: Grid<TestStatus>,
        gridLeft: Grid<TestStatus>,
        gridRight: Grid<TestStatus>,
        gridPanel: HorizontalLayout,
    ) {
        if (Downloader().ifFilesDownloaded(REPORT_1_CSV, REPORT_2_CSV)) {
            data =
                Comparator().compare(
                    REPORT_1_CSV,
                    REPORT_2_CSV,
                )
            // val details = Comparator().getDiffDetails(data.getDifferenceList())
            gridDiff.setItems(data!!.getDifferenceList())
            gridLeft.setItems(data!!.getLeftEntriesList())
            gridRight.setItems(data!!.getRightEntriesList())
            gridPanel.add(gridLeft, gridRight)
            add(gridDiff, gridPanel)
        } else {
            Notification.show("Не могу скачать репорты для сравнения")
        }
    }

    private fun downloadAllureCsv() {
        with(Downloader()) {
            deleteFiles(REPORT_1_CSV, REPORT_2_CSV)
            val v1 = urlField1.value.refineUrl()
            val v2 = urlField2.value.refineUrl()
            download(v1.replace(INDEX_HTML, "/data/suites.csv"), REPORT_1_CSV)
            download(v1.replace(INDEX_HTML, "/data/suites.json"), SUITE_1_JSON)
            download(v2.replace(INDEX_HTML, "/data/suites.csv"), REPORT_2_CSV)
            download(v2.replace(INDEX_HTML, "/data/suites.json"), SUITE_2_JSON)
        }
    }

    private fun createRightGrid(): Grid<TestStatus> {
        val gridRight =
            Grid(
                TestStatus::class.java,
                false,
            )
        with(gridRight) {
            addColumn(TestStatus::name).setHeader("Тесты справа").setAutoWidth(true).setFlexGrow(0)
            addColumn(TestStatus::status).setHeader("Статус теста")
        }
        return gridRight
    }

    private fun createLeftGrid(): Grid<TestStatus> {
        val gridLeft =
            Grid(
                TestStatus::class.java,
                false,
            )
        with(gridLeft) {
            addColumn(TestStatus::name).setHeader("Тесты слева").setAutoWidth(true).setFlexGrow(0)
            addColumn(TestStatus::status).setHeader("Статус теста")
        }
        return gridLeft
    }

    private fun createDifferenceGrid(): Grid<TestStatus> {
        val gridDiff =
            Grid(
                TestStatus::class.java,
                false,
            )
        with(gridDiff) {
            addColumn(TestStatus::name).setHeader("Название теста").setAutoWidth(true).setFlexGrow(0)
            addColumn(
                LitRenderer
                    .of<TestStatus>(LIT_TEMPLATE_HTML)
                    .withProperty("id", TestStatus::status)
                    .withFunction("clickHandler") { status: TestStatus ->
                        data
                            ?.getDifferenceList()
                            ?.let {
                                Comparator().getDiffDetails(
                                    it,
                                    SUITE_1_JSON,
                                    SUITE_2_JSON,
                                )
                            }?.filter { it.name == status.name }
                            ?.forEach {
                                CompareNotification(
                                    "${urlField1.value.refineUrl()}#suites/${it.infoLeft}",
                                    "${urlField2.value.refineUrl()}#suites/${it.infoRight}",
                                ).show()
                            }
                    },
            ).setHeader("Разница")
                .setSortable(true)
        }
        return gridDiff
    }

    companion object {
        const val REPORT_1_CSV = "report1.csv"
        const val REPORT_2_CSV = "report2.csv"
        const val SUITE_1_JSON = "suite1.json"
        const val SUITE_2_JSON = "suite2.json"
        const val INDEX_HTML = "index.html"
        private val LIT_TEMPLATE_HTML =
            """
            <vaadin-button title="Go to ..."
                           @click="${'$'}{clickHandler}"
                           theme="tertiary-inline small link">
                ${'$'}{item.id}
            </vaadin-button>
            """.trimIndent()
    }
}

// Иногда url до Allure репорта содержит # в конце, которую надо убирать
private fun String.refineUrl(): String = if (this.last() == '#') this.dropLast(1) else this
