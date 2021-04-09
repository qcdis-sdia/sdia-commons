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

import com.macasaet.fernet.Key;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author S. Koulouzis
 */
public class ConverterTest {

    public ConverterTest() {
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
     * Test of decryptString method, of class Converter.
     * @throws java.lang.Exception
     */
    @Test
    public void testEncryptDecryptString() throws Exception {
        System.out.println("decryptString");
        String contents = "this is very important information";
        String secret = "sn7M5MwhUDi7GviSU-0YtNDpgXlKvA4NYHGKBvzToas=";//"2vSfzOCCIj6__YYWiJDaY1n0CcwUCsPIDJVTkILDhc93JgYrYhUrqSAFNDdnB9XlwJgqEzLbxFU_YZ2PBwKX2Q==";//Key.generateKey().serialise();
        String expResult = contents;
        String enc = Converter.encryptString(contents, secret);
        System.out.println("Encrypted String: " + enc);
        String result = Converter.decryptString(enc, secret);
        assertEquals(expResult, result);
    }

}
