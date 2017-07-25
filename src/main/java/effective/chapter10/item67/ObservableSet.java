package effective.chapter10.item67;

import effective.chapter4.item16.ForwardingSet;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可以使用CopyOnWriteArrayList类型代替observers，就能避免异常和死锁的发生
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
//        set.addObserver(new SetObserver<Integer>() {
//            @Override
//            public void added(ObservableSet set, Integer element) {
//                System.out.println(element);
//            }
//        });

        // 会报ConcurrentModificationException异常，因为在add操作时，会遍历set，而遍历的过程中等于23时删除观察者，所以报错
//        set.addObserver(new SetObserver<Integer>() {
//            @Override
//            public void added(ObservableSet set, Integer element) {
//                System.out.println(element);
//                if (element == 23) {
//                    set.removeObserver(this);
//                }
//            }
//        });
        // 下面这种方式会造成死锁
        set.addObserver(new SetObserver<Integer>() {
            @Override
            public void added(final ObservableSet set, Integer element) {
                System.out.println(element);
                if (element == 23) {
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    final  SetObserver<Integer> observer = this;
                    try {
                        executor.submit(new Runnable() {
                            @Override
                            public void run() {
                                set.removeObserver(observer);
                            }
                        }).get();
                    } catch (ExecutionException e) {
                        throw new AssertionError(e.getCause());
                    } catch (InterruptedException e) {
                        throw new AssertionError(e.getCause());
                    } finally {
                        executor.shutdown();
                    }
                }
            }
        });
        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }
}
