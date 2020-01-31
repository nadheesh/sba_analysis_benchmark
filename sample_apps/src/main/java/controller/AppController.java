package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.apps.*;
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

@Controller
public class AppController {

    private Logger logger = LoggerFactory.getLogger(AppController.class);


    @Autowired
    private AppMonitor monitor;

    @Autowired
    private SampleApp1 app1;

    @GetMapping("/app1")
    @ResponseBody
    public ResponseEntity<String> executeApp1(@RequestParam(name = "message") String message, @RequestParam(name = "number") String number) {

        int value;
        try {
            value = Integer.parseInt(number);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }

        return app1.execute(new String[]{"number", "message"}, new String[]{String.valueOf(value), message});

    }

    @Autowired
    private EchoApp echo;

    @GetMapping("/echo")
    @ResponseBody
    public ResponseEntity<String> executeEchoApp(@RequestParam(name = "message") String message) {

        return echo.execute(new String[]{"message"}, new String[]{message});
    }


    @Autowired
    private SortApp sort;

    @GetMapping("/sort")
    @ResponseBody
    public ResponseEntity<String> executeSortApp(@RequestParam(name = "size") String size) {

        int value;
        try {
            value = Integer.parseInt(size);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }

        return sort.execute(new String[]{"size"}, new String[]{String.valueOf(value)});
    }

    @Autowired
    private PrimeApp prime;

    @GetMapping("/prime")
    @ResponseBody
    public ResponseEntity<String> executePrimeApp(@RequestParam(name = "number") String number) {

        int value;
        try {
            value = Integer.parseInt(number);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }

        return prime.execute(new String[]{"number"}, new String[]{String.valueOf(value)});
    }


    @Autowired
    private SampleApp2 app2;

    @GetMapping("/app2")
    @ResponseBody
    public ResponseEntity<String> executeApp2(@RequestParam(name = "size") String size, @RequestParam(name = "number") String number) {

        int value_number, value_size;
        try {
            value_number = Integer.parseInt(number);
            value_size = Integer.parseInt(size);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("size or number is not numeric");
        }

        return app2.execute(new String[]{"number", "size"}, new String[]{String.valueOf(value_number), String.valueOf(value_size)});

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
