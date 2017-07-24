package effective.chapter10.item67;

import effective.chapter4.item16.ForwardingSet;

import java.util.*;

/**
 * @author ll
 * @create 2017-07-24
 */
public class ObservableSet<E> extends ForwardingSet<E> {

    public ObservableSet(Set<E> set) {
        super(set);
    }
    private final List<SetObserver<E>> observers = new ArrayList<SetObserver<E>>();

    public void addObserver(SetObserver<E> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public boolean removeObserver(SetObserver<E> observer) {
        synchronized (observers) {
            return observers.remove(observer);
        }
    }

    private void notifyElementAdded(E element) {
        synchronized (observers) {
            for (SetObserver<E> observer : observers) {
                observer.added(this, element);
            }
        }
    }

    @Override
    public boolean add(E element) {
        boolean added = super.add(element);
        if (added) {
            notifyElementAdded(element);
        }
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for (E element : c) {
            result |= add(element);
        }
        return result;
    }

    public static void main(String[] args) {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());
        set.addObserver(new SetObserver<Integer>() {
            @Override
            public void added(ObservableSet set, Integer element) {
                System.out.println(element);
            }
        });
        set.addObserver(new SetObserver<Integer>() {
            @Override
            public void added(ObservableSet set, Integer element) {
                System.out.println("我是第二个观察者");
            }
        });
        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }
}
