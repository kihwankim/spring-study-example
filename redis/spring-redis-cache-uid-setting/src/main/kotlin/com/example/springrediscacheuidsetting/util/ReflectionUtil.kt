package com.example.springrediscacheuidsetting.util

import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import java.lang.reflect.Method

object ReflectionUtil {

    fun getAnnotationMethod(packageName: String, annotationClassType: Class<out Annotation>): Set<Method> {
        val reflections = Reflections(ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(packageName)).setScanners(Scanners.MethodsAnnotated))
        return reflections.getMethodsAnnotatedWith(annotationClassType)
    }
}