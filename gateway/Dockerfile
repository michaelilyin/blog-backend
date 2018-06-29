FROM node:carbon

WORKDIR /app

COPY package*.json ./

RUN npm install --only=production

COPY src /app/src
COPY index.js /app
COPY tsconfig.json /app

ENV PORT 8080

EXPOSE 8080
CMD [ "node", "index.js" ]