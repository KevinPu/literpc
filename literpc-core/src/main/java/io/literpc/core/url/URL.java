package io.literpc.core.url;

import java.io.Serializable;
import java.util.Map;

/**
 * @author kevin Pu
 */
public class URL implements Serializable {
    private static final long serialVersionUID = -3335663050157672764L;

    private final String protocol;

    private final String host;

    private final int port;

    private final String path;

    private final Map<String, String> parameters;

    public URL() {
        this.protocol = null;
        this.host = null;
        this.port = 0;
        this.path = null;
        this.parameters = null;
    }

    public URL(String protocol, String host, int port, String path, Map<String, String> parameters) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters;
    }

    public URL(String protocol, int port, String path) {
        this(protocol, null, port, path, null);
    }

    public String getAddress() {
        return port <= 0 ? host : host + ":" + port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
