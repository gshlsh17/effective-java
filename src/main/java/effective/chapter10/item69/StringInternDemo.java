package effective.chapter10.item69;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 采用ConcurrentHashMap模拟String.intern行为
 *
 * @author ll
 * @create 2017-07-25
 */
public class StringInternDemo {
    private static final ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

    public static String intern1(String s) {
        //如果s在map中没有，则插入s，并且返回s
        String previousValue = map.putIfAbsent(s, s);
        return previousValue == null ? s: previousValue;
    }
    // 相对于intern1，intern2更快，因为intern1不管map中有没有s，都先执行putIfAbsent操作
    // 并且map对于get操作进行了优化，所以intern2实现更好
    public static String intern2(String s) {
        String result = map.get(s);
        if (result == null) {
            result = map.putIfAbsent(s, s);
            if (result == null) {
                result = s;
            }
        }
        return result;
    }


}
