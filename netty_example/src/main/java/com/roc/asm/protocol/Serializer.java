package com.roc.asm.protocol;

import com.google.gson.Gson;
import com.roc.asm.message.Message;

import java.io.*;
import java.nio.charset.StandardCharsets;

public interface Serializer {
    <T> T deserialize(Class<T> clazz, byte[] bytes);
    <T> byte[] serialize(T object);
    enum Algorithm implements Serializer{

        Java{
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (T) ois.readObject();
                } catch (Exception e) {
                    throw new RuntimeException("反序列化失败",e);
                }
            }

            @Override
            public <T> byte[] serialize(T object) {

                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(object);
                    return bos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("序列化失败",e);
                }
            }
        },

        Json{
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                String json = new String(bytes, StandardCharsets.UTF_8);
                return new Gson().fromJson(json,clazz);
            }

            @Override
            public <T> byte[] serialize(T object) {
                return new Gson().toJson(object).getBytes(StandardCharsets.UTF_8);
            }
        }

    }
}
