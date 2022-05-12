### When working with **config server** and **actuator/refresh** we sometimes want to do extra task, according to the refreshed data. To do so we need to listen the the refresh event completion.
```
@Service
@Slf4j
public class RefreshListener {

    @Autowired
    private SynchronizedGateService synchronizedGateService;

    @EventListener (RefreshScopeRefreshedEvent.class)
    public synchronized void onApplicationEvent(RefreshScopeRefreshedEvent refreshScopeRefreshedEvent) {
        log.info("Refreshing All data");
        synchronizedGateService.clearCache();
        synchronizedGateService.initData();
        log.info("Refreshing All data ended");
    }
}
```
