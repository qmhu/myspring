package my.bean;

import my.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by I311862 on 2016/5/8.
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

    private BeanDefinationXMLLoader beanDefinationLoader;

    private List<BeanDefinition>  beanDefinations;

    private Map<String,Object> beanMap = new HashMap<String, Object>();

    private String configName;

    public AnnotationApplicationContext(String configName){
        super(new AutowireBeanFactory());
        this.configName = configName;
        refresh();
    }

    @Override
    protected void loadBeanDefinition(AbstractBeanFactory beanFactory) {
        beanDefinationLoader = new BeanDefinationXMLLoader();
        beanDefinationLoader.loadBeanDefinations(configName);

        for (BeanDefinition beanDefinition : beanDefinationLoader.getBeanDefinitions()){
            beanFactory.registerBeanDefinition(beanDefinition);
        }
    }


}
