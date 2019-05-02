package server.controller;

import domain.Course;
import server.service.CourseService;
import utils.Response;

import java.util.List;

public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public Response getAll() {
        List<Course> courses = courseService.getAll();
        return new Response(Response.Type.GET_COURSES, courses);
    }
}
