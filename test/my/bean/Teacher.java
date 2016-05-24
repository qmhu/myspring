package my.bean;

import my.annotation.Autowired;

/**
 * Created by I311862 on 2016/5/15.
 */
public class Teacher {

    private String name;

    private String id;

    @Autowired(name = "student")
    private Student student;

    @Autowired(name = "country")
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", student=" + student +
                ", country=" + country +
                '}';
    }
}
