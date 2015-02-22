package com.github.iesen.rabbitmq.plugin.manager;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

public class FileUtils {

    private static final int BUFFER = 2048;

    public static void download(String url, String destPath) throws IOException {
        InputStream downloadIn = new BufferedInputStream(new URL(url).openStream(), BUFFER);
        File destFile = new File(destPath);
        com.google.common.io.Files.createParentDirs(destFile);
        Files.copy(downloadIn, destFile.toPath());
    }

    public static void extractTarGzip(String tarGzipFilePath, String destPath) throws IOException {
        TarArchiveInputStream tarIn = null;
        try {
            GzipCompressorInputStream gzIn = new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(tarGzipFilePath), BUFFER));
            tarIn = new TarArchiveInputStream(gzIn);
            // Read entries
            TarArchiveEntry entry;
            while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    File f = new File(destPath + File.separator + entry.getName());
                    f.mkdirs();
                } else {
                    int count;
                    byte data[] = new byte[BUFFER];
                    FileOutputStream fos = new FileOutputStream(destPath + File.separator + entry.getName());
                    BufferedOutputStream destOut = new BufferedOutputStream(fos, BUFFER);
                    while ((count = tarIn.read(data, 0, BUFFER)) != -1) {
                        destOut.write(data, 0, count);
                    }
                    destOut.close();
                }
            }
        } finally {
            if (tarIn != null) {
                tarIn.close();
            }
        }
    }

    public static void extractZip(String zipFilePath, String destPath) throws IOException {
        ZipArchiveInputStream zipIn = null;
        try {
            zipIn = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFilePath), BUFFER));
            ZipArchiveEntry entry;
            while ((entry = zipIn.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File f = new File(destPath + File.separator + entry.getName());
                    f.mkdirs();
                } else {
                    int count;
                    byte data[] = new byte[BUFFER];
                    FileOutputStream fos = new FileOutputStream(destPath + File.separator + entry.getName());
                    BufferedOutputStream destOut = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zipIn.read(data, 0, BUFFER)) != -1) {
                        destOut.write(data, 0, count);
                    }
                    destOut.close();
                }
            }
        } finally {
            if (zipIn != null) {
                zipIn.close();
            }
        }
    }
}
