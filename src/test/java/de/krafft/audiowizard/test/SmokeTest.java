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

import java.io.BufferedInputStream;

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

        byte[] blob = IOUtils.toByteArray(getClass().getResourceAsStream("/SoftCell-TaintedLove.wav"));
        String expectedJson = new String(IOUtils.toByteArray(getClass().getResourceAsStream("/SoftCell-TaintedLove_audioinfo.json")));

        MockMultipartFile audioFile = new MockMultipartFile("audiofile", "SoftCell-TaintedLove.wav", "audio/wav", blob);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/audioinfo");

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/audioinfo").file(audioFile)
                .param("some-random", "4"))
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));


    }
}
