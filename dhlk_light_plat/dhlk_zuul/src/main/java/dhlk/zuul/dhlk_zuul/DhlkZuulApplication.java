package dhlk.zuul.dhlk_zuul;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class DhlkZuulApplication {
	@Autowired
	private DiscoveryClient discoveryClient;
	@Value("${server.port}")
	private Integer port;
	/**
	 * 获取所有服务
	 */
	@RequestMapping("/services")
	public Object services() {
		return discoveryClient.getServices();
	}

	public static void main(String[] args) {
		SpringApplication.run(DhlkZuulApplication.class, args);
	}
//	@Bean
//	public ServletWebServerFactory servletContainer() {
//
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//
//			@Override
//			protected void postProcessContext(Context context) {
//
//				SecurityConstraint securityConstraint = new SecurityConstraint();
//				securityConstraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection = new SecurityCollection();
//				collection.addPattern("/*");
//				securityConstraint.addCollection(collection);
//				context.addConstraint(securityConstraint);
//			}
//		};
//		tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
//		return tomcat;
//	}
//
//
//	/**
//	 * 让我们的应用支持HTTP是个好想法，但是需要重定向到HTTPS，
//	 * 但是不能同时在application.properties中同时配置两个connector，
//	 * 所以要以编程的方式配置HTTP connector，然后重定向到HTTPS connector
//	 * @return Connector
//	 */
//	private Connector initiateHttpConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		connector.setScheme("http");
//		connector.setPort(18080); // http端口
//		connector.setSecure(false);
//		connector.setRedirectPort(port); // application.properties中配置的https端口
//		return connector;
//	}
}
