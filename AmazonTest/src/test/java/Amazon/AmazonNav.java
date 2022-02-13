package Amazon;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class AmazonNav {
	public static String sURL = "https://www.amazon.in/";
	public static WebDriver driver;
	public static String oProduct,oPrice;

	public static void main(String[] args) throws InterruptedException {
		navigateURL();
		Move();
		Signin();
		cartValue();
		searchProduct("lenovo","Electronics");
		selectProduct();
		newWindow();
		checkcart();
		signOut();
	}
	public static void navigateURL() {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(sURL);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}

	public static void Move() {
		WebElement oSign,oSelectSignin;
		oSign = driver.findElement(By.xpath("//a[@id='nav-link-accountList']"));
		oSelectSignin = driver.findElement(By.xpath("//div[@id='nav-flyout-ya-signin']//span[text()='Sign in']"));
		Actions oAction = new Actions(driver);
		oAction.moveToElement(oSign).pause(Duration.ofSeconds(2)).moveToElement(oSelectSignin).click().build().perform();
		
	}
	public static void Signin() {
		driver.findElement(By.xpath("//input[@id='ap_email']")).sendKeys("7868023702");
		driver.findElement(By.xpath("//input[@id='continue']")).click();
		driver.findElement(By.xpath("//input[@id='ap_password']")).sendKeys("786802");
		driver.findElement(By.xpath("//input[@id='signInSubmit']")).click();
		String User=driver.findElement(By.xpath("//span[@id='nav-link-accountList-nav-line-1'][contains(text(),'vinith')]")).getText();
		System.out.println("User Name is : "+User);	
		
	}
	public static void cartValue() {
		String oCart=driver.findElement(By.xpath("//span[@id='nav-cart-count']")).getText();
		System.out.println("Cart Value is : "+oCart);
	}
	public static void searchProduct(String sProduct,String sCat) {
		WebElement oTxt,oDrop,oBtn;
		oTxt = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
		oTxt.sendKeys(sProduct);		
		oDrop = driver.findElement(By.xpath("//select[@id='searchDropdownBox']"));
		Select oSelect = new Select(oDrop);
		oSelect.selectByVisibleText(sCat);		
		oBtn = driver.findElement(By.id("nav-search-submit-button"));
		oBtn.submit();
		
	}
	public static void selectProduct() {
		oProduct=driver.findElement(By.xpath("(//h2[@class='a-size-mini a-spacing-none a-color-base s-line-clamp-4'])[1]")).getText();
		oPrice=driver.findElement(By.xpath("(//span[@class='a-price-whole'])[1]")).getText();
		System.out.println(oProduct);
		System.out.println(oPrice);
		driver.findElement(By.xpath("(//h2[@class='a-size-mini a-spacing-none a-color-base s-line-clamp-4'])[1]/a")).click();
		
	}
	public static void newWindow() throws InterruptedException {
		driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		String newName=driver.findElement(By.xpath("//h1[@id='title']")).getText();
		String newPrice=driver.findElement(By.xpath("//span[@id='priceblock_ourprice']")).getText();		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		validateProduct(newName,newPrice);
		driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();
		
	}
	public static void checkcart() {
		cartValue();
		driver.findElement(By.xpath("//a[@id='nav-cart']")).click();
		String cartName=driver.findElement(By.xpath("(//a[@class='a-link-normal sc-product-link'])[6]")).getText();
		String cartPrice=driver.findElement(By.xpath("(//span[@class='currencyINR'])[2]")).getText();
		validateProduct(cartName,cartPrice);
		driver.findElement(By.xpath("//input[@value='Delete']")).click();
		cartValue();
		driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
		
	}
	public static void signOut() {
		WebElement oSign,oSelectSignOut;
		oSign = driver.findElement(By.xpath("//a[@id='nav-link-accountList']"));
		oSelectSignOut = driver.findElement(By.xpath("//a[@id='nav-item-signout']/span[contains(text(),'Sign Out')]"));
		Actions oAction = new Actions(driver);
		oAction.moveToElement(oSign).pause(Duration.ofSeconds(2)).moveToElement(oSelectSignOut).click().build().perform();
		System.out.println("Sighned out");
		
	}
	public static void validateProduct(String Name,String Price) {
		if(Name.contains(oProduct)||Price.contains(oPrice)) {
			System.out.println("This is currect product");
		}else {
			System.out.println("This is wrong product");
			
		}
		
	}
	
	
}
