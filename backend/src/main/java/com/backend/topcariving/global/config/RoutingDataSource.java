package com.backend.topcariving.global.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		final boolean isReadable = TransactionSynchronizationManager
			.isCurrentTransactionReadOnly();

		if (isReadable) {
			return DatasourceConfig.SLAVE_DATA_SOURCE;
		}
		return DatasourceConfig.MASTER_DATA_SOURCE;
	}
}
