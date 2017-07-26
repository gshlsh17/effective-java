package effective.chapter10.item71;

/**
 * 采用双重检查延迟初始化域
 * @author ll
 * @create 2017-07-26
 */
public class DoubleCheck {
    private volatile Object obj;

    public Object getObject() {
        Object result = obj;
        if (result == null) {
            synchronized (this) {
                result = obj;
                if (result == null) {
                    obj = result = computeObj();
                }
            }
        }
        return result;
    }
    private Object computeObj() {
        return new Object();
    }
}
