package it.unisalento.pas2425.positionservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopicConfig {

    public static final String TOPIC_EXCHANGE_NAME = "topic-exchange";
    public static final String QUEUE_ERROR_LOGS = "queue-error-logs";
    public static final String QUEUE_ALL_LOGS = "queue-all-logs";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Queue queueErrorLogsTopic() {
        return new Queue(QUEUE_ERROR_LOGS, false);
    }

    @Bean
    public Queue queueAllLogsTopic() {
        return new Queue(QUEUE_ALL_LOGS, false);
    }

    @Bean
    public Binding bindingErrorLogsTopic(Queue queueErrorLogsTopic, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueErrorLogsTopic).to(topicExchange).with("log.error");
    }

    @Bean
    public Binding bindingAllLogsTopic(Queue queueAllLogsTopic, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueAllLogsTopic).to(topicExchange).with("log.*");
    }

}

