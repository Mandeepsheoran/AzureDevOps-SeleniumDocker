package dockerizedSelenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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

	 //Disabling the firefox test cases as parallel execution on azure is under paid plan

//	@Parameters("browser")
	@BeforeTest
	public  void tearUp() throws MalformedURLException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
//	if(browser.equalsIgnoreCase("chrome")) {
	//	WebDriverManager.chromedriver().setup();
	//	driver = new ChromeDriver();		
//	}
		//else if(browser.equalsIgnoreCase("firefox")) {
	//	WebDriverManager.firefoxdriver().setup();
	//	driver = new FirefoxDriver();
		 
//	}
	
		driver.get("https://evisa.rop.gov.om/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test(priority=1)
	public  void eVisaTrackApplication() throws MalformedURLException {
		System.out.println("Test case 1 started");
		driver.findElement(By.linkText("Track Your Application")).click();
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
	
	@AfterTest
	public  void teraDown() {
		driver.quit();
	}

}
