package date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.stream.Stream;

/**
 * 打印当月的日历
 * Mon  Tue  Wed  Fri  Sat  Sun
 *           1   2   3   4   5
 *   6   7   8*  9  10  11  12
 *  13  14  15  16  17  18  19
 *  20  21  22  23  24  25  26
 *  27  28
 *  不需要判断这个月有几天，因为LocalDate会知道是第几月，直接循环即可
 *
 */
public class Calender {
    private static void main() {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int today = date.getDayOfMonth();

        date = date.minusDays(today - 1);//设为当月的第一天

        DayOfWeek weekDay = date.getDayOfWeek();//星期的枚举
        int value = weekDay.getValue();//上个月的，要在开头打一些空格
        StringJoiner stringJoiner = new StringJoiner(" ");
        Arrays.stream(DayOfWeek.values()).forEach(dayOfWeek -> stringJoiner.add(dayOfWeek.toString().substring(0, 3)));
        System.out.println(stringJoiner);
        for (int i = 1; i < value; i++) {
            System.out.print("    ");
        }
        while (date.getMonthValue() == month) {
            System.out.printf("%2d", date.getDayOfMonth());
            if (date.getDayOfMonth() == today) {
                System.out.print("* ");
            } else {
                System.out.print("  ");
            }
            date = date.plusDays(1);
            if (date.getDayOfWeek().getValue() == 1) {
                System.out.println();
            }
        }
        if (date.getDayOfWeek().getValue() != 1) {
            System.out.println();
        }
    }

    public static void main(String[] args) {
       main();
    }
}
