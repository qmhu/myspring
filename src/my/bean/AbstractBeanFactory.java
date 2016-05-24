package my.bean;

import my.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by I311862 on 2016/5/21.
 */
public class AbstractBeanFactory implements BeanFactory {

    private HashMap<String,BeanDefinition> beanDefinitionHashMap = new HashMap<>();

    private HashMap<String,Object> beanCache = new HashMap<>();

    public void registerBeanDefinition(BeanDefinition beanDefinition){
        beanDefinitionHashMap.put(beanDefinition.getId(), beanDefinition);
    }

    public Object getBean(String name) {
        Object bean = beanCache.get(name);
        if (bean != null) {
            return bean;
        }

        BeanDefinition beanDefinition = beanDefinitionHashMap.get(name);
        String beanClass = beanDefinition.getBeanClass();
        try {
            Class className = Class.forName(beanClass);
            bean = className.newInstance();
            this.applyPropertyValues(bean, beanDefinition);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bean != null){
            beanCache.put(name, bean);
        }

        return bean;
    }

    protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {

    }
}
