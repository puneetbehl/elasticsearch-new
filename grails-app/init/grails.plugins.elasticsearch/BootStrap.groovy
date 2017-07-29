package grails.plugins.elasticsearch

import org.elasticsearch.client.Client

class BootStrap {

    Client elasticSearchClient

    def init = { servletContext ->
        elasticSearchClient.index {
            index "my_index"
            type "my_type"
            // Note: The ID is completely optional and a
            //  unique one will be generated on the server
            id "my_id"
            source {
                user = "kimchy"
                postDate = new Date()
                message = "trying out Elasticsearch"
                nested {
                    details {
                        here = 123
                        timestamp = new Date()
                    }
                }
            }
        }.actionGet()
        elasticSearchClient.close()
    }
    def destroy = {
    }
}
