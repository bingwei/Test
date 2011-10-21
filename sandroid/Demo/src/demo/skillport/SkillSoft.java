package demo.skillport;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.AndroidDriver;

public class SkillSoft {
	private ToolUtils toolUtils;
	private final boolean IS_DEBUG = true;
	
	private final String SEARCH = "Search";
	private final String MYFEED = "MyFeed";
	private final String MYPLAN = "MyPlan";
	private final String MYPROGRESS = "MyProgress";
	private final String MY_FEED = "My Feed";
	private final String MY_PLAN = "My Plan";
	private final String MY_PROGRESS = "My Progress";
	private final String ACTIONS = "Actions";
	
	
	private final String TEXT = "text";
	private final String TAG_NAME = "tagName";
	private final String ACTION_BUTTON_ID = "actionCommonButton";
	// div id of top title in main views
	private final String COMMON_TITLE = "commonTitle";
	// div id of bottom buttons in main views
	private final String PARENT_TAB_BAR = "parentTabBar";
	// search bar tag name
	private final String NAME = "name";
	private final String PASSWORD = "password";
	private final int LIST_NUMBER_OF_ONE_PAGE = 20;
	
	// In Search View
	private final String SEARCH_RESULT_XPATH = "//div[contains(@id, results) and @name='itemsDiv']"; 
	private final String SEARCH_RESULT_FOLDER_LISTS_XPATH = "//div[@id='searchItems']//td";
	private final String FIRST_SEARCH_RESULT_FOLDER_XPATH = "//div[contains(@id, 'results')]//td[@align='left'][1]";
	private final String ONLY_ONE_COURSE_SEARCH_RESULT_FOLDER_XPATH = "//div[contains(@id, 'results')]//td[@align='left'][1]";
	private final String SEARCH_RESULT_COURSE_LIST_TITLE_XPATH = "//td[@name='headerText']";
	private final String SEARCH_BAR_XPATH = "//input[@name='searchfield']";
	private final String SEARCH_RESULT_SINGLE_ONLY_COURSE_XPATH = "//div[contains(@id,'of') and contains(@id, 'center id=')]/div[@class='x-list-item '][1]/div[@class='x-list-item-body']/table//td[4]";
				//"//div[contains(@id, '1 of 1') and @name='itemsDiv']";
	private final String SAVE_BUTTON_XPATH = "//div[contains(@class, 'x-button-normal')][span='Save']";
	private final String COURSE_ADDED_MESSAGE_XPATH = "//div[div='Added in My plan successfully.']";
	private final String COURSE_ADD_BUTTON_XPATH = "//img[contains(@class,'add_black')]";
	
	// In My Plan View
	
	// In Common View
	private final String COMMON_TITLE_XPATH = "//div[@id='%s'][div='%s']";
	private final String PARENT_TAB_BAR_XPATH = "//div[@id='%s']//div[span='%s']";
	private final String LOG_OUT_BUTTON_XPATH = "//div[span='Logout']";
	private final String YES_BUTTON_XPATH = "//div[span='Yes']";
	private final String OK_BUTTON_XPATH = "//div[span='OK'][1]";
	
	// In Log in View
	private final String LOGIN_TO_SKILLPORT_XPATH = "//div[@id='loginTitle'][div='Login to SkillPort']";
	private final String LOGIN_BUTTON_XPATH = "//div[span='Log In']";
	
	private final String BACK_TO_VIEW_BUTTON_XPATH = "//span[@class='x-button-label']";
	private final String PERSONAL_FOLDER_XPATH = "//div[@class='x-list-group']" +
											"/div[@id='preview_item_<center>folder view</center>']" +
											"/div[@class='x-list-item '][1]";
	private final String PERSONAL_TITLE_XPATH = "//table[@class='headerTable']//td[@name='folder_headerText']";
	private final String PERSONAL_COURSE_NUM_XPATH = "//td[@id='Personal']";
	private final String PERSONAL_COURSE_NUM_EMPTY_XPATH = "//tr[td='     Personal (%d)']";
	private final String MORE_BUTTON_XPATH = "//td[@class='buttonTd']" +
										"//div[@id='Personal'][span='More'][1]";
	private final String BOTTOM_COURSE_XPATH = "//div[contains(@class,'x-list-group')]"
			+ "/div[contains(@id, 'folder_item_<center>personal')]"
			+ "/div[@class='x-list-item '][last()]";
	private final String TEXT_ID_XPATH = "//div[span='ID:'][1]";
	private final String REMOVE_BUTTON_XPATH = "//div[@id='myplanSummaryToolbar']//div[@id='myplanRemoveButton']";
	
	private int courseNumInMyPlan = 0;
	
	public SkillSoft(AndroidDriver driver){
		toolUtils = new ToolUtils(driver);
	}

	/**
	 * Return WebElement via By method
	 * 
	 * @throws Exception 
	 * 
	 */
	public WebElement getElement(By by, String elementName){
		return toolUtils.getElement(by, elementName);
	}
	/**
	 * Return WebElement via By method and text content and method[getText() | getTagName()]
	 * 
	 * @throws Exception 
	 * 
	 */
	public WebElement getElementWithText(By by, String elementName, String type){
		return toolUtils.getElementWithName(by, elementName, type);
	}
	
	/**
	 * Close Web Driver
	 * 
	 * @throws Exception 
	 * 
	 */
	public void closeWebDriver() throws Exception{
		toolUtils.closeWebDriver();
	}
	
	/**
	 * Open web site
	 * 
	 * @param url web site url
	 *  
	 */
	public void openUrl(String url){
		toolUtils.openUrl(url);
	}
	
	/**
	 * Back to some view from its content
	 * 
	 * @param view_Name - SEARCH | MY FEED | MY PLAN | MY PROGRESS
	 * 
	 */
	public void backToView(String viewName) {
		toolUtils.loadPageWithName(By.xpath(BACK_TO_VIEW_BUTTON_XPATH), viewName, TEXT).click();
		toolUtils.loadPage(By.id(ACTION_BUTTON_ID), "Actions button");
		toolUtils.log(String.format("Back to %s view", viewName));
	}
	

	/**
	 * Switch to some view from any other main view
	 * 
	 * @param viewName - SEARCH | MYFEED | MYPLAN | MYPROGRESS
	 * @param view_Name - SEARCH | MY FEED | MY PLAN | MY PROGRESS
	 * 
	 */
	public void switchToView(String viewName, String view_Name) {
		String bottomButton = String.format(PARENT_TAB_BAR_XPATH, PARENT_TAB_BAR, viewName);
		toolUtils.getElement(By.xpath(bottomButton), String.format("Bottom %s button", viewName)).click();
		String viewTitle = String.format(COMMON_TITLE_XPATH, COMMON_TITLE, view_Name);
		toolUtils.loadPage(By.xpath(viewTitle), "Common title");
		toolUtils.log(String.format("Switch to %s view", view_Name));
	}

	/**
	 * Log in web site with proper username and password
	 * 
	 * @param url web site address
	 * @param username
	 * @param password
	 * 
	 */
	public void logIn(String url, String username, String password) throws Exception {
		toolUtils.openUrl(url);
		toolUtils.loadPage(By.xpath(LOGIN_TO_SKILLPORT_XPATH), "SkillPort log in page");
		
		toolUtils.getElement(By.name(NAME), "Name").sendKeys(username);
		toolUtils.getElement(By.name(PASSWORD), "Password").sendKeys(password);
		toolUtils.getElement(By.xpath(LOGIN_BUTTON_XPATH), "Log in button").click();
		toolUtils.loadPage(By.id(ACTION_BUTTON_ID), "Actions button");

		toolUtils.log("Log in.");
	}
	
	/**
	 * Log out from any main view
	 * 
	 * @param url web site address
	 * @param username
	 * @param password
	 * 
	 */
	public void logOut() throws Exception {
		// Click Search at the bottom
		toolUtils.log("Before log out.");
		// Click Action button
		toolUtils.loadPage(By.id(ACTION_BUTTON_ID), "Actions button").click();
		// Click Logout button
		toolUtils.loadPage(By.xpath(LOG_OUT_BUTTON_XPATH), "Log out button").click();
		// Confirm Logout
		toolUtils.loadPage(By.xpath(YES_BUTTON_XPATH), "Yes button").click();
		// Confirm Status
		toolUtils.loadPage(By.xpath(OK_BUTTON_XPATH), "OK button").click();

		toolUtils.loadPage(By.xpath(LOGIN_TO_SKILLPORT_XPATH), "SkillPort log in page");
		toolUtils.log("After log out.");
	}
	
	/**
	 * Search course
	 * 
	 * @param courseName 
	 * 
	 */
	public void searchCourse(String courseName) {
		toolUtils.log("Start to search course: " + courseName);
		// Test search result
		WebElement searchBox = toolUtils.loadPage(By.xpath(SEARCH_BAR_XPATH), "Search bar");
		searchBox.sendKeys(courseName);
		// Press Enter to search
		searchBox.sendKeys(Keys.ENTER);
		ToolUtils.sleep(4);

		// Verify with Search result list
		toolUtils.loadPage(By.xpath(SEARCH_RESULT_XPATH),"Search result");
		toolUtils.log("Search result is shown");
	}
	
	/**
	 * Clear remembered key word in search bar. This is used right after get search result.
	 * 
	 * @param courseName 
	 * 
	 */
	public void clearSearchKeyWord() {
		toolUtils.log("Start to clear key word.");
		WebElement searchBox = toolUtils.loadPage(By.xpath(SEARCH_BAR_XPATH), "Search bar");
	
		searchBox.clear();
		toolUtils.log("Clear search box done.");
	}
	
	/**
	 * Enter specific searched folder
	 * 
	 * @param folderName
	 * 
	 */
	public void enterSpecificSearchedFolder(String folderName) {
		toolUtils.log(String.format("Prepare to enter course %s.", folderName));
		
		WebElement searchFolder = toolUtils.loadPageWithName(By.xpath(SEARCH_RESULT_FOLDER_LISTS_XPATH), folderName, TEXT);
		searchFolder.click();
		toolUtils.loadPage(By.xpath(SEARCH_RESULT_COURSE_LIST_TITLE_XPATH), "SearchCourseList");
		toolUtils.log(String.format("Enter course folder: %s.", folderName));
	}
	
	/**
	 * Enter searched folder contains only one course
	 * 
	 */
	public void enterSearchedFolderContainOnlyOneCourse() {
		toolUtils.log("Prepare to enter course containing only one course.");
		
		WebElement onlyOneCourseFolder = toolUtils.getElementWithNameWithoutAssert(By.xpath(ONLY_ONE_COURSE_SEARCH_RESULT_FOLDER_XPATH), "1", TEXT);
		if(null != onlyOneCourseFolder){
			onlyOneCourseFolder.click();
			toolUtils.loadPage(By.xpath(SEARCH_RESULT_COURSE_LIST_TITLE_XPATH), "SearchCourseList");
			toolUtils.log("Enter course containing only one course.");
		}else{
			enterFirstSearchedFolder();
		}
	}
	
	/**
	 * Enter first searched folder
	 * 
	 */
	public void enterFirstSearchedFolder() {
		toolUtils.log("Prepare to enter first course.");
		
		WebElement searchFolder = toolUtils.loadPage(By.xpath(FIRST_SEARCH_RESULT_FOLDER_XPATH), "FirstFolder");
		searchFolder.click();
		toolUtils.loadPage(By.xpath(SEARCH_RESULT_COURSE_LIST_TITLE_XPATH), "SearchCourseList");
		toolUtils.log("Enter first course folder.");
	}
	
	/**
	 * Enter searched single only course from course list
	 * 
	 */
	public void enterFirstSearchedCource() {
		toolUtils.logMethodStart(new Throwable().getStackTrace()[0], IS_DEBUG);
		// Load page
		toolUtils.loadPage(By.xpath(SEARCH_RESULT_COURSE_LIST_TITLE_XPATH), "SearchCourseList");
		// Enter the single only course
		toolUtils.getElement(By.xpath(SEARCH_RESULT_SINGLE_ONLY_COURSE_XPATH), "Single only course").click();
		// Load page
		ToolUtils.sleep(3);
		toolUtils.loadPage(By.xpath(COURSE_ADD_BUTTON_XPATH), "Add button");
		toolUtils.log("Enter single only course");
		toolUtils.logMethodEnd(new Throwable().getStackTrace()[0], IS_DEBUG);
	}
	
	/**
	 * Add current course
	 * 
	 */
	public void addCurrentSource() {
		// Click add button
		toolUtils.loadPage(By.xpath(COURSE_ADD_BUTTON_XPATH), "Add button").click();
		// Click save button
		toolUtils.loadPage(By.xpath(SAVE_BUTTON_XPATH), "Save button").click();
		// Check added message
		toolUtils.loadPage(By.xpath(COURSE_ADDED_MESSAGE_XPATH), "Message about added successfully");
		toolUtils.loadPage(By.xpath(OK_BUTTON_XPATH), "OK button").click();
		toolUtils.loadPageWithName(By.xpath(BACK_TO_VIEW_BUTTON_XPATH), SEARCH, TEXT);
		
		toolUtils.log("Course is added.");
	}
	
	/**
	 * Enter course list of My Plan from My Plan view
	 * 
	 */
	public void enterCourseListOfMyPlan() {
		String myPlanTitle = String.format(COMMON_TITLE_XPATH, COMMON_TITLE, MY_PLAN);
		toolUtils.loadPage(By.xpath(myPlanTitle), "Common title");
		courseNumInMyPlan = getCourseNumberOfMyPlan();
		// Click personal button
		toolUtils.loadPage(By.xpath(PERSONAL_FOLDER_XPATH), "Personal folder").click();
		toolUtils.log("Get into personal folder.");
	}
	
	/**
	 * Get the number of course in personal folder of My Plan. 
	 * 
	 */
	public int getCourseNumberOfMyPlan(){
		WebElement personalCourse = toolUtils.loadPage(By.xpath(PERSONAL_COURSE_NUM_XPATH), "Personal course");
		String text = personalCourse.getText();
		String mText = text.substring(text.indexOf("(")+1, text.indexOf(")"));
		return Integer.parseInt(mText);
	}
	
	/**
	 * Enter bottom course of in the list of personal folder. If it has many pages, 
	 * then enter the bottom course of last page.
	 * 
	 */
	public void enterBottomCourseOfMyPlan() {
		toolUtils.loadPageWithName(By.xpath(PERSONAL_TITLE_XPATH), "Personal", TEXT);
		//Switch to last page and enter the bottom course
		for(int i = 0; i < courseNumInMyPlan/LIST_NUMBER_OF_ONE_PAGE; i++){
			toolUtils.loadPage(By.xpath(MORE_BUTTON_XPATH), "More button").click();
			ToolUtils.sleep(20);
		}
		toolUtils.loadPage(By.xpath(BOTTOM_COURSE_XPATH),"Bottom course").click();
		toolUtils.loadPage(By.xpath(TEXT_ID_XPATH), "Text ID");
		toolUtils.log("Get into bottom course of last page.");
	}
	
	/**
	 * Remove current course and back to My Plan main view.
	 * 
	 */
	public void removeCurrentCourse() {
		WebElement removeBtn = toolUtils.loadPage(By.xpath(REMOVE_BUTTON_XPATH), "Remove button");
		toolUtils.log("Removing current course..");
		removeBtn.click();
		toolUtils.loadPage(By.xpath(YES_BUTTON_XPATH), "Yes Button").click();
		toolUtils.loadPage(By.xpath(String.format(PERSONAL_COURSE_NUM_EMPTY_XPATH, --courseNumInMyPlan)), "After remove");
		toolUtils.log("Course is removed.");
			
	}
	
		
}
