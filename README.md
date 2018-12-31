

------

环境：

[jkd8+]()

[mysql5.6+]()



## 一、配置

-  在processEngineConfiguration的定义中配置属性idGenerator如下:

```
 <bean id="processEngineConfiguration" class="org.flowable.spring.SpringProcessEngineConfiguration">
 ...
 <!-- 自定义uuid生成id-->
 <property name="idGenerator" ref="strongUuidGenerator" />
```

- 定义bean

```   
<bean id="customUuidGenerator" class="com.study.demo.CustomUuidGenerator" />
```



## 二、实践测试



- 运行demo
- 查看数据库表

