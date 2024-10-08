package Prod;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import Functions.Login_Functions;
import Prod.Full_Flow;
import java.time.Duration;


public class All_One_Calendar {
	static String Calendar_Name = "All In One";
	static String lookbusy = "5";
	static String No_slot = "1";
	static String Break = "15 minutes";
	static String numericPart = Break.split(" ")[0];
	static int slotbreak = Integer.parseInt(numericPart);
	static int Makeaway = 10;	
	static String str = String.valueOf(Makeaway);
	static int fet = slotbreak+Makeaway;
	static String formdate = "22";
	static String todate = "25";
	static String Specialdate = "27";
	static String specialformtime = "07:30 PM";
	static String specialtotime = "11:30 PM";
	static String H_Inperson =  "/html/body/div[2]/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/div[1]/div/div/a";
	static String H_Online =  "/html/body/div[2]/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/div[2]/div/div/a";
	static String H_Phonecall =  "/html/body/div[2]/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/div[3]/div/div/a";
	String JavascriptExecutor ;
	static String Calendar_link = "https://imeetify.com/testpurplemeet/All-In-One" ;
	static String Calendar_Starttime = "09:00 AM";
	static String Calendar_Endtime = "09:00 PM";
    static int durationMinutes = 30;
	
    public static List<String> splitTimeRange(String startTime, String endTime, int durationMinutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime start = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("h:mm a"));
        LocalTime end = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("h:mm a"));  
        List<String> intervals = new ArrayList<>();   
        LocalTime current = start;
        while (current.isBefore(end) || current.equals(end)) {
            intervals.add(current.format(formatter));
            current = current.plusMinutes(durationMinutes);
        }
        return intervals;
    } 
    public static String findClosestSlot(String slotTime, List<String> intervals) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime slot = LocalTime.parse(slotTime, DateTimeFormatter.ofPattern("h:mm a"));
        LocalTime closestSlot = null;
        long minDifference = Long.MAX_VALUE;
        for (String interval : intervals) {
            LocalTime intervalTime = LocalTime.parse(interval, formatter);
            long difference = Math.abs(slot.until(intervalTime, java.time.temporal.ChronoUnit.MINUTES));
            if (difference < minDifference) {
                minDifference = difference;
                closestSlot = intervalTime;
            }
        } 
        return closestSlot != null ? closestSlot.format(formatter) : null;
    }
    
    
    
	public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\driver\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://imeetify.com/login");
        Thread.sleep(2500);
        driver.findElement(By.id("email")).sendKeys("imeetifydemo@gmail.com");
		driver.findElement(By.id("password")).sendKeys("Welcome@123");
        driver.findElement(By.id("rememberMe")).click();
//      Thread.sleep(2500);
        
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div/form/div[5]/button")).click();
        String parentWindowHandle = driver.getWindowHandle();
		driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[2]/a")).click();
		driver.findElement(By.xpath("//nav[@ class=\"menu open-current-submenu\"]/ul/li[2]/div/ul/li[2]")).click();
		
		// Create new calendar booking page 
		
		driver.findElement(By.id("hybr-online")).click();
		driver.findElement(By.id("hybr-phone")).click();
		driver.findElement(By.id("create_hybrid_cal")).click();
		
		driver.findElement(By.id("calendarname")).sendKeys(Calendar_Name);
		
		// Online Provider
		String [] provider = {"Zoom","Google-Meet","Microsoft Teams","Custom Link"};
		WebElement Provider_Type = driver.findElement(By.id("hybrid_provider"));
		Select Provider_select = new Select (Provider_Type);
		Random random  = new Random();
		int randomIndex = random.nextInt(provider.length);
		String selectedProvider = provider[randomIndex];
		Provider_select.selectByVisibleText(selectedProvider);
		if (selectedProvider.equals("Custom Link")) {
			driver.findElement(By.id("custom_link")).sendKeys("https://meet.google.com/vtj-jgyy-mdk");
		}
    	WebElement D = driver.findElement(By.xpath("//*[@id=\"validationTextarea\"]/div[1]/p"));
    	JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", D);
    	D.sendKeys("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim.");
       new Select(driver.findElement(By.id("days"))).selectByVisibleText("Indefinitely into the future");
		
		// Look busy 
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div[3]/div/div/div/div/div/div[4]/div/div/div/div[2]/div/div[1]/div[1]/a")).click();
		driver.findElement(By.id("look_busy")).sendKeys(lookbusy);
		
		// Number of slots per day 
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div[3]/div/div/div/div/div/div[4]/div/div/div/div[2]/div/div[2]/div[1]/a")).click();
		driver.findElement(By.id("bufferslotTime")).sendKeys(No_slot);
		
		// Mark a away
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div[3]/div/div/div/div/div/div[4]/div/div/div/div[2]/div/div[3]/div[1]/a")).click();
		driver.findElement(By.id("bufferTime")).sendKeys(str);
		new Select(driver.findElement(By.id("bufferTimeHorM"))).selectByVisibleText("Minutes");
		
		// Break
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div[3]/div/div/div/div/div/div[4]/div/div/div/div[2]/div/div[4]/div[1]/a")).click();
		new Select(driver.findElement(By.id("break_between_slot"))).selectByVisibleText(Break);
		
		// Make Private 
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div[3]/div/div/div/div/div/div[4]/div/div/div/div[2]/div/div[8]/div[1]/a")).click();
		driver.findElement(By.id("makeprivate")).click();
		
		
    	// off the google sync
    	driver.findElement(By.xpath("//*[@id=\"headingSeven\"]/a")).click();
    	WebElement google = driver.findElement(By.id("googlecalsync"));
        JavascriptExecutor AS = (JavascriptExecutor) driver;
        AS.executeScript("arguments[0].scrollIntoView(true);", google);
        google.click();
    	driver.findElement(By.id("btn-next")).click();
		
		// Set_availability
    	driver.findElement(By.id("sundaycheckid")).click();
    	WebElement From_Time = driver.findElement(By.id("sunfromtime"));
    	Select select_From_Time = new Select(From_Time);
    	select_From_Time.selectByVisibleText(Calendar_Starttime);
    	WebElement To_Time = driver.findElement(By.id("suntotime"));
    	Select select_To_Time = new Select(To_Time);	
    	select_To_Time.selectByVisibleText(Calendar_Endtime);
    	driver.findElement(By.id("sun_1")).click();
    	driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[3]/div[2]/button")).click();
    	WebElement element = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[@id=\"content\"]/div/div[3]/div[1]/div/div/div/div/div/div/form/div/div/h4")));
    	
    	// custom Avaliable 
    	driver.findElement(By.xpath("//*[@id=\"calendar\"]/div[1]/div[2]/div/button[2]")).click();
    	WebElement sourceElement = driver.findElement(By.xpath("//td//span[text()="+formdate+"]"));
    	JavascriptExecutor jsa = (JavascriptExecutor) driver;
		jsa.executeScript("arguments[0].scrollIntoView(true);", sourceElement);
    	WebElement targetElement = driver.findElement(By.xpath("//td//span[text()="+todate+"]"));
    	Actions actions = new Actions(driver);
    	actions.clickAndHold(sourceElement) .moveToElement(targetElement).release(targetElement).build() .perform(); 
    	Thread.sleep(1500);
    	driver.findElement(By.id("radio2lbl")).click();
    	driver.findElement(By.id("btn-On")).click();
    	
    	// special avaliable
    	driver.findElement(By.xpath("//td//span[text()="+Specialdate+"]")).click();
    	Thread.sleep(1500);
    	new Select(driver.findElement(By.id("fromtime"))).selectByVisibleText(specialformtime);
    	new Select(driver.findElement(By.id("totime"))).selectByVisibleText(specialtotime);
        driver.findElement(By.id("addbtn")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//*[@id=\"monthly-calendar-event\"]/div/div/div[1]/button")).click();
        Thread.sleep(1500);
        
       	WebElement home = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@class=\"sidebar-content mt-3\"]/nav/ul/li[1]/a")));
    	home.click();
    	driver.findElement(By.xpath("//*[@class=\"menu-item sub-menu open\"]/div/ul/li[1]/a")).click();
    	WebElement Dashborad = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"content\"]/div/div/div/div/div/div[2]/div/div[1]/h4")));
    	
		
		driver.findElement(By.id("oldnameid")).click();
		
		Set<String> windowHandles = driver.getWindowHandles();
		for (String handle : windowHandles) {
		    if (!handle.equals(parentWindowHandle)) {
		        driver.switchTo().window(handle);
		        break;
		    }
		}
		List<WebElement> Calendarname = driver.findElements(By.xpath("//div[@class=\"col-md-6 col-sm-4 my-3\"]/a/div/div[2]/span"));
		for (WebElement element1 : Calendarname) {
		    String Cname = element1.getText();
            if (Cname.equals(Calendar_Name)) {
		        System.err.println("Private Mode Not Working");
		        
		    } 
		}
		driver.close();
		driver.switchTo().window(parentWindowHandle);
        driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(Calendar_link);
    	WebElement hybrid = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(H_Online)));
    	hybrid.click();
    	String ht = driver.getCurrentUrl();
    	if (ht.contains("Tele-Zoom") || ht.contains("Phone-call")) {
            WebElement Country = driver.findElement(By.id("countryid"));
            Select Country_Select = new Select(Country);
            String link = driver.getCurrentUrl();
            	if (link.contains("DemoimeetUX710xcz")) {
            		Country_Select.selectByVisibleText("India Standard Time");
            	} else {
            		Country_Select.selectByVisibleText("Chennai, Kolkata, Mumbai, New Delhi");
            	}
    	}
        
    	WebElement n = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));
    	n.click();
    	
    	// Do not Disturb
        LocalTime currentTime = LocalTime.now();
        int extraMinutes = fet;  
        LocalTime newTime = currentTime.plusMinutes(extraMinutes);  
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        
        String formattedTime = newTime.format(formatter);
        System.out.println("Current Time: " + currentTime.format(formatter));
        System.out.println("Time after adding " + extraMinutes + " minutes: " + formattedTime);
        
        List<String> intervals = splitTimeRange(Calendar_Starttime, Calendar_Endtime, durationMinutes);
        System.out.println(intervals);
        
        // Pass 'newTime' to find the closest slot after adding the minutes.
        String closestSlot = findClosestSlot(formattedTime, intervals).trim();
        System.out.println("Closest Slot to: " + closestSlot);

        WebElement First_Slot = driver.findElement(By.xpath("//li[@class=\"time-slot-item\"]"));
        String minutesDifferencea = First_Slot.getText();
        
        // Trim whitespace and remove "PM" or "AM"
        String trimmedTime = minutesDifferencea.trim();
        String[] timeParts = trimmedTime.split(" ")[0].split(":");
        
        // Parse hour and minute
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        
        // Convert to 24-hour format if needed
        if (trimmedTime.contains("PM") && hour != 12) {
            hour += 12; // Convert PM hour to 24-hour format
        } else if (trimmedTime.contains("AM") && hour == 12) {
            hour = 0; // Midnight case
        }
        
        // Calculate total minutes
        int minutesDifference = hour * 60 + minute;        
        // Check difference
        if (minutesDifference > fet) {
            System.out.println("The closest slot is greater than " + fet + " minutes from the current time.");
        } else {
            System.err.println("The closest slot is within " + fet + " minutes of the current time.");
        }


	}


 		

	}

	
