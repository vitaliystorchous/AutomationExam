package selenium.tests;


import org.testng.annotations.Test;

import java.math.BigDecimal;

public class Sandbox extends TestBase {

    @Test
    public void test() {
        double x = 3.144;
        BigDecimal decimal = new BigDecimal(x).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(decimal);
    }
}