package com.example.demo;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;

public class MySqlSourceExample {
    public static void main(String[] args) throws Exception {
        MySqlSource<String> source = MySqlSource.<String>builder().hostname("mysql-dev")
                .port(3306).databaseList("wordpress").tableList("wordpress.*").username("root").password("nothing")
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
