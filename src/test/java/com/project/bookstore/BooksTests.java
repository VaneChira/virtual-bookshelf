package com.project.bookstore;

import com.project.bookstore.model.*;
import com.project.bookstore.repository.BookProgressRepository;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.FeedbackRepository;
import com.project.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.LocalDate;

class BooksTests extends BaseTest {

	public static final long PROGRESS_PAGE = 120L;
	private final static Long USER_ID = 1L;
	private final static Long BOOK_ID = 1L;
	private final static Integer RATING = 5;
	private final static String COMMENT = "Nice book";
	private final static LocalDate LOCAL_DATE = LocalDate.ofYearDay(2021, 6);


	@Autowired
	BookRepository bookRepository;
	@Autowired
	BookProgressRepository bookProgressRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	FeedbackRepository feedbackRepository;

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
	void when_updateBookTitle_then_accessIt(){
		Book book = bookRepository.findById(BOOK_ID).get();
		String originalTitle = book.getBookTitle();
		book.setBookTitle("new Title");
		Book updatedBook = bookRepository.save(book);
		assert (!updatedBook.getBookTitle().equals(originalTitle));

		//revert changes
		updatedBook.setBookTitle("Origin");
		bookRepository.save(updatedBook);
	}



	@Test
	void when_givingFeedback_ToABook_byId(){
		FeedbackKey feedbackKey = new FeedbackKey(USER_ID, BOOK_ID);

		User user = userRepository.findById(USER_ID).get();
		Book book = bookRepository.findById(BOOK_ID).get();

		Feedback feedback = new Feedback(feedbackKey,
				user,
				book,
				RATING,
				COMMENT,
				LOCAL_DATE);

		int preFeedbackCount = feedbackRepository.getFeedbacksByBook(BOOK_ID).size();
		Feedback savedFeedback = feedbackRepository.save(feedback);

		int postFeedbackCount = feedbackRepository.getFeedbacksByBook(BOOK_ID).size();
		try{
			assert (preFeedbackCount == postFeedbackCount - 1);
			assert (savedFeedback.getRating().equals(RATING));
			assert (savedFeedback.getComment().equals(COMMENT));
			assert (savedFeedback.getDate().equals(LOCAL_DATE));
		}catch (AssertionError assertionError){
			feedbackRepository.delete(savedFeedback);
		}
		finally {
			if(feedbackRepository.findById(savedFeedback.getFeedbackKey()).isPresent()){
				feedbackRepository.delete(savedFeedback);
			}
		}
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

		long preInsertionCount = bookProgressRepository.count();
		BookProgress savedBookProgress = bookProgressRepository.save(bookProgress);

		long postInsertionCount = bookProgressRepository.count();
		try {
			assert (preInsertionCount == postInsertionCount - 1);
			assert (savedBookProgress.getProgressPage() == PROGRESS_PAGE);
			assert (savedBookProgress.getBookState() == BookStateEnum.fromEnumToInt(BookStateEnum.CURRENTLY_READING));
			assert (savedBookProgress.getBook().equals(book));
			assert (savedBookProgress.getUser().equals(user));
		}catch(AssertionError assertionError) {
			bookProgressRepository.delete(savedBookProgress);
		}
		finally {
			if (bookProgressRepository.findById(savedBookProgress.getBookProgressKey()).isPresent()){ // daca nu s-a sters in catch => toate assert-urile au mers
				bookProgressRepository.delete(savedBookProgress);
			}
		}
	}


	@Test
	void when_addingBookToWishlist_then_accessIt(){
		BookProgressKey bookProgressKey = new BookProgressKey(USER_ID,BOOK_ID);

		User user = userRepository.findById(USER_ID).get();
		Book book = bookRepository.findById(BOOK_ID).get();

		BookProgress bookProgress = new BookProgress(bookProgressKey,
				user,
				book,
				PROGRESS_PAGE,
				BookStateEnum.fromEnumToInt(BookStateEnum.WISHLIST));

		long preInsertionCount = bookProgressRepository.count();
		BookProgress savedBookProgress = bookProgressRepository.save(bookProgress);

		long postInsertionCount = bookProgressRepository.count();
		try {
			assert (preInsertionCount == postInsertionCount - 1);
			assert (savedBookProgress.getProgressPage() == PROGRESS_PAGE);
			assert (savedBookProgress.getBookState() == BookStateEnum.fromEnumToInt(BookStateEnum.WISHLIST));
			assert (savedBookProgress.getBook().equals(book));
			assert (savedBookProgress.getUser().equals(user));
		}catch(AssertionError assertionError) {
			bookProgressRepository.delete(savedBookProgress);
		}
		finally {
			if (bookProgressRepository.findById(savedBookProgress.getBookProgressKey()).isPresent()){ // daca nu s-a sters in catch => toate assert-urile au mers
				bookProgressRepository.delete(savedBookProgress);
			}
		}

	}
}
