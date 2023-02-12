package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {
    @FindBy(id = "successHomeLink")
    private WebElement successHomeLink;

    @FindBy(id ="errorHomeLink")
    private WebElement errorHomeLink;

    @FindBy(id ="alertError")
    private WebElement errorAlert;

    @FindBy(id ="success")
    private WebElement successAlert;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }

    public void navigateToHomeFromSuccess(){
        successHomeLink.click();
    }

    public String getSuccessAlert() {
        return successAlert.getText();
    }
}
