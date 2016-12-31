# Spring Core Notes

* [Spring Core Notes](#spring-core-notes)
      * [The IoC container](#the-ioc-container)
         * [Container overview](#container-overview)
            * [Instantiating a container](#instantiating-a-container)
               * [Composing XML-based configuration metadata](#composing-xml-based-configuration-metadata)
            * [Using the container](#using-the-container)
         * [Bean overview](#bean-overview)
               * [Naming beans](#naming-beans)
               * [Aliasing a bean outside the bean definition](#aliasing-a-bean-outside-the-bean-definition)
            * [Instantiating beans](#instantiating-beans)
               * [Instantiation with a constructor](#instantiation-with-a-constructor)
               * [Instantiation with a static factory method](#instantiation-with-a-static-factory-method)
               * [Instantiation using an instance factory method](#instantiation-using-an-instance-factory-method)
         * [Dependencies](#dependencies)
            * [Constructor argument resolution](#constructor-argument-resolution)
            * [Setter-based dependency injection](#setter-based-dependency-injection)
            * [Dependency resolution process](#dependency-resolution-process)
            * [Dependencies and configuration in detail](#dependencies-and-configuration-in-detail)
               * [Straight values (primitives, Strings, and so on)](#straight-values-primitives-strings-and-so-on)
               * [The idref element](#the-idref-element)
               * [References to other beans (collaborators)](#references-to-other-beans-collaborators)
               * [Inner beans](#inner-beans)
               * [Collections](#collections)
               * [Collection merging](#collection-merging)
               * [Null and empty string values](#null-and-empty-string-values)
            * [Using depends-on](#using-depends-on)
            * [Lazy-initialized beans](#lazy-initialized-beans)
            * [Autowiring](#autowiring)
               * [Limitations and disadvantages of autowiring](#limitations-and-disadvantages-of-autowiring)
               * [Excluding a bean from autowiring](#excluding-a-bean-from-autowiring)
               * [Arbitrary method replacement](#arbitrary-method-replacement)
         * [Bean scopes](#bean-scopes)
            * [Singleton scope](#singleton-scope)
            * [Prototype scope](#prototype-scope)
            * [Custom scopes](#custom-scopes)
               * [Creating a custom scope](#creating-a-custom-scope)
         * [Customizing the nature of a bean](#customizing-the-nature-of-a-bean)
            * [Lifecycle callbacks](#lifecycle-callbacks)
               * [Initialization callbacks](#initialization-callbacks)
               * [Destruction callbacks](#destruction-callbacks)
               * [Default initialization and destroy methods](#default-initialization-and-destroy-methods)
               * [Combining lifecycle mechanisms](#combining-lifecycle-mechanisms)
            * [ApplicationContextAware and BeanNameAware](#applicationcontextaware-and-beannameaware)
         * [Bean definition inheritance](#bean-definition-inheritance)
         * [Container extension points](#container-extension-points)
            * [Customizing beans using a BeanPostProcessor](#customizing-beans-using-a-beanpostprocessor)
            * [Customizing configuration metadata with a BeanFactoryPostProcessor](#customizing-configuration-metadata-with-a-beanfactorypostprocessor)
            * [Customizing instantiation logic with a FactoryBean](#customizing-instantiation-logic-with-a-factorybean)
         * [Annotation-based container configuration](#annotation-based-container-configuration)
            * [@Required](#required)
            * [@Autowired](#autowired)
            * [Fine-tuning annotation-based autowiring with @Primary](#fine-tuning-annotation-based-autowiring-with-primary)
            * [Fine-tuning annotation-based autowiring with qualifiers](#fine-tuning-annotation-based-autowiring-with-qualifiers)
            * [Using generics as autowiring qualifiers](#using-generics-as-autowiring-qualifiers)
            * [@Resource](#resource)
         * [Classpath scanning and managed components](#classpath-scanning-and-managed-components)
            * [@Component and further stereotype annotations](#component-and-further-stereotype-annotations)
            * [Meta-annotations](#meta-annotations)
            * [Automatically detecting classes and registering bean definitions](#automatically-detecting-classes-and-registering-bean-definitions)
            * [Using filters to customize scanning](#using-filters-to-customize-scanning)
            * [Defining bean metadata within components](#defining-bean-metadata-within-components)
      * [Aspect oriented programming with Spring](#aspect-oriented-programming-with-spring)
         * [Introduction](#introduction)
            * [AOP concepts](#aop-concepts)
         * [AspectJ support](#aspectj-support)
            * [Enabling AspectJ support](#enabling-aspectj-support)
            * [Declaring an aspect](#declaring-an-aspect)
            * [Declaring a pointcut](#declaring-a-pointcut)
               * [Supported Pointcut Designators](#supported-pointcut-designators)
               * [Combining pointcut expressions](#combining-pointcut-expressions)
            * [Examples](#examples)
            * [Declaring advice](#declaring-advice)
               * [Before advice](#before-advice)
               * [After returning advice](#after-returning-advice)
               * [After throwing advice](#after-throwing-advice)
               * [After (finally) advice](#after-finally-advice)
               * [Around advice](#around-advice)
               * [Passing parameters to advice](#passing-parameters-to-advice)


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

### Dependencies

#### Constructor argument resolution
```
<beans>
    <bean id="foo" class="x.y.Foo">
        <constructor-arg ref="bar"/>
        <constructor-arg ref="baz"/>
    </bean>

    <bean id="bar" class="x.y.Bar"/>
    <bean id="baz" class="x.y.Baz"/>
</beans>
```

```
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg type="int" value="7500000"/>
    <constructor-arg type="java.lang.String" value="42"/>
</bean>
```

Use the `index` attribute to specify explicitly the index of constructor arguments. For example:
```
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg index="0" value="7500000"/>
    <constructor-arg index="1" value="42"/>
</bean>
```

You can also use the constructor parameter name for value disambiguation:
```
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg name="years" value="7500000"/>
    <constructor-arg name="ultimateAnswer" value="42"/>
</bean>
```

#### Setter-based dependency injection
```
<bean id="exampleBean" class="examples.ExampleBean">
    <!-- setter injection using the nested ref element -->
    <property name="beanOne">
        <ref bean="anotherExampleBean"/>
    </property>

    <!-- setter injection using the neater ref attribute -->
    <property name="beanTwo" ref="yetAnotherBean"/>
    <property name="integerProperty" value="1"/>
</bean>
```

#### Dependency resolution process

The container performs bean dependency resolution as follows:

* The `ApplicationContext` is created and initialized with configuration metadata that describes all the beans. Configuration metadata can be specified via XML, Java code, or annotations.
* For each bean, its dependencies are expressed in the form of properties, constructor arguments, or arguments to the static-factory method if you are using that instead of a normal constructor. These dependencies are provided to the bean, when the bean is actually created.
* Each property or constructor argument is an actual definition of the value to set, or a reference to another bean in the container.
* Each property or constructor argument which is a value is converted from its specified format to the actual type of that property or constructor argument. By default Spring can convert a value supplied in string format to all built-in types, such as `int`, `long`, `String`, `boolean`, etc

#### Dependencies and configuration in detail

##### Straight values (primitives, Strings, and so on)

The `value` attribute of the `<property/>` element specifies a property or constructor argument as a human-readable string representation. Spring’s conversion service is used to convert these values from a `String` to the actual type of the property or argument.
```
<bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <property name="username" value="root"/>
    <property name="password" value="masterkaoli"/>
</bean>
```

The following example uses the p-namespace for even more succinct XML configuration:
```
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource"
        p:username="root"
        p:password="masterkaoli"/>
</beans>
```

You can also configure a `java.util.Properties` instance as:
```
<bean id="mappings"
    class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
    <property name="properties">
        <value>
            jdbc.driver.className=com.mysql.jdbc.Driver
            jdbc.url=jdbc:mysql://localhost:3306/mydb
        </value>
    </property>
</bean>
```

##### The idref element
      
The `idref` element is simply an error-proof way to pass the id (string value - not a reference) of another bean in the container to a `<constructor-arg/>` or `<property/>` element.
```
<bean id="theTargetBean" class="..."/>
<bean id="theClientBean" class="...">
    <property name="targetName">
        <idref bean="theTargetBean" />
    </property>
</bean>
```

##### References to other beans (collaborators)

The `ref` element is the final element inside a `<constructor-arg/>` or `<property/>` definition element.
```
<ref bean="someBean"/>
```

Specifying the target bean through the `parent` attribute creates a reference to a bean that is in a parent container of the current container. The value of the `parent` attribute may be the same as either the `id` attribute of the target bean, or one of the values in the `name` attribute of the target bean, and the target bean must be in a parent container of the current one.
```
<!-- in the parent context -->
<bean id="accountService" class="com.foo.SimpleAccountService">
    <!-- insert dependencies as required as here -->
</bean>
```
```
<!-- in the child (descendant) context -->
<bean id="accountService" <!-- bean name is the same as the parent bean -->
    class="org.springframework.aop.framework.ProxyFactoryBean">
    <property name="target">
        <ref parent="accountService"/> <!-- notice how we refer to the parent bean -->
    </property>
    <!-- insert other configuration and dependencies as required here -->
</bean>
```

##### Inner beans
      
A `<bean/>` element inside the `<property/>` or `<constructor-arg/>` elements defines a so-called _inner bean_.
```
<bean id="outer" class="...">
    <!-- instead of using a reference to a target bean, simply define the target bean inline -->
    <property name="target">
        <bean class="com.example.Person"> <!-- this is the inner bean -->
            <property name="name" value="Fiona Apple"/>
            <property name="age" value="25"/>
        </bean>
    </property>
</bean>
```
An inner bean definition does not require a defined id or name; if specified, the container does not use such a value as an identifier. The container also ignores the `scope` flag on creation: Inner beans are always anonymous and they are always created with the outer bean. It is not possible to inject inner beans into collaborating beans other than into the enclosing bean or to access them independently.

##### Collections
In the `<list/>`, `<set/>`, `<map/>`, and `<props/>` elements, you set the properties and arguments of the Java Collection types `List`, `Set`, `Map`, and `Properties`, respectively.
```
<bean id="moreComplexObject" class="example.ComplexObject">
    <!-- results in a setAdminEmails(java.util.Properties) call -->
    <property name="adminEmails">
        <props>
            <prop key="administrator">administrator@example.org</prop>
            <prop key="support">support@example.org</prop>
            <prop key="development">development@example.org</prop>
        </props>
    </property>
    <!-- results in a setSomeList(java.util.List) call -->
    <property name="someList">
        <list>
            <value>a list element followed by a reference</value>
            <ref bean="myDataSource" />
        </list>
    </property>
    <!-- results in a setSomeMap(java.util.Map) call -->
    <property name="someMap">
        <map>
            <entry key="an entry" value="just some string"/>
            <entry key ="a ref" value-ref="myDataSource"/>
        </map>
    </property>
    <!-- results in a setSomeSet(java.util.Set) call -->
    <property name="someSet">
        <set>
            <value>just some string</value>
            <ref bean="myDataSource" />
        </set>
    </property>
</bean>
```

##### Collection merging
```
<beans>
    <bean id="parent" abstract="true" class="example.ComplexObject">
        <property name="adminEmails">
            <props>
                <prop key="administrator">administrator@example.com</prop>
                <prop key="support">support@example.com</prop>
            </props>
        </property>
    </bean>
    <bean id="child" parent="parent">
        <property name="adminEmails">
            <!-- the merge is specified on the child collection definition -->
            <props merge="true">
                <prop key="sales">sales@example.com</prop>
                <prop key="support">support@example.co.uk</prop>
            </props>
        </property>
    </bean>
<beans>
```

##### Null and empty string values
```
<bean class="ExampleBean">
    <property name="email">
        <null/>
    </property>
</bean>
```


#### Using depends-on
```
<bean id="beanOne" class="ExampleBean" depends-on="manager,accountDao">
    <property name="manager" ref="manager" />
</bean>

<bean id="manager" class="ManagerBean" />
<bean id="accountDao" class="x.y.jdbc.JdbcAccountDao" />
```

#### Lazy-initialized beans
```
<bean id="lazy" class="com.foo.ExpensiveToCreateBean" lazy-init="true"/>
<bean name="not.lazy" class="com.foo.AnotherBean"/>
```

```
<beans default-lazy-init="true">
    <!-- no beans will be pre-instantiated... -->
</beans>
```

#### Autowiring
When using XML-based configuration metadata, you specify autowire mode for a bean definition with the autowire attribute of the `<bean/>` element. The autowiring functionality has four modes. You specify autowiring per bean and thus can choose which ones to autowire.

| Mode        | Explaination                                                                                                                                                                                                                                                                                                                                    |
|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| no          | (Default) No autowiring. Bean references must be defined via a ref element. Changing the default setting is not recommended for larger deployments, because specifying collaborators explicitly gives greater control and clarity. To some extent, it documents the structure of a system.                                                      |
| byName      | Autowiring by property name. Spring looks for a bean with the same name as the property that needs to be autowired. For example, if a bean definition is set to autowire by name, and it contains a master property (that is, it has a setMaster(..) method), Spring looks for a bean definition named master, and uses it to set the property. |
| byType      | Allows a property to be autowired if exactly one bean of the property type exists in the container. If more than one exists, a fatal exception is thrown, which indicates that you may not use byType autowiring for that bean. If there are no matching beans, nothing happens; the property is not set.                                       |
| constructor | Analogous to byType, but applies to constructor arguments. If there is not exactly one bean of the constructor argument type in the container, a fatal error is raised.                                                                                                                                                                         |

##### Limitations and disadvantages of autowiring

* Explicit dependencies in property and constructor-arg settings always override autowiring. You cannot autowire so-called simple properties such as primitives, Strings, and Classes (and arrays of such simple properties). This limitation is by-design.
* Autowiring is less exact than explicit wiring. Although, as noted in the above table, Spring is careful to avoid guessing in case of ambiguity that might have unexpected results, the relationships between your Spring-managed objects are no longer documented explicitly.
* Wiring information may not be available to tools that may generate documentation from a Spring container.
* Multiple bean definitions within the container may match the type specified by the setter method or constructor argument to be autowired. For arrays, collections, or Maps, this is not necessarily a problem. However for dependencies that expect a single value, this ambiguity is not arbitrarily resolved. If no unique bean definition is available, an exception is thrown.

##### Excluding a bean from autowiring

On a per-bean basis, you can exclude a bean from autowiring. In Spring’s XML format, set the `autowire-candidate` attribute of the `<bean/>` element to false; the container makes that specific bean definition unavailable to the autowiring infrastructure (including annotation style configurations such as `@Autowired)`.

##### Arbitrary method replacement

With XML-based configuration metadata, you can use the `replaced-method` element to replace an existing method implementation with another, for a deployed bean. Consider the following class, with a method computeValue, which we want to override:
```
public class MyValueCalculator {
    public String computeValue(String input) {
        // some real code...
    }
```
```
public class ReplacementComputeValue implements MethodReplacer {
    public Object reimplement(Object o, Method m, Object[] args) throws Throwable {
        // get the input value, work with it, and return a computed result
        String input = (String) args[0];
        return ...;
    }
}
```
```
<bean id="myValueCalculator" class="x.y.z.MyValueCalculator">
    <!-- arbitrary method replacement -->
    <replaced-method name="computeValue" replacer="replacementComputeValue">
        <arg-type>String</arg-type>
    </replaced-method>
</bean>
<bean id="replacementComputeValue" class="a.b.c.ReplacementComputeValue"/>
```

### Bean scopes

| Scope       | Definition                                                                                                                                                                                                                                                 |
|-------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| singleton   | (Default) Scopes a single bean definition to a single object instance per Spring IoC container.                                                                                                                                                            |
| prototype   | Scopes a single bean definition to any number of object instances.                                                                                                                                                                                         |
| request     | Scopes a single bean definition to the lifecycle of a single HTTP request; that is, each HTTP request has its own instance of a bean created off the back of a single bean definition. Only valid in the context of a web-aware Spring ApplicationContext. |
| session     | Scopes a single bean definition to the lifecycle of an HTTP Session. Only valid in the context of a web-aware Spring ApplicationContext.                                                                                                                   |
| application | Scopes a single bean definition to the lifecycle of a ServletContext. Only valid in the context of a web-aware Spring ApplicationContext.                                                                                                                  |
| websocket   | Scopes a single bean definition to the lifecycle of a WebSocket. Only valid in the context of a web-aware Spring ApplicationContext.                                                                                                                       |

#### Singleton scope

Only one shared instance of a singleton bean is managed, and all requests for beans with an id or ids matching that bean definition result in that one specific bean instance being returned by the Spring container.
To put it another way, when you define a bean definition and it is scoped as a singleton, the Spring IoC container creates exactly one instance of the object defined by that bean definition. This single instance is stored in a cache of such singleton beans, and all subsequent requests and references for that named bean return the cached object.
Spring’s concept of a singleton bean differs from the Singleton pattern as defined in the Gang of Four (GoF) patterns book. The GoF Singleton hard-codes the scope of an object such that one and only one instance of a particular class is created per ClassLoader. The scope of the Spring singleton is best described as _per container_ and _per bean_.

#### Prototype scope

The non-singleton, prototype scope of bean deployment results in the creation of a new bean instance every time a request for that specific bean is made. That is, the bean is injected into another bean or you request it through a `getBean()` method call on the container. As a rule, use the prototype scope for all stateful beans and the singleton scope for stateless beans.
```
<bean id="accountService" class="com.foo.DefaultAccountService" scope="prototype"/>
```

In contrast to the other scopes, Spring does not manage the complete lifecycle of a prototype bean: the container instantiates, configures, and otherwise assembles a prototype object, and hands it to the client, with no further record of that prototype instance. Thus, although initialization lifecycle callback methods are called on all objects regardless of scope, in the case of prototypes, configured _destruction_ lifecycle callbacks are _not called_.

#### Custom scopes

##### Creating a custom scope

To integrate your custom scope(s) into the Spring container, you need to implement the `org.springframework.beans.factory.config.Scope` interface

[Example](src/main/java/app/example4)

### Customizing the nature of a bean

#### Lifecycle callbacks

To interact with the container’s management of the bean lifecycle, you can implement the Spring `InitializingBean` and `DisposableBean` interfaces. The container calls `afterPropertiesSet()` for the former and `destroy()` for the latter to allow the bean to perform certain actions upon initialization and destruction of your beans.

##### Initialization callbacks

The `org.springframework.beans.factory.InitializingBean` interface allows a bean to perform initialization work after all necessary properties on the bean have been set by the container. The `InitializingBean` interface specifies a single method:
```
void afterPropertiesSet() throws Exception;
```
Alternatively, use the `@PostConstruct` annotation or specify a POJO initialization method. In the case of XML-based configuration metadata, you use the init-method attribute to specify the name of the method that has a void no-argument signature.
```
<bean id="exampleInitBean" class="examples.ExampleBean" init-method="init"/>
```

##### Destruction callbacks

Implementing the `org.springframework.beans.factory.DisposableBean` interface allows a bean to get a callback when the container containing it is destroyed. The `DisposableBean` interface specifies a single method:
```
void destroy() throws Exception;
```

Alternatively, use the `@PreDestroy` annotation or specify a generic method that is supported by bean definitions. With XML-based configuration metadata, you use the destroy-method attribute.
```
<bean id="exampleInitBean" class="examples.ExampleBean" destroy-method="cleanup"/>
```

##### Default initialization and destroy methods
```
<beans default-init-method="init">
    <bean id="blogService" class="com.foo.DefaultBlogService">
        <property name="blogDao" ref="blogDao" />
    </bean>
</beans>
```

##### Combining lifecycle mechanisms

Multiple lifecycle mechanisms configured for the same bean, with different initialization methods, are called as follows:

* Methods annotated with `@PostConstruct`
* `afterPropertiesSet()` as defined by the `InitializingBean` callback interface
* A custom configured `init()` method

Destroy methods are called in the same order:

* Methods annotated with `@PreDestroy`
* `destroy()` as defined by the `DisposableBean` callback interface
* A custom configured `destroy()` method

#### ApplicationContextAware and BeanNameAware

When an `ApplicationContext` creates an object instance that implements the `org.springframework.context.ApplicationContextAware` interface, the instance is provided with a reference to that `ApplicationContext`.
Thus beans can manipulate programmatically the `ApplicationContext` that created them, through the `ApplicationContext` interface, or by casting the reference to a known subclass of this interface, such as `ConfigurableApplicationContext`, which exposes additional functionality.

### Bean definition inheritance

A bean definition can contain a lot of configuration information, including constructor arguments, property values, and container-specific information such as initialization method, static factory method name, and so on. A child bean definition inherits configuration data from a parent definition. The child definition can override some values, or add others, as needed. Using parent and child bean definitions can save a lot of typing.
```
<bean id="inheritedTestBean" abstract="true"
        class="org.springframework.beans.TestBean">
    <property name="name" value="parent"/>
    <property name="age" value="1"/>
</bean>

<bean id="inheritsWithDifferentClass"
        class="org.springframework.beans.DerivedTestBean"
        parent="inheritedTestBean" init-method="initialize">
    <property name="name" value="override"/>
    <!-- the age property value of 1 will be inherited from parent -->
</bean>
```

A child bean definition uses the bean class from the parent definition if none is specified, but can also override it. In the latter case, the child bean class must be compatible with the parent, that is, it must accept the parent’s property values.

A child bean definition inherits scope, constructor argument values, property values, and method overrides from the parent, with the option to add new values. Any scope, initialization method, destroy method, and/or static factory method settings that you specify will override the corresponding parent settings
The remaining settings are always taken from the child definition: _depends on_, _autowire mode_, _dependency check_, _singleton_, _lazy init_.

### Container extension points

#### Customizing beans using a BeanPostProcessor

The `BeanPostProcessor` interface defines callback methods that you can implement to provide your own (or override the container’s default) instantiation logic, dependency-resolution logic, and so forth. If you want to implement some custom logic after the Spring container finishes instantiating, configuring, and initializing a bean, you can plug in one or more `BeanPostProcessor` implementations.
You can configure multiple `BeanPostProcessor` instances, and you can control the order in which these `BeanPostProcessors` execute by setting the order property. You can set this property only if the `BeanPostProcessor` implements the `Ordered` interface; if you write your own `BeanPostProcessor` you should consider implementing the `Ordered` interface too.

#### Customizing configuration metadata with a BeanFactoryPostProcessor

The next extension point that we will look at is the `org.springframework.beans.factory.config.BeanFactoryPostProcessor`. The semantics of this interface are similar to those of the `BeanPostProcessor`, with one major difference: `BeanFactoryPostProcessor` operates on the bean configuration metadata; that is, the Spring IoC container allows a `BeanFactoryPostProcessor` to read the configuration metadata and potentially change it before the container instantiates any beans other than `BeanFactoryPostProcessors`.

#### Customizing instantiation logic with a FactoryBean

Implement the `org.springframework.beans.factory.FactoryBean` interface for objects that are themselves factories.

The `FactoryBean` interface is a point of pluggability into the Spring IoC container’s instantiation logic. If you have complex initialization code that is better expressed in Java as opposed to a (potentially) verbose amount of XML, you can create your own `FactoryBean`, write the complex initialization inside that class, and then plug your custom `FactoryBean` into the container.

The `FactoryBean` interface provides three methods:

* `Object getObject()`: returns an instance of the object this factory creates. The instance can possibly be shared, depending on whether this factory returns singletons or prototypes.
* `boolean isSingleton()`: returns true if this FactoryBean returns singletons, false otherwise.
* `Class getObjectType()`: returns the object type returned by the `getObject()` method or null if the type is not known in advance.

### Annotation-based container configuration
```
<context:annotation-config/>
```

(The implicitly registered post-processors include `AutowiredAnnotationBeanPostProcessor`, `CommonAnnotationBeanPostProcessor`, `PersistenceAnnotationBeanPostProcessor`, as well as the aforementioned `RequiredAnnotationBeanPostProcessor`.)

#### @Required
The `@Required` annotation applies to bean property setter methods, as in the following example:
```
public class SimpleMovieLister {
    private MovieFinder movieFinder;
    @Required
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }
    // ...
}
```
This annotation simply indicates that the affected bean property must be populated at configuration time, through an explicit property value in a bean definition or through autowiring. The container throws an exception if the affected bean property has not been populated; this allows for eager and explicit failure, avoiding NullPointerExceptions or the like later on.

#### @Autowired
ou can apply the `@Autowired` annotation to constructors, setters, methods or fields.
It is also possible to provide all beans of a particular type from the ApplicationContext by adding the annotation to a field or method that expects an array of that type:
```
public class MovieRecommender {
    @Autowired
    private MovieCatalog[] movieCatalogs;
    // ...
}
```
The same applies for typed collections.

By default, the autowiring fails whenever zero candidate beans are available; the default behavior is to treat annotated methods, constructors, and fields as indicating required dependencies. This behavior can be changed as demonstrated below.
```
public class SimpleMovieLister {
    private MovieFinder movieFinder;
    @Autowired(required=false)
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }
    // ...
}
```
You can also use `@Autowired` for interfaces that are well-known resolvable dependencies: `BeanFactory`, `ApplicationContext`, `Environment`, `ResourceLoader`, `ApplicationEventPublisher`, and `MessageSource`. These interfaces and their extended interfaces, such as `ConfigurableApplicationContext` or `ResourcePatternResolver`, are automatically resolved, with no special setup necessary.

#### Fine-tuning annotation-based autowiring with @Primary
Because autowiring by type may lead to multiple candidates, it is often necessary to have more control over the selection process. One way to accomplish this is with Spring’s `@Primary` annotation. `@Primary` indicates that a particular bean should be given preference when multiple beans are candidates to be autowired to a single-valued dependency. If exactly one 'primary' bean exists among the candidates, it will be the autowired value.

#### Fine-tuning annotation-based autowiring with qualifiers
`@Primary` is an effective way to use autowiring by type with several instances when one primary candidate can be determined. When more control over the selection process is required, Spring’s `@Qualifier` annotation can be used. You can associate qualifier values with specific arguments, narrowing the set of type matches so that a specific bean is chosen for each argument. In the simplest case, this can be a plain descriptive value:
```
public class MovieRecommender {
    @Autowired
    @Qualifier("main")
    private MovieCatalog movieCatalog;
    // ...
}
```

#### Using generics as autowiring qualifiers
In addition to the `@Qualifier` annotation, it is also possible to use Java generic types as an implicit form of qualification. For example, suppose you have the following configuration:
```
@Configuration
public class MyConfiguration {
    @Bean
    public StringStore stringStore() {
        return new StringStore();
    }
    @Bean
    public IntegerStore integerStore() {
        return new IntegerStore();
    }
}
```

Assuming that beans above implement a generic interface, i.e. `Store<String>` and `Store<Integer>`, you can `@Autowire` the `Store` interface and the generic will be used as a qualifier:
```
@Autowired
private Store<String> s1; // <String> qualifier, injects the stringStore bean
@Autowired 
private Store<Integer> s2; // <Integer> qualifier, injects the integerStore bean
```

#### @Resource
Spring also supports injection using the JSR-250 `@Resource` annotation on fields or bean property setter methods.

### Classpath scanning and managed components

#### `@Component` and further stereotype annotations

Spring provides further stereotype annotations: `@Component`, `@Service`, and `@Controller`. `@Component` is a generic stereotype for any Spring-managed component. `@Repository`, `@Service`, and `@Controller` are specializations of `@Component` for more specific use cases, for example, in the persistence, service, and presentation layers, respectively. Therefore, you can annotate your component classes with `@Component`, but by annotating them with `@Repository`, `@Service`, or `@Controller` instead, your classes are more properly suited for processing by tools or associating with aspects.

#### Meta-annotations

Many of the annotations provided by Spring can be used as meta-annotations in your own code. A meta-annotation is simply an annotation that can be applied to another annotation.
Meta-annotations can also be combined to create composed annotations. For example, the `@RestController` annotation from Spring MVC is composed of `@Controller` and `@ResponseBody`.

#### Automatically detecting classes and registering bean definitions

To autodetect classes and register the corresponding beans, you need to add `@ComponentScan` to your `@Configuration` class, where the `basePackages` attribute is a common parent package for the two classes.
```
@Configuration
@ComponentScan(basePackages = "org.example")
public class AppConfig  {
}
```

#### Using filters to customize scanning

```
@Configuration
@ComponentScan(basePackages = "org.example",
        includeFilters = @Filter(type = FilterType.REGEX, pattern = ".*Stub.*Repository"),
        excludeFilters = @Filter(Repository.class))
public class AppConfig {
}
```

#### Defining bean metadata within components

```
@Component
public class FactoryMethodComponent {
    @Bean
    @Qualifier("public")
    public TestBean publicInstance() {
        return new TestBean("publicInstance");
    }
    public void doWork() {
    }
}
```

## Aspect oriented programming with Spring

### Introduction

#### AOP concepts

* _Aspect_: a modularization of a concern that cuts across multiple classes.
* _Join point_: a point during the execution of a program, such as the execution of a method or the handling of an exception. In Spring AOP, a join point always represents a _method execution_.
* _Advice_: action taken by an aspect at a particular join point. Different types of advice include "around," "before" and "after" advice.
* _Pointcut_: a predicate that matches join points.
* _Target object_: object being advised by one or more aspects. Also referred to as the advised object.
* _AOP proxy_: an object created by the AOP framework in order to implement the aspect contracts
* _Weaving_: linking aspects with other application types or objects to create an advised object.

Types of advice:

* _Before advice_: Advice that executes before a join point, but which does not have the ability to prevent execution flow proceeding to the join point (unless it throws an exception).
* _After_ returning advice_: Advice to be executed after a join point completes normally: for example, if a method returns without throwing an exception.
* _After_ throwing advice_: Advice to be executed if a method exits by throwing an exception.
* _After_ (finally) advice_: Advice to be executed regardless of the means by which a join point exits (normal or exceptional return).
* _Around_ advice_: Advice that surrounds a join point such as a method invocation. This is the most powerful kind of advice. Around advice can perform custom behavior before and after the method invocation. It is also responsible for choosing whether to proceed to the join point or to shortcut the advised method execution by returning its own return value or throwing an exception.

### AspectJ support

#### Enabling AspectJ support
```
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
}
```

#### Declaring an aspect
```
@Aspect
public class NotVeryUsefulAspect {
}
```

#### Declaring a pointcut
```
@Pointcut("execution(* transfer(..))")// the pointcut expression
private void anyOldTransfer() {}// the pointcut signature
```

##### Supported Pointcut Designators

* _execution_ - for matching method execution join points, this is the primary pointcut designator you will use when working with Spring AOP
* _within_ - limits matching to join points within certain types (simply the execution of a method declared within a matching type when using Spring AOP)
* _this_ - limits matching to join points (the execution of methods when using Spring AOP) where the bean reference (Spring AOP proxy) is an instance of the given type
* _target_ - limits matching to join points (the execution of methods when using Spring AOP) where the target object (application object being proxied) is an instance of the given type
* _args_ - limits matching to join points (the execution of methods when using Spring AOP) where the arguments are instances of the given types
* _@target_ - limits matching to join points (the execution of methods when using Spring AOP) where the class of the executing object has an annotation of the given type
* _@args_ - limits matching to join points (the execution of methods when using Spring AOP) where the runtime type of the actual arguments passed have annotations of the given type(s)
* _@within_ - limits matching to join points within types that have the given annotation (the execution of methods declared in types with the given annotation when using Spring AOP)
* _@annotation_ - limits matching to join points where the subject of the join point (method being executed in Spring AOP) has the given annotation

##### Combining pointcut expressions
```
@Pointcut("execution(public * *(..))")
private void anyPublicOperation() {}

@Pointcut("within(com.xyz.someapp.trading..*)")
private void inTrading() {}

@Pointcut("anyPublicOperation() && inTrading()")
private void tradingOperation() {}
```

#### Examples

* the execution of any public method:
`execution(public * *(..))`

* the execution of any method with a name beginning with "set":
`execution(* set*(..))`

* the execution of any method defined by the `AccountService` interface:
`execution(* com.xyz.service.AccountService.*(..))*`

* the execution of any method defined in the service package:
`execution(* com.xyz.service.*.*(..))`

* any join point (method execution only in Spring AOP) within the service package:
`within(com.xyz.service.*)`

* any join point (method execution only in Spring AOP) within the service package or a sub-package:
`within(com.xyz.service..*)`

* any join point (method execution only in Spring AOP) where the proxy implements the `AccountService` interface:
`this(com.xyz.service.AccountService)`

* any join point (method execution only in Spring AOP) where the target object implements the `AccountService` interface:
target(com.xyz.service.AccountService)

* any join point (method execution only in Spring AOP) which takes a single parameter, and where the argument passed at runtime is `Serializable`:
`args(java.io.Serializable)`

* any join point (method execution only in Spring AOP) where the target object has an `@Transactional` annotation:
`@target(org.springframework.transaction.annotation.Transactional)`

* any join point (method execution only in Spring AOP) where the declared type of the target object has an `@Transactional` annotation:
`@within(org.springframework.transaction.annotation.Transactional)`

* any join point (method execution only in Spring AOP) where the executing method has an `@Transactional` annotation:
`@annotation(org.springframework.transaction.annotation.Transactional)`

* any join point (method execution only in Spring AOP) which takes a single parameter, and where the runtime type of the argument passed has the `@Classified` annotation:
`@args(com.xyz.security.Classified)`

* any join point (method execution only in Spring AOP) on a Spring bean named `tradeService`:
`bean(tradeService)`

* any join point (method execution only in Spring AOP) on Spring beans having names that match the wildcard expression `*Service`:
`bean(*Service)`

#### Declaring advice

##### Before advice

```
@Before("com.xyz.myapp.SystemArchitecture.dataAccessOperation()")
public void doAccessCheck() {
}
```

##### After returning advice

```
@AfterReturning("com.xyz.myapp.SystemArchitecture.dataAccessOperation()")
    public void doAccessCheck() {
        // ...
    }
```
Sometimes you need access in the advice body to the actual value that was returned. You can use the form of `@AfterReturning` that binds the return value for this:
```
@AfterReturning(
        pointcut="com.xyz.myapp.SystemArchitecture.dataAccessOperation()",
        returning="retVal")
    public void doAccessCheck(Object retVal) {
        // ...
    }
```

##### After throwing advice
```
@AfterThrowing(
        pointcut="com.xyz.myapp.SystemArchitecture.dataAccessOperation()",
        throwing="ex")
    public void doRecoveryActions(DataAccessException ex) {
    }
```

##### After (finally) advice
```
@After("com.xyz.myapp.SystemArchitecture.dataAccessOperation()")
    public void doReleaseLock() {
        // ...
    }
```

##### Around advice 
```
@Around("com.xyz.myapp.SystemArchitecture.businessService()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        return retVal;
    }
```

##### Passing parameters to advice
```
@Before("com.xyz.myapp.SystemArchitecture.dataAccessOperation() && args(account,..)")
public void validateAccount(Account account) {
    // ...
}
```