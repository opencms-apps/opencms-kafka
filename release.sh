./gradlew clean
./gradlew Jar
rm -f dependencies/*.jar
./gradlew finalRelease

echo "" > ~/servers/tomcat-9.0.62/webapps/ROOT/WEB-INF/logs/opencms.log
rm -f ~/servers/tomcat-9.0.62/webapps/ROOT/WEB-INF/lib/opencms-kafka-1.0.jar
cp build/libs/opencms-kafka-1.0.jar ~/servers/tomcat-9.0.62/webapps/ROOT/WEB-INF/lib/opencms-kafka-1.0.jar