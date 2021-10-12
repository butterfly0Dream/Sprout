package com.jsx.applib.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Author: JackPan
 * Date: 2021-10-09
 * Time: 17:18
 * Description:
 */
open class BaseRepository {

    /**
     * 在协程作用域中切换至IO线程
     */
    protected suspend fun <T> withIO(block: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            block.invoke()
        }
    }
}