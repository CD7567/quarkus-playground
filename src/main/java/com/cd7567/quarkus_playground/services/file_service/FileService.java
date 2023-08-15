package com.cd7567.quarkus_playground.services.file_service;

import com.cd7567.quarkus_playground.services.file_service.impl.FileDescPojo;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.multipart.FormValue;

import java.util.List;

/**
 * Service that manages repository
 */
public interface FileService {
    /**
     * Method for saving files
     * @param fileValues -- FormValues representing received files
     * @return Pojo representation of received files
     */
    List<FileDescPojo> saveFiles(List<FormValue> fileValues);

    /**
     * Method for requesting files
     * @param filename -- file to get
     * @return Pojo representation of requested file
     */
    Uni<Response> getFile(String filename);

    /**
     * Method for deleting files
     * @param fileNames -- files to remove
     * @return Pojo representation of deleted files
     */
    List<FileDescPojo> rmFiles(List<String> fileNames);

    /**
     * Method that lists repository
     * @return Pojo representation of stored files
     */
    List<FileDescPojo> lsFiles();
}
