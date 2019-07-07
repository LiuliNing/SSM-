ssm框架手动配置环境

1，用mybatis逆向工程建立好bean和entity包

2.因为要集成，所以mybatis的的主配置文件mybatis-config不用配置了，
建立一个spring默认配置文件 applicationContext.xml
1>先自动扫包
<!--  	自动扫描包 -->
	<context:component-scan base-package="com.woniuxy"></context:component-scan>
2>取代mybatis的的主配置文件，
	配置德鲁伊连接池去连接数据库
	<!--  	让spring管理数据库连接，让德鲁伊连接池配置sql -->
	<bean id="ds" class="com.alibaba.druid.pool.DruidDataSource">
		<property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
		<property name="url" value="jdbc:mysql:///test"></property>
		<property name="username" value="root"></property>
		<property name="password" value="123465"></property>
	</bean>
	
	配置sqlSessionFactoryBean，ref德鲁伊连接池
	<bean id="sf" class="org.mybatis.spring.SqlSessionFactoryBean">  
		<property name="dataSource" ref="ds"></property>
		<property name="typeAliasesPackage" value="com.woniu.entity"></property>
	</bean>
	
	配置指定子配置文件的路径
	<!-- 
		指定子配置文件的位置，工厂就会自动根据找到的子配置，来动态生成对应接口的实现类，并让入容器 
		比如，在com.woniu.dao下找到了UserMapper.xml文件，就会自动创建一个类， 去实现UserMapper接口！
		并且把该UserMapper的实现类的对象，存入容器中，这样在@Autowired UserMapper mapper;就能注入成功了！
	-->
	<bean id="mapperScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.woniu.dao" />
	</bean>
3>配置声明式事务管理器DatasourceTransactionManager
	<bean id="TransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="ds"></property>
	</bean>
并启用<tx:annotation-driven/>	

