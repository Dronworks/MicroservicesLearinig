# Setting up h2
1. In **start.spring.io** select
```
H2 Database SQL
Provides a fast in-memory database that supports JDBC API and R2DBC access, with a small (2mb) footprint. Supports embedded and server modes as well as a browser based console application.
```
In pom this will look like this:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

2. In application.properties
- Set constant connection name

    **spring.datasource.url=jdbc:h2:mem:testdb**
- For spring after 2.4 version   

    **spring.data.jpa.repositories.bootstrap-mode=default**
- Show sql in logs

    **spring.jpa.show-sql=true** 
- Lets us see the database in http://localhost:8080/h2-console
    
    **spring.h2.console.enabled=true**
- To see the h2 creation during startup
    
    **logging.level.org.springframework=debug**


# Creating h2 entries on the server startup
We can do this 2 ways:

1. By implementing commandLineRunner (will run after server start)
    ```
    @Component
    public class UserDAOServiceCommandLineRunner implements CommandLineRunner {

        private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

        @Autowired
        private UserDAOService userDAOService;

        @Override
        public void run(String... args) throws Exception {
            User user = new User("Andrey", "Admin");
            long id = userDAOService.insert(user);
            log.info("user created: {} ", id);
        }
    }
    ```
2. By adding **data.sql** to resources folder
    ```
    insert into currency_exchange
    (id, conversion_multiplier, environment, currency_from, currency_to)
    values (1000, 65, '', 'USD', 'INR');
    insert into currency_exchange
    (id, conversion_multiplier, environment, currency_from, currency_to)
    values (1001, 75, '', 'EUR', 'INR');
    insert into currency_exchange
    (id, conversion_multiplier, environment, currency_from, currency_to)
    values (1002, 25, '', 'AUD', 'INR');
    ```
# Info
- H2 works with standard @Repository class.
    ```
    public interface UserRepository extends JpaRepository<User, Long> {
    }
    ```
- Repository **MAGIC!** - we can run query for example by 2 fields (**select * from table where from= and to= ...**) by adding functions without body to the repository:
    ```
    ExchangeValue findByFromAndTo(String from, String to);
    ```
- We cant have fields with names like "from" in our entity. If we have we need to set another name for the DB.
    ```
    @Column(name="from_col")
    private String from;
    ```
