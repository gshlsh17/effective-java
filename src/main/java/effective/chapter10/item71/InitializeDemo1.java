package effective.chapter10.item71;

/**
 * 采用静态内部类实现单例模式
 * 因为 JVM在初始化类的时候会同步访问域，一旦类被初始化，JVM会修改代码，后续对此域的访问不会导致同步
 * @author ll
 * @create 2017-07-26
 */
public class InitializeDemo1 {
    private static class SingletonHolder {
        private static final InitializeDemo1 INSTANCE = new InitializeDemo1();
    }
    private InitializeDemo1 (){}
    public static final InitializeDemo1 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
