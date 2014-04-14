package ru.nektodev.vlastfm.model;

import java.io.File;
import java.util.UUID;

/**
 * TODO
 *
 * @author nektodev
 */
public enum Settings {
    INSTANCE;

    private Settings() {
        destDir = new File("/Users/nektodev/temp/vLastFm/" + UUID.randomUUID());
        destDir.mkdirs();
    }

    private File destDir;

    public File getDestDir() {
        return destDir;
    }
}
