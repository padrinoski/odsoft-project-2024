package pt.psoft.g1.psoftg1.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.common.domain.CreateBookEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteBookEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateBookEvent;
import pt.psoft.g1.psoftg1.interfaces.AuthorRepository;
import pt.psoft.g1.psoftg1.interfaces.BookRepository;
import pt.psoft.g1.psoftg1.interfaces.BookService;
import pt.psoft.g1.psoftg1.interfaces.GenreRepository;
import pt.psoft.g1.psoftg1.model.Book;
import pt.psoft.g1.psoftg1.model.Author;
import pt.psoft.g1.psoftg1.model.BookDTO;
import pt.psoft.g1.psoftg1.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2

public class BookServiceImpl implements BookService {

	private final BookRepository repository;
	private final AuthorRepository authorRepository;
	private final GenreRepository genreRepository;
	public BookServiceImpl(BookRepository repository, AuthorRepository authorRepository, GenreRepository genreRepository) {
		this.repository = repository;
		this.authorRepository = authorRepository;
		this.genreRepository = genreRepository;
	}

	@Override
	public Optional<Book> getBookByIsbn(final String isbn) {

		return repository.findByIsbn(isbn);
	}

	@Override
	public Optional<BookDTO> findByIsbn(String isbn) {
		final Optional<Book> book = repository.findByIsbn(isbn);
		return book.map(Book::toDTO);
	}


	@Override
	public Iterable<BookDTO> findByTitle(final String title) {
		Iterable<Book> p = repository.findByTitle(title);
		List<BookDTO> pDto = new ArrayList<>();
		for (Book pd : p) {
			pDto.add(pd.toDTO());
		}

		return pDto;
	}

	@Override
	public Iterable<BookDTO> getCatalog() {
		Iterable<Book> p = repository.findAll();
		List<BookDTO> pDto = new ArrayList<>();
		for (Book pd : p) {
			pDto.add(pd.toDTO());
		}

		return pDto;
	}
	
	@Override
	public void createBook(CreateBookEvent event) {
		String genrePK = event.getGenreId();
		Optional<Genre> genre = genreRepository.findByPk(genrePK);
		if(genre.isEmpty())throw new RuntimeException("Genre with PK = " + genrePK + " does not exist");
		
		List<String> authorIds = event.getAuthorIds();
		List<Author> authors = new ArrayList<>();
		for (String authorId : authorIds) {
			Optional<Author> author = authorRepository.findByAuthorNumber(authorId);
			if (author.isPresent()) {
				authors.add(author.get());
			} else {
				throw new RuntimeException("Author with ID = " + authorId + " does not exist");
			}
		}
				
		Book p = new Book(event.getIsbn(), event.getTitle(), event.getDescription(), genre.get(), authors,null);
		repository.save(p);
		log.debug("Persisted book: " + p);

	}

	@Override
	public void updateBook(UpdateBookEvent event) {
		Optional<Book> p = repository.findByIsbn(event.getIsbn());
		if (p.isPresent()) {
			Book book = p.get();
			book.setTitle(event.getTitle());
			book.setDescription(event.getDescription());
			repository.save(book);
		} else {
			log.error("Book with isbn " + event.getIsbn() + " not found!");
		}
	}

	@Override
	public void deleteBook(DeleteBookEvent event) {
		Optional<Book> p = repository.findByIsbn(event.getIsbn());
		if (p.isPresent()) {
			Book book = p.get();
			repository.delete(book);
		} else {
			log.error("Book with isbn " + event.getIsbn() + " not found!");
		}
	}
}
