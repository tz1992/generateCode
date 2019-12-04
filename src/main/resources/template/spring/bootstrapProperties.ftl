spring.profiles.active=${r'${app.profile:dev}'}

##config center
spring.cloud.config.uri=http://sc:8888
spring.cloud.config.profile=${r'${spring.profiles.active}'}
spring.cloud.config.label=
spring.cloud.config.failFast=true
