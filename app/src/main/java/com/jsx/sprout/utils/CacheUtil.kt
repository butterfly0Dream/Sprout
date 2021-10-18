package com.jsx.sprout.utils

import com.jsx.applib.utils.SPUtils
import com.jsx.sprout.constants.Constants
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
        //发送退出登录消息
//        EventBus.getDefault().post(LogoutEvent())
//        //更新登陆状态
//        PrefUtils.setBoolean(Constants.LOGIN, false)
//        //移除用户信息
//        PrefUtils.removeKey(Constants.USER_INFO)
//        //移除积分信息
//        PrefUtils.removeKey(Constants.INTEGRAL_INFO)
        // TODO: 2021/10/18 退出登录
    }
}