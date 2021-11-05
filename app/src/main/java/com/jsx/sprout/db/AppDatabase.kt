package com.jsx.sprout.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jsx.applib.BaseApp
import com.jsx.sprout.bean.ArticleListBean
import com.jsx.sprout.db.dao.BrowseHistoryDao

/**
 * Author: JackPan
 * Date: 2021-11-05
 * Time: 16:22
 * Description:
 */
@Database(entities = [ArticleListBean::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun browseHistoryDao() : BrowseHistoryDao

    companion object{
        private var instance: AppDatabase? = null

        fun getInstance():AppDatabase{
            return instance?: synchronized(this){
                instance?:buildDataBase(BaseApp.getContext())
                    .also {
                        instance = it
                    }
            }
        }

        private fun buildDataBase(context: Context):AppDatabase{
            return Room
                .databaseBuilder(context,AppDatabase::class.java,"jet_database")
                .addCallback(object : RoomDatabase.Callback(){
                })
                .build()
        }
    }
}