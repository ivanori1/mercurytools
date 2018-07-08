package com.demoaut.newtours;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class FinalExam {
    WebDriver driver;
    String baseURL = "http://newtours.demoaut.com/mercuryregister.php";
    String strUsername = "ivanivan";
    String strPassword = "123";


    //Elements
    private By email = By.id("email");
    By password = By.name("password");
    By comfirmPass = By.name("confirmPassword");
    By submit = By.name("register");
    By signIn = By.cssSelector("[href='mercurysignon.php']");
    By username = By.name("userName");
    By login = By.name("login");
    By toMonth = By.name("toMonth");
    By toDay = By.name("toDay");
    By business = By.cssSelector("[value='Business']");
    By continueButton = By.name("findFlights");
    By reserveFlight = By.name("reserveFlights");
    By buyFlights = By.name("buyFlights");
    By logout = By.cssSelector("[src='/images/forms/Logout.gif']");

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get(baseURL);
    }

    @Test
    public void test() {

        //1) Register and assert register successful
        driver.findElement(email).sendKeys(strUsername);
        driver.findElement(password).sendKeys(strPassword);
        driver.findElement(comfirmPass).sendKeys(strPassword);
        driver.findElement(submit).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("create_account_success"));

        //2) Login
        driver.findElement(signIn).click();
        driver.findElement(username).sendKeys(strUsername);
        driver.findElement(password).sendKeys(strPassword);
        driver.findElement(login).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("mercuryreservation"));

        //3) Change 3 settings and assert that those are correct
        //a) Returning month
        Select returningMonth = new Select(driver.findElement(toMonth));
        returningMonth.selectByValue("7");
        Assert.assertEquals(returningMonth.getFirstSelectedOption().getText(), "July");
        //b) Returning day
        String selectDate = "20";
        Select returningDay = new Select(driver.findElement(toDay));
        returningDay.selectByVisibleText(selectDate);
        Assert.assertEquals(returningDay.getFirstSelectedOption().getText(), selectDate);
        //c) Service class
        WebElement businessRadio = driver.findElement(business);
        businessRadio.click();
        Assert.assertTrue(businessRadio.isSelected());

        //4) Change both settings and assert that those are selected
        driver.findElement(continueButton).click();
        WebElement radioOutFlight = driver.findElement(By.cssSelector("[value*='361']"));
        radioOutFlight.click();
        Assert.assertTrue(radioOutFlight.isSelected());
        WebElement radioInFlight = driver.findElement(By.cssSelector("[value*='631']"));
        radioInFlight.click();
        Assert.assertTrue(radioInFlight.isSelected());

        //5) Click Secure purchase
        driver.findElement(reserveFlight).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("mercurypurchase"));

        //6) Assert that itinerary has been booked
        driver.findElement(buyFlights).click();
        Assert.assertTrue(driver.getTitle().contains("Flight Confirmation"));
        //7) Log out
        driver.findElement(logout).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("mercurysignon"));

    }

    @After
    public void after() {
        driver.quit();
    }
}
