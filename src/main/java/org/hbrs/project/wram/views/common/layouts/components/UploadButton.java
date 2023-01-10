package org.hbrs.project.wram.views.common.layouts.components;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.hbrs.project.wram.control.entwickler.EntwicklerService;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author @tkeppe2s (Tom Keppeler)
 * @apiNote this class is responsible for uploading images
 * @vision 1.0
 */
public class UploadButton extends Upload {

    private final UUID userId;
    private final EntwicklerService entwicklerService;
    private MemoryBuffer memoryBuffer = new MemoryBuffer();
    private int maxFileSizeInBytes = 10 * 1024 * 1024; // 10MB;
    private Button descriptionButton = new Button("Upload profile image");

    public UploadButton(UUID userId, EntwicklerService entwicklerService) {
        super();
        this.entwicklerService = entwicklerService;
        memoryBuffer = new MemoryBuffer();
        this.userId = userId;
        init();
    }

    /**
     * @param userId
     * @param listener
     * @apiNote this method initializes the upload button and sets the listener. In addition, only a jpg or png image is accepted
     */
    private void init() {
        this.setReceiver(memoryBuffer);
        this.setAcceptedFileTypes("image/jpeg", "image/png", "image/jpg");
        this.setMaxFileSize(this.maxFileSizeInBytes);
        this.setUploadButton(this.descriptionButton);
        this.setWidth("175px");
        this.addSucceededListener(clickListener());
    }

    /**
     * @return ComponentEventListener<SucceededEvent>
     * @apiNote gets the image from the memory buffer and calls the methode to saves it in the database
     */
    private ComponentEventListener<SucceededEvent> clickListener() {
        return event -> {
            // Get information about the uploaded file
            InputStream fileData = memoryBuffer.getInputStream();
            String fileName = event.getFileName();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();
            processFile(fileData, fileName, contentLength, mimeType);
        };
    }

    /**
     * @param fileData
     * @param fileName
     * @param contentLength
     * @param mimeType
     * @apiNote saves the image in the database
     */
    private void processFile(InputStream fileData, String fileName, long contentLength, String mimeType) {
        //save file to database
        byte[] imageBytes = null;
        try {
            imageBytes = fileData.readAllBytes();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            Notification.show("error while saving the image");
        }
        if (imageBytes != null) {
            entwicklerService.saveImage(imageBytes, userId);
        }
    }

    public void setMaxFileSizeInBytes(int maxFileSizeInBytes) {
        this.maxFileSizeInBytes = maxFileSizeInBytes;
    }

    public void setDescription(Button descriptionButton) {
        this.descriptionButton = descriptionButton;
    }
}
