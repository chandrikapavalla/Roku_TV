package endToEnd;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Roku_TV extends GenericUtils{
public static final String email="nagandla.narayana@gmail.com";
public static final String password="Aspire@123";
public static final String roku_url="https://www.roku.com/en-gb/";
	
	
	
	public static void main(String[] args) throws Throwable {
		
		// TODO Auto-generated method stub
		try {
			// invoke chrome driver
			System.setProperty("webdriver.chrome.driver","E:/drivers/chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			// open url
			driver.navigate().to(roku_url);
           //wait method
			implicitWait(driver,2);
			
			Actions a = new Actions(driver);
			// signin
			rokuSignIn(driver, a);
            //Check channel store functionality in WhatToWatch Menu 
			menuWhatToWatchChannelstore(driver, a);
			// Menulist - products
			checkRokuDemoVideo(driver, a);
			// signout
			rokuSignout(driver);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

	/**
	 * @param driver
	 * @param a
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	private static void checkRokuDemoVideo(WebDriver driver, Actions a)
			throws InterruptedException, AWTException {
		rokuMenuList(driver, a, "Products",
				"a[aria-label='Roku TV™']>aside+span");

		// click on video
		WebElement video = driver.findElement(By
				.cssSelector("section[data-id='tmgxf']>div>a>div>div>div"));
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", video);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].click();", video);
		implicitWait(driver, 15);
		driver.switchTo().frame(driver.findElement(By
				.cssSelector("#productVideo_pe5PMsYwvQo")));				
		WebElement pause = driver.findElement(By
				.cssSelector("a[role='button']+button"));
		Robot rb = new Robot();

		if (pause.isEnabled()) {
			pause.sendKeys(Keys.ENTER);

		}

		driver.switchTo().defaultContent();

		WebElement close = driver.findElement(By
				.cssSelector("button[class='video-modal-close']"));
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].click();", close);
	}

	/**
	 * 
	 * @param driver
	 * @param a
	 * @throws InterruptedException
	 */
	private static void rokuMenuList(WebDriver driver, Actions a,String menuName,String subMenu)
 throws InterruptedException {
		List<WebElement> menuList1 = driver.findElements(By
				.cssSelector(".desktop-menu>li"));
		Iterator itr1 = menuList1.iterator();
		while (itr1.hasNext()) {
			WebElement menu1 = (WebElement) itr1.next();
			String menuname1 = menu1.getText();
			System.out.println(menuname1);

			if (menuname1.equals(menuName)) {
				a.moveToElement(menu1).build().perform();
				implicitWait(driver, 2);

				a.moveToElement(driver.findElement(By.cssSelector(subMenu)))
						.click().build().perform();
				
				break;
			}

		}
	}

	/**
	 * @param driver
	 */
	private static void rokuSignout(WebDriver driver) {
		WebElement signout = driver
				.findElement(By
						.cssSelector("li[data-id='desktop-signed-in-user']>img"));
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", signout);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", signout);
		WebElement logout=driver.findElement(By.cssSelector("li[data-id='signout']>a"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", logout);
	}

	/**
	 * @param driver
	 * @return
	 * @throws InterruptedException
	 */
	private static  void rokuSignIn(WebDriver driver,Actions a)
			throws InterruptedException {
		
		WebElement ele = driver.findElement(By.linkText("Sign in"));
		a.moveToElement(ele).click().build().perform();
		implicitWait(driver, 2);

		driver.findElement(By.name("email")).sendKeys(
				email);
		driver.findElement(By.name("password")).sendKeys(password);

		driver.switchTo()
				.frame(driver.findElement(By
						.cssSelector("div[class='Roku-Captcha'] iframe[role='presentation']")));

		driver.findElement(By.cssSelector("#recaptcha-anchor")).click();
		implicitWait(driver, 2);
		driver.switchTo().defaultContent();
		WebElement scroll = driver.findElement(By
				.cssSelector(".signin-button"));
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", scroll);
	    
		//explicit wait
		waitForElementVisibility(driver,By
				.cssSelector(".signin-button"));
	
		scroll.click();
		

		implicitWait(driver, 2);
		System.out.println("Sign in successfull");
		
	}



	

	private static void menuWhatToWatchChannelstore(WebDriver driver, Actions a)
			throws InterruptedException {
		
		// menu list
		rokuMenuList(driver, a, "What to watch", ".nav__submenu>li+li");
		WebElement 
		addchannel = driver.findElement(By
				.cssSelector("a[title='STV Player']>div>div>img"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				addchannel);
		implicitWait(driver, 2);
		driver.findElement(
				By.xpath("//img[@alt='STV Player']//ancestor::div[@class='container row']//button"))
				.click();
		
		implicitWait(driver, 2);

		String link1 = driver.getWindowHandle();
		WebElement buy = driver.findElement(By
				.cssSelector("button[title='Buy']"));
		// driver.findElement(By.cssSelector("//*[@class='modal-dialog']//ancestor::div[@class='modal-dialog']//button[@title='Buy']"));
		a.moveToElement(buy).click().build().perform();

		String link = driver.getWindowHandle();
		driver.switchTo().window(link);
		driver.switchTo().window(link1);
		WebElement cancel = driver.findElement(By
				.cssSelector("button[title='Cancel']"));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				cancel);

		driver.findElement(By.linkText("Return to all channels")).click();
	}

}
