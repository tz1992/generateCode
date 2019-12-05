#\u670d\u52a1\u914d\u7f6e\u4e2d\u5fc3
spring.profiles.active=${r'${app.profile:dev}'}

##config center
spring.cloud.config.uri=http://sc:8888
spring.cloud.config.profile=${r'${spring.profiles.active}'}
spring.cloud.config.label=
spring.cloud.config.failFast=true

##banner
banner.location=classpath:smartms.txt