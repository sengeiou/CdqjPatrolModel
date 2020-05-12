package com.cdqj.cdqjpatrolandroid.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by lyf on 2017/7/11.
 * 反序列化时NULL替换为空串
 */
public class GsonUtils {

    public static GsonBuilder gsonBuilder = new GsonBuilder()
            //.setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .enableComplexMapKeySerialization()
            .addSerializationExclusionStrategy(new IgnoreStrategy())
            .registerTypeAdapter(String.class, new TypeAdapter<String>() {
                @Override
                public void write(JsonWriter out, String value) throws IOException {
                    if (value == null) {
                        // 序列化时将 null 转为 ""
                        out.value("");
                    } else {
                        out.value(value);
                    }
                }

                @Override
                public String read(JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.NULL) {
                        in.nextNull();
                        return null;
                    }
                    String str = in.nextString();
                    // 反序列化时将 "" 转为 null
                    if ("".equals(str)) {
                        return null;
                    } else {
                        return str;
                    }
                }

            });
}
