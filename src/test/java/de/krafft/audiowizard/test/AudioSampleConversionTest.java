package de.krafft.audiowizard.test;

import de.krafft.audiowizard.service.AudioSampleConversion;
import org.junit.Test;

import javax.sound.sampled.AudioFormat;

import static org.junit.Assert.assertEquals;

public class AudioSampleConversionTest {

    @Test
    public void unpack16bitChannel() {
        int bitsPerSample = 16;
        int bytesPerSample = bitsPerSample / 8;

        // bits(83,-10): 01010011, 11110110
        //  (!bigEndian) => 11110110 01010011 => 63059
        byte[] bytes = new byte[]{83, -10};
        long value = AudioSampleConversion.unpackBits(bytes, 0, false, bytesPerSample);
        assertEquals(63059, value);

        // bits(110,-3): 01101110, 11111101
        //  (!bigEndian) => 11111101 01101110 => 64878
        bytes = new byte[]{110, -3};
        value = AudioSampleConversion.unpackBits(bytes, 0, false, bytesPerSample);
        assertEquals(64878, value);
    }

    @Test
    public void unpack16bitSampleFrame() {
        int bitsPerSample = 16;

        float result = AudioSampleConversion.getSample(
                bitsPerSample,
                AudioFormat.Encoding.PCM_SIGNED,
                AudioSampleConversion.fullScale(16),
                63059);
        assertEquals(-0.075592041015625, result, 0.0001);

        result = AudioSampleConversion.getSample(
                bitsPerSample,
                AudioFormat.Encoding.PCM_SIGNED,
                AudioSampleConversion.fullScale(16),
                64878);
        assertEquals(-0.02008056640625, result, 0.0001);

    }


    @Test
    public void fullScale() {
        float value = AudioSampleConversion.fullScale(8);
        assertEquals(128.0,value,0.0001);

        value = AudioSampleConversion.fullScale(16);
        assertEquals(32768.0,value,0.0001);

        value = AudioSampleConversion.fullScale(24);
        assertEquals(8388608.0,value,0.0001);
    }
}
