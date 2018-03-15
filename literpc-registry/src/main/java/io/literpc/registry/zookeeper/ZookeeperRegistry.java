package io.literpc.registry.zookeeper;

import io.literpc.core.url.URL;
import io.literpc.registry.Registry;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author kevin Pu
 */
public class ZookeeperRegistry implements Registry {

    @Override
    public void register(URL url) {
        ZkClient zkClient = new ZkClient(url.getAddress(), ZKConstant.ZK_SESSION_TIMEOUT);

        if (!zkClient.exists(ZKConstant.ZK_ROOT_PATH)) {
            zkClient.createPersistent(ZKConstant.ZK_ROOT_PATH);
        }

        zkClient.createPersistent(url.getPath(), true);

        zkClient.createEphemeral(url.getAddress());
    }
}
