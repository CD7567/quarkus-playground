package com.cd7567.quarkus_playground;

import com.cd7567.quarkus_playground.services.file_service.FileService;
import com.cd7567.quarkus_playground.services.file_service.impl.FileDescPojo;
import io.quarkus.arc.log.LoggerName;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.multipart.FormValue;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataInput;

import java.util.Collection;
import java.util.List;

/**
 * Simple file repository resource
 */
@Path("/file")
public class FileResource {
    // JBoss Logger injection
    @LoggerName("FileResource")
    Logger log;

    // UriInfo to get params from URI
    @Inject
    UriInfo uriInfo;

    // FileService that manages repository
    @Inject
    FileService filesService;

    /**
     * REST endpoint that receives incoming files
     * @param input -- files as MultiPartForm
     * @return JSON representation of received files
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Upload file as MultiPart body")
    public List<FileDescPojo> receiveMultipart(MultipartFormDataInput input) {
        log.info("Received POST on /file");

        List<FormValue> receivedFiles;

        // FlatMap MultiPartForm values and filtering files;
        // Non-files are ignored
        receivedFiles = input.getValues()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter(FormValue::isFileItem)
                .toList();

        return filesService.saveFiles(receivedFiles);
    }

    /**
     * REST endpoint that sends requested file
     * @return MultiPartForm of requested file
     */
    @GET
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Operation(description = "Download file as MultiPart body; filename can be passed as 'file' URI parameter")
    public Uni<Response> sendMultipart(@QueryParam("file") String file) {
        log.info("Received GET on /file/download");
        return filesService.getFile(file);
    }

    /**
     * REST endpoint that receives incoming files
     * @return JSON representation of received files
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Delete files and get JSON pojo representation of those files")
    public List<FileDescPojo> deleteFile(@QueryParam("files") List<String> filenames) {
        log.info("Received DELETE on /{files}");
        return filesService.rmFiles(filenames);
    }

    /**
     * REST endpoint that sends list of existing files
     * @return JSON representation of existing files
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "List stored files")
    public List<FileDescPojo> listStorage() {
        log.info("Received GET on /file");
        return filesService.lsFiles();
    }
}
