package be.fp.distriWebApp.webapp.managedBean;

import javax.annotation.ManagedBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;	


@Controller
@ManagedBean
@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
public class ApplicationMb {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationMb.class);
	
	public void test(){
		System.out.println("test");
	}
	
}
