package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[normalize-space()='My Account']")
	private WebElement lnkMyaccount;

	@FindBy(xpath = "//a[normalize-space()='Register']")
	private WebElement lnkRegister;

	@FindBy(xpath = "//a[normalize-space()='Login']")
	private WebElement lnkLogin;

	public void clickMyAccount() {
		lnkMyaccount.click();
	}

	public void clickRegister() {
		lnkRegister.click();
	}

	public void clickLogin() {
		lnkLogin.click();
	}
}
