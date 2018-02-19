FROM anapsix/alpine-java

ARG VERSION

ENV PORT=8080
ENV VERSION=${VERSION}

ADD build/libs/blog-backend-${VERSION}.jar blog-backend-${VERSION}.jar

ADD entrypoint.sh entrypoint.sh
RUN chmod a+x entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]