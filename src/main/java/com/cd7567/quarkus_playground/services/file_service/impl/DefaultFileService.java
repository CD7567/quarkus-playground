package com.cd7567.quarkus_playground.services.file_service.impl;

import com.cd7567.quarkus_playground.conf.ConfPojo;
import com.cd7567.quarkus_playground.services.file_service.FileService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.multipart.FormValue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

;

/**
 * Service that provides file management
 */
@RequestScoped
public class DefaultFileService implements FileService {
    @Inject
    ConfPojo conf;

    public List<FileDescPojo> saveFiles(List<FormValue> fileValues) {
        // List of received files descriptions
        List<FileDescPojo> addedFilesDesc = new ArrayList<>();

        fileValues.forEach(
                it -> {
                    try {
                        // Creating file entry in repository
                        File destFile = getStoredFile(it.getFileName());

                        // If file doesn't exist, we should create it
                        if (destFile.exists()) {
                            destFile.delete();
                        }

                        // Write received content
                        it.getFileItem().write(destFile.toPath());
                        addedFilesDesc.add(new FileDescPojo(destFile));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        return addedFilesDesc;
    }

    public List<FileDescPojo> lsFiles() {
        // File entry of storage directory
        File destFile = getDirFile();

        // Return list of File converted to FileDescPojo
        return Arrays.stream(Objects.requireNonNull(destFile.listFiles()))
                .map(FileDescPojo::new)
                .sorted()
                .toList();
    }

    public Uni<Response> getFile(String filename) {
        // Filter requested filenames that represent existing files
        File file = getStoredFile(filename);

        // Response builder to generate MultiPart response
        Response.ResponseBuilder builder;

        if (file.exists()) {
            // Success, returning file
            builder = Response.ok(file);
            builder.header("Content-Disposition", "attachment; filename=" + file.getName());
        } else {
            // Handling user mess up
            builder = Response.status(404, "No such files found!");
        }

        // Build final response for client
        return Uni.createFrom().item(builder.build());
    }

    @Override
    public List<FileDescPojo> rmFiles(List<String> fileNames) {
        // Filter requested filenames that represent existing files
        List<File> validFiles = fileNames.stream()
                .map(this::getStoredFile)
                .filter(File::exists)
                .toList();

        // Delete existing files
        validFiles.forEach(File::delete);

        return validFiles.stream()
                .map(FileDescPojo::new)
                .sorted()
                .toList();
    }

    private File getDirFile() {
        File dirFile = new File("%s/%s".formatted(conf.tmp().path(), conf.tmp().storage()));

        if (!dirFile.exists()) {
            dirFile.mkdir();
        }

        return dirFile;
    }

    private File getStoredFile(String name) {
        File dirFile = getDirFile();

        return new File(dirFile.getAbsolutePath() + File.separator + name);
    }
}
