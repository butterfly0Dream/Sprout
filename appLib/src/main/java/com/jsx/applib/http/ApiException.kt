package com.jsx.applib.http

/**
 * Author: JackPan
 * Date: 2021-10-09
 * Time: 14:11
 * Description:
 */
class ApiException(val errorMessage: String, val errorCode: Int) :
    Throwable()