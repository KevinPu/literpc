package io.literpc.demo.provider;

import io.literpc.config.LiterpcProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderAppTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        LiterpcProperties literpcProperties = applicationContext.getBean(LiterpcProperties.class);
        System.out.println(literpcProperties);
    }

}
