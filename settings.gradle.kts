rootProject.name = "blog-backend"

include("tools:starters:graphql-starter")

//include(":services:auth-service")
include("services:tech-service")
include("services:welcome-service")
//include(":services:blog-service")

include("services:composite")
