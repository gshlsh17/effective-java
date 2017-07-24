package effective.chapter10.item67;

/**
 * @author ll
 * @create 2017-07-24
 */
public interface SetObserver<E> {
    // 当在set中添加元素后，此方法被调用
    void added(ObservableSet set, E element);
}
