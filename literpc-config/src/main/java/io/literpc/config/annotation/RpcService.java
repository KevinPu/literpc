package io.literpc.config.annotation;

import java.lang.annotation.*;

/**
 * @author kevin Pu
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface RpcService {
}
