package my.bean;

/**
 * Created by I311862 on 2016/5/21.
 */
public abstract class AbstractApplicationContext implements ApplicationContext{

    private AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    public void refresh(){
        loadBeanDefinition(this.beanFactory);
    }

    protected abstract void loadBeanDefinition(AbstractBeanFactory beanFactory);

    public Object getBean(String name){
        return beanFactory.getBean(name);
    }

}
