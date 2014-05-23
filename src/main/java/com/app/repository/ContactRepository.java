package  com.app.repository;
 
import com.app.classes.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import java.util.List;
 
public interface ContactRepository extends JpaRepository<Contact, Long>{
		//QueryDslPredicateExecutor<Contact> {
 
	//Contact findByContactFirstname(String firstname);
	List<Contact> findByLastname(String lastName);
}