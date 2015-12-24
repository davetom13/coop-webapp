package org.seanano.coop.hardware;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.seanano.coop.model.Coop;
import org.seanano.coop.model.DoorCommand;
import org.seanano.coop.model.LightCommand;
import org.seanano.coop.model.ScheduledCommand;

/**
 * Factory class for a Coops.
 */
public class CoopFactory {
    /**
     * Minimum amount of time between refreshes, in millis.
     */
    private static final long MINIMUM_REFRESH_TIME = 1000;

    private static long lastRefreshTime;
    private static ArduinoCoop coop;
    private static ScheduledThreadPoolExecutor executor;

    /**
     * Initializes the factory and coop.
     * 
     * @throws Exception if an error occurs initializing
     */
    public synchronized static void initialize() throws Exception {
        assert coop == null;
        coop = new ArduinoCoop(0);
        coop.refresh();

        executor = new ScheduledThreadPoolExecutor(1);
        scheduleNextDoorExecutor();
        scheduleNextLightExecutor();
    }

    /**
     * Gets the Coop. Will force a refresh of the coop if time since last retrieval exceeds MINIMUM_REFRESH_TIME.
     * 
     * @return Coop
     * @throws Exception if refresh required and unable to complete
     */
    public synchronized static Coop getCoop() throws Exception {
        long currentTime = System.currentTimeMillis();
        if (currentTime > lastRefreshTime + MINIMUM_REFRESH_TIME) {
            coop.refresh();
            lastRefreshTime = currentTime;
        }
        return coop;
    }

    /**
     * Schedules the next LightCommand to be executed.
     */
    private static void scheduleNextLightExecutor() {
        ScheduledCommand<LightCommand> command = CoopScheduler.getNextLightCommand();

        executor.schedule(new LightExecutor(command.getCommand()),
                (command.getScheduledTime() - System.currentTimeMillis()), TimeUnit.MILLISECONDS);
    }

    /**
     * Schedules the next DoorCommand to be executed.
     */
    private static void scheduleNextDoorExecutor() {
        ScheduledCommand<DoorCommand> command = CoopScheduler.getNextDoorCommand();

        executor.schedule(new DoorExecutor(command.getCommand()),
                (command.getScheduledTime() - System.currentTimeMillis()), TimeUnit.MILLISECONDS);
    }

    /**
     * Executor for DoorCommands.
     */
    private static class DoorExecutor implements Runnable {
        private DoorCommand command;

        DoorExecutor(DoorCommand command) {
            this.command = command;
        }

        @Override
        public void run() {
            try {
                coop.control(coop.getDoor(0), command);
                // TODO log this executed scheduled event
            }
            catch (Exception e) {
                // TODO log this more appropriately
                e.printStackTrace();
            }
            finally {
                scheduleNextDoorExecutor();
            }
        }
    }

    /**
     * Executor for LightCommands.
     */
    private static class LightExecutor implements Runnable {
        private LightCommand command;

        LightExecutor(LightCommand command) {
            this.command = command;
        }

        @Override
        public void run() {
            try {
                coop.control(coop.getLight(0), command);
                // TODO log this executed scheduled event
            }
            catch (Exception e) {
                // TODO log this more appropriately
                e.printStackTrace();
            }
            finally {
                scheduleNextLightExecutor();
            }
        }
    }
}
