# Use Amazon Corretto 21 base image
FROM amazoncorretto:21

# Install tzdata package and set the time zone
RUN yum install -y tzdata

# Set the time zone to Asia/Jakarta
RUN ln -sf /usr/share/zoneinfo/Asia/Jakarta /etc/localtime && \
    echo "Asia/Jakarta" > /etc/timezone

COPY target/app-0.0.1-SNAPSHOT.jar app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xmx2g", "-Xms1g", "-jar", "app-0.0.1-SNAPSHOT.jar"]