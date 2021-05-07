package bean;

import lombok.Data;
import lombok.ToString;

/**
 * 构造函数传参列表
 */
@Data
@ToString
public class ConstructArg {
    //参数位置
    private int index;
    //参数名称
    private String name;
    //参数引用类型名
    private String ref;
    //参数值
    private Object value;
}
