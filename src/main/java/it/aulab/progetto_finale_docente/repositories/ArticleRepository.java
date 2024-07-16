package it.aulab.progetto_finale_docente.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import it.aulab.progetto_finale_docente.models.Article;
import it.aulab.progetto_finale_docente.models.Category;
import it.aulab.progetto_finale_docente.models.User;

public interface ArticleRepository extends ListCrudRepository<Article, Long>{
    List<Article> findByCategory(Category category);
    List<Article> findByUser(User user);
}