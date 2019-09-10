package org.dimensinfin.eveonline.neocom;

import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.Objects;

@EnableScheduling
@SpringBootApplication
public class NeoComBackendApplication /*extends NeoComMicroServiceApplicationSerializers*/ {
    private static Logger logger = LoggerFactory.getLogger("NeoComMicroServiceApplication");
    public static final NeoComComponentFactory neocomComponentFactory = new NeoComComponentFactory();
    public static final TimedUpdater timedService = new TimedUpdater();

    // - M A I N   E N T R Y P O I N T

    /**
     * The component factory is not required on SpringBoot since the components can be wrapped into read SB
     * auto configurable components. But the factory should be instantiated as on other platforms.
     *
     * @param args
     */
    public static void main(final String[] args) throws IOException {
        logger.info(">> [NeoComBackendApplication.main]");
//        neocomComponentFactory=new NeoComComponentFactory();
        // Load the Session data.
//        logger.info("-- [NeoComBackendApplication.main]> Read Session data...");
//        SessionManager.readSessionData();

        logger.info("-- [NeoComBackendApplication.main]> Verifying factory availability...");
        Objects.requireNonNull(neocomComponentFactory);
        Objects.requireNonNull(timedService);

        logger.info("-- [NeoComBackendApplication.main]> Starting application instance...");
        SpringApplication.run(NeoComBackendApplication.class, args);
        logger.info("<< [NeoComBackendApplication.main]");
    }

    // - S E R V I C E S

    /**
     * Run the tasks scheduler every minute to launch any pending action registered.
     */
    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    private void onTime() {
        // Fire another background update scan.
        // Check if the configuration properties allow to run the updater.
        if (GlobalDataManager.getResourceBoolean("P.updater.allowtimer", false)) {
            timedService.timeTick();
        }
    }

    public static class TimedUpdater {

        public void timeTick() {

        }
    }
}