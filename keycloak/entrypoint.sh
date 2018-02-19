#!/bin/sh

export JAVA_OPTS='-server -Xms64m -Xmx256m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.net.preferIPv4Stack=true -Djboss.modules.system.pkgs=org.jboss.byteman -Djava.awt.headless=true'
exec /opt/jboss/docker-entrypoint.sh $@ -Djboss.http.port=${PORT}
