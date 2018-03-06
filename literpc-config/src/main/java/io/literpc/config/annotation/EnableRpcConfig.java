package io.literpc.config.annotation;

import io.literpc.config.LiterpcConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author kevin Pu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(LiterpcConfig.class)
@RpcComponentScan
public @interface EnableRpcConfig {
}
