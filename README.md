# Wearable Presenter
Presentation controller for smart watches that run Android Wear OS, designed to take control of every single aspect regarding a presentation: from slides management to scheduled notifications. This project was implemented with the sole purpose of vanishing the need of manual pointers, therefore enhancing body language through the freedom of hands.

The implemented functionalities are the following:
* **Connection through wifi** to the PC that hosts the presentation.
* **Slides management**, allowing to move forward and backwards through the slides of the presentation.
* **Scheduled vibrating notifications** so that the user can set certain timeouts and the watch will silently vibrate passed those deadlines, therefore allowing control of the time flow during the presentation without even looking at the watch.
* **Multiplatform support**, allowing any OS in the PC that hosts the presentation (Windows, MAC, Linux, ChromeOS, etc) as well as any presentation platform (PowerPoint, Keynote, Adobe Acrobat Reader, Prezi, etc).

# Instructions
To install the client just go to the "Install" section bellow or search for "Wearable Presenter" in the Google Play Store. To install the server download the "WatchSliderServer.jar" and place it into an easily reachable path. To deploy it just open a command line and type "java -jar WatchSliderServer.jar" and it'll start accepting connections from the watch app. 

## Demo

### Connection to server
![](./snapshots/config_ip.gif)

### Timing configuration 1/2
![](./snapshots/config_timing_1.gif)

### Timing configuration 2/2
![](./snapshots/config_timing_2.gif)

### Final presentation
![](./snapshots/presentation.gif)

## Install
[1] https://play.google.com/store/apps/details?id=com.faridarbai.watchsliderbeta
