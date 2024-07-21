package com.vamsi.soap.webservices.soap_course_management.soap;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.vamsi.courses.CourseDetails;
import com.vamsi.courses.DeleteCourseDetailsRequest;
import com.vamsi.courses.DeleteCourseDetailsResponse;
import com.vamsi.courses.GetAllCourseDetailsRequest;
import com.vamsi.courses.GetAllCourseDetailsResponse;
import com.vamsi.courses.GetCourseDetailsRequest;
import com.vamsi.courses.GetCourseDetailsResponse;
import com.vamsi.soap.webservices.soap_course_management.soap.bean.Course;
import com.vamsi.soap.webservices.soap_course_management.soap.exception.CourseNotFoundException;
import com.vamsi.soap.webservices.soap_course_management.soap.service.CourseDetailsService;
import com.vamsi.soap.webservices.soap_course_management.soap.service.CourseDetailsService.Status;


@Endpoint
public class CourseDetailsEndpoint {
	
	@Autowired
	CourseDetailsService service;
	
//	GetCourseDetailsRequest
//	GetCourseDetailsResponse
	
	@PayloadRoot(namespace="http://vamsi.com/courses", 
			localPart="GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest
		(@RequestPayload GetCourseDetailsRequest request) {
		
		Course course = service.findById(request.getId());
		
		if(course==null) {
			throw new CourseNotFoundException("Invalid Course Id " + request.getId());
		}
		return mapCourseDetails(course);
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		
		response.setCourseDetails(mapCourse(course));
		
		return response;
	}
	
	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for(Course course:courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}		
		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();
		
		courseDetails.setId(course.getId());
		
		courseDetails.setName(course.getName());
		
		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}

	@PayloadRoot(namespace="http://vamsi.com/courses", 
			localPart="GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processCourseDetailsRequest
		(@RequestPayload GetAllCourseDetailsRequest request) {
		
		List<Course> courses = service.findAll();
		
		return mapAllCourseDetails(courses);
	}
	@PayloadRoot(namespace="http://vamsi.com/courses", 
			localPart="DeleteCourseDetailsRequest")
	@ResponsePayload
	public DeleteCourseDetailsResponse deleteCourseDetailsRequest
		(@RequestPayload DeleteCourseDetailsRequest request) {
		
		Status status = service.deleteById(request.getId());
		
		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		response.setStatus(mapStatus(status));
		return response;
	}

	private com.vamsi.courses.Status mapStatus(Status status) {
		// TODO Auto-generated method stub
		if(status==Status.FAILURE)
			return com.vamsi.courses.Status.FAILURE;
		return com.vamsi.courses.Status.SUCCESS;
	}
}
