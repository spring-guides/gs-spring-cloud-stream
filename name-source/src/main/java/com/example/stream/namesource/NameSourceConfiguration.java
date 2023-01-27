package com.example.stream.namesource;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NameSourceConfiguration {

	// tag::supplyname[]
	@Bean
	public Supplier<String> supplyName() {
		return () -> "Christopher Pike";
	}
	// end::supplyname[]
}
