package com.iguojie.rbac.controller;

import com.iguojie.rbac.common.entity.JsonResult;
import com.iguojie.rbac.context.UserProxy;
import com.iguojie.rbac.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;

@RestController
@RequestMapping(produces = {"application/json;charset=utf-8"})
public class TestController {



    @GetMapping("/hello")
    public JsonResult hello(){
        return JsonResult.success(UserProxy.getUserFromContext());
    }
    @GetMapping("/admin")
    public JsonResult admin(){
        return JsonResult.success(UserProxy.getUserFromContext());
    }
}
