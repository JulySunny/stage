package com.gabriel.stage.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Gabriel
 * @date 2020-01-08
 * @description  自定义SpringMvc转换器 解决数据转换问题（例如BigDecimal转换为String,解决精度问题）
 * 注意：springboot2.x以后继承 WebMvcConfigurationSupport 会覆盖"所有的WebMvc默认的配置,比如WebMvcConfigure",因此不推荐该中方法
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    /**
     * Date格式化字符串
     */
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * DateTime格式化字符串
     */
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * Time格式化字符串
     */
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //super.configureMessageConverters(converters);
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = converter.getObjectMapper();

        // 反序列化失败
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // long 转换为字符串
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        // 浮点型使用字符串
        simpleModule.addSerializer(Double.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Double.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);

        // java8 时间格式化
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATETIME_FORMAT));
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMAT));
        simpleModule.addSerializer(LocalTime.class, new LocalTimeSerializer(TIME_FORMAT));

        objectMapper.registerModule(simpleModule);

        // 为mapper注册一个带有SerializerModifier的Factory，处理null值
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
                //CustomizeBeanSerializerModifier 自定义序列化修改器
                .withSerializerModifier(new CustomizeBeanSerializerModifier()));

        // 处理中文乱码问题
        converter.setSupportedMediaTypes(ImmutableList.of(MediaType.APPLICATION_JSON_UTF8));

        converter.setObjectMapper(objectMapper);
        converters.add(converter);
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    /** fastJson序列化 */
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(new SerializerFeature[]{SerializerFeature.PrettyFormat, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCheckSpecialChar});
//        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<MediaType> fastMediaTypes = new ArrayList();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        fastConvert.setSupportedMediaTypes(fastMediaTypes);
//        fastConvert.setFastJsonConfig(fastJsonConfig);
//
//    }
}
