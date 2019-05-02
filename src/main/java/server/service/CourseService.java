package server.service;

import domain.Course;
import server.repository.CourseRepository;
import java.util.List;

public class CourseService {

    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAll() {
        return courseRepository.getAll();
    }
}
