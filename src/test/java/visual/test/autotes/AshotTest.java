package visual.test.autotes;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@SpringBootTest
public class AshotTest {
    private WebDriver driver;
    private AShot ashot;

    @BeforeEach
    public void beforeEach() {
        ChromeDriverManager.getInstance().setup("80.0.3987.106");
        driver = new ChromeDriver();

        ashot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100));
    }

    @Test
    public void test() throws IOException {
        new File("./").getPath();
        Screenshot expected = new Screenshot(ImageIO.read(new File("./screenshots/expected/Laravel_full_01.png")));
        Screenshot expectedFull = new Screenshot(ImageIO.read(new File("./screenshots/expected/Laravel_manual_01.png")));

        driver.get("http://laravel.com");
        Screenshot actualScreenshot = ashot.takeScreenshot(driver);

        actualScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);

        ImageDiff diffFull = diffImage(expectedFull, actualScreenshot, "diffFull_mark_01");
        Assertions.assertEquals(0, diffFull.getDiffSize());

        ImageDiff diff = diffImage(expected, actualScreenshot, "diff_mark_01");
        Assertions.assertEquals(0, diff.getDiffSize());
    }

    private ImageDiff diffImage(Screenshot expected, Screenshot actualScreenshot, String diffFileName) throws IOException {
        ImageDiff diff = new ImageDiffer().makeDiff(expected, actualScreenshot);
        ImageIO.write(
                diff.getMarkedImage(),
                "PNG",
                new File("./screenshots/diff/" + diffFileName + ".png")
        );
        return diff;
    }

    @AfterEach
    public void afterEach(){
        driver.quit();
        driver.close();
    }
}
