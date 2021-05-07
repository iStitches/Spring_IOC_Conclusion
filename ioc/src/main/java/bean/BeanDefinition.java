package bean;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 读取解析配置文件后根据<bean> 生成的BeanDefinition 对象
 */
@Data
@ToString
public class BeanDefinition {
    //名称
    private String name;
    //类的名称
    private String className;
    //接口名称
    private String interfaceName;
    //构造方法参数
    private List<ConstructArg> constructArgs;
//    private List<PropertyArg> propertyArgs;
}
