package com.minshenglife.controller;

import com.minshenglife.Account;
import com.minshenglife.BljClient;
import com.minshenglife.Device;
import com.minshenglife.entity.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    @GetMapping("")
    public String index() {
        return "pages/index";
    }

    @PostMapping("")
    @ResponseBody
    public ResultBean get(@RequestParam(value = "server") String server,
                          @RequestParam("account") String account,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "force", required = false) boolean force) {
        ResultBean rb = new ResultBean();
        //1.发起请求，给xx主机新增xx账号密码  post
        //2.后台判断是否能够找到唯一一台主机
        //2.1 找不到：告诉用户无此主机  return resultBean status=false,message=无此主机
        //2.2 找到唯一一条记录
        //2.2.1 判断提交的账号是否已有密码  return resultBean status=true,message=该账号已有密码，是否覆盖？
        //2.2.2 不覆盖：到此结束
        //2.2.3 覆盖：用新密码覆盖老密码  重新发起一次post请求，带一个参数，force=true,后台直接更新密码
        //2.2.3.1 更新完密码后，return resultBean status=true,message=null
        Device device = null;
        //两种情况获取device
        //ip
        if (server.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            device = BljClient.getDeviceByIp(server);
        } else {
            //设备名称
            device = BljClient.getDeviceByName(server);

        }

        if (device == null) {
            rb.setFailure();
            rb.setMessage("无此主机！");
        } else {
            //找到唯一一条设备
            if (BljClient.getAccountByRemote(account) == null) {
                Account account1 = new Account();
                account1.setRemote(account);
                BljClient.addAccount(account1);
                int op = 1;
                List<Object> a = new ArrayList<>();
                List<List> b = new ArrayList<>();
                a.add(BljClient.getAccountByRemote(account).getId());
                a.add("\"" + password + "\"");
                b.add(a);
                BljClient.modifyAccountByServiceIp(device.getIpaddr(), op, b);
                rb.setMessage("密码添加成功");
            } else {
                if (!BljClient.getAccountPasswordByIp(device.getIpaddr(), account).isEmpty()) {
                    //存在
                    if (force) {
                        int op = 3;
                        List<Object> a = new ArrayList<>();
                        List<List> b = new ArrayList<>();
                        a.add(BljClient.getAccountByRemote(account).getId());
                        a.add("\"" + password + "\"");
                        b.add(a);
                        BljClient.modifyAccountByServiceIp(device.getIpaddr(), op, b);
                    } else {
                        rb.setMessage("该账号已存在密码，是否覆盖？");
                    }
                } else {
                    //不存在
                    int op = 1;
                    List<Object> a = new ArrayList<>();
                    List<List> b = new ArrayList<>();
                    a.add(BljClient.getAccountByRemote(account).getId());
                    a.add("\"" + password + "\"");
                    b.add(a);
                    BljClient.modifyAccountByServiceIp(device.getIpaddr(), op, b);
                    rb.setMessage("密码添加成功");
                }
            }
        }
        return rb;
    }
}

