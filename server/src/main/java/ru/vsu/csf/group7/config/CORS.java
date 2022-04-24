//package ru.vsu.csf.group7.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//public class CORS {
//    @Configuration(proxyBeanMethods = false)
//    public class MyCorsConfiguration {
//
//        @Bean
//        public WebMvcConfigurer corsConfigurer() {
//            return new WebMvcConfigurer() {
//
//                @Override
//                public void addCorsMappings(CorsRegistry registry) {
//                    registry.addMapping("/api/**");
//                }
//            };
//        }
//
//    }
//}
