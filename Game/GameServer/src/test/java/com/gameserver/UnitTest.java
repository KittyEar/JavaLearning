package com.gameserver;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;

//单元测试,参数化测试
@RunWith(Parameterized.class)
public class UnitTest {
    private String input;
    private String expected;
    private StringSolution solution;
    private static  int count = 0;

    @Parameterized.Parameters
    public static Collection<?> data() {         //左边为输入，右边为预期结果
        return Arrays.asList(new String[][] {
                { "vaLidHTTPRequest", "va_lid_http_request" },
                { "validHTTPRequest", "valid_http_request" },
                { "ValidHTTPRequest", "valid_http_request" },
                { "vaLidHTTPRequest", "va_lid_http_request" },
                { "validHTTPRequest", "valid_http_request" },
                { "validHTTPRequesT", "valid_http_reques_t" },
                { "aBCCdE", "a_bc_cd_e"},
                { "myFirstName", "my_first_name" },
                { "OnlineUsers", "online_users" },
                { "ABCDefg", "abc_defg" },
                { "Address", "address" },
                {"",""},
                {"A","a"},
        });
    }

    @Before
    public void setUp() {
        System.out.println("用例 " + ++count);
        this.solution = new StringSolution();
    }

    public UnitTest(String input, String expected) {
        this.input = input;
        this.expected = expected;
    }


    @Test
    public void test() {
        Assertions.assertEquals(this.expected, solution.solve(this.input), "测试失败");
        //System.out.println();
    }
    @After
    public void tearDown() {
        System.out.println("输入：" + this.input + "，测试结果："+solution.solve(this.input)+"，预期结果：" + this.expected);
    }
}
//题目
class StringSolution {
    public String solve(String str) {
        char[] chars = str.toCharArray();
        int length = chars.length;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // 前一个字母大写，后一个字母小写，将该字母从大写转换成小写
            if (i + 1 < length && (isChange(chars[i], chars[i + 1]) == 1)) {
                result.append(Character.toLowerCase(chars[i]));
//                if(i > 0 && i + 1 < length && isChange(chars[i - 1], chars[i]) == -1 && isChange(chars[i], chars[i + 1]) == 1){
//                    result.append("_");
//                }
            } else {
                result.append(Character.toLowerCase(chars[i]));
            }
            //前一个字母是小写，后一个字母是大写
            if (i + 1 < length && (isChange(chars[i], chars[i + 1]) == -1)) {
                result.append("_");
            }
            //前两个字母是大写，后一个字母是小写
            boolean flag = i + 2 < length
                    && (Character.isUpperCase(chars[i])
                    && Character.isUpperCase(chars[i + 1])
                    && Character.isLowerCase(chars[i + 2]));

            if (flag) {
                result.append("_");
            }
//            flag = i + 2 < length
//                    && (Character.isLowerCase(chars[i])
//                    && Character.isUpperCase(chars[i + 1])
//                    && Character.isLowerCase(chars[i + 2]));
//            if (flag) {
//                result.append("_");
//            }
        }
        return result.toString();
    }
    //字符大小写突变
    private int isChange(char a, char b) {
        if (Character.isUpperCase(a) && Character.isLowerCase(b)) {//由大变小
            return 1;
        } else if (Character.isLowerCase(a) && Character.isUpperCase(b)) {//由小变大
            return -1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        double a = 304;
        double b  = 592;
        double c = 384;
        double d = 0;
        double sum = a + b + c + d;
        while(true){
            d = d + 1;
            if(d * 0.3 + d * 0.5 + d * 0.2 + d * (d / (d * 0.3 + d * 0.5 + d * 0.2 + d)) == 1.0){
                System.out.println(d);
                break;
            }
            System.out.println(d);
        }
        System.out.println(a / sum);
        System.out.println(b / sum);
        System.out.println(c / sum);
        System.out.println(d / sum);
    }
}