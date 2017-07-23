package effective.chapter5;

import java.util.ArrayList;

/**
 * 24：消除非受检警告
 * Created by liang on 2017-07-23.
 */
public class Item24 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        String[] arr = new String[4];
        arr[0] = "9";
        arr[1] = "8";
        arr[2] = "7";
        arr[3] = "6";
        String[] arr1 = list.toArray(new String[0]);
        for (String s : arr1) {
            System.out.println(s);
        }
    }

}
