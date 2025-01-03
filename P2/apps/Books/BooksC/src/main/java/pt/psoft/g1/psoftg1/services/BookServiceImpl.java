package pt.psoft.g1.psoftg1.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pt.psoft.g1.psoftg1.common.ApplicationType;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.common.EventType;
import pt.psoft.g1.psoftg1.common.domain.CreateBookEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteBookEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateBookEvent;
import pt.psoft.g1.psoftg1.interfaces.BookService;
import pt.psoft.g1.psoftg1.model.Book;
import pt.psoft.g1.psoftg1.model.Author;
import pt.psoft.g1.psoftg1.model.BookDTO;
import pt.psoft.g1.psoftg1.repositories.EventRepository;
import pt.psoft.g1.psoftg1.shared.IDGenerators.IDGenerator;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2

public class BookServiceImpl implements BookService {

	private final IDGenerator idGenerator;
	private final EventRepository eventRepository;
	private final EventServiceProducer eventServiceProducer;

	public BookServiceImpl(IDGenerator idGenerator, EventRepository eventRepository, EventServiceProducer eventServiceProducer) {

		this.idGenerator = idGenerator;
		this.eventRepository = eventRepository;
		this.eventServiceProducer = eventServiceProducer;
	}
	@Override
	public BookDTO create(Book book) throws Exception {
		if (eventRepository.bookExists(book.getIsbn())) {
			throw new Exception("Book with isbn " + book.getIsbn() + " already exists!");
		}

		book.setIsbn(StringUtils.isEmpty(book.getIsbn()) ?
				idGenerator.generateID() : book.getIsbn());

		if (book.getID() == null)
			book = new Book(book.getIsbn(), book.getTitle(), book.getDescription(), book.getGenre(),book.getAuthors(),null);

		List<String> authorIds = book.getAuthors().stream()
				.map(Author::getAuthorNumber)
				.toList();

		final CreateBookEvent eventMsg = new CreateBookEvent(book.getIsbn(), book.getTitle(), book.getDescription(), book.getGenre().getGenre(),authorIds,null);
		final Event event = new Event(EventType.BOOK_CREATE, eventMsg);

		eventServiceProducer.send(event, ApplicationType.BOOK);
		log.info("Create Book: " + eventMsg);


		return book.toDTO();
	}

	@Override
	public BookDTO updateByISBN(String isbn, Book book) throws Exception {

		if (!eventRepository.bookExists(isbn)) {
			throw new Exception("Book with isbn " + isbn + " not found!");
		}

		book.setIsbn(isbn);

		List<String> authorIds = book.getAuthors().stream()
				.map(Author::getAuthorNumber)
				.toList();

		final UpdateBookEvent eventMsg = new UpdateBookEvent(book.getIsbn(), book.getTitle(), book.getDescription(), book.getGenre().getGenre(),authorIds,null);
		final Event event = new Event(EventType.BOOK_UPDATE, eventMsg);

		eventServiceProducer.send(event, ApplicationType.BOOK, ApplicationType.LENDING, ApplicationType.USER);
		log.info("Update Book: " + eventMsg);

		return book.toDTO();
	}

	@Override
	public void deleteByISBN(String isbn) throws Exception {
		if (!eventRepository.bookExists(isbn)) {
			throw new Exception("Book with isbn " + isbn + " not found!");
		}

		final DeleteBookEvent eventMsg = new DeleteBookEvent(isbn);
		final Event event = new Event(EventType.BOOK_DELETE, eventMsg);
		eventServiceProducer.send(event, ApplicationType.BOOK, ApplicationType.LENDING, ApplicationType.USER);
		log.info("Delete Book: " + eventMsg);
	}
}
