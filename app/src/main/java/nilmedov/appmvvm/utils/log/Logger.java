package nilmedov.appmvvm.utils.log;

/**
 * Created by Nazar on 26.11.2016.
 */

public interface Logger {

    void d(String tag, String message);

    void i(String tag, String message);

    void e(String tag, String message, Throwable e);
}
