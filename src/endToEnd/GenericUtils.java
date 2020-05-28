package endToEnd;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GenericUtils {
	
	
	
	/**
	 * @param driver
	 */
	public static void implicitWait(WebDriver driver,int sec) {
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	}
	
	
	/**
	 * @param driver
	 */
	public static void waitForElementVisibility(WebDriver driver,By path) {
		WebDriverWait wait=new WebDriverWait(driver,2);
		wait.until(ExpectedConditions.visibilityOfElementLocated(path));
	}
	
	
	
	
	

}
