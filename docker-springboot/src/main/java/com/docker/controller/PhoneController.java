package com.docker.controller;

import com.docker.entity.Phone;
import com.docker.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docker")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @PostMapping("/phone")
    public Phone createPhone(@RequestBody Phone phone){
        Phone phone1 = this.phoneService.createPhone(phone);
        return phone1;
    }

    @GetMapping("/phones")
    public List<Phone> getAllPhone(){
        return this.phoneService.getAllPhone();
    }

    @GetMapping("/hello")
    public String getHello(){
        System.out.println("hello");
        return "hello from application...";
    }

}
