package com.crm.qa.util;



	import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
	import java.util.List;
	import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
	import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
	import org.apache.poi.ss.usermodel.Sheet;
	import org.apache.poi.ss.usermodel.Workbook;
	import org.apache.poi.ss.usermodel.WorkbookFactory;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.crm.qa.base.TestBase;
import com.google.common.base.Verify;

import io.netty.util.Timeout;

	public class TestUtil extends TestBase {
		static WebDriver driver;

		public static long PAGE_LOAD_TIMEOUT = 20;
		public static long IMPLICIT_WAIT = 20;

		public static String TESTDATA_SHEET_PATH = "/Users/naveenkhunteta/Documents/workspace"
				+ "/FreeCRMTest/src/main/java/com/crm/qa/testdata/FreeCrmTestData.xlsx";

		static Workbook book;
		static Sheet sheet;
		static JavascriptExecutor js;

		public void switchToFrame() {
			driver.switchTo().frame("mainpanel");
		}

		public static Object[][] getTestData(String sheetName) {
			FileInputStream file = null;
			try {
				file = new FileInputStream(TESTDATA_SHEET_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				book = WorkbookFactory.create(file);
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			sheet = book.getSheet(sheetName);
			Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			// System.out.println(sheet.getLastRowNum() + "--------" +
			// sheet.getRow(0).getLastCellNum());
			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
					data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
					// System.out.println(data[i][k]);
				}
			}
			return data;
		}

		public static void takeScreenshotAtEndOfTest(String fileWithPath) throws IOException {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String currentDir = System.getProperty("user.dir");
			FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));

			TakesScreenshot srcfile = ((TakesScreenshot) driver);
			File srcshot = srcfile.getScreenshotAs(OutputType.FILE);
			File Destfile = new File(fileWithPath);
		 
		}
		
		public static void dragDrop() throws IOException{
		WebElement fromElement = driver.findElement(By.id(""));
		WebElement toElement = driver.findElement(By.name(""));
		
		
		Actions act = new Actions(driver);
			act.dragAndDrop(fromElement, toElement).build().perform();
			act.dragAndDropBy(fromElement, 56, 45).build().perform();
		}

		public void scrollDown(){
			
			
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0,3000)");
		}
		
		public void windowHandle(){
			
			String parentwindow=driver.getWindowHandle();
			Set<String> set = driver.getWindowHandles();
			
			Iterator<String>I=set.iterator();
			
			while (I.hasNext()) {
			String childwindow=I.next();
			
			if (!parentwindow.equals(childwindow)) {
				driver.switchTo().window(childwindow);
				
			}
			}
			}
			
		
			
			public void dropDown(){
				Select select =new Select(driver.findElement(By.xpath("//*[@id='oldSelectMenu']")));
				select.selectByIndex(2);
			}
			
			public class MultipleDropDown{
				Select select= new Select(driver.findElement(By.id("oldSelectMenu")));
				List<WebElement> options = select.getOptions();
			}
			
			public void MouseHover(){
				WebElement subMenuOption = driver.findElement(By.xpath(".//div[contains(text(),'Rock')]")); 
		    	//Mouse hover menuOption 'Rock'
				Actions actions=new Actions(driver);
		    	actions.moveToElement(subMenuOption).perform();
		    	System.out.println("Done Mouse hover on 'Rock' from Menu");
		    	
		    	//Now , finally, it displays the desired menu list from which required option needs to be selected
		    	//Now Select 'Alternative' from sub menu which has got displayed on mouse hover of 'Rock'
		    	WebElement selectMenuOption = driver.findElement(By.xpath(".//div[contains(text(),'Alternative')]"));
		    	selectMenuOption.click();
		    	System.out.println("Selected 'Alternative' from Menu");
			}
			public void alert(){
				driver.findElement(By.id("")).click();
				driver.switchTo().alert().accept();
			}
			
			public void alertGetText(){
				WebElement web=driver.findElement(By.id(""));
				driver.switchTo().alert().getText();	
					}
			public void iFrames(){
				driver.get("");
				driver.switchTo().frame(0);
				driver.switchTo().defaultContent();
				driver.quit();
			}
			
			public void IndexofFrames(){
			int size=driver.findElements(By.tagName("iframe")).size();
			for (int i = 0; i <=size; i++) {
				driver.switchTo().frame(i);
				int total=driver.findElements(By.xpath("")).size();
				System.out.println(total);	
			}
			}
	public void UploadFile() throws InterruptedException, AWTException{
	
		 WebElement browse = driver.findElement(By.linkText("Upload a file"));
	     // using linkText, to click on browse element 
	    browse.click(); // Click on browse option on the webpage
	    Thread.sleep(2000); // suspending execution for specified time period
		
	    Robot rb=new Robot();
	    
	    StringSelection str=new StringSelection("C:\\Users\\Chait\\Desktop\\File upload.docx");
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
	    
	    rb.keyPress(KeyEvent.VK_CONTROL);
	    rb.keyPress(KeyEvent.VK_V);
	    
	    rb.keyPress(KeyEvent.VK_ENTER);
	    rb.keyRelease(KeyEvent.VK_ENTER);
	    
	}
	
	public void brokenLinks(){
	
		String homePage = "http://www.zlti.com";
		String url = "";
		HttpURLConnection huc = null;
		int respCode = 200;

		driver = new ChromeDriver();

		driver.manage().window().maximize();

		driver.get(homePage);

		List<WebElement> links = driver.findElements(By.tagName("a"));

		Iterator<WebElement> it = links.iterator();

		while(it.hasNext()){

		url = it.next().getAttribute("href");

		System.out.println(url);

		if(url == null || url.isEmpty()){
		System.out.println("URL is either not configured for anchor tag or it is empty");
		continue;
		}

		if(!url.startsWith(homePage)){
		System.out.println("URL belongs to another domain, skipping it.");
		continue;
		}

		try {
		huc = (HttpURLConnection)(new URL(url).openConnection());

		huc.setRequestMethod("HEAD");

		huc.connect();

		respCode = huc.getResponseCode();

		if(respCode >= 400){
		System.out.println(url+" is a broken link");
		}
		else{
		System.out.println(url+" is a valid link");
		}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			}
	}
	
	public void sslCertificate(){
		
		DesiredCapabilities handlSSLErr = DesiredCapabilities.htmlUnit();
		handlSSLErr.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);
		WebDriver driver = new ChromeDriver (handlSSLErr);
		
	}
	public void DynamicPopUp(){
		WebDriverWait wait=new WebDriverWait(driver, 60);
		if (wait.until(ExpectedConditions.alertIsPresent())==null) {
			System.out.println("Alert is not displayed");
			
		}
		else {
			driver.switchTo().alert().accept();
		}
	}
		private JavascriptExecutor JavascriptExecutor(WebDriver driver2) {
		// TODO Auto-generated method stub
		return null;
	}

		public static void runTimeInfo(String messageType, String message) throws InterruptedException {
			js = (JavascriptExecutor) driver;
			// Check for jQuery on the page, add it if need be
			js.executeScript("if (!window.jQuery) {"
					+ "var jquery = document.createElement('script'); jquery.type = 'text/javascript';"
					+ "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';"
					+ "document.getElementsByTagName('head')[0].appendChild(jquery);" + "}");
			Thread.sleep(5000);

			// Use jQuery to add jquery-growl to the page
			js.executeScript("$.getScript('https://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");

			// Use jQuery to add jquery-growl styles to the page
			js.executeScript("$('head').append('<link rel=\"stylesheet\" "
					+ "href=\"https://the-internet.herokuapp.com/css/jquery.growl.css\" " + "type=\"text/css\" />');");
			Thread.sleep(5000);

			// jquery-growl w/ no frills
			js.executeScript("$.growl({ title: 'GET', message: '/' });");
	//'"+color+"'"
			if (messageType.equals("error")) {
				js.executeScript("$.growl.error({ title: 'ERROR', message: '"+message+"' });");
			}else if(messageType.equals("info")){
				js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
			}else if(messageType.equals("warning")){
				js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
			}else
				System.out.println("no error message");
			// jquery-growl w/ colorized output
//			js.executeScript("$.growl.error({ title: 'ERROR', message: 'your error message goes here' });");
//			js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
//			js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
			Thread.sleep(5000);
		}

	}


