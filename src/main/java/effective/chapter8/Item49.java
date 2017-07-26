package effective.chapter8;

import java.util.Comparator;

/**
 * @author ll
 * @create 2017-07-26
 */
public class Item49 {
    Comparator<Integer> badComparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            // 实际运行时，这里会将o1和o2抓为对应的int，之后进行比较，但是effective java书中是进行对象的比较
            return o1 < o2 ? -1 : (o1 == o1 ? 0 : 1);
        }
    };
    Comparator<Integer> goodComparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            int i1 = o1.intValue();
            int i2 = o2.intValue();
            return i1 < i2 ? -1 : (i1 == i2 ? 0 : 1);
        }
    };

    public static void main(String[] args) {
        Integer o1 = new Integer(42);
        Integer o2 = new Integer(42);
        System.out.println(o1 == o2);
        Item49 item49 = new Item49();
        System.out.println("调用badComparator比较结果：" + item49.badComparator.compare(o1, o2));
        System.out.println("调用goodComparator比较结果：" + item49.goodComparator.compare(o1, o2));
    }
}
