package io.literpc.demo.consumer.controller;

import io.literpc.config.annotation.RpcReferer;
import io.literpc.demo.api.service.HelloService;
import org.springframework.stereotype.Controller;

/**
 * @author kevin Pu
 */
@Controller
public class HelloController {

    @RpcReferer
    private HelloService helloService;

    public void sayHello() {
        String result = helloService.sayHello("Kevin Pu");

        System.out.println(result);
    }
}
