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


public class SampleApp2 {

//    private APIStore apiStore;

    public SampleApp2(){
    }

//    public ResponseEntity<String> execute(int value, String message) throws IOException, URISyntaxException {
//        Response response = apiStore.callAPI(APIStore.API.PRIME, 9002,
//                new String[]{"number"}, new String[]{String.valueOf(value)});

//        if (response.getStatusCode() == 200) {
//            response = apiStore.callAPI(APIStore.API.ECHO, 9000, new String[]{"message"}, new String[]{message + response.getResponseData()});
//            if (response.getStatusCode() == 200) {
//                return ResponseEntity.accepted().body(response.getResponseData());
//            } else {
//                return ResponseEntity.badRequest().body("echo api failed with code :" + response.getStatusCode());
//            }
//        } else {
//            return ResponseEntity.badRequest().body("prime api failed with code :" + response.getStatusCode());
//        }

//        Response response = apiStore.callAPI(APIStore.API.PRIME, "prime", 9002,
//                new String[]{"number"}, new String[]{String.valueOf(value)});
//
//        if (response.getStatusCode() == 200) {
//            response = apiStore.callAPI(APIStore.API.ECHO, "echo", 9000, new String[]{"message"}, new String[]{message + response.getResponseData()});
//            if (response.getStatusCode() == 200) {
//                return ResponseEntity.accepted().body(response.getResponseData());
//            } else {
//                return ResponseEntity.badRequest().body("echo api failed with code :" + response.getStatusCode());
//            }
//        } else {
//            return ResponseEntity.badRequest().body("prime api failed with code :" + response.getStatusCode());
//        }
//    }
//        if ("true".equals(isPrime)) {
//            apiStore.callAPI(APIStore.API.MERGE, new String[]{"number"}, new String[]{String.valueOf(value)});
//        } else {
//            apiStore.callAPI(APIStore.API.ECHO, new String[]{"message"}, new String[]{message});
//        }


//        Date date = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        String strData = dateFormat.format(date);
//
//        String response = apiStore.callAPI(APIStore.API.ECHO, 9000, new String[]{"message"}, new String[]{strData});

}
