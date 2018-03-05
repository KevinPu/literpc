package io.literpc.demo.provider.service.impl;

import io.literpc.config.annotation.RpcService;
import io.literpc.demo.api.service.HelloService;

/**
 * @author kevin Pu
 */
@RpcService
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "hello : " + name;
    }
}
