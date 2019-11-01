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
package model;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;

public class Response {
    private int statusCode;

    private String responseData;

    public Response(CloseableHttpResponse response) throws IOException {
        this.statusCode = response.getStatusLine().getStatusCode();
        ResponseHandler<String> handler = new BasicResponseHandler();
        this.responseData = handler.handleResponse(response);

        response.close();

    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseData() {
        return responseData;
    }
}
