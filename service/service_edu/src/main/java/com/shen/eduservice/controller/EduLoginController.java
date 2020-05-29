package com.shen.eduservice.controller;

import com.shen.commonutils.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: shenge
 * @Date: 2020-05-07 20:12
 */

@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin
public class EduLoginController {

    @PostMapping("login")
    public Result login() {
        return Result.ok().data("token", "admin");
    }

    @GetMapping("info")
    public Result info() {
        return Result.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "");
    }
}
