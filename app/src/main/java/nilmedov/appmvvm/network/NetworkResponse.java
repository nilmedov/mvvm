package nilmedov.appmvvm.network;

/**
 * Created by Nazar on 26.12.2016.
 */

public class NetworkResponse<T> {
    private T body;
    private int code;

    public NetworkResponse(T body, int code) {
        this.body = body;
        this.code = code;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccessful() {
        return code == 200;
    }
}
