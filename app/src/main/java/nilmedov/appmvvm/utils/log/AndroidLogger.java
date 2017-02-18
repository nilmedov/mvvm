package nilmedov.appmvvm.utils.log;

import android.util.Log;

/**
 * Created by Nazar on 26.11.2016.
 */

public class AndroidLogger implements Logger {

    @Override
    public void d(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    public void i(String tag, String message) {
        Log.i(tag, message);
    }

    @Override
    public void e(String tag, String message, Throwable e) {
        Log.e(tag, message, e);
    }
}
