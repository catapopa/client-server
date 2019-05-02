package server.repository;

import utils.JdbcUtil;
import domain.Course;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository implements ICourseRepository {

    private JdbcUtil jdbcUtil;
    private static final Logger LOGGER = LogManager.getLogger(User.class);

    public CourseRepository() {
        jdbcUtil = new JdbcUtil();
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        String selectString = "select * from Courses";

        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(selectString)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String destination = resultSet.getString("destination");
                    String date = resultSet.getString("date");
                    int seats = resultSet.getInt("seats");
                    Course course = new Course(id, destination, date, seats);
                    courses.add(course);
                }
                return courses;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Course create(Course course) {
        String insertString = "insert into Courses (destination,date) values(?,?);";

        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(insertString)) {
            stm.setString(1, course.getDestination());
            stm.setString(2, course.getDate());
            stm.executeUpdate();
            LOGGER.debug("course save - successful");
            return course;
        } catch (SQLException e) {
            LOGGER.debug("course save - fail");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Course delete(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Course update(Course course, Integer id) {
        String updateString = "update Courses set destination = ?, date = ?, seats = ? where id = ?;";

        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(updateString)) {
            stm.setString(1, course.getDestination());
            stm.setString(2, course.getDate());
            stm.setInt(3, course.getSeats());
            stm.setInt(4, id);
            stm.executeUpdate();
            LOGGER.debug("course update - successful");
            return course;
        } catch (SQLException e) {
            LOGGER.debug("course update - fail");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Course findOne(Integer id) {
        String findString = "select * from Courses where id=?;";

        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(findString)) {
            stm.setInt(1, id);
            try (ResultSet result = stm.executeQuery()) {
                if (result.next()) {
                    String destination_found = result.getString("destination");
                    String date_found = result.getString("date");
                    int seats_found = result.getInt("seats");
                    Course found = new Course(id, destination_found, date_found, seats_found);
                    LOGGER.debug("course found");
                    return found;
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("course not found");
            e.printStackTrace();
        }
        return null;
    }

    public Course findByDestinationDate(String destination, String date) {
        String findString = "select * from Courses where destination=? and date=?;";

        try (PreparedStatement stm = jdbcUtil.getConnection().prepareStatement(findString)) {
            //stm.setInt(1, id);
            stm.setString(1, destination);
            stm.setString(2, date);
            try (ResultSet result = stm.executeQuery()) {
                if (result.next()) {
                    int seats_found = result.getInt("seats");
                    int id_found = result.getInt("id");
                    Course found = new Course(id_found, destination, date, seats_found);
                    LOGGER.debug("course found");
                    return found;
                }
            }
        } catch (SQLException e) {
            LOGGER.debug("course not found");
            e.printStackTrace();
        }
        return null;
    }

}
