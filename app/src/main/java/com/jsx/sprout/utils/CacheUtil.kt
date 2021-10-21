package com.jsx.sprout.utils

import com.jsx.applib.utils.SPUtils
import com.jsx.sprout.constants.SPConstants

/**
 * Author: JackPan
 * Date: 2021-10-18
 * Time: 15:23
 * Description:
 */
object CacheUtil {
    /**
     * 登录状态
     */
    fun isLogin(): Boolean {
        return SPUtils.getBoolean(SPConstants.LOGIN, false)
    }

    /**
     * 退出登录，重置用户状态
     */
    fun resetUser() {
        //更新登陆状态
        SPUtils.setBoolean(SPConstants.LOGIN, false)
        //移除用户信息
        SPUtils.removeKey(SPConstants.USER_INFO)
        //移除积分信息
        SPUtils.removeKey(SPConstants.INTEGRAL_INFO)
        // TODO: 2021/10/18 退出登录
    }
}