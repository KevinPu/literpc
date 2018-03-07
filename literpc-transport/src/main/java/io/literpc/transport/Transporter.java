package io.literpc.transport;


import io.literpc.core.url.URL;

/**
 * @author kevin Pu
 */
public interface Transporter {

    Server create(URL url, MessageHandler handler);

    Client connect(URL url);
}
