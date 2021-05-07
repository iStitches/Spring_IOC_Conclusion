package utils;

/**
 * 加载类信息的工具类
 */
public class ClassUtils {
    private static ClassLoader getDefaultClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类的方法
     * (1) 根据当前线程获取类加载器 ClassLoader
     * (2) 调用 ClassLoader.loadClass(xxx) 方法进行类加载，返回 Class对象
     * @param className
     * @return
     */
    public static Class loadClass(String className){
        try {
            ClassLoader classLoader = getDefaultClassLoader();
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
