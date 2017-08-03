package effective.chapter6;

/**
 * 用实例域代替序数
 * Created by liang on 2017-07-23.
 */

/**
 * BadEnsemble中使用ordinal为每个枚举赋予一个值，但是这是基于顺序的，
 * 若改变了顺序，这种方法就不好了
 */
enum BadEnsemble {
    SOLO, DUET, TRIO;
    public int numberOfMusicians() {
        return ordinal() + 1;
    }
}

/**
 * 使用这种方式来为枚举赋值
 */
enum GoodEnsemble {
    SOLO(1), DUET(2), TRIO(3);

    private final int numberOfMusicians;
    GoodEnsemble(int size) {
        this.numberOfMusicians = size;
    }

    public int numberOfMusicians() {
        return numberOfMusicians;
    }
}
public class Item31 {
}
