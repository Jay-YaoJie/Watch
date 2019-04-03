package com.tzx.watch.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.tzx.watch.MainActivity
import com.tzx.watch.R
import com.tzx.watch.filekts.FinalValue.SPLASH_DELAY_TIME
import com.tzx.watch.filekts.SPUtil

/**
 * author : Jeff  5899859876@qq.com
 * Csdn :https://blog.csdn.net/Jeff_YaoJie
 * Github: https://github.com/Jay-YaoJie
 * Created :  2019-03-15.
 * description ：引导页面
 */
class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            //doSomethingHere()
            //如果有记住密码，登录成功跳到主界面，否则登录不成功跳到登录界面
            if (SPUtil.getBoolean("login", true)) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }
            // 3600毫秒    //启动界面延迟时间
        }, SPLASH_DELAY_TIME)
    }
}



