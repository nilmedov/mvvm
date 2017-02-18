package nilmedov.appmvvm.utils;


import nilmedov.appmvvm.utils.log.Logger;

/**
 * Created by Nazar on 26.11.2016.
 */

public class TestLogger implements Logger {

    @Override
    public void d(String tag, String message) {
        System.out.println(tag + " " + message);
    }

    @Override
    public void i(String tag, String message) {
        System.out.println(tag + " " + message);
    }

    @Override
    public void e(String tag, String message, Throwable e) {
        System.out.println(tag + " " + message + "\n" + e.toString());
    }
}
