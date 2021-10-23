package com.jsx.applib.utils

/**
 * Author: JackPan
 * Date: 2021-10-23
 * Time: 14:03
 * Description: 参数解析注解
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Param(val value:String)