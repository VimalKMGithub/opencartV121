//package testCases;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import pageObjects.HomePage;
//import pageObjects.LoginPage;
//import pageObjects.MyAccountPage;
//import testBase.BaseClass;
//import utilities.DataProviders;
//
//public class TC003_LoginDDT extends BaseClass {
//	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = { "dataDriven", "master" })
//	public void verify_LoginDDT(String userName, String pwd, String exp) {
//		logger.info("**** Started TC003_LoginDDT ****");
//
//		try {
//			HomePage hp = new HomePage(driver);
//			hp.clickMyAccount();
//			logger.info("clicked on Myaccount link");
//			hp.clickLogin();
//			logger.info("clicked on Login link");
//
//			LoginPage lp = new LoginPage(driver);
//			logger.info("providing login details");
//
//			lp.setEmail(userName);
//			lp.setPassword(pwd);
//			lp.clickLogin();
//
//			logger.info("... Validating expected message ...");
//			if (exp.equalsIgnoreCase("valid")) {
//				if (driver.getTitle().equals("My Account")) {
//					MyAccountPage myAcPage = new MyAccountPage(driver);
//					myAcPage.clickLogout();
//					Assert.assertTrue(true);
//				} else {
//					Assert.assertTrue(false);
//				}
//			}
//			if (exp.equalsIgnoreCase("invalid")) {
//				if (driver.getTitle().equals("My Account")) {
//					MyAccountPage myAcPage = new MyAccountPage(driver);
//					myAcPage.clickLogout();
//					Assert.assertTrue(false);
//				} else {
//					Assert.assertTrue(true);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		logger.info("**** Finished TC003_LoginDDT ****");
//	}
//}

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = { "dataDriven", "master" })
	public void verify_LoginDDT(String userName, String pwd, String exp) {
		logger.info("**** Started TC003_LoginDDT ****");

		try {
			HomePage hp = new HomePage(getDriver());
			hp.clickMyAccount();
			logger.info("Clicked on My Account link");

			hp.clickLogin();
			logger.info("Clicked on Login link");

			LoginPage lp = new LoginPage(getDriver());
			logger.info("Providing login details for username: " + userName);

			lp.setEmail(userName);
			lp.setPassword(pwd);
			lp.clickLogin();

			logger.info("Validating expected outcome...");

			if (exp.equalsIgnoreCase("valid")) {
				if (getDriver().getTitle().equals("My Account")) {
					MyAccountPage myAcPage = new MyAccountPage(getDriver());
					myAcPage.clickLogout();
					logger.info("Login successful, logged out successfully.");
					Assert.assertTrue(true, "Login successful and user is logged out.");
				} else {
					logger.error("Login failed, expected 'My Account' page but got: " + getDriver().getTitle());
					Assert.fail("Login failed, expected 'My Account' page but got: " + getDriver().getTitle());
				}
			}

			if (exp.equalsIgnoreCase("invalid")) {
				if (getDriver().getTitle().equals("My Account")) {
					MyAccountPage myAcPage = new MyAccountPage(getDriver());
					myAcPage.clickLogout();
					logger.error("Login should have failed but user was redirected to 'My Account' page.");
					Assert.fail("Login should have failed but user was redirected to 'My Account' page.");
				} else {
					logger.info("Login failed as expected.");
					Assert.assertTrue(true, "Login failed as expected.");
				}
			}
		} catch (Exception e) {
			logger.error("An error occurred during login: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}

		logger.info("**** Finished TC003_LoginDDT ****");
	}
}