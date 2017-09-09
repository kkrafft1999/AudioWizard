package de.krafft.audiowizard.service;

import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class AudioWizardService {


    /**
     * see http://www.jsresources.org/examples/AudioFileInfo.java.html
     *
     */
    public AudioInfo getAudioInfo(InputStream inputStream, String filename) throws Exception {

        AudioInfo info = new AudioInfo();
        info.setFilename(filename);


        AudioFileFormat aff = AudioSystem.getAudioFileFormat(inputStream);

        info.setType(aff.getType().toString());
        info.setByteLength(aff.getByteLength());

        AudioFormat af = aff.getFormat();
        info.setFrameSize(af.getFrameSize());
        info.setFrameRate(af.getFrameRate());
        info.setFrameLength(aff.getFrameLength());
        info.setSampleRate(af.getSampleRate());
        info.setSampleSizeinBits(af.getSampleSizeInBits());
        info.setChannels(af.getChannels());
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
    public float[] getAudioSamples(InputStream inputStream, int position, int count) throws Exception {

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);


        return new float[0];

    }
}
