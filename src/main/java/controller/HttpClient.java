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
package controller;

import model.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpClient {

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public Response sendGet(String host, int port, String api, String[] params, String[] values) throws IOException, URISyntaxException {

        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(host)
                .setPort(port)
                .setPath(api)
                .setParameter(params[0], values[0])
                .build();

        HttpGet request = new HttpGet(uri);

        for (int i = 0; i < params.length; i++) {
            request.addHeader(params[i], values[i]);
        }

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return new Response(response);
        }
    }
}


