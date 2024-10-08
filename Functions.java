package Functions;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Functions {
JavascriptExecutor js;
String Date_Selector = "//*[@class=\"span\" and normalize-space(text())=\"1\"]";
String Dashboad_Date_Selector = "//*[@class=\"day-number \" and normalize-space(text())=\"1\"]";
WebElement DS;
static WebDriver driver;
static Login_Functions Login = new Login_Functions();
static String Time_Zone_Calendar_Link = "https://imeetify.com/testpurplemeet/Time-Zone";
static String timePart;
static String Country_Name;
	

	public static Object[] Appointment(WebDriver driver , String Date,String Meeting_time,String Appointment_timezone) throws Exception{
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        String CT = "";   
        WebElement Country1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("countryid")));
        Select Country_Select = new Select(Country1);
        List<WebElement> options = Country_Select.getOptions();
        List<String> optionsList = new ArrayList<>();
        for (WebElement option : options) {
            optionsList.add(option.getText());
        }
        if (Appointment_timezone == null ) {
            Random random = new Random();
            int randomIndex = random.nextInt(optionsList.size());
            String randomCountry = optionsList.get(randomIndex);
            Country_Select.selectByVisibleText(randomCountry);
            Country_Name = randomCountry;
        }
        else {
        	Country_Select.selectByVisibleText(Appointment_timezone);
        	Country_Name = Appointment_timezone;
        }
        Thread.sleep(1000);
        WebElement CurrentCountry = driver.findElement(By.xpath("//*[@id=\"select2-countryid-container\"]"));
        String name = CurrentCountry.getText();
        System.out.println("Appointment Country Name : " + name );
        WebElement Current_TimeZone = driver.findElement(By.id("endusercountry"));
        CT = Current_TimeZone.getAttribute("value");
        new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));
        WebElement next = driver.findElement(By.xpath("//*[@id='demo-2']/div[3]/div[1]/table/thead/tr[2]/th[3]"));
        next.click();
        Thread.sleep(3000);
        WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Date)));
        element1.click();
        Thread.sleep(1000);
        WebElement Next = driver.findElement(By.id(Meeting_time));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Next);
        Next.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@id='nextbtn']")).click();
        WebElement AD = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='pe-4']")));
        String Appointment_Details = AD.getText();
        System.out.println("Appointment Details : " + Appointment_Details );
        driver.findElement(By.id("name")).sendKeys("Time Zone");
        driver.findElement(By.id("lastname")).sendKeys("verification");
        driver.findElement(By.id("email")).sendKeys("sathishtest16@gmail.com");
        Thread.sleep(1000);
        driver.findElement(By.id("btn_submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[2]/div/div/div/h3")));
        return new Object[] {CT,name};
    }
 
    public static void TimeZone_Convert(WebDriver driver,Object Current_Timezone , Object Convert_Timezine , String Meeting_time ,int day,int month ) throws Exception{
        int A_month = month;  
        int A_year = 2024; 
        String time = Meeting_time; 
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime localTime;
        localTime = LocalTime.parse(time, timeFormatter);
        LocalDate localDate = LocalDate.of(A_year, A_month, day);
        ZoneId initialZoneId = ZoneId.of((String) Current_Timezone);
        ZonedDateTime customDateTime = ZonedDateTime.of(localDate, localTime, initialZoneId);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a ");
        customDateTime.format(dateTimeFormatter);    
        ZoneId newZoneId = ZoneId.of((String) Convert_Timezine);
        ZonedDateTime newZoneDateTime = customDateTime.withZoneSameInstant(newZoneId);
        String formattedNewZoneTime = newZoneDateTime.format(dateTimeFormatter);
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
        String dayPart = newZoneDateTime.format(dayFormatter);
        DateTimeFormatter timeFormatterOnly = DateTimeFormatter.ofPattern("h:mm a");
        String timePart = newZoneDateTime.format(timeFormatterOnly);
        System.out.println(Convert_Timezine + " : " + formattedNewZoneTime);
		driver.findElement(By.xpath("//*[@id=\"demo-2\"]/div[3]/div[1]/table/thead/tr[2]/th[3]")).click();
		Thread.sleep(3000);
		int dayPartInt = Integer.parseInt(dayPart); 
		driver.findElement(By.xpath("//td[@class='day']//div[@class='span' and normalize-space(text())='" + dayPartInt + "']")).click();
		Thread.sleep(1800);
		WebElement Current_Time = driver.findElement(By.id(timePart));
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", Current_Time);
		String classAttributeValue = Current_Time.getAttribute("class");
		if (classAttributeValue.contains("disabled-sec")) {
			System.out.println(Convert_Timezine + " : Bloked "+"\n");
		} else {
			System.err.println(Convert_Timezine + " : Not Bloked "+"\n");
		}       
    }    
    
    public static void verfiy_Slot_Bloking(String Date , String Time , Object appointment_Timezone2) throws Exception {
		driver.findElement(By.xpath("//*[@id=\"demo-2\"]/div[3]/div[1]/table/thead/tr[2]/th[3]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@class='span' and contains(text(), '" + Date + "')]")).click();
		Thread.sleep(1800);
		WebElement Current_Time = driver.findElement(By.id(Time));
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", Current_Time);
		String classAttributeValue = Current_Time.getAttribute("class");
		if (classAttributeValue.contains("disabled-sec")) {
			System.out.println(appointment_Timezone2 + " : Bloked "+"\n");
		} else {
			System.err.println(appointment_Timezone2 + " : Not Bloked "+"\n");
		}
    }

    public String Appointment(WebDriver driver, String Ctype, String Fn, String Ln, String mail, String parentwindowhandle) throws InterruptedException {
        String r_url;
        String Meeting_ID = "";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement Country = driver.findElement(By.id("countryid"));
        Select Country_Select = new Select(Country);
        WebElement type = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[1]/div"));
        String Meeting_Type = type.getText();
        if (!Meeting_Type.contains("In person")) {
        	String link = driver.getCurrentUrl();
        	if (link.contains("DemoimeetUX710xcz")) {
        		Country_Select.selectByVisibleText("India Standard Time");
        	} else {
        		Country_Select.selectByVisibleText("Chennai, Kolkata, Mumbai, New Delhi");
        	}
            
        } 
        new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));
        String Next_Button = "//*[@id=\"demo-2\"]/div[3]/div[1]/table/thead/tr[2]/th[3]/i";
        Thread.sleep(1000);
        WebElement next = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(Next_Button)));
        next.click();
        Thread.sleep(3500);
        try {
            driver.findElement(By.xpath(Date_Selector)).click();
        } catch (StaleElementReferenceException e) {
            Thread.sleep(5000);
            driver.findElement(By.xpath(Date_Selector)).click();
        }
        WebElement FT = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.elementToBeClickable(
                        By.id("10:00 AM")));
        FT.click();
      	WebElement NB = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id("nextbtn")));
        Thread.sleep(1000);
        NB.click();
        driver.findElement(By.id("name")).sendKeys(Fn);
        driver.findElement(By.id("lastname")).sendKeys(Ln);
        driver.findElement(By.id("email")).sendKeys(mail);
        Thread.sleep(1000);
        WebElement Get_A_Type = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[1]/div/div[1]/p[2]"));
        String A_Type = Get_A_Type.getText();
        if (A_Type.equals("Webconference") || A_Type.equals("In person")) {
            driver.findElement(By.id("btn_submit")).click();
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[2]/div/div/div/h3")));
            if (element.isDisplayed()) {
                Reporter.log("\n" + Ctype + " Appointment successfully<br/>");
                System.out.println(Ctype + " Appointment successfully");
                
                r_url = driver.getCurrentUrl();
                Meeting_ID = r_url.substring(r_url.indexOf("meeting=", 8), r_url.indexOf("&", 8));
            } else {
                System.err.println(Ctype + " Not Able to Appointment ");
                Reporter.log(Ctype + " Not Able to Appointment<br/>");
                driver.close();
                driver.switchTo().window(parentwindowhandle);
            }
        } else if (A_Type.contains("Hybrid")) {
            String current = A_Type.substring(8, A_Type.length() - 1);
            String online = "Online";
            String inperson = "In person";
            if (current.equals(online) || current.equals(inperson)) {
                driver.findElement(By.id("btn_submit")).click();
                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(60));
                WebElement element = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[2]/div/div/div/h3")));
                if (element.isDisplayed()) {
                    Reporter.log('\n' + Ctype + " Appointment successfully<br/>");
                    System.out.println( Ctype + " Appointment successfully");
                    r_url = driver.getCurrentUrl();
                    Meeting_ID = r_url.substring(r_url.indexOf("meeting=", 8), r_url.indexOf("&", 8));
                } else {
                    System.err.println(Ctype + " Not Able to Appointment ");
                }
             }
            else {       	
                driver.findElement(By.id("mobile")).sendKeys("5382948430");
                driver.findElement(By.id("btn_submit")).click();
                WebElement element = new WebDriverWait(driver, Duration.ofSeconds(50))
                        .until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[2]/div/div/div/h3")));

                if (element.isDisplayed()) {
                    Reporter.log("\n" + Ctype + " Appointment successfully<br/>");
                    System.out.println(Ctype + " Appointment successfully");
                    r_url = driver.getCurrentUrl();
                    Meeting_ID = r_url.substring(r_url.indexOf("meeting=", 8), r_url.indexOf("&", 8));
                } else {
                    System.err.println(Ctype + " Not Able to Appointment ");
                }
            }
            
        } else {
            driver.findElement(By.id("mobile")).sendKeys("5382948430");
            driver.findElement(By.id("btn_submit")).click();
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(50))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[2]/div/div/div/h3")));

            if (element.isDisplayed()) {
                Reporter.log("\n" + Ctype + " Appointment successfully<br/>");
                System.out.println(Ctype + " Appointment successfully");
                r_url = driver.getCurrentUrl();
                Meeting_ID = r_url.substring(r_url.indexOf("meeting=", 8), r_url.indexOf("&", 8));
            } else {
                System.err.println(Ctype + " Not Able to Appointment ");
            }
        }
        driver.close();
        driver.switchTo().window(parentwindowhandle);
        return Meeting_ID;
    }

	public void Reschedule(WebDriver driver, String Ctype, String parentwindowhandle,String Hybrid_Type) throws InterruptedException {
        Thread.sleep(2500);
		WebElement RS = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/div[1]/div/div[2]/div/div/div/div/div[2]/div/div[2]/a/button")));
        RS.click();
        if (Ctype.contains("Hybrid")) {
        	driver.findElement(By.xpath(Hybrid_Type)).click();
        }
        Thread.sleep(1000);
        WebElement DT = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(Date_Selector)));
        DT.click();
        WebElement ST = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id("12:30 PM")));
        ST.click();
    	WebElement NB = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id("nextbtn")));
        Thread.sleep(1000);
        NB.click();
        WebElement Get_A_Type = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[1]/div/div[1]/p[4]"));
        String A_Type = Get_A_Type.getText();
        if (A_Type.contains("Hybrid")) {
            String current = A_Type.substring(8, A_Type.length() - 1);;
            String PhoneCall = "Over phone";
            if (current.equals(PhoneCall)){
            	driver.findElement(By.id("mobile")).sendKeys("5382948430");
            }
        } 
        Thread.sleep(1200);
        driver.findElement(By.id("btn_submit")).click();
        WebElement element1 = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[2]/div/div/div/h3")));
        if (element1.isDisplayed()) {
            Reporter.log("\n" + Ctype + " Appointment Rescheduled successfully<br/>");
            System.out.println( Ctype + " Appointment Rescheduled successfully");
        } else {
            System.err.println(Ctype + " Not Able to Rescheduled ");
        }
        driver.close();
        driver.switchTo().window(parentwindowhandle);
            
        
            
    }

    public void Cancel(WebDriver driver, String Ctype, String Invitee_Name) throws InterruptedException {
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement Next = driver.findElement(By.xpath("//*[@id=\"calendar\"]/div[1]/div[1]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", Next);
        Next.click();
        Thread.sleep(1200);
        WebElement DDS = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(Dashboad_Date_Selector)));
        DDS.click();
        WebElement Invitee_Details = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//h6[contains(text(),'" + Invitee_Name + "')]")));
        Invitee_Details.click();
        WebElement Type = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[4]/td[2]/p")));
        String Appointment_Type = Type.getText();
        if (Type.isDisplayed()) {
           Reporter.log ("\n" + "Appointment Type : <b>"+ Appointment_Type + "</b><br/>");
           System.out.println( "Appointment Type : " + Appointment_Type);
        } else {
            System.err.println("Appointment Type Not Showing");
            Reporter.log ("\n" + "<span style='color: red;'><b>Appointment Type Not Showing</b></span>"+ "</b><br/>");
            
        }

        if (Appointment_Type.contains("Online meeting")) {
            WebElement get_Tag = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[5]/td[2]/p/a | //*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[5]/td[2]/p/button"));
            String Tag = get_Tag.getTagName();
            if (Tag.equals("a")) {
                String Meeting_Link = get_Tag.getAttribute("href");
                Reporter.log("\n" + "Meeting Link : <a href=\"" + Meeting_Link + "\" style=\"color:blue;\">" + Meeting_Link + "</a><br/>");
                System.out.println("Meeting Link : " + Meeting_Link);
                WebElement GetMail = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[6]/td[2]/p/a"));
                String Invitee_Mail_ID = GetMail.getText();
                Reporter.log("\n" + "This a Invitee Mail Id : " + Invitee_Mail_ID+"<br/>");
                System.out.println("This a Invitee Mail Id : " + Invitee_Mail_ID);
            }else if (Tag.equals("button")) {
                WebElement GetMail = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[6]/td[2]/p/a"));
                String Invitee_Mail_ID = GetMail.getText();
                Reporter.log("\n" + "This a Invitee Mail Id : " + Invitee_Mail_ID+"<br/>");
                System.out.println("This a Invitee Mail Id : " + Invitee_Mail_ID);
            	System.out.println("\n" + "Meeting Link Not Generated Showing Generate link Button<br/>");
            	Reporter.log("\n" + "Meeting Link Not Generated Showing Generate link Button<br/>");
            	Reporter.log("\n" + "<span style='color: red;'><b>Meeting Link Not Generated Showing Generate link Button</b></span>" + "<br/>");
            }
             else {
            	Reporter.log("\n" + "<span style='color: red;'><b>Meeting Link Not Generated & Generate button also not Displayed</b></span>" + "<br/>");
                System.out.println("Meeting Link Not Generated & Generate button also not Displayed");
            }
        } else if (Appointment_Type.equals("Over Phone")) {
            WebElement Mobile = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[7]/td[2]/p"));
            String Number = Mobile.getText();
            if (Mobile.isDisplayed()) {
                Reporter.log("\n" + "This is a Mobile Number :  " + Number+"<br/>");
                System.out.println("This is a Mobile Number :  " + Number);
                WebElement GetMail = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[5]/td[2]/p/a"));
                String Invitee_Mail_ID = GetMail.getText();
                Reporter.log("\n" + "This a Invitee Mail Id : " + Invitee_Mail_ID+"<br/>");
                System.out.println("This a Invitee Mail Id : " + Invitee_Mail_ID);

            } else {
            	Reporter.log("\n" + "<span style='color: red;'><b>Mobile Number is Not Showing </b></span>" + "<br/>");
                System.out.println(" Mobile Number is Not Showing ");
            }
        } else if (Appointment_Type.equals("In-person")) {
            WebElement GetMail = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[5]/td[2]/p/a"));
            String Invitee_Mail_ID = GetMail.getText();
            Reporter.log("\n" + "This a Invitee Mail Id : " + Invitee_Mail_ID +"<br/>");
            System.out.println("This a Invitee Mail Id : " + Invitee_Mail_ID);

        } else {
        	Reporter.log("\n" + "<span style='color: red;'><b> Appointment Type IS Not Showing</b></span>" + "<br/>");
            System.out.println(" Appointment Type IS Not Showing ");
        }
        WebElement Get_Appointment_Time = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[1]/h5/span"));
        String Appointment_Time = Get_Appointment_Time.getText();
        System.out.println("Meeting Time : " + Appointment_Time);
        Reporter.log("\n" + "Meeting Time : " + Appointment_Time + "<br/>");
        WebElement Cancel_Button = driver.findElement(By.xpath("//*[@id=\"calendar\"]/div[1]/div[1]"));
        JavascriptExecutor AS = (JavascriptExecutor) driver;
        AS.executeScript("arguments[0].scrollIntoView(true);", Cancel_Button);
        driver.findElement(By.xpath("//button[contains(text(),'Cancel this meeting')]")).click();
        WebElement Cancel_InputTag = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[@id=\"reason-ts-control\"]")));
        Cancel_InputTag.click();
        WebElement dropdown = driver.findElement(By.xpath("//*[@id='reason']"));
        Select select = new Select(dropdown);
        List<WebElement> options = select.getOptions();
        List<String> optionTexts = new ArrayList<>();
        for (WebElement option : options) {
            String disabledAttribute = option.getAttribute("disabled");
            if (disabledAttribute == null || !disabledAttribute.equals("true")) {
                optionTexts.add(option.getText());

            }
        }
        String selectedOption = optionTexts.get(new Random().nextInt(optionTexts.size()));
        select.selectByVisibleText(selectedOption);
        if (selectedOption.equals("Others - not listed")) {
            driver.findElement(By.xpath("//*[@id=\"feedback\"]")).sendKeys("I can't able to attend the meeting , Sorry ");
        }
        Reporter.log("\n" + "Cancel Reason : " + selectedOption + "<br/>");
        System.out.println("Cancel Reason : " + selectedOption);
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id=\"meeting_delete\"]/div/div/div[3]/button")));
        element.click();
        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id=\"delete_meeting_confirm\"]/div/div/div[3]/button")));
        button.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    	WebElement cancel_confrim = driver.findElement(By.xpath("//*[@id=\"cancel_confirm_host\"]/div/div/div"));
    	wait.until(ExpectedConditions.invisibilityOf(cancel_confrim));
        Reporter.log("\n" + Ctype + " Appointment Canceled Successfully<br/>");
        System.out.println( Ctype + " Appointment Canceled Successfully"+"\n"+"\n");
        ExpectedCondition<Boolean> pageLoadCondition = driverInstance  -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        // Use WebDriverWait to wait up to 30 seconds for the page to load
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(pageLoadCondition);
        


    }

    public void First_Slot_Blocking_Verfiy(WebDriver driver, String Ctype, String parentwindowhandle, String FirstTime) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement Country = driver.findElement(By.id("countryid"));
        Select Country_Select = new Select(Country);
        WebElement type = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[1]/div"));
        String Meeting_Type = type.getText();
        if (!Meeting_Type.contains("In person")) {
        	String link = driver.getCurrentUrl();
        	if (link.contains("DemoimeetUX710xcz")) {
        		Country_Select.selectByVisibleText("India Standard Time");
        	} else {
        		Country_Select.selectByVisibleText("Chennai, Kolkata, Mumbai, New Delhi");
        	}
            
        }     	
        WebElement n = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));
        String Next_Button = "//*[@id=\"demo-2\"]/div[3]/div[1]/table/thead/tr[2]/th[3]/i";
        Thread.sleep(2000);
        WebElement next = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(Next_Button)));
        next.click();
        Thread.sleep(3000);
        driver.findElement(By.xpath(Date_Selector)).click();
        WebElement Slot_1 = driver.findElement(By.id(FirstTime));
        String classAttributeValue = Slot_1.getAttribute("class");
        if (classAttributeValue.contains("disabled-sec")) {
            Reporter.log("\n" + Ctype + " First Block working Fine<br/>");
            System.out.println(Ctype + " First Block working Fine");
        } else {
            System.err.println(Ctype + " First Block is Not Working");
        }
    }

    public void Secound_Slot_Blocking_Verfiy(WebDriver driver, String Ctype, String parentwindowhandle, String SecoundTime, String FirstTime) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().refresh();
        WebElement Country = driver.findElement(By.id("countryid"));
        Select Country_Select = new Select(Country);
        WebElement type = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[1]/div"));
        String Meeting_Type = type.getText();
        if (!Meeting_Type.contains("In person")) {
        	String link = driver.getCurrentUrl();
        	if (link.contains("DemoimeetUX710xcz")) {
        		Country_Select.selectByVisibleText("India Standard Time");
        	} else {
        		Country_Select.selectByVisibleText("Chennai, Kolkata, Mumbai, New Delhi");
        	}
            
        } 
        try {
            driver.findElement(By.xpath(Date_Selector)).click();
        } catch (ElementClickInterceptedException e) {
            Thread.sleep(5000);
            driver.findElement(By.xpath(Date_Selector)).click();
        }

        WebElement Slot_1 = driver.findElement(By.id(FirstTime));
        String classAttributeValue1 = Slot_1.getAttribute("class");
        if (classAttributeValue1.contains("disabled-sec")) {
            System.err.println("First Slot Blocking Not Relased");
        }
        WebElement Slot_2 = driver.findElement(By.id(SecoundTime));
        String classAttributeValue2 = Slot_2.getAttribute("class");
        if (classAttributeValue2.contains("disabled-sec")) {
            Reporter.log("\n" + Ctype + " Secound Block working Fine<br/>");
            System.out.println(Ctype + " Secound Block working Fine");
        } else {
            System.err.println(Ctype + " Secound Block is Not Working");
        }
        driver.close();
        driver.switchTo().window(parentwindowhandle);
    }

    public void Recurring_Appointment(WebDriver driver, String[] times, String[] Date,String Fn, String Ln, String mail , String Calendar_Link , String parentWindowHandle) throws InterruptedException {
    	String initialPageSource = driver.getPageSource();
    	WebElement next = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"demo-2\"]/div[2]/div[1]/table/thead/tr[2]/th[3]/i")));
        next.click();
        Thread.sleep(3500);
    	for(int a=0;a<Date.length;a++) {
    		driver.findElement(By.xpath(Date[a])).click();
    		Thread.sleep(2500);
    		driver.findElement(By.id(times[a])).click();
        	}
    	Thread.sleep(1200);
        WebElement Next = driver.findElement(By.id("nextbtn"));
        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", Next);
        Next.click();
        Thread.sleep(1200);
        driver.findElement(By.xpath("//*[@id=\"new-name\"]")).sendKeys(Fn);
        driver.findElement(By.xpath("//*[@id=\"new-lname\"]")).sendKeys(Ln);
        driver.findElement(By.xpath("//*[@id=\"new-email\"]")).sendKeys(mail);
        
        WebElement Proceed_to_confirm = driver.findElement(By.id("confirm-appt"));
        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", Proceed_to_confirm);
        Proceed_to_confirm.click();
        Thread.sleep(1200);
        
        driver.findElement(By.id("proceed_to_confirm")).click();
        
        Reporter.log("\n" + "Three Slots Booked for Invitee<br/>");
        System.out.println("Three Slots Booked for Invitee");
    	
        
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(Calendar_Link);
        WebElement Country = driver.findElement(By.id("countryid"));
        Select Country_Select = new Select(Country);
        WebElement type = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[1]/div"));
        String Meeting_Type = type.getText();
        if (!Meeting_Type.contains("In person")) {
        	String link = driver.getCurrentUrl();
        	if (link.contains("DemoimeetUX710xcz")) {
        		Country_Select.selectByVisibleText("India Standard Time");
        	} else {
        		Country_Select.selectByVisibleText("Chennai, Kolkata, Mumbai, New Delhi");
        	}
            
        } 
    	WebElement next1 = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"demo-2\"]/div[3]/div[1]/table/thead/tr[2]/th[3]")));
        next1.click();
        Thread.sleep(3500);
    	for(int a=0;a<Date.length;a++) {
    		driver.findElement(By.xpath(Date[a])).click();
    		Thread.sleep(1500);
    		WebElement First_Time_Verify = driver.findElement(By.id(times[a]));
    		String classAttributeValue = First_Time_Verify.getAttribute("class");
            if (classAttributeValue.contains("disabled-sec")) {
                System.out.println(times[a] + " : Blocked ");
                Reporter.log("\n" + times[a] + " : <b>Blocked</b></span> <br/>");        
            } else {
                System.err.println(times[a] + " : Not Blocked");
                Reporter.log("\n" + times[a] + " : <span style='color: red;'><b>Not Blocked</b></span> <br/>");
                try {
                    driver.findElement(By.id(times[a])).click();
                  	WebElement NB = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id("nextbtn")));
                    Thread.sleep(1000);
                    NB.click();
                    driver.findElement(By.id("name")).sendKeys(Fn);
                    driver.findElement(By.id("lastname")).sendKeys(Ln);
                    driver.findElement(By.id("email")).sendKeys(mail);
                    Thread.sleep(1000);
                    WebElement Get_A_Type = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[1]/div/div[1]/p[2]"));
                    String A_Type = Get_A_Type.getText();
                    if (A_Type.equals("Webconference") || A_Type.equals("In person")) {
                        driver.findElement(By.id("btn_submit")).click();
                        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[2]/div/div/div/h3")));
                        if (element.isDisplayed()) {
                            Reporter.log("\n" + " Appointment successfully<br/>");
                            System.out.println(" Appointment successfully");
                        } else {
                            System.err.println(" Not Able to Appointment ");
                            Reporter.log(" Not Able to Appointment<br/>");
                            driver.close();
                            driver.switchTo().window(parentWindowHandle);
                        }
                    }
                } catch (Exception E) {
                	System.out.println("slot not Blocking try to appointment but not able to appointment");
                }                
            }
            
        }
    	driver.close();
        driver.switchTo().window(parentWindowHandle);

        
    	
    }

    public void Recurring_Appointment_Cancel(WebDriver driver, String Invitee_Name,String Select_Recurring_Date) throws InterruptedException {
        Thread.sleep(3500);
    	WebElement Button_View = driver.findElement(By.xpath("//*[@class='day-number selected']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", Button_View);
        
        WebElement Next1 = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id=\"calendar\"]/div[1]/div[1]")));
        Next1.click();
        Thread.sleep(3500);
        
        WebElement get_date = driver.findElement(By.xpath(Select_Recurring_Date));
        get_date.click();

        WebElement Invitee_Details = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//h6[contains(text(),'" + Invitee_Name + "')]")));
        Invitee_Details.click();
        WebElement Appointment_Date = driver.findElement(By.xpath("//*[@id=\"apptListSec\"]/ul/h6"));
        String Cancel_Date = Appointment_Date.getText();
        Reporter.log("\n" + "Meeting Date : "+ Cancel_Date + "<br/>");
        System.out.println( "\nMeeting Date : "+ Cancel_Date );
        WebElement Type = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[4]/td[2]/p")));
        String Appointment_Type = Type.getText();
        if (Type.isDisplayed()) {
           Reporter.log ("\n" + "Appointment Type : <b>"+ Appointment_Type + "</b><br/>");
           System.out.println( "Appointment Type : " + Appointment_Type);
        } else {
            System.err.println("Appointment Type Not Showing");
            Reporter.log ("\n" + "<span style='color: red;'><b>Appointment Type Not Showing</b></span>"+ "</b><br/>");
            
        }

        if (Appointment_Type.contains("Online meeting")) {
            WebElement get_Tag = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[5]/td[2]/p/a | //*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[5]/td[2]/p/button"));
            String Tag = get_Tag.getTagName();
            if (Tag.equals("a")) {
                String Meeting_Link = get_Tag.getAttribute("href");
                Reporter.log("\n" + "Meeting Link : <a href=\"" + Meeting_Link + "\" style=\"color:blue;\">" + Meeting_Link + "</a><br/>");
                System.out.println("Meeting Link : " + Meeting_Link);
                WebElement GetMail = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[6]/td[2]/p/a"));
                String Invitee_Mail_ID = GetMail.getText();
                Reporter.log("\n" + "This a Invitee Mail Id : " + Invitee_Mail_ID+"<br/>");
                System.out.println("This a Invitee Mail Id : " + Invitee_Mail_ID);
            }else if (Tag.equals("button")) {
                WebElement GetMail = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[6]/td[2]/p/a"));
                String Invitee_Mail_ID = GetMail.getText();
                Reporter.log("\n" + "This a Invitee Mail Id : " + Invitee_Mail_ID+"<br/>");
                System.out.println("This a Invitee Mail Id : " + Invitee_Mail_ID);
            	System.out.println("\n" + "Meeting Link Not Generated Showing Generate link Button<br/>");
            	Reporter.log("\n" + "Meeting Link Not Generated Showing Generate link Button<br/>");
            	Reporter.log("\n" + "<span style='color: red;'><b>Meeting Link Not Generated Showing Generate link Button</b></span>" + "<br/>");
            }
             else {
            	Reporter.log("\n" + "<span style='color: red;'><b>Meeting Link Not Generated & Generate button also not Displayed</b></span>" + "<br/>");
                System.out.println("Meeting Link Not Generated & Generate button also not Displayed");
            }
        } else if (Appointment_Type.equals("Over Phone")) {
            WebElement Mobile = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[7]/td[2]/p"));
            String Number = Mobile.getText();
            if (Mobile.isDisplayed()) {
                Reporter.log("\n" + "This is a Mobile Number :  " + Number+"<br/>");
                System.out.println("This is a Mobile Number :  " + Number);
                WebElement GetMail = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[5]/td[2]/p/a"));
                String Invitee_Mail_ID = GetMail.getText();
                Reporter.log("\n" + "This a Invitee Mail Id : " + Invitee_Mail_ID+"<br/>");
                System.out.println("This a Invitee Mail Id : " + Invitee_Mail_ID);

            } else {
            	Reporter.log("\n" + "<span style='color: red;'><b>Mobile Number is Not Showing </b></span>" + "<br/>");
                System.out.println(" Mobile Number is Not Showing ");
            }
        } else if (Appointment_Type.equals("In-person")) {
            WebElement GetMail = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[2]/table/tbody/tr[5]/td[2]/p/a"));
            String Invitee_Mail_ID = GetMail.getText();
            Reporter.log("\n" + "This a Invitee Mail Id : " + Invitee_Mail_ID +"<br/>");
            System.out.println("This a Invitee Mail Id : " + Invitee_Mail_ID);

        } else {
        	Reporter.log("\n" + "<span style='color: red;'><b> Appointment Type IS Not Showing</b></span>" + "<br/>");
            System.out.println(" Appointment Type IS Not Showing ");
        }
        WebElement Get_Appointment_Time = driver.findElement(By.xpath("//*[@id=\"screen8\"]/div/div[1]/h5/span"));
        String Appointment_Time = Get_Appointment_Time.getText();
        System.out.println("Meeting Time : " + Appointment_Time);
        Reporter.log("\n" + "Meeting Time : " + Appointment_Time + "<br/>");
        WebElement Cancel_Button = driver.findElement(By.xpath("//*[@id=\"calendar\"]/div[1]/div[1]"));
        JavascriptExecutor AS = (JavascriptExecutor) driver;
        AS.executeScript("arguments[0].scrollIntoView(true);", Cancel_Button);
        driver.findElement(By.xpath("//button[contains(text(),'Cancel this meeting')]")).click();
        WebElement Cancel_InputTag = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[@id=\"reason-ts-control\"]")));
        Cancel_InputTag.click();
        WebElement dropdown = driver.findElement(By.xpath("//*[@id='reason']"));
        Select select = new Select(dropdown);
        List<WebElement> options = select.getOptions();
        List<String> optionTexts = new ArrayList<>();
        for (WebElement option : options) {
            String disabledAttribute = option.getAttribute("disabled");
            if (disabledAttribute == null || !disabledAttribute.equals("true")) {
                optionTexts.add(option.getText());

            }
        }
        String selectedOption = optionTexts.get(new Random().nextInt(optionTexts.size()));
        select.selectByVisibleText(selectedOption);
        if (selectedOption.equals("Others - not listed")) {
            driver.findElement(By.xpath("//*[@id=\"feedback\"]")).sendKeys("I can't able to attend the meeting , Sorry ");
        }
        Reporter.log("\n" + "Cancel Reason : " + selectedOption + "<br/>");
        System.out.println("Cancel Reason : " + selectedOption);
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id=\"meeting_delete\"]/div/div/div[3]/button")));
        element.click();
        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id=\"delete_meeting_confirm\"]/div/div/div[3]/button")));
        button.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    	WebElement cancel_confrim = driver.findElement(By.xpath("//*[@id=\"cancel_confirm_host\"]/div/div/div"));
    	wait.until(ExpectedConditions.invisibilityOf(cancel_confrim));

        ExpectedCondition<Boolean> pageLoadCondition = driverInstance  -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        // Use WebDriverWait to wait up to 30 seconds for the page to load
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(pageLoadCondition);
        
    }

    public void Approval_Pending(WebDriver driver, String Calendar_link, String parentwindowhandle, String Email) throws InterruptedException {
        String First_Time = "10:00 AM";
        String Secound_Time = "10:30 AM";
        String S_First_Time = "12:00 PM";
        String S_Secound_Time = "12:30 PM";
        driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(Calendar_link);
        WebElement Country = driver.findElement(By.id("countryid"));
        Select Country_Select = new Select(Country);
        WebElement type = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[1]/div"));
        String Meeting_Type = type.getText();
        if (!Meeting_Type.contains("In person")) {
        	String link = driver.getCurrentUrl();
        	if (link.contains("DemoimeetUX710xcz")) {
        		Country_Select.selectByVisibleText("India Standard Time");
        	} else {
        		Country_Select.selectByVisibleText("Chennai, Kolkata, Mumbai, New Delhi");
        	}
            
        } 
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        WebElement n = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));
        String Next_Button = "//*[@id=\"demo-2\"]/div[3]/div[1]/table/thead/tr[2]/th[3]/i";
        WebElement next = new WebDriverWait(driver, Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(Next_Button)));
        next.click();
        Thread.sleep(3500);
        try {
        	WebElement DS = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(Date_Selector)));
        	DS.click();
        } catch (Exception JR) {
        	WebElement DS1 = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(Date_Selector)));
        	DS1.click();
        }
    
    	WebElement FT = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id(First_Time)));
    	FT.click();
    	WebElement ST = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id(Secound_Time)));
    	ST.click();
    	Thread.sleep(1500);
        WebElement NB = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id("nextbtn")));
        NB.click(); 
    	WebElement name = new WebDriverWait(driver,Duration.ofSeconds(50))
    	        .until(ExpectedConditions.visibilityOfElementLocated(
    	                By.name("name")));
    	name.sendKeys("First");
		driver.findElement(By.id("lastname")).sendKeys("Request");
		driver.findElement(By.id("email")).sendKeys(Email);
		driver.findElement(By.id("btn_submit")).click();
    	WebElement element = new WebDriverWait(driver,Duration.ofSeconds(50))
    	        .until(ExpectedConditions.visibilityOfElementLocated(
    	                By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[2]/div/div/div/h3")));
    	if (element.isDisplayed()) {
    	    System.out.println("\n"+"First Appointment Request Sended successfully");
    	    Reporter.log("First Appointment Request Sended successfully<br/>");
    	} else {
    	    System.err.println("First Appointment Request Not Sended");
    	    Reporter.log("First Appointment Request Not Sended<br/>");
    	}
    	driver.get(Calendar_link);
        WebElement Country1 = driver.findElement(By.id("countryid"));
        Select Country_Select1 = new Select(Country1);
        WebElement type1 = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[1]/div"));
        String Meeting_Type1 = type1.getText();
        if (!Meeting_Type1.contains("In person")) {
        	String link = driver.getCurrentUrl();
        	if (link.contains("DemoimeetUX710xcz")) {
        		Country_Select1.selectByVisibleText("India Standard Time");
        	} else {
        		Country_Select1.selectByVisibleText("Chennai, Kolkata, Mumbai, New Delhi");
        	}
            
        }  
    	WebElement n1 = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));
    	next = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"demo-2\"]/div[3]/div[1]/table/thead/tr[2]/th[3]")));
		next.click();

        try {
    		Thread.sleep(5000);
            driver.findElement(By.xpath(Date_Selector)).click();
        } catch (ElementClickInterceptedException e) {
        	driver.navigate().refresh();
            Thread.sleep(5000);
            driver.findElement(By.xpath(Date_Selector)).click();
        }
    	WebElement FV = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id(First_Time)));
    	WebElement SV = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id(Secound_Time)));
    	String FCV = FV.getAttribute("class");
    	String SCV = SV.getAttribute("class");
    	if (FCV.contains("disabled-sec") && SCV.contains("disabled-sec")){
    		System.out.println("Approval pending flow blocking is working fine");
    		Reporter.log("Approval pending flow blocking is working fine<br/>");
    		driver.close();
    		driver.switchTo().window(parentwindowhandle);
    	}
    	else {
    		System.err.println("Approval pending flow blocking is working fine , Showing block avaliable");
    		Reporter.log("Approval pending flow blocking is working fine , Showing block avaliable<br/>");
    		driver.close();
    		driver.switchTo().window(parentwindowhandle);
    	}
    	driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[1]/div/ul/li[2]/a")).click();
    	driver.findElement(By.xpath("//*[@id=\"pending-details\"]/div/div[4]/a")).click();
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
    	driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/main/div/div/div/div[2]/div/div/div/div[2]/div/div[3]/button")).click();
    	WebElement proceed_button = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id("app-remove")));
    	proceed_button.click();
    	WebElement Close1 = driver.findElement(By.xpath("//*[@id=\"app-failure\"]/div/div/div[1]"));
    	wait.until(ExpectedConditions.invisibilityOf(Close1));
    	Thread.sleep(2500);
    	WebElement RC = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable((By.xpath("//*[@id=\"app-failure\"]/div/div/div[3]/button"))));
    	RC.click();
    	WebElement element1 = driver.findElement(By.xpath("//*[@id=\"app-failure\"]/div/div/div[1]"));
    	wait.until(ExpectedConditions.invisibilityOf(element1));
    	System.out.println("Appointment request rejected succuessfully");
    	Reporter.log("Appointment request rejected succuessfully<br/>");
    	driver.switchTo().newWindow(WindowType.TAB);
    	driver.get(Calendar_link);
        WebElement Country3 = driver.findElement(By.id("countryid"));
        Select Country_Select3 = new Select(Country3);
        WebElement type3 = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[1]/div"));
        String Meeting_Type3 = type3.getText();
        if (!Meeting_Type3.contains("In person")) {
        	String link = driver.getCurrentUrl();
        	if (link.contains("DemoimeetUX710xcz")) {
        		Country_Select3.selectByVisibleText("India Standard Time");
        	} else {
        		Country_Select3.selectByVisibleText("Chennai, Kolkata, Mumbai, New Delhi");
        	}
            
        } 
    	n1 = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@class = \"today day\"]")));
    	next = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"demo-2\"]/div[3]/div[1]/table/thead/tr[2]/th[3]")));
		next.click();
		Thread.sleep(3500);
		try {
			DS = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(Date_Selector)));
	    	DS.click();
		} catch (StaleElementReferenceException o) {
			Thread.sleep(4000);
			DS = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(Date_Selector)));
	    	DS.click();
		}
    	
    	FT = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id(S_First_Time)));
    	FT.click();
    	ST = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id(S_Secound_Time)));
    	ST.click();
    	Thread.sleep(1500);
        NB = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id("nextbtn")));
        NB.click(); 
    	name = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
    	name.sendKeys("Secound");
		driver.findElement(By.id("lastname")).sendKeys("Request");
		driver.findElement(By.id("email")).sendKeys(Email);
		driver.findElement(By.id("btn_submit")).click();
        element1 = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.visibilityOfElementLocated( By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[2]/div/div/div/h3")));
    	if (element1.isDisplayed()) {
    	    System.out.println("Secound Appointment Request Sended successfully");
    	    Reporter.log("Secound Appointment Request Sended successfully<br/>");
    	} else {
    	    System.err.println("Secound Appointment Request Not Sended");
    	    Reporter.log("Secound Appointment Request Not Sended<br/>");
    	}
    	driver.get(Calendar_link);
        WebElement Country4 = driver.findElement(By.id("countryid"));
        Select Country_Select4 = new Select(Country4);
        WebElement type4 = driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div/div/div[1]/div"));
        String Meeting_Type4 = type4.getText();
        if (!Meeting_Type4.contains("In person")) {
        	String link = driver.getCurrentUrl();
        	if (link.contains("DemoimeetUX710xcz")) {
        		Country_Select4.selectByVisibleText("India Standard Time");
        	} else {
        		Country_Select4.selectByVisibleText("Chennai, Kolkata, Mumbai, New Delhi");
        	}
            
        } 
    	next = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"demo-2\"]/div[3]/div[1]/table/thead/tr[2]/th[3]")));
		next.click();
		Thread.sleep(3500);
		try {
			DS = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(Date_Selector)));
	    	DS.click();
		} catch (StaleElementReferenceException o) {
			Thread.sleep(4000);
			DS = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath(Date_Selector)));
	    	DS.click();
		}
    	 FV = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id(S_First_Time)));
    	 SV = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.id(S_Secound_Time)));
    	 FCV = FV.getAttribute("class");
    	 SCV = SV.getAttribute("class");
    	if (FCV.contains("disabled-sec") && SCV.contains("disabled-sec")){
    		System.out.println("Secound Approval pending flow blocking is working fine");
    		Reporter.log("Secound Approval pending flow blocking is working fine<br/>"); 
    		driver.close();
    		driver.switchTo().window(parentwindowhandle);
    	}
    	else {
    		System.err.println("Approval pending flow blocking is not working  , Showing block avaliable");
    		driver.close();
    		driver.switchTo().window(parentwindowhandle);
    	}
    	driver.navigate().refresh();
    	WebElement page = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pending-details\"]/div/div[4]/a")));
    	page.click();
    	driver.findElement(By.xpath("//*[@id=\"pending-details\"]/div[1]/div[4]/a")).click();
    	Thread.sleep(1500);

    	// Approve the Slot

    	WebElement aa = driver.findElement(By.xpath("//*[@id=\"screen6\"]/div[1]/div/p/span[3]/a"));
    	wait.until(ExpectedConditions.visibilityOf(aa));
    	aa.click();
    	WebElement bb = new WebDriverWait(driver,Duration.ofSeconds(10))
    	                    .until(ExpectedConditions.elementToBeClickable(By.id("app-proceed")));
    	bb.click();
    	WebElement c = new WebDriverWait(driver,Duration.ofSeconds(70))
    	                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-success\"]/div/div/div[2]/h5")));
    	Thread.sleep(1200);
    	WebElement e = new WebDriverWait(driver,Duration.ofSeconds(50)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app-success\"]/div/div/div[3]/button")));
    	e.click();
    	WebElement Close = driver.findElement(By.xpath("//*[@id=\"app-failure\"]/div/div/div[1]"));
    	wait.until(ExpectedConditions.invisibilityOf(Close));
    	driver.findElement(By.xpath("//*[@id=\"sidebar\"]/div/div[2]/nav/ul/li[1]/div/ul/li[1]")).click();
    	Functions fun = new Functions();
    	fun.Cancel(driver, "Pending Approval", "Secound Request");
    }
   
}
   

