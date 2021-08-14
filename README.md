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

## 要实现的功能

+ IOC控制翻转

  > 

+ AOP 面向切面编程

  > 

相关测试类：

| -                                               | -                                                            |
| ----------------------------------------------- | ------------------------------------------------------------ |
| [配置文件](./src/main/resources/wills-bean.xml) | [测试类](./src/main/java/com/wills/spring/example/Example.java) |



## 弊端

目前版本需要手动书写 wills-bean.xml里面包含的 ServiceImpl实现类，里面带有的@AutoWired会进行自动注入，后期版本会引入 @Mapper @Service 注解，完全抛弃wills-bean.xml配置文件，达到springboot那般顺滑的无感注入！

