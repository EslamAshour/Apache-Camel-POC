package Basket.Basket;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class DistributeOrderDSL {
	
	public static void main(String[] args) throws Exception {
		
		// create DefaultCamelContext
		CamelContext context = new DefaultCamelContext();
		try {
			// add a route by creating an anonymous RouteBuilder instance
			context.addRoutes(new RouteBuilder() {
				//override the configure method to add a route from a direct URI DistributeOrderDSL to the system console
				@Override
				public void configure() throws Exception {
					from("direct:DistributeOrderDSL").split(xpath("//order[@product='soaps']/items")).to("stream:out");

					// .to("file:src/main/resources/order/");
				}
			});
			//start the context
			context.start();
			
			
			ProducerTemplate orderProducerTemplate = context.createProducerTemplate();
			InputStream orderInputStream = new FileInputStream(
					ClassLoader.getSystemClassLoader().getResource("order.xml").getFile());
			
			//Finally, we start the processing
			orderProducerTemplate.sendBody("direct:DistributeOrderDSL", orderInputStream);
		} finally {
			context.stop();
		}
	}
}
