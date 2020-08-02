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
 * @date: Created in 2020��7��24�� ����1:00:07

 */
public class Consumer {
	
	//activemq��ַ
	public static final String ACTIVEMQ_URL = "tcp://192.168.187.129:61616";
	
	//�����Ķ��е�����
	public static final String QUEUE_NAME = "queue02";
	
	public static void main(String[] args) throws JMSException {
		
		//1 �������ӹ���
    	ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
    	
    	//2 ��ȡconnection
    	Connection connection = activeMQConnectionFactory.createConnection();
    	connection.start();
    	
    	//3 �����Ựsession
    	//������������һ�������񣬵ڶ�����ǩ��
    	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	
    	//4 ����Ŀ�ĵأ������Ƕ��л������⣩
    	Queue queue = session.createQueue(QUEUE_NAME);
    	
    	//5 ����������
    	MessageConsumer consumer = session.createConsumer(queue);
    	
    	//6 ������Ϣ
    	while(true) {
    		//receive����������ǵȴ���ʱ�䣬�ղδ���һֱ�ȴ�
    		TextMessage message = (TextMessage) consumer.receive(4000);
    		
    		if(message != null) {
    			System.out.println("�����߽��ܵ�����Ϣ��"+message.getText());
    		}else {
    			break;
    		}
    	}
    	
    	//7 �ر���Դ
    	consumer.close();
    	session.close();
    	connection.close();
    	
	}

}
