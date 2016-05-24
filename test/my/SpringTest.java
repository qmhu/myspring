package my;

import my.bean.AnnotationApplicationContext;
import my.bean.Teacher;

/**
 * Created by I311862 on 2016/5/15.
 */
public class SpringTest {

    public static void main(String[] args){
        AnnotationApplicationContext applicationContext = new AnnotationApplicationContext("content-test.xml");
        Teacher teacher = (Teacher) applicationContext.getBean("teacher");
        System.out.println(teacher.toString());
    }
}
