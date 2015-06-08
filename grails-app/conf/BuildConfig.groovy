grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.server.port.http=80
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false
    // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins
//        mavenRepo "http://192.168.1.15:8080/nexus/content/groups/public/"
        mavenRepo "http://repo.grails.org/grails/core"
        mavenRepo "http://repo.grails.org/grails/plugins"
        mavenLocal()
        mavenCentral()
        grailsPlugins()
        grailsHome()
        grailsCentral()
//        mavenRepo "http://maven.oschina.net/content/groups/public/"
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
//        mavenRepo "http://snapshots.repository.codehaus.org"
//        mavenRepo "http://repository.codehaus.org"
//        mavenRepo "http://download.java.net/maven/2/"
//        mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

        // runtime 'mysql:mysql-connector-java:5.1.22'
        compile 'org.apache.httpcomponents:httpclient:4.4'
        compile 'org.jsoup:jsoup:1.8.2'
        compile 'org.codehaus.groovy.modules.http-builder:http-builder:0.5.2'
        compile 'net.sf.ezmorph:ezmorph:1.0.6'
//        compile 'net.sf.json-lib:json-lib:2.4'
//        compile 'xml-resolver:xml-resolver:1.2'
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.8.3"
        runtime ":resources:1.2"
        build ":tomcat:$grailsVersion"
        runtime ":database-migration:1.3.2"
        compile ':cache:1.0.1'
        compile ':quartz:1.0.2'
    }
}
