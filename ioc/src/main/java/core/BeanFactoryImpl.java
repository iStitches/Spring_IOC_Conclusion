package core;

import bean.BeanDefinition;
import bean.ConstructArg;
import utils.BeanUtils;
import utils.ClassUtils;
import utils.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BeanFactoryImpl implements BeanFactory{
    //存储所有已经实例化的Bean对象
    private static final ConcurrentHashMap<String,Object> beanMap = new ConcurrentHashMap<>();

    //存储未实例化的BeanDefinition对象
    private static final ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    //存储容器中所有Bean对象的名称
    private static final Set<String> beanNameSet = Collections.synchronizedSet(new HashSet<>());


    @Override
    /**
     * 根据 name 获取到容器中的 Bean对象
     */
    public Object getBean(String name) throws Exception {
        //1.容器中存在直接返回
        if (beanMap.containsKey(name)){
            return beanMap.get(name);
        }
        //2.容器中不存在就进行对象初始化
        Object bean = createBean(beanDefinitionMap.get(name));

        //3. 对生成的Bean对象进行依赖注入
        if(bean != null){
             // 对象内部依赖注入
             populatebean(bean);
             // 生产完成的对象加入到容器中
             beanMap.put(name,bean);
        }
        return bean;
    }

    /**
     * 根据配置文件解析的内容实例化 BeanDefinition 对象
     * @param name
     * @param beanDefinition
     */
    protected void registerBean(String name, BeanDefinition beanDefinition){
         beanDefinitionMap.put(name,beanDefinition);
         beanNameSet.add(name);
    }

    /**
     * 实例化 Bean
     * @param beanDefinition
     * @return
     */
    private Object createBean(BeanDefinition beanDefinition){
        //1. 根据Bean对象名称获取字节码对象Class
        String beanName = beanDefinition.getClassName();
        Class clz = ClassUtils.loadClass(beanName);
        if(clz == null)
             throw new RuntimeException("不能够根据名称找到Bean对象...");

        //2. 根据BeanDefinition生成构造器所需参数
        List<ConstructArg> constructArgList = beanDefinition.getConstructArgs();
        try {
            // 如果构造器的参数列表不为空，要先获取对应参数的值、对应的构造方法
            if(constructArgList!=null && !constructArgList.isEmpty()){
               // 生成所有构造函数参数对应的Bean对象实体
               List<Object> objectList = new ArrayList<>();
               for(ConstructArg constructorArg:constructArgList){
                   if(constructorArg.getValue() != null){
                       objectList.add(constructorArg.getValue());
                   }
                   else
                       objectList.add(getBean(constructorArg.getRef()));
               }
               // 从对象实体的集合中生成所有对象的Class对象数组，采用 stream().map(xxxx)
               Class[] constructorArgTypes = objectList.stream().map(x->x.getClass()).collect(Collectors.toList()).toArray(new Class[objectList.size()]);
               // 根据参数Class[] 数组获取到对应格式的构造器
               Constructor constructor = clz.getConstructor(constructorArgTypes);
               return BeanUtils.instancedByCglib(clz,constructor,objectList.toArray());
            }

            //否则说明参数列表为空，直接生成
            else
                return BeanUtils.instancedByCglib(clz,null,null);
        } catch (Exception e) {
            return null;
        }
    }

    // 依赖注入
    private void populatebean(Object bean) throws Exception {
        Field[] fields = bean.getClass().getSuperclass().getDeclaredFields();
        if(fields!=null && fields.length>0){
            // 循环遍历每个Field对象
            for(Field field:fields){
                String fieldName = field.getName();
                // 如果配置文件中存在对应的Bean对象，就注入
                if(beanNameSet.contains(fieldName)){
                   Object fieldBean = getBean(fieldName);
                    ReflectionUtils.insertField(field,bean,fieldBean);
                }
            }
        }
    }
}
