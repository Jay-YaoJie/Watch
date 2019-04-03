package  com.tzx.watch.filekts

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.tzx.watch.Watch.Companion.instance

/**
 * author : Jeff  5899859876@qq.com
 * Csdn :https://blog.csdn.net/Jeff_YaoJie
 * Github: https://github.com/Jay-YaoJie
 * Created :  2019-03-15.
 * description ：SharedPreferences工具对象，文件储存对象
 */

object SPUtil {
    fun getString(
        key: String,
        defaultValue: String
    ): String? {
        val settings = PreferenceManager
            .getDefaultSharedPreferences(instance)
        return settings.getString(key, defaultValue)
    }

    fun setString(
        key: String,
        value: String
    ) {
        val settings = PreferenceManager
            .getDefaultSharedPreferences(instance)
        settings.edit().putString(key, value)//.apply()
    }

    fun setBoolean(
        key: String,
        value: Boolean
    ) {
        val settings = PreferenceManager
            .getDefaultSharedPreferences(instance)
        settings.edit().putBoolean(key, value)//.apply()
    }

    fun getBoolean(
        key: String,
        defaultValue: Boolean
    ): Boolean {
        val settings = PreferenceManager
            .getDefaultSharedPreferences(instance)
        return settings.getBoolean(key, defaultValue)
    }


    fun hasKey(context: Context, key: String): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(instance).contains(
            key
        )
    }


    fun setInt(
        key: String,
        value: Int
    ) {
        val settings = PreferenceManager
            .getDefaultSharedPreferences(instance)
        settings.edit().putInt(key, value)//.apply()
    }

    fun getInt(
        key: String,
        defaultValue: Int
    ): Int {
        val settings = PreferenceManager
            .getDefaultSharedPreferences(instance)
        return settings.getInt(key, defaultValue)
    }

    fun setFloat(
        key: String,
        value: Float
    ) {
        val settings = PreferenceManager
            .getDefaultSharedPreferences(instance)
        settings.edit().putFloat(key, value)//.apply()
    }

    fun getFloat(
        key: String,
        defaultValue: Float
    ): Float {
        val settings = PreferenceManager
            .getDefaultSharedPreferences(instance)
        return settings.getFloat(key, defaultValue)
    }

    fun setLong(
        key: String,
        value: Long
    ) {
        val settings = PreferenceManager
            .getDefaultSharedPreferences(instance)
        settings.edit().putLong(key, value)//.apply()
    }

    fun getLong(
        key: String,
        defaultValue: Long
    ): Long {
        val settings = PreferenceManager
            .getDefaultSharedPreferences(instance)
        return settings.getLong(key, defaultValue)
    }

    fun clear(
        p: SharedPreferences
    ) {
        val editor = p.edit()
        editor.clear()
       // editor.apply()
    }
}
