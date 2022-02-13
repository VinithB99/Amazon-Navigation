package Amazon;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Amazon {
	public static int iBrowserType = 5; //1-Chrome,2-FF,3-Edge
	public static String sURL = "https://www.amazon.in/";
	public static WebDriver driver;
	
	public static void main(String[] args) {
		invokeBrowser();
		windowSettings();
		navigateURL();
		getPageInfo();
		searchProduct("lenovo", "Electronics");		
		getSearchResult();
		//closeBrowser();
	}
	
	public static void invokeBrowser() {
		
		switch (iBrowserType) {
		case 1:
			System.out.println("User option is "+iBrowserType+",So invoking Chrome browser!!!");
			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case 2:
			System.out.println("User option is "+iBrowserType+",So invoking Firefox browser!!!");
			System.setProperty("webdriver.gecko.driver", "./driver/geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case 3:
			System.out.println("User option is "+iBrowserType+",So invoking Edge browser!!!");
			System.setProperty("webdriver.edge.driver", "./driver/msedgedriver.exe");
			driver = new org.openqa.selenium.edge.EdgeDriver();
			break;

		default:
			System.out.println("User option is wrong "+iBrowserType+",So invoking default Chrome browser!!!");
			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
	}
	
	public static void windowSettings() {
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		}
	
	public static void navigateURL() {
		driver.get(sURL);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}
	
	public static void getPageInfo() {
		String title = driver.getTitle();
		String currentUrl = driver.getCurrentUrl();
		System.out.println("Page Title is : "+title);
		System.out.println("Page URL is : "+currentUrl);
	}
	
	public static void searchProduct(String sProduct,String sCat) {
		WebElement oTxt,oDrop,oBtn;
		oTxt = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
		oTxt.sendKeys(sProduct);
		
		oDrop = driver.findElement(By.xpath("//select[@id='searchDropdownBox']"));
		Select oSelect = new Select(oDrop);
		oSelect.selectByVisibleText(sCat);
		System.out.println("Dropdown is multi-select : "+oSelect.isMultiple());
		
		oBtn = driver.findElement(By.id("nav-search-submit-button"));
		oBtn.submit();
		
	}
	
	public static void getSearchResult() {
		WebElement oText,oElement;
		oText = driver.findElement(By.xpath("//div[@class='a-section a-spacing-small a-spacing-top-small']/span"));
		String resultText = oText.getText();
		resultText = resultText.replaceAll("[^0-9]", "").trim();
		int result = Integer.parseInt(resultText);
		if(result!=0) {
			java.util.List<WebElement> List1 = driver.findElements(By.xpath("//div[@class='a-section a-spacing-none a-spacing-top-small']/h2"));
			for(int i=0;i<List1.size();i++) {
				i=i+1;
				oElement = List1.get(i);
				String sName = oElement.findElement(By.xpath("(//div[@class='a-section a-spacing-none a-spacing-top-small'])["+i+"]/h2/a[@class='a-link-normal a-text-normal']")).getText();
				System.out.println(sName);
			}
		}else {
			System.out.println("Search result is zero, Try with different search!!!");
		}
	}
	
	public static void closeBrowser() {
		driver.quit();
	}

}