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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class EchoApp {

    @Value("${echo.server.address}")
    private String echoAddress;
    @Value("${echo.server.port}")
    private Integer echoPort;

    private AppMonitor monitor;

    @Autowired
    private RestTemplate restTemplate;

    public EchoApp(AppMonitor monitor) {
        this.monitor = monitor;

        String[] appNames = Arrays.stream(API.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        monitor.registerApps(appNames);
        monitor.startStats(getAPIIndex(API.ECHO_APP));
    }

    private int getAPIIndex(API apiName) {
        return apiName.ordinal();
    }

    public ResponseEntity<String> execute(String[] params, String[] values){
        long startEcho;

        String echoURL = "http://" + echoAddress + ":" + echoPort + "/echo?" + params[0] + "=" + values[0];
        startEcho = monitor.startStats(getAPIIndex(API.ECHO_APP));
        // performs the second api call
        ResponseEntity<String> response = this.restTemplate.getForEntity(echoURL, String.class);
        monitor.updateStats(getAPIIndex(API.ECHO_APP), startEcho);


        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.accepted().body(response.getBody());
        } else {
            return ResponseEntity.badRequest().body("echo api failed with code :" + response.getStatusCode());
        }
    }

    public enum API {ECHO_APP}
}
