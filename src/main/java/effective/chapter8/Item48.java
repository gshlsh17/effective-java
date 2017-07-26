package effective.chapter8;

import java.math.BigDecimal;

/**
 *
 * @author ll
 * @create 2017-07-26
 */
public class Item48 {
    /**
     * 采用double实现，发现计算不准确
     */
    public static void compute1() {
        double funds = 1.00;
        int itemsBought = 0;
        for (double price = .10; funds >= price; price += .10) {
            funds -= price;
            itemsBought++;
        }
        System.out.println("itemsBought:" + itemsBought);
        System.out.println("剩余的钱数：" + funds);
    }

    /**
     * 采用BigDecimal实现，准确计算数字
     */
    public static void compute2() {
        final BigDecimal TEN_CENTS = new BigDecimal(".10");
        int itemsBought = 0;
        BigDecimal funds = new BigDecimal("1.00");
        for (BigDecimal price = TEN_CENTS; funds.compareTo(price) >= 0; price = price.add(TEN_CENTS)) {
            itemsBought++;
            funds = funds.subtract(price);
        }
        System.out.println("itemsBought:" + itemsBought);
        System.out.println("剩余的钱："+funds);
    }
    public static void main(String[] args) {
        Item48.compute1();
        Item48.compute2();
    }
}
