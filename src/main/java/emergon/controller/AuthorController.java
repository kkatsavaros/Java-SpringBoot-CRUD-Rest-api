package emergon.controller;

import emergon.entity.Author;
import emergon.repository.AuthorRepo;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//@Controller
@RestController // https://www.baeldung.com/spring-controller-vs-restcontroller //Είναι controller
@RequestMapping("/api/authors") //που ακούει, οπότε όλες οι μέθοδοι θα το κληρονομήσουν.
public class AuthorController {
    
    @Autowired
    private AuthorRepo authorRepo;
    
    @Autowired
    private RestTemplate restTemplate;        
    
    
    //----------------------------------------------------------------------------------
    //findAll
    // @ResponseBody //https://www.baeldung.com/spring-request-response-body
    // @ResponseBody : Λέει στον controller να κάνει το Object σε JSON.
    
    // GetMapping: Ακούει σε ένα request το οποίο κληρονομεί από πάνω.
    //@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})    
    
    //@ResponseBody
    @GetMapping
    public List<Author> findAll(){   // Αυτή η μέθοδος επιστρέφει μία λίστα.
    
        return authorRepo.findAll(); // findAll() είναι στο JPA Repository.
        //return "main"; //To SpringFramework είναι view agnostic, επιστρέφει JSON και όχι JSP σελίδα.
    }
    //----------------------------------------------------------------------------------    
    
    
    //----------------------------------------------------------------------------------
    //findById
    // Object ResponseEntity του Spring Framework, το οποίο αγκαλιάζει το Entity μας που είναι ο Author.
    // @GetMapping(value = "/{id}") // το ίδιο είναι.
    // produces = {MediaType.APPLICATION_JSON_VALUE} : για να μην επιστρέφει XML, μόνο JSON.    
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Author> getAuthorById(@PathVariable(value = "id") int id){
        
        // Το φέρνουμε σαν authorRepo. Επιστρέφει Optional<Author>.
        Optional<Author> optionalAuthor = authorRepo.findById(id); // Είναι ένα κουτί.
        
        if(!optionalAuthor.isPresent()){
            // Αν πάρει id που δεν υπάρχει θα στείλει request 404.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
        // optionalAuthor.get() παίρνω το αντικείμενο που είναι μέσα 
        Author author = optionalAuthor.get(); 
        // στο κουτάκι μας και επιστρέφει Author και το βάζω σε μια  μεταβλητή author.
        
        // Φτιάχνω ένα αντικείμενο ResponseEntity
        ResponseEntity responseEntity = new ResponseEntity(author, HttpStatus.OK);
        
        return responseEntity;
    }
    //----------------------------------------------------------------------------------
    
    
    
    //----------------------------------------------------------------------------------
    //create save=add
    @PostMapping // Ακούει σε ένα POST request: /api/authors      με POST.
    //@RequestBody Author author // Θα λάβει ο controller ένα request , αυτό θα έχει ένα κυριως μέρος, 
    // το οποίο θα είναι σε JSON μορφή. Τότε το @RequestBody θα καταλάβει αυτό το αντικείμενο 
    // θα το πάρει 
    // θα το κάνει deserialize και θα το μετατρέψει σε ένα αντικείμενο της Java τύπου Author.
    // και μετά θα αποθηκεύσουμε τον Author.
    public Author create(@RequestBody Author author){ //Json->Java Object
        Author savedAuthor = authorRepo.save(author); //Θα αποθηκεύσει έναν author.
        return savedAuthor;
    }
    //----------------------------------------------------------------------------------
    
    
    
    
    //----------------------------------------------------------------------------------
    //update
    @PutMapping("/{id}")
    // ResponseEntity: Αγκαλιάζουμε το αντικείμενο που θέλουμε και μπορούμε να στείλουμε ένα 
    // πιο ωραία απάντηση. Μας δίνει περισσότερες λειτουργίες για το http πρωτόκολλο.
    
    // Σαν request θέλω να στείλω 
    // @PathVariable(value = "id"): που θα κάνει την αλλαγή.
    // @RequestBody Author author: Στο RequestBody θα βάλω τα καινούργια στοιχεία του author.
    public ResponseEntity<Author> update(
            @RequestBody Author author, 
            @PathVariable(value = "id") int id){
        
        //find author to update
        Author authorToUpdate = authorRepo.findById(id).get();
        
        // change author details
        // getName(): Το παίρνει από το: @RequestBody Author author.
        authorToUpdate.setName(author.getName()); 
        
        
        //save author to DB
        authorToUpdate = authorRepo.save(authorToUpdate); // add
        
        //return a response
        // ResponseEntity: το όνομα της κλάσης.
        // status: Θα στείλει 200άρι ή 200 κάτι. (201)
        return ResponseEntity.status(HttpStatus.CREATED).body(authorToUpdate);
    }
    //----------------------------------------------------------------------------------
    
    
    
    //----------------------------------------------------------------------------------
    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") int id){
        
        //find author to delete
        Author authorToDelete = authorRepo.findById(id).get();
        
        // Σβήστο
        authorRepo.delete(authorToDelete);
        
        //Επέστρεψε μήνυμα.
        String message = "Author with id:"+id+" successfully deleted!";
        
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    //----------------------------------------------------------------------------------
}