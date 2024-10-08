package Prod;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Functions.Functions;
import Functions.Login_Functions;


public class TestNG {
    WebDriver driver;
    String parentWindowHandle;
    String Invitee_Name;
    String Meeting_ID;
    String reUrl;
    Functions fun = new Functions();
    Login_Functions Login = new Login_Functions();
    String Email = "sathishtest16@gmail.com";
    String Date_Selector = "//*[@id=\"demo-2\"]/div[2]/div[1]/table/tbody/tr[2]/td[3]"; 
    String BaseLink = "https://imeetify.com/testpurplemeet/";
    String Online_link = (BaseLink + "Online-Calendar");
    String Inperson_link = (BaseLink +"Inperson-Calendar");
    String PhoneCall_link = (BaseLink + "OverThePhoneCalendar");
    String C_online = (BaseLink + "C-online");
    String C_Inperson = (BaseLink + "Inperson-Conflict");
    String C_phone = (BaseLink + "C-OverThePhone");
    String Outlook_ME = (BaseLink + "Outlook-ME");
    String Outloo_CA = (BaseLink + "Outlook-CA");
    String Calendar_link =(BaseLink + "Approval-Pending");
    String Hybrid_Calendar = (BaseLink + "Hybrid-Calendar");
    
    // Recurring Appintments Details
    static String FD = "1";
    static String SD = "2";
    static String TD = "3";
    
    static String Recurring_One_Dashboad_Date_Selector = "//div[@class='day']//div[@class='day-number ' and normalize-space(text())='" + FD + "']";
    static String Recurring_Two_Dashboad_Date_Selector = "//div[@class='day']//div[@class='day-number ' and normalize-space(text())='" + SD + "']";
    static String Recurring_Three_Dashboad_Date_Selector = "//div[@class='day']//div[@class='day-number ' and normalize-space(text())='" + TD + "']";
    static String Select_Recurring_Date[] = {Recurring_One_Dashboad_Date_Selector,Recurring_Two_Dashboad_Date_Selector,Recurring_Three_Dashboad_Date_Selector};

    String F_date ="//*[@class=\"span\" and normalize-space(text())="+FD+"]";
    String S_Date = "//*[@class=\"span\" and normalize-space(text())="+SD+"]";
    String T_Date = "//*[@class=\"span\" and normalize-space(text())="+TD+"]";
    

     
    @BeforeClass
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://imeetify.com/login");
        Thread.sleep(2500);
        driver.findElement(By.id("email")).sendKeys("imeetifydemo@gmail.com");
        driver.findElement(By.id("password")).sendKeys("Welcome@123");
        driver.findElement(By.id("rememberMe")).click();
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div/form/div[5]/button")).click();
        parentWindowHandle = driver.getWindowHandle();
    }

    @Test (priority = 9)
    public void Online() throws InterruptedException {
        driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(Online_link);
    	Functions fun = new Functions(); 
        String Invitee_Name = "Online Basic";
    	String Meeting_ID = fun.Appointment(driver,"Online","Online","Basic",Email,parentWindowHandle);
    	String reUrl = "https://imeetify.com/testpurplemeet/Online-Calendar/re-book?" + Meeting_ID ;
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(reUrl);
    	fun.Reschedule(driver, "Online", parentWindowHandle,null);
    	fun.Cancel(driver, "Online" , Invitee_Name);
    }
    
    @Test  (priority = 2)
    public void Inperson() throws InterruptedException {
        driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(Inperson_link); 
        Invitee_Name = "Inperson Basic";
    	Meeting_ID = fun.Appointment(driver,"Inperson","Inperson","Basic",Email,parentWindowHandle);
    	reUrl = "https://imeetify.com/testpurplemeet/Inperson-Calendar/re-book?" + Meeting_ID ;
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(reUrl);
    	fun.Reschedule(driver, "Inperson", parentWindowHandle,null);
    	fun.Cancel(driver,"Inperson" , Invitee_Name);
    }
    
    @Test (priority = 3)
    public void PhoneCall () throws InterruptedException {
        driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(PhoneCall_link); 
        Invitee_Name = "PhoneCall Basic";
    	Meeting_ID = fun.Appointment(driver,"PhoneCall","PhoneCall","Basic",Email,parentWindowHandle);
    	reUrl = "https://imeetify.com/testpurplemeet/OverThePhoneCalendar/re-book?" + Meeting_ID ;
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(reUrl);
    	fun.Reschedule(driver, "PhoneCall", parentWindowHandle,null);
    	fun.Cancel(driver, "PhoneCall" , Invitee_Name);
    }
    
    @Test (priority = 4)
    public void Conflict_Online_To_Inperson () throws InterruptedException {
        driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(C_online);
    	Functions fun = new Functions();
        Invitee_Name = "Online Inperson";
    	Meeting_ID = fun.Appointment(driver,"Conflict Online TO Inperson","Online","Inperson",Email,parentWindowHandle);
    	reUrl = "https://imeetify.com/testpurplemeet/C-online/re-book?" + Meeting_ID ;
    	
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(C_Inperson);
    	fun.First_Slot_Blocking_Verfiy(driver, "Conflict Online To Inperson",parentWindowHandle , "10:00 AM");
    	String checking_page = driver.getWindowHandle();
    	
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(reUrl);
    	fun.Reschedule(driver, "Conflict online to inperson", checking_page,null);
    	fun.Secound_Slot_Blocking_Verfiy(driver, "Conflict online to inperson", parentWindowHandle, "12:30 PM","10:00 AM");
    	fun.Cancel(driver, "Conflict Online To Inperson", Invitee_Name);
    }
    
    @Test (priority = 5)
    public void Conflict_Inperson_To_OverPhone() throws InterruptedException {
        driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(C_Inperson);
        Invitee_Name = "Inperson OverPhone";
    	Meeting_ID = fun.Appointment(driver,"Conflict Inperson TO OverPhone","Inperson","OverPhone",Email,parentWindowHandle);
    	reUrl = "https://imeetify.com/testpurplemeet/C-Inperson/re-book?" + Meeting_ID ;
    	
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(C_phone);
    	fun.First_Slot_Blocking_Verfiy(driver, "Conflict Inperson To OverPhone",parentWindowHandle , "10:00 AM");
    	String checking_page2 = driver.getWindowHandle();
    	
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(reUrl);
    	fun.Reschedule(driver, "Conflict Inperson To OverPhone", checking_page2,null);
    	fun.Secound_Slot_Blocking_Verfiy(driver, "Conflict Inperson To OverPhone", parentWindowHandle, "12:30 PM","10:00 AM");
    	fun.Cancel(driver, "Conflict Inperson To OverPhone", Invitee_Name);
    }
    
    @Test (priority = 6)
    public void Outlook_ME_CA() throws InterruptedException {
        driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(Outlook_ME);
    	Functions fun = new Functions();
        Invitee_Name = "Outlook ME";
    	Meeting_ID = fun.Appointment(driver,"Outlook ME ","Outlook","ME",Email,parentWindowHandle);
    	reUrl = Outlook_ME + "/re-book?" + Meeting_ID ;
    	
    	//Check Avaliable
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(Outloo_CA);
    	Thread.sleep(6500);
    	fun.First_Slot_Blocking_Verfiy(driver, "Outlook Check Available",parentWindowHandle , "10:00 AM");
    	String checking_page = driver.getWindowHandle();
    	
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(reUrl);
    	fun.Reschedule(driver, "Outlook ME", checking_page,null);
    	fun.Secound_Slot_Blocking_Verfiy(driver, "Outlook", parentWindowHandle, "12:30 PM" ,"10:00 AM");
    	fun.Cancel(driver, "Outlook", Invitee_Name);
    }
    
    @Test (priority = 7)
    public void Pending_Approval() throws InterruptedException{
    	fun.Approval_Pending(driver, Calendar_link, parentWindowHandle, Email);
    }
    
    @Test (priority = 8)
    public void Hybrid_Calendar()throws InterruptedException{
        String Online = "/html/body/div[2]/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/div[2]/div/div/a";
        String Inperson = "/html/body/div[2]/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/div[1]/div/div/a";
        String PhoneCall = "/html/body/div[2]/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/div[3]/div/div/a";
        
        // Basic Online Online
        driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(Hybrid_Calendar);
        Thread.sleep(1000);
    	driver.findElement(By.xpath(Online)).click();
    	Functions fun = new Functions(); 
        String Invitee_Name = "Hybrid Online";
    	String Meeting_ID = fun.Appointment(driver,"Hybrid Online","Hybrid","Online",Email,parentWindowHandle);
    	String reUrl = "https://imeetify.com/testpurplemeet/Hybrid-Calendar/re-book?" + Meeting_ID ;
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(reUrl);
    	fun.Reschedule(driver, "Hybrid Online To Inperson", parentWindowHandle,Inperson);
    	fun.Cancel(driver, "Hybrid Online To Inperson" , Invitee_Name);
    	
 
    	// Basic Inperson
        driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(Hybrid_Calendar); 
    	Thread.sleep(1000);
    	driver.findElement(By.xpath(Inperson)).click();
        Invitee_Name = "Hybrid Inperson";
    	Meeting_ID = fun.Appointment(driver,"Hybrid-Inperson","Hybrid","Inperson",Email,parentWindowHandle);
    	reUrl = "https://imeetify.com/testpurplemeet/Hybrid-Calendar/re-book?" + Meeting_ID ;
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(reUrl);
    	fun.Reschedule(driver, "Hybrid Inperson To PhoneCall", parentWindowHandle,PhoneCall);
    	fun.Cancel(driver,"Hybrid Inperson To PhoneCall" , Invitee_Name);
        
        // Basic OverPhone
        driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(Hybrid_Calendar);
    	Thread.sleep(1000);
    	driver.findElement(By.xpath(PhoneCall)).click();
    	fun = new Functions(); 
        Invitee_Name = "Hybrid PhoneCall";
    	Meeting_ID = fun.Appointment(driver,"Hybrid PhoneCall","Hybrid","PhoneCall",Email,parentWindowHandle);
    	reUrl = "https://imeetify.com/testpurplemeet/Hybrid-Calendar/re-book?" + Meeting_ID ;
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(reUrl);
    	fun.Reschedule(driver, "Hybrid PhoneCall To Online", parentWindowHandle,Online);
    	fun.Cancel(driver, "Hybrid PhoneCall To Online" , Invitee_Name);

    }

    @Test (priority = 1)
    public void Recurring_Appointment()throws InterruptedException {
    	try {
    		String Calendar_Link = "https://imeetify.com/testpurplemeet/Online-Calendar";
    	    driver.findElement(By.xpath("//*[@class=\"sidebar-content mt-3\"]/nav/ul/li[2]/a")).click();
    	    driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[2]/div/ul/li[1]/a")).click();
    	    
    	    WebElement Calendar_Name = driver.findElement(By.xpath("//*[@id=\"calendar\"]"));
    	    Select Calendar_Country = new Select(Calendar_Name);
    	    Calendar_Country.selectByVisibleText("Online-Calendar");
        
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(50))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[@id=\"demo-2\"]/div[2]/div[1]/table/tbody/tr[2]/td[5]")));
            
            String[] times = {"10:00 AM", "10:30 AM" , "11:00 AM"};

            String[] Date = {F_date, S_Date ,T_Date};
            String Fn = "Recurring";
            String Ln = "Appointment";
            String Invitee_Name = Fn+" "+Ln;
            fun.Recurring_Appointment(driver,times,Date,Fn,Ln,Email,Calendar_Link,parentWindowHandle);
            driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[1]/a")).click();
            driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[1]/div/ul/li[1]")).click();
            for(int i = 0; i< Select_Recurring_Date.length; i++) {
                fun.Recurring_Appointment_Cancel(driver, Invitee_Name, Select_Recurring_Date[i]);
            }
            Reporter.log("All Appointment Canceled Successfully<br/>");
            System.out.println("All Appointment Canceled Successfully"+"\n"+"\n");
    	} catch (Exception v) {
    		driver.findElement(By.xpath("//*[@class=\"sidebar-content mt-3\"]/nav/ul/li[1]/a")).click();
    		driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[1]/div/ul/li[1]/a")).click();
    	}

	}	
  }