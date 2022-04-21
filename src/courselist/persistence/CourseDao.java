package courselist.persistence;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import courselist.Course;

public class CourseDao {
  private Connection connection;

  // initialize a new CourseDao with JDBC connection to the database
  public CourseDao(Connection connection) {
    this.connection = connection;
  }

  // retrive a course fron database by id
  public Course get(int id) {
    Course course = null;
    try {
      Statement statement = connection.createStatement();
      String query = "SELECT * FROM courses WHERE id = " + id;
      ResultSet rs = statement.executeQuery(query);
      // create a Course obj from the first row of ResultSet
      if (rs.next()) {
        course = makeCourse(id, rs);
      }
    } catch(SQLException ex) {
      throw new RuntimeException("Error getting course from database", ex);
    }
    return course;
  }

  private Course makeCourse(int id, ResultSet rs) throws SQLException {
    String courseNumber = rs.getString("course_number");
    String title = rs.getString("title");
    int credits = rs.getInt("credits");
    double difficulty = rs.getDouble("difficulty");
    Course course = new Course(courseNumber, title, credits);
    course.setDifficulty(difficulty);
    course.setId(id);
    return course;
  }
}
