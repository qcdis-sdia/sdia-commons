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
package nl.uva.qcdis.sdia.commons.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.jcraft.jsch.KeyPair;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import static nl.uva.qcdis.sdia.commons.utils.Constants.*;
import nl.uva.qcdis.sdia.model.NodeTemplate;
import nl.uva.qcdis.sdia.model.NodeTemplateMap;
import nl.uva.qcdis.sdia.model.Provisioner;
import nl.uva.qcdis.sdia.model.cloud.storm.CloudsStormSubTopology;
import nl.uva.qcdis.sdia.model.cloud.storm.OpCode;
import nl.uva.qcdis.sdia.model.tosca.Credential;
import nl.uva.qcdis.sdia.model.tosca.ToscaTemplate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import nl.uva.qcdis.sdia.sure.tosca.client.ApiException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author S.Koulouzis
 */
public class ToscaHelperTest {

    private static ObjectMapper objectMapper;

    private static ToscaHelper instance;
    private static ToscaTemplate toscaTemplatea2TVMopologies;
    private static Boolean serviceUp;
    private static ToscaTemplate provisionedToscaTemplate;
    private ToscaTemplate toscaTemplateWithCredentials;

    public ToscaHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws UnsupportedEncodingException, JsonProcessingException, IOException, ApiException {

        Properties prop = new Properties();
        String resourceName = "src/test/resources/application.properties";
        prop.load(new FileInputStream(resourceName));
        File file = File.createTempFile("application_example_2_topologies", ".yaml");
        file.deleteOnExit();
        FileUtils.copyURLToFile(
                new URL("https://raw.githubusercontent.com/QCDIS/sdia-tosca/master/examples/application_example_2_topologies.yaml"),
                file);

        byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        String ymlStr = new String(bytes, "UTF-8");
        objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        toscaTemplatea2TVMopologies = objectMapper.readValue(ymlStr, ToscaTemplate.class);

        file = File.createTempFile("application_example_provisioned", ".yaml");
        file.deleteOnExit();
        FileUtils.copyURLToFile(
                new URL("https://raw.githubusercontent.com/QCDIS/sdia-tosca/master/examples/application_example_provisioned.yaml"),
                file);

        bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        ymlStr = new String(bytes, "UTF-8");

        objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        provisionedToscaTemplate = objectMapper.readValue(ymlStr, ToscaTemplate.class);

        String serviceBasePath = prop.getProperty("sure-tosca.base.path");
        serviceUp = ToscaHelper.isServiceUp(serviceBasePath);
        if (serviceUp) {
            instance = new ToscaHelper(serviceBasePath);
            instance.uploadToscaTemplate(toscaTemplatea2TVMopologies);
        }

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
     * Test of getId method, of class ToscaHelper.
     */
    @Test
    public void testGetId() {
        if (serviceUp) {
            System.out.println("getId");
            Integer result = instance.getId();
            assertNotNull(result);
        }
    }

    /**
     * Test of getSupportedProvisionInterfaceDefinitions method, of class
     * ToscaHelper.
     *
     * @throws nl.uva.qcdis.sdia.sure.tosca.client.ApiException
     */
    @Test
    public void testGetProvisionInterfaceDefinitions() throws ApiException {
        if (serviceUp) {
            System.out.println("getProvisionInterfaceDefinitions");
            String expected = CLOUD_STORM_INTERFACE;
            List<String> toscaInterfaceTypes = new ArrayList<>();
            toscaInterfaceTypes.add(expected);
            List<Map<String, Object>> result = instance.getProvisionInterfaceDefinitions(toscaInterfaceTypes);
            assertNotNull(result);
            String key = result.get(0).keySet().iterator().next();
//            assertEquals(expected, key);
        }
    }

    /**
     * Test of getVMTopologyTemplates method, of class ToscaHelper.
     *
     * @throws nl.uva.qcdis.sdia.sure.tosca.client.ApiException
     */
    @Test
    public void testGetVMTopologyTemplates() throws ApiException {
        if (serviceUp) {
            System.out.println("getVMTopologyTemplates");
            List<NodeTemplateMap> result = instance.getVMTopologyTemplates();
            assertNotNull(result);
            for (NodeTemplateMap nodeTemplateMap : result) {
                assertEquals(nodeTemplateMap.getNodeTemplate().getType(), VM_TOPOLOGY);
            }
        }
    }

    /**
     * Test of getTemplateVMsForVMTopology method, of class ToscaHelper.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetTemplateVMsForVMTopology() throws Exception {
        if (serviceUp) {
            System.out.println("getTemplateVMsForVMTopology");
            List<NodeTemplateMap> vmTopologyTemplatesMap = instance.getVMTopologyTemplates();
            for (NodeTemplateMap nodeTemplateMap : vmTopologyTemplatesMap) {
                List<NodeTemplateMap> result = instance.getTemplateVMsForVMTopology(nodeTemplateMap);
                for (NodeTemplateMap mvmTopology : result) {
                    assertEquals(VM_TYPE, mvmTopology.getNodeTemplate().getType());

                }
            }
        }

    }

    /**
     * Test of setCredentialsInVMTopology method, of class ToscaHelper.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testSetCredentialsAndInterface() throws Exception {
        if (serviceUp) {
            toscaTemplateWithCredentials = null;

            System.out.println("setCredentialsInVMTopology");
            instance.uploadToscaTemplate(toscaTemplatea2TVMopologies);
            List<NodeTemplateMap> vmTopologies = instance.getVMTopologyTemplates();

            for (NodeTemplateMap vmTopologyMap : vmTopologies) {
                String provider = instance.getTopologyProvider(vmTopologyMap);

                Credential credential = new Credential();
                credential.setCloudProviderName(provider);
                credential.setProtocol("protocol");
                Map<String, String> keys = new HashMap<>();
                keys.put("key1", "eeeeeeavovfouirveiuvbepuyb8rwqovd8boacbdbvwy8oqry7f08r3euadinanzxcjc078yn0183xoqedw");
                credential.setKeys(keys);
                credential.setToken("ijwbfciweubfriw");
                credential.setTokenType("passwrd");
                credential.setUser("user");
                vmTopologyMap = instance.setCredentialsInVMTopology(vmTopologyMap, credential);
                toscaTemplateWithCredentials = instance.setNodeInToscaTemplate(toscaTemplatea2TVMopologies, vmTopologyMap);

            }
            instance.uploadToscaTemplate(toscaTemplateWithCredentials);
            NodeTemplate topology_1 = toscaTemplateWithCredentials.getTopologyTemplate().getNodeTemplates().get("topology_1");
            Map<String, Object> attributes = topology_1.getAttributes();
            assertNotNull(attributes);
            assertNotNull(attributes.get("credentials"));
            NodeTemplate topology = toscaTemplateWithCredentials.getTopologyTemplate().getNodeTemplates().get("topology");
            attributes = topology.getAttributes();
            assertNotNull(attributes);
            assertNotNull(attributes.get("credentials"));

            List<NodeTemplateMap> vmTopologiesMaps = instance.getVMTopologyTemplates();

            for (NodeTemplateMap vmTopologyMap : vmTopologiesMaps) {
                List<Credential> toscaCredentials = instance.getCredentialsFromVMTopology(vmTopologyMap);
                assertNotNull(toscaCredentials);
            }

            vmTopologies = instance.getVMTopologyTemplates();
            ToscaTemplate toscaTemplateWithInterface = null;
            Provisioner provisioner = new Provisioner();
            provisioner.setName("CloudsStorm");
            provisioner.setDescription("Interface for VM topology management with "
                    + "CloudsStorm. More at https://cloudsstorm.github.io/");
            provisioner.setToscaInterfaceType(CLOUD_STORM_INTERFACE);
            String operation = "provision";

//            for (NodeTemplateMap vmTopologyMap : vmTopologies) {
//                Map<String, Object> provisionInterface = instance.getProvisionInterface(provisioner, operation);
//                List<String> objects = new ArrayList<>();
//                objects.add("subtopology");
//                String key = provisionInterface.keySet().iterator().next();
//                Map<String, Object> provisionOperation = (Map<String, Object>) provisionInterface.get(key);
//                Map<String, Object> operationMap = (Map<String, Object>) provisionOperation.get(operation);
//                List<Map<String, Object>> inputs = (List<Map<String, Object>>) operationMap.get("inputs");
//                for (Map<String, Object> input : inputs) {
//                    if (input.containsKey("objects")) {
//                        input.put("objects", objects);
//                        break;
//                    }
//                }
//                vmTopologyMap = instance.setProvisionerInterfaceInVMTopology(vmTopologyMap, provisionInterface);
//                toscaTemplateWithInterface = instance.setNodeInToscaTemplate(toscaTemplatea2TVMopologies, vmTopologyMap);
////            }
//            instance.uploadToscaTemplate(toscaTemplateWithInterface);
//            topology_1 = toscaTemplateWithCredentials.getTopologyTemplate().getNodeTemplates().get("topology_1");
//
//            topology = toscaTemplateWithCredentials.getTopologyTemplate().getNodeTemplates().get("topology");
            instance.uploadToscaTemplate(toscaTemplatea2TVMopologies);
        }
    }

    /**
     * Test of uploadToscaTemplate method, of class ToscaHelper.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testUploadToscaTemplate() throws Exception {
        if (serviceUp) {
            System.out.println("uploadToscaTemplate");
            instance.uploadToscaTemplate(toscaTemplatea2TVMopologies);
            assertTrue(true);
        }

    }

    /**
     * Test of getVMNumOfCores method, of class ToscaHelper.
     */
    @Test
    public void testGetVMNumOfCores() throws Exception {
        if (serviceUp) {
            System.out.println("getVMNumOfCores");
            instance.uploadToscaTemplate(provisionedToscaTemplate);
            List<NodeTemplateMap> vmTopologyTemplatesMap = instance.getVMTopologyTemplates();
            for (NodeTemplateMap nodeTemplateMap : vmTopologyTemplatesMap) {
                List<NodeTemplateMap> vmTemplatesMap = instance.getTemplateVMsForVMTopology(nodeTemplateMap);
                for (NodeTemplateMap vmMap : vmTemplatesMap) {
                    Double result = instance.getVMNumOfCores(vmMap);
                    assertNotNull(result);
                }
            }
        }
    }

    /**
     * Test of getVMNMemSize method, of class ToscaHelper.
     */
    @Test
    public void testGetVMNMemSize() throws Exception {
        if (serviceUp) {
            System.out.println("getVMNMemSize");
            instance.uploadToscaTemplate(provisionedToscaTemplate);
            List<NodeTemplateMap> vmTopologyTemplatesMap = instance.getVMTopologyTemplates();
            for (NodeTemplateMap nodeTemplateMap : vmTopologyTemplatesMap) {
                List<NodeTemplateMap> vmTemplatesMap = instance.getTemplateVMsForVMTopology(nodeTemplateMap);
                for (NodeTemplateMap vmMap : vmTemplatesMap) {
                    Double result = instance.getVMNMemSize(vmMap);
                    assertNotNull(result);
                }
            }
        }
    }

    /**
     * Test of getVMNDiskSize method, of class ToscaHelper.
     */
    @Test
    public void testGetVMNDiskSize() throws Exception {
        if (serviceUp) {
            System.out.println("getVMNDiskSize");
            instance.uploadToscaTemplate(provisionedToscaTemplate);
            List<NodeTemplateMap> vmTopologyTemplatesMap = instance.getVMTopologyTemplates();
            for (NodeTemplateMap nodeTemplateMap : vmTopologyTemplatesMap) {
                List<NodeTemplateMap> vmTemplatesMap = instance.getTemplateVMsForVMTopology(nodeTemplateMap);
                for (NodeTemplateMap vmMap : vmTemplatesMap) {
                    Double result = instance.getVMNDiskSize(vmMap);
                    assertNotNull(result);
                }
            }
        }
    }

    /**
     * Test of getVMNOSDistro method, of class ToscaHelper.
     */
    @Test
    public void testGetVMNOS() throws Exception {
        if (serviceUp) {
            System.out.println("getVMNOS");
            instance.uploadToscaTemplate(provisionedToscaTemplate);
            List<NodeTemplateMap> vmTopologyTemplatesMap = instance.getVMTopologyTemplates();
            for (NodeTemplateMap nodeTemplateMap : vmTopologyTemplatesMap) {
                List<NodeTemplateMap> vmTemplatesMap = instance.getTemplateVMsForVMTopology(nodeTemplateMap);
                for (NodeTemplateMap vmMap : vmTemplatesMap) {
                    String result = instance.getVMNOSDistro(vmMap);
                    assertNotNull(result);
                }
            }
        }
    }

    /**
     * Test of getTopologyDomain method, of class ToscaHelper.
     */
    @Test
    public void testGetTopologyDomain() throws Exception {
        if (serviceUp) {
            System.out.println("getTopologyDomain");
            instance.uploadToscaTemplate(provisionedToscaTemplate);
            for (NodeTemplateMap nodeTemplateMap : instance.getVMTopologyTemplates()) {
                String result = instance.getTopologyDomain(nodeTemplateMap);
                assertNotNull(result);
            }
        }
    }

    /**
     * Test of getTopologyProvider method, of class ToscaHelper.
     */
    @Test
    public void testGetTopologyProvider() throws Exception {
        if (serviceUp) {
            System.out.println("getTopologyProvider");
            instance.uploadToscaTemplate(provisionedToscaTemplate);
            for (NodeTemplateMap nodeTemplateMap : instance.getVMTopologyTemplates()) {
                String result = instance.getTopologyProvider(nodeTemplateMap);
                assertNotNull(result);
            }
        }

    }

    /**
     * Test of setCredentialsInVMTopology method, of class ToscaHelper.
     */
    @Test
    public void testSetCredentialsInVMTopology() throws Exception {

        if (serviceUp) {
            toscaTemplateWithCredentials = null;
            instance.uploadToscaTemplate(provisionedToscaTemplate);
            System.out.println("setCredentialsInVMTopology");
            List<NodeTemplateMap> vmTopologies = instance.getVMTopologyTemplates();

            for (NodeTemplateMap vmTopologyMap : vmTopologies) {
                String provider = instance.getTopologyProvider(vmTopologyMap);

                Credential credential = new Credential();
                credential.setCloudProviderName(provider);
                credential.setProtocol("protocol");
                Map<String, String> keys = new HashMap<>();
                keys.put("key1", "eeeeeeavovfouirveiuvbepuyb8rwqovd8boacbdbvwy8oqry7f08r3euadinanzxcjc078yn0183xoqedw");
                credential.setKeys(keys);
                credential.setToken("ijwbfciweubfriw");
                credential.setTokenType("passwrd");
                credential.setUser("user");
                vmTopologyMap = instance.setCredentialsInVMTopology(vmTopologyMap, credential);
            }
        }
    }

    /**
     * Test of getVMTopologyUser method, of class ToscaHelper.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetVMTopologyUser() throws Exception {
        if (serviceUp) {
            System.out.println("getVMTopologyUser");
            instance.uploadToscaTemplate(provisionedToscaTemplate);
            String result = instance.getVMTopologyUser();
            assertEquals("vm_user", result);

        }
    }

    /**
     * Test of getKeyPairsFromVM method, of class ToscaHelper.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetKeyPairsFromVM() throws Exception {
        if (serviceUp) {
            System.out.println("getKeyPairsFromVM");
            instance.uploadToscaTemplate(provisionedToscaTemplate);
            KeyPair keyPair;
            List<NodeTemplateMap> vmTopologyTemplatesMap = instance.getVMTopologyTemplates();
            assertNotNull(vmTopologyTemplatesMap);
            for (NodeTemplateMap nodeTemplateMap : vmTopologyTemplatesMap) {
                assertNotNull(nodeTemplateMap);
                List<NodeTemplateMap> vmTemplatesMap = instance.getTemplateVMsForVMTopology(nodeTemplateMap);
                assertNotNull(vmTemplatesMap);
                for (NodeTemplateMap vmMap : vmTemplatesMap) {
                    assertNotNull(vmMap);
                    assertNotNull(vmMap.getNodeTemplate());
                    keyPair = instance.getKeyPairsFromVM(vmMap.getNodeTemplate());
                    assertNotNull(keyPair);
                }
            }
        }
    }

    /**
     * Test of cloudStormStatus2NodeState method, of class ToscaHelper.
     */
    @Test
    public void testCloudStormStatus2NodeState() {
        System.out.println("cloudStormStatus2NodeState");
        for (CloudsStormSubTopology.StatusEnum value : CloudsStormSubTopology.StatusEnum.values()) {
            Constants.NODE_STATES result = ToscaHelper.cloudStormStatus2NodeState(value);
            if (value.equals(CloudsStormSubTopology.StatusEnum.FRESH)) {
                assertNull(result);
            } else {
                assertEquals(value.toString().toUpperCase(), result.toString().toUpperCase());
            }
        }
    }

    /**
     * Test of getNodeCurrentState method, of class ToscaHelper.
     *
     * @throws java.io.IOException
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws nl.uva.qcdis.sdia.sure.tosca.client.ApiException
     */
    @Test
    public void testGetNodeCurrentState() throws IOException, JsonProcessingException, ApiException {
        if (serviceUp) {
            System.out.println("getNodeCurrentState");
            instance.uploadToscaTemplate(provisionedToscaTemplate);
            NodeTemplateMap node = instance.getVMTopologyTemplates().get(0);
            Constants.NODE_STATES expResult = Constants.NODE_STATES.RUNNING;
            Constants.NODE_STATES result = instance.getNodeCurrentState(node);
            assertEquals(expResult, result);
        }

    }

    /**
     * Test of setNodeCurrentState method, of class ToscaHelper.
     *
     * @throws java.io.IOException
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws nl.uva.qcdis.sdia.sure.tosca.client.ApiException
     */
    @Test
    public void testSetNodeCurrentState() throws IOException, JsonProcessingException, ApiException {
        if (serviceUp) {
            System.out.println("setNodeCurrentState");
            instance.uploadToscaTemplate(provisionedToscaTemplate);
            NodeTemplateMap node = instance.getVMTopologyTemplates().get(0);
            Constants.NODE_STATES nodeState = Constants.NODE_STATES.DELETED;
            NodeTemplateMap result = instance.setNodeCurrentState(node, nodeState);
            assertEquals(instance.getNodeCurrentState(node), nodeState);

            instance.setNodeCurrentState(node, null);
        }

    }

    /**
     * Test of NodeDesiredState2CloudStormOperation method, of class
     * ToscaHelper.
     */
    @Test
    public void testNodeDesiredState2CloudStormOperation() {
        System.out.println("NodeDesiredState2CloudStormOperation");
        Constants.NODE_STATES nodeDesiredState = Constants.NODE_STATES.RUNNING;
        OpCode.OperationEnum expResult = OpCode.OperationEnum.PROVISION;
        OpCode.OperationEnum result = ToscaHelper.NodeDesiredState2CloudStormOperation(nodeDesiredState);
        assertEquals(expResult, result);

        nodeDesiredState = Constants.NODE_STATES.DELETED;
        expResult = OpCode.OperationEnum.DELETE;
        result = ToscaHelper.NodeDesiredState2CloudStormOperation(nodeDesiredState);
        assertEquals(expResult, result);

        nodeDesiredState = Constants.NODE_STATES.STOPPED;
        expResult = OpCode.OperationEnum.STOP;
        result = ToscaHelper.NodeDesiredState2CloudStormOperation(nodeDesiredState);
        assertEquals(expResult, result);

        nodeDesiredState = Constants.NODE_STATES.STARTED;
        expResult = OpCode.OperationEnum.START;
        result = ToscaHelper.NodeDesiredState2CloudStormOperation(nodeDesiredState);
        assertEquals(expResult, result);

    }

    /**
     * Test of nodeCurrentState2CloudStormStatus method, of class ToscaHelper.
     */
    @Test
    public void testNodeCurrentState2CloudStormStatus() {
        System.out.println("nodeCurrentState2CloudStormStatus");
        Constants.NODE_STATES currentState = Constants.NODE_STATES.CONFIGURED;
        CloudsStormSubTopology.StatusEnum expResult = null;
        CloudsStormSubTopology.StatusEnum result = ToscaHelper.nodeCurrentState2CloudStormStatus(currentState);
        assertEquals(expResult, result);
    }

    public static String readContentsFromURL(String address) throws IOException {
        URL url = new URL(address);
        StringBuilder contents = new StringBuilder();
        try (BufferedReader read = new BufferedReader(
                new InputStreamReader(url.openStream()))) {
            String i;
            while ((i = read.readLine()) != null) {
                contents.append(i);
            }
        }
        return contents.toString();
    }

}
