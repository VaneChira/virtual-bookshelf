package com.project.bookstore;

import com.project.bookstore.model.*;
import com.project.bookstore.repository.BookProgressRepository;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BooksTests extends BaseTest {

	public static final long PROGRESS_PAGE = 120L;
	private final static Long USER_ID = 1L;
	private final static Long BOOK_ID = 1L;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	BookProgressRepository bookProgressRepository;
	@Autowired
	UserRepository userRepository;

	@Test
	void when_insertNewBook_then_countIncreases() {
		Book testBook = new Book();
		int countAll = bookRepository.findAll().size();
		Book addedBook = bookRepository.save(testBook);

		int countAfterAdd = bookRepository.findAll().size();
		assert (countAll + 1 == countAfterAdd);

		bookRepository.delete(addedBook);
		int countAfterDelete = bookRepository.findAll().size();
		assert (countAll == countAfterDelete);
	}


	@Test
	void when_addingBookToCurrentlyReading_then_accessIt() { //integration test
		BookProgressKey bookProgressKey = new BookProgressKey(USER_ID, BOOK_ID);

		User user = userRepository.findById(USER_ID).get();
		Book book = bookRepository.findById(BOOK_ID).get();

		BookProgress bookProgress = new BookProgress(bookProgressKey,
				user,
				book,
				PROGRESS_PAGE,
				BookStateEnum.fromEnumToInt(BookStateEnum.CURRENTLY_READING));

		Long preInsertionCount = bookProgressRepository.count();
		BookProgress savedBookProgress = bookProgressRepository.save(bookProgress);

		Long postInsertionCount = bookProgressRepository.count();
		assert (preInsertionCount == postInsertionCount - 1);


		assert (savedBookProgress.getProgressPage() == PROGRESS_PAGE);
		assert (savedBookProgress.getBookState() == BookStateEnum.fromEnumToInt(BookStateEnum.CURRENTLY_READING));
		assert (savedBookProgress.getBook() == book);
		assert (savedBookProgress.getUser() == user);

		bookProgressRepository.delete(savedBookProgress);

	}


}
