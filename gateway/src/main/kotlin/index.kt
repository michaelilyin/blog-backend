import kotlin.js.Date
import kotlin.js.Json
import kotlin.js.json

external fun require(module: String): dynamic
external val __dirname: String
external val process: dynamic

val express = require("express")
val cors = require("cors")
val apolloServer = require("apollo-server-express")
val bodyParser = require("body-parser")
val graphqlTools = require("graphql-tools")

class Schema {
  val typeDefs =
    """
      type GatewayStatus {
        status: String!
      }

      type Query {
        gatewayStatus: GatewayStatus!
      }
"""
  val resolvers = json(
    Pair("Query", json(
      Pair("gatewayStatus", { json(Pair("status", "ok")) })
    ))
  )
}

fun createStitcherSchema(): Json {
  return graphqlTools.makeExecutableSchema(Schema())
}

fun main(args: Array<String>) {
  println("Starting gateway server")
  println("dirname $__dirname")

  process.on("warning", { e -> console.warn(e.stack) });
  process.on("error", { e -> console.warn(e.stack) });

  val port = 8081

  val app = express()

  app.use(cors())
  app.use("/graphql", bodyParser.json(), apolloServer.graphqlExpress(json(Pair("schema", createStitcherSchema()))))
  app.use("/graphiql", apolloServer.graphiqlExpress(json(Pair("endpointURL", "/graphql"))))

  app.listen(port, {
    println("Schema Stitcher  running on $port")
    println("Schema Stitcher  GraphiQL http://localhost:$port/graphiql")
  });
}