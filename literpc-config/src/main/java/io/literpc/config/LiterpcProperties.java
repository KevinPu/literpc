package io.literpc.config;

/**
 * @author kevin Pu
 */
public class LiterpcProperties {

    /**
     * application name
     */
    private String appname;
    /**
     * registry address
     */
    private String registry;
    /**
     * communication protocol
     */
    private String protocol;
    /**
     * listen port, default 20800
     */
    private int port = 20800;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "LiterpcConfig{" +
                "appname='" + appname + '\'' +
                ", registry='" + registry + '\'' +
                ", protocol='" + protocol + '\'' +
                ", port=" + port +
                '}';
    }


}
