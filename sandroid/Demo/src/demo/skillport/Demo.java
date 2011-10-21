package demo.skillport;

import junit.framework.TestCase;

import org.openqa.selenium.android.AndroidDriver;

public class Demo extends TestCase{
	private AndroidDriver driver;
	private Tools tools;
	private final String URL 
		= "http://mobile3vdz73a.skillsoft.com/skillportfe/mobilelogin.html";
	
	private final String startPort = "adb forward tcp:8080 tcp:8080";
	private final String startWebDriver = "adb shell am start -n org.openqa.selenium.android.app/.MainActivity";
	private final String closeWebDriver = "adb shell input keyevent 4";
	
	private final String course = "mobile_xx_a13_bs_enus";
	private final String[] courseList = {
			"COMM0101",
			"113095_eng"
			};
	


	public void setUp() throws Exception {
		Tools.runCmd(startPort);
		Tools.runCmd(startWebDriver);
		Tools.sleep(3);
		driver = new AndroidDriver();
		tools = new Tools(driver);
		tools.logIn(URL);
		tools.log("Setting up done.");
		tools.log("========================================================");
	}
	

	public void testCaseAddCourseAndVerifyCourse() throws Exception {
		tools.searchCourse(course);
		tools.enterCourseContent(course);
		tools.addCourseToMyPlan();
		tools.backToSearchView();
		tools.enterMyPlan();
		int courseNumber = tools.getLeftCourseNumber();
		tools.enterCourseListOfMyPlan();
		tools.enterLatestCourseOfMyPlan(courseNumber);
		tools.confirmCourseOfMyPlan(course);
		tools.backToMyPlanView();
	}


	public void testCaseRepeatAddAndVerifyCourses() throws Exception {
		for(int i = 0; i < courseList.length; i++){
			String mCourse = courseList[i];
			tools.addCourseAndVerifyCourse(mCourse);
		}
	}


	public void testCaseRemoveCourses() throws Exception {
		tools.enterMyPlan();
		int courseNumber = tools.getLeftCourseNumber();
		while(courseNumber>0){
			tools.enterCourseListOfMyPlan();
			tools.enterLatestCourseOfMyPlan(courseNumber);
			tools.removeCurrentCourse(courseNumber);
			courseNumber--;
		}
	}
	


	public void tearDown() throws Exception {
		tools.closeDriver();
		Tools.runCmd(closeWebDriver);
		Tools.sleep(3);
		tools.log("TearDown.");
	}
		
}
