package com.jsx.sprout.utils

import org.junit.Assert
import org.junit.Test

/**
 * Author: JackPan
 * Date: 2021-11-02
 * Time: 15:54
 * Description:
 */
class StringUtilsTest {

    @Test
    fun isEmpty() {
        Assert.assertEquals(StringUtils.isEmpty(""), true)
    }
}