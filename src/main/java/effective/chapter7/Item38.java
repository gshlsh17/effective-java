package effective.chapter7;

import java.math.BigInteger;

/**
 * @author ll
 * @create 2017-07-27
 */
public class Item38 {
    /**
     * 对于公有方法，
     * 在方法文档中会写出参数的要求，同时写出抛出的异常
     * @param m the modulus, which must be positive
     * @return
     * @throws ArithmeticException if m is less than or equal to 0
     */
    public BigInteger mod(BigInteger m) {
        if (m.signum() <= 0) {
            throw new ArithmeticException("Modulus <= 0: " + m);
        }
        // do some thing
        return m;
    }

    /**
     * 可以在没有导出的方法（私有方法等）采用assert来验证参数
     * assert在默认情况下不启用，可以将 -ea(-enableassertions)传递给Java解释器来启动
     * @param a
     * @param offset
     * @param length
     */
    private static void sort(long a[], int offset, int length) {
        assert a != null;
        assert offset >= 0 && offset <= a.length;
        assert length >= 0 && length <= a.length - offset;
        // do sort
    }
}
