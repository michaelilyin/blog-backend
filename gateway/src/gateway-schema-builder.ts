import {GraphQLSchema} from 'graphql';
import mergeSchemas from 'graphql-tools/dist/stitching/mergeSchemas';
import {
  introspectSchema,
  makeExecutableSchema,
  makeRemoteExecutableSchema,
  RenameRootFields,
  RenameTypes, transformSchema
} from 'graphql-tools';
import fetch from 'node-fetch';
import {HttpLink} from 'apollo-link-http';
import {Eureka} from 'eureka-client';

export class GatewaySchemaBuilder {

  constructor(private eureka: Eureka) {
  }

  public async build(): Promise<GraphQLSchema> {
    const ownSchema = this.createOwnSchema();

    // let authSchema = await this.createRemoteSchema('auth-service');
    // authSchema = this.patchSchema(authSchema, 'AuthService');

    let techSchema = await this.createRemoteSchema('tech-service');
    techSchema = this.patchSchema(techSchema, 'TechService');


    return mergeSchemas({
      schemas: [
        ownSchema,
        // authSchema,
        techSchema
      ]
    });
  }

  private async createRemoteSchema(service: string, iteration?: number): Promise<GraphQLSchema> {
    console.info('Fetch service', service, 'iteration', iteration);
    const services = this.eureka.getInstancesByAppId(service);
    if (!services.length) {
      console.warn(`Service ${services} is undefined`);
      if (iteration && iteration > 20) {
        return Promise.reject();
      }
      return new Promise<GraphQLSchema>(((resolve, reject) => {
        const timeout = iteration ? 1000 * iteration : 1000;
        console.info('Wait for service startup', timeout);
        setTimeout(() => {
          this.createRemoteSchema(service, iteration ? iteration + 1 : 1).then(resolve).catch(reject)
        }, timeout);
      }));
    }

    const ipAddr = services[0].ipAddr;
    const port = services[0].port.$;
    const url = `http://${ipAddr}:${port}/graphql`;
    console.info('fetch service', url);
    const link = new HttpLink({ uri: url, fetch });
    const schema = await introspectSchema(link);

    return makeRemoteExecutableSchema({
      schema,
      link
    });
  }

  private patchSchema(schema: GraphQLSchema, systemName: string) {
    return transformSchema(schema, [
      new RenameTypes((name: string) => (name === "StatusInfo" ? `${systemName}StatusInfo` : undefined)),
      new RenameRootFields(
        (_operation: string, name: string) =>
          name === 'status' ? `${systemName.substring(0, 1).toLowerCase()}${systemName.substring(1)}Status` : name
      )
    ]);
  }

  private createOwnSchema(): GraphQLSchema {
    const bootTime = Date.now();
    return makeExecutableSchema({
      typeDefs: `
      type GatewayServiceStatus {
        nodeJsVersion: String!
        uptime: Int!
      }
  
      type Query {
        gatewayServiceStatus: GatewayServiceStatus!
      }
    `,
      resolvers: {
        Query: {
          gatewayServiceStatus: () => ({
            nodeJsVersion: process.versions.node,
            uptime: Date.now() - bootTime,
          })
        }
      }
    });
  }

}