package io.literpc.transport;


import io.literpc.core.url.URL;

/**
 * @author kevin Pu
 */
public interface Transporter {

    Server bind(URL url);

    Client connect(URL url);
}
