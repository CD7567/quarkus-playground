package com.cd7567.quarkus_playground.services.file_service.impl;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;

/**
 * Simple pojo for file description
 */
public class FileDescPojo implements Comparable<FileDescPojo>{
    public String name;
    public long length;
    public boolean canRead;
    public boolean canWrite;

    /**
     * Representative constructor
     * @param file -- file to be represented
     */
    public FileDescPojo(File file) {
        this.name = file.getName();
        this.length = file.length();
        this.canRead = file.canRead();
        this.canWrite = file.canWrite();
    }

    /**
     * Builtin comparator to sort files when processing ls request
     * @param other the object to be compared.
     * @return standard integer comparator result
     */
    @Override
    public int compareTo(FileDescPojo other) {
        return String.CASE_INSENSITIVE_ORDER.compare(name, other.name);
    }
}

