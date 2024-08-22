package com.roc.asm.protocol;

import com.google.gson.*;
import com.roc.asm.message.Message;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public interface Serializer {
    <T> T deserialize(Class<T> clazz, byte[] bytes);
    <T> byte[] serialize(T object);
    enum Algorithm implements Serializer{

        Java {
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {

                try {
                    // 处理内容
                    final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                    final ObjectInputStream ois = new ObjectInputStream(bis);

                    // 转成 Message类型
                    T message = (T) ois.readObject();

                    return message;
                } catch (IOException | ClassNotFoundException e) {

                    throw new RuntimeException("反序列化算法失败！");
                }

            }

            @Override
            public <T> byte[] serialize(T object) {
//                System.out.println("对象流算法---加密");
                try {
                    // 处理内容 用对象流包装字节数组 并写入
                    ByteArrayOutputStream bos = new ByteArrayOutputStream(); // 访问数组
                    ObjectOutputStream oos = new ObjectOutputStream(bos);    // 用对象流 包装
                    oos.writeObject(object);

                    return bos.toByteArray();

                } catch (IOException e) {
                    throw new RuntimeException("序列化算法失败！", e);
                }
            }
        },

        Json{
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                // 指定 下面自定义处理类 生成 对象
                Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new Serializer.ClassCodec()).create();

                String json = new String(bytes, StandardCharsets.UTF_8);

                return gson.fromJson(json, clazz);
            }

            @Override
            public <T> byte[] serialize(T object) {
                // 指定 下面自定义处理类 生成 对象
                Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new Serializer.ClassCodec()).create();

                String json = gson.toJson(object);
                return json.getBytes(StandardCharsets.UTF_8);
            }
        }

    }


    // 针对之前报出：不支持 Class类转json的异常 做处理
    class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {
        @Override
        public Class<?> deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                String str = json.getAsString();
                return Class.forName(str);

            } catch (ClassNotFoundException e) {

                throw new JsonParseException(e);
            }
        }

        @Override
        public JsonElement serialize(Class<?> src, Type type, JsonSerializationContext jsonSerializationContext) {

            // JsonPrimitive 转化基本数据类型
            return new JsonPrimitive(src.getName());
        }
    }
}
