# @SpringBootApplication - defines next
- Spring boot context file
- Enables AutoConfiguration
- Enables components scan - scanning components in this package and below

### Printing loaded beans
In main application we can do somth like this
```
ApplicationContext app = SrpingApplication.run(ExampleClass.class, args);

for(String name : app.getDefinitionNames()) {
    sout(name);
}
```
Lots of beans are created. To be more specific lots of beans from the **spring-boot-autoconfigure-2.4.1.jar**

Auto configure looks what is on the class path and according to it configures. For example if **WEB** on the path the **DISPACHER** will be configured. + The beans we configured in the application.

### @ConditionalOnClass 
A Specific bean will be created only if specified classes are on the classpath.

### @ConditionalOnMissingBean
A Specific bean will be created only if class is missing on the classpath.

**NOTE** to see what conditions are met and what autoconfigure loading we can turn on the debug:
```
logging.level.org.springframework=DEBUG
```
And then we would see
```
================
AUTCO-CONFIGURATION REPORT
================
```

# What starters gives us from spring
We can click on starter dependency in pom, and see what it brings us :)

# JPA

### Queries name
JPA entities quering called **JPQL**

### Inheritance
For example we have different types of employees in java, each type has its own special fields. We can use same table by inheritance.

```
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminationColumn(name="EMPLOYEE_TYPE")
...
And then each class EXTENDS Employee class, with his special field.
```

### JPA -interface | Hibernate -implementation
Once we annotate all the needed, hibernate will be able to use it.

### Who creates h2 in memory table and schemas? 
Wehn using in memory DB the AUTO-CONFIGURATION creates both when app starts. Also next beans initiated:
```
HibernateJpaAutoConfiguration matched:
    - @ConditionalOnClass found required classes 'org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean', 'javax.persistence.EntityManager', 'org.hibernate.engine.spi.SessionImplementor' (OnClassCondition)

HibernateJpaConfiguration matched:
    - @ConditionalOnSingleCandidate (types: javax.sql.DataSource; SearchStrategy: all) found a single bean 'dataSource' (OnBeanCondition)
    
DataSourceAutoConfiguration matched:
    - @ConditionalOnClass found required classes 'javax.sql.DataSource', 'org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType' (OnClassCondition)
    - @ConditionalOnMissingBean (types: io.r2dbc.spi.ConnectionFactory; SearchStrategy: all) did not find any beans (OnBeanCondition)

 JpaBaseConfiguration#entityManagerFactory matched:
      - @ConditionalOnMissingBean (types: org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean,javax.persistence.EntityManagerFactory; SearchStrategy: all) did not find any beans (OnBeanCondition)

```

# Run code with app start
We can run a code with implementing CommandLineRunner, and it will run with the spring start.

# Get spring variables
For example get the port in the code:
```
@Autowired
private Environment environment;
...
environment.getProperty("local.server.port")
```

# Set server port per run
In the run configuration in the VM arguments
```
-DserverPort=<port>
```
