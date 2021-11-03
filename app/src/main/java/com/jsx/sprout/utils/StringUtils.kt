package com.jsx.sprout.utils

/**
 * Author: JackPan
 * Date: 2021-11-02
 * Time: 15:51
 * Description:
 */
object StringUtils {

    fun isEmpty(str: String?): Boolean{
        return str == null || str.length == 0
    }

}