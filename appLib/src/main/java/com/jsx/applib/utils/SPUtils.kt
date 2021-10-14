package com.jsx.applib.utils

import android.content.Context
import android.text.TextUtils
import android.util.Base64
import com.jsx.applib.BaseApp
import java.io.*

/**
 * Author: JackPan
 * Date: 2021-10-14
 * Time: 10:37
 * Description: SharePreference封装
 */
object SPUtils {
    private const val PREF_NAME = "sp_app"

    fun getBoolean(
        key: String,
        defaultValue: Boolean, ctx: Context = BaseApp.getContext()
    ): Boolean {
        val sp = ctx.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getBoolean(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean, ctx: Context = BaseApp.getContext()) {
        val sp = ctx.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putBoolean(key, value).apply()
    }

    fun getString(key: String, defaultValue: String, ctx: Context = BaseApp.getContext()): String? {
        val sp = ctx.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getString(key, defaultValue)
    }

    fun setString(key: String, value: String, ctx: Context = BaseApp.getContext()) {
        val sp = ctx.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putString(key, value).apply()
    }

    fun setInt(key: String, value: Int, ctx: Context = BaseApp.getContext()) {
        val sp = ctx.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int, ctx: Context = BaseApp.getContext()): Int {
        val sp = ctx.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getInt(key, defaultValue)
    }

    fun setLong(key: String, value: Long, ctx: Context = BaseApp.getContext()) {
        val sp = ctx.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        sp.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long, ctx: Context = BaseApp.getContext()): Long {
        val sp = ctx.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.getLong(key, defaultValue)
    }

    fun setHashSet(key: String, value: HashSet<String>, ctx: Context = BaseApp.getContext()) {
        val sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sp.edit().putStringSet(key, value).apply()
    }

    fun getHashSet(key: String, ctx: Context = BaseApp.getContext()): MutableSet<String>? {
        val sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sp.getStringSet(key, null)
    }

    fun removeKey(key: String): Boolean {
        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        return sp.edit().remove(key).commit()
    }

    fun setObject(key: String, value: Any?) {
        if (value == null) {
            return
        }
        if (value !is Serializable) {
            return
        }
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            val sp = BaseApp.getContext().getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(value)
            val temp = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
            sp.edit().putString(key, temp).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            CloseUtils.closeIO(oos, baos)
        }
    }

    fun getObject(key: String): Any? {
        var `object`: Any? = null
        var bais: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null

        val sp = BaseApp.getContext().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
        val temp = sp.getString(key, "")
        if (!TextUtils.isEmpty(temp)) {
            try {
                if (temp != null) {
                    bais = ByteArrayInputStream(Base64.decode(temp.toByteArray(), Base64.DEFAULT))
                }
                ois = ObjectInputStream(bais)
                `object` = ois.readObject()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                CloseUtils.closeIO(ois, bais)
            }
        }
        return `object`
    }

}