package com.example;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by benjaminliu on 7/7/16.
 */
@Configuration
public class DatabaseConfig {

	@Bean
	public Connection connection(@Value("${flyway.url}") final String flywayUrl) throws SQLException {
		return DriverManager.getConnection(flywayUrl, null, null);
	}

	@Bean
	@Autowired
	public DSLContext dslContext(final Connection connection) {
		return DSL.using(connection, SQLDialect.POSTGRES);
	}

}
