var ioc = {
	dataSource: {
		type: "com.alibaba.druid.pool.DruidDataSource",
		events: {
			depose: "close"
		},
		fields: {
			driverClassName: 'com.mysql.jdbc.Driver',
			url: 'jdbc:mysql://localhost:3306/chong?useUnicode=true&characterEncoding=UTF-8',
			username: 'mobile',
			password: 'XXXXXXXXXXXXXX',
			initialSize: 1,
			maxActive: 500,
			minIdle: 1,
			filters: 'wall,config',
			connectionProperties: 'config.decrypt=true;config.decrypt.key=XXXXXXXXXXXXXXXXXXX',
			defaultAutoCommit: false,

			//validationQueryTimeout : 5,
			validationQuery: "select 1"
		}
	},
	dao: {
		type: "org.nutz.dao.impl.NutDao",
		args: [{refer: "dataSource"}],
		fields: {
			executor: {refer: "cacheExecutor"}
		}
	},
	cacheExecutor: {
		type: "org.nutz.plugins.cache.dao.CachedNutDaoExecutor",
		fields: {
			cacheProvider: {refer: "cacheProvider"},
			cachedTableNames: [ // 任意N个表
				"tb_chanyue_common_notice"
			],
			//cachedTableNamePatten : ".+" // 也可以通过正则表达式来匹配
		}
	},
	cacheProvider: {
		type: "org.nutz.plugins.cache.dao.impl.provider.MemoryDaoCacheProvider",
		fields: {
			cacheSize: 10000 // 缓存的对象数
		},
		events: {
			create: "init"
		}
	}
};