package com.dailynews.dailynews;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Administrator on 2017/5/7.
 */
class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;
    private final Gson gson;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.adapter = adapter;
        this.gson = gson;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        Reader reader = value.charStream();
        value.string();
        try {
            return gson.fromJson(reader, new TypeToken<MostPopular>() {
            }.getType());
        } catch (Exception ex) {
            return gson.fromJson(reader, new TypeToken<MostPopular<String>>() {
            }.getType());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}