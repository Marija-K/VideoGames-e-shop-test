package mk.ukim.finki.wp.eshop.selenium;

import mk.ukim.finki.wp.eshop.model.Category;
import mk.ukim.finki.wp.eshop.model.Publisher;
import mk.ukim.finki.wp.eshop.model.Role;
import mk.ukim.finki.wp.eshop.model.User;
import mk.ukim.finki.wp.eshop.service.CategoryService;
import mk.ukim.finki.wp.eshop.service.PublisherService;
import mk.ukim.finki.wp.eshop.service.UserService;
import mk.ukim.finki.wp.eshop.service.VideoGamesService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {

    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PublisherService publisherService;

    @Autowired
    VideoGamesService videoGamesService;

    private HtmlUnitDriver driver;

    private static Category c1;
    private static Category c2;
    private static Publisher p1;
    private static Publisher p2;
    private static User regularUser;
    private static User adminUser;

    private static boolean dataInitialized = false;

    @BeforeEach
    public void setup(WebApplicationContext wac) throws ParseException {
        this.driver = new HtmlUnitDriver(true);
        initData();
    }

    public void initData() throws ParseException {
        String birthday = "1999-12-19";
        if (!dataInitialized){
            c1 = categoryService.create("c1", "c1");
            categoryService.create("c2", "c2");

            p1 = publisherService.save("p1").get();
            publisherService.save("p1");

            String user = "user";
            String admin = "admin";

            regularUser = userService.register(user, user, user, user, user, Role.ROLE_USER, birthday);
            adminUser = userService.register(admin, admin, admin, admin, admin, Role.ROLE_ADMIN, birthday);
            dataInitialized = true;
        }
    }

    @Test
    public void testScenario() throws Exception{
        VideoGamesPage videoGamesPage = VideoGamesPage.to(this.driver);
        videoGamesPage.assertElemts(0,0,0,0,0);
        LoginPage loginPage = LoginPage.openLogin(this.driver);
        videoGamesPage = LoginPage.doLogin(this.driver, loginPage, adminUser.getUsername(), adminUser.getPassword());
        videoGamesPage.assertElemts(0,0,0,0,1);
    }

    @AfterEach
    public void destroy(){
        if (this.driver!=null){
            this.driver.close();
        }
    }

}
