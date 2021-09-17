package mk.ukim.finki.wp.eshop.web.controller;

import mk.ukim.finki.wp.eshop.model.Category;
import mk.ukim.finki.wp.eshop.model.Publisher;
import mk.ukim.finki.wp.eshop.model.VideoGame;
import mk.ukim.finki.wp.eshop.service.CategoryService;
import mk.ukim.finki.wp.eshop.service.PublisherService;
import mk.ukim.finki.wp.eshop.service.VideoGamesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/videogames")
public class VideoGameController {

    private final VideoGamesService videoGamesService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;

    public VideoGameController(VideoGamesService videoGamesService,
                               CategoryService categoryService,
                               PublisherService publisherService) {
        this.videoGamesService = videoGamesService;
        this.categoryService = categoryService;
        this.publisherService = publisherService;
    }

    @GetMapping
    public String getProductPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<VideoGame> videoGames = this.videoGamesService.findAll();
        model.addAttribute("videoGames", videoGames);
        model.addAttribute("bodyContent","videogames");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.videoGamesService.deleteById(id);
        return "redirect:/videogames";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if(this.videoGamesService.findById(id).isPresent()){
            VideoGame videoGame = this.videoGamesService.findById(id).get();
            List<Publisher> publishers = this.publisherService.findAll();
            List<Category> categories = this.categoryService.listCategories();
            model.addAttribute("publishers", publishers);
            model.addAttribute("categories", categories);
            model.addAttribute("videoGame", videoGame);
            model.addAttribute("bodyContent","add-game");
            return "master-template";
        }
        return "redirect:/videogames?error=GameNotFound";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProductPage(Model model) {
        List<Publisher> publishers = this.publisherService.findAll();
        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("publishers", publishers);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent","add-game");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveProduct(@RequestParam String name,
                              @RequestParam String imgUrl,
                              @RequestParam Double price,
                              @RequestParam String description,
                              @RequestParam Long category,
                              @RequestParam Long publisher){
        this.videoGamesService.save(name, imgUrl, price, description, category, publisher);
        return "redirect:/videogames";
    }
}
