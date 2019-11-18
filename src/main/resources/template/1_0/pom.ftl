<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<parent>
		<groupId>com.fiberhome.ms</groupId>
		<artifactId>fae-ms-dependencies</artifactId>
		<version>1.0.0-SNAPSHOT</version>
		<relativePath />
	</parent>

	<artifactId>${project.name}</artifactId>
	<packaging>jar</packaging>

	<name>${project.name}</name>
	

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<java.version>1.8</java.version>
	</properties>


	<dependencies>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix</artifactId>
		</dependency>


		<dependency>
			<groupId>com.fiberhome.smartms</groupId>
			<artifactId>smartms-spring-boot-starter-cache</artifactId>
		</dependency>
		<dependency>
			<groupId>com.fiberhome.smartms</groupId>
			<artifactId>smartms-spring-boot-starter-session</artifactId>
		</dependency>
		<dependency>
			<groupId>com.fiberhome.smartms</groupId>
			<artifactId>smartms-spring-boot-starter-dao</artifactId>
		</dependency>
		<dependency>
			<groupId>com.fiberhome.smartms</groupId>
			<artifactId>smartms-spring-boot-starter-service</artifactId>
		</dependency>
		<dependency>
			<groupId>com.fiberhome.smartms</groupId>
			<artifactId>smartms-spring-boot-starter-ui</artifactId>
		</dependency>
		<dependency>
			<groupId>com.fiberhome.smartms</groupId>
			<artifactId>smartms-spring-boot-starter-web-support</artifactId>
		</dependency>

	</dependencies>

	<build>
		<finalName>${"$"}{project.artifactId}</finalName>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>
