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

    let authSchema = await this.createRemoteSchema('auth-service');
    authSchema = this.patchSchema(authSchema, 'AuthService');

    let techSchema = await this.createRemoteSchema('tech-service');
    techSchema = this.patchSchema(techSchema, 'TechService');


    return mergeSchemas({
      schemas: [
        ownSchema,
        authSchema,
        techSchema
      ]
    });
  }

  private async createRemoteSchema(service: string): Promise<GraphQLSchema> {
    const services = this.eureka.getInstancesByAppId(service);
    if (!services.length) {
      console.warn(`Service ${services} is undefined`);
      return Promise.reject();
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