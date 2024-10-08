package Prod;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.util.Random;
import Functions.Functions;

public class Full_Flow extends Functions{
	public static String parentWindowHandle;
	static WebDriver driver = new ChromeDriver();
	static String calendar_link;
	static Functions fun = new Functions(); 
	public static String Email = "demoimeetify@outlook.com"; 
	static String Invitee_Name ;
	static String Meeting_ID;
	static String reUrl ;
	static String Date_Selector = "//*[@class=\"span\" and normalize-space(text())=\"1\"]";
	static String c_name;
	static String c_Type;
	



        public static void Select_Calendar_Type(WebDriver driver, String calendar_Type,String parentwindowhandle){
            switch (calendar_Type) {
                case "Online":
                    driver.findElement(By.id("Reg_online")).click();
                    driver.findElement(By.id("create_calendar")).click();
                    break;
                case "Inperson":
                    driver.findElement(By.id("Reg_in_person")).click();
                    driver.findElement(By.id("create_calendar")).click();
                    break;
                case "OverPhone":
                    driver.findElement(By.id("Reg_phone_call")).click();
                    driver.findElement(By.id("create_calendar")).click();
                    break;
                case "Approval_Online":
                    {
                        driver.findElement(By.id("appr_online")).click();
                        WebElement create = driver.findElement(By.id("approval_pending_create"));
                        JavascriptExecutor AS = (JavascriptExecutor) driver;
                        AS.executeScript("arguments[0].scrollIntoView(true);", create);
                        create.click();
                        break;
                    }
                case "Hybrid":	
                    {
                        WebElement create = driver.findElement(By.id("create_hybrid_cal"));
                        JavascriptExecutor AS = (JavascriptExecutor) driver;
                        AS.executeScript("arguments[0].scrollIntoView(true);", create);
                        driver.findElement(By.id("hybr-online")).click();
                        driver.findElement(By.id("hybr-phone")).click();
                        create.click();
                        break;
                    }
                default:
                    break;
            }
        	c_Type = calendar_Type;
        }
        public static void Booking_Page_Details(WebDriver driver, String calendar_name,String parentwindowhandle,String Type) {
//        	 new WebDriverWait(driver,Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("calendarname")));
        	if (Type.equals("Online") || Type.equals("ApprovalOnline")){
            	String[] values = {"Zoom", "Google-Meet", "Microsoft Teams"};
            	Random rand = new Random();
            	int randomIndex = rand.nextInt(values.length);
            	String randomValue = values[randomIndex];
            	WebElement dropdownElement = driver.findElement(By.id("meeting_type"));
            	Select dropdown = new Select(dropdownElement);
            	dropdown.selectByVisibleText(randomValue);
        	}

        	// calendar name 
        	driver.findElement(By.id("calendarname")).sendKeys(calendar_name);
        	calendar_link = "https://imeetify.com/ProdFullFlow/"+calendar_name;
        	c_name = calendar_name;
        	
        	if (Type.equals("Hybrid")) {
        	  	String[] values = {"Zoom", "Google-Meet", "Microsoft Teams"};
            	Random rand = new Random();
            	int randomIndex = rand.nextInt(values.length);
            	String randomValue = values[randomIndex];
        		WebElement dropdownElement = driver.findElement(By.id("hybrid_provider"));
            	Select dropdown = new Select(dropdownElement);
            	dropdown.selectByVisibleText(randomValue);
        	}
        	
        	// Description 
        	WebElement D = driver.findElement(By.xpath("//*[@id=\"validationTextarea\"]/div[1]/p"));
    		JavascriptExecutor js = (JavascriptExecutor) driver;
    		js.executeScript("arguments[0].scrollIntoView(true);", D);
        	D.sendKeys("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim.");
        	
        	// TimeZone 
        	WebElement Timezone = driver.findElement(By.id("country"));
        	Select TimeZoneSelect = new Select(Timezone);
        	TimeZoneSelect.selectByValue("55");
        	
        	// Schedule Days
        	WebElement days = driver.findElement(By.id("days"));
        	Select select_days = new Select(days);
        	select_days.selectByVisibleText("180 Days");
        	
        	if (Type.equals("ApprovalOnline")) {
        		driver.findElement(By.xpath("//*[@id=\"approval_slots\"]")).sendKeys("5");
        		driver.findElement(By.xpath("//*[@id=\"approval_min_slots\"]")).sendKeys("2");
        	}
        	
        	// off the google sync
        	driver.findElement(By.xpath("//*[@id=\"headingSeven\"]/a")).click();
        	WebElement google = driver.findElement(By.id("googlecalsync"));
            JavascriptExecutor AS = (JavascriptExecutor) driver;
            AS.executeScript("arguments[0].scrollIntoView(true);", google);
            google.click();
        	driver.findElement(By.id("btn-next")).click();
        }
        public static void Set_availability(WebDriver driver,String parentwindowhandle) throws InterruptedException {
        	driver.findElement(By.id("sundaycheckid")).click();
        	WebElement From_Time = driver.findElement(By.id("sunfromtime"));
        	Select select_From_Time = new Select(From_Time);
        	select_From_Time.selectByValue("09:00 AM,540");
        	WebElement To_Time = driver.findElement(By.id("suntotime"));
        	Select select_To_Time = new Select(To_Time);
        	select_To_Time.selectByValue("09:00 PM,1260");
        	driver.findElement(By.id("sun_1")).click();
        	driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[3]/div[2]/button")).click();
        	WebElement element = new WebDriverWait(driver, Duration.ofSeconds(50))
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.xpath("//*[@id=\"content\"]/div/div[3]/div[1]/div/div/div/div/div/div/form/div/div/h4")));
        	System.out.println("Calendar created successfully : Calendar name = " + c_name );
        	System.out.println("Calendar Type : " + c_Type);
        	System.out.println("Calendar Link : " + calendar_link);
        	Reporter.log("\n Calendar created successfully: Calendar name = " + c_name + "<br/>");
        	Reporter.log("\n" +"Calendar Type : " + c_Type + "<br/>");
        	Reporter.log("\n" + "Calendar Link : " + calendar_link +"<br/>");
        	WebElement home = new WebDriverWait(driver, Duration.ofSeconds(50))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[@class=\"sidebar-content mt-3\"]/nav/ul/li[1]/a")));
        	home.click();
        	driver.findElement(By.xpath("//*[@class=\"menu-item sub-menu open\"]/div/ul/li[1]/a")).click();
        	WebElement Dashborad = new WebDriverWait(driver, Duration.ofSeconds(50))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[@id=\"content\"]/div/div/div/div/div/div[2]/div/div[1]/h4")));


        	
        }	

        
        public static void Online() throws InterruptedException {
 	        // Online
 	        Select_Calendar_Type(driver, "Online", parentWindowHandle);
 	        Booking_Page_Details(driver, "Online", parentWindowHandle,"Online");
 	        Set_availability(driver,parentWindowHandle);
 	        driver.switchTo().newWindow(WindowType.TAB);
 	        driver.get(calendar_link);
 	        Invitee_Name = "Online Basic";
 	    	Meeting_ID = fun.Appointment(driver,"Online","Online","Basic",Email,parentWindowHandle);
 	    	reUrl = calendar_link +"/re-book?"+ Meeting_ID ;
 	    	driver.switchTo().newWindow(WindowType.TAB);
 	    	driver.get(reUrl);
 	    	fun.Reschedule(driver, "Online", parentWindowHandle,null);
 	    	fun.Cancel(driver, "Online" , Invitee_Name);
 	    	Thread.sleep(3000);
        	driver.findElement(By.xpath("//*[@class=\"sidebar-content mt-3\"]/nav/ul/li[2]/a")).click();
 	    	driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[2]/div/ul/li[2]/a")).click();
	        }
        
        
        public static void Inperson() throws InterruptedException {
 	        // Inperson
 	        Select_Calendar_Type(driver, "Inperson", parentWindowHandle);
	        Booking_Page_Details(driver, "Inperson", parentWindowHandle,"Inperson");
	        Set_availability(driver,parentWindowHandle);
 	        driver.switchTo().newWindow(WindowType.TAB);
 	        driver.get(calendar_link);
 	        Invitee_Name = "Inperson Basic";
 	        Meeting_ID = fun.Appointment(driver,"Online","Inperson","Basic",Email,parentWindowHandle);
 	        reUrl = calendar_link +"/re-book?"+ Meeting_ID ;
 	    	driver.switchTo().newWindow(WindowType.TAB);
 	    	driver.get(reUrl);
 	    	fun.Reschedule(driver, "Inperson", parentWindowHandle,null);
 	    	fun.Cancel(driver, "Inperson" , Invitee_Name);
 	    	Thread.sleep(3000);
        	driver.findElement(By.xpath("//*[@class=\"sidebar-content mt-3\"]/nav/ul/li[2]/a")).click();
 	    	driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[2]/div/ul/li[2]/a")).click();
        }
        
       
        public static void Phonecall() throws InterruptedException {
 	    	// OverPhone
	        Select_Calendar_Type(driver, "OverPhone", parentWindowHandle);
	        Booking_Page_Details(driver, "OverPhone", parentWindowHandle,"PhoneCall");
	        Set_availability(driver,parentWindowHandle);
	        driver.switchTo().newWindow(WindowType.TAB);
 	        driver.get(calendar_link);
 	        Invitee_Name = "OverPhone Basic";
 	    	Meeting_ID = fun.Appointment(driver,"Online","OverPhone","Basic",Email,parentWindowHandle);
 	    	reUrl = calendar_link +"/re-book?"+ Meeting_ID ;
 	    	driver.switchTo().newWindow(WindowType.TAB);
 	    	driver.get(reUrl);
 	    	fun.Reschedule(driver, "OverPhone", parentWindowHandle,null);
 	    	fun.Cancel(driver, "OverPhone" , Invitee_Name);
 	    	Thread.sleep(3000);
        	driver.findElement(By.xpath("//*[@class=\"sidebar-content mt-3\"]/nav/ul/li[2]/a")).click();
 	    	driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[2]/div/ul/li[2]/a")).click();
        }
        
       
        public static void Approval_Pending() throws InterruptedException {
 	    	// Approval Pending
	        Select_Calendar_Type(driver, "Approval_Online", parentWindowHandle);
	        Booking_Page_Details(driver, "ApprovalOnline", parentWindowHandle,"ApprovalOnline");
	        Set_availability(driver,parentWindowHandle);
	        fun.Approval_Pending(driver, calendar_link, parentWindowHandle, Email);
 	    	Thread.sleep(3000);
        	driver.findElement(By.xpath("//*[@class=\"sidebar-content mt-3\"]/nav/ul/li[2]/a")).click();
 	    	driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[2]/div/ul/li[2]/a")).click();
        }
        
        
        public static void Hybrid()throws InterruptedException{
 	        Select_Calendar_Type(driver, "Hybrid", parentWindowHandle);
	        Booking_Page_Details(driver, "Hybrid", parentWindowHandle,"Hybrid");
	        Set_availability(driver,parentWindowHandle);
	        String Online = "/html/body/div[2]/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/div[2]/div/div/a";
	        String Inperson = "/html/body/div[2]/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/div[1]/div/div/a";
	        String PhoneCall = "/html/body/div[2]/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/div[3]/div/div/a";
	        
	        // Basic Online Online
	        driver.switchTo().newWindow(WindowType.TAB);
	    	driver.get(calendar_link);
	    	driver.findElement(By.xpath(Online)).click();
	    	Functions fun = new Functions(); 
	        String Invitee_Name = "Hybrid Online";
	    	String Meeting_ID = fun.Appointment(driver,"Hybrid Online","Hybrid","Online",Email,parentWindowHandle);
	    	reUrl = calendar_link +"/re-book?"+ Meeting_ID ;
	    	driver.switchTo().newWindow(WindowType.TAB);
	    	driver.get(reUrl);
	    	fun.Reschedule(driver, "Hybrid Online To Inperson", parentWindowHandle,Inperson);
	    	fun.Cancel(driver, "Hybrid Online To Inperson" , Invitee_Name);
	    	
	 
	    	// Basic Inperson
	        driver.switchTo().newWindow(WindowType.TAB);
	    	driver.get(calendar_link); 
	    	driver.findElement(By.xpath(Inperson)).click();
	        Invitee_Name = "Hybrid Inperson";
	    	Meeting_ID = fun.Appointment(driver,"Hybrid-Inperson","Hybrid","Inperson",Email,parentWindowHandle);
	    	reUrl = calendar_link +"/re-book?"+ Meeting_ID ;
	    	driver.switchTo().newWindow(WindowType.TAB);
	    	driver.get(reUrl);
	    	fun.Reschedule(driver, "Hybrid Inperson To PhoneCall", parentWindowHandle,PhoneCall);
	    	fun.Cancel(driver,"Hybrid Inperson To PhoneCall" , Invitee_Name);
	        
	        // Basic OverPhone
	        driver.switchTo().newWindow(WindowType.TAB);
	    	driver.get(calendar_link);
	    	driver.findElement(By.xpath(PhoneCall)).click();
	    	fun = new Functions(); 
	        Invitee_Name = "Hybrid PhoneCall";
	    	Meeting_ID = fun.Appointment(driver,"Hybrid PhoneCall","Hybrid","PhoneCall",Email,parentWindowHandle);
	    	reUrl = calendar_link +"/re-book?"+ Meeting_ID ;
	    	driver.switchTo().newWindow(WindowType.TAB);
	    	driver.get(reUrl);
	    	fun.Reschedule(driver, "Hybrid PhoneCall To Online", parentWindowHandle,Online);
	    	fun.Cancel(driver, "Hybrid PhoneCall To Online" , Invitee_Name);
	    	Thread.sleep(3000);
       	    driver.findElement(By.xpath("//*[@class=\"sidebar-content mt-3\"]/nav/ul/li[2]/a")).click();
	    	driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[2]/div/ul/li[2]/a")).click();
        }
        
        
        public static void main(String[] args) throws InterruptedException{
            System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\driver\\chromedriver.exe");
//            driver = new ChromeDriver();
    		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.manage().window().maximize();
            driver.get("https://imeetify.com/login");
            Thread.sleep(2500);
            driver.findElement(By.id("email")).sendKeys("demopurple001@outlook.com");
            driver.findElement(By.id("password")).sendKeys("Welcome@123");
            driver.findElement(By.id("rememberMe")).click();
//            Thread.sleep(2500);
            driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div/form/div[5]/button")).click();
            parentWindowHandle = driver.getWindowHandle();
 	        String page = driver.getCurrentUrl();
 	        if (page.equals("https://imeetify.com/dashboard?Screen=new")) {
 	        	driver.findElement(By.xpath("//*[@class=\"sidebar-content mt-3\"]/nav/ul/li[2]/a")).click();
 	 	    	driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[2]/div/ul/li[3]/a")).click();
 	 	    	WebElement calendar_Count = driver.findElement(By.id("calendarscount"));
 	 	    	String i = calendar_Count.getText();
 	 	    	for(int j = 0 ; j <Integer.parseInt(i); j++) {
 	 	    		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/span")).click();
 	 	    		WebElement D = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div/div/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div/a[5]"));
 	 	    		JavascriptExecutor js = (JavascriptExecutor) driver;
 	 	    		js.executeScript("arguments[0].scrollIntoView(true);", D);
 	 	    		D.click();
 	 	    		driver.findElement(By.xpath("//*[@id=\"delete\"]/div/div/div[3]/button")).click();
 	 	    		Thread.sleep(1000);
 	 	    		driver.navigate().refresh();
 	 	    		
 	 	    	}
 	        }
 	        driver.findElement(By.id("Do_Later")).click();
 	        Thread.sleep(1200);
 	        
   	       Online();
   	       Inperson();
   	       Phonecall();
   	       Approval_Pending();
   	       Hybrid();

    	}


}