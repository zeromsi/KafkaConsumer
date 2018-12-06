package com.msi.kafka.consumer;

import java.util.Arrays;
import java.util.HashMap;

import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.msi.kafka.consumer.service.KafkaConsumerService;

@SpringBootApplication
public class KafkaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerApplication.class, args);
		
		
		  String topicName = "FirstTopic";
	      String groupName = "RG";
       
          Properties props = new Properties();
          props.put("bootstrap.servers", "localhost:9092,localhost:9093");
          props.put("group.id", groupName);
          props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
          props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
          
          KafkaConsumer<String, String> consumer = null;
          consumer = new KafkaConsumer<>(props);   
          consumer.subscribe(Arrays.asList(topicName));
          try{
              while (true){
                  ConsumerRecords<String, String> records = consumer.poll(100);
                  for (ConsumerRecord<String, String> record : records){
                    //  System.out.println("Topic:"+ record.topic() +" Partition:" + record.partition() + " Offset:" + record.offset() + " Value:"+ record.value());
                      KafkaConsumerService kafkaConsumerService=new KafkaConsumerService();
                      kafkaConsumerService.getMessage("Topic:"+ record.topic() +" Partition:" + record.partition() + " Offset:" + record.offset() + " Value:"+ record.value());
                  }
                     
              }
          }catch(Exception ex){
              System.out.println("Exception.");
              ex.printStackTrace();
          }
          finally{
                  consumer.close();
          }
	}
	
	
}
