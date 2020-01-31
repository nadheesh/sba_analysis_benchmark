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
public class SortApp {

    @Value("${sort.server.address}")
    private String sortAddress;
    @Value("${sort.server.port}")
    private Integer sortPort;

    private AppMonitor monitor;


    @Autowired
    private RestTemplate restTemplate;

    public SortApp(AppMonitor monitor) {
        this.monitor = monitor;

        String[] appNames = Arrays.stream(API.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        monitor.registerApps(appNames);
        monitor.startStats(getAPIIndex(API.SORT_APP));
    }


    private int getAPIIndex(API apiName) {
        return apiName.ordinal();
    }

    public ResponseEntity<String> execute(String[] params, String[] values) {
        long startSort;

        String sortURL = "http://" + sortAddress + ":" + sortPort + "/sort?" + params[0] + "=" + values[0];
        startSort = monitor.startStats(getAPIIndex(API.SORT_APP));
        // performs the first api call
        ResponseEntity<String> response = this.restTemplate.getForEntity(sortURL, String.class);
        monitor.updateStats(getAPIIndex(API.SORT_APP), startSort);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.accepted().body(response.getBody());
        } else {
            return ResponseEntity.badRequest().body("sort api failed with code :" + response.getStatusCode());
        }
    }

    public enum API {SORT_APP}
}
