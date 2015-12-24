# coop-webapp
This is a POC (Proof Of Concept) web application for controlling / monitoring a chicken coop from a Raspberry Pi.

## Functionality
This web application provides the following functionality:
* REST API for monitoring and controlling a chicken coop via a [coop-alamode](https://github.com/seanano/coop-alamode).
  * Endpoints for door control (open and close)
  * Endpoints for light control (on and off)
  * Endpoints for current coop state (door, light, embedded uptime)
* Scheduling service for the coop to open/close the door and turn a light on and off at scheduled times.
  * Schedule is currently static and not settable via the REST API.
  * Light turns on at civil sunrise and off at civil sunset plus 5 minutes.
  * Door opens at noon and closes at civil sunset.
    * There are potential dangers in the morning so we don't want the door to open too early unless we have verified the area is secure. The noon opening is a failsafe in case we forget to let the chickens out in the morning.  Otherwise, I would have the door open at civil sunrise.
* Simple web page for viewing current state of coop, viewing streaming webcam, and providing manual controls of light and door.
* No security - This web application is a POC and has no security provisions.  Do not make this externally available if you value your chickens or hardware.

## Software Prerequisites
* OS on the PI - I'm using [Raspbian](https://www.raspberrypi.org/downloads/raspbian/) Wheezy as I started this project before Jessie was released and it is not externally accessible.
* Servlet container - for running the web application. Some examples are [Jetty](http://www.eclipse.org/jetty/) or [Tomcat](http://tomcat.apache.org/).
* [mjpg-streamer](http://sourceforge.net/projects/mjpg-streamer/) - for streaming the camera view back to the browser.

## Software Dependencies
* [Pi4J](http://pi4j.com/) - Provides Java bindings for access to the I2C bus (among other things) on the Pi
* [Jersey](https://jersey.java.net/) - Provides a REST API framework to simplify implementation. I'm using the Jersey provided Jackson for object mapping duties (Java beans <-> JSON)
* [sunrisesunsetlib-java](http://mikereedell.github.io/sunrisesunsetlib-java/) - Provides the calculations for determining sunrise / sunset times

## Building
This is a [gradle](http://gradle.org/) project and supports the [gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html), so after cloning the repository just run "sh gradlew war" on unix-based systems or "gradlew.bat war" on Windows in the CoopControl directory to generate the war file to use in your servlet container (CoopControl/build/libs/CoopControl.war). This will download gradle (if necessary), download any dependencies (if necessary), and then build the war file.

Note: In order for the sunrise / sunset calculations to be accurate, you will need to set the coop's latitude/longitude in CoopControl/src/main/java/org/seanano/coop/hardware/CoopScheduler.java.  Otherwise you will get sunrise/sunset calculations for 0 degrees latitude and 0 degrees longitude which are probably not appropriate unless your coop is on a boat in Gulf of Guinea.

## Hardware needed
* Raspberry Pi - I'm using a Model B with satisfactory results.  Using other models may require changing the I2C bus used. A Pi 2 would allow for better performance if mjpg-streamer has to software encode the video from the camera.
* Camera - I'm using a Logitech C310 USB webcam. I don't currently recommend this model as mjpg-streamer has issues using the raw mjpeg mode so I have to use YUV mode and convert which increases the CPU and USB bus load. It should be fixable as others report this working, but I haven't delved too deep into this yet.
* Alamode for Raspberry Pi - I'm using this as the interface between the Pi and the controls for the coop. See https://github.com/seanano/coop-alamode for more details on the firmware and hardware needed for this.  I'm also using the RTC on this board for the Pi in case it powers up with no external network connection for NTP.
