package com.vamsi.soap.webservices.soap_course_management.soap;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


//Enable Spring Web Services
@EnableWs
//Spring Configuration
@Configuration
public class WebServiceConfig {
	//MessageDispatcherServlet
	// ApplicationContext
	//url -> /ws/*
	
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcherServlet = 
				new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
	}
	
	//  /ws/courses.wsdl
	 // course-details.xsd
	
	@Bean(name="courses")
	public DefaultWsdl11Definition defaultWsdl11Definition
			(XsdSchema courseSchema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		//PortType - CoursePort
		definition.setPortTypeName("CoursePort");
	    //NameSpace - http;//vamsi.com/courses
		definition.setTargetNamespace("http://vamsi.com/courses");
		// /ws
		definition.setLocationUri("/ws");
		//schema
		definition.setSchema(courseSchema);
		return definition;
	}
	
	
	@Bean
	public XsdSchema coursesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
	}
}
