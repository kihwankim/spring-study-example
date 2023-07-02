package com.example.springrediscacheuidsetting.global.annotation;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Cacheable
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JaCustomAnnotation {

    @AliasFor(annotation = Cacheable.class)
    String[] value() default {};

    @AliasFor(annotation = Cacheable.class)
    String[] cacheNames() default {};

    //
    @AliasFor(annotation = Cacheable.class)
    String keyGenerator() default "";

    @AliasFor(annotation = Cacheable.class)
    String cacheManager() default "";


    @AliasFor(annotation = Cacheable.class)
    String cacheResolver() default "";

    @AliasFor(annotation = Cacheable.class)
    String condition() default "";

    @AliasFor(annotation = Cacheable.class)
    String unless() default "";

    @AliasFor(annotation = Cacheable.class)
    boolean sync() default false;

    @AliasFor(annotation = Cacheable.class)
    String key() default "";

    Class<?>[] serialTypes() default {};
}
