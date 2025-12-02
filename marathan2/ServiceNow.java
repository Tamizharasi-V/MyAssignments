package marathan2;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.sukgu.Shadow;

public class ServiceNow {

	public static void main(String[] args) throws InterruptedException, IOException {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://dev181504.service-now.com/");
		driver.findElement(By.xpath("//input[@id='user_name']")).sendKeys("admin");
		driver.findElement(By.xpath("//input[@id='user_password']")).sendKeys("F/*jyM6Q6pHh");
		driver.findElement(By.xpath("//button[@id='sysverb_login']")).click();
		
		Shadow shadow = new Shadow(driver);
		shadow.setImplicitWait(7);
		shadow.findElementByXPath("//div[@role='menuitem']").click();
		Thread.sleep(3000);
		WebElement serviceClick = shadow.findElementByXPath("//span[text()='Service Catalog']");
		driver.executeScript("arguments[0].click()", serviceClick);
		
		/*WebElement frame1 = driver.findElement(By.xpath("//iframe[@id='gsft_main']"));
		driver.switchTo().frame(frame1);
		driver.findElement(By.xpath("(//a[@target='_self']/h2)[8]")).click();*/
		Shadow shadow1 = new Shadow(driver);
		shadow1.setImplicitWait(4);
		WebElement frame = shadow1.findElementByXPath("//iframe[@id='gsft_main']");
		driver.switchTo().frame(frame);
		//shadow1.findElementByXPath("(//a[@target='_self']/h2)[8]").click();
		driver.findElement(By.xpath("(//a[@target='_self']/h2)[8]")).click();
		
		driver.findElement(By.xpath("//strong[text()='Apple iPhone 13 pro']")).click();
		driver.findElement(By.xpath("(//span[@class='input-group-radio'])[1]")).click();
		driver.findElement(By.xpath("//input[@class='cat_item_option sc-content-pad form-control']")).sendKeys("99");
		WebElement selectDD = driver.findElement(By.xpath("//select[@class='form-control cat_item_option ']"));
		selectDD.click();
		Select select = new Select(selectDD);
		select.selectByValue("unlimited");
		driver.findElement(By.xpath("//label[text()='Sierra Blue']")).click();
		driver.findElement(By.xpath("//button[@name='oi_order_now_button']")).click();
		
		String orderPlaced = driver.findElement(By.xpath("//dd[b]")).getText();
		System.out.println("Order Placed: " +orderPlaced);
		String requestNo = driver.findElement(By.xpath("//a[b]")).getText();
		System.out.println("Request No: " +requestNo);
		
		File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File destination = new File("./snap/serviceNowEvidence.png");
		FileUtils.copyFile(source, destination);
		
		Thread.sleep(3000);
		driver.quit();
	}

}
