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

public class APIStats {

    //    private String apiName;
    private long startTime;
    private long endTime;
    private int requestCount;

//    public APIStats(String name) {
//        apiName = name;
//    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getRequestCount() {
        return requestCount;
    }

//    public String getApiName() {
//        return apiName;
//    }

    public void setStartTime() {
        if (startTime <= 0) {
            startTime = System.currentTimeMillis();
        }
    }

    public void setEndTime() {
        endTime = System.currentTimeMillis();
    }

    public void incrementRequestCount() {
        requestCount++;
    }

    public void updatedStats() {
        setEndTime();
        incrementRequestCount();
    }

    public void clearStats() {
        requestCount = 0;
    }
}
