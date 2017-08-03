package effective.chapter2.item2;

/**
 * 采用JavaBeans模式来解决参数较多时得构建问题
 * 存在问题
 * 1. 类的构建过程 JavaBean可能处于不一致的状态
 * 2. 无法将类构建为不可变的类
 * Created by liang on 2017-08-03.
 */
public class NutritionFacts2 {
    private int servingSize = -1;
    private int servings = -1;
    private int calories = 0;
    private int fat = 0;
    private int sodium = 0;
    private int carbohydrate = 0;

    public NutritionFacts2() {

    }

    // Setters
    public void setServingSize(int val) {
        this.servingSize = val;
    }

    public void setServings(int val) {
        this.servings = val;
    }

    public void setCalories(int val) {
        this.calories = val;
    }

    public void setFat(int val) {
        this.fat = val;
    }

    public void setSodium(int val) {
        this.sodium = val;
    }
    public void setCarbohydrate(int val) {
        this.carbohydrate = val;
    }

    public static void main(String[] args) {
        // 我们只需填写我们需要的参数
        NutritionFacts2 nutritionFacts2 = new NutritionFacts2();
        nutritionFacts2.setServingSize(240);
        nutritionFacts2.setServings(8);
        nutritionFacts2.setCalories(100);
        nutritionFacts2.setSodium(35);
        nutritionFacts2.setCarbohydrate(27);
    }
}
