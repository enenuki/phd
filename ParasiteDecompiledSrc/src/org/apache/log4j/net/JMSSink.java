/*   1:    */ package org.apache.log4j.net;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.InputStreamReader;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import javax.jms.Connection;
/*   7:    */ import javax.jms.JMSException;
/*   8:    */ import javax.jms.Message;
/*   9:    */ import javax.jms.MessageConsumer;
/*  10:    */ import javax.jms.MessageListener;
/*  11:    */ import javax.jms.ObjectMessage;
/*  12:    */ import javax.jms.Topic;
/*  13:    */ import javax.jms.TopicConnection;
/*  14:    */ import javax.jms.TopicConnectionFactory;
/*  15:    */ import javax.jms.TopicSession;
/*  16:    */ import javax.jms.TopicSubscriber;
/*  17:    */ import javax.naming.Context;
/*  18:    */ import javax.naming.InitialContext;
/*  19:    */ import javax.naming.NameNotFoundException;
/*  20:    */ import javax.naming.NamingException;
/*  21:    */ import org.apache.log4j.Category;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ import org.apache.log4j.PropertyConfigurator;
/*  24:    */ import org.apache.log4j.spi.LoggingEvent;
/*  25:    */ import org.apache.log4j.xml.DOMConfigurator;
/*  26:    */ 
/*  27:    */ public class JMSSink
/*  28:    */   implements MessageListener
/*  29:    */ {
/*  30: 49 */   static Logger logger = Logger.getLogger(JMSSink.class);
/*  31:    */   
/*  32:    */   public static void main(String[] args)
/*  33:    */     throws Exception
/*  34:    */   {
/*  35: 52 */     if (args.length != 5) {
/*  36: 53 */       usage("Wrong number of arguments.");
/*  37:    */     }
/*  38: 56 */     String tcfBindingName = args[0];
/*  39: 57 */     String topicBindingName = args[1];
/*  40: 58 */     String username = args[2];
/*  41: 59 */     String password = args[3];
/*  42:    */     
/*  43:    */ 
/*  44: 62 */     String configFile = args[4];
/*  45: 64 */     if (configFile.endsWith(".xml")) {
/*  46: 65 */       DOMConfigurator.configure(configFile);
/*  47:    */     } else {
/*  48: 67 */       PropertyConfigurator.configure(configFile);
/*  49:    */     }
/*  50: 70 */     new JMSSink(tcfBindingName, topicBindingName, username, password);
/*  51:    */     
/*  52: 72 */     BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
/*  53:    */     
/*  54: 74 */     System.out.println("Type \"exit\" to quit JMSSink.");
/*  55:    */     for (;;)
/*  56:    */     {
/*  57: 76 */       String s = stdin.readLine();
/*  58: 77 */       if (s.equalsIgnoreCase("exit"))
/*  59:    */       {
/*  60: 78 */         System.out.println("Exiting. Kill the application if it does not exit due to daemon threads.");
/*  61:    */         
/*  62: 80 */         return;
/*  63:    */       }
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public JMSSink(String tcfBindingName, String topicBindingName, String username, String password)
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71: 89 */       Context ctx = new InitialContext();
/*  72:    */       
/*  73: 91 */       TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory)lookup(ctx, tcfBindingName);
/*  74:    */       
/*  75:    */ 
/*  76: 94 */       TopicConnection topicConnection = topicConnectionFactory.createTopicConnection(username, password);
/*  77:    */       
/*  78:    */ 
/*  79: 97 */       topicConnection.start();
/*  80:    */       
/*  81: 99 */       TopicSession topicSession = topicConnection.createTopicSession(false, 1);
/*  82:    */       
/*  83:    */ 
/*  84:102 */       Topic topic = (Topic)ctx.lookup(topicBindingName);
/*  85:    */       
/*  86:104 */       TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);
/*  87:    */       
/*  88:106 */       topicSubscriber.setMessageListener(this);
/*  89:    */     }
/*  90:    */     catch (JMSException e)
/*  91:    */     {
/*  92:109 */       logger.error("Could not read JMS message.", e);
/*  93:    */     }
/*  94:    */     catch (NamingException e)
/*  95:    */     {
/*  96:111 */       logger.error("Could not read JMS message.", e);
/*  97:    */     }
/*  98:    */     catch (RuntimeException e)
/*  99:    */     {
/* 100:113 */       logger.error("Could not read JMS message.", e);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void onMessage(Message message)
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:122 */       if ((message instanceof ObjectMessage))
/* 109:    */       {
/* 110:123 */         ObjectMessage objectMessage = (ObjectMessage)message;
/* 111:124 */         LoggingEvent event = (LoggingEvent)objectMessage.getObject();
/* 112:125 */         Logger remoteLogger = Logger.getLogger(event.getLoggerName());
/* 113:126 */         remoteLogger.callAppenders(event);
/* 114:    */       }
/* 115:    */       else
/* 116:    */       {
/* 117:128 */         logger.warn("Received message is of type " + message.getJMSType() + ", was expecting ObjectMessage.");
/* 118:    */       }
/* 119:    */     }
/* 120:    */     catch (JMSException jmse)
/* 121:    */     {
/* 122:132 */       logger.error("Exception thrown while processing incoming message.", jmse);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   protected static Object lookup(Context ctx, String name)
/* 127:    */     throws NamingException
/* 128:    */   {
/* 129:    */     try
/* 130:    */     {
/* 131:140 */       return ctx.lookup(name);
/* 132:    */     }
/* 133:    */     catch (NameNotFoundException e)
/* 134:    */     {
/* 135:142 */       logger.error("Could not find name [" + name + "].");
/* 136:143 */       throw e;
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   static void usage(String msg)
/* 141:    */   {
/* 142:148 */     System.err.println(msg);
/* 143:149 */     System.err.println("Usage: java " + JMSSink.class.getName() + " TopicConnectionFactoryBindingName TopicBindingName username password configFile");
/* 144:    */     
/* 145:151 */     System.exit(1);
/* 146:    */   }
/* 147:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.net.JMSSink
 * JD-Core Version:    0.7.0.1
 */