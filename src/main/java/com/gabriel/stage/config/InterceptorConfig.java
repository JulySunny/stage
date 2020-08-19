package com.gabriel.stage.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: Gabriel
 * @date: 2020/2/5 14:15
 * @description 登录拦截配置
 */
@Configuration
@Order(1)
public class InterceptorConfig implements WebMvcConfigurer, InitializingBean {
//    @Autowired
//    private AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;

    @Autowired
    private ApiIdempotentInterceptor apiIdempotentInterceptor;


    /**
     * 添加资源处理器-这里主要是添加swagger的相关资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 拦截器注册
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 接口幂等性拦截器
        registry.addInterceptor(accessLimitInterceptor);
        // 接口防刷限流拦截器
        registry.addInterceptor(apiIdempotentInterceptor)
        // 登录拦截器-验证签名

                .addPathPatterns("/**")
                //不拦截用户登录接口
                .excludePathPatterns("/user/login")
                //不拦截swagger相关的接口
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");


    }

    /**
     * 添加Cors跨域配置
     * addMapping/allowedMethods/allowedOrigins 主要用于简单请求
     * allowCredentials/allowedHeaders 主要用于非简单请求 非简单请求比简单请求的请求头字段要多
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //必填：配置支持跨域的路径 请求头字段无, 非Cors属性,属于SpringBoot配置
        registry.addMapping("/**")
                //必填：配置支持跨域请求的方法,如：GET、POST，一次性返回
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTION")
                //必填：配置允许的源 Access-Control-Allow-Origin
                .allowedOrigins("*")
                //选填: 配置是否允许发送Cookie, 用于 凭证请求
                .allowCredentials(true)
                //选填: 配置允许的自定义请求头, 用于 预检请求(这里对应的预检请求是Option请求) Access-Control-Request-Headers
                .allowedHeaders("*");
    }

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


    @Autowired(required = false)
    private ObjectMapper objectMapper;


    /**
     * 实现InitializingBean接口,重写afterPropertiesSet方法用于处理返回的数据
     * 解决数据转换问题（例如BigDecimal转换为String,解决精度问题）
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (objectMapper != null) {
            SimpleModule simpleModule = getSimpleModule();
            objectMapper.registerModule(simpleModule);
            // 为mapper注册一个带有SerializerModifier的Factory，处理null值
            objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
                    //CustomizeBeanSerializerModifier 自定义序列化修改器
                    .withSerializerModifier(new CustomizeBeanSerializerModifier()));
        }
    }

    private SimpleModule getSimpleModule() {
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

        return simpleModule;
    }
}
