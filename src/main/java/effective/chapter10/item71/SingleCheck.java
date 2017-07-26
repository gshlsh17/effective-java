package effective.chapter10.item71;

/**
 * 对于域可以接受重复值，可以采用单重检查模式进行延迟初始化
 * @author ll
 * @create 2017-07-26
 */
public class SingleCheck {
    private volatile Object obj;

    public Object getObj() {
        Object result = obj;
        if (result == null) {
            obj = result = computeObj();
        }
        return result;
    }
    private Object computeObj() {
        return new Object();
    }
}
