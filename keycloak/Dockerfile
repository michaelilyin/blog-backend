FROM jboss/keycloak

ENV KEYCLOAK_USER=${USER}
ENV KEYCLOAK_PASSWORD=${PASSWORD}
ENV PROXY_ADDRESS_FORWARDING=true
ENV KEYCLOAK_LOGLEVEL=INFO

ADD entrypoint.sh entrypoint.sh
USER root
RUN chmod a+x entrypoint.sh
USER jboss

ENTRYPOINT ["./entrypoint.sh"]
CMD ["-b", "0.0.0.0"]