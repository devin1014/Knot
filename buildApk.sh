#!/usr/bin/env bash

ls -l ./gradlew
chmod +x gradlew

./gradlew :app:clean
./gradlew :app:assembleAppRelease
./gradlew :app:assembleTabletRelease