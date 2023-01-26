package com.senla.internship.caveatemptor.shared;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

// Реализация PhysicalNamingStrategy, переопределяющая соглашения об именовании по умолчанию
public class CENamingStrategy extends PhysicalNamingStrategyStandardImpl {
    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment context) {
        return new Identifier("CE_ " + logicalName.getText(), logicalName.isQuoted());
    }
}
