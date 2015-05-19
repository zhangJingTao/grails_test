import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.PropertiesLoaderUtils

def properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource('datasource.properties'))

dataSource {
    pooled = true
    driverClassName = properties.getProperty("dataSource.driverClassName")
    username = properties.getProperty("dataSource.username")
    password = properties.getProperty("dataSource.password")
    url = properties.getProperty("dataSource.url")
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
        }
    }
    test {
        dataSource {
            dbCreate = "update"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
        }
    }
}
