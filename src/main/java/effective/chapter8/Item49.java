package effective.chapter8;

import java.util.Comparator;

/**
 *
 * @author ll
 * @create 2017-07-26
 */
public class Item49 {
    Comparator<Integer> badComparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            // 实际运行时（JDK8_141），这里会将o1和o2转为对应的int，之后进行比较，但是effective java书中是进行对象的比较
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
        Integer temp1 = 42;
        Integer temp2 = 42;
        // 输出true，因为Integer对于[-128,127]之间的数字进行了缓存
        System.out.println(temp1 == temp2);

        Integer o1 = new Integer(42);
        Integer o2 = new Integer(42);
        System.out.println(o1 == o2);

        Item49 item49 = new Item49();
        System.out.println("调用badComparator比较结果：" + item49.badComparator.compare(o1, o2));
        System.out.println("调用goodComparator比较结果：" + item49.goodComparator.compare(o1, o2));
    }
}
