package main.java.com.ljh.com.ljh;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Hello world!
 *
 */
public class Produce 
{
	//activemq地址
	public static final String ACTIVEMQ_URL = "tcp://192.168.187.129:61616";
	
	//创建的队列的名称
	public static final String QUEUE_NAME = "queue02";
	
	public static void main(String[] args) throws JMSException
    {
       //1 创建连接工厂
    	ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
    	
    	//2 获取connection
    	Connection connection = activeMQConnectionFactory.createConnection();
    	connection.start();
    	
    	//3 创建会话session
    	//两个参数，第一个叫事务，第二个叫签收
    	//若事务设置为true开启，则需要session.commit()提交事务
    	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	
    	//4 创建目的地（具体是队列还是主题）
    	Queue queue = session.createQueue(QUEUE_NAME);
    	
    	//5 创建消息的生产者
    	MessageProducer producer = session.createProducer(queue);
    	
    	//此为设置数据的持久化，默认的是持久的
    	//producer.setDeliveryMode(DeliveryMode.PERSISTENT);
    	
    	//6 创建消息
    	for(int i =0;i<3;i++) {
    		TextMessage textMessage = session.createTextMessage("hello,this is message:"+i);
    		
    		//7 发送消息
        	producer.send(textMessage);
    	}	
    	
    	//8关闭资源
    	producer.close();
    	session.close();
    	connection.close();
    	
    	System.out.println("MQ发送消息成功！！");
    }
}
