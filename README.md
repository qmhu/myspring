# myspring

a simple container framework implements by java refer to spring.


myspring provide the following functions:

IOC

1.configuration ApplicationContext just like Spring does

    AnnotationApplicationContext applicationContext = new AnnotationApplicationContext("content-test.xml");
    Teacher teacher = (Teacher) applicationContext.getBean("teacher");
    System.out.println(teacher.toString());
    
2.support configure bean in XML file just like Spring does

    <bean id="teacher" class="my.bean.Teacher">
        <property id="id" value="123"/>
        <property id="name" value="xiaowang"/>
    </bean>
    
    <bean id="student" class="my.bean.Student">
        <property id="id" value="333"/>
        <property id="name" value="student name"/>
    </bean>
    
    <component-scan base-package="my" />

3.provide annotation like @Service @Autowired just like Spring does

4.provide new annotation @Field to inject POJO value

    @Field(value = "123")
    private int id;
    
    @Field(value = "countryname")
    private String name;
