//package testCases;
//
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import pageObjects.AccountRegistrationPage;
//import pageObjects.HomePage;
//import testBase.BaseClass;
//
//public class TC001_AccountRegistrationTest extends BaseClass {
//	@Test(groups = { "regression", "master" })
//	public void verify_account_registration() {
//		logger.info("**** Started TC001_AccountRegistrationTest ****");
//
//		try {
//			HomePage hp = new HomePage(driver);
//			hp.clickMyAccount();
//			logger.info("clicked on Myaccount link");
//
//			hp.clickRegister();
//			logger.info("clicked on Register link");
//
//			AccountRegistrationPage arp = new AccountRegistrationPage(driver);
//			logger.info("providing customer details");
//
//			arp.setFirstName(randStr().toUpperCase());
//			arp.setLastName(randStr().toUpperCase());
//			arp.setEmail(randStr() + "@gmail.com");
//			arp.setTelephone(randTel());
//			arp.setPassword("password");
//			arp.setConfirmPassword("password");
//			arp.setPrivacyPolicy();
//			arp.clickContinue();
//
//			logger.info("... Validating expected message ...");
//			if (arp.getConfirmationMsg().equals("Your Account Has Been Created!")) {
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
//		logger.info("**** Finished TC001_AccountRegistrationTest ****");
//	}
//}

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups = { "regression", "master" })
	public void verify_account_registration() {
		logger.info("**** Started TC001_AccountRegistrationTest ****");

		try {
			HomePage hp = new HomePage(getDriver());
			hp.clickMyAccount();
			logger.info("Clicked on My Account link");

			hp.clickRegister();
			logger.info("Clicked on Register link");

			AccountRegistrationPage arp = new AccountRegistrationPage(getDriver());
			logger.info("Providing customer details");

			arp.setFirstName(randStr().toUpperCase());
			arp.setLastName(randStr().toUpperCase());
			arp.setEmail(randStr() + "@gmail.com");
			arp.setTelephone(randTel());
			arp.setPassword("password");
			arp.setConfirmPassword("password");
			arp.setPrivacyPolicy();
			arp.clickContinue();

			logger.info("Validating expected confirmation message...");

			String expectedMessage = "Your Account Has Been Created!";
			if (arp.getConfirmationMsg().equals(expectedMessage)) {
				logger.info("Test passed..");
				Assert.assertTrue(true, "Account created successfully. Confirmation message matched.");
			} else {
				logger.error("Test failed.. Confirmation message mismatch");
				logger.debug("Expected message: " + expectedMessage + ", but got: " + arp.getConfirmationMsg());
				Assert.fail("Test failed - Confirmation message did not match.");
			}
		} catch (Exception e) {
			logger.error("Exception encountered during account registration: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}

		logger.info("**** Finished TC001_AccountRegistrationTest ****");
	}
}