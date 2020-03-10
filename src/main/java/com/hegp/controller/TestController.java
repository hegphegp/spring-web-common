package com.hegp.controller;

import com.hegp.core.domain.Response;
import com.hegp.domain.Login;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Validated
@RestController
@RequestMapping("/v1")
public class TestController {

    @PostMapping("/login")
    public Response login(@Valid Login login) {
        return Response.success();
    }

    // @Validated 加在方法前，加在参数前，无效，死亡的教训
    @GetMapping("/test")
    public Response test(@NotEmpty(message = "param1不允许为空") @RequestParam String param1) {
        return Response.success();
    }

    @GetMapping("/{id}")
    public Response id(@PathVariable(name = "id") String id,
                       @NotEmpty(message = "param1不允许为空") @RequestParam String param1) {
        return Response.success();
    }
}
