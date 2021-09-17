package mk.ukim.finki.wp.eshop;

import mk.ukim.finki.wp.eshop.model.Category;
import mk.ukim.finki.wp.eshop.model.Publisher;
import mk.ukim.finki.wp.eshop.model.Role;
import mk.ukim.finki.wp.eshop.model.VideoGame;
import mk.ukim.finki.wp.eshop.service.CategoryService;
import mk.ukim.finki.wp.eshop.service.PublisherService;
import mk.ukim.finki.wp.eshop.service.UserService;
import mk.ukim.finki.wp.eshop.service.VideoGamesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.ParseException;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class EShopApplicationTests {

    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PublisherService publisherService;

    @Autowired
    VideoGamesService videoGamesService;

    private static Category c1;
    private static Publisher p1;
    private static boolean dataInitialized = false;

    @BeforeEach
    public void setup(WebApplicationContext wac) throws ParseException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
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

            userService.register(user, user, user, user, user, Role.ROLE_USER, birthday);
            userService.register(admin, admin, admin, admin, admin, Role.ROLE_ADMIN, birthday);
            dataInitialized = true;
        }
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetProducts() throws Exception {
        MockHttpServletRequestBuilder videoGameRequest = MockMvcRequestBuilders.get("/videogames");
        this.mockMvc.perform(videoGameRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("videoGames"))
                .andExpect(MockMvcResultMatchers.model().attribute("bodyContent","videogames"))
                .andExpect(MockMvcResultMatchers.view().name("master-template"))
        ;
    }

    @Test
    public void testDeleteVideoGame() throws Exception {
        VideoGame videoGame = this.videoGamesService.save("test", 15.00, this.c1.getId(), this.p1.getId()).get();
        MockHttpServletRequestBuilder videoGamesDeleteRequest = MockMvcRequestBuilders.delete("/videogames/delete/"+videoGame.getId());
        this.mockMvc.perform(videoGamesDeleteRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/videogames"));
    }
}
