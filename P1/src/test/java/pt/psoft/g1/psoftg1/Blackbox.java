package pt.psoft.g1.psoftg1;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.ArrayList;
import java.util.List;


public class Blackbox {

    @Nested
    class BookDomain{
        @Test
        public void testBookConstructor() {
            Genre genre = new Genre("Science Fiction");
            List<Author> authors = new ArrayList<>();
            authors.add(new Author("Author Name", "Author Bio", "photoURI"));
            Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");

            assertEquals("8175257660", book.getIsbn().toString());
            assertEquals("Book Title", book.getTitle().toString());
            assertEquals("Book Description", book.getDescription().toString());
            assertEquals(genre, book.getGenre());
            assertEquals(authors, book.getAuthors());
        }

        @Test
        public void testBookSetTitle() {
            Genre genre = new Genre("Science Fiction");
            List<Author> authors = new ArrayList<>();
            authors.add(new Author("Author Name", "Author Bio", "photoURI"));
            Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
            book.setTitle("New Title");

            assertEquals("New Title", book.getTitle().toString());
        }

        @Test
        public void testBookSetDescription() {
            Genre genre = new Genre("Science Fiction");
            List<Author> authors = new ArrayList<>();
            authors.add(new Author("Author Name", "Author Bio", "photoURI"));
            Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
            book.setDescription("New Description");

            assertEquals("New Description", book.getDescription().toString());
        }

        @Test
        public void testBookSetGenre() {
            Genre genre = new Genre("Science Fiction");
            Genre newGenre = new Genre("Fantasy");
            List<Author> authors = new ArrayList<>();
            authors.add(new Author("Author Name", "Author Bio", "photoURI"));
            Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
            book.setGenre(newGenre);

            assertEquals(newGenre, book.getGenre());
        }

        @Test
        public void testBookSetAuthors() {
            Genre genre = new Genre("Science Fiction");
            List<Author> authors = new ArrayList<>();
            authors.add(new Author("Author Name", "Author Bio", "photoURI"));
            List<Author> newAuthors = new ArrayList<>();
            newAuthors.add(new Author("New Author", "New Bio", "photoURI"));
            Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
            book.setAuthors(newAuthors);

            assertEquals(newAuthors, book.getAuthors());
        }
    }

    @Nested
    class AuthorDomain{
        @Test
        public void testAuthorConstructor() {
            Author author = new Author("Author Name", "Author Bio", "photoURI");

            assertEquals("Author Name", author.getName().toString());
            assertEquals("Author Bio", author.getBio().toString());
        }

        @Test
        public void testAuthorSetName() {
            Author author = new Author("Author Name", "Author Bio", "photoURI");
            author.setName("New Name");

            assertEquals("New Name", author.getName().toString());
        }

        @Test
        public void testAuthorSetBio() {
            Author author = new Author("Author Name", "Author Bio", "photoURI");
            author.setBio("New Bio");

            assertEquals("New Bio", author.getBio().toString());
        }
    }

    @Nested
    class DescriptionDomain{
        @Test
        public void testAuthorConstructor() {
            Author author = new Author("Author Name", "Author Bio", "photoURI");

            assertEquals("Author Name", author.getName().toString());
            assertEquals("Author Bio", author.getBio().toString());
        }

        @Test
        public void testAuthorSetName() {
            Author author = new Author("Author Name", "Author Bio", "photoURI");
            author.setName("New Name");

            assertEquals("New Name", author.getName().toString());
        }

        @Test
        public void testAuthorSetBio() {
            Author author = new Author("Author Name", "Author Bio", "photoURI");
            author.setBio("New Bio");

            assertEquals("New Bio", author.getBio().toString());
        }
    }

    @Nested
    class IsbnDomain{
        @Test
        public void testIsbnConstructor() {
            Isbn isbn = new Isbn("8175257660");

            assertEquals("8175257660", isbn.toString());
        }

        @Test
        public void testIsbnInvalidConstructor() {
            assertThrows(IllegalArgumentException.class, () -> new Isbn("123456789012"));
        }
    }

    @Nested
    class TitleDomain{
        @Test
        public void testTitleConstructor() {
            Title title = new Title("Book Title");

            assertEquals("Book Title", title.toString());
        }

        @Test
        public void testSetTitle() {
            Title title = new Title("Book Title");
            title.setTitle("New Title");

            assertEquals("New Title", title.toString());
        }
    }

    @Nested
    class BioDomain{
        @Test
        public void testBioConstructor() {
            Bio bio = new Bio("Author Bio");

            assertEquals("Author Bio", bio.toString());
        }

        @Test
        public void testSetBio() {
            Bio bio = new Bio("Author Bio");
            bio.setBio("New Bio");

            assertEquals("New Bio", bio.toString());
        }
    }
}
