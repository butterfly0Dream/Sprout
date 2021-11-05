package com.jsx.sprout.db.dao

import androidx.room.*
import com.jsx.sprout.bean.ArticleListBean

/**
 * Author: JackPan
 * Date: 2021-11-05
 * Time: 16:20
 * Description:
 */
@Dao
interface BrowseHistoryDao {

    @Query("SELECT COUNT(uid) FROM browse_history")
    suspend fun getCount(): Int

    @Query("SELECT * FROM browse_history where id = :id")
    suspend fun getArticle(id: Int): ArticleListBean?

    @Query("SELECT * FROM browse_history")
    suspend fun getAllArticleListBean(): List<ArticleListBean>

    @Query("SELECT * FROM browse_history order by uid desc limit :page,20")
    suspend fun getHistoryArticleList(page: Int): List<ArticleListBean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(articleList: List<ArticleListBean>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleListBean)

    @Update
    suspend fun update(article: ArticleListBean): Int

    @Delete
    suspend fun delete(article: ArticleListBean): Int

    @Delete
    suspend fun deleteList(articleList: List<ArticleListBean>): Int

    @Query("DELETE FROM browse_history")
    suspend fun deleteAll()
}