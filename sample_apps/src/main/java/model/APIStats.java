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

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class APIStats {

    private String apiName;
    private AtomicLong startTime;
    private AtomicLong endTime;
    //    private ConcurrentLinkedQueue<Long> requestTimes;
    private ConcurrentLinkedQueue<Long> responseTimes;
    private AtomicInteger requestCount;

    public APIStats(String apiName) {
        this.apiName = apiName;
        this.startTime = new AtomicLong();
        this.endTime = new AtomicLong();
        this.requestCount = new AtomicInteger();

//        this.requestTimes = new ConcurrentLinkedQueue<>();
        this.responseTimes = new ConcurrentLinkedQueue<>();
    }

    // getters
    public String getApiName() {
        return apiName;
    }

    public AtomicLong getStartTime() {
        return startTime;
    }

    public AtomicLong getEndTime() {
        return endTime;
    }

    public AtomicInteger getRequestCount() {
        return requestCount;
    }

//    public ConcurrentLinkedQueue<Long> getRequestTimes() {
//        return requestTimes;
//    }

    public ConcurrentLinkedQueue<Long> getResponseTimes() {
        return responseTimes;
    }

    // setters (private use only)
    private void incrementRequestCount() {
        requestCount.incrementAndGet();
    }

    // update methods from here

    public long setStartTime() {
        long currentTime = System.currentTimeMillis();
        startTime.updateAndGet(value -> value <= 0 || value > currentTime ? currentTime : value);
        return currentTime;
    }

    public void updatedStats(long startTime) {
        long currentTime = System.currentTimeMillis();
        endTime.updateAndGet(value -> value < currentTime ? currentTime : value);
        responseTimes.add(currentTime - startTime);
        incrementRequestCount();
    }

    public void clearStats() {
        requestCount.set(0);
        startTime.set(0);
        endTime.set(0); // not necessary

        responseTimes.clear();
    }
}
