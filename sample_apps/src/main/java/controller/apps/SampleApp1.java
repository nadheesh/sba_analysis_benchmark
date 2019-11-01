/*
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
package controller.apps;

import controller.HttpClient;
import model.Response;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;


public class SampleApp1 implements SampleApps {

    //    @Value("${prime.server.address}")
    private final String primeAddress = "prime";
    //    @Value("${prime.server.port}")
    private final Integer primePort = 9002;
    //    @Value("${echo.server.address}")
    private final String echoAddress = "echo";
    //    @Value("${echo.server.port}")
    private final Integer echoPort = 9000;
    private HttpClient client;
    private AppMonitor monitor;


    public SampleApp1(AppMonitor monitor) {
        this.client = new HttpClient();
        this.monitor = monitor;

        String[] appNames = Arrays.stream(API.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        monitor.registerApps(appNames);
        monitor.startStats(getAPIIndex(API.SAMPLE_APP));
    }

    private int getAPIIndex(API apiName) {
        return apiName.ordinal();
    }

    public ResponseEntity<String> execute(String[] params, String[] values) throws IOException, URISyntaxException {

        long startAPP, startPrime, startEcho;

        startAPP = monitor.startStats(getAPIIndex(API.SAMPLE_APP));
        startPrime = monitor.startStats(getAPIIndex(API.PRIME_APP));
        // perform the first api call
        Response response = client.sendGet(
                primeAddress,
                primePort,
                "prime",
                new String[]{params[0]},
                new String[]{values[0]}
        );
        monitor.updateStats(getAPIIndex(API.PRIME_APP), startPrime);

        if (response.getStatusCode() == 200) {
            startEcho = monitor.startStats(getAPIIndex(API.ECHO_APP));
            response = client.sendGet(echoAddress, echoPort, "echo", new String[]{params[1]},
                    new String[]{values[1] + " " + response.getResponseData()});
            monitor.updateStats(getAPIIndex(API.ECHO_APP), startEcho);

            monitor.updateStats(getAPIIndex(API.SAMPLE_APP), startAPP);
            if (response.getStatusCode() == 200) {
                return ResponseEntity.accepted().body(response.getResponseData());
            } else {
                return ResponseEntity.badRequest().body("echo api failed with code :" + response.getStatusCode());
            }
        } else {
            monitor.updateStats(getAPIIndex(API.SAMPLE_APP), startAPP);
            return ResponseEntity.badRequest().body("prime api failed with code :" + response.getStatusCode());
        }
    }

    public enum API {SAMPLE_APP, PRIME_APP, ECHO_APP}
}
