package my.bean;

import my.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by I311862 on 2016/5/21.
 */
public class AutowireBeanFactory extends AbstractBeanFactory{

    @Override
    protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
        Field[] fields = bean.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);

            Autowired autowiredAn = field.getAnnotation(Autowired.class);
            my.annotation.Field myfieldAn = field.getAnnotation(my.annotation.Field.class);
            if (autowiredAn != null){
                field.set(bean, this.getBean(autowiredAn.name()));
            } else if (myfieldAn != null){
                Type type = field.getGenericType();
                if (type.getTypeName() == "int"){
                    field.set(bean, Integer.valueOf(myfieldAn.value()));
                }else {
                    field.set(bean, String.valueOf(myfieldAn.value()));
                }
            }
            else {
                for (BeanProperty beanProperty : beanDefinition.getBeanProperties()) {
                    if (field.getName().equals(beanProperty.getName())) {
                        if (beanProperty.getRefBean() != null) {
                            field.set(bean, this.getBean(beanProperty.getRefBean()));
                        } else {
                            field.set(bean, beanProperty.getValue());
                        }

                        break;
                    }
                }
            }
        }
    }
}
