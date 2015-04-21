package com.miller.remotelogger;

import java.io.FileNotFoundException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

/**
 * This class will configure settings on the embedded TomCat instance
 * for things like HTTPS supports, etc.  
 * 
 * Values are loaded from the application.properties file.
 * @author Jonathan
 */
@Configuration
public class TomcatConfiguration {
	@Bean
	@Autowired
	public EmbeddedServletContainerCustomizer containerCustomizer(
		@Value("${keystore.file}") String keystoreFile,
		@Value("${keystore.password}") final String keystorePassword,
		@Value("${keystore.type}") final String keystoreType,
		@Value("${keystore.alias}") final String keystoreAlias,
		@Value("${tomcat.connection.port}") final int tomcatConnectionPort,
		@Value("${tomcat.connection.scheme}") final String tomcatConnectionScheme,
		@Value("${tomcat.connection.secured}") final boolean tomcatConnectionSecured,
		@Value("${tomcat.connection.sslenabled}") final boolean tomcatConnectionSSLEnabled) throws FileNotFoundException
	{
	    final String absoluteKeystoreFile = ResourceUtils.getFile(keystoreFile).getAbsolutePath();
	     return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				 TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
				    tomcat.addConnectorCustomizers(new TomcatConnectorCustomizer() {
						@Override
						public void customize(Connector connector) {
							  connector.setPort(tomcatConnectionPort);
			                  connector.setSecure(tomcatConnectionSecured);
			                  connector.setScheme(tomcatConnectionScheme);
			                  Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
			                  proto.setSSLEnabled(tomcatConnectionSSLEnabled);
			                  proto.setKeystoreFile(absoluteKeystoreFile);
			                  proto.setKeystorePass(keystorePassword);
			                  proto.setKeystoreType(keystoreType);
			                  proto.setKeyAlias(keystoreAlias);
						}
				});
			}
		};	   
	}
	
}
