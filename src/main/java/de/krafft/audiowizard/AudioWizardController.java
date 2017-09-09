package de.krafft.audiowizard;

import de.krafft.audiowizard.service.AudioWizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;


/**
 * see http://codophile.com/2015/05/27/how-to-upload-binary-file-to-spring-rest-service/
 */

@RestController
@MultipartConfig(fileSizeThreshold = 20971520) // 20MB
class AudioWizardController {

    String info = "AudioWizard Version 1.0";

    @Autowired
    AudioWizardService service;

    @RequestMapping("/")
    @ResponseBody
    String info() {
        return getInfo();
    }

    @RequestMapping("/audioinfo")
    @ResponseBody
    String audioinfo(@RequestParam("audiofile") MultipartFile audioFileRef)
    {
        String fileName = audioFileRef.getOriginalFilename();
        try {
            return service.getAudioInfo( audioFileRef.getInputStream() );
        } catch (IOException e) {
            return "error:"+e.getMessage();
        }
    }


    public String getInfo() {
        return info;
    }
}


