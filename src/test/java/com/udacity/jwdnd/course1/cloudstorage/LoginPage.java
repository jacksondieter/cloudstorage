package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "buttonLogin")
    private WebElement loginButton;

    @FindBy(id = "alertError")
    private WebElement errorAlert;

    @FindBy(id = "logoutAlert")
    private WebElement logoutAlert;

    @FindBy(id = "successMsg")
    private WebElement successAlert;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void loginUser(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    public String getSuccessAlert() {
        return successAlert.getText();
    }
}
