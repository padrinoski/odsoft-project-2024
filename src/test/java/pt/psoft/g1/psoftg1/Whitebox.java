package pt.psoft.g1.psoftg1;

import static org.junit.jupiter.api.Assertions.*;
import static pt.psoft.g1.psoftg1.usermanagement.model.Librarian.newLibrarian;
import static pt.psoft.g1.psoftg1.usermanagement.model.Reader.newReader;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.access.AccessDeniedException;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.*;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.usermanagement.model.*;

public class Whitebox {

    @Nested
    class UserDomain {


        @Test
        public void testUserInternalState() throws NoSuchFieldException, IllegalAccessException {
            User user = new User("testUser", "Password123!");

            Field usernameField = User.class.getDeclaredField("username");
            usernameField.setAccessible(true);
            assertEquals("testUser", usernameField.get(user));

            Field passwordField = User.class.getDeclaredField("password");
            passwordField.setAccessible(true);
            assertNotNull(passwordField.get(user));

            Field authoritiesField = User.class.getDeclaredField("authorities");
            authoritiesField.setAccessible(true);
            assertTrue(((Set<Role>) authoritiesField.get(user)).isEmpty());
        }

        @Test
        public void testUserSetPassword() throws NoSuchFieldException, IllegalAccessException {
            User user = new User("testUser", "Password123!");
            user.setPassword("NewPassword123!");

            Field passwordField = User.class.getDeclaredField("password");
            passwordField.setAccessible(true);
            assertNotEquals("Password123!", passwordField.get(user));
            assertNotEquals("NewPassword123!", passwordField.get(user)); // Password should be encoded
        }

        @Test
        public void testUserAddAuthority() throws NoSuchFieldException, IllegalAccessException {
            User user = new User("testUser", "Password123!");
            user.addAuthority(new Role(Role.ADMIN));

            Field authoritiesField = User.class.getDeclaredField("authorities");
            authoritiesField.setAccessible(true);
            Set<Role> authorities = (Set<Role>) authoritiesField.get(user);
            assertEquals(1, authorities.size());
            assertTrue(authorities.contains(new Role(Role.ADMIN)));
        }

        @Test
        public void testUserSetName() throws NoSuchFieldException, IllegalAccessException {
            User user = new User("testUser", "Password123!");
            user.setName("John Doe");

            Field nameField = User.class.getDeclaredField("name");
            nameField.setAccessible(true);
            assertEquals(new Name("John Doe").toString(), nameField.get(user).toString());
        }

        @Test
        public void testRoleInternalState() throws NoSuchFieldException, IllegalAccessException {
            Role role = new Role(Role.LIBRARIAN);

            Field authorityField = Role.class.getDeclaredField("authority");
            authorityField.setAccessible(true);
            assertEquals(Role.LIBRARIAN, authorityField.get(role));
        }

        @Test
        public void testReaderInternalState() throws NoSuchFieldException, IllegalAccessException {
            Reader reader = new Reader("readerUser", "Password123!");

            Field usernameField = User.class.getDeclaredField("username");
            usernameField.setAccessible(true);
            assertEquals("readerUser", usernameField.get(reader));

            Field authoritiesField = User.class.getDeclaredField("authorities");
            authoritiesField.setAccessible(true);
            Set<Role> authorities = (Set<Role>) authoritiesField.get(reader);
            assertEquals(1, authorities.size());
            assertTrue(authorities.contains(new Role(Role.READER)));
        }

        @Test
        public void testReaderFactoryMethod() throws NoSuchFieldException, IllegalAccessException {
            Reader reader = newReader("readerUser", "Password123!", "Jane Doe");

            Field usernameField = User.class.getDeclaredField("username");
            usernameField.setAccessible(true);
            assertEquals("readerUser", usernameField.get(reader));

            Field nameField = User.class.getDeclaredField("name");
            nameField.setAccessible(true);
            assertEquals(new Name("Jane Doe").toString(), nameField.get(reader).toString());

            Field authoritiesField = User.class.getDeclaredField("authorities");
            authoritiesField.setAccessible(true);
            Set<Role> authorities = (Set<Role>) authoritiesField.get(reader);
            assertEquals(1, authorities.size());
            assertTrue(authorities.contains(new Role(Role.READER)));
        }

        @Test
        public void testPasswordInternalState() throws NoSuchFieldException, IllegalAccessException {
            Password password = new Password("Password123!");

            Field passwordField = Password.class.getDeclaredField("password");
            passwordField.setAccessible(true);
            assertEquals("Password123!", passwordField.get(password));
        }

        @Test
        public void testPasswordUpdate() throws NoSuchFieldException, IllegalAccessException {
            Password password = new Password("Password123!");
            password.updatePassword("NewPassword123!");

            Field passwordField = Password.class.getDeclaredField("password");
            passwordField.setAccessible(true);
            assertEquals("NewPassword123!", passwordField.get(password));
        }

        @Test
        public void testLibrarianInternalState() throws NoSuchFieldException, IllegalAccessException {
            Librarian librarian = newLibrarian("librarianUser", "Password123!", "John");

            Field usernameField = User.class.getDeclaredField("username");
            usernameField.setAccessible(true);
            assertEquals("librarianUser", usernameField.get(librarian));

            Field authoritiesField = User.class.getDeclaredField("authorities");
            authoritiesField.setAccessible(true);
            Set<Role> authorities = (Set<Role>) authoritiesField.get(librarian);
            assertEquals(1, authorities.size());
            assertTrue(authorities.contains(new Role(Role.LIBRARIAN)));
        }

        @Test
        public void testLibrarianFactoryMethod() throws NoSuchFieldException, IllegalAccessException {
            Librarian librarian = newLibrarian("librarianUser", "Password123!", "John Doe");

            Field usernameField = User.class.getDeclaredField("username");
            usernameField.setAccessible(true);
            assertEquals("librarianUser", usernameField.get(librarian));

            Field nameField = User.class.getDeclaredField("name");
            nameField.setAccessible(true);
            assertEquals(new Name("John Doe").toString(), nameField.get(librarian).toString());

            Field authoritiesField = User.class.getDeclaredField("authorities");
            authoritiesField.setAccessible(true);
            Set<Role> authorities = (Set<Role>) authoritiesField.get(librarian);
            assertEquals(1, authorities.size());
            assertTrue(authorities.contains(new Role(Role.LIBRARIAN)));
        }
    }

    @Nested
    class ReaderDomain{
        @Nested
        class EmailAddressTests {

            @Test
            public void testEmailAddressInternalState() throws NoSuchFieldException, IllegalAccessException {
                EmailAddress emailAddress = new EmailAddress("test@example.com");

                Field addressField = EmailAddress.class.getDeclaredField("address");
                addressField.setAccessible(true);
                assertEquals("test@example.com", addressField.get(emailAddress));
            }
        }

        @Nested
        class PhoneNumberTests {

            @Test
            public void testPhoneNumberInternalState() throws NoSuchFieldException, IllegalAccessException {
                PhoneNumber phoneNumber = new PhoneNumber("912345678");

                Field phoneNumberField = PhoneNumber.class.getDeclaredField("phoneNumber");
                phoneNumberField.setAccessible(true);
                assertEquals("912345678", phoneNumberField.get(phoneNumber));
            }

            @Test
            public void testPhoneNumberInvalid() {
                assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("123456789"));
            }

            @Test
            public void testPhoneNumberToString() {
                PhoneNumber phoneNumber = new PhoneNumber("912345678");
                assertEquals("912345678", phoneNumber.toString());
            }
        }

        @Nested
        class ReaderDetailsTests {

            @Test
            public void testReaderDetailsInternalState() throws NoSuchFieldException, IllegalAccessException {
                pt.psoft.g1.psoftg1.usermanagement.model.Reader reader = new pt.psoft.g1.psoftg1.usermanagement.model.Reader("readerUser", "Password123!");
                ReaderDetails readerDetails = new ReaderDetails(1, reader, "2000-01-01", "912345678", true, true, true, "photoURI", List.of());

                Field readerField = ReaderDetails.class.getDeclaredField("reader");
                readerField.setAccessible(true);
                assertEquals(reader, readerField.get(readerDetails));

                Field phoneNumberField = ReaderDetails.class.getDeclaredField("phoneNumber");
                phoneNumberField.setAccessible(true);
                assertEquals("912345678", ((PhoneNumber) phoneNumberField.get(readerDetails)).toString());
            }

        }

        @Nested
        class ReaderNumberTests {

            @Test
            public void testReaderNumberInternalState() throws NoSuchFieldException, IllegalAccessException {
                ReaderNumber readerNumber = new ReaderNumber(2023, 1);

                Field readerNumberField = ReaderNumber.class.getDeclaredField("readerNumber");
                readerNumberField.setAccessible(true);
                assertEquals("2023/1", readerNumberField.get(readerNumber));
            }

            @Test
            public void testReaderNumberToString() {
                ReaderNumber readerNumber = new ReaderNumber(2023, 1);
                assertEquals("2023/1", readerNumber.toString());
            }
        }

        @Nested
        class BirthDateTests {

            @Test
            public void testBirthDateInternalState() throws NoSuchFieldException, IllegalAccessException {
                BirthDate birthDate = new BirthDate("2000-01-01");

                Field birthDateField = BirthDate.class.getDeclaredField("birthDate");
                birthDateField.setAccessible(true);
                assertEquals("2000-01-01", birthDateField.get(birthDate).toString());
            }

            @Test
            public void testBirthDateInvalidFormat() {
                assertThrows(IllegalArgumentException.class, () -> new BirthDate("01-01-2000"));
            }

            @Test
            public void testBirthDateUnderage() {
                assertThrows(AccessDeniedException.class, () -> new BirthDate(2025, 1, 1));
            }
        }
    }

    @Nested
    class LendingDomain{
        @Test
        public void testLendingNumberInternalState() throws NoSuchFieldException, IllegalAccessException {
            LendingNumber lendingNumber = new LendingNumber(2023, 1);

            Field lendingNumberField = LendingNumber.class.getDeclaredField("lendingNumber");
            lendingNumberField.setAccessible(true);
            assertEquals("2023/1", lendingNumberField.get(lendingNumber));
        }

        @Test
        public void testLendingNumberStringConstructor() throws NoSuchFieldException, IllegalAccessException {
            LendingNumber lendingNumber = new LendingNumber("2023/1");

            Field lendingNumberField = LendingNumber.class.getDeclaredField("lendingNumber");
            lendingNumberField.setAccessible(true);
            assertEquals("2023/1", lendingNumberField.get(lendingNumber));
        }

        @Test
        public void testLendingNumberInvalidFormat() {
            assertThrows(IllegalArgumentException.class, () -> new LendingNumber("2023-1"));
        }
    }

    @Nested
    class GenreDomain{
        @Test
        public void testGenreInternalState() throws NoSuchFieldException, IllegalAccessException {
            Genre genre = new Genre("Science Fiction");

            Field genreField = Genre.class.getDeclaredField("genre");
            genreField.setAccessible(true);
            assertEquals("Science Fiction", genreField.get(genre));
        }

        @Test
        public void testGenreConstructorNull() {
            assertThrows(IllegalArgumentException.class, () -> new Genre(null));
        }

        @Test
        public void testGenreConstructorBlank() {
            assertThrows(IllegalArgumentException.class, () -> new Genre(""));
        }

        @Test
        public void testGenreConstructorTooLong() {
            String longGenre = "A".repeat(101);
            assertThrows(IllegalArgumentException.class, () -> new Genre(longGenre));
        }

        @Test
        public void testSetGenreValid() throws NoSuchFieldException, IllegalAccessException {
            Genre genre = new Genre("Science Fiction");
            genre.setGenre("Fantasy");

            Field genreField = Genre.class.getDeclaredField("genre");
            genreField.setAccessible(true);
            assertEquals("Fantasy", genreField.get(genre));
        }

        @Test
        public void testSetGenreNull() {
            Genre genre = new Genre("Science Fiction");
            assertThrows(IllegalArgumentException.class, () -> genre.setGenre(null));
        }

        @Test
        public void testSetGenreBlank() {
            Genre genre = new Genre("Science Fiction");
            assertThrows(IllegalArgumentException.class, () -> genre.setGenre(""));
        }

        @Test
        public void testSetGenreTooLong() {
            Genre genre = new Genre("Science Fiction");
            String longGenre = "A".repeat(101);
            assertThrows(IllegalArgumentException.class, () -> genre.setGenre(longGenre));
        }
    }

    @Nested
    class BookAuthorDomain {
        @Test
        public void testBookInternalState() throws NoSuchFieldException, IllegalAccessException {
            Genre genre = new Genre("Science Fiction");
            List<Author> authors = new ArrayList<>();
            authors.add(new Author("Author Name", "Author Bio", "photoURI"));
            Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");

            Field isbnField = Book.class.getDeclaredField("isbn");
            isbnField.setAccessible(true);
            assertEquals(new Isbn("8175257660").toString(), isbnField.get(book).toString());

            Field titleField = Book.class.getDeclaredField("title");
            titleField.setAccessible(true);
            assertEquals(new Title("Book Title").toString(), titleField.get(book).toString());

            Field descriptionField = Book.class.getDeclaredField("description");
            descriptionField.setAccessible(true);
            assertEquals(new Description("Book Description").toString(), descriptionField.get(book).toString());

            Field genreField = Book.class.getDeclaredField("genre");
            genreField.setAccessible(true);
            assertEquals(genre, genreField.get(book));

            Field authorsField = Book.class.getDeclaredField("authors");
            authorsField.setAccessible(true);
            assertEquals(authors, authorsField.get(book));
        }

        @Test
        public void testBookConstructorNullGenre() {
            List<Author> authors = new ArrayList<>();
            authors.add(new Author("Author Name", "Author Bio", "photoURI"));
            assertThrows(IllegalArgumentException.class, () -> new Book("8175257660", "Book Title", "Book Description", null, authors, "photoURI"));
        }

        @Test
        public void testBookConstructorEmptyAuthors() {
            Genre genre = new Genre("Science Fiction");
            List<Author> authors = new ArrayList<>();
            assertThrows(IllegalArgumentException.class, () -> new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI"));
        }

        @Test
        public void testBookSetTitle() throws NoSuchFieldException, IllegalAccessException {
            Genre genre = new Genre("Science Fiction");
            List<Author> authors = new ArrayList<>();
            authors.add(new Author("Author Name", "Author Bio", "photoURI"));
            Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
            book.setTitle("New Title");

            Field titleField = Book.class.getDeclaredField("title");
            titleField.setAccessible(true);
            assertEquals(new Title("New Title").toString(), titleField.get(book).toString());
        }

        @Test
        public void testBookSetDescription() throws NoSuchFieldException, IllegalAccessException {
            Genre genre = new Genre("Science Fiction");
            List<Author> authors = new ArrayList<>();
            authors.add(new Author("Author Name", "Author Bio", "photoURI"));
            Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
            book.setDescription("New Description");

            Field descriptionField = Book.class.getDeclaredField("description");
            descriptionField.setAccessible(true);
            assertEquals(new Description("New Description").toString(), descriptionField.get(book).toString());
        }

        @Test
        public void testBookSetGenre() throws NoSuchFieldException, IllegalAccessException {
            Genre genre = new Genre("Science Fiction");
            Genre newGenre = new Genre("Fantasy");
            List<Author> authors = new ArrayList<>();
            authors.add(new Author("Author Name", "Author Bio", "photoURI"));
            Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
            book.setGenre(newGenre);

            Field genreField = Book.class.getDeclaredField("genre");
            genreField.setAccessible(true);
            assertEquals(newGenre, genreField.get(book));
        }

        @Test
        public void testBookSetAuthors() throws NoSuchFieldException, IllegalAccessException {
            Genre genre = new Genre("Science Fiction");
            List<Author> authors = new ArrayList<>();
            authors.add(new Author("Author Name", "Author Bio", "photoURI"));
            List<Author> newAuthors = new ArrayList<>();
            newAuthors.add(new Author("New Author", "New Bio", "photoURI"));
            Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
            book.setAuthors(newAuthors);

            Field authorsField = Book.class.getDeclaredField("authors");
            authorsField.setAccessible(true);
            assertEquals(newAuthors, authorsField.get(book));
        }

        @Nested
        class AuthorTests{
            @Test
            public void testBookInternalState() throws NoSuchFieldException, IllegalAccessException {
                Genre genre = new Genre("Science Fiction");
                List<pt.psoft.g1.psoftg1.authormanagement.model.Author> authors = new ArrayList<>();
                authors.add(new pt.psoft.g1.psoftg1.authormanagement.model.Author("Author Name", "Author Bio", "photoURI"));
                Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");

                Field isbnField = Book.class.getDeclaredField("isbn");
                isbnField.setAccessible(true);
                assertEquals(new Isbn("8175257660").toString(), isbnField.get(book).toString());

                Field titleField = Book.class.getDeclaredField("title");
                titleField.setAccessible(true);
                assertEquals(new Title("Book Title").toString(), titleField.get(book).toString());

                Field descriptionField = Book.class.getDeclaredField("description");
                descriptionField.setAccessible(true);
                assertEquals(new Description("Book Description").toString(), descriptionField.get(book).toString());

                Field genreField = Book.class.getDeclaredField("genre");
                genreField.setAccessible(true);
                assertEquals(genre, genreField.get(book));

                Field authorsField = Book.class.getDeclaredField("authors");
                authorsField.setAccessible(true);
                assertEquals(authors, authorsField.get(book));
            }

            @Test
            public void testBookConstructorNullGenre() {
                List<pt.psoft.g1.psoftg1.authormanagement.model.Author> authors = new ArrayList<>();
                authors.add(new pt.psoft.g1.psoftg1.authormanagement.model.Author("Author Name", "Author Bio", "photoURI"));
                assertThrows(IllegalArgumentException.class, () -> new Book("8175257660", "Book Title", "Book Description", null, authors, "photoURI"));
            }

            @Test
            public void testBookConstructorEmptyAuthors() {
                Genre genre = new Genre("Science Fiction");
                List<pt.psoft.g1.psoftg1.authormanagement.model.Author> authors = new ArrayList<>();
                assertThrows(IllegalArgumentException.class, () -> new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI"));
            }

            @Test
            public void testBookSetTitle() throws NoSuchFieldException, IllegalAccessException {
                Genre genre = new Genre("Science Fiction");
                List<pt.psoft.g1.psoftg1.authormanagement.model.Author> authors = new ArrayList<>();
                authors.add(new pt.psoft.g1.psoftg1.authormanagement.model.Author("Author Name", "Author Bio", "photoURI"));
                Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
                book.setTitle("New Title");

                Field titleField = Book.class.getDeclaredField("title");
                titleField.setAccessible(true);
                assertEquals(new Title("New Title").toString(), titleField.get(book).toString());
            }

            @Test
            public void testBookSetDescription() throws NoSuchFieldException, IllegalAccessException {
                Genre genre = new Genre("Science Fiction");
                List<pt.psoft.g1.psoftg1.authormanagement.model.Author> authors = new ArrayList<>();
                authors.add(new pt.psoft.g1.psoftg1.authormanagement.model.Author("Author Name", "Author Bio", "photoURI"));
                Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
                book.setDescription("New Description");

                Field descriptionField = Book.class.getDeclaredField("description");
                descriptionField.setAccessible(true);
                assertEquals(new Description("New Description").toString(), descriptionField.get(book).toString());
            }

            @Test
            public void testBookSetGenre() throws NoSuchFieldException, IllegalAccessException {
                Genre genre = new Genre("Science Fiction");
                Genre newGenre = new Genre("Fantasy");
                List<pt.psoft.g1.psoftg1.authormanagement.model.Author> authors = new ArrayList<>();
                authors.add(new pt.psoft.g1.psoftg1.authormanagement.model.Author("Author Name", "Author Bio", "photoURI"));
                Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
                book.setGenre(newGenre);

                Field genreField = Book.class.getDeclaredField("genre");
                genreField.setAccessible(true);
                assertEquals(newGenre, genreField.get(book));
            }

            @Test
            public void testBookSetAuthors() throws NoSuchFieldException, IllegalAccessException {
                Genre genre = new Genre("Science Fiction");
                List<pt.psoft.g1.psoftg1.authormanagement.model.Author> authors = new ArrayList<>();
                authors.add(new pt.psoft.g1.psoftg1.authormanagement.model.Author("Author Name", "Author Bio", "photoURI"));
                List<pt.psoft.g1.psoftg1.authormanagement.model.Author> newAuthors = new ArrayList<>();
                newAuthors.add(new pt.psoft.g1.psoftg1.authormanagement.model.Author("New Author", "New Bio", "photoURI"));
                Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
                book.setAuthors(newAuthors);

                Field authorsField = Book.class.getDeclaredField("authors");
                authorsField.setAccessible(true);
                assertEquals(newAuthors, authorsField.get(book));
            }
        }

        @Nested
        class DescriptionTests{
            @Test
            public void testBookInternalState() throws NoSuchFieldException, IllegalAccessException {
                Genre genre = new Genre("Science Fiction");
                List<Author> authors = new ArrayList<>();
                authors.add(new Author("Author Name", "Author Bio", "photoURI"));
                Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");

                Field isbnField = Book.class.getDeclaredField("isbn");
                isbnField.setAccessible(true);
                assertEquals(new Isbn("8175257660").toString(), isbnField.get(book).toString());

                Field titleField = Book.class.getDeclaredField("title");
                titleField.setAccessible(true);
                assertEquals(new Title("Book Title").toString(), titleField.get(book).toString());

                Field descriptionField = Book.class.getDeclaredField("description");
                descriptionField.setAccessible(true);
                assertEquals(new Description("Book Description").toString(), descriptionField.get(book).toString());

                Field genreField = Book.class.getDeclaredField("genre");
                genreField.setAccessible(true);
                assertEquals(genre, genreField.get(book));

                Field authorsField = Book.class.getDeclaredField("authors");
                authorsField.setAccessible(true);
                assertEquals(authors, authorsField.get(book));
            }

            @Test
            public void testBookConstructorNullGenre() {
                List<Author> authors = new ArrayList<>();
                authors.add(new Author("Author Name", "Author Bio", "photoURI"));
                assertThrows(IllegalArgumentException.class, () -> new Book("8175257660", "Book Title", "Book Description", null, authors, "photoURI"));
            }

            @Test
            public void testBookConstructorEmptyAuthors() {
                Genre genre = new Genre("Science Fiction");
                List<Author> authors = new ArrayList<>();
                assertThrows(IllegalArgumentException.class, () -> new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI"));
            }

            @Test
            public void testBookSetTitle() throws NoSuchFieldException, IllegalAccessException {
                Genre genre = new Genre("Science Fiction");
                List<Author> authors = new ArrayList<>();
                authors.add(new Author("Author Name", "Author Bio", "photoURI"));
                Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
                book.setTitle("New Title");

                Field titleField = Book.class.getDeclaredField("title");
                titleField.setAccessible(true);
                assertEquals(new Title("New Title").toString(), titleField.get(book).toString());
            }

            @Test
            public void testBookSetDescription() throws NoSuchFieldException, IllegalAccessException {
                Genre genre = new Genre("Science Fiction");
                List<Author> authors = new ArrayList<>();
                authors.add(new Author("Author Name", "Author Bio", "photoURI"));
                Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
                book.setDescription("New Description");

                Field descriptionField = Book.class.getDeclaredField("description");
                descriptionField.setAccessible(true);
                assertEquals(new Description("New Description").toString(), descriptionField.get(book).toString());
            }

            @Test
            public void testBookSetGenre() throws NoSuchFieldException, IllegalAccessException {
                Genre genre = new Genre("Science Fiction");
                Genre newGenre = new Genre("Fantasy");
                List<Author> authors = new ArrayList<>();
                authors.add(new Author("Author Name", "Author Bio", "photoURI"));
                Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
                book.setGenre(newGenre);

                Field genreField = Book.class.getDeclaredField("genre");
                genreField.setAccessible(true);
                assertEquals(newGenre, genreField.get(book));
            }

            @Test
            public void testBookSetAuthors() throws NoSuchFieldException, IllegalAccessException {
                Genre genre = new Genre("Science Fiction");
                List<Author> authors = new ArrayList<>();
                authors.add(new Author("Author Name", "Author Bio", "photoURI"));
                List<Author> newAuthors = new ArrayList<>();
                newAuthors.add(new Author("New Author", "New Bio", "photoURI"));
                Book book = new Book("8175257660", "Book Title", "Book Description", genre, authors, "photoURI");
                book.setAuthors(newAuthors);

                Field authorsField = Book.class.getDeclaredField("authors");
                authorsField.setAccessible(true);
                assertEquals(newAuthors, authorsField.get(book));
            }
        }

        @Nested
        class IsbnTests{
            @Test
            public void testIsbnInternalState() throws NoSuchFieldException, IllegalAccessException {
                Isbn isbn = new Isbn("8175257660");

                Field isbnField = Isbn.class.getDeclaredField("isbn");
                isbnField.setAccessible(true);
                assertEquals("8175257660", isbnField.get(isbn));
            }

            @Test
            public void testIsbnConstructorInvalid() {
                assertThrows(IllegalArgumentException.class, () -> new Isbn("817525766012"));
            }

            @Test
            public void testIsbnConstructorNull() {
                assertThrows(IllegalArgumentException.class, () -> new Isbn(null));
            }

            @Test
            public void testIsbnToString() {
                Isbn isbn = new Isbn("8175257660");
                assertEquals("8175257660", isbn.toString());
            }
        }
        @Nested
        class TitleTests{
            @Test
            public void testTitleInternalState() throws NoSuchFieldException, IllegalAccessException {
                Title title = new Title("Book Title");

                Field titleField = Title.class.getDeclaredField("title");
                titleField.setAccessible(true);
                assertEquals("Book Title", titleField.get(title));
            }

            @Test
            public void testTitleConstructorNull() {
                assertThrows(IllegalArgumentException.class, () -> new Title(null));
            }

            @Test
            public void testTitleConstructorBlank() {
                assertThrows(IllegalArgumentException.class, () -> new Title(""));
            }

            @Test
            public void testTitleConstructorTooLong() {
                String longTitle = "A".repeat(129);
                assertThrows(IllegalArgumentException.class, () -> new Title(longTitle));
            }

            @Test
            public void testSetTitleValid() throws NoSuchFieldException, IllegalAccessException {
                Title title = new Title("Book Title");
                title.setTitle("New Title");

                Field titleField = Title.class.getDeclaredField("title");
                titleField.setAccessible(true);
                assertEquals("New Title", titleField.get(title));
            }

            @Test
            public void testSetTitleNull() {
                Title title = new Title("Book Title");
                assertThrows(IllegalArgumentException.class, () -> title.setTitle(null));
            }

            @Test
            public void testSetTitleBlank() {
                Title title = new Title("Book Title");
                assertThrows(IllegalArgumentException.class, () -> title.setTitle(""));
            }

            @Test
            public void testSetTitleTooLong() {
                Title title = new Title("Book Title");
                String longTitle = "A".repeat(129);
                assertThrows(IllegalArgumentException.class, () -> title.setTitle(longTitle));
            }
        }

        @Nested
        class BioTests{
            @Test
            public void testBioInternalState() throws NoSuchFieldException, IllegalAccessException {
                Bio bio = new Bio("Author Bio");

                Field bioField = Bio.class.getDeclaredField("bio");
                bioField.setAccessible(true);
                assertEquals("Author Bio", bioField.get(bio));
            }

            @Test
            public void testBioConstructorNull() {
                assertThrows(IllegalArgumentException.class, () -> new Bio(null));
            }

            @Test
            public void testBioConstructorTooLong() {
                String longBio = "A".repeat(4097);
                assertThrows(IllegalArgumentException.class, () -> new Bio(longBio));
            }

            @Test
            public void testSetBioValid() throws NoSuchFieldException, IllegalAccessException {
                Bio bio = new Bio("Author Bio");
                bio.setBio("New Bio");

                Field bioField = Bio.class.getDeclaredField("bio");
                bioField.setAccessible(true);
                assertEquals("New Bio", bioField.get(bio));
            }

            @Test
            public void testSetBioNull() {
                Bio bio = new Bio("Author Bio");
                assertThrows(IllegalArgumentException.class, () -> bio.setBio(null));
            }

            @Test
            public void testSetBioTooLong() {
                Bio bio = new Bio("Author Bio");
                String longBio = "A".repeat(4097);
                assertThrows(IllegalArgumentException.class, () -> bio.setBio(longBio));
            }
        }

    }
}