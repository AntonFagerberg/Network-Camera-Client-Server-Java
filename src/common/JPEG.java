package common;

public class JPEG {
    private byte[] data;
    private long timeStamp;

    public JPEG(byte[] data, long timeStamp) {
        this.data = data;
        this.timeStamp = timeStamp;
    }

    public byte[] getData() {
        return data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
