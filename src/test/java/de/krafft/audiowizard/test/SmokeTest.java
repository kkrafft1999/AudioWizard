package de.krafft.audiowizard.test;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void getAudioInfo() throws Exception {

        byte[] blob = IOUtils.toByteArray(getClass().getResourceAsStream("/Softcell-TaintedLove.wav"));
        String expectedJson = new String(IOUtils.toByteArray(getClass().getResourceAsStream("/Softcell-TaintedLove_audioinfo.json")));

        MockMultipartFile audioFile = new MockMultipartFile("audiofile", "Softcell-TaintedLove.wav", "audio/wav", blob);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/audioinfo").file(audioFile))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));


    }

    @Test
    public void getAudioData() throws Exception {

        byte[] blob = IOUtils.toByteArray(getClass().getResourceAsStream("/Softcell-TaintedLove.wav"));
        String expectedJson = new String(IOUtils.toByteArray(getClass().getResourceAsStream("/Softcell-TaintedLove_audioinfo.json")));

        MockMultipartFile audioFile = new MockMultipartFile("audiofile", "Softcell-TaintedLove.wav", "audio/wav", blob);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/audiodata").file(audioFile)
                .param("offset","65535").param("count","21"))
                .andExpect(status().is(200))
                .andExpect(content().string("----"));


    }
}
