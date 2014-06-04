package  com.app.repository;
 
import com.app.classes.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
 
public interface ContactRepository extends JpaRepository<Contact, Long>{
		//QueryDslPredicateExecutor<Contact> {
 
	//Contact findByContactFirstname(String firstname);
	List<Contact> findByLastnameIgnoreCase(String lastName);
	
	Contact findByEmail(String Email);
	
	@Query("select u from Contact u where u.firstname like %?1")
	List<Contact> buscarPorPrimerNombreEndLike(String name);
}