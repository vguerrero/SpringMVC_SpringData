package com.app.controllers;

import com.app.classes.Contact;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.context.annotation.Scope;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.validation.Valid;
import com.app.repository.ContactRepository;
import com.app.classes.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.ModelMap;
import org.springframework.transaction.annotation.Transactional;

//Rest Rules
/*Create -> Post
Read   -> Get
Update -> Put
Delete -> Delete*/


@Controller
//@SessionAttributes( { "contacts" })
@Scope("request")
@RequestMapping("/contact")
@Transactional
public class ContactController {
	
	private List<Contact> contacts = new ArrayList<Contact>();
	private ContactRepository repository;
	
	@Autowired
	public ContactController(ContactRepository contactrep){
		this.repository=contactrep;
	}
	
	//crear ---> post
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addContact(@Valid @ModelAttribute("contact")
	Contact contact, BindingResult result) {
		
		System.out.println("First Name:" + contact.getFirstname() );
		
		if (result.hasErrors()) {  
			System.out.println( "Error de Validacion!" );
			return "contact";
		}
		repository.save(contact);
		//contacts.add(contact);
		return "redirect:/contact";
		//return "contact";
	}
	
	@RequestMapping("")
	public ModelAndView index(Map<String, Object> map) {
		System.out.println("inicio " + contacts + " \n ");
		map.put("contact", new Contact());//inicializamos el modelo para el form
		this.getContacts();
		map.put("contacts", contacts); //inicializamos la lista de contacts
		//return "contact";
		return new ModelAndView("contact", "model", map);
	}
	
	//get contacts from db
	public void getContacts(){
		if(repository != null){
			this.contacts = repository.findAll();
		}
		else{
			System.out.println("el repo es nulo");
		}
	}

	@RequestMapping(value="/{lname}", method=RequestMethod.GET)
	public String getContactByName(@PathVariable String lname, ModelMap model){
		System.out.println("enter in getContactByName " + lname);
		if("".equals(lname) ){
			return "redirect:/contact";
		}
		else{
			
			List<Contact> list = repository.findByLastnameIgnoreCase(lname);
			if(list != null){
				System.out.println("list esta llena");
				if(list.size() > 0){
					//this.contacts = list;
					model.addAttribute("contact", new Contact());//inicializamos el modelo para el form
					model.addAttribute("contacts", list); //inicializamos la lista de contacts
				}
				
			}
			else
			System.out.println("No se encontraron coincidencias con:  "+ lname );
			
		}
		return "contact";
		
	}

	
	@RequestMapping(value="/byEmail/{emailadd}", method=RequestMethod.GET)
	public String getContactByEmailAddress(@PathVariable String emailadd, ModelMap model){
		
		if(!"".equals(emailadd)){
			System.out.println("get Contact by Email address " + emailadd);
			Contact contact = repository.findByEmail(emailadd);
			if(contact != null){
				
				System.out.println(contact.toString());
			}
			else
			System.out.println("No se encontro Contact con email: " + emailadd);
		}
		return "redirect:/contact";
	}
	
	/*@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	public String deleteContact(@RequestParam String id) {

		User existingUser = new User();
		existingUser.setUsername(username);
		
		return service.delete(existingUser);
	}*/
	
	//@RequestMapping(value="/delete/{contactId}", method=RequestMethod.DELETE)
	@RequestMapping(value="/delete/{contactId}", method=RequestMethod.GET) //for test, deberia ser delete
	public String deleteContact(@PathVariable("contactId") Long contactId) {
		repository.delete(contactId);
		System.out.println("the contact:  "+ contactId + " has been deleted" );
		
		return "redirect:/contact";
	}
	
	@RequestMapping(value="/NombreEndLike/{nombre}", method=RequestMethod.GET)
	public String getPrimerNombreEndLike(@PathVariable String nombre, ModelMap model){//buscarPorPrimerNombreEndLike
		System.out.println("getPrimerNombreEndLike:  "+ nombre );
		if(!"".equals(nombre)){
			this.contacts = repository.buscarPorPrimerNombreEndLike(nombre);
			if(contacts != null){
				
				model.addAttribute("contact", new Contact());//inicializamos el modelo para el form
				model.addAttribute("contacts", contacts); //inicializamos la lista de contacts
				return "contact";
			}
			else
			System.out.println("No se encontro Contact con nombre like end: " + nombre);
		}
		return "redirect:/contact";
	}
}