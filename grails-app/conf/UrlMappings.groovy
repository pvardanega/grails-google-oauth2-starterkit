class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/" (controller: "Home")
		"404" (view:'/error')
		"500" (view:'/error')
	}
}
