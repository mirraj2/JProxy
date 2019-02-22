#!/bin/sh

screen -S jproxy -X quit
screen -S jproxy -d -m java -jar server.jar
echo Server started.
