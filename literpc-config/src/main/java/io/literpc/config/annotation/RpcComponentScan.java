package io.literpc.config.annotation;

import io.literpc.config.RpcComponentScanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author kevin Pu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RpcComponentScanRegistrar.class)
public @interface RpcComponentScan {
}
