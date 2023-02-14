package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

public class HomePage {
    @FindBy(id = "nav-files-tab")
    private WebElement filesNavTab;

    @FindBy(id = "fileUpload")
    private WebElement fileUploadButton;

    @FindBy(id = "uploadButton")
    private WebElement uploadButton;


    @FindBy(id = "button-view-file")
    private WebElement buttonViewFile;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesNavTab;
    @FindBy(id = "button-add-note")
    private WebElement addNoteButton;

    @FindBy(id = "button-edit-note")
    private WebElement editNoteButton;
    @FindBy(id = "button-delete-note")
    private WebElement deleteNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;
    @FindBy(id = "noteSubmitButton")
    private WebElement noteSubmitButton;


    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsNavTab;

    @FindBy(id = "button-add-credential")
    private WebElement addCredentialButton;

    @FindBy(id = "button-edit-credential")
    private WebElement editCredentialButton;
    @FindBy(id = "button-delete-credential")
    private WebElement deleteCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;
    @FindBy(id = "credentialSubmitButton")
    private WebElement credentialSubmitButton;

    public final String fileTable = "fileTable";
    public final String noteTable = "noteTable";
    public final String noteModal = "noteModal";
    public final String credentialTable = "credentialTable";
    public final String credentialModal = "credentialModal";
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }

    public void navigateToFile(){
        filesNavTab.click();
    }
    public void dowloadFile(){
        buttonViewFile.click();
    }

    public void uploadFile(String filename){
        fileUploadButton.sendKeys(new File(filename).getAbsolutePath());
        uploadButton.click();
    }

    public void navigateToNote(){
        notesNavTab.click();
    }

    public void addNoteClick(){
        addNoteButton.click();
    }
    public void editNoteClick(){
        editNoteButton.click();
    }
    public void deleteNoteClick(){
        deleteNoteButton.click();
    }
    public void addNote(String title, String description){
        noteTitleField.clear();
        noteTitleField.sendKeys(title);
        noteDescriptionField.clear();
        noteDescriptionField.sendKeys(description);
        noteSubmitButton.click();
    }

    public void navigateToCredentials(){
        credentialsNavTab.click();
    }
    public void addCredentialClick(){
        addCredentialButton.click();
    }

    public void editCredentialClick(){
        editCredentialButton.click();
    }
    public void deleteCredentialClick(){
        deleteCredentialButton.click();
    }
    public void addCredential(String url, String username, String password){
        credentialUrlField.sendKeys(url);
        credentialUsernameField.clear();
        credentialUsernameField.sendKeys(username);
        credentialPasswordField.clear();
        credentialPasswordField.sendKeys(password);
        credentialSubmitButton.click();
    }

}
