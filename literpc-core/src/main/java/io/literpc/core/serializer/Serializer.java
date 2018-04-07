package io.literpc.core.serializer;

/**
 * @author kevin Pu
 */
public interface Serializer {

    /**
     * 序列化
     */
    byte[] serialize(Object obj);

    /**
     * 反序列化
     */
    <T> T deserialize(byte[] bytes);
}
