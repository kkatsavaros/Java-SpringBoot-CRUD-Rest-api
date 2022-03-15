package emergon.repository;

import emergon.entity.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{
    
    // Είναι μία abstract μέθοδος. Φέρε μου την λίστα με τα βιβλία με βάση το author id.
    // Έτσι απλά, τίποτε άλλο.
    List<Book> findByAuthorId(int authorId);
    
    // Για το delete στον BookController.
    // Επιστρέφει βιβλίο.
    // findByIdAndAuthorId: Δεν υπάρχει πουθενά αυτή η μέθοδος
    // ,μάλλον είναι ερώτημα από μόνο του, με βάση την ονομασία.
    Optional<Book> findByIdAndAuthorId(int bookId, int authorId);
    
}
