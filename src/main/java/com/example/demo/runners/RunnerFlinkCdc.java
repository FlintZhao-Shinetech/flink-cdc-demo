package com.example.demo.runners;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.demo.config.CustomSink;
import com.example.demo.config.FlinkConfig;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;

@Component
public class RunnerFlinkCdc implements ApplicationRunner{
    @Autowired
    private FlinkConfig flinkConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        MySqlSource<String> source = MySqlSource.<String>builder().hostname(flinkConfig.getHost())
                .port(flinkConfig.getPort()).databaseList(flinkConfig.getDatabaseList()).tableList(flinkConfig.getTableList()).username(flinkConfig.getUsername()).password(flinkConfig.getPassword())
                .deserializer(new JsonDebeziumDeserializationSchema()).includeSchemaChanges(true).build();
        Configuration configuration=new Configuration();
        configuration.setInteger(RestOptions.PORT, 8081);
        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment(configuration);
        // 存档点
        env.enableCheckpointing(5000);
        DataStreamSink<String> sink=env.fromSource(source, WatermarkStrategy.noWatermarks(), "MySQL Source").addSink(new CustomSink());
        env.execute();
    }
}
