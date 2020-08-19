package com.gabriel.stage.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * @author: Gabriel
 * @date: 2020/1/17 15:49
 * @description 自定义序列化修改器 处理Null值
 */


public class CustomizeBeanSerializerModifier extends BeanSerializerModifier {
    private static final List<Class<?>> BASIC_TYPE = Arrays.asList(Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, String.class, Date.class, BigDecimal.class);

    public CustomizeBeanSerializerModifier() {
    }
    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        Iterator var4 = beanProperties.iterator();

        while(var4.hasNext()) {
            BeanPropertyWriter writer = (BeanPropertyWriter)var4.next();
            Class<?> clazz = writer.getType().getRawClass();
            if (this.isArrayType(clazz)) {
                writer.assignNullSerializer(new CustomizeBeanSerializerModifier.NullArrayJsonSerializer());
            } else if (Boolean.class.equals(clazz)) {
                writer.assignNullSerializer(new CustomizeBeanSerializerModifier.NullBoolTypeJsonSerializer());
            } else if (this.isBasicType(clazz)) {
                writer.assignNullSerializer(new CustomizeBeanSerializerModifier.NullBasicTypeJsonSerializer());
            } else {
                writer.assignNullSerializer(new CustomizeBeanSerializerModifier.NullObjectJsonSerializer());
            }
        }

        return beanProperties;
    }

    private boolean isArrayType(Class<?> clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    private boolean isBasicType(Class<?> clazz) {
        return BASIC_TYPE.contains(clazz) || Temporal.class.isAssignableFrom(clazz);
    }

    public static class NullObjectJsonSerializer extends JsonSerializer<Object> {
        public NullObjectJsonSerializer() {
        }
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            if (value == null) {
                jgen.writeStartObject();
                jgen.writeEndObject();
            } else {
                jgen.writeObject(value);
            }

        }
    }

    public static class NullArrayJsonSerializer extends JsonSerializer<Object> {
        public NullArrayJsonSerializer() {
        }
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            if (value == null) {
                jgen.writeStartArray();
                jgen.writeEndArray();
            } else {
                jgen.writeObject(value);
            }

        }
    }

    public static class NullBoolTypeJsonSerializer extends JsonSerializer<Object> {
        public NullBoolTypeJsonSerializer() {
        }
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            if (value == null) {
                jgen.writeBoolean(false);
            } else {
                jgen.writeObject(value);
            }

        }
    }

    public static class NullBasicTypeJsonSerializer extends JsonSerializer<Object> {
        public NullBasicTypeJsonSerializer() {
        }
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            if (value == null) {
                jgen.writeString("");
            } else {
                jgen.writeObject(value);
            }

        }
    }
}

