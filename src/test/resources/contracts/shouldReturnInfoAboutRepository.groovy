package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description """
        should returrn info about games
"""
    request {
        urlPath "/repos/krzpob/jug-factor"
        method GET()
    }

    response {
        status 200
        headers {
            header('Content-Type':'application/json')
        }
        body """
        {
            "full_name": "krzpob/jug-factor",
            "description": "Lorem ipsum blabla",
            "clone_url": "https://github/test",
            "stargazers_count": 10
            
        }
"""
    }

}

Contract.make {
    description """
        should returrn info about games
"""
    request {
        urlPath "/repos/krzpob/jug-factor"
        method GET()
    }

    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body """
        {
            "full_name": "krzpob/jug-factor",
            "description": "Lorem ipsum blabla",
            "clone_url": "https://github/test",
            "stargazers_count": 10
            
        }
"""
    }

}

