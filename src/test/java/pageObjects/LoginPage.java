package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-email']")
	private WebElement txtEmail;

	@FindBy(xpath = "//input[@id='input-password']")
	private WebElement txtPassword;

	@FindBy(xpath = "//input[@value='Login']")
	private WebElement btnLogin;

	public void setEmail(String str) {
		txtEmail.sendKeys(str);
	}

	public void setPassword(String str) {
		txtPassword.sendKeys(str);
	}

	public void clickLogin() {
		btnLogin.click();
	}
}
