package Prod;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Working {
	WebDriver driver;
	String parentWindowHandle;
	String Calendar_Name = "All In One";
	String lookbusy = "5";
	String No_slot = "1";
	String Break = "5 minutes";
	String numericPart = Break.split(" ")[0];
	int slotbreak = Integer.parseInt(numericPart);
	int Makeaway = 15;	
	int formdate = 15;
	int todate = 25;
	String Specialdate = "27";
	String specialformtime = "07:30 PM";
	String specialtotime = "11:30 PM";
	String JavascriptExecutor ;
	String Calendar_link = "https://imeetify.com/testpurplemeet/All-In-One" ;
	String Calendar_Starttime = "08:00 AM";
	String Calendar_Endtime = "10:00 PM";
    int durationMinutes = 15  ;
    String duration = durationMinutes+" minutes";
    int findclose = durationMinutes + slotbreak ;
    int acutual_Slots;
    String H_Inperson ;
    String H_Online;
    String H_Phonecall;
    

    List<String> splitTimeRange(String startTime, String endTime, int durationMinutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter);
        List<String> timeSlots = new ArrayList<>();
        while (!start.isAfter(end)) {
            timeSlots.add(start.format(formatter));
            start = start.plusMinutes(durationMinutes);
        }
        return timeSlots;
    } 
    static String findClosestSlot(String slotTime, List<String> intervals) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime slot = LocalTime.parse(slotTime, formatter);
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
    void Call_Calendar() {
//        driver.switchTo().newWindow(WindowType.TAB);
//    	driver.get(Calendar_link);
    	WebElement hybrid = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(H_Online)));
    	hybrid.click();
    	new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));;
    	String ht = driver.getCurrentUrl();
    	if (ht.contains("Tele-Zoom") || ht.contains("Phone-call")) {
            WebElement Country = driver.findElement(By.id("countryid"));
            Select Country_Select = new Select(Country);
            String link = driver.getCurrentUrl();
            	if (link.contains("DemoimeetUX710xcz")) {
            		Country_Select.selectByVisibleText("India Standard Time");
            	} else {
            		Country_Select.selectByVisibleText("Chennai, Kolkata, Mumbai, New Delhi");
//                  Country_Select.selectByVisibleText("Dubai/Abudhabi");
            	}
    	}
    }
    
	@BeforeClass
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\driver\\chromedriver.exe"); 
        driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
//        driver.get("https://imeetify.com/login");
//        Thread.sleep(2500);
//        driver.findElement(By.id("email")).sendKeys("imeetifydemo@gmail.com");
//        driver.findElement(By.id("password")).sendKeys("Welcome@123");
//        driver.findElement(By.id("rememberMe")).click();
//        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div/form/div[5]/button")).click();    
//        parentWindowHandle = driver.getWindowHandle();
		H_Inperson =  "/html/body/div[2]/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/div[1]/div/div/a";
		H_Online =  "/html/body/div[2]/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/div[2]/div/div/a";
		H_Phonecall =  "/html/body/div[2]/div[1]/div/div/div/div/div[2]/div[2]/div[1]/div/div[3]/div/div/a";
    }
//		
//	
//	@Test (priority = 1)
//	void Create_Calendar() throws Throwable {
//		driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[2]/a")).click();
//		driver.findElement(By.xpath("//nav[@ class=\"menu open-current-submenu\"]/ul/li[2]/div/ul/li[2]")).click();
//		driver.findElement(By.id("hybr-online")).click();
//		driver.findElement(By.id("hybr-phone")).click();
//		driver.findElement(By.id("create_hybrid_cal")).click();
//		
//		driver.findElement(By.id("calendarname")).sendKeys(Calendar_Name);
//		new Select(driver.findElement(By.id("slot_duration"))).selectByVisibleText(duration);
//		System.out.println(duration);
//		// Online Provider
//		String [] provider = {"Zoom","Google-Meet","Microsoft Teams","Custom Link"};
//		WebElement Provider_Type = driver.findElement(By.id("hybrid_provider"));
//		Select Provider_select = new Select (Provider_Type);
//		Random random  = new Random();
//		int randomIndex = random.nextInt(provider.length);
//		String selectedProvider = provider[randomIndex];
//		Provider_select.selectByVisibleText(selectedProvider);
//		if (selectedProvider.equals("Custom Link")) {
//			driver.findElement(By.id("custom_link")).sendKeys("https://meet.google.com/vtj-jgyy-mdk");
//		}
//    	WebElement D = driver.findElement(By.xpath("//*[@id=\"validationTextarea\"]/div[1]/p"));
//    	JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript("arguments[0].scrollIntoView(true);", D);
//    	D.sendKeys("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim.");
//       new Select(driver.findElement(By.id("days"))).selectByVisibleText("Indefinitely into the future");
//		
//		// Look busy 
//		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div[3]/div/div/div/div/div/div[4]/div/div/div/div[2]/div/div[1]/div[1]/a")).click();
//		driver.findElement(By.id("look_busy")).sendKeys(lookbusy);
//		
//		// Number of slots per day 
//		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div[3]/div/div/div/div/div/div[4]/div/div/div/div[2]/div/div[2]/div[1]/a")).click();
//		driver.findElement(By.id("bufferslotTime")).sendKeys(No_slot);
//		
//		// Mark a away
//		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div[3]/div/div/div/div/div/div[4]/div/div/div/div[2]/div/div[3]/div[1]/a")).click();
//		String str = String.valueOf(Makeaway);
//		driver.findElement(By.id("bufferTime")).sendKeys(str);
//		new Select(driver.findElement(By.id("bufferTimeHorM"))).selectByVisibleText("Minutes");
//		
//		// Break
//		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div[3]/div/div/div/div/div/div[4]/div/div/div/div[2]/div/div[4]/div[1]/a")).click();
//		new Select(driver.findElement(By.id("break_between_slot"))).selectByVisibleText(Break);
//		
//    	// off the google sync
//    	driver.findElement(By.xpath("//*[@id=\"headingSeven\"]/a")).click();
//    	WebElement google = driver.findElement(By.id("googlecalsync"));
//        JavascriptExecutor AS = (JavascriptExecutor) driver;
//        AS.executeScript("arguments[0].scrollIntoView(true);", google);
//        google.click();
//        
//		// Make Private 
//		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div[3]/div/div/div/div/div/div[4]/div/div/div/div[2]/div/div[8]/div[1]/a")).click();
//		driver.findElement(By.id("makeprivate")).click();
//		
//    	driver.findElement(By.id("btn-next")).click();
//		
//		// Set_availability
//    	driver.findElement(By.id("sundaycheckid")).click();
//    	WebElement From_Time = driver.findElement(By.id("sunfromtime"));
//    	Select select_From_Time = new Select(From_Time);
//    	select_From_Time.selectByVisibleText(Calendar_Starttime);
//    	WebElement To_Time = driver.findElement(By.id("suntotime"));
//    	Select select_To_Time = new Select(To_Time);	
//    	select_To_Time.selectByVisibleText(Calendar_Endtime);
//    	driver.findElement(By.id("sun_1")).click();
//    	driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[3]/div[2]/button")).click();
//    	new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"content\"]/div/div[3]/div[1]/div/div/div/div/div/div/form/div/div/h4")));
//    	
//    	// custom Avaliable 
//    	driver.findElement(By.xpath("//*[@id=\"calendar\"]/div[1]/div[2]/div/button[2]")).click();
//    	WebElement sourceElement = driver.findElement(By.xpath("//td//span[text()="+formdate+"]"));
//    	JavascriptExecutor jsa = (JavascriptExecutor) driver;
//		jsa.executeScript("arguments[0].scrollIntoView(true);", sourceElement);
//    	WebElement targetElement = driver.findElement(By.xpath("//td//span[text()="+todate+"]"));
//    	Actions actions = new Actions(driver);
//    	actions.clickAndHold(sourceElement) .moveToElement(targetElement).release(targetElement).build() .perform(); 
//    	Thread.sleep(1500);
//    	driver.findElement(By.id("radio2lbl")).click();
//    	driver.findElement(By.id("btn-On")).click();
//    	
//    	// special avaliable
//    	driver.findElement(By.xpath("//td//span[text()="+Specialdate+"]")).click();
//    	Thread.sleep(1500);
//    	new Select(driver.findElement(By.id("fromtime"))).selectByVisibleText(specialformtime);
//    	new Select(driver.findElement(By.id("totime"))).selectByVisibleText(specialtotime);
//        driver.findElement(By.id("addbtn")).click();
//        Thread.sleep(1500);
//        driver.findElement(By.xpath("//*[@id=\"monthly-calendar-event\"]/div/div/div[1]/button")).click();
//        Thread.sleep(1500);
//        
//       	WebElement home = new WebDriverWait(driver, Duration.ofSeconds(50))
//                .until(ExpectedConditions.elementToBeClickable(
//                        By.xpath("//*[@class=\"sidebar-content mt-3\"]/nav/ul/li[1]/a")));
//    	home.click();
//    	driver.findElement(By.xpath("//*[@class=\"menu-item sub-menu open\"]/div/ul/li[1]/a")).click();
//    	new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/div/div/div/div/div/div[2]/div/div[1]/h4")));
//	}
//	
//	
//	@Test(priority = 2)
//	void Verify_Privatelink() {
//		driver.findElement(By.id("oldnameid")).click();
//		Set<String> windowHandles = driver.getWindowHandles();
//		for (String handle : windowHandles) {
//		    if (!handle.equals(parentWindowHandle)) {
//		        driver.switchTo().window(handle);
//		        break;
//		    }
//		}
//		List<WebElement> Calendarname = driver.findElements(By.xpath("//div[@class=\"col-md-6 col-sm-4 my-3\"]/a/div/div[2]/span"));
//		for (WebElement element1 : Calendarname) {
//		    String Cname = element1.getText();
//            if (Cname.equals(Calendar_Name)) {
//		        System.err.println( "\n"+"Private Mode Not Working" + "\n");	        
//		    } 
//		}
//		driver.close();
//		driver.switchTo().window(parentWindowHandle);
//	}
//	
//
//	@Test(priority = 3)
//	void Verify_Do_Not_Disturb() {
//		   Call_Calendar();
//	    	WebElement n = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));
//	    	n.click();
//	        LocalTime currentTime = LocalTime.now();
//	        int fet = slotbreak+Makeaway;
//	        int extraMinutes = fet;  
//	        LocalTime newTime = currentTime.plusMinutes(extraMinutes);  
//	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
//            String formattedTime = newTime.format(formatter);
//	        System.out.println("Current Time: " + currentTime.format(formatter));
//	        System.out.println("Time after adding " + extraMinutes + " minutes: " + formattedTime);
//	        List<String> intervals = splitTimeRange(Calendar_Starttime, Calendar_Endtime, findclose);
//	        String closestSlot = findClosestSlot(formattedTime, intervals).trim();
//	        System.out.println("Closest Slot to: " + closestSlot);
//	        WebElement First_Slot = driver.findElement(By.xpath("//li[@class=\"time-slot-item\"]"));
//	        String minutesDifferencea = First_Slot.getText();
//	        if (minutesDifferencea.equals(closestSlot)) {
//	        	System.out.println("Do not Disturb Working fine" + "\n");
//	        } else {
//	        	System.err.println("Do not Disturb Working fine not working" + "\n");
//	        }
//	        String trimmedTime = minutesDifferencea.trim();
//	        String[] timeParts = trimmedTime.split(" ")[0].split(":");
//	        int hour = Integer.parseInt(timeParts[0]);
//	        int minute = Integer.parseInt(timeParts[1]);
//	        if (trimmedTime.contains("PM") && hour != 12) {
//	            hour += 12; // Convert PM hour to 24-hour format
//	        } else if (trimmedTime.contains("AM") && hour == 12) {
//	            hour = 0; // Midnight case
//	        }
//	        int minutesDifference = hour * 60 + minute;        
//	        if (minutesDifference > fet) {
//	            System.out.println("The closest slot is greater than " + fet + " minutes from the current time.");
//	        } else {
//	            System.err.println("The closest slot is within " + fet + " minutes of the current time.");
//	        }
//			driver.close();
//			driver.switchTo().window(parentWindowHandle);
//	}
//
//	@Test(priority = 4)
//	void Verify_Look_Busy_And_Break() {
//        Call_Calendar();
//    	WebElement n = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));
//    	n.click();
//    	List<WebElement> All_Slots = driver.findElements(By.xpath("//li[@class=\"time-slot-item\"]"));
//    	acutual_Slots = All_Slots.size();
//    	String FirstSlot = All_Slots.get(0).getText();
//    	String SecoundSlot = All_Slots.get(1).getText();
//    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a",Locale.ENGLISH);
//        LocalTime startTime = LocalTime.parse(FirstSlot.trim(), formatter);
//        LocalTime endTime = LocalTime.parse(SecoundSlot.trim(), formatter);
//        long minutesDifference = ChronoUnit.MINUTES.between(startTime, endTime);
//        int adjustedMinutes = (int) (minutesDifference - durationMinutes);
//    	String numericPart = Break.split(" ")[0];
//    	int slotbreak = Integer.parseInt(numericPart);
//    	System.out.println("Slot Break Time :  " + slotbreak + " Minutes");
//    	System.out.println("Acutal Break Time :  " + adjustedMinutes + " Minutes");
//       if (adjustedMinutes == slotbreak ) {
//    	   System.out.println("Break working Fine");
//       } else {
//    	   System.err.println("Break not working");
//       }
//        int lookBusy = Integer.parseInt(lookbusy);
//    	System.out.println("lookbusy Slot :  " + lookBusy + " Slots");
//    	System.out.println("Acutal lookbusy Slot :  " + acutual_Slots + " Slots");
//    	if (lookBusy == acutual_Slots ) {
//    		System.out.println("Look busy working fine" + "\n");
//    	} else {
//    		System.err.println("Look busy not working showing :  " + acutual_Slots  + "\n");
//    	}
//		driver.close();
//		driver.switchTo().window(parentWindowHandle);
//	}
//	
//
//	@Test(priority = 5)
//	void Verify_No_of_slots() {
//		Call_Calendar();
//		new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));
//		driver.findElement(By.xpath("//td[@class=\"day\"]")).click();
//		driver.findElement(By.xpath("//li[@class=\"time-slot-item\"]")).click();
//      	WebElement NB = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id("nextbtn")));
//        NB.click();
//        driver.findElement(By.id("name")).sendKeys("verify");
//        driver.findElement(By.id("lastname")).sendKeys("No Of Slots");
//        driver.findElement(By.id("email")).sendKeys("sathishtest16@gmail.com");
//        WebElement Get_A_Type = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[1]/div/div[1]/p[2]"));
//        String A_Type = Get_A_Type.getText();
//		if (A_Type.contains("Hybrid")) {
//            String current = A_Type.substring(8, A_Type.length() - 1);
//            String online = "Online";
//            String inperson = "In person";
//            if (current.equals(online) || current.equals(inperson)) {
//                driver.findElement(By.id("btn_submit")).click();
//                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(60));
//                WebElement element = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[2]/div/div/div/h3")));
//                if (element.isDisplayed()) {
//                    System.out.println("Appointment successfully");
//                    String r_url = driver.getCurrentUrl();
//                    String Meeting_ID = r_url.substring(r_url.indexOf("meeting=", 8), r_url.indexOf("&", 8));
//                } else {
//                    System.err.println(" Not Able to Appointment ");
//                }
//             }
//            else {       	
//                driver.findElement(By.id("mobile")).sendKeys("5382948430");
//                driver.findElement(By.id("btn_submit")).click();
//                WebElement element = new WebDriverWait(driver, Duration.ofSeconds(50))
//                        .until(ExpectedConditions.visibilityOfElementLocated(
//                                By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[2]/div/div/div/h3")));
//                if (element.isDisplayed()) {
//                    System.out.println(" Appointment successfully");
//                    String r_url = driver.getCurrentUrl();
//                    String Meeting_ID = r_url.substring(r_url.indexOf("meeting=", 8), r_url.indexOf("&", 8));
//                } else {
//                    System.err.println(" Not Able to Appointment ");
//                }
//            }
//            driver.close();
//            driver.switchTo().window(parentWindowHandle);
//    		Call_Calendar();
//    		new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));
//    		driver.findElement(By.xpath("//td[@class=\"day\"]")).click();
//    		List<WebElement> timeSlots = driver.findElements(By.xpath("//li[@class='time-slot-item' and (not(@style) or not(contains(@style, 'display: none')))]"));
//    		if (timeSlots.isEmpty()) {
//    		    System.out.println("No of Slot wroking fine" + "\n");
//    		} else {
//    			System.err.println("Slot available , No of Slot not working" + "\n");
//    		   for (WebElement errorslots : timeSlots) {
//    			   System.out.println(errorslots.getText());
//       		}
//    		   }
//    			
//		}
//		driver.close();
//		driver.switchTo().window(parentWindowHandle);
//	}
	
	
	@Test(priority = 1)
	void Verify_Custom_Availability() throws InterruptedException {
//		Call_Calendar();
		driver.get(Calendar_link);
		Call_Calendar();
		new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));
		driver.findElement(By.xpath("//*[@id=\"demo-2\"]/div[3]/div[1]/table/thead/tr[2]/th[3]")).click();
		// Find all elements with the ID "inactivedates_datepicker"
		List<WebElement> inactiveDates = driver.findElements(By.id("inactivedates"));
		WebElement inactiveDateElement = inactiveDates.get(0);
		String inactiveDate = inactiveDateElement.getAttribute("value").replace("'", ""); // Remove single quotes
		String[] dates = inactiveDate.split(",");
		// Split the start time to end time 
		int[] dateRange = new int[todate - formdate + 1];
		for (int i = 0; i < dateRange.length; i++) {
            dateRange[i] = formdate + i;
        }
//        for (int date : dateRange) {
//            System.out.println("This Expected Need To Disabled Date :  " + date );           
//        }
        int[] ENDD = new int[dateRange.length];
		new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[starts-with(@class, 'old disabled disabled day')]")));
		List<WebElement> dayDisabledElements = driver.findElements(By.xpath( "//td[starts-with(@class, 'day disabled')]"));
//		for (WebElement fa : dayDisabledElements) {
//	           String text = fa.getText(); 
//	           System.out.println("Date with class 'day disabled': " + text);
//		
//	    }
		int[] ANDD = new int[dayDisabledElements.size()];		
		 boolean areEqual = Arrays.equals(ENDD, ANDD);
		 if (areEqual) {
			 System.out.println("Cutom Unavailable Working fine" + "\n");
		 } else {
			 System.err.println("Custom Unavailable Not  working Fine" + "\n");
		 }

	}
	
}




	
