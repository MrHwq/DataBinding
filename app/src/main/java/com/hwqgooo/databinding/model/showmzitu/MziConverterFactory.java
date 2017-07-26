package com.hwqgooo.databinding.model.showmzitu;

import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by weiqiang on 2016/8/22.
 */
public final class MziConverterFactory extends Converter.Factory {
    public MziConverterFactory() {
        super();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[]
            parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (type instanceof Class<?>) {

        }
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit
            retrofit) {
        return super.stringConverter(type, annotations, retrofit);
    }

    class MziResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private final Charset UTF_8 = Charset.forName("UTF-8");

        private final TypeAdapter<T> adapter;

        public MziResponseBodyConverter(TypeAdapter<T> adapter) {
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            return null;
        }
    }
}
