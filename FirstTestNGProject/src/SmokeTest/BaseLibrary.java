package com.myaccess.utils;

import com.myaccess.pages.LoginPage;

/*
 * BaseLibrary is singleton class.
 * */

public class BaseLibrary extends DriverHelper {

	private String baseURL;
	public String adminUsername;
	public String adminPassword;
	public String testUserUsername;
	public String testUserPassword;
	public String testUser1Username;
	public String testUser1Password;
	public String environment;
	
	private String dbHost;
	private String dbPort;
	private String dbServiceName;
	private String dbUserName;
	private String dbPassword;
	
	
	private String propertiesFilePath = "//src//com//myaccess//config//application.properties";
	private String commonLocatorsFilePath = "//src//com//myaccess//config//IdentityAndAccess.xml";
	private String configFilePath = "//src//com//myaccess//config//configuration.xml";
	public LocatorReader configReader;
	public LocatorReader commonLocatorReader;
	public PropertyReader propertyReader;

	private static BaseLibrary setupObj;

	private LoginPage loginpageObj;
	public DatabaseConnector dbConnectorObj;

	private BaseLibrary() {

	}

	public static BaseLibrary getInstance() {
		if (setupObj == null) {
			setupObj = new BaseLibrary();
		}
		return setupObj;
	}

	/*
     * 
     */

	public void readySetup(String environment, boolean isDBConnectionRequired) throws Exception {
		setEnvironment(environment);

		String basePath = "configuration.environments." + environment;
		
		configReader = new LocatorReader(configFilePath);
		commonLocatorReader = new LocatorReader(commonLocatorsFilePath);
		propertyReader = new PropertyReader();
		propertyReader.loadApplicationFile(propertiesFilePath);

		coreSetup(propertyReader, commonLocatorReader);

		baseURL = configReader.getLocator(basePath + ".url");
		adminUsername = configReader.getLocator("configuration.credentials.account[@name='admin'].userId");
		adminPassword = propertyReader.getProperty("adminPassword");
		testUserUsername = configReader.getLocator("configuration.credentials.account[@name='testUser'].userId");
		testUserPassword = propertyReader.getProperty("testUserPassword");
		testUser1Username = configReader.getLocator("configuration.credentials.account[@name='testUser1'].userId");
		testUser1Password = propertyReader.getProperty("testUser1Password");

		if (isDBConnectionRequired) {
			String dbBasePath = basePath + ".database";
			dbHost = configReader.getLocator(dbBasePath + ".host");
			dbPort = configReader.getLocator(dbBasePath + ".port");
			dbServiceName = configReader.getLocator(dbBasePath + ".servicename");
			dbUserName = configReader.getLocator(dbBasePath + ".username");
			dbPassword = configReader.getLocator(dbBasePath + ".password");

			dbConnectorObj = new DatabaseConnector();
			dbConnectorObj.connectViaJDBC(dbHost, dbPort, dbServiceName, dbUserName, dbPassword);
		}

		driver.navigate().to(baseURL);
		loginpageObj = new LoginPage();
	}

	public void setEnvironment(String env) {
		environment = env;
	}

	public String getEnvironment() {
		return environment;
	}

	public void login(String username, String password) throws Exception {
		loginpageObj.login(username, password);
	}

	public void logout() throws Exception {
		loginpageObj.logout();
	}

	public String getMethodName(final int depth) {
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		return ste[depth].getMethodName();
	}

	public void navigateToBaseURL() {
		driver.navigate().to(baseURL);
	}

	public void close() {
		driver.close();
		driver.quit();
	}

}
