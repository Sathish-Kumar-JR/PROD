package Prod;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import Functions.Functions;
import Functions.Login_Functions;

public class Recurring_Apppointment {	
	static Login_Functions fun = new Login_Functions();
	static Functions Fun = new Functions();
	static String Mail = "sathishtest16@gmail.com";
    static String Recurring_One_Dashboad_Date_Selector = "//*[@id=\"calendar\"]/div[6]/div[1]/div[1]";
    static String Recurring_Two_Dashboad_Date_Selector = "//*[@id=\"calendar\"]/div[6]/div[1]/div[2]";
    static String Recurring_Three_Dashboad_Date_Selector = "//*[@id=\"calendar\"]/div[6]/div[1]/div[3]";
    static String Select_Recurring_Date[] = {Recurring_One_Dashboad_Date_Selector,Recurring_Two_Dashboad_Date_Selector,Recurring_Three_Dashboad_Date_Selector}; 
	
	public static void main(String args[]) throws InterruptedException {
		
		WebDriver driver = new ChromeDriver();
		String Calendar_Link = "https://imeetify.com/testpurplemeet/Online-Calendar";
 		System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\driver\\chromedriver.exe");
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
	    driver.manage().window().maximize();
	    String parentWindowHandle = fun.Login(driver,"Prod","old");
	    driver.findElement(By.xpath("//*[@class=\"sidebar-content mt-3\"]/nav/ul/li[2]/a")).click();
	    driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[2]/div/ul/li[1]/a")).click();
	    
	    WebElement Calendar_Name = driver.findElement(By.xpath("//*[@id=\"calendar\"]"));
	    Select Calendar_Country = new Select(Calendar_Name);
	    Calendar_Country.selectByVisibleText("Online-Calendar");
    
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"demo-2\"]/div[2]/div[1]/table/tbody/tr[2]/td[5]")));
        
        String[] times = {"10:00 AM", "10:30 AM" , "11:00 AM"};
        String F_date ="//*[@id=\"demo-2\"]/div[2]/div[1]/table/tbody/tr[2]/td[1]/div";
        String S_Date = "//*[@id=\"demo-2\"]/div[2]/div[1]/table/tbody/tr[2]/td[2]/div";
        String T_Date = "//*[@id=\"demo-2\"]/div[2]/div[1]/table/tbody/tr[2]/td[3]/div";
        String[] Date = {F_date, S_Date ,T_Date};
        String Fn = "Recurring";
        String Ln = "Appointment";
        String Invitee_Name = Fn+" "+Ln;
        Fun.Recurring_Appointment(driver,times,Date,Fn,Ln,Mail,Calendar_Link,parentWindowHandle);
        driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[1]/div/ul/li[1]")).click();
        for(int i = 0; i< Select_Recurring_Date.length; i++) {
            Fun.Recurring_Appointment_Cancel(driver, Invitee_Name, Select_Recurring_Date[i]);
        }
        Reporter.log("All Appointment Canceled Successfully<br/>");
        System.out.println("All Appointment Canceled Successfully"+"\n"+"\n");
	}	
  }
	

