package com.visual.face.search.server.bootstrap.conf;

import com.visual.face.search.engine.api.SearchEngine;
import com.visual.face.search.engine.impl.ElasticSearchEngine;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration("visualEngineConfig")
public class EngineConfig {
    //日志
    public Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${visual.engine.open-search.host:localhost}")
    private String openSearchHost;
    @Value("${visual.engine.open-search.port:9200}")
    private Integer openSearchPort;
    @Value("${visual.engine.open-search.scheme:https}")
    private String openSearchScheme;
    @Value("${visual.engine.open-search.username:admin}")
    private String openSearchUserName;
    @Value("${visual.engine.open-search.password:admin}")
    private String openSearchPassword;

    @Value("${visual.engine.elastic-search.address:10.2.3.14:18115}")
    private String elasticSearchaddress;
    @Value("${visual.engine.elastic-search.scheme:http}")
    private String elasticSearchScheme;
    @Value("${visual.engine.elastic-search.username:elastic}")
    private String elasticSearchUserName;
    @Value("${visual.engine.elastic-search.password:Rootmaster@777}")
    private String elasticSearchPassword;

    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient restHighLevelClient() {
        // 构建连接对象
        RestClientBuilder builder = RestClient.builder(getEsHost());

        // 连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            /*requestConfigBuilder.setConnectTimeout(connectTimeOut);
            requestConfigBuilder.setSocketTimeout(socketTimeOut);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);*/
            return requestConfigBuilder;
        });

        // 连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            /*httpClientBuilder.setMaxConnTotal(maxConnectNum);
            httpClientBuilder.setMaxConnPerRoute(maxConnectNumPerRoute);*/
            httpClientBuilder.setDefaultCredentialsProvider(getCredentialsProvider());
            return httpClientBuilder;
        });

        return new RestHighLevelClient(builder);
    }

    @Bean(name = "visualSearchEngine")
    public SearchEngine simpleSearchEngine(RestHighLevelClient restHighLevelClient) {
        //构建client
        return  new ElasticSearchEngine(restHighLevelClient);
    }

    private HttpHost[] getEsHost() {
        // 拆分地址（es为多节点时，不同host以逗号间隔）
        List<HttpHost> hostLists = new ArrayList<>();
        String[] hostList = elasticSearchaddress.split(",");
        for (String addr : hostList) {
            String host = addr.split(":")[0];
            String port = addr.split(":")[1];
            hostLists.add(new HttpHost(host, Integer.parseInt(port), elasticSearchScheme));
        }
        // 转换成 HttpHost 数组
        return hostLists.toArray(new HttpHost[]{});
    }

    private CredentialsProvider getCredentialsProvider() {
        // 设置用户名、密码
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticSearchUserName, elasticSearchPassword));
        return credentialsProvider;
    }


}
