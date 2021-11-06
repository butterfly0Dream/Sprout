package com.jsx.sprout.ui.rank

/**
 * Author: JackPan
 * Date: 2021-11-06
 * Time: 15:03
 * Description:
 */
class RankBean{
    var curPage = 0
    var offset = 0
    var over = false
    var pageCount = 0
    var size = 0
    var total = 0
    var datas: MutableList<DatasBean>? = null

    class DatasBean{
        var coinCount = 0
        var level = 0
        var rank = 0
        var username: String? = null
    }
}
