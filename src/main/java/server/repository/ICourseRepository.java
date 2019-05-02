package server.repository;

import domain.Course;

public interface ICourseRepository extends ICrudRepository<Integer, Course> {
    Course findByDestinationDate(String destination, String date);
}
