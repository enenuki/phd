/*  1:   */ package org.apache.log4j.or.jms;
/*  2:   */ 
/*  3:   */ import javax.jms.JMSException;
/*  4:   */ import javax.jms.Message;
/*  5:   */ import org.apache.log4j.helpers.LogLog;
/*  6:   */ import org.apache.log4j.or.ObjectRenderer;
/*  7:   */ 
/*  8:   */ public class MessageRenderer
/*  9:   */   implements ObjectRenderer
/* 10:   */ {
/* 11:   */   public String doRender(Object o)
/* 12:   */   {
/* 13:44 */     if ((o instanceof Message))
/* 14:   */     {
/* 15:45 */       StringBuffer sbuf = new StringBuffer();
/* 16:46 */       Message m = (Message)o;
/* 17:   */       try
/* 18:   */       {
/* 19:48 */         sbuf.append("DeliveryMode=");
/* 20:49 */         switch (m.getJMSDeliveryMode())
/* 21:   */         {
/* 22:   */         case 1: 
/* 23:51 */           sbuf.append("NON_PERSISTENT");
/* 24:52 */           break;
/* 25:   */         case 2: 
/* 26:54 */           sbuf.append("PERSISTENT");
/* 27:55 */           break;
/* 28:   */         default: 
/* 29:56 */           sbuf.append("UNKNOWN");
/* 30:   */         }
/* 31:58 */         sbuf.append(", CorrelationID=");
/* 32:59 */         sbuf.append(m.getJMSCorrelationID());
/* 33:   */         
/* 34:61 */         sbuf.append(", Destination=");
/* 35:62 */         sbuf.append(m.getJMSDestination());
/* 36:   */         
/* 37:64 */         sbuf.append(", Expiration=");
/* 38:65 */         sbuf.append(m.getJMSExpiration());
/* 39:   */         
/* 40:67 */         sbuf.append(", MessageID=");
/* 41:68 */         sbuf.append(m.getJMSMessageID());
/* 42:   */         
/* 43:70 */         sbuf.append(", Priority=");
/* 44:71 */         sbuf.append(m.getJMSPriority());
/* 45:   */         
/* 46:73 */         sbuf.append(", Redelivered=");
/* 47:74 */         sbuf.append(m.getJMSRedelivered());
/* 48:   */         
/* 49:76 */         sbuf.append(", ReplyTo=");
/* 50:77 */         sbuf.append(m.getJMSReplyTo());
/* 51:   */         
/* 52:79 */         sbuf.append(", Timestamp=");
/* 53:80 */         sbuf.append(m.getJMSTimestamp());
/* 54:   */         
/* 55:82 */         sbuf.append(", Type=");
/* 56:83 */         sbuf.append(m.getJMSType());
/* 57:   */       }
/* 58:   */       catch (JMSException e)
/* 59:   */       {
/* 60:93 */         LogLog.error("Could not parse Message.", e);
/* 61:   */       }
/* 62:95 */       return sbuf.toString();
/* 63:   */     }
/* 64:97 */     return o.toString();
/* 65:   */   }
/* 66:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.or.jms.MessageRenderer
 * JD-Core Version:    0.7.0.1
 */