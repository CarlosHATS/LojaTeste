package base;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.HomePage;

public class BaseTests {

	private static WebDriver driver;
	protected HomePage homePage;

	@BeforeAll
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chrome\\109.0.5414.25\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		new WebDriverWait(driver, Duration.ofSeconds(3));

	}

	@BeforeEach
	public void carregarPaginaInicial() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
		homePage = new HomePage(driver);
		driver.manage().window().maximize();
	}

	@AfterAll
	public static void finalizar() {
		driver.quit();
	}

}
