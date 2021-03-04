package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(id="logout-button")
    private WebElement logoutButton;

    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteDescription;

    @FindBy(id="credential-url")
    private WebElement credentialUrl;

    @FindBy(id="credential-username")
    private WebElement credentialUsername;

    @FindBy(id="credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "nav-notes-tab")
    WebElement navTabNote;

    @FindBy(id = "nav-credentials-tab")
    WebElement navTabCredential;

    @FindBy(css = "#nav-notes > button")
    WebElement addNoteButton;

    @FindBy(css = "#nav-credentials > button")
    WebElement addCredentialButton;

    @FindBy(css = "#noteModal > .modal-dialog > .modal-content > .modal-footer > .btn-primary")
    WebElement saveNoteButton;

    @FindBy(css = "#credentialModal > .modal-dialog > .modal-content > .modal-footer > .btn-primary")
    WebElement saveCredentialButton;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logout() throws InterruptedException {
        Thread.sleep(1000);
        this.logoutButton.click();
    }

    public void createNote(String noteTitle, String noteDescription) throws InterruptedException {
        Thread.sleep(1000);
        this.noteTitle.clear();
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.clear();
        this.noteDescription.sendKeys(noteDescription);
        Thread.sleep(1000);
        this.saveNoteButton.click();
    }
    public void clickAddNoteButton() throws InterruptedException {
        Thread.sleep(1000);
        this.addNoteButton.click();
    }
    public void clickNavTabNote() throws InterruptedException {
        Thread.sleep(2000);
        this.navTabNote.click();
    }
    public void createCredential(String credentialUrl, String credentialUsername, String credentialPassword) throws InterruptedException {
        Thread.sleep(1000);
        this.credentialUrl.clear();
        this.credentialUrl.sendKeys(credentialUrl);
        this.credentialUsername.clear();
        this.credentialUsername.sendKeys(credentialUsername);
        this.credentialPassword.clear();
        this.credentialPassword.sendKeys(credentialPassword);
        Thread.sleep(1000);
        this.saveCredentialButton.click();
    }

    public void clickAddCredentialButton() throws InterruptedException {
        Thread.sleep(1000);
        this.addCredentialButton.click();
    }
    public void clickNavTabCredential() throws InterruptedException {
       Thread.sleep(1000);
        this.navTabCredential.click();
    }
}
