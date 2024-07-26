package it.aulab.progetto_finale_docente.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.aulab.progetto_finale_docente.dtos.ArticleDto;
import it.aulab.progetto_finale_docente.dtos.CategoryDto;
import it.aulab.progetto_finale_docente.models.Category;
import it.aulab.progetto_finale_docente.services.ArticleService;
import it.aulab.progetto_finale_docente.services.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;
    
   //Rotta per la ricerca dell'articolo in base alla categoria
    @GetMapping("/search/{id}")
    public String categorySearch(@PathVariable("id") Long id, Model viewModel) {
        CategoryDto category = categoryService.read(id);

        viewModel.addAttribute("title", "Tutti gli articoli trovati per categoria " + category.getName());

        List<ArticleDto> articles = articleService.searchByCategory(modelMapper.map(category, Category.class));
        viewModel.addAttribute("articles", articles);

        return "article/articles";
    }

}
