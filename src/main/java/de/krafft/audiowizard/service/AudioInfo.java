package de.krafft.audiowizard.service;

public class AudioInfo {

    private String filename;
    private String type;
    private long frameSize;
    private int frameLength;
    private float frameRate;
    private float sampleRate;
    private long sampleSizeinBits;
    private long byteLength;
    private int channels;
    private boolean isBigEndian;
    private String encoding;



    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    public long getFrameSize() {
        return frameSize;
    }

    void setFrameSize(long frameSize) {
        this.frameSize = frameSize;
    }

    public float getFrameRate() {
        return frameRate;
    }

    void setFrameRate(float frameRate) {
        this.frameRate = frameRate;
    }

    public long getByteLength() {
        return byteLength;
    }

    void setByteLength(long byteLength) {
        this.byteLength = byteLength;
    }


    public long getSampleSizeinBits() {
        return sampleSizeinBits;
    }

    void setSampleSizeinBits(int sampleSizeinBits) {
        this.sampleSizeinBits = sampleSizeinBits;
    }



    public float getSampleRate() {
        return sampleRate;
    }

    void setSampleRate(float sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int getChannels() {
        return channels;
    }

    void setChannels(int channels) {
        this.channels = channels;
    }

    public boolean isBigEndian() {
        return isBigEndian;
    }

    void setBigEndian(boolean bigEndian) {
        this.isBigEndian = bigEndian;
    }

    public String getEncoding() {
        return encoding;
    }

    void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getFrameLength() {
        return frameLength;
    }

    void setFrameLength(int frameLength) {
        this.frameLength = frameLength;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
