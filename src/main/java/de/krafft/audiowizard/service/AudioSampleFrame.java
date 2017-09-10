package de.krafft.audiowizard.service;

public class AudioSampleFrame {
    private float[] channels;

    AudioSampleFrame(int numberOfChannels) {
        channels = new float[numberOfChannels];
    }

    void setSample(float sample, int channelNo) {
            channels[channelNo] = sample;
    }

    public float[] getChannels() {
        return channels;
    }
}
