package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegistrationPage extends BasePage {
//	private WebDriver driver;

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
//		this.driver = driver;
	}

	@FindBy(xpath = "//input[@id='input-firstname']")
	private WebElement txtFirstname;

	@FindBy(xpath = "//input[@id='input-lastname']")
	private WebElement txtLastname;

	@FindBy(xpath = "//input[@id='input-email']")
	private WebElement txtEmail;

	@FindBy(xpath = "//input[@id='input-telephone']")
	private WebElement txtTelephone;

	@FindBy(xpath = "//input[@id='input-password']")
	private WebElement txtPassword;

	@FindBy(xpath = "//input[@id='input-confirm']")
	private WebElement txtConfirmPassword;

	@FindBy(xpath = "//input[@name='agree']")
	private WebElement chkdPolicy;

	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement btnContinue;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	private WebElement msgConfirmation;

	public void setFirstName(String fname) {
		txtFirstname.sendKeys(fname);
	}

	public void setLastName(String lname) {
		txtLastname.sendKeys(lname);
	}

	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}

	public void setTelephone(String tel) {
		txtTelephone.sendKeys(tel);
	}

	public void setPassword(String pwd) {
		txtPassword.sendKeys(pwd);
	}

	public void setConfirmPassword(String pwd) {
		txtConfirmPassword.sendKeys(pwd);
	}

	public void setPrivacyPolicy() {
		chkdPolicy.click();
	}

	public void clickContinue() {
		/* sol1 */
		btnContinue.click();

		/* sol2 */
//		btnContinue.submit();

		/* sol3 */
//		Actions act = new Actions(driver);
//		act.moveToElement(btnContinue).click().perform();

		/* sol4 */
//		JavascriptExecutor jse = (JavascriptExecutor) driver;
//		jse.executeScript("arguments[0].click();", btnContinue);

		/* sol5 */
//		btnContinue.sendKeys(Keys.RETURN);

		/* sol6 */
//		WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(10));
//		myWait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
	}

	public String getConfirmationMsg() {
		try {
			return msgConfirmation.getText();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}