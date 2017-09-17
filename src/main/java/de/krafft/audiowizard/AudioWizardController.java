package de.krafft.audiowizard;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.krafft.audiowizard.service.AudioInfo;
import de.krafft.audiowizard.service.AudioSampleFrame;
import de.krafft.audiowizard.service.AudioWizardService;
import de.krafft.audiowizard.service.Oscillator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tritonus.share.sampled.AudioFileTypes;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;


/**
 * see http://codophile.com/2015/05/27/how-to-upload-binary-file-to-spring-rest-service/
 */

@RestController
@MultipartConfig(fileSizeThreshold = 20971520) // 20MB
class AudioWizardController {

    private String info = "<h1>AudioWizard Version 1.0</h1>";

    @Autowired
    private AudioWizardService service;

    @RequestMapping("/")
    @ResponseBody
    String info() {
        return getInfo();
    }

    @RequestMapping("/audioinfo")
    @ResponseBody
    String audioinfo(@RequestParam("audiofile") MultipartFile audioFileRef) {

        try {
            AudioInfo info = service.getAudioInfo(audioFileRef.getInputStream());
            info.setFilename(audioFileRef.getOriginalFilename());
            info.setFilelength(audioFileRef.getSize());

            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(info);

        } catch (Exception e) {
            return e.toString();
        }
    }

    @RequestMapping("/audiobytedata")
    @ResponseBody
    String audiobytedata(@RequestParam("offset")int pos,@RequestParam("count")int count,@RequestParam("audiofile") MultipartFile audioFileRef)
    {

        try {
            byte[] samples = service.getAudioByteArray( audioFileRef.getInputStream(), pos, count );
            return Arrays.toString(samples);
        } catch (Exception e) {
            return e.toString();
        }
    }

    @RequestMapping("/audiosampledata")
    @ResponseBody
    String audiosampledata(@RequestParam("offset")int pos,@RequestParam("count")int count,@RequestParam("audiofile") MultipartFile audioFileRef)
    {

        try {
            StringBuilder result = new StringBuilder("[");
            AudioSampleFrame[] frames = service.getAudioSamples( audioFileRef.getInputStream(), pos, count );
            ObjectMapper mapper = new ObjectMapper();

            for (int i=0;i<frames.length;i++) {
                result.append(mapper.writeValueAsString(frames[i]));
                if(i<frames.length-1) result.append(",");
            }

            return result.append("]").toString();
        } catch (Exception e) {
            return e.toString();
        }
    }


    // http://localhost:8080/oscillator?waveform=0&frequency=2500&amplitude=0.5&samplerate=48000&samplesizeinbits=16&channels=2&duration=3
    @RequestMapping("/oscillator")
    @ResponseBody
    void oscillator(@RequestParam("waveform")int nWaveform,
                                  @RequestParam("frequency")float fFrequency,
                                  @RequestParam("amplitude") float fAmplitude,
                                  @RequestParam("samplerate") float fSampleRate,
                                  @RequestParam("samplesizeinbits") int nSampleSizeInBits,
                                  @RequestParam("channels") int nChannels,
                                  @RequestParam("duration") int nDuration,
                                  HttpServletResponse response)
    {

        int nFrameSize = nSampleSizeInBits / 8 * nChannels;

        try {

            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    fSampleRate, nSampleSizeInBits, nChannels, nFrameSize, fSampleRate, false);
            long	nLengthInFrames = Math.round(nDuration * fSampleRate);
            AudioInputStream inputStream = new Oscillator(nWaveform, fFrequency, fAmplitude, audioFormat, nLengthInFrames);

            response.setContentType("audio/basic");
            response.setHeader("Content-Disposition", "attachment; filename=\"Oscillatortone-"+fFrequency+"Hz-"+nDuration+"sec.au\"");
            AudioSystem.write(inputStream, AudioFileTypes.AU, response.getOutputStream());
            response.flushBuffer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getInfo() {
        return info;
    }
}


