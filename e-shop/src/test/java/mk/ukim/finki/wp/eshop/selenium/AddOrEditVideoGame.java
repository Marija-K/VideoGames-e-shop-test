package mk.ukim.finki.wp.eshop.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AddOrEditVideoGame extends AbstractPage {

    private WebElement name;
    private WebElement price;
    private WebElement category;
    private WebElement publisher;
    private WebElement submit;

    public AddOrEditVideoGame(WebDriver driver) {
        super(driver);
    }

    public static VideoGamesPage addProduct(WebDriver driver, String name, String price, String category, String publisher) {
        get(driver, "/products/add-form");
        AddOrEditVideoGame addOrEditVideoGame = PageFactory.initElements(driver, AddOrEditVideoGame.class);
        addOrEditVideoGame.name.sendKeys(name);
        addOrEditVideoGame.price.sendKeys(price);
        addOrEditVideoGame.category.click();
        addOrEditVideoGame.category.findElement(By.xpath("//option[. = '" + category + "']")).click();
        addOrEditVideoGame.publisher.click();
        addOrEditVideoGame.publisher.findElement(By.xpath("//option[. = '" + publisher + "']")).click();

        addOrEditVideoGame.submit.click();
        return PageFactory.initElements(driver, ProductsPage.class);
    }

    public static VideoGamesPage editProduct(WebDriver driver, WebElement editButton, String name, String price, String category, String publisher) {
        editButton.click();
        System.out.println(driver.getCurrentUrl());
        AddOrEditVideoGame addOrEditVideoGame = PageFactory.initElements(driver, AddOrEditVideoGame.class);
        addOrEditVideoGame.name.sendKeys(name);
        addOrEditVideoGame.price.sendKeys(price);
        addOrEditVideoGame.category.click();
        addOrEditVideoGame.category.findElement(By.xpath("//option[. = '" + category + "']")).click();
        addOrEditVideoGame.publisher.click();
        addOrEditVideoGame.publisher.findElement(By.xpath("//option[. = '" + publisher + "']")).click();

        addOrEditVideoGame.submit.click();
        return PageFactory.initElements(driver, ProductsPage.class);
    }


}
