# 属于Wills的Spring框架

> 自制 简易版spring框架，包含IOC和AOP，此项目仅仅是研究spring框架的原理

## 已经实现的功能

+ Bean 配置文件的自动注入，以及bean配置文件内容field字段@AutoWired自动注入

  > [配置文件](./src/main/resources/wills-bean.xml)
  >
  > ![](http://image.tinx.top/20210814110004.png)
  >
  > [测试注解类](./src/main/java/com/wills/spring/example/service/impl/TransferServiceImpl.java)
  >
  > ![](http://image.tinx.top/20210814110023.png)

+ Wills-spring transaction 事务支持 （要在接口处使用@Transaction(startTransaction = true)开启事务，报错自动回滚）

  > 相关测试类： 
  >
  > [Example](./src/main/java/com/wills/spring/example/Example.java)
  >
  >  [测试事务注解类](./src/main/java/com/wills/spring/example/service/TransferService.java)
  >
  > ![](http://image.tinx.top/20210814105922.png)
  
+ Wills-Spring AOP支持 (要在接口处使用 @AOP(pointcut={AOPEnum.XXXX},resolveClass=AOPTemplate的实现类.class))

  > 相关测试类：
  >
  > [Example](./src/main/java/com/wills/spring/example/Example.java)
  >
  > [测试事务注解类](./src/main/java/com/wills/spring/example/service/TransferService.java)
  >
  > ![](http://image.tinx.top/20210814134548.png)
  >
  > [切面实现类-TransferAOP](./src/main/java/com/wills/spring/example/aop/TransferAOP.java)
  >
  > ![](http://image.tinx.top/20210814134738.png)
  >
  > 执行结果：
  >
  > ![](http://image.tinx.top/20210814134833.png)
  >
  > 说明， point可选有五个值，分别为： 
  >
  > + AOPEnum.BEFORE 目标方法执行前
  > + AOPEnum.AFTER 目标方法执行后
  > + AOPEnum.AFTER_RETURNING 目标方法返回值以后
  > + AOPEnum.THROWING 目标方法遇到错误，throw Exception 后会执行该方法
  > + AOPEnum.AROUND 目标方法执行期间
  >
  > resolveClass下面的类,要实现 AOPTemplate接口 或者继承 SimpleAOPTemplate 抽象类，将上面的point数组需要的方法进行重写，然后即可实现该功能. 源码原型：
  >
  > ​	> public Class<? extends AOPTemplate> resolveClass();

相关测试类：

| -                                               | -                                                            |
| ----------------------------------------------- | ------------------------------------------------------------ |
| [配置文件](./src/main/resources/wills-bean.xml) | [测试类](./src/main/java/com/wills/spring/example/Example.java) |



## 弊端

目前版本需要手动书写 wills-bean.xml里面包含的 ServiceImpl实现类，里面带有的@AutoWired会进行自动注入，后期版本会引入 @Mapper @Service 注解，完全抛弃wills-bean.xml配置文件，达到springboot那般顺滑的无感注入！

