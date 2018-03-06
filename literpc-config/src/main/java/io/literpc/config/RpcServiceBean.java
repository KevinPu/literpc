package io.literpc.config;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author kevin Pu
 */
public class RpcServiceBean  implements InitializingBean {
    
    @Override
    public void afterPropertiesSet() throws Exception {
        export();
    }

    private void export() {
    }
}
