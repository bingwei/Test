package demo.skillport;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ToolUtils {
	private AndroidDriver driver;
	
	private static String deviceID;
//	private static String deviceID="04037A2A1000700D";
	private final static int START_SERVER_TIME_WAIT = 3;
	private final int CAPTURE_TIME_WAIT = 7;
	private final int EXPLICIT_TIME_OUT = 60;
	private final int IMPLICIT_TIME_OUT = 20;
	private final static String KILL_SERVER = "adb kill-server";
	private final static String START_SERVER = "adb start-server";
	private final static String GET_DEVICES = "adb devices";
	private final static String START_PORT = "adb -s %s forward tcp:8080 tcp:8080";
	private final static String START_WEB_DRIVER = "adb -s %s shell am start -n org.openqa.selenium.android.app/.MainActivity";
	private final static String CLOSE_WEB_DRIVER = "adb shell input keyevent 4";
	
	private String captureDir = System.getProperty("user.dir");
	
	public ToolUtils(AndroidDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Close driver
	 * 
	 */
	public void closeDriver(){
		driver.close();
	}

	/**
	 * Set implicit waiting time and open web site
	 * 
	 * @param url web site url
	 *  
	 */
	public void openUrl(String url){
		driver.manage().timeouts().implicitlyWait(IMPLICIT_TIME_OUT, TimeUnit.SECONDS);
		driver.get(url);
	}
	
	/**
	 * Basic log tool instead of System.err.println.
	 * 
	 * @param str the error log to be shown.
	 *  
	 */
	public static void logErr(Object str) {
		System.err.println("err ] " + str.toString());
	}
	
	/**
	 * Basic log tool instead of System.out.println.
	 * 
	 * @param str the log to be shown.
	 *  
	 */
	public void log(Object str) {
		System.out.println("-> " + str.toString());
	}
	
	/**
	 * Check element whether exists.
	 * 
	 * @param element element to search for.
	 * @param isFound element is found.
	 *  
	 */
	public void logElementCheck(Object element, boolean isFound) {
		if(isFound)
			System.out.println(String.format("---><] %s is found.", element.toString()));
		else
			System.out.println(String.format("---><] %s is not found.", element.toString()));
	}
	
	/**
	 * Mark method starts.
	 * 
	 * @param method method name.
	 * @param isDebug only works while isDebug is true.
	 *  
	 */
	public void logMethodStart(Object method, boolean isDebug) {
		if(isDebug)
			System.out.println(String.format("-] Start method: %s", method.toString()));
	}
	
	/**
	 * Mark method ends.
	 * 
	 * @param method method name.
	 * @param isDebug only works while isDebug is true.
	 *  
	 */
	public void logMethodEnd(Object method, boolean isDebug) {
		if(isDebug)
			System.out.println(String.format("-] End method: %s", method.toString()));
	}
	
	/**
	 * Wait.
	 * 
	 * @param seconds waiting time.
	 *  
	 */
	public static void sleep(double seconds) {
		try {
			Thread.sleep((long) (seconds * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Run external command. Error message is dealt and output between <error> and </error>
	 * 
	 * @param cmd external command.
	 * @return InputStream return if external command has output
	 * @throws
	 *  
	 */
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
	
	/**
	 * Capture current screen and save to given directory
	 * 
	 * @param dirName directory for captured photo.
	 * @param photoName photo name
	 *  
	 */
	public void captureScreen(String dirName, String photoName){
		try {
			sleep(CAPTURE_TIME_WAIT);
			File scrFile = driver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(dirName + "//" + photoName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Capture current screen and save to given directory
	 * 
	 * @param dirName directory for captured photo.
	 * @param photoName photo name
	 * @param errMsg Error message to be printed
	 *  
	 */
	public void captureScreen(String dirName, String photoName, String errMsg){
		try {
			sleep(CAPTURE_TIME_WAIT);
			File scrFile = driver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(dirName + "//" + photoName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		logErr(String.format("%s. Snapshot is: %s", errMsg, photoName));
		Assert.assertNotNull(null);
	}
	
	/**
	 * Set capture directory
	 * 
	 * @param dirName directory for captured photo.
	 *  
	 */
	public void setCaptureDir(String dirName){
		captureDir = dirName;
	}
	
	/**
	 * Get capture directory
	 * 
	 * @return return capture directory
	 *  
	 */
	public String getCaptureDir(){
		return captureDir;
	}
	
	/**
	 * Wait for loading page till given By class
	 * 
	 * @param by by could be By.name(name) | By.id(id) | By.xpath(xpath)
	 * @param elementName Such as xx button, xx icon, etc.
	 * 
	 * @return return WebElement found
	 *  
	 */
	public WebElement loadPage(final By by, String elementName) {
		// TODO Auto-generated method stub
		(new WebDriverWait(driver, EXPLICIT_TIME_OUT))
		.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(by);
			}
		});
		return getElement(by, elementName);
	}
	/**
	 * Wait for loading page till given By class
	 * 
	 * @param by by could be By.name(name) | By.id(id) | By.xpath(xpath)
	 * @param elementName Such as xx button, xx icon, etc.
	 * @type TEXT | TAG_NAME
	 * 
	 * @return return WebElement found
	 *  
	 */
	public WebElement loadPageWithName(final By by, String elementName, String type) {
		// TODO Auto-generated method stub
		(new WebDriverWait(driver, EXPLICIT_TIME_OUT))
		.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(by);
			}
		});
		return getElementWithName(by, elementName, type);
	}
	
	/**
	 * Check whether text could be found with given element.getText()
	 * It element is not found, Assert occurs
	 * 
	 * @param by by could be By.name(name) | By.id(id) | By.xpath(xpath)
	 * @param name text from element.getText() | element.getTagName()...
	 * @return WebElement 
	 *  
	 */
	public WebElement getElementWithName(By by, String name, String type) {
		List<WebElement> wel = driver.findElements(by);
		
		String method="";
		if(type.equals("tagName")){
			method = "getTagName()";
		}else if(type.equals("text")){
			method = "getText()";
		}
		
		for (WebElement w : wel) {
			String eText="";
			if(null != w){
				if(type.equals("tagName")){
					eText = w.getTagName();
				}
				else if(type.equals("text")){
					eText = w.getText();
				}
			}
			if (!eText.isEmpty() && -1 != eText.indexOf(name) && w.isEnabled()) 
			{
				logElementCheck(name, true);
				return w;
			}
		}
		logElementCheck(name, false);
		String timeStamp = String.valueOf(System.currentTimeMillis());
		captureScreen(captureDir, 
				String.format("%s_%s.png", timeStamp, name),
				String.format("(%s.%s == %s) is not found", by.toString(), method, name));
		return null;
	}
	/**
	 * Check whether text could be found with given element.getText()
	 * 
	 * @param by by could be By.name(name) | By.id(id) | By.xpath(xpath)
	 * @param name text from element.getText() | element.getTagName()...
	 * @return WebElement 
	 *  
	 */
	public WebElement getElementWithNameWithoutAssert(By by, String name, String type) {
		List<WebElement> wel = driver.findElements(by);
		
		String method="";
		if(type.equals("tagName")){
			method = "getTagName()";
		}else if(type.equals("text")){
			method = "getText()";
		}
		
		for (WebElement w : wel) {
			String eText="";
			if(null != w){
				if(type.equals("tagName")){
					eText = w.getTagName();
				}
				else if(type.equals("text")){
					eText = w.getText();
				}
			}
			if (!eText.isEmpty() && -1 != eText.indexOf(name) && w.isEnabled()) 
			{
				logElementCheck(name, true);
				return w;
			}
		}
		logElementCheck(String.format("(%s.%s == %s)", by.toString(), method, name), false);
		return null;
	}

	/**
	 * Check whether text could be found with given element.getText()
	 * 
	 * @param by by could be By.name(name) | By.id(id) | By.xpath(xpath)
	 * @param elementName such as add button, remove button, etc.
	 * @return WebElement
	 *  
	 */
	public WebElement getElement(By by, String elementName) {
		List<WebElement> wel = driver.findElements(by);
		for (WebElement w : wel) {
			if (null != w && w.isEnabled()
					&& w.isDisplayed()) {
				logElementCheck(elementName, true);
				return w;
			}
		}
		logElementCheck(elementName, false);
		String timeStamp = String.valueOf(System.currentTimeMillis());
		captureScreen(captureDir, 
				String.format("%s_%s.png", timeStamp, elementName),
				String.format("(%s: %s) is not found", elementName, by.toString()));
		return null;
	}
	
	/**
	 * Restart ADB Server and wait for several seconds after start server
	 * 
	 * @throws Exception coming from runCmd
	 *  
	 */
	public static void restartADBServer() throws Exception{
		runCmd(KILL_SERVER);
		runCmd(START_SERVER);
		sleep(START_SERVER_TIME_WAIT);
	}

	/**
	 * Restart ADB Server
	 * @return 
	 * 
	 * @return boolean check whether device is connected to adb
	 * @throws Exception coming from runCmd
	 *  
	 */
	public static boolean checkDevice() throws Exception{
		InputStream stdout= runCmd(GET_DEVICES);
		InputStreamReader osr = new InputStreamReader(stdout);
        BufferedReader obr = new BufferedReader(osr);
        // Ignore the first line
        obr.readLine();
        String line;
        ArrayList<String> devices = new ArrayList<String>();
        while ( (line = obr.readLine()) != null){
        	if(-1 != line.indexOf("device")){
        		// Get device's ID
        		devices.add(line.substring(0, line.indexOf("\t")));
        	}
        }
        int deviceNum = devices.size();
        InputStreamReader is_reader;
        String str;
        // If exist devices, let user to choose which one should be used
        if(deviceNum >= 2){
        	for(int i = 0; i < deviceNum; i++){
        		System.out.println(String.format("Do you want to use device %s :[yes|no]", devices.get(i)));
            	is_reader = new InputStreamReader(System.in);
            	str = new BufferedReader(is_reader).readLine();
            	if(str.toLowerCase().startsWith("y")){
            		deviceID = devices.get(i);
            		return true;
            	}
        	}
        	if(null == deviceID){
        		logErr("Please select one device for test.");
            	return false;
        	}
        }
        else if(deviceNum == 1){
        	deviceID = devices.get(0);
        	return true;
        }
        
    	logErr("No device found. Please check connection.");
    	return false;
	}
	
	/**
	 * Start port and WebDriver. There should be waited for several seconds after start web driver
	 * 
	 * @return boolean check whether device is connected to adb
	 * @throws coming from runCmd
	 *  
	 */
	public static boolean startPortAndWebDriver() throws Exception{
		runCmd(String.format(START_PORT,deviceID));
		InputStream stdout= runCmd(String.format(START_WEB_DRIVER,deviceID));
		InputStreamReader osr = new InputStreamReader(stdout);
		BufferedReader obr = new BufferedReader(osr);
		String line;
		while ( (line = obr.readLine()) != null){
			if(-1 != line.indexOf("Error")){
				logErr("WebDriver doesn't start. Please check whether it's installed.");
				return false;
			}
		}
		sleep(3);
		return true;
	}
	
	/**
	 * Close WebDriver
	 * 
	 * @throws Exception coming from runCmd
	 *  
	 */
	public void closeWebDriver() throws Exception{
		driver.close();
		runCmd(CLOSE_WEB_DRIVER);
	}
}
