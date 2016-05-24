package my.bean;

import java.util.List;

/**
 * Created by I311862 on 2016/5/8.
 */
public class BeanDefinition {

    private String id;

    private String beanClass;

    private List<BeanProperty> beanProperties;

    private boolean isAnnotation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public List<BeanProperty> getBeanProperties() {
        return beanProperties;
    }

    public void setBeanProperties(List<BeanProperty> beanProperties) {
        this.beanProperties = beanProperties;
    }

    public boolean isAnnotation() {
        return isAnnotation;
    }

    public void setAnnotation(boolean annotation) {
        isAnnotation = annotation;
    }
}
