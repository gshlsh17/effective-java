package effective.chapter5;

import java.util.ArrayList;
import java.util.List;

/**
 * 25:列表由于数组
 * Created by liang on 2017-07-23.
 */
public class Item25 {
    public static void test1() {
        // 运行期才能发现错误
        Object[] objectArray = new Long[1];
        objectArray[0] = "1111";

        // 通不过编译期
//        List<Object> ol = new ArrayList<Long>();
//        ol.add("111");
    }

    public static void main(String[] args) {
        Item25.test1();
    }
}
