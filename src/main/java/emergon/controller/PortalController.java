package emergon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalController {

	@RequestMapping(value="/index")
	public String indexPage() {
		return "index.html";
	}
        
        
        @RequestMapping(value="/authors")
	public String indexPage2() {
		return "authors.html";
	}
        
        @RequestMapping(value="/addauthor")
	public String addAuthor() {
		return "addAuthor.html";
	}        
        
        @RequestMapping(value="/updateAuthor/{id}")
	public String updateAuthor(@PathVariable(value = "id") int id) {
		return "updateAuthor.html";
	}
	
}
