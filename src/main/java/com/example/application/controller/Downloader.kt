package com.example.application.controller

import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels

class Downloader {
    fun download (url: URL, outputFileName: String) {
        url.openStream().use {
            Channels.newChannel(it).use { rbc ->
                FileOutputStream(outputFileName).use { fos ->
                    fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                }
            }
        }
    }
}