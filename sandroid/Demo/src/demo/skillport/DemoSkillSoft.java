package demo.skillport;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.android.AndroidDriver;

public class DemoSkillSoft extends TestCase{
	private AndroidDriver driver;
	private SkillSoft skillSoft;
	private final String URL 
		= "http://mobile3vdz73a.skillsoft.com/skillportfe/mobilelogin.html";
//	private final String USER_NAME = "demo";
	private final String USER_NAME = "auto";
	private final String PASSWORD = "123";
	private final String SEARCH_FOLDER = "Courses";
	
	private final String SEARCH = "Search";
	private final String MYFEED = "MyFeed";
	private final String MYPLAN = "MyPlan";
	private final String MYPROGRESS = "MyProgress";
	private final String MY_FEED = "My Feed";
	private final String MY_PLAN = "My Plan";
	private final String MY_PROGRESS = "My Progress";
	private final String ACTIONS = "Actions";
	
	private final String course = "mobile_xx_a13_bs_enus";
	private final String[] courseList = {
			"COMM0101",
			"113095_eng"
			};
	private final String[] courseListFull = {
			"COMM0101",
			"113095_eng",
			"B14Convertede3_eng",
			"TP1Z0007_eng",
			"FE0001_eng",
			"V210x7_eng",
			"Gettys_enUS",
			"228315_eng",
			"mnt1z0040",
			"_lot_nxt_icpdt_01",
			"_scorm12_xes_motorcy_0001",
			"_scorm12_san_byg445_0001",
			"_s24_mft_setscore",
			"ZQA0101",//DONE Need modify to enter different folder
			"_aicc_gary_19sct18sr1",
			"CUST0100",
			"CUST005A",// Done. One folder has two courses
			"JAV03SE",
			//"_pc_eg_640811", // not exist
			"GOTR0104",
			"KN00074006_ng",
			"en_US_48603_ng",
			"cust_01_a01_bs_enus",
			"DD31_Build688_EN_8x6_SPCSF ",
			"kc_1ca_a01_cj_enus",
			"Comm_01_a01_bsc_enus",
			"kc_vb_a02_kc_enus",
			"kc_vbcust_a02_kc_enus",
			//"4925", // not course
			"COMM0606_jco0606b",
			"COMM0606_sbCOMM0606004002",
			//"19147", // not exist
			"_pc_ls_ccds001",
			"_sun_ws_2020_s10",
			"_pc_bi_lsbi001",
			"_pc_ch_pmch001",
			"Asst10x7_eng",
			"sla_01_a00_sla_enus",
			"sla_01_a01_sla_enus",
			//"LLCS0043EN03P", // not exist
			"cust_01_a01_bs_enus_T18",
			//"10482-427627901"// not exist
	};
	


	public void setUp() throws Exception {
		ToolUtils.checkDevice();
		ToolUtils.startPortAndWebDriver();
		driver = new AndroidDriver();
		skillSoft = new SkillSoft(driver);
		skillSoft.logIn(URL, USER_NAME, PASSWORD);
	}
	

	public void testOneCourse(){
		skillSoft.searchCourse(course);
		skillSoft.clearSearchKeyWord();
		skillSoft.enterFirstSearchedFolder();
		skillSoft.enterFirstSearchedCource();
		assertNotNull(skillSoft.getElement(By.xpath("//img[contains(@class,'add_black')]"), "Add button"));
		skillSoft.addCurrentSource();
		skillSoft.backToView("Search");
	}
	
	public void testSearchCourse(){
		for(int i = 0; i < courseListFull.length; i++){
			String mCourse = courseListFull[i];
			skillSoft.searchCourse(mCourse);
			skillSoft.clearSearchKeyWord();
//			skillSoft.enterFirstSearchedFolder();
			skillSoft.enterSearchedFolderContainOnlyOneCourse();
			skillSoft.enterFirstSearchedCource();
			assertNotNull(skillSoft.getElement(By.xpath("//img[contains(@class,'add_black')]"), "Add button"));
			skillSoft.addCurrentSource();
			skillSoft.backToView("Search");
		}
	}
	
	public void testLastAddedCourse(){
		skillSoft.switchToView(MYPLAN, MY_PLAN);
		skillSoft.enterCourseListOfMyPlan();
		skillSoft.enterBottomCourseOfMyPlan();
		assertNotNull(skillSoft.getElement(By.xpath("//div[@id='myplanSummaryToolbar']//div[@id='myplanRemoveButton']"), "Remove button"));
		skillSoft.backToView(MY_PLAN);
	}

	public void testLogOut() throws Exception {
		skillSoft.logOut();
	}

	public void tearDown() throws Exception {
		skillSoft.closeWebDriver();
	}
		
}
