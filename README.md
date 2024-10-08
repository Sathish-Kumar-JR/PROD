package Prod;
import java.util.List;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import Functions.Functions;
import Functions.Login_Functions;

public class Time_Zone {
    static int day = 4;
    static int month = 11;
    static WebDriver driver;
    static Login_Functions Login = new Login_Functions();
    static String Time_Zone_Calendar_Link = "https://imeetify.com/testpurplemeet/Time-Zone";
    static String Date = "//div[@class='span' and contains(text(), '" + day + "')]";
    static String Meeting_time = "11:30 PM";
	static String dayPart;
	static String timePart;
	static String Country_Name;
	static String parentWindowHandle;
    static String ATZ = " Hawaii";
	 
	public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://imeetify.com/login");
        Thread.sleep(2500);
        driver.findElement(By.id("email")).sendKeys("imeetifydemo@gmail.com");
        driver.findElement(By.id("password")).sendKeys("Welcome@123");
        driver.findElement(By.id("rememberMe")).click();
//        Thread.sleep(2500);
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div/form/div[5]/button")).click();
        parentWindowHandle = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(Time_Zone_Calendar_Link); 
        Object[] Appointment_Timezone = Functions.Appointment(driver,Date,Meeting_time,ATZ);
        int ab = Appointment_Timezone.length;
        System.out.println("Appointment Time Zone : " + Appointment_Timezone[0] + "\n");
        driver.get(Time_Zone_Calendar_Link);
        Thread.sleep(3000);
        WebElement Country1 = driver.findElement(By.id("countryid"));
        Select Country_Select = new Select(Country1);
        Country_Select.selectByVisibleText((String) Appointment_Timezone[1]);
        Thread.sleep(1500);
        WebElement next1 = driver.findElement(By.xpath("//*[@id=\"demo-2\"]/div[3]/div[1]/table/thead/tr[2]/th[3]"));
        next1.click();
        Thread.sleep(3000);
        driver.findElement(By.xpath(Date)).click();
        Thread.sleep(1500);
        WebElement Block_Slot = driver.findElement(By.id(Meeting_time));
        String classAttributeValue = Block_Slot.getAttribute("class");
        if (classAttributeValue.contains("disabled-sec")) {
         WebElement countryDropdown = driver.findElement(By.id("countryid"));
        Select select = new Select(countryDropdown);
        List<WebElement> options = select.getOptions();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        for (int i = 1; i < options.size(); i++) {
            boolean staleElement = true;
            int retries = 3;
            while (staleElement && retries > 0) {
                try {
                    countryDropdown = driver.findElement(By.id("countryid"));
                    select = new Select(countryDropdown);
                    options = select.getOptions();
                    select.selectByIndex(i);
                    wait.until(ExpectedConditions.visibilityOf(options.get(i)));
                    String country = options.get(i).getText();
                    System.out.println("Country: " + country );
	                WebElement Current_TimeZone = driver.findElement(By.id("endusercountry"));
                  	String cTimezone = Current_TimeZone.getAttribute("Value");
                  	System.out.println("Verify Time Zone : " + cTimezone);
                  	WebElement CD = driver.findElement(By.xpath("//*[@id=\"select2-countryid-container\"]"));
                  	String Current_Drop_Down_Value = CD.getText();
                  	System.out.println("Current_Drop_Down_Value : " + Current_Drop_Down_Value);
                  	Functions.TimeZone_Convert(driver,Appointment_Timezone[0],cTimezone,Meeting_time,day, month);
                    staleElement = false; // Successfully processed without exception
                } catch (StaleElementReferenceException e) {
                    retries--;
                    if (retries == 0) {
                        System.out.println("Failed to process option: " + options.get(i).getText());
                        throw e; 
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        driver.quit();
    }

	}
}

 

