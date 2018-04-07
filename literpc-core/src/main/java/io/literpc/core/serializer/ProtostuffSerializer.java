package io.literpc.core.serializer;

/**
 * @author kevin Pu
 */
public class ProtostuffSerializer implements Serializer {


    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes) {
        return null;
    }
}
