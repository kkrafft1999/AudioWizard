package de.krafft.audiowizard;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.krafft.audiowizard.service.AudioInfo;
import de.krafft.audiowizard.service.AudioWizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.util.Arrays;


/**
 * see http://codophile.com/2015/05/27/how-to-upload-binary-file-to-spring-rest-service/
 */

@RestController
@MultipartConfig(fileSizeThreshold = 20971520) // 20MB
class AudioWizardController {

    String info = "<h1>AudioWizard Version 1.0</h1>";

    @Autowired
    AudioWizardService service;

    @RequestMapping("/")
    @ResponseBody
    String info() {
        return getInfo();
    }

    @RequestMapping("/audioinfo")
    @ResponseBody
    String audioinfo(@RequestParam("audiofile") MultipartFile audioFileRef) {

        try {
            AudioInfo info = service.getAudioInfo(audioFileRef.getInputStream(), audioFileRef.getOriginalFilename());

            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(info);

        } catch (Exception e) {
            return e.toString();
        }
    }

    @RequestMapping("/audiodata")
    @ResponseBody
    String audiodata(@RequestParam("offset")int pos,@RequestParam("count")int count,@RequestParam("audiofile") MultipartFile audioFileRef)
    {

        try {
            byte[] samples = service.getAudioByteArray( audioFileRef.getInputStream(), pos, count );
            return Arrays.toString(samples);
        } catch (Exception e) {
            return e.toString();
        }
    }


    public String getInfo() {
        return info;
    }
}


