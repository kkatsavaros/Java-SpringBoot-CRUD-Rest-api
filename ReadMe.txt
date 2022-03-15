Java-SpringBoot-CRUD-Rest-api


Database: restapi
tables: author, book

With that project i am using Spring Boot Framework to produce JSON Objects from database tables.

Then we can make CRUD Operations to Create, Update or Delete books and authors.

There is no front end in that project so the only way to test create, update and delete operations is using Postman.

For the CRUD operation i use JPARepository.



1. Spring Boot Initializer.
2. Netbeans Configuration - Application Properties.
3. Workbench - Δημιoυργία της βάσης δεδομένων db:restapi, table: author, book.


4. Αρχικά θα περάσω τα Entities από την βάση δεδομένων στο Netbeans.
5. Author Entity
6. AuthorRepo - Παρέχει το Spring Framework το JPARepository είναι ένα Interface το οποίο παρέχει όλες τις CRUD μεθόδους για ένα Entity.
7. AuthorController - findAll() : Βγάζει JSON με @ResponseBody και @Controller χωρίς @RestController.
8. AuthorController - findAll()
9. AuthorController - findById() - ResponseEntity.
10. update - PUT
11. delete() - DELETE


12. Entity: Book
13. BookRepo
14. BookController
15. findBookByAuthorId
16. createBookByAuthor - Σώζουμε ένα βιβλίο σε ένα συγκεκριμένο author.
17 Delete Book.



localhost links:
http://localhost:8080/api/authors   - .findAll()

http://localhost:8080/api/authors/3  - .findById()


http://localhost:8080/api/books/authors/1 - .findByAuthorId()

http://localhost:8080/api/books - .findAll() 




Katsavaros Konstantinos



