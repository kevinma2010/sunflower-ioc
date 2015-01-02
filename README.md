sunflower-ioc
=============
这是一个基于注解的简易ioc工具, 为什么要重复造轮子呢, 只是为了好玩.:grin:

### 用法

在Class上使用`@Bean`标注一个对象需要容器管理;

在Field上使用`@Resource`标注依赖, 该field需要提供setter器.

#### 示例
```java
@Bean("jack")
public class Person {

    private String name = "jack";

    public String getName() {
        return name;
    }
}

@Bean("hi")
public class Hi {
    @Resource("jack")
    private Person person;

    /**
     * Say Hello
     */
    public void sayHello() {

        System.out.println("Hello " + person.getName());

    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
```

#### 注入示例
```java
Set<Class<?>> classes = new Scanner().scanPackage("com.mlongbo.sunflower.ioc.bean");

BeanContext.me().init(classes);

Hi hi = (Hi) BeanContext.me().getBean("hi");
hi.sayHello();
```