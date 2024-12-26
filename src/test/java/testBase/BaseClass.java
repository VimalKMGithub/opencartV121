//package testBase;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.time.Duration;
//import java.util.Date;
//import java.util.Properties;
//
//import org.apache.commons.lang3.RandomStringUtils;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.testng.Assert;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Parameters;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//public class BaseClass {
//	protected static WebDriver driver;
//	protected Logger logger;
//	protected Properties properties;
//	private FileInputStream fi;
//
//	@BeforeClass(groups = { "sanity", "regression", "master", "dataDriven" })
//	@Parameters({ "os", "browser" })
//	public void setup(String os, String br) {
//		logger = LogManager.getLogger(this.getClass());
//
//		switch (br.toLowerCase()) {
//		case "chrome":
//			driver = new ChromeDriver();
//			break;
//		case "edge":
//			driver = new EdgeDriver();
//			break;
//		case "firefox":
//			driver = new FirefoxDriver();
//			break;
//		default:
//			Assert.fail();
//			break;
//		}
//
//		driver.manage().deleteAllCookies();
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//
//		String fname = "config.properties";
//		String fpath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + fname;
//		try {
//			fi = new FileInputStream(fpath);
//			properties = new Properties();
//			properties.load(fi);
//			driver.get(properties.getProperty("appUrl"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		driver.manage().window().maximize();
//	}
//
//	@AfterClass(groups = { "sanity", "regression", "master", "dataDriven" })
//	public void tearDown() {
//		if (driver != null) {
//			driver.quit();
//		}
//		if (fi != null) {
//			try {
//				fi.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	protected String randStr() {
//		String str = RandomStringUtils.randomAlphabetic(5);
//		return str;
//	}
//
//	protected String randTel() {
//		String str = RandomStringUtils.randomNumeric(10);
//		return str;
//	}
//
//	public String captureScreen(String tName) throws IOException {
//		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
//		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
//
//		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tName + "_" + timeStamp + ".png";
//		File targetFile = new File(targetFilePath);
//
//		sourceFile.renameTo(targetFile);
//		return targetFilePath;
//	}
//}

package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {
	private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
	protected Logger logger;
	protected Properties properties;
	private FileInputStream fi;

	public static WebDriver getDriver() {
		return threadDriver.get();
	}

	private static void setDriver(WebDriver driver) {
		threadDriver.set(driver);
	}

	private static void removeDriver() {
		threadDriver.remove();
	}

	@BeforeClass(groups = { "sanity", "regression", "master", "dataDriven" })
	@Parameters({ "os", "browser" })
	public void setup(String os, String br) {
		logger = LogManager.getLogger(this.getClass());

		String fname = "config.properties";
		String fpath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + fname;
		try {
			fi = new FileInputStream(fpath);
			properties = new Properties();
			properties.load(fi);
		} catch (IOException e) {
			logger.error("Failed to load config.properties: " + e.getMessage());
			throw new RuntimeException("Config file loading failed.", e);
		}

		WebDriver driverInstance = null;

		if (properties.getProperty("execution_env").equalsIgnoreCase("remote")) {
			logger.info("Setting up remote WebDriver for browser: " + br + " on OS: " + os);
			DesiredCapabilities cap = new DesiredCapabilities();

			if (os.equalsIgnoreCase("windows")) {
				cap.setPlatform(Platform.WIN11);
			} else if (os.equalsIgnoreCase("mac")) {
				cap.setPlatform(Platform.MAC);
			} else if (os.equalsIgnoreCase("Linux")) {
				cap.setPlatform(Platform.LINUX);
			} else {
				System.out.println("No matching os..");
				logger.error("Invalid OS specified: " + os);
				Assert.fail("Invalid OS specified.");
			}

			switch (br.toLowerCase()) {
			case "chrome":
				cap.setBrowserName("chrome");
				break;
			case "edge":
				cap.setBrowserName("MicrosoftEdge");
				break;
			case "firefox":
				cap.setBrowserName("firefox");
				break;
			default:
				System.out.println("No matching browser..");
				logger.error("Invalid browser specified: " + br);
				Assert.fail("Invalid browser specified.");
				break;
			}

			try {
				driverInstance = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
				logger.info("Remote WebDriver setup successful.");
			} catch (Exception e) {
				logger.error("Failed to initialize remote WebDriver: " + e.getMessage());
				throw new RuntimeException("Remote WebDriver setup failed.", e);
			}
		} else if (properties.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch (br.toLowerCase()) {
			case "chrome":
				driverInstance = new ChromeDriver();
				break;
			case "edge":
				driverInstance = new EdgeDriver();
				break;
			case "firefox":
				logger.info("Setting up Firefox with WebDriver BiDi.");
				FirefoxOptions options = new FirefoxOptions();
				options.setCapability("moz:webdriverClick", true);
				GeckoDriverService service = new GeckoDriverService.Builder().usingAnyFreePort().build();
				driverInstance = new FirefoxDriver(service, options);
				break;
			default:
				Assert.fail("Invalid browser specified: " + br);
				return;
			}
		}

		setDriver(driverInstance);
		logger.info("WebDriver initialized successfully.");
		getDriver().manage().deleteAllCookies();
		logger.info("All cookies cleared.");
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		logger.info("Implicit wait set to 10 seconds.");

		try {
			getDriver().get(properties.getProperty("appUrl"));
			logger.info("Navigated to the application URL: " + properties.getProperty("appUrl"));
		} catch (Exception e) {
			logger.error("Failed to navigate to the application URL: " + properties.getProperty("appUrl"), e);
			e.printStackTrace();
			Assert.fail("Test setup failed: Unable to open the application URL.");
		}

		getDriver().manage().window().maximize();
	}

	@AfterClass(groups = { "sanity", "regression", "master", "dataDriven" })
	public void tearDown() {
		if (getDriver() != null) {
			getDriver().quit();
			removeDriver();
		}
		if (fi != null) {
			try {
				fi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected String randStr() {
		return RandomStringUtils.randomAlphabetic(5);
	}

	protected String randTel() {
		return RandomStringUtils.randomNumeric(10);
	}

	public String captureScreen(String tName) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tName + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);

		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}
}