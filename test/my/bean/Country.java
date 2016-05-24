package my.bean;

import my.annotation.Autowired;
import my.annotation.Field;
import my.annotation.Service;

/**
 * Created by I311862 on 2016/5/15.
 */
@Service
public class Country {

    @Field(value = "123")
    private int id;

    @Field(value = "countryname")
    private String name;

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
