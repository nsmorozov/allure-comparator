package com.example.application.views.main;

import com.example.application.controller.Comparator;
import com.example.application.dto.Diff;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.stefan.table.Table;
import org.vaadin.stefan.table.TableCell;
import org.vaadin.stefan.table.TableDataCell;
import org.vaadin.stefan.table.TableRow;

@PageTitle("Сравнение репортов")
@Route(value = "")
public class MainView extends VerticalLayout {

    public static final String REPORT_1_CSV = "report1.csv";
    public static final String REPORT_2_CSV = "report2.csv";
    private final TextField urlField1;
    private final TextField urlField2;
    private final Button downloadBtn;
    private final Button cleanTableButton;
    private final Table table;


    public MainView() {

        urlField1 = new TextField("Репорт 1");
        urlField2 = new TextField("Репорт 2");
        downloadBtn = new Button("Сравнить");
        cleanTableButton = new Button("Очистить");
        table =  new Table();
        HorizontalLayout buttonsPanel = new HorizontalLayout();

        downloadBtn.addClickListener(e -> {
//            (new Downloader()).download(urlField1.getValue(), REPORT_1_CSV);
//            (new Downloader()).download(urlField2.getValue(), REPORT_2_CSV);
            table.getBody().removeAllRows();
            Diff data = new Comparator().compare("/Users/n.morozov/IdeaProjects/allure-comparator/src/main/resources/suites1.csv",
                    "/Users/n.morozov/IdeaProjects/allure-comparator/src/main/resources/suites2.csv");
            buildTable(table, data);
            add(table);
        });

        cleanTableButton.addClickListener(e -> {
            table.getBody().removeAllRows();
            remove(table);
        });

        buttonsPanel.add(downloadBtn, cleanTableButton);
        setMargin(true);
        setHorizontalComponentAlignment(Alignment.CENTER, urlField1, urlField2, buttonsPanel, table);
        add(urlField1, urlField2, buttonsPanel);
    }

    private Table buildTable(Table table, Diff diff) {
        String borderStyle = "1px solid black";
        String cellPadding = "5px";
        table.getStyle().setBorder(borderStyle);
        table.getStyle().setWidth("width:100%");
        TableRow headerRow = table.addRow();
        headerRow.addHeaderCells("Test", "Status");
        diff.getDiff().forEach((key, value) -> {
            TableRow detailsRow = table.addRow();
            detailsRow.getStyle().setBorder(borderStyle);
            TableCell c1 = new TableDataCell();
            c1.setText(key.toString());
            c1.getStyle().setBorder(borderStyle).setPadding(cellPadding);
            TableCell c2 = new TableDataCell();
            c2.setText(value);
            c2.getStyle().setBorder(borderStyle).setPadding(cellPadding);
            detailsRow.addCells(c1, c2);
        });
        return table;
    }

}
