package selenium.tests;


import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Sandbox extends TestBase {

    @Test
    public void test() {
        String newLogFilePath = System.getProperty("user.dir") +
                "/src/test/resources/driver_logs/WebDriver_logs" +
                LocalDateTime.now().toString().replaceAll(":", "-") +
                ".txt";
        System.out.println(newLogFilePath);
        File file = new File(newLogFilePath);


        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("qwer");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}