package utils;

import java.lang.reflect.Field;

/**
 * 依赖注入相关的工具类
 */

public class ReflectionUtils {
    public static void insertField(Field field,Object obj,Object value) throws IllegalAccessException {
          if(field != null){
              field.setAccessible(true);
              field.set(obj,value);
          }
    }
}
