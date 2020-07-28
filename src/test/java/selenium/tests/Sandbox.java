package selenium.tests;


import io.qameta.allure.Step;
import org.testng.annotations.Test;

public class Sandbox extends TestBase {

    @Test
    public void test() {
        app.goTo().homepage();
        showSecondStep();
    }

    @Step("step number 2")
    private void showSecondStep() {

    }
}