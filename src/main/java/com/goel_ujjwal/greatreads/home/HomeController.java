package com.goel_ujjwal.greatreads.home;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.goel_ujjwal.greatreads.user.BooksByUser;
import com.goel_ujjwal.greatreads.user.BooksByUserRepository;

// Controller class for managing:
// Login Page for unauthenticated users
// Home Page for authenticated users

@Controller
public class HomeController {
    
    private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";

    @Autowired
    BooksByUserRepository booksByUserRepository;


    @GetMapping("/")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        
        // user not logged in, so go to login page
        if(principal == null || principal.getAttribute("login") == null) {  
            return "index";
        }

        String userId = principal.getAttribute("login");
        
        // limiting to first 100 rows of books_by_userid table present in the Cassandra partition for the looged-in user
        Slice<BooksByUser> booksSlice = booksByUserRepository.findAllById(userId, CassandraPageRequest.of(0, 100));
        
        List<BooksByUser> booksByUser = booksSlice.getContent();
        booksByUser = booksByUser.stream().distinct().map(book -> {
            
            String coverImageUrl = "/images/no-book-cover.jpg";
            if (book.getCoverIds() != null && book.getCoverIds().size() > 0) {
                coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0) + "-M.jpg";
            }

            book.setCoverUrl(coverImageUrl);
            return book;
        }).collect(Collectors.toList());

        // sort booksByUser so that the following book order is shown in My Books: 
        // Currently Reading -> Finished -> Did Not Finish
        booksByUser.sort(Comparator.comparing(BooksByUser::getReadingStatus));
        
        model.addAttribute("books", booksByUser);
        return "home";
    }
}
