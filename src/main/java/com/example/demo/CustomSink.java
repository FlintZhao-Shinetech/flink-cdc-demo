package com.example.demo;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

public class CustomSink extends RichSinkFunction<String> {
    @Override
    public void invoke(String json, Context context){
        System.out.println(">>> "+json);
    }
    @Override
    public void open(Configuration parameters) throws Exception {
        // TODO Auto-generated method stub
        // super.open(parameters);
    }
    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        // super.close();
    }
}