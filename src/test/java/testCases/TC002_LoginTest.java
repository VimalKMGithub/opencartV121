//package testCases;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import pageObjects.HomePage;
//import pageObjects.LoginPage;
//import pageObjects.MyAccountPage;
//import testBase.BaseClass;
//
//public class TC002_LoginTest extends BaseClass {
//	@Test(groups = { "sanity", "master" })
//	public void verify_login() {
//		logger.info("**** Started TC002_LoginTest ****");
//
//		try {
//			HomePage hp = new HomePage(driver);
//			hp.clickMyAccount();
//			logger.info("clicked on Myaccount link");
//
//			hp.clickLogin();
//			logger.info("clicked on Login link");
//
//			LoginPage lp = new LoginPage(driver);
//			logger.info("providing login details");
//
//			lp.setEmail(properties.getProperty("email"));
//			lp.setPassword(properties.getProperty("password"));
//			lp.clickLogin();
//
//			MyAccountPage myAcPage = new MyAccountPage(driver);
//			logger.info("... Validating expected message ...");
//
//			if (driver.getTitle().equals(properties.getProperty("loginedMyAccTitle"))) {
//				logger.info("Test passed..");
//				Assert.assertTrue(true);
//			} else {
//				logger.error("Test failed..");
//				logger.debug("Debug logs..");
//				Assert.assertTrue(false);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//
//		logger.info("**** Finished TC002_LoginTest ****");
//	}
//}

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	@Test(groups = { "sanity", "master" })
	public void verify_login() {
		logger.info("**** Started TC002_LoginTest ****");

		try {
			HomePage hp = new HomePage(getDriver());
			hp.clickMyAccount();
			logger.info("Clicked on My Account link");

			hp.clickLogin();
			logger.info("Clicked on Login link");

			LoginPage lp = new LoginPage(getDriver());
			logger.info("Providing login details");

			lp.setEmail(properties.getProperty("email"));
			lp.setPassword(properties.getProperty("password"));
			lp.clickLogin();

			logger.info("Validating expected message...");
			String expectedTitle = properties.getProperty("loginedPageTitle");
			if (getDriver().getTitle().equals(expectedTitle)) {
				logger.info("Test passed..");
				Assert.assertTrue(true, "Login successful - Page title matched.");
			} else {
				logger.error("Test failed.. Page title mismatch");
				logger.debug("Expected title: " + expectedTitle + ", but got: " + getDriver().getTitle());
				Assert.fail("Test failed - Page title did not match.");
			}
		} catch (Exception e) {
			logger.error("Exception encountered during login test: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception.");
		}

		logger.info("**** Finished TC002_LoginTest ****");
	}
}