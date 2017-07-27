package effective.chapter7;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 39：必要时进行保护性拷贝
 *
 * @author ll
 * @create 2017-07-27
 */

/**
 * 这里本来想构造固定的起始时间和终止时间，但是客户端仍然能够修改时间
 * main方法中显示了如何修改
 */
final class BadPeriod {
    private final Date start;
    private final Date end;

    public BadPeriod(Date start, Date end) {
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "start:"+start.toString()+" end:"+end.toString();
    }
}

final class GoodPeriod {
    private final Date start;
    private final Date end;

    public GoodPeriod(Date start1, Date end1) {
        // 采用保护性拷贝
        start = new Date(start1.getTime());
        end = new Date(end1.getTime());
        // 为什么先拷贝而后进行参数验证，是因为若先验证参数，
        // 而在拷贝之前这段时间内客户端仍然能够改变参数的值
        // 这是Time-Of-Check/Time-Of-Use或者TOCTOU攻击
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "start:"+start.toString()+" end:"+end.toString();
    }
}
// 最安全的Period
final class Period {
    private final Date start;
    private final Date end;

    public Period(Date start1, Date end1) {
        // 采用保护性拷贝
        start = new Date(start1.getTime());
        end = new Date(end1.getTime());
        // 为什么先拷贝而后进行参数验证，是因为若先验证参数，
        // 而在拷贝之前这段时间内客户端仍然能够改变参数的值
        // 这是Time-Of-Check/Time-Of-Use或者TOCTOU攻击
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }
    }
    // 返回可变内部域的保护性拷贝，可以防止另一种攻击
    public Date getStart() {
        return new Date(start.getTime());
    }

    public Date getEnd() {
        return new Date(end.getTime());
    }

    @Override
    public String toString() {
        return "start:"+start.toString()+" end:"+end.toString();
    }
}
public class Item39 {
    public static void main(String[] args) {
        Date start = new Date();
        Date end = new Date();

        BadPeriod bp = new BadPeriod(start, end);
        GoodPeriod gp = new GoodPeriod(start, end);
        Period p = new Period(start, end);
        end.setYear(71);//改变了bp但是没有改变gp
        System.out.println("bp:"+bp);
        System.out.println("gp:" + gp);
        System.out.println(p);
        // 对GoodPeriod的攻击
        gp.getEnd().setYear(78);
        System.out.println("gp:" + gp);
        System.out.println("p:" + p);
    }
}
