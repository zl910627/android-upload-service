package net.gotev.uploadservice;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import net.gotev.uploadservice.data.UploadStatus;
import net.gotev.uploadservice.logger.UploadServiceLogger;
import net.gotev.uploadservice.network.ServerResponse;

/**
 * Class which contains all the data passed in broadcast intents to notify task progress, errors,
 * completion or cancellation.
 *
 * @author gotev (Aleksandar Gotev)
 */
class BroadcastData implements Parcelable {

    private UploadStatus status;
    private Exception exception;
    private UploadInfo uploadInfo;
    private ServerResponse serverResponse;

    public BroadcastData() {

    }

    public Intent getIntent() {
        Intent intent = new Intent(UploadServiceConfig.INSTANCE.getBroadcastAction());
        intent.setPackage(UploadServiceConfig.INSTANCE.getNamespace());
        intent.putExtra(UploadService.PARAM_BROADCAST_DATA, this);
        return intent;
    }

    // This is used to regenerate the object.
    // All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<BroadcastData> CREATOR =
            new Parcelable.Creator<BroadcastData>() {
                @Override
                public BroadcastData createFromParcel(final Parcel in) {
                    return new BroadcastData(in);
                }

                @Override
                public BroadcastData[] newArray(final int size) {
                    return new BroadcastData[size];
                }
            };

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(status.ordinal());
        parcel.writeSerializable(exception);
        parcel.writeParcelable(uploadInfo, flags);
        parcel.writeParcelable(serverResponse, flags);
    }

    private BroadcastData(Parcel in) {
        status = UploadStatus.values()[in.readInt()];
        exception = (Exception) in.readSerializable();
        uploadInfo = in.readParcelable(UploadInfo.class.getClassLoader());
        serverResponse = in.readParcelable(ServerResponse.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public UploadStatus getStatus() {
        if (status == null) {
            UploadServiceLogger.INSTANCE.error(getClass().getSimpleName(), "Status not defined! Returning " + UploadStatus.CANCELLED);
            return UploadStatus.CANCELLED;
        }

        return status;
    }

    public BroadcastData setStatus(UploadStatus status) {
        this.status = status;
        return this;
    }

    public Exception getException() {
        return exception;
    }

    public BroadcastData setException(Exception exception) {
        this.exception = exception;
        return this;
    }

    public UploadInfo getUploadInfo() {
        return uploadInfo;
    }

    public BroadcastData setUploadInfo(UploadInfo uploadInfo) {
        this.uploadInfo = uploadInfo;
        return this;
    }

    public ServerResponse getServerResponse() {
        return serverResponse;
    }

    public BroadcastData setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
        return this;
    }
}
