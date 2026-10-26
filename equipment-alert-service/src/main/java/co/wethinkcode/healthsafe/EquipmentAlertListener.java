package co.wethinkcode.healthsafe;

import co.wethinkcode.healthsafe.mq.MqConfig;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.lang.IllegalStateException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EquipmentAlertListener {
    private final AlertParser parser = new AlertParser();
    private final List<EquipmentAlert> alerts = new CopyOnWriteArrayList<>();
    private Connection connection;   // kept open on purpose

    public List<EquipmentAlert> alerts() {
        return alerts;
    }

    public void start() {
        try {
            connection = (Connection) new ActiveMQConnectionFactory(MqConfig.BROKER_URL).createConnection();
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue queue = session.createQueue(MqConfig.QUEUE);
            MessageConsumer consumer = session.createConsumer(queue);

            consumer.setMessageListener(message -> {
                System.out.println("Got a message: " + message.getClass().getSimpleName());
                try {
                    if (message instanceof TextMessage textMessage) {
                        try {
                            alerts.add(parser.parse(textMessage.getText()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Bad message: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Ignored a message that is not text");
                    }
                    message.acknowledge();
                } catch (JMSException e) {
                    System.out.println("Could not handle message: " + e.getMessage());
                }
            });

            connection.start();
        } catch (JMSException e) {
            throw new IllegalStateException("Could not listen on " + MqConfig.QUEUE, e);
        }
    }

}
