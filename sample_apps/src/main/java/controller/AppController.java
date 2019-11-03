package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.apps.AppMonitor;
import controller.apps.SampleApp1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class AppController {

    @Autowired
    private SampleApp1 app;
    @Autowired
    private AppMonitor monitor;

    @GetMapping("/app1")
    @ResponseBody
    public ResponseEntity<String> executeApp1(@RequestParam(name = "message") String message, @RequestParam(name = "number") String number) {

        int value;
        try {
            value = Integer.parseInt(number);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);

        }

        try {
            return app.execute(new String[]{"number", "message"}, new String[]{String.valueOf(value), message});

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/get_stats")
    @ResponseBody
    public ResponseEntity<String> monitorStats() {
//            @RequestParam(name = "message") String message, @RequestParam(name = "number") String number) {

        if (monitor == null) {
            return ResponseEntity.badRequest().body("no monitor registered");
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonString = mapper.writeValueAsString(monitor.getApiStats());
            return ResponseEntity.accepted().body(jsonString);

        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pop_stats")
    @ResponseBody
    public ResponseEntity<String> popStats() {

        if (monitor == null) {
            return ResponseEntity.badRequest().body("no monitor registered");
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonString = mapper.writeValueAsString(monitor.getApiStats());
            monitor.clearStats();
            return ResponseEntity.accepted().body(jsonString);

        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
