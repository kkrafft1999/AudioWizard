package de.krafft.audiowizard.service;

import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.InputStream;

@Service
public class AudioWizardService {


    /**
     * see http://www.jsresources.org/examples/AudioFileInfo.java.html
     *
     */
    public AudioInfo getAudioInfo(InputStream inputStream) throws Exception {

        AudioInfo info = new AudioInfo();
        AudioFileFormat aff = AudioSystem.getAudioFileFormat(inputStream);
        AudioInputStream ais = AudioSystem.getAudioInputStream(inputStream);
        AudioFormat af = aff.getFormat();

        info.setType(aff.getType().toString());

        int bytesPerSample = AudioSampleConversion.bytesPerSample(af.getSampleSizeInBits());
        info.setBytesPerSample(bytesPerSample);
        info.setChannels(af.getChannels());

        info.setByteLength(ais.getFrameLength()*bytesPerSample*af.getChannels());
        info.setFrameSize(af.getFrameSize());
        info.setFrameRate(af.getFrameRate());
        info.setFrameLength(aff.getFrameLength());
        info.setSampleRate(af.getSampleRate());
        info.setSampleSizeinBits(af.getSampleSizeInBits());

        info.setBigEndian(af.isBigEndian());
        info.setEncoding(af.getEncoding().toString());

        return info;

    }


    public byte[] getAudioByteArray(InputStream inputStream, int position, int count) throws Exception {

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        byte[] buffer = new byte[count];

        final long skip = audioInputStream.skip(position);
        final int read = audioInputStream.read(buffer, 0, buffer.length);

        return buffer;

    }

    // see https://stackoverflow.com/questions/26824663/how-do-i-use-audio-sample-data-from-java-sound
    public AudioSampleFrame[] getAudioSamples(InputStream inputStream, int position, int numberOfFrames) throws Exception {

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        AudioFormat format = audioInputStream.getFormat();
        int bytesPerSample = AudioSampleConversion.bytesPerSample(format.getSampleSizeInBits());
        int numberOfchannels = format.getChannels();

        float[] samples = new float[numberOfFrames*numberOfchannels];
        byte[] buffer = new byte[samples.length*bytesPerSample];

        final long skip = audioInputStream.skip(position * numberOfchannels * bytesPerSample);
        final int read = audioInputStream.read(buffer, 0, buffer.length);

        final int nConverted = AudioSampleConversion.unpack(buffer, samples, buffer.length, format);

        AudioSampleFrame[] frames = new AudioSampleFrame[numberOfFrames];

        for (int i = 0; i < frames.length; i++) {
            frames[i] = new AudioSampleFrame(numberOfchannels);
            for(int c=0; c<numberOfchannels; c++) {
                frames[i].setSample( samples[i*numberOfchannels+c],c);
            }

        }

        return frames;

    }
}
