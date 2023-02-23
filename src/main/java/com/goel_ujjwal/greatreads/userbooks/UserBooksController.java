package com.goel_ujjwal.greatreads.userbooks;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.goel_ujjwal.greatreads.book.Book;
import com.goel_ujjwal.greatreads.book.BookRepository;
import com.goel_ujjwal.greatreads.user.BooksByUser;
import com.goel_ujjwal.greatreads.user.BooksByUserRepository;

// Controller class for tracking interaction of logged-in user with different books

@Controller
public class UserBooksController {
    
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserBooksRepository userBooksRepository;

    @Autowired
    BooksByUserRepository booksByUserRepository;


    @PostMapping(value = "/addUserBook")
    public ModelAndView addBookforUser(@RequestBody MultiValueMap<String, String> formData, @AuthenticationPrincipal OAuth2User principal) {

        // if user is not logged-in
        if(principal == null || principal.getAttribute("login") == null) {
            return null;
        }

        String userId = principal.getAttribute("login");
        String bookId = formData.getFirst("bookId");
        int rating = Integer.parseInt(formData.getFirst("rating"));

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(!optionalBook.isPresent()) {
            return new ModelAndView("redirect:/");
        }
        Book book = optionalBook.get();
        

        // insert or update book_by_userid_and_bookid table
        UserBooks userBooks = new UserBooks();
        UserBooksPrimaryKey key = new UserBooksPrimaryKey();
        
        key.setUserId(userId);
        key.setBookId(bookId);

        userBooks.setKey(key);
        userBooks.setStartedDate(LocalDate.parse(formData.getFirst("startDate")));
        userBooks.setCompletedDate(LocalDate.parse(formData.getFirst("completedDate")));
        userBooks.setRating(rating);
        userBooks.setReadingStatus(formData.getFirst("readingStatus"));

        userBooksRepository.save(userBooks);


        // insert or update books_by_userid table
        BooksByUser booksByUser = new BooksByUser();

        booksByUser.setId(userId);
        booksByUser.setBookId(bookId);
        booksByUser.setBookName(book.getName());
        booksByUser.setAuthorNames(book.getAuthorNames());
        booksByUser.setCoverIds(book.getCoverIds());
        booksByUser.setReadingStatus(formData.getFirst("readingStatus"));
        booksByUser.setRating(rating);

        booksByUserRepository.save(booksByUser);

        // redirect to Home Page which will show the updated list of books associated with the logged-in user
        return new ModelAndView("redirect:/");
    }

}
