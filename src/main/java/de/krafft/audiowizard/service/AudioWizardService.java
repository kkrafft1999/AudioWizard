package de.krafft.audiowizard.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

@Service
public class AudioWizardService {


    /**
     * see http://www.jsresources.org/examples/AudioFileInfo.java.html
     *
     */
    public String getAudioInfo(InputStream in) {

        AudioInfo info = new AudioInfo();

        try {
            AudioFileFormat aff = AudioSystem.getAudioFileFormat(in);

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





            // Create Jackson ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(info);
            } catch (JsonProcessingException e) {
                return "error: " + e.getMessage();
            }

        } catch (Exception e) {
            return e.toString();
        }

    }

}
