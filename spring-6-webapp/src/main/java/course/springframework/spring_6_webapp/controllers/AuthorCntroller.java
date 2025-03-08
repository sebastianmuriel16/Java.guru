package course.springframework.spring_6_webapp.controllers;

import course.springframework.spring_6_webapp.services.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorCntroller {

    private final AuthorService authorService;

    public AuthorCntroller(AuthorService authorService){
        this.authorService = authorService;
    }

    @RequestMapping("/authors")
    public String getAuthors(Model model){
        model.addAttribute("allAuthors", authorService.findAll() );
        return "authors";
    }

}
