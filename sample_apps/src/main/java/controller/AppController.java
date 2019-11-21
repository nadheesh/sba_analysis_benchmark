package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.apps.AppMonitor;
import controller.apps.SampleApp1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class AppController {

    private Logger logger = LoggerFactory.getLogger(AppController.class);

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
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/get_stats")
    @ResponseBody
    public void monitorStats(@RequestParam(name = "filename") String filename) {

        if (monitor == null) {
            logger.error("no monitor registered");
            return;
        }

        try {

            ObjectMapper mapper = new ObjectMapper();
            FileOutputStream outputStream = new FileOutputStream("temp/" + filename + ".json");

            String jsonString = mapper.writeValueAsString(monitor.getApiStats());
            outputStream.write(jsonString.getBytes());
            outputStream.close();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @GetMapping("/pop_stats")
    @ResponseBody
    public void popStats(@RequestParam(name = "filename") String filename) {

        if (monitor == null) {
            logger.error("no monitor registered");
            return;
        }

        try {

            ObjectMapper mapper = new ObjectMapper();
            FileOutputStream outputStream = new FileOutputStream("temp/" + filename + ".json");

            String jsonString = mapper.writeValueAsString(monitor.getApiStats());
            monitor.clearStats();
            outputStream.write(jsonString.getBytes());
            outputStream.close();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
