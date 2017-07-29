package grails.plugins.elasticsearch

import groovy.util.logging.Slf4j
import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.node.Node
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.springframework.beans.factory.FactoryBean

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Slf4j
class ClientNodeFactoryBean implements FactoryBean {

    Node node

    @Override
    Object getObject() throws Exception {
        Settings settings = Settings.builder().
                put('cluster.name', 'elasticsearch').
                put('path.home', tmpDirectory()).
                put('transport.type', 'local').
                put('http.enabled', false).
                build()
        List plugins = []
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY, plugins)
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))
        log.info("Connecting to client ... ")
        client
    }

    @Override
    Class<?> getObjectType() {
        Client
    }

    @Override
    boolean isSingleton() {
        return true
    }

    def shutdown() {
        node?.close()        // close() seems to be more appropriate than stop()
    }
    private String tmpDirectory() {
        String baseDirectory = System.getProperty("java.io.tmpdir") ?: '/tmp'
        Path path = Files.createTempDirectory(Paths.get(baseDirectory), 'elastic-data-' + new Date().time)
        File file = path.toFile()
        file.deleteOnExit()
        return file.absolutePath
    }
}
