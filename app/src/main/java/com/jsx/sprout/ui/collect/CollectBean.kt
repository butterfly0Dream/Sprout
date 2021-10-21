package com.jsx.sprout.ui.collect

/**
 * Author: JackPan
 * Date: 2021-10-21
 * Time: 10:27
 * Description:
 */
class CollectBean {

    var curPage: Int = 0
    var offset: Int = 0
    var over: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
    var datas: MutableList<DatasBean>? = null

    class DatasBean {

        var author: String? = null
        var chapterId: Int = 0
        var chapterName: String? = null
        var courseId: Int = 0
        var desc: String? = null
        var envelopePic: String? = null
        var id: Int = 0
        var link: String? = null
        var niceDate: String? = null
        var origin: String? = null
        var originId: Int = 0
        var publishTime: Long = 0
        var title: String? = null
        var userId: Int = 0
        var visible: Int = 0
        var zan: Int = 0
    }
}