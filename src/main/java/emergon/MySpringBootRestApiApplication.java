package emergon;

import emergon.entity.Demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MySpringBootRestApiApplication {
        
        @Bean
        public RestTemplate getRestTemplate(){
            return new RestTemplate();
        
        }

	public static void main(String[] args) {
		SpringApplication.run(MySpringBootRestApiApplication.class, args);
	}
        
        @RequestMapping(value="/getdata", method=RequestMethod.GET)
	public ResponseEntity<Object> getData() {
		Demo demo = new Demo();
		demo.setId("1");
		demo.setName("talk2Amareswaran");
		return new ResponseEntity<>(demo,HttpStatus.OK);
	}
	
	@RequestMapping(value="/postdata", method=RequestMethod.POST)
	public ResponseEntity<Object> postData(@RequestBody Demo demo) {
		System.out.println("demo id:"+demo.getId());
		System.out.println("demo name:"+demo.getName());
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}	

}
