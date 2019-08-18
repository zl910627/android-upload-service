package net.gotev.uploadservice;

import net.gotev.uploadservice.network.BodyWriter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Implements a binary file upload task.
 *
 * @author cankov
 * @author gotev (Aleksandar Gotev)
 */
public class BinaryUploadTask extends HttpUploadTask {

    @Override
    protected long getBodyLength() throws UnsupportedEncodingException {
        return params.files.get(0).getHandler().size(service);
    }

    @Override
    public void onWriteRequestBody(BodyWriter bodyWriter) throws IOException {
        bodyWriter.writeStream(params.files.get(0).getHandler().stream(service), this);
    }

    @Override
    protected void onSuccessfulUpload() {
        addSuccessfullyUploadedFile(params.files.get(0));
    }
}
