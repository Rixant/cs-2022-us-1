package com.cs.rfq.decorator;

import com.cs.rfq.decorator.RfqProcessor;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.Arrays;

public class RfqDecoratorMain {

    public static void main(String[] args) throws Exception {
        System.setProperty("hadoop.home.dir", "C:\\Java\\hadoop-2.9.2");
        System.setProperty("spark.master", "local[4]");

        //TODO: create a Spark configuration and set a sensible app name

        SparkConf conf = new SparkConf().setAppName("StreamRFQ");


        //TODO: create a Spark streaming context
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(5));

        //TODO: create a Spark session

        SparkSession session = SparkSession.builder().config(conf).getOrCreate();


        //TODO: create a new RfqProcessor and set it listening for incoming RFQs
        RfqProcessor processor = new RfqProcessor(session, jssc);
        processor.startSocketListener();

    }

}
