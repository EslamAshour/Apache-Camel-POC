package BasketWithSpring.BasketWithSpring;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DistributeOrderXML {
	
	public static void main(String[] args) throws Exception {
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext("SpringRouteContext.xml");
		
		CamelContext camelContext = SpringCamelContext.springCamelContext(appContext, false);
		try {
			camelContext.start();
			ProducerTemplate orderProducerTemplate = camelContext.createProducerTemplate();
			InputStream orderInputStream = new FileInputStream(
					ClassLoader.getSystemClassLoader().getResource("order.xml").getFile());

			orderProducerTemplate.sendBody("direct:DistributeOrderXML", orderInputStream);
		} finally {
			camelContext.stop();
		}
	}
}
