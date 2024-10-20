

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter; 
import java.io.PrintWriter;
/** Represents a course information decription.
*/

class Course {
  private int capacity;
  private int class_time;//classTime
  private char course_name;//courseName

/** Creates a course with specified capacity, class time, name.
 * @param capacity Course capacity.
 * @param class_time Course class time.
 * @param course_name Course name.
*/

  public Course(int capacity, int class_time, char course_name) {
    this.capacity = capacity;
    this.class_time = class_time;
    this.course_name = course_name;
  }

/** Gets the course capacity.
 * @return An integer representing the course capacity.
*/

  public int getCapacity() {
    return capacity;
  }

/** Gets the course class_time.
 * @return An integer representing the course time.
*/

  public int getclass_time() {
    return class_time;
  }

/** Gets the course name.
 * @return A character representing the course name.
*/

  public char getcourse_name() {
    return course_name;
  }

/** Sets the course capacity.
 * @param capactity An integer containing the capacity.
*/

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }


  @Override
  public String toString() {
    return "Course{" +
                "capacity=" + capacity +
                ", classTimings=" + class_time +
                ", course_name='" + course_name +
                '}';
  }

}

class Student {
  private int id;
  private char[] preferences;
  private ArrayList<String> my_courses;
  private ArrayList<Integer> my_timings;
  private int satisfaction;

  public Student(int id, char[] preferences) {
    this.id = id;
    this.preferences = preferences;
    this.my_courses = new ArrayList<String>();
    this.my_timings = new ArrayList<Integer>();
    this.satisfaction = 0;
  }

/**
* Returns the Id of a student. 
* This method returns the id of a particular student when this method is called.
*
* @return  integer id
*/

  public int getId() {
    return id;
  }

  public char[] getPreferences() {
    return preferences;
  }

  public ArrayList<String> getCourses() {
    return my_courses;
  }

  public ArrayList<Integer> getTimings() {
    return my_timings;
  }

  public int getSatisfaction() {
    return satisfaction;
  }

  /** 
* The courses argument must be an array-list of strings which represents the courses.
* This method sets the list of registerd courses of a particular student. 
*
* @param  courses  array-list of strings which represents the courses.
*/

  public void setCourses(ArrayList<String> courses) {
    this.my_courses = courses;
  }

  public void setTimings(ArrayList<Integer> time) {
    this.my_timings = time;
  }

  public void setSatisfaction(int satisfaction) {
    this.satisfaction = satisfaction;
  }
  
  
  @Override
  public String toString() {
    return "student{" +
                "id=" + id +
                ", pref=" + String.valueOf(preferences) +
                '}';
  }

}

interface main {
  void readCourseInfo(String read);
  void readCoursePrefs(String prefs);
  void assignCourses(String conflicts,String errors);
  void writeRegResults(String write);
}

public class Driver implements main {
  public Course[] courses;
  public Student[] students;

  public static void main(String[] args) {
    if (args.length != 7 || args[0].equals("${arg0}") || args[1].equals("${arg1}") || args[2].equals("${arg2}")
				|| args[3].equals("${arg3}") || args[4].equals("${arg4}") || args[5].equals("${arg5}") || args[6].equals("${arg6}")) {

			System.err.println("Error: Incorrect number of arguments. Program accepts 7 arguments.");
			System.exit(0);
      
		}
    
    Driver driver = new Driver();
    driver.readCourseInfo(args[1]);
    driver.readCoursePrefs(args[0]);
    driver.assignCourses(args[3],args[4]);
    driver.writeRegResults(args[2]);
    LectureHall lh=new LectureHall();
    try{
      FileWriter fw = new FileWriter(args[6], false);
      PrintWriter pw = new PrintWriter(fw, false);
      pw.flush();
      pw.close();
      fw.close();
      }
      catch(Exception exception){
          System.out.println("Exception");         
      }
    lh.readinputFile(args[5]);
    String CourseHallAssignedOutput = args[6];
    
    
  }

  public void readCourseInfo(String CourseInfo_args) {
    courses = new Course[9];
    try{
      Scanner s = new Scanner(new File(CourseInfo_args));
      for (int i = 0; i < 9; i++) {
        String line = s.nextLine();
        String[] parts = line.split(":");
        char course_name = parts[0].charAt(0);
        int capacity = Integer.parseInt(parts[1]);
        int class_time = Integer.parseInt(parts[2]);
        courses[i] = new Course(capacity, class_time, course_name);
      }
      s.close();
    } 
    catch (FileNotFoundException e) {
      System.err.println("File not found " + e);
      System.exit(0);
    }
    finally{}
  }

  public void readCoursePrefs(String coursePrefs_args) {
    students = new Student[1000];
    try {
      Scanner s = new Scanner(new File(coursePrefs_args));
      int j=0;
      while (s.hasNextLine()) {
        String line = s.nextLine();
        String[] parts = line.split(" ");
        int id = Integer.parseInt(parts[0]);
        char[] preferences = new char[9];
        for (int i = 0; i < 9; i++) {
          preferences[i] = parts[i + 1].charAt(0);
        }
        students[j] = new Student(id, preferences);
        j=j+1;
      }
      s.close();
    } 
    catch (FileNotFoundException e) {
      System.err.println("File not found " + e);
      System.exit(0);
    }
    finally{}
  }

  public void assignCourses(String reg_conflicts_args, String err_args) {
    String c = "";
    String d = "";
    for(Student student : students){
        if(student != null){
            for(int i =0; i < 9; i++){
                char course_name =  student.getPreferences()[i];
                int satisfactionRating = 9 - i;
                ArrayList<String> coursesreg = student.getCourses();
                if(coursesreg.size()<3){ 
                  for(Course course : courses){
                      if (course.getcourse_name() == course_name){
                          int capacityleft = course.getCapacity();
                          if(capacityleft > 0 ){
                              ArrayList<Integer> timings = student.getTimings();
                              Boolean flag = false;
                              int time = course.getclass_time();
                              for(int mytime : timings){
                                if(mytime== time){
                                  flag = true;
                                  int index = timings.indexOf(mytime);
                                  String courseconflict = coursesreg.get(index);
                                  c = c + "Student "+ student.getId() + " didnt get course "+ course_name + " due to the Time conflict with course " + courseconflict + "\n";
                                }
                              }
                              if(flag == false){
                                timings.add(time);
                                student.setTimings(timings);
                                String s = Character.toString(course_name);
                                coursesreg.add(s);
                                student.setCourses(coursesreg);
                                student.setSatisfaction(student.getSatisfaction()+satisfactionRating);
                                course.setCapacity(capacityleft-1);
                              }
                          }
                          else{
                            d= d + "Student " + student.getId() + " didnt get course "+ course_name + " due to the course capacity" + "\n";
                          } 
                      }
                  }
                }
                Results display = new Results();
                display.write_regconflicts(c,reg_conflicts_args);
                display.write_error(d,err_args);
            }
        }
    }   
  }

  public void writeRegResults(String reg_results_args) {
    try {
      int number_of_students = 0;
      java.io.PrintWriter writer = new java.io.PrintWriter(reg_results_args);
      float totalSatisfactionRating = 0;
      for(Student student : students) {
        if(student!=null){
          float f = student.getSatisfaction();
          writer.printf(student.getId() + ": " + student.getCourses() + " :: SatisfactionRating=%.2f ", f/3 );
          writer.printf("\n");
          totalSatisfactionRating += f/3;
          number_of_students+=1;
        }
      }
      writer.printf("Average SatisfactionRating=%.2f ", totalSatisfactionRating/number_of_students);
      writer.close();
    } 
    catch (FileNotFoundException e) {
      System.err.println("File not found " + e);
      System.exit(0);
    }
    finally{}
  }
  
}