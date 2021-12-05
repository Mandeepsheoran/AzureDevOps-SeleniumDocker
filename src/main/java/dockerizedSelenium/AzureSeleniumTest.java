package dockerizedSelenium;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AzureSeleniumTest {
	WebDriver driver;

	@BeforeTest
	public  void tearUp() throws MalformedURLException, InterruptedException {
		
		System.out.println("Test will run on chrome inside docker container, will create docker driver connection now");

		//We will use chromeOptions instead of Desired capabilities which are deplecating in Selenium 4
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--ignore-ssl-errors=yes");
		options.addArguments("--disable-web-security");
		options.addArguments("--allow-running-insecure-content");
		options.setCapability("platform", Platform.ANY);
		try {
		driver = new RemoteWebDriver(new URL("http://localhost:4449"), options);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.get("https://evisa.rop.gov.om/");	
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
	
	}

	@Test(priority=1)
	public  void eVisaTrackApplication() throws IOException {
		System.out.println("Test case 1 started");
		driver.findElement(By.linkText("Track Your Application")).click();
		System.out.println("Link is clicked");
		Boolean expected = true;
		Boolean applno = driver.findElement(By.id("_Trackapplication_WAR_trackapplicationportlet_visaAppilcationNumber")).isDisplayed();
		Assert.assertEquals(applno, expected);		
	}

	@Test(priority=2)
	public  void contactUsText() throws MalformedURLException {
		System.out.println("Test case 2 started");
		driver.findElement(By.xpath("//a[@class='dropbtn'][normalize-space()='Contact Us']")).click();
		String element =driver.findElement(By.className("icon-txt")).getText();
		Assert.assertEquals(element, "Share your Experience");		
	}

	@Test(priority=3,enabled=false)
	public  void captchaCheck() throws MalformedURLException {
		System.out.println("Test case 3 started");
		driver.navigate().to("https://evisa.rop.gov.om/en/loginportal");
		Boolean captcha = driver.findElement(By.id("_evisaregister_WAR_Evisaregisterportlet_captchaText")).isEnabled();
		Assert.assertEquals(captcha, "true");		
	}

	@Test(priority=4)
	public  void titleCheck() throws MalformedURLException {
		System.out.println("Test case 4 started");
		String actualtitle = driver.getTitle();
		String expectedtitle = "Reach Us - Evisa";
		Assert.assertEquals(actualtitle, expectedtitle);		
	}
	
	@Test(priority=5)
	public  void googleSearch() throws MalformedURLException {
		System.out.println("Test case 5 started");
		driver.get("http://google.com/");
		String actualtitle = driver.getTitle();
		String expectedtitle = "Google";
		Assert.assertEquals(actualtitle, expectedtitle);		
	}

	@AfterTest
	public  void teraDown() {
		driver.quit();
	}

}
