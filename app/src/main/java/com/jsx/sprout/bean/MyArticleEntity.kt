package com.jsx.sprout.bean

/**
 * Author: JackPan
 * Date: 2021-11-06
 * Time: 15:57
 * Description:
 */
class MyArticleEntity {
    var coinInfo: CoinInfoBean? = null
    var shareArticles: ShareArticlesBean? = null

    class CoinInfoBean {
        /**
         * coinCount : 400
         * level : 4
         * rank : 1085
         * userId : 36628
         * username : 1**16720137
         */
        var coinCount = 0
        var level = 0
        var rank = 0
        var userId = 0
        var username: String? = null
    }

    class ShareArticlesBean {
        var curPage = 0
        var offset = 0
        var over = false
        var pageCount = 0
        var size = 0
        var total = 0
        var datas: List<ArticleBean.DatasBean>? = null
    }
}