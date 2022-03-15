package emergon.controller;

import emergon.entity.Author;
import emergon.entity.Book;
import emergon.repository.AuthorRepo;
import emergon.repository.BookRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepo bookRepo;
    
     
    @Autowired
    private AuthorRepo authorRepo;

    
    //---------------------------------------------------------------------------------------------
    // All books @GetMapping("/books")
    @GetMapping
    public List<Book> findAll() {
        return bookRepo.findAll();
    }
    //---------------------------------------------------------------------------------------------
    
    
    //---------------------------------------------------------------------------------------------
    // Get book by id: @GetMapping("/books/{id}")
    // Save Book @PostMapping("/books")
//    @PostMapping
//    public ResponseEntity<Book> create(@RequestBody Book book){
//        book = bookRepo.save(book);
//        return ResponseEntity.status(HttpStatus.CREATED).body(book);
//    }
    //---------------------------------------------------------------------------------------------

    
    //---------------------------------------------------------------------------------------------
    // Σώζουμε ένα βιβλίο σε ένα συγκεκριμένο author.
    // Save a Book to a specific Author
    @PostMapping("/authors/{authorId}")
    // @RequestBody Book book: Μετατροπή από JSON σε Object
    public ResponseEntity<Book> createBookByAuthor(@RequestBody Book book,
            @PathVariable int authorId) {
        
        // Βρίσκουμε τον author
        Author author = authorRepo.findById(authorId).get();
        
        // Συσχετίζουμε τον auhor στο βιβλίο
        book.setAuthor(author);
        
        //Σώζουμε
        book = bookRepo.save(book);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }
    //---------------------------------------------------------------------------------------------
    
    
    //---------------------------------------------------------------------------------------------
    // Βασικό παράδειγμα
    //---------------------------------------------------------------------------------------------
    // Get Books by Author id  - ok
    @GetMapping("/authors/{authorId}")
    // @PathVariable(value = "authorId"): αφού πάρουμε το id, το βάζουμε στ μία μεταβλητή authorId.
    public List<Book> getBooksByAuthor(@PathVariable(value = "authorId") int authorId) {
        
        // Στο BookRepo.
        // Επειδή το SpringBoot δεν έχει φυσικά find(Book)ByAuthor, θα φτiάξω εγώ στο Repo
        // Επιστρέφει μία λίστα από Book.
        List<Book> books = bookRepo.findByAuthorId(authorId); //
        return books;
    }
    //---------------------------------------------------------------------------------------------
    
    
    //---------------------------------------------------------------------------------------------
    // Update a Book of an Author id @PutMapping("/authors/{authorId}/books/{book_id}")
    @PutMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Book> updateBook(@RequestBody Book bookDetails,
            @PathVariable int bookId,
            @PathVariable int authorId) {
        
        //Αν υπάρχει αυτός ο Author, authorExists=true        
        boolean authorExists = authorRepo.existsById(authorId);
        
        // authorExists=true
        if (!authorExists) {
            //not Found: 404
            return ResponseEntity.notFound().build();
        }
        
        //Βρες το βιβλίο.
        Book book = bookRepo.findById(bookId).get();
        // Κάνε UpDate.
        book.setTitle(bookDetails.getTitle());
        // Αποθηκευσέ το στην βάση.
        book = bookRepo.save(book);        
        // Στείλε ΟΚ.
        return ResponseEntity.status(HttpStatus.OK).body(book);

    }
    //---------------------------------------------------------------------------------------------
    
    
    //---------------------------------------------------------------------------------------------
    // Delete a Book from an Author id @DeleteMapping("/authors/{authorId}/books/{book_id}")
    @DeleteMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<String> deleteBookOfAuthor(//<String>:Book deleted
            @PathVariable int bookId,
            @PathVariable int authorId){
        
        // Κάτι καινούργιο:
        // find book with id=bookId and author_id=authorId
        Optional<Book> optionalBook = bookRepo.findByIdAndAuthorId(bookId, authorId);
        
        // If book !exists 
        //isPresent(): Το παρέχει η Optional.
        if(!optionalBook.isPresent()){
            // return NOT_FOUND
            return ResponseEntity.notFound().build();
        }else{//else delete book
            // Αλλιώς διαγραψέ το.
            bookRepo.delete(optionalBook.get());
            
            //ΟΚ.
            return ResponseEntity.ok("Book deleted");
        }
    }
    //---------------------------------------------------------------------------------------------
}

