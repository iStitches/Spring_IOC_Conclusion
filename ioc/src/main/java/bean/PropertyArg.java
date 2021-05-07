package bean;

import lombok.Data;
import lombok.ToString;

/**
 *需要注入的参数列表
 */
@Data
@ToString
public class PropertyArg {
    private String name;
    private String value;
    private String typeName;
}
