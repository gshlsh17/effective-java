package effective.chapter8;

/**
 * @author ll
 * @create 2017-07-27
 */
public class Item51 {
    private static final int NUM = 1000;
    private static final String STR = "abcdefghtjalsgncjgoglaklafcnkvlkglkfdlkjfsdlkj"
            + "l;asdjfdafaoghang;agln;lang;lgnb";

    // 没有使用StringBuilder进行字符串的拼接，性能很差
    public String badStatement() {
        String result = "";
        for (int i = 0; i < NUM; i++) {
            result += STR;
        }
        return result;
    }
    public String goodStatement() {
        // 预先分配容量，这样能够提高性能
        StringBuilder b = new StringBuilder(NUM * STR.length());
        for (int i = 0; i < NUM; i++) {
            b.append(STR);
        }
        return b.toString();
    }

    public static void main(String[] args) {
        Item51 item51 = new Item51();

        long time1 = System.nanoTime();
        item51.badStatement();
        long internal1 = System.nanoTime() - time1;
        long time2 = System.nanoTime();
        item51.goodStatement();
        long internal2 = System.nanoTime() - time2;
        System.out.println("badStatement耗时是goodStatement的" + internal1 / internal2 + "倍");
    }
}
