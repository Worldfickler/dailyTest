package org.fickler.resourceAndautowired;

/**
 * SpringBoot（Spring）中为什么不推荐使用 @Autowired
 * => 因为 Spring 官方实际上推荐是构造注入，而不是字段注入 【区别看下方代码】
 *    且 @Autowired 实际上是 Spring 提供的，导致了业务代码和框架的强绑定，如果更换成其它 IOC 框架，就不适用了
 *    而 @Resource 是 JSR-250 提供的，它是 Java 的标准，因此，即使要使用字段注入也要使用 @Resource
 *
 * @author guojuhui@jbinfo.cn
 * @since 2025/3/28
 */
public class ResourceAndAutowired {


    public static void main(String[] args) {

    }

}

class UserService {

    private final DatabaseService databaseService;

    // 构造器注入
    public UserService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    // 字段注入就是 SpringBoot 项目中常见的写法
    /**
     * @Autowired
     * private DatabaseService databaseService;
     */

    public void createUser() {
        databaseService.connect();
        System.out.println("User created.");
    }
}

class DatabaseService {
    public void connect() {
        System.out.println("Connected to the database...");
    }
}
