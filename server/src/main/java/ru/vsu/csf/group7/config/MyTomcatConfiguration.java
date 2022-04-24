//package ru.vsu.csf.group7.security;
//
//import org.apache.catalina.connector.Connector;
//import org.apache.coyote.http11.Http11NioProtocol;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ResourceUtils;
//
//import java.io.IOException;
//import java.net.URL;
//
//@Configuration
//public class MyTomcatConfiguration {
//
//    @Bean
//    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> sslConnectorCustomizer() {
//        return (tomcat) -> tomcat.addAdditionalTomcatConnectors(createSslConnector());
//    }
//
//    private Connector createSslConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//        try {
//            URL keystore = ResourceUtils.getURL("keystore");
//            URL truststore = ResourceUtils.getURL("truststore");
//            connector.setScheme("https");
//            connector.setSecure(true);
//            connector.setPort(8443);
//            protocol.setSSLEnabled(true);
//            protocol.setKeystoreFile(keystore.toString());
//            protocol.setKeystorePass("changeit");
//            protocol.setTruststoreFile(truststore.toString());
//            protocol.setTruststorePass("changeit");
//            protocol.setKeyAlias("apitester");
//            return connector;
//        }
//        catch (IOException ex) {
//            throw new IllegalStateException("Fail to create ssl connector", ex);
//        }
//    }
//
//}
