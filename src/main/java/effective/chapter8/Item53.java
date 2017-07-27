package effective.chapter8;

import java.util.Arrays;
import java.util.Set;

/**
 * 程序输入参数：java.util.HashSet 1 2 3 4 5 1 或者 java.util.TreeSet 1 2 3 4 5 1
 *
 * @author ll
 * @create 2017-07-27
 */
public class Item53 {
    public static void main(String[] args) {
        Class<?> cl =null;
        try {
            cl = Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found.");
            System.exit(1); //当输入错误的参数时，终止JVM虚拟机
        }

        // 采用Set引用代表具体的类对象，而不是使用反射的构造函数，当然前提条件是类存在默认构造函数
        Set<String> s = null;
        try {
            s = (Set<String>) cl.newInstance();
        } catch (IllegalAccessException e) {
            System.err.println("Class not accessible.");
            System.exit(1);
        } catch (InstantiationException e) {
            System.err.println("Class not instantiable.");
            System.exit(1);
        }

        s.addAll(Arrays.asList(args).subList(1, args.length));
        System.out.println(s);

    }
}
