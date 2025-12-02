package marathan2;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TataCliq {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("guest");
		options.addArguments("-start-maximized");
		
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		driver.get("https://www.tatacliq.com/");
		
		driver.findElement(By.xpath("//button[contains(text(),'No, Thanks')]")).click();
		WebElement brandsHover = driver.findElement(By.xpath("//div[text()='Brands']"));
		Actions action = new Actions(driver);
		action.moveToElement(brandsHover).pause(3000).build().perform();

		WebElement watchHover = driver.findElement(By.xpath("//div[@aria-label='Watches & Accessories button, Press right arrow or Enter to expand']"));
		action.moveToElement(watchHover).pause(3000).build().perform();
		
		driver.findElement(By.xpath("//div[text()='Casio']")).click();
		
		driver.findElement(By.xpath("//select[@class='SelectBoxDesktop__hideSelect']")).click();
		driver.findElement(By.xpath("(//select[@class='SelectBoxDesktop__hideSelect']/option)[4]")).click();
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[text()='Women']")).click();
		
		//String priceOfFirstElement = driver.findElement(By.xpath("(//div[@class='ProductDescription__priceHolder']//h3)[1]")).getText();
		//System.out.println("Price of first element is : " +priceOfFirstElement);
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String priceOfFirstElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ProductDescription__priceHolder']//h3)[1]"))).getText();
		System.out.println("Price of the first element " +priceOfFirstElement);
		WebElement firstElement = driver.findElement(By.xpath("(//div[@class='ProductDescription__priceHolder']//h3)[1]"));
		WebElement lastElement = driver.findElement(By.xpath("(//div[@class='ProductDescription__priceHolder']//h3)[40]"));
		
		Actions action1 = new Actions(driver);
		action1.scrollToElement(lastElement);
		
		int size = driver.findElements(By.xpath("//div[@class='ProductDescription__priceHolder']//h3")).size();
		System.out.println("Prices of all watches are: ");
		for (int i=1; i<=size; i++)
		{
			WebElement desc = driver.findElement(By.xpath("(//div[@class='ProductDescription__content']//h2)[" +i+ "]"));
			String descValue = desc.getText();
			WebElement price1 = driver.findElement(By.xpath("(//div[@class='ProductDescription__priceHolder']//h3)[" +i+ "]"));
			String pricevalue = price1.getText();
			System.out.println(descValue+ " : " +pricevalue);
		}
		WebElement firstWatchClick = driver.findElement(By.xpath("//div[@class='ProductModule__flagHolder']"));
		driver.executeScript("arguments[0].click()", firstWatchClick);
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> windowHandleList = new ArrayList<>(windowHandles);
		driver.switchTo().window(windowHandleList.get(1));
		
		WebElement priceOfFirstProduct1 = driver.findElement(By.xpath("//div[@class='ProductDetailsMainCard__price']/h3"));
		String text = priceOfFirstProduct1.getText();
		if(text.contains(priceOfFirstElement))
		{
			System.out.println("Price of first product is compared and price is : " +text);
		}
		else
		{
			System.out.println("incorrect");
		}
		
		driver.findElement(By.xpath("//span[text()='ADD TO BAG']")).click();
		WebElement count = driver.findElement(By.xpath("//span[@class='DesktopHeader__cartCount']"));
		String addToCartValue = count.getText();
		System.out.println("The count of cart value is : " +addToCartValue);
		
		File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File destination = new File("./snap/addToCart.png");
		FileUtils.copyFile(source, destination);
		
		Thread.sleep(3000);
		driver.quit();
	}
}
