package core;

import bean.BeanDefinition;
import com.fasterxml.jackson.core.type.TypeReference;
import utils.JsonUtils;

import java.io.InputStream;
import java.util.List;

public class JsonApplicationContext extends BeanFactoryImpl{
    private String fileName;

    public JsonApplicationContext(String fileName){
        this.fileName = fileName;
    }

    public void init(){
        loadFile();
    }

    /**
     * 读取并加载配置文件，生成 BeanDefinition对象并放入集合中等待被初始化
     */
    public void loadFile(){
        InputStream in  = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        List<BeanDefinition> beanDefinitions = JsonUtils.readValue(in,new TypeReference<List<BeanDefinition>>(){});
        if(beanDefinitions!=null && !beanDefinitions.isEmpty()){
            for(BeanDefinition definition:beanDefinitions){
                registerBean(definition.getName(),definition);
            }
        }
    }
}