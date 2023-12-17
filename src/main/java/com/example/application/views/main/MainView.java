package com.example.application.views.main;

import com.example.application.controller.Comparator;
import com.example.application.controller.Downloader;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.net.MalformedURLException;
import java.net.URL;

@PageTitle("Сравнение репортов")
@Route(value = "")
public class MainView extends HorizontalLayout {

    private final TextField urlField1;
    private final TextField urlField2;
    private final Button downloadBtn;

    public MainView() {
        urlField1 = new TextField("Репорт 1");
        urlField2 = new TextField("Репорт 2");
        downloadBtn = new Button("Сравнить");
        downloadBtn.addClickListener(e -> {
            Notification.show("Hello " + urlField1.getValue());
            //                (new Downloader()).download(new URL(urlField1.getValue()), "report1.csv");
//                (new Downloader()).download(new URL(urlField2.getValue()), "report2.csv");
            new Comparator().compare("12", "34");
        });
        downloadBtn.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, urlField1, urlField2, downloadBtn);

        add(urlField1, urlField2, downloadBtn);
    }

}
