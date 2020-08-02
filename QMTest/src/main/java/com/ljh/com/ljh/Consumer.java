package main.java.com.ljh.com.ljh;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @description: 
 * @author: ljh
 * @date: Created in 2020年7月24日 下午1:00:07

 */
public class Consumer {
	
	//activemq地址
	public static final String ACTIVEMQ_URL = "tcp://192.168.187.129:61616";
	
	//创建的队列的名称
	public static final String QUEUE_NAME = "queue02";
	
	public static void main(String[] args) throws JMSException {
		
		//1 创建连接工厂
    	ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
    	
    	//2 获取connection
    	Connection connection = activeMQConnectionFactory.createConnection();
    	connection.start();
    	
    	//3 创建会话session
    	//两个参数，第一个叫事务，第二个叫签收
    	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	
    	//4 创建目的地（具体是队列还是主题）
    	Queue queue = session.createQueue(QUEUE_NAME);
    	
    	//5 创建消费者
    	MessageConsumer consumer = session.createConsumer(queue);
    	
    	//6 接收消息
    	while(true) {
    		//receive参数代表的是等待的时间，空参代表一直等待
    		TextMessage message = (TextMessage) consumer.receive(4000);
    		
    		if(message != null) {
    			System.out.println("消费者接受到的消息："+message.getText());
    		}else {
    			break;
    		}
    	}
    	
    	//7 关闭资源
    	consumer.close();
    	session.close();
    	connection.close();
    	
	}

}
