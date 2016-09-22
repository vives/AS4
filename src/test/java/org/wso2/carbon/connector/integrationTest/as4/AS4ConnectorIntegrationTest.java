/*
* Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* WSO2 Inc. licenses this file to you under the Apache License,
* Version 2.0 (the "License"); you may not use this file except
* in compliance with the License.
* You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.connector.integrationTest.as4;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Integration test class for file connector
 */
public class AS4ConnectorIntegrationTest extends ConnectorIntegrationTestBase {

    private final Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();
    private Map<String, String> apiRequestHeadersMap = new HashMap<String, String>();

    /**
     * Set up the environment.
     */
    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        init("as4-connector-1.0.0");
        esbRequestHeadersMap.put("Content-Type", "application/json");
        esbRequestHeadersMap.put("Accept", "application/json");

        apiRequestHeadersMap.put("Content-Type", "application/json");
       /* apiRequestHeadersMap.put("Accept", "application/json");*/
    }

    /**
     * Positive test case for getMessage with mandatory parameters.
     */
    @Test(groups = {"wso2.esb"}, description = "as4 {getMessage} integration test with mandatory parameters")
    public void testGetMessageWithMandatoryParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:getMessage");
        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "getMessage_mandatory.json");

        String apiEndPoint = connectorProperties.getProperty("endpointUrl");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().toString(), apiRestResponse.getBody().toString());
    }

    /**
     * Negative test case for getMessage method with mandatory parameters.
     */
    @Test(groups = {"wso2.esb"}, description = "as4 {getMessage} integration test with mandatory parameters")
    public void testGetMessageWithNegativeCase() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:getMessage");
        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "getMessage_negative.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
    }

    /**
     * Positive test case for sendMessage method with mandatory parameters.
     */
    @Test(groups = {"wso2.esb"}, description = "as4 {sendMessage} integration test with mandatory parameters")
    public void testSendMessageWithMandatoryParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:sendMessage");
        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "sendMessage_mandatory.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 201);
        Assert.assertEquals(true, esbRestResponse.getBody().toString().contains("true"));
    }

    /**
     * Negative test case for sendMessage method with mandatory parameters.
     */
    @Test(groups = {"wso2.esb"}, description = "as4 {sendMessage} integration test with mandatory parameters")
    public void testSendMessageWithNegativeCase() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:sendMessage");
        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "sendMessage_negative.json");
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 404);
    }
}
