package effective.chapter5;

import java.util.*;

/**
 * 第23条：请不要在新代码中使用原生态类型
 * Created by liang on 2017-07-22.
 */
public  class Item23 {
    /**
     * 没有为stamps指定参数化类型，所以可以存入任意的类型，但是我们原本的目的是只保存String
     * 如果我们指定了参数化类型为String，则在编译期就能够检测出此类错误，不用等到真正运行时
     */
    public static void badTest1() {
        // 这里没有为Collection指定参数化类型，不推荐这样做
        Collection stamps = new ArrayList();
        stamps.add("1a");
        stamps.add(new Date());
        for (Iterator i = stamps.iterator(); i.hasNext();) {
            String temp = (String) i.next();  //运行时出错
            System.out.println(temp);
        }
        for (Object s : stamps) {
            System.out.println(s);
        }
    }
    public static void goodTest1() {
        // 指定了参数化类型, 注意JDK8中不用写ArrayList中的参数类型了，编译期能够自动推导类型
        Collection<String> stamps = new ArrayList<>();
        stamps.add("1a");
        // stamps.add(new Date()); 编译的时候报错

        for (Iterator<String> i = stamps.iterator(); i.hasNext();) {
            String temp = i.next();  //这里也没有强制转换了
            System.out.println(temp);
        }
        // 相对于test1方法，这里的类型更安全，注意s的类型为String，编译器能够帮助我们把stamps中类型转换为String
        for (String s : stamps) {
            System.out.println(s);
        }
    }

    /**
     * list参数用的 raw type
     */
    public static void badAdd(List list, Object o) {
        list.add(o);
    }

    public static void goodAdd(List<Object> list, Object o) {
        list.add(o);
    }

    public static int badNumElementsInCommon(Set s1, Set s2) {
        int result = 0;
        for (Object o1 : s1) {
            if (s2.contains(s1)) {
                result++;
            }
        }
        return result;
    }
    public static int goodNumElementsInCommon(Set<?> s1, Set<?> s2) {
        int result = 0;
        for (Object o1 : s1) {
            if (s2.contains(s1)) {
                result++;
            }
        }
        return result;
    }

    /**
     * 利用泛型使用 instanceof的首选方法
     * @param set
     */
    public static void goodTest3(Set<?> set) {
        if (set instanceof Set) {
            Set<?> m = (Set<?>) set;
            System.out.println(m.size());
        } else {
            System.out.println(set.getClass());
        }
    }
    public static void main(String[] args) {
//        Item23.badTest1();
        Item23.goodTest1();

        List<String> strings = new ArrayList<>();
        badAdd(strings, new Integer(42));
        String s = strings.get(0); // 这种错误只能在运行时才能检测到

        List<String> strings2 = new ArrayList<>();
        // goodAdd(strings2, new Integer(42)); //编译时报错，不能报string2传递给goodAdd
        String s2 = strings2.get(0);

        // set中不能放置任何元素(除了null之外)
        Set<?> set = new HashSet<>();
        // set.add(new Object());
        set.add(null);
        Set set1 = new HashSet<>();
        set1.add(new Object());

        // 下面两行在语法上都不合法
//        List<String>.class;
//        List<? extends Class> list = new ArrayList<String.class>();

    }
}
