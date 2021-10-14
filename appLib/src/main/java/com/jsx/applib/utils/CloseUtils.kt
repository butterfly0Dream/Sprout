package com.jsx.applib.utils

import java.io.Closeable
import java.io.IOException

/**
 * Author: JackPan
 * Date: 2021-10-14
 * Time: 11:14
 * Description:
 */
object CloseUtils {

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    fun closeIO(vararg closeables: Closeable?) {
        for (closeable in closeables) {
            if (closeable != null) {
                try {
                    closeable.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }
}