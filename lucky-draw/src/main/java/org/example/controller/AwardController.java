package org.example.controller;


import org.example.model.Award;
import org.example.model.User;
import org.example.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/award")
public class AwardController {
    @Autowired
    private AwardService awardService;

    @PostMapping("/add")
    public Object add(@RequestBody Award award,HttpSession session){
        User user = (User) session.getAttribute("user");
        award.setSettingId(user.getSettingId());
        int n = awardService.add(award);
        return null;
    }

    @PostMapping("/update")
    public Object update(@RequestBody Award award, HttpSession session){
        int n = awardService.add(award);
        return null;
    }

    @GetMapping("/delete/{id}")
    public Object delete(@PathVariable Integer id){
        int n = awardService.delete(id);
        return null;

    }}
