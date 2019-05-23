package com.minshenglife.controller;

import com.minshenglife.BljClient;
import com.minshenglife.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/")
public class roleController {
    @GetMapping("role")
    public String index() {
        return "pages/role";
    }

    @PostMapping("role")
    public Device get(@RequestParam(value = "server") String server) {
        Device device = BljClient.getDeviceByIp(server);
        return device;
    }
}
