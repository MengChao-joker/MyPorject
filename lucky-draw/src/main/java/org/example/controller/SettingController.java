package org.example.controller;


import org.example.model.Award;
import org.example.model.Setting;
import org.example.model.User;
import org.example.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;
    @GetMapping("/query")
    public Object query(HttpSession session){

        //设置好setting的各个属性（前端所需要的）
        User user = (User) session.getAttribute("user");
        Setting setting = settingService.query(user.getId());
        setting.setUser(user);
        return setting;
    }

    @GetMapping("/update")
    public Object update(@RequestParam Integer batchNumber,HttpSession session){
        User user = (User) session.getAttribute("user");
        Setting setting = new Setting();
        setting.setBatchNumber(batchNumber);
        setting.setId(user.getSettingId());
        settingService.update(setting);
        return null;
    }

}
