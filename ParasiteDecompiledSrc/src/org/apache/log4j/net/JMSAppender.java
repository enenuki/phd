/*   1:    */ package org.apache.log4j.net;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.Properties;
/*   5:    */ import javax.jms.Connection;
/*   6:    */ import javax.jms.JMSException;
/*   7:    */ import javax.jms.ObjectMessage;
/*   8:    */ import javax.jms.Session;
/*   9:    */ import javax.jms.Topic;
/*  10:    */ import javax.jms.TopicConnection;
/*  11:    */ import javax.jms.TopicConnectionFactory;
/*  12:    */ import javax.jms.TopicPublisher;
/*  13:    */ import javax.jms.TopicSession;
/*  14:    */ import javax.naming.Context;
/*  15:    */ import javax.naming.InitialContext;
/*  16:    */ import javax.naming.NameNotFoundException;
/*  17:    */ import javax.naming.NamingException;
/*  18:    */ import org.apache.log4j.AppenderSkeleton;
/*  19:    */ import org.apache.log4j.helpers.LogLog;
/*  20:    */ import org.apache.log4j.spi.ErrorHandler;
/*  21:    */ import org.apache.log4j.spi.LoggingEvent;
/*  22:    */ 
/*  23:    */ public class JMSAppender
/*  24:    */   extends AppenderSkeleton
/*  25:    */ {
/*  26:    */   String securityPrincipalName;
/*  27:    */   String securityCredentials;
/*  28:    */   String initialContextFactoryName;
/*  29:    */   String urlPkgPrefixes;
/*  30:    */   String providerURL;
/*  31:    */   String topicBindingName;
/*  32:    */   String tcfBindingName;
/*  33:    */   String userName;
/*  34:    */   String password;
/*  35:    */   boolean locationInfo;
/*  36:    */   TopicConnection topicConnection;
/*  37:    */   TopicSession topicSession;
/*  38:    */   TopicPublisher topicPublisher;
/*  39:    */   
/*  40:    */   public void setTopicConnectionFactoryBindingName(String tcfBindingName)
/*  41:    */   {
/*  42:130 */     this.tcfBindingName = tcfBindingName;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getTopicConnectionFactoryBindingName()
/*  46:    */   {
/*  47:138 */     return this.tcfBindingName;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setTopicBindingName(String topicBindingName)
/*  51:    */   {
/*  52:148 */     this.topicBindingName = topicBindingName;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getTopicBindingName()
/*  56:    */   {
/*  57:156 */     return this.topicBindingName;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean getLocationInfo()
/*  61:    */   {
/*  62:166 */     return this.locationInfo;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void activateOptions()
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69:178 */       LogLog.debug("Getting initial context.");
/*  70:    */       Context jndi;
/*  71:    */       Context jndi;
/*  72:179 */       if (this.initialContextFactoryName != null)
/*  73:    */       {
/*  74:180 */         Properties env = new Properties();
/*  75:181 */         env.put("java.naming.factory.initial", this.initialContextFactoryName);
/*  76:182 */         if (this.providerURL != null) {
/*  77:183 */           env.put("java.naming.provider.url", this.providerURL);
/*  78:    */         } else {
/*  79:185 */           LogLog.warn("You have set InitialContextFactoryName option but not the ProviderURL. This is likely to cause problems.");
/*  80:    */         }
/*  81:188 */         if (this.urlPkgPrefixes != null) {
/*  82:189 */           env.put("java.naming.factory.url.pkgs", this.urlPkgPrefixes);
/*  83:    */         }
/*  84:192 */         if (this.securityPrincipalName != null)
/*  85:    */         {
/*  86:193 */           env.put("java.naming.security.principal", this.securityPrincipalName);
/*  87:194 */           if (this.securityCredentials != null) {
/*  88:195 */             env.put("java.naming.security.credentials", this.securityCredentials);
/*  89:    */           } else {
/*  90:197 */             LogLog.warn("You have set SecurityPrincipalName option but not the SecurityCredentials. This is likely to cause problems.");
/*  91:    */           }
/*  92:    */         }
/*  93:201 */         jndi = new InitialContext(env);
/*  94:    */       }
/*  95:    */       else
/*  96:    */       {
/*  97:203 */         jndi = new InitialContext();
/*  98:    */       }
/*  99:206 */       LogLog.debug("Looking up [" + this.tcfBindingName + "]");
/* 100:207 */       TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory)lookup(jndi, this.tcfBindingName);
/* 101:208 */       LogLog.debug("About to create TopicConnection.");
/* 102:209 */       if (this.userName != null) {
/* 103:210 */         this.topicConnection = topicConnectionFactory.createTopicConnection(this.userName, this.password);
/* 104:    */       } else {
/* 105:213 */         this.topicConnection = topicConnectionFactory.createTopicConnection();
/* 106:    */       }
/* 107:216 */       LogLog.debug("Creating TopicSession, non-transactional, in AUTO_ACKNOWLEDGE mode.");
/* 108:    */       
/* 109:218 */       this.topicSession = this.topicConnection.createTopicSession(false, 1);
/* 110:    */       
/* 111:    */ 
/* 112:221 */       LogLog.debug("Looking up topic name [" + this.topicBindingName + "].");
/* 113:222 */       Topic topic = (Topic)lookup(jndi, this.topicBindingName);
/* 114:    */       
/* 115:224 */       LogLog.debug("Creating TopicPublisher.");
/* 116:225 */       this.topicPublisher = this.topicSession.createPublisher(topic);
/* 117:    */       
/* 118:227 */       LogLog.debug("Starting TopicConnection.");
/* 119:228 */       this.topicConnection.start();
/* 120:    */       
/* 121:230 */       jndi.close();
/* 122:    */     }
/* 123:    */     catch (JMSException e)
/* 124:    */     {
/* 125:232 */       this.errorHandler.error("Error while activating options for appender named [" + this.name + "].", e, 0);
/* 126:    */     }
/* 127:    */     catch (NamingException e)
/* 128:    */     {
/* 129:235 */       this.errorHandler.error("Error while activating options for appender named [" + this.name + "].", e, 0);
/* 130:    */     }
/* 131:    */     catch (RuntimeException e)
/* 132:    */     {
/* 133:238 */       this.errorHandler.error("Error while activating options for appender named [" + this.name + "].", e, 0);
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected Object lookup(Context ctx, String name)
/* 138:    */     throws NamingException
/* 139:    */   {
/* 140:    */     try
/* 141:    */     {
/* 142:245 */       return ctx.lookup(name);
/* 143:    */     }
/* 144:    */     catch (NameNotFoundException e)
/* 145:    */     {
/* 146:247 */       LogLog.error("Could not find name [" + name + "].");
/* 147:248 */       throw e;
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   protected boolean checkEntryConditions()
/* 152:    */   {
/* 153:253 */     String fail = null;
/* 154:255 */     if (this.topicConnection == null) {
/* 155:256 */       fail = "No TopicConnection";
/* 156:257 */     } else if (this.topicSession == null) {
/* 157:258 */       fail = "No TopicSession";
/* 158:259 */     } else if (this.topicPublisher == null) {
/* 159:260 */       fail = "No TopicPublisher";
/* 160:    */     }
/* 161:263 */     if (fail != null)
/* 162:    */     {
/* 163:264 */       this.errorHandler.error(fail + " for JMSAppender named [" + this.name + "].");
/* 164:265 */       return false;
/* 165:    */     }
/* 166:267 */     return true;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public synchronized void close()
/* 170:    */   {
/* 171:277 */     if (this.closed) {
/* 172:278 */       return;
/* 173:    */     }
/* 174:280 */     LogLog.debug("Closing appender [" + this.name + "].");
/* 175:281 */     this.closed = true;
/* 176:    */     try
/* 177:    */     {
/* 178:284 */       if (this.topicSession != null) {
/* 179:285 */         this.topicSession.close();
/* 180:    */       }
/* 181:286 */       if (this.topicConnection != null) {
/* 182:287 */         this.topicConnection.close();
/* 183:    */       }
/* 184:    */     }
/* 185:    */     catch (JMSException e)
/* 186:    */     {
/* 187:289 */       LogLog.error("Error while closing JMSAppender [" + this.name + "].", e);
/* 188:    */     }
/* 189:    */     catch (RuntimeException e)
/* 190:    */     {
/* 191:291 */       LogLog.error("Error while closing JMSAppender [" + this.name + "].", e);
/* 192:    */     }
/* 193:294 */     this.topicPublisher = null;
/* 194:295 */     this.topicSession = null;
/* 195:296 */     this.topicConnection = null;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void append(LoggingEvent event)
/* 199:    */   {
/* 200:303 */     if (!checkEntryConditions()) {
/* 201:304 */       return;
/* 202:    */     }
/* 203:    */     try
/* 204:    */     {
/* 205:308 */       ObjectMessage msg = this.topicSession.createObjectMessage();
/* 206:309 */       if (this.locationInfo) {
/* 207:310 */         event.getLocationInformation();
/* 208:    */       }
/* 209:312 */       msg.setObject(event);
/* 210:313 */       this.topicPublisher.publish(msg);
/* 211:    */     }
/* 212:    */     catch (JMSException e)
/* 213:    */     {
/* 214:315 */       this.errorHandler.error("Could not publish message in JMSAppender [" + this.name + "].", e, 0);
/* 215:    */     }
/* 216:    */     catch (RuntimeException e)
/* 217:    */     {
/* 218:318 */       this.errorHandler.error("Could not publish message in JMSAppender [" + this.name + "].", e, 0);
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   public String getInitialContextFactoryName()
/* 223:    */   {
/* 224:329 */     return this.initialContextFactoryName;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setInitialContextFactoryName(String initialContextFactoryName)
/* 228:    */   {
/* 229:342 */     this.initialContextFactoryName = initialContextFactoryName;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String getProviderURL()
/* 233:    */   {
/* 234:346 */     return this.providerURL;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setProviderURL(String providerURL)
/* 238:    */   {
/* 239:350 */     this.providerURL = providerURL;
/* 240:    */   }
/* 241:    */   
/* 242:    */   String getURLPkgPrefixes()
/* 243:    */   {
/* 244:354 */     return this.urlPkgPrefixes;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setURLPkgPrefixes(String urlPkgPrefixes)
/* 248:    */   {
/* 249:358 */     this.urlPkgPrefixes = urlPkgPrefixes;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public String getSecurityCredentials()
/* 253:    */   {
/* 254:362 */     return this.securityCredentials;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setSecurityCredentials(String securityCredentials)
/* 258:    */   {
/* 259:366 */     this.securityCredentials = securityCredentials;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public String getSecurityPrincipalName()
/* 263:    */   {
/* 264:371 */     return this.securityPrincipalName;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void setSecurityPrincipalName(String securityPrincipalName)
/* 268:    */   {
/* 269:375 */     this.securityPrincipalName = securityPrincipalName;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public String getUserName()
/* 273:    */   {
/* 274:379 */     return this.userName;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setUserName(String userName)
/* 278:    */   {
/* 279:390 */     this.userName = userName;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public String getPassword()
/* 283:    */   {
/* 284:394 */     return this.password;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public void setPassword(String password)
/* 288:    */   {
/* 289:401 */     this.password = password;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setLocationInfo(boolean locationInfo)
/* 293:    */   {
/* 294:410 */     this.locationInfo = locationInfo;
/* 295:    */   }
/* 296:    */   
/* 297:    */   protected TopicConnection getTopicConnection()
/* 298:    */   {
/* 299:418 */     return this.topicConnection;
/* 300:    */   }
/* 301:    */   
/* 302:    */   protected TopicSession getTopicSession()
/* 303:    */   {
/* 304:426 */     return this.topicSession;
/* 305:    */   }
/* 306:    */   
/* 307:    */   protected TopicPublisher getTopicPublisher()
/* 308:    */   {
/* 309:434 */     return this.topicPublisher;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public boolean requiresLayout()
/* 313:    */   {
/* 314:442 */     return false;
/* 315:    */   }
/* 316:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.net.JMSAppender
 * JD-Core Version:    0.7.0.1
 */