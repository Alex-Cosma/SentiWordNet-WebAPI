dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    username = "sa"
    password = ""
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
            logSql = true
            dbCreate = "update"
            url = "jdbc:mysql://localhost/sentiwordnet"
            username = "root"
            password = "root"

            removeAbandoned = "true"
            removeAbandonedTimeout = "120"
            logAbandoned = "true"
            testOnBorrow = "false"
            testOnReturn = "false"
            timeBetweenEvictionRunsMillis = "60000"
            numTestsPerEvictionRun = "5"
            minEvictableIdleTimeMillis = "30000"
            testWhileIdle = "true"
            validationQuery = "select now()"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
        dataSource {
            logSql = true
            dbCreate = "update"
            url = "jdbc:mysql://localhost/sentiwordnet"
            username = "root"
            password = "root"

            removeAbandoned = "true"
            removeAbandonedTimeout = "120"
            logAbandoned = "true"
            testOnBorrow = "false"
            testOnReturn = "false"
            timeBetweenEvictionRunsMillis = "60000"
            numTestsPerEvictionRun = "5"
            minEvictableIdleTimeMillis = "30000"
            testWhileIdle = "true"
            validationQuery = "select now()"
        }
    }
}
