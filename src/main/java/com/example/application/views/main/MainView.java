package com.example.application.views.main;

import com.example.application.controller.Comparator;
import com.example.application.dto.Diff;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Сравнение репортов")
@Route(value = "")
public class MainView extends VerticalLayout {

    public static final String REPORT_1_CSV = "report1.csv";
    public static final String REPORT_2_CSV = "report2.csv";
    private final TextField urlField1;
    private final TextField urlField2;
    private final Button downloadBtn;
    private final Button cleanTableButton;



    public MainView() {

        urlField1 = new TextField("Репорт 1");
        urlField2 = new TextField("Репорт 2");
        downloadBtn = new Button("Сравнить");
        cleanTableButton = new Button("Очистить");

        HorizontalLayout buttonsPanel = new HorizontalLayout();
        Grid<Diff.TestStatus> grid = new Grid<>(Diff.TestStatus.class, false);
        grid.addColumn(Diff.TestStatus::getName).setHeader("Test name");
        grid.addColumn(Diff.TestStatus::getStatus).setHeader("Test status");


        downloadBtn.addClickListener(e -> {
//            (new Downloader()).download(urlField1.getValue(), REPORT_1_CSV);
//            (new Downloader()).download(urlField2.getValue(), REPORT_2_CSV);
            Diff data = new Comparator().compare("/Users/n.morozov/IdeaProjects/allure-comparator/src/main/resources/suites1.csv",
                    "/Users/n.morozov/IdeaProjects/allure-comparator/src/main/resources/suites2.csv");
            grid.setItems(data.getDifferenceList());
            add(grid);

        });

        cleanTableButton.addClickListener(e -> remove(grid));

        buttonsPanel.add(downloadBtn, cleanTableButton);
        setMargin(true);
        setHorizontalComponentAlignment(Alignment.CENTER, urlField1, urlField2, buttonsPanel);
        add(urlField1, urlField2, buttonsPanel);
    }
}
