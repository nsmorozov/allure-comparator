package com.example.application.controller

import com.vaadin.flow.component.notification.Notification
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.nio.channels.Channels
import java.nio.file.Files
import kotlin.io.path.Path

class Downloader {
    fun download(
        url: String,
        outputFileName: String,
    ) {
        try {
            URL(url).openStream().use {
                Channels.newChannel(it).use { rbc ->
                    FileOutputStream(outputFileName).use { fos ->
                        fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                    }
                }
            }
        } catch (e: IOException) {
            Notification.show("Проверьте URL до allure репорта. Текущий url = $url")
        }
    }

    fun deleteFiles(vararg files: String) {
        files.forEach { Files.deleteIfExists(Path(it)) }
    }

    fun ifFilesDownloaded(vararg files: String): Boolean {
        for (f in files) {
            if (!File(f).exists()) return false
        }
        return true
    }
}
