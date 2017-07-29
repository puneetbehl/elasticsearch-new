package grails.plugins.elasticsearch

import org.elasticsearch.client.Client
import org.springframework.beans.factory.FactoryBean

class ClientNodeFactoryBean implements FactoryBean {

    @Override
    Object getObject() throws Exception {
        return null
    }

    @Override
    Class<?> getObjectType() {
        return Client
    }

    @Override
    boolean isSingleton() {
        return true
    }
}
