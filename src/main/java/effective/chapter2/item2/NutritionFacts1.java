package effective.chapter2.item2;

/**
 * 类中需要初始化的域很多，采用重叠构造器模式来编写构造器
 * 但是当类中的域增加时，此种模式就不是一个很好的选择
 * 并且在参数很多时，我们需要仔细进行参数的对应关系，如果两个参数类型相同，但是我们把顺序搞错了，
 * 编译器不会报错，但是在运行时会出现一些奇怪的错误
 * Created by liang on 2017-08-03.
 */
public class NutritionFacts1 {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public NutritionFacts1(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public NutritionFacts1(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public NutritionFacts1(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories, fat, 0);
    }

    public NutritionFacts1(int servingSize, int servings, int calories, int fat, int sodium) {
        this(servingSize, servings, calories, fat, sodium, 0);
    }

    public NutritionFacts1(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }

    public static void main(String[] args) {
        // 假设我们不想设置fat的值，但是根据这里提供的构造器，我们必须提供fat值
        // 这里仅仅只有六个参数，如果参数有十几个，那么构造这个类就会非常麻烦
        NutritionFacts1 cocaCola = new NutritionFacts1(240, 8, 100, 0,
                35, 27);
    }
}
