SET CATALINA_OPTS=-server -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8005
set JAVA_OPTS=-server -Xms128m -Xmx256m -XX:PermSize=128m -XX:MaxPermSize=256m
startup 