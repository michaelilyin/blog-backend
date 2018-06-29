import express from "express";
import bodyParser from "body-parser";
import cors from "cors";

import {graphiqlExpress, graphqlExpress} from "apollo-server-express";
import {GatewaySchemaBuilder} from './gateway-schema-builder';
import Eureka from 'eureka-client';

const PORT = process.env.PORT;

(async () => {
  console.info(`Starting gateway on ${PORT}`);

  const app = express();

  const client = new Eureka({
    instance: {
      app: 'gateway-service',
      port: {
        '$': process.env.PORT,
        '@enabled': 'true',
      },
      hostName: 'gateway-service',
      ipAddr: '127.0.0.1',
      vipAddress: 'jq.test.something.com',
      dataCenterInfo: {
        '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
        name: 'MyOwn',
      },
    },
    eureka: {
      serviceUrl: [
      //  http://localhost:8761/eureka/apps/
        process.env.EUREKA_URL
      ]
    },
  });

  client.once('registryUpdated', async () => {
    const schemaBuilder = new GatewaySchemaBuilder(client);

    app.use(cors());
    app.use("/graphql", bodyParser.json(), graphqlExpress({schema: await schemaBuilder.build()}));
    app.use("/graphiql", graphiqlExpress({endpointURL: '/graphql'}));

    app.listen(PORT, () => {
      console.log(`Gateway running on ${PORT}`);
    });
  });

  client.start();
})();