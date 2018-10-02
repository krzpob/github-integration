package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority 1
    description """ 
Should return 401 when try authorized whe is not needed
"""
    request{
        urlPath "/repos/krzpob/jug-factor"
        method GET()
        headers {
            header 'Authorization':'Basic a3J6cG9iQGdtYWlsLmNvbToyVG0yLDMtcXJ0'
        }

    }

    response{
        status 401
        headers {
            header('Content-Type':'application/json')
        }
    }
}