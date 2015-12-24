package org.seanano.coop.hardware;

import java.util.Calendar;

import org.seanano.coop.model.DoorCommand;
import org.seanano.coop.model.LightCommand;
import org.seanano.coop.model.ScheduledCommand;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

/**
 * Static scheduler for the coop. This is a little goofy due to our requirements.<BR><BR>
 * Morning: <BR>
 * The dogs are let out in the morning in an area the chickens can get to, so we want to manually let the chickens
 * out after we have put the dogs up. As a failback in case we forget, the door will open at noon as the dogs are not
 * left out that late. The coop light will turn on at the civil sunrise.
 * <BR>
 * <BR>
 * Evening: <BR>
 * The door will shut at civil sunset as the chickens always put themselves in the coop before then. The light
 * will shut off 5 minutes later.
 */
class CoopScheduler {
    // TODO update coop location here (latitude, longitude)
    private static Location location = new Location(0.0d, 0.0d);
    
    private static SunriseSunsetCalculator sunriseSunsetCalculator = new SunriseSunsetCalculator(location,
            Calendar.getInstance().getTimeZone().getID());

    /**
     * Gets the next door command to schedule.
     * 
     * @return next door command to schedule
     */
    static ScheduledCommand<DoorCommand> getNextDoorCommand() {
        ScheduledCommand<DoorCommand> scheduledCommand = new ScheduledCommand<>();
        Calendar currentTime = Calendar.getInstance();

        Calendar openingTime = Calendar.getInstance();
        openingTime.set(Calendar.HOUR_OF_DAY, 12);
        openingTime.set(Calendar.MINUTE, 0);
        openingTime.set(Calendar.SECOND, 0);
        openingTime.set(Calendar.MILLISECOND, 0);

        if (currentTime.compareTo(openingTime) <= 0) {
            scheduledCommand.setCommand(new DoorCommand(DoorCommand.Command.OPEN));
            scheduledCommand.setScheduledTime(openingTime.getTimeInMillis());
            return scheduledCommand;
        }

        Calendar closingTime = sunriseSunsetCalculator.getCivilSunsetCalendarForDate(currentTime);
        if (currentTime.compareTo(closingTime) <= 0) {
            scheduledCommand.setCommand(new DoorCommand(DoorCommand.Command.CLOSE));
            scheduledCommand.setScheduledTime(closingTime.getTimeInMillis());
            return scheduledCommand;
        }

        // If we made it here, the current time is after closing time, so the next event is
        // tomorrows opening time
        openingTime.add(Calendar.DATE, 1);
        scheduledCommand.setCommand(new DoorCommand(DoorCommand.Command.OPEN));
        scheduledCommand.setScheduledTime(openingTime.getTimeInMillis());
        return scheduledCommand;
    }

    /**
     * Gets the next light command to schedule.
     * 
     * @return next light command to schedule
     */
    static ScheduledCommand<LightCommand> getNextLightCommand() {
        ScheduledCommand<LightCommand> scheduledCommand = new ScheduledCommand<>();
        Calendar currentTime = Calendar.getInstance();

        Calendar onTime = sunriseSunsetCalculator.getCivilSunriseCalendarForDate(currentTime);

        if (currentTime.compareTo(onTime) <= 0) {
            scheduledCommand.setCommand(new LightCommand(LightCommand.Command.ON));
            scheduledCommand.setScheduledTime(onTime.getTimeInMillis());
            return scheduledCommand;
        }

        Calendar offTime = sunriseSunsetCalculator.getCivilSunsetCalendarForDate(currentTime);
        offTime.add(Calendar.MINUTE, 5);
        if (currentTime.compareTo(offTime) <= 0) {
            scheduledCommand.setCommand(new LightCommand(LightCommand.Command.OFF));
            scheduledCommand.setScheduledTime(offTime.getTimeInMillis());
            return scheduledCommand;
        }

        // If we made it here, the current time is after sunset, so the next event is tomorrows
        // on time
        currentTime.add(Calendar.DATE, 1);
        onTime = sunriseSunsetCalculator.getCivilSunriseCalendarForDate(currentTime);
        scheduledCommand.setCommand(new LightCommand(LightCommand.Command.ON));
        scheduledCommand.setScheduledTime(onTime.getTimeInMillis());
        return scheduledCommand;
    }
}
