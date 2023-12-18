package com.example.application.controller

import com.vaadin.flow.component.notification.Notification
import java.io.FileOutputStream
import java.net.MalformedURLException
import java.net.URL
import java.nio.channels.Channels

class Downloader {

    private val csvFile = "/data/suites.csv"
    fun download (url: String, outputFileName: String) {

        try {
            URL(url + csvFile).openStream().use {
                Channels.newChannel(it).use { rbc ->
                    FileOutputStream(outputFileName).use { fos ->
                        fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                    }
                }
            }
        } catch (e: MalformedURLException) {
            Notification.show("Проверьте URL до allure репорта. Текущий url = $url")
        }
    }
}