package visual.test.autotes;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplitoolsTest {
	private WebDriver driver;
	private Eyes eyes;

	@BeforeEach
	public void setup() {
		ChromeDriverManager.getInstance().setup("80.0.3987.106");
		driver = new ChromeDriver();
		eyes = new Eyes();
	}

	@Test
	public void test() {
		driver.get("http://applitools.com/helloworld");

		// Start visual testing with browser viewport set to 1024x768.
		// Make sure to use the returned driver from this point on.
		eyes.setApiKey("OlBkcNw0GIueCW53xOXhE103Omhp2A6RtOYa0xqXd10686o110"); // account andrey.a.epifanov@gmail.com
		driver = eyes.open(driver, "Applitools website", "Test Applitools homepage", new RectangleSize(900, 700));

		// Visual validation point #1
		eyes.checkWindow("Home Page");

		driver.findElement(By.cssSelector("button")).click();

		// Visual validation point #2
		eyes.checkWindow("Features page");
	}

	@AfterEach
	public void tearDown() {
		// End visual testing. Validate visual correctness.
		eyes.close();
		eyes.abortIfNotClosed();

		driver.close();
	}

}
