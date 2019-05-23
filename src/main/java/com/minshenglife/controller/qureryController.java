package com.minshenglife.controller;

import com.minshenglife.BljClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class qureryController {


    @GetMapping("query")
    public String index() {
        return "pages/index";
    }

    @PostMapping("query")
    @ResponseBody
    public Map<String, String> get(@RequestParam(value = "server") String server,
                                   @RequestParam("account") String account) {
        if (server.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            Map<String, String> map = new HashMap<>();
            map.put("password", BljClient.getAccountPasswordByIp(server, account));
            return map;
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("password", BljClient.getAccountPasswordByIp(BljClient.getDeviceByName(server).getIpaddr(), account));
            return map;
        }
    }
}
