package com.ohnet.elasticex.common;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {


    @Override
    public RestHighLevelClient elasticsearchClient() {
        final var config = ClientConfiguration.builder().connectedTo("localhost:9200").build();
        return RestClients.create(config).rest();
    }
}
