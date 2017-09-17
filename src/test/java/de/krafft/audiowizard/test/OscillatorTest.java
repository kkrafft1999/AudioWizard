package de.krafft.audiowizard.test;

import de.krafft.audiowizard.service.Oscillator;
import org.junit.Test;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class OscillatorTest {

    @Test
    public void simpleWave() {

        float fSignalFrequency = 2000.0F;
        float	fAmplitude = 0.4F;
        float fSampleRate = 48000.0F;
        int nDuration = 1;
        AudioFileFormat.Type	targetType = AudioFileFormat.Type.AU;


        AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                fSampleRate, 16, 2, 4, fSampleRate, false);


        long	nLengthInFrames = Math.round(nDuration * fSampleRate);
        assertEquals(48000, nLengthInFrames);

        AudioInputStream inputStream = new Oscillator(Oscillator.WAVEFORM_SINE, fSignalFrequency, fAmplitude, audioFormat, nLengthInFrames);


        try {
            AudioSystem.write(inputStream, targetType, new File(System.getProperty("user.home")+"/AudioTest.au"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
