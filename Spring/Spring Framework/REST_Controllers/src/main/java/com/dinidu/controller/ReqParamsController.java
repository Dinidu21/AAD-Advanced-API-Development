package com.dinidu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reqparams")
public class ReqParamsController {

/*   This controller can be used to handle request parameters in various endpoints.
     You can define methods here to handle specific request parameters as needed.
*/

     @GetMapping("/para")
     public String exampleMethod(@RequestParam("param") String param) {
         return "Received parameter: " + param;
     }
}
