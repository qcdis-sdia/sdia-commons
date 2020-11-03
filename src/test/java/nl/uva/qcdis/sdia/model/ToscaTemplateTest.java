/*
 * Copyright 2019 S. Koulouzis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.uva.qcdis.sdia.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import nl.uva.qcdis.sdia.model.tosca.ToscaTemplate;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author S. Koulouzis
 */
public class ToscaTemplateTest {

    private final ObjectMapper objectMapper;

    public ToscaTemplateTest() {
        this.objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test 
     *
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws java.io.UnsupportedEncodingException
     */
    @Test
    public void test() throws JsonProcessingException, UnsupportedEncodingException, IOException {
        FileInputStream in = new FileInputStream(downloadFile("https://raw.githubusercontent.com/qcdis-sdia/sdia-tosca/master/examples/application_example_planed.yaml"));
        MultipartFile file = new MockMultipartFile("file", in);
        byte[] bytes = file.getBytes();
        String ymlStr = new String(bytes, "UTF-8");
        ToscaTemplate tt = objectMapper.readValue(ymlStr, ToscaTemplate.class);
        assertNotNull(tt);
    }

    public static String downloadFile(String url) throws IOException {
        File f = File.createTempFile("application_example_updated", ".yml");
        f.deleteOnExit();

        FileUtils.copyURLToFile(
                new URL(url),
                f);
        return f.getAbsolutePath();
    }

}
