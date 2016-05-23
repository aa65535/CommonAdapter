package commonadapter.sample;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Jian Chang on 2016-05-23.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
