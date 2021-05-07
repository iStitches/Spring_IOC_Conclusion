import core.BeanFactory;
import core.JsonApplicationContext;
import entity.Person;

public class test {
    public static void main(String[] args) throws Exception {
        JsonApplicationContext applicationContext = new JsonApplicationContext("bean.json");
        applicationContext.init();
        Person person = (Person)applicationContext.getBean("person");
        person.perform();
    }
}
