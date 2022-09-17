package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("even or odd value return")

    request {
        urlPath("/api/v1/callee") {
            queryParameters {
                parameter "num": value(1)
            }
        }
        method GET()
    }

    response {
        status OK()
        body(
                "odd val java"
        )
    }
}