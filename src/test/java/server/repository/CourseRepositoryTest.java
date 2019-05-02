package server.repository;

import domain.Course;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

public class CourseRepositoryTest {

    private CourseRepository courseRepository = new CourseRepository();

    @Test
    public void getAll() {
        List<Course> courses = courseRepository.getAll();
        Assert.assertNotEquals(courses.size(), null);
    }

    @Test
    public void save() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);

        String destination = new String(array, Charset.forName("UTF-8"));
        String date = new String(array, Charset.forName("UTF-8"));
        Course course = new Course(destination, date);
        Assert.assertEquals(courseRepository.create(course), course);
    }
}