ssm����ֶ����û���

1����mybatis���򹤳̽�����bean��entity��

2.��ΪҪ���ɣ�����mybatis�ĵ��������ļ�mybatis-config���������ˣ�
����һ��springĬ�������ļ� applicationContext.xml
1>���Զ�ɨ��
<!--  	�Զ�ɨ��� -->
	<context:component-scan base-package="com.woniuxy"></context:component-scan>
2>ȡ��mybatis�ĵ��������ļ���
	���õ�³�����ӳ�ȥ�������ݿ�
	<!--  	��spring�������ݿ����ӣ��õ�³�����ӳ�����sql -->
	<bean id="ds" class="com.alibaba.druid.pool.DruidDataSource">
		<property name="driverClassName" value="com.mysql.jdbc.Driver"></property>
		<property name="url" value="jdbc:mysql:///test"></property>
		<property name="username" value="root"></property>
		<property name="password" value="123465"></property>
	</bean>
	
	����sqlSessionFactoryBean��ref��³�����ӳ�
	<bean id="sf" class="org.mybatis.spring.SqlSessionFactoryBean">  
		<property name="dataSource" ref="ds"></property>
		<property name="typeAliasesPackage" value="com.woniu.entity"></property>
	</bean>
	
	����ָ���������ļ���·��
	<!-- 
		ָ���������ļ���λ�ã������ͻ��Զ������ҵ��������ã�����̬���ɶ�Ӧ�ӿڵ�ʵ���࣬���������� 
		���磬��com.woniu.dao���ҵ���UserMapper.xml�ļ����ͻ��Զ�����һ���࣬ ȥʵ��UserMapper�ӿڣ�
		���ҰѸ�UserMapper��ʵ����Ķ��󣬴��������У�������@Autowired UserMapper mapper;����ע��ɹ��ˣ�
	-->
	<bean id="mapperScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<property name="basePackage" value="com.woniu.dao" />
	</bean>
3>��������ʽ���������DatasourceTransactionManager
	<bean id="TransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="ds"></property>
	</bean>
������<tx:annotation-driven/>	

