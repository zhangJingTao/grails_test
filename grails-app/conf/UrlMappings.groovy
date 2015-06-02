class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/layouts/main")
        "500"(view: '/error')
        "404"(view: '/error')
    }
}
