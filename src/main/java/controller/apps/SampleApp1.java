package controller.apps;/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import controller.HttpClient;
import model.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;


public class SampleApp1 implements SampleApps {

    private static final String SAMPLE_APP = "app1";
    private static final String PRIME_APP = "prime";
    private static final String ECHO_APP = "echo";

    @Value("${prime.server.address}")
    private String primeAddress;
    @Value("${prime.server.port}")
    private Integer primePort;

    @Value("${echo.server.address}")
    private String echoAddress;
    @Value("${echo.server.port}")
    private Integer echoPort;


    private HttpClient client;
    private AppMonitor monitor;

    public SampleApp1(AppMonitor monitor) {
        this.client = new HttpClient();
        this.monitor = monitor;

        monitor.registerApps(new String[]{SAMPLE_APP, PRIME_APP, ECHO_APP});
        monitor.startStats(SAMPLE_APP);
    }

    public ResponseEntity<String> execute(String[] params, String[] values) throws IOException, URISyntaxException {
        monitor.startStats(PRIME_APP);
        // perform the first api call
        Response response = client.sendGet(
                primeAddress,
                primePort,
                "prime",
                new String[]{params[0]},
                new String[]{values[0]}
        );
        monitor.updateStats(PRIME_APP);

        if (response.getStatusCode() == 200) {
            monitor.startStats(ECHO_APP);
            response = client.sendGet(echoAddress, echoPort, "echo", new String[]{params[1]},
                    new String[]{values[1] + " " + response.getResponseData()});
            monitor.updateStats(ECHO_APP);

            monitor.updateStats(SAMPLE_APP);
            if (response.getStatusCode() == 200) {
                return ResponseEntity.accepted().body(response.getResponseData());
            } else {
                return ResponseEntity.badRequest().body("echo api failed with code :" + response.getStatusCode());
            }
        } else {
            monitor.updateStats(SAMPLE_APP);
            return ResponseEntity.badRequest().body("prime api failed with code :" + response.getStatusCode());
        }
    }
}
