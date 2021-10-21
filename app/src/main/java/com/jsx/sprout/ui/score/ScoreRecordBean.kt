package com.jsx.sprout.ui.score

/**
 * Author: JackPan
 * Date: 2021-10-21
 * Time: 09:52
 * Description:
 */
class ScoreRecordBean {
    var curPage: Int = 0
    var offset: Int = 0
    var over: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
    var datas: MutableList<DatasBean>? = null

    class DatasBean {
        var coinCount: Int = 0
        var date: Long = 0
        var desc: String? = null
        var id: Int = 0
        var reason: String? = null
        var type: Int = 0
        var userId: Int = 0
        var userName: String? = null
    }
}