package effective.chapter2.item5;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by liang on 2017-08-08.
 */
public class Person1 {
    private final Date birthDate;
    public Person1(Date birthDate) {
        this.birthDate = birthDate;
    }
    // 每次调用isBabyBoomer方法时，都会新建gmtCal，boomStart，boomEnd
    public boolean isBabyBoomer() {
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);
        Date boomStart = gmtCal.getTime();
        gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
        Date boomEnd = gmtCal.getTime();
        return birthDate.compareTo(boomStart) >= 0 && birthDate.compareTo(boomEnd) < 0;
    }
}
