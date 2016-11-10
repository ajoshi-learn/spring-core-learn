# Spring Core Notes

## The IoC container

### Container overview

The `org.springframework.beans` and `org.springframework.context` packages are the basis for Spring Framework’s IoC container. The `BeanFactory` interface provides an advanced configuration mechanism capable of managing any type of object. `ApplicationContext` is a sub-interface of `BeanFactor`y. It adds easier integration with Spring’s AOP features; message resource handling (for use in internationalization), event publication; and application-layer specific contexts such as the `WebApplicationContext` for use in web applications.

#### Instantiating a container

```
ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"services.xml", "daos.xml"});
```

##### Composing XML-based configuration metadata
```
<beans>
    <import resource="services.xml"/>
</beans>
```

#### Using the container
```
// create and configure beans
ApplicationContext context =
    new ClassPathXmlApplicationContext(new String[] {"services.xml", "daos.xml"});

// retrieve configured instance
PetStoreService service = context.getBean("petStore", PetStoreService.class);
```

### Bean overview

##### Naming beans

If you want to refer to that bean by name, through the use of the ref element or Service Locator style lookup, you must provide a name.

##### Aliasing a bean outside the bean definition
```
<alias name="fromName" alias="toName"/>
```

#### Instantiating beans

##### Instantiation with a constructor
```
<bean id="exampleBean" class="examples.ExampleBean"/>
```

##### Instantiation with a static factory method
```
<bean id="clientService"
    class="examples.ClientService"
    factory-method="createInstance"/>
```
      
##### Instantiation using an instance factory method
```
<!-- the factory bean, which contains a method called createInstance() -->
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
    <!-- inject any dependencies required by this locator bean -->
</bean>

<!-- the bean to be created via the factory bean -->
<bean id="clientService"
    factory-bean="serviceLocator"
    factory-method="createClientServiceInstance"/>
```