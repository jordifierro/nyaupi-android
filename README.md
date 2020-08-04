# Nyaupi Android

![Nyaupi](images/nyaupi_art.png)

Nyaupi Android is a client application to remote control
[Nyaupi raspberry alarm](https://jordifierro.com/nyaupi-raspberry-alarm),
which is a handmade house alarm.

Basically, it connects with the alarm api, shows its status (active/inactive)
and lets the user switch that status with a single click.
It also handles errors and allows retries.

![Screenshot 1](images/nyaupi_android_1.png)
![Screenshot 2](images/nyaupi_android_2.png)
![Screenshot 3](images/nyaupi_android_3.png)

## Documentation

This project is pretty straighforward.

It shows the api status after making a call to `/status` endpoint.
User can switch the status with a switch button,
which represents a call to `/on` or `/off` endpoints.
Also, there is a secret token added to `Authorization` header.
[nyaupi-raspberry](github.com/jordifierro/nyaupi-raspberry)
has the code of the api on its `switch.py` file.
 
It uses kind of clean architecture (presenters and repos)
with a reactive approach.
Main used libraries are: RxJava2, Dagger 2, Retrofit and a custom switch button.
Made with kotlin.

## Setup

Just copy `app.properties.sample` to `app.properties` and replace the values for real ones.
Then play!
