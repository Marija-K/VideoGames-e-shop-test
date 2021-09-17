package mk.ukim.finki.wp.eshop.selenium;

import lombok.Getter;
import mk.ukim.finki.wp.eshop.model.VideoGame;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VideoGamesPage extends AbstractPage {

    @FindBy(css = "tr[class=videogame]")
    private List<WebElement> videogameRows;


    @FindBy(css = ".delete-videogame")
    private List<WebElement> deleteButtons;


    @FindBy(className = "edit-videogame")
    private List<WebElement> editButtons;


    @FindBy(css = ".cart-videogame")
    private List<WebElement> cartButtons;


    @FindBy(css = ".add-videogame-btn")
    private List<WebElement> addProductButton;

    public VideoGamesPage(WebDriver driver) {
        super(driver);
    }

    public static VideoGamesPage to(WebDriver driver) {
        get(driver, "/videogames");
        return PageFactory.initElements(driver, VideoGamesPage.class);
    }

    public void assertElemts(int videoGamesNumber, int deleteButtons, int editButtons, int cartButtons, int addButtons) {
        Assert.assertEquals("rows do not match", videoGamesNumber, this.getVideogameRows().size());
        Assert.assertEquals("delete do not match", deleteButtons, this.getDeleteButtons().size());
        Assert.assertEquals("edit do not match", editButtons, this.getEditButtons().size());
        Assert.assertEquals("cart do not match", cartButtons, this.getCartButtons().size());
        Assert.assertEquals("add is visible", addButtons, this.getAddProductButton().size());
    }
}
