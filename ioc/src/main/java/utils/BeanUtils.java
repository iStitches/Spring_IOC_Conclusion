package utils;

import bean.ConstructArg;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * Bean对象实例化工具类
 * 使用动态代理Cglib进行实例化
 */
public class BeanUtils {
    public static <T> T instancedByCglib(Class<T> clz, Constructor construct, Object[] args){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clz);
        enhancer.setCallback(NoOp.INSTANCE);

        if(construct == null)
            return (T) enhancer.create();
        else{
            return (T) enhancer.create(construct.getParameterTypes(),args);
        }
    }
}
