package com.minshenglife.controller;

import com.minshenglife.BljClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class delController {


    @GetMapping("delete")
    public String index() {
        return "pages/index";
    }

    @ResponseBody
    @PostMapping("delete")
    public String get(@RequestParam(value = "server") String server,
                      @RequestParam("account") String account) {
        int op = 2;
        List<Object> list1 = new ArrayList<>();
        List<List> list2 = new ArrayList<>();
        list1.add(BljClient.getAccountByRemote(account).getId());
        list1.add("\"\"");
        list2.add(list1);
        if (server.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            BljClient.modifyAccountByServiceIp(server, op, list2);
            return null;
        } else {
            BljClient.modifyAccountByServiceIp(BljClient.getDeviceByName(server).getIpaddr(), op, list2);
            return null;
        }
    }
}
