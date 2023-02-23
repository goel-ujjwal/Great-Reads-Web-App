package com.goel_ujjwal.greatreads.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.goel_ujjwal.greatreads.userbooks.UserBooks;
import com.goel_ujjwal.greatreads.userbooks.UserBooksPrimaryKey;
import com.goel_ujjwal.greatreads.userbooks.UserBooksRepository;

// Controller class for managing 1 page per book 

@Controller
public class BookController {

    private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserBooksRepository userBooksRepository;

    
    @GetMapping(value = "/books/{bookId}")
    public String getBook(@PathVariable String bookId, Model model, @AuthenticationPrincipal OAuth2User principal) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);

        // when bookId is valid
        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();

            String coverImageUrl = "/images/no-book-cover.jpg";
            if(book.getCoverIds() != null && book.getCoverIds().size() > 0) {
                coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0) + "-L.jpg";  
            }

            model.addAttribute("coverImage", coverImageUrl);
            model.addAttribute("book", book);

            // when a user is logged in 
            if(principal != null && principal.getAttribute("login") != null) {
                
                String userId = principal.getAttribute("login");
                model.addAttribute("loginId", userId);

                UserBooksPrimaryKey key = new UserBooksPrimaryKey();
                key.setUserId(userId);
                key.setBookId(bookId);

                Optional<UserBooks> userBooks = userBooksRepository.findById(key);
                
                if(userBooks.isPresent()) {
                    // to pre-populate the /addUserBook form on book.html page
                    model.addAttribute("userBooks", userBooks.get());
                }
                else {
                    // send empty object if the logged-in user has never interacted with the book
                    model.addAttribute("userBooks", new UserBooks());
                }
            }
            return "book";
        }
        else {
            return "book-not-found";
        }
    }
    
}
