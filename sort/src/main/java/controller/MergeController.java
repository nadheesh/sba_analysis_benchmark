package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@Controller

public class MergeController {

    @GetMapping("/sort")
    @ResponseBody
    public String merge(@RequestParam(name = "size") String size) {
        int int_number;
        try {
            int_number = Integer.parseInt(size);

        } catch (Exception ex) {

            return "Integer argument is expected";
        }
        double[] result = new MergeSort().sortNNumbers(int_number);
//        String response = Arrays.toString(result);

        return "done";
    }

}