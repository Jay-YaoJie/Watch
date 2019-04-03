package  com.tzx.watch.bases

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tzx.watch.Watch.Companion.eBus
import com.tzx.watch.utils.ActivitiesManager.popActivity
import com.tzx.watch.utils.ActivitiesManager.pushActivity


/**
 * author : Jeff  5899859876@qq.com
 * Csdn :https://blog.csdn.net/Jeff_YaoJie
 * Github: https://github.com/Jay-YaoJie
 * Created :  2019-03-15.
 * description ： baseAppCompatActivity
 */
open class BaseActivity : AppCompatActivity() {
    var tag: String? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        into();
        tag = this.localClassName;
        pushActivity(this);//添加当前activity
    }

    //   如果设置了abstract就会TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    open fun into() {};

    override fun onDestroy() {
        close();
        if (eBus.isRegistered(this)) {
            //如果有注册eventbus则在结束当前页面时关闭
            eBus.unregister(this);
        }
        popActivity(this);//关闭当前activity
        super.onDestroy()
    }

    //页面被关闭生命周期到了onDestroy
    open fun close() {}
}
