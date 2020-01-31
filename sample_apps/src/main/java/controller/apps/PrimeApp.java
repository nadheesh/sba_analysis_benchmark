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
public class PrimeApp {

    @Value("${prime.server.address}")
    private String primeAddress;
    @Value("${prime.server.port}")
    private Integer primePort;

    private AppMonitor monitor;


    @Autowired
    private RestTemplate restTemplate;

    public PrimeApp(AppMonitor monitor) {
        this.monitor = monitor;

        String[] appNames = Arrays.stream(API.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        monitor.registerApps(appNames);
        monitor.startStats(getAPIIndex(API.PRIME_APP));
    }


    private int getAPIIndex(API apiName) {
        return apiName.ordinal();
    }

    public ResponseEntity<String> execute(String[] params, String[] values) {
        long startPrime;

        String primeURL = "http://" + primeAddress + ":" + primePort + "/prime?" + params[0] + "=" + values[0];
        startPrime = monitor.startStats(getAPIIndex(API.PRIME_APP));
        // performs the first api call
        ResponseEntity<String> response = this.restTemplate.getForEntity(primeURL, String.class);
        monitor.updateStats(getAPIIndex(API.PRIME_APP), startPrime);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.accepted().body(response.getBody());
        } else {
            return ResponseEntity.badRequest().body("prime api failed with code :" + response.getStatusCode());
        }
    }

    public enum API {PRIME_APP}
}
