package com.example.javanotifications.common.application.port.out;

public interface DomainEntityMapper<T, U> {
	public T toDomain(U entity);
	public U toEntity(T domain);

}
