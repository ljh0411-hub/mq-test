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
	//activemq��ַ
	public static final String ACTIVEMQ_URL = "tcp://192.168.187.129:61616";
	
	//�����Ķ��е�����
	public static final String QUEUE_NAME = "queue02";
	
	public static void main(String[] args) throws JMSException
    {
       //1 �������ӹ���
    	ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
    	
    	//2 ��ȡconnection
    	Connection connection = activeMQConnectionFactory.createConnection();
    	connection.start();
    	
    	//3 �����Ựsession
    	//������������һ�������񣬵ڶ�����ǩ��
    	//����������Ϊtrue����������Ҫsession.commit()�ύ����
    	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    	
    	//4 ����Ŀ�ĵأ������Ƕ��л������⣩
    	Queue queue = session.createQueue(QUEUE_NAME);
    	
    	//5 ������Ϣ��������
    	MessageProducer producer = session.createProducer(queue);
    	
    	//��Ϊ�������ݵĳ־û���Ĭ�ϵ��ǳ־õ�
    	//producer.setDeliveryMode(DeliveryMode.PERSISTENT);
    	
    	//6 ������Ϣ
    	for(int i =0;i<3;i++) {
    		TextMessage textMessage = session.createTextMessage("hello,this is message:"+i);
    		
    		//7 ������Ϣ
        	producer.send(textMessage);
    	}	
    	
    	//8�ر���Դ
    	producer.close();
    	session.close();
    	connection.close();
    	
    	System.out.println("MQ������Ϣ�ɹ�����");
    }
}
