package demo.skillport;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Tools {
	
	private AndroidDriver driver;

	private final String userId = "demo";
	private final String password = "123";
	private final String logInId = "ext-comp-1007";
	private final String actionId = "actionCommonButton";
	private final int timeout = 30;
	private final String commonIconId = "mainPanelCommonLogo";
	private final String searchIconId = "actionCommonButton";
	private final String myPlanIconId = "Personal";
	private final String myProgressIconId = "myProgressPreviewList";
	
	private final String savedDir = "D://SkillsoftDemo//";
	private final int listNumberOfOnePage = 20;

	
	/****************************************************************************
	*	Constructor
	*****************************************************************************/
	public Tools(AndroidDriver driver) {
		this.driver = driver;
		this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	
	/****************************************************************************
	*	Log tools
	*	public void log(String str)
	*	public void logElement(String str)
	*	public void logFuncProc(String str)
	*****************************************************************************/
	public void log(String str) {
		System.out.println("-> " + str);
	}
	private void logElement(String str) {
		System.out.println("---><] " + str);
	}
	
	
	/****************************************************************************
	*	Time wait tool
	*	public void sleep(double d)
	*****************************************************************************/
	public static void sleep(double d) {
		try {
			Thread.sleep((long) (d * 1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/****************************************************************************
	*	Capture tools
	*	private boolean captureScreen(String dirName, String captureName)
	*	public static InputStream runCmd(String cmd) throws Exception
	*****************************************************************************/	
	private void captureScreen(String dirName, String captureName, String msg){
		try {
			sleep(10);
			File scrFile = driver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(dirName + "//" + captureName));
			Assert.assertEquals(msg, true, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static InputStream runCmd(String cmd) throws Exception{
		 try {           
	            Runtime rt = Runtime.getRuntime ();
	            Process proc = rt.exec (cmd);
	            proc.waitFor ();
	            String line = null;
	 
	            InputStream stderr = proc.getErrorStream ();
	            InputStreamReader esr = new InputStreamReader (stderr);
	            BufferedReader ebr = new BufferedReader (esr);
	            
	            int exitVal = proc.waitFor ();
	            if(exitVal != 0){
	            	System.out.println("<error>");
	            	while ( (line = ebr.readLine ()) != null)
	            		System.out.println(line);
	            	System.out.println("</error>");
	            }
	    		InputStream stdout = proc.getInputStream ();
	            if(stdout != null)
	            	return stdout;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
         	return null;
	}

	/****************************************************************************
	*	Assert tools
	*	private boolean assertTextByXpath(String xpath, String text)
	*****************************************************************************/
	// Judge if text could be found in such xpath 
	private boolean assertTextByXpath(String xpath, String text) {
		loadPage(By.xpath(xpath));
		List<WebElement> wel = driver.findElementsByXPath(xpath);
		Pattern p = Pattern.compile(text);
		for (WebElement w : wel) {
			String eText = "";
			if(null != w)
				eText = w.getText();
			if (!eText.isEmpty() && p.matcher(eText).matches()
					&& w.isEnabled()
					&& w.isDisplayed()
					) {
				return true;
			}
		}
		return false;
	}
	
	
	/****************************************************************************
	*	Get element tools
	*	private WebElement getElementByXpath(String xpath)
	*	private WebElement getElementByType(String type, String className, String Name)
	*****************************************************************************/
	// Get element found via its xpath 
	private WebElement getElementByXpath(String xpath) {
//		loadPage(By.xpath(xpath));
		List<WebElement> wel = driver.findElementsByXPath(xpath);
		for (WebElement w : wel) {
			if (null != w && w.isEnabled()
					&& w.isDisplayed()) {
				return w;
			}
		}
		String timeStamp = String.valueOf(System.currentTimeMillis());
		captureScreen(savedDir, timeStamp +".png", 
				String.format("Xpath: %s is not found.\n"
						+ "Snapshot is: %s", xpath, timeStamp));
		return null;
	}
	
	private WebElement getElementByType(String type, String className, String Name) {
		loadPage(By.className(className));
		List<WebElement> wel = driver.findElementsByClassName(className);
		for (WebElement w : wel) {
			String eText= "";
			if(null != w){
				if(type.equals("tagName"))
					eText = w.getTagName();
				else if(type.equals("text"))
					eText = w.getText();
			}
				
			if (!eText.isEmpty() && eText.equals(Name) && w.isEnabled()) {
				return w;
			}
		}
		String timeStamp = String.valueOf(System.currentTimeMillis());
		captureScreen(savedDir, timeStamp +".png", 
				String.format("Web element:  %s is not found.\n"
						+ "Snapshot is: %s", Name, timeStamp));
		return null;
	}
	

	
	/****************************************************************************
	*	Page Load tools
	*	private void loadPageByName(final By by)
	*****************************************************************************/
	// Wait for loading page till given By class
	// by could be By.name(name) | By.id(id) | By.xpath(xpath)
	private void loadPage(final By by) {
		// TODO Auto-generated method stub
		(new WebDriverWait(driver, this.timeout))
		.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(by);
			}
		});
	}

	
	
	/****************************************************************************
	*	String tools
	*	public int getLeftCourseNumber()
	*****************************************************************************/
	public int getLeftCourseNumber(){
		sleep(3.5);
		loadPage(By.xpath("//td[@id='Personal']"));
		WebElement we = getElementByXpath("//td[@id='Personal']");
		String text = we.getText();
		int len = text.length();
		String mText = text.substring(text.indexOf("(")+1, len-1);
		return Integer.parseInt(mText);
	}

	
	/****************************************************************************
	*	Process control tools - setUp & tearDdown
	*	public void logIn(String url)
	* 	public void logIn(String url, boolean debug)
	* 	public void logOut()
	* 	public void closeDriver()
	*****************************************************************************/
	// Log in with userId and password
	public void logIn(String url) throws Exception {
		// And now use this to visit Skillport
		driver.get(url);
		// Wait for page load
		// Verify with Log in button
		loadPage(By.id(logInId));
		log("Page is loaded.");
		
		
		driver.findElementByName("name").sendKeys(userId);
		driver.findElementByName("password").sendKeys(password);

		getElementByXpath("//div[span='Log In']").click();
		// Verify with Action button
		loadPage(By.id(actionId));
		Assert.assertNotNull(driver.findElementById("actionCommonButton"));

		log("Log in.");
	}

	// Log in without userId and password (Debug mode)
	public void logIn(String url, boolean debug) throws Exception {
		// And now use this to visit Skillport
		driver.get(url);
		// Wait for page load
		loadPage(By.id(logInId));
		log("Page is opened in debug mode.");
		if (debug != true) {
			driver.findElementByName("name").sendKeys(userId);
			driver.findElementByName("password").sendKeys(password);
			getElementByXpath("//div[span='Log In']").click();
		}
		// Wait for page load
		loadPage(By.id(actionId));
		log("Log in.");
	}

	// Log out from main view
	public void logOut() throws Exception {
		// Click Search at the bottom
		log("Before log out.");
		loadPage(By.id(commonIconId));
		sleep(3.5);
		// Click Action button
		getElementByType("text", "x-button-label", "Actions").click();
		sleep(2);
		// Click Logout button
		getElementByXpath("//div[span='Logout']").click();
		// Confirm Logout
		getElementByXpath("//div[span='Yes']").click();

		final String okBtnXpath = "//div[span='OK']";
		loadPage(By.xpath(okBtnXpath));
		// Confirm Status
		getElementByXpath(okBtnXpath).click();

		loadPage(By.id(searchIconId));
		log("After log out.");
	}
	
	// driver.quit();
	public void closeDriver(){
		driver.close();
	}
	
	/****************************************************************************
	*	Process control tools- Assistant functions
	*	public void backToSearchView()
	*	public void backToMyPlanView()
	*	public void backToMyProgress()
	*	public void enterSearchView()
	*	public void enterMyPlanView()
	*	public void enterMyProgress()
	*****************************************************************************/
	// Switch back to Search view
	public void backToSearchView() {
		// Click Search to get back search view
		getElementByType("text", "x-button-label", "Search").click();
		loadPage(By.id(searchIconId));
		log("Back to search view");
	}

	// Switch back to MyPlan view
	public void backToMyPlanView() {
		// Click My Plan to get back to My Plan view
		// Starts with "My", which is "My Plan" for full name
		getElementByType("text", "x-button-label", "My Plan").click();
		loadPage(By.id(commonIconId));
		log("Back to My Plan view");
	}

	// Switch back to MyProgress view
	public void backToMyProgress() {
		// Starts with "My", which is "My Progress" for full name
		getElementByType("text", "x-button-label", "My").click();
		loadPage(By.id(myProgressIconId));
		log("Back to My Progress view");
	}

	// Enter Search
	public void enterSearch() {
		getElementByType("text", "x-button-label", "Search").click();
		loadPage(By.id(searchIconId));
		log("Enter search view");
	}

	// Enter MyPlan
	public void enterMyPlan() {
		// Notice here: sometimes, one click could not open "My Plan" view
		for(int i = 0; i < 2; i++){
			getElementByType("text", "x-button-label", "MyPlan").click();
		}
		loadPage(By.id(myPlanIconId));
		log("Enter My Plan view");
	}

	// Enter MyProgress
	public void enterMyProgress() {
		getElementByType("text", "x-button-label", "MyProgress").click();
		loadPage(By.id(myProgressIconId));
		log("Enter My Progress view");
	}
	
	
	/****************************************************************************
	*	Process control tools- Core functions
	*	public void searchCourse(final String course)
	*	public void enterCourseContent(String course)
	*	public void addCourseToMyPlan()
	*	public void enterCourseListOfMyPlan()
	*	public void enterLatestCourseOfMyPlan()
	*	public void confirmCourseOfMyPlan(String course)
	*	public void enterAndQuitEditPage()
	*	public void removeCurrentCourse()
	*****************************************************************************/
	// Search given course
	public void searchCourse(final String course) {
		log("Start to search course: " + course);
		// Test search result
		WebElement searchBox = getElementByType("tagName", "x-input-search", "INPUT");

		
		searchBox.sendKeys(course);
		log("---------------------------------------------------------course:" + course);
		// Press Enter to search
		searchBox.sendKeys(Keys.ENTER);

		// Verify with Search result list
		final String id = "searchItems";
		loadPage(By.id(id));
		sleep(3.5);
		getElementByXpath("//td[@id='headerTextValue']");
		
		// Clear value in searchbox
		searchBox = getElementByType("tagName", "x-input-search", "INPUT");
		sleep(4);

		searchBox.sendKeys("");
		for(int i = 0; i < course.length(); i++){
			try {
				runCmd("adb shell input keyevent 67");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		log("Clear done.");
		
		log("Clear search box.");
	}

	// Enter the first group's first course. 
	// Need to be modified to be more adaptive
	public void enterCourseContent(String course) {
		log("Enter course content.");
		// Click first group
		getElementByXpath(
				"//div[contains(@id, 'results')]//td[@align='left'][1]")
				.click();
		// Click first course from list
		sleep(2);
		getElementByXpath(
				"//div[contains(@id, '1 of 1') and @name='itemsDiv']").click();
		// Verify with Play button
		final String id = "assetSummaryPlayButton";
		loadPage(By.id(id));
		sleep(4);
		
		// Check content
		getElementByType("text", "x-button-label", "Search");
		logElement("Search button is found.");
		getElementByXpath("//img[contains(@class,'add_black')]");
		logElement("Add button is found.");
		getElementByXpath("//div[@class='x-list-item-body']/span[@class='summary_body']");
		logElement(course + " is found.");
	}

	// Click "Add" button to enter adding course page
	public void addCourseToMyPlan() {
		log("Start to add current course to My Plan.");
		// Click Add button
		sleep(5);
		getElementByXpath("//div[@class='x-layout-box-inner x-layout-box']" +
				"/div[@class='x-button x-button-plain x-iconalign-left']" +
				"/img[contains(@class,'add_black')]").click();
		final String name = "name";
		loadPage(By.name(name));
		sleep(3.5);
		getElementByXpath("//div[contains(@class, 'x-button-normal')][span='Save']").click();
		// Verify tips
		final String xpath = "//div[div='Added in My plan successfully.']";
		loadPage(By.xpath(xpath));
		sleep(2);
		driver.findElementByXPath("//div[span='OK'][1]").click();
		log("Course is added.");
	}

	// Enter course list from My Plan view via click "Personal (xx)" folder
	public void enterCourseListOfMyPlan() {
		loadPage(By.id("Personal"));
		// Enter Personal
		log("Before get into personal");
		// Need several times to load page
		sleep(4.5);
		try{
			WebElement personal = getElementByXpath("//div[@class='x-list-group']" +
					"/div[@id='preview_item_<center>folder view</center>']" +
					"/div[@class='x-list-item '][1]");
			if(null != personal)
				personal.click();
		}catch(StaleElementReferenceException e){
			enterSearch();
			enterMyPlan();
			getElementByXpath("//div[@class='x-list-group']" +
					"/div[@id='preview_item_<center>folder view</center>']" +
					"/div[@class='x-list-item '][2]").click();
		}
		log("After get into personal");
	}

	// Enter latest/last course from course list
	public void enterLatestCourseOfMyPlan(int courseNumber) {
		log("Before enter last added course.");
		loadPage(By.xpath("//div[@id='PersonalmyPlanFolderPreviewBack'][span='My Plan']"));
		sleep(5);
		for(int i = 0; i < courseNumber/listNumberOfOnePage; i++){
			driver.findElementByXPath("//td[@class='buttonTd']" +
					"//div[@id='Personal'][span='More']").click();
			loadPage(By.xpath("//div[@id='PersonalmyPlanFolderPreviewBack'][span='My Plan']"));
			sleep(5);
		}
		// Select last one, also the latest one
		String lastCourseXpath = "//div[contains(@class,'x-list-group')]"
				+ "/div[contains(@id, 'folder_item_<center>personal')]"
				+ "/div[@class='x-list-item '][last()]";
		loadPage(By.xpath(lastCourseXpath));
		sleep(3);
		getElementByXpath(lastCourseXpath).click();
		final String xpath = "//div[span='ID:'][1]";
		loadPage(By.xpath(xpath));
		log("Now last add course is shown.");
		log("After enter last added course.");
	}

	// Enter detail of course and confirm course name 
	public void confirmCourseOfMyPlan(String course) {
		log("Start to check course name");
		sleep(2);
		
		// check Content
		getElementByType("text", "x-button-label", "My Plan");
		logElement("My Plan Button is found.");
		getElementByXpath("//div[@id='myplanSummaryToolbar']//div[@id='myplanRemoveButton']");
		logElement("Remove button is found");
		getElementByXpath("//div[@id='myplanSummaryToolbar']//div[@id='myplanEditButton']");
		logElement("Edit button is found");
		
		if(assertTextByXpath("//div[contains(@class,'x-list-item')]" +
				"/div[@class='x-list-item-body']" +
				"/span[contains(@class,'bodytext')][2]", course)){
			logElement("Yep! The expected course is " + course);
		}else{
			log("Sorry! We don't find " + course);
		}
	}

	// Enter and quit edit page (Not used)
	public void enterAndQuitEditPage() {
		driver.findElementById("myplanEditButton").click();
		log("Enter edit page");
		loadPage(By.xpath("//div[span='Cancel']"));
		getElementByType("text", "x-button-label", "Cancel").click();
		log("Quit edit page");
	}

	// Remove current course and back to MyPlan View
	public void removeCurrentCourse(int courseNum) {
		sleep(3);
		String removeBtn = "//div[@id='myplanSummaryToolbar']//div[@id='myplanRemoveButton']";
		loadPage(By.xpath(removeBtn));
		log("Removing current course..");
		getElementByXpath(removeBtn).click();
		loadPage(By.xpath("//div[span='Yes']"));
		sleep(3);
		getElementByType("text", "x-button-label", "Yes").click();
		loadPage(By.id("Personal"));
		getElementByXpath(String.format("//tr[td='     Personal (%d)']", courseNum-1));
		log("Course is removed.");
			
	}
	
	public void addCourseAndVerifyCourse(String course){
		enterSearch();
		searchCourse(course);
		enterCourseContent(course);
		addCourseToMyPlan();
		backToSearchView();
		enterMyPlan();
		int courseNumber = getLeftCourseNumber();
		enterCourseListOfMyPlan();
		enterLatestCourseOfMyPlan(courseNumber);
		confirmCourseOfMyPlan(course);
		backToMyPlanView();
	}

}
