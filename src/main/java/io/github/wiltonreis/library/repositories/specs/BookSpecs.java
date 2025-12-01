package io.github.wiltonreis.library.repositories.specs;

import io.github.wiltonreis.library.model.Book;
import io.github.wiltonreis.library.model.GenreBook;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {

    public static Specification<Book> isbnEqual(String isbn){
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title){
        return (root, query, cb) ->
                cb.like( cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> genreEqual(GenreBook genre){
        return (root, query, cb) -> cb.equal(root.get("genre"), genre);
    }

    public static Specification<Book> publicationYearEqual(Integer publicationYear){
        return (root, query, cb) ->
                cb.equal(cb.function("to_char", String.class, root.get("publicationDate"), cb.literal("YYYY")),
                        publicationYear.toString());
    }

    public static Specification<Book> genreEqual(String name){
        return (root, query, cb) -> {
            Join<Object, Object> authorJoin = root.join("author", JoinType.INNER);
            return cb.like( cb.upper(authorJoin.get("name")), "%" + name.toUpperCase() + "%");
        };
    }
}
