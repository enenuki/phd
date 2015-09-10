/*   1:    */ package org.apache.log4j.jmx;
/*   2:    */ 
/*   3:    */ import java.beans.IntrospectionException;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.util.Enumeration;
/*   6:    */ import java.util.Vector;
/*   7:    */ import javax.management.Attribute;
/*   8:    */ import javax.management.AttributeNotFoundException;
/*   9:    */ import javax.management.InvalidAttributeValueException;
/*  10:    */ import javax.management.JMException;
/*  11:    */ import javax.management.MBeanAttributeInfo;
/*  12:    */ import javax.management.MBeanConstructorInfo;
/*  13:    */ import javax.management.MBeanException;
/*  14:    */ import javax.management.MBeanInfo;
/*  15:    */ import javax.management.MBeanNotificationInfo;
/*  16:    */ import javax.management.MBeanOperationInfo;
/*  17:    */ import javax.management.MBeanParameterInfo;
/*  18:    */ import javax.management.MBeanServer;
/*  19:    */ import javax.management.MalformedObjectNameException;
/*  20:    */ import javax.management.Notification;
/*  21:    */ import javax.management.NotificationListener;
/*  22:    */ import javax.management.ObjectName;
/*  23:    */ import javax.management.ReflectionException;
/*  24:    */ import javax.management.RuntimeOperationsException;
/*  25:    */ import org.apache.log4j.Appender;
/*  26:    */ import org.apache.log4j.Category;
/*  27:    */ import org.apache.log4j.Level;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.apache.log4j.Priority;
/*  30:    */ import org.apache.log4j.helpers.OptionConverter;
/*  31:    */ 
/*  32:    */ public class LoggerDynamicMBean
/*  33:    */   extends AbstractDynamicMBean
/*  34:    */   implements NotificationListener
/*  35:    */ {
/*  36: 49 */   private MBeanConstructorInfo[] dConstructors = new MBeanConstructorInfo[1];
/*  37: 50 */   private MBeanOperationInfo[] dOperations = new MBeanOperationInfo[1];
/*  38: 52 */   private Vector dAttributes = new Vector();
/*  39: 53 */   private String dClassName = getClass().getName();
/*  40: 55 */   private String dDescription = "This MBean acts as a management facade for a org.apache.log4j.Logger instance.";
/*  41: 59 */   private static Logger cat = Logger.getLogger(LoggerDynamicMBean.class);
/*  42:    */   private Logger logger;
/*  43:    */   
/*  44:    */   public LoggerDynamicMBean(Logger logger)
/*  45:    */   {
/*  46: 65 */     this.logger = logger;
/*  47: 66 */     buildDynamicMBeanInfo();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void handleNotification(Notification notification, Object handback)
/*  51:    */   {
/*  52: 71 */     cat.debug("Received notification: " + notification.getType());
/*  53: 72 */     registerAppenderMBean((Appender)notification.getUserData());
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void buildDynamicMBeanInfo()
/*  57:    */   {
/*  58: 79 */     Constructor[] constructors = getClass().getConstructors();
/*  59: 80 */     this.dConstructors[0] = new MBeanConstructorInfo("HierarchyDynamicMBean(): Constructs a HierarchyDynamicMBean instance", constructors[0]);
/*  60:    */     
/*  61:    */ 
/*  62:    */ 
/*  63: 84 */     this.dAttributes.add(new MBeanAttributeInfo("name", "java.lang.String", "The name of this Logger.", true, false, false));
/*  64:    */     
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70: 91 */     this.dAttributes.add(new MBeanAttributeInfo("priority", "java.lang.String", "The priority of this logger.", true, true, false));
/*  71:    */     
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:102 */     MBeanParameterInfo[] params = new MBeanParameterInfo[2];
/*  82:103 */     params[0] = new MBeanParameterInfo("class name", "java.lang.String", "add an appender to this logger");
/*  83:    */     
/*  84:105 */     params[1] = new MBeanParameterInfo("appender name", "java.lang.String", "name of the appender");
/*  85:    */     
/*  86:    */ 
/*  87:108 */     this.dOperations[0] = new MBeanOperationInfo("addAppender", "addAppender(): add an appender", params, "void", 1);
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected Logger getLogger()
/*  91:    */   {
/*  92:117 */     return this.logger;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public MBeanInfo getMBeanInfo()
/*  96:    */   {
/*  97:125 */     MBeanAttributeInfo[] attribs = new MBeanAttributeInfo[this.dAttributes.size()];
/*  98:126 */     this.dAttributes.toArray(attribs);
/*  99:    */     
/* 100:128 */     MBeanInfo mb = new MBeanInfo(this.dClassName, this.dDescription, attribs, this.dConstructors, this.dOperations, new MBeanNotificationInfo[0]);
/* 101:    */     
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:135 */     return mb;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Object invoke(String operationName, Object[] params, String[] signature)
/* 111:    */     throws MBeanException, ReflectionException
/* 112:    */   {
/* 113:143 */     if (operationName.equals("addAppender"))
/* 114:    */     {
/* 115:144 */       addAppender((String)params[0], (String)params[1]);
/* 116:145 */       return "Hello world.";
/* 117:    */     }
/* 118:148 */     return null;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Object getAttribute(String attributeName)
/* 122:    */     throws AttributeNotFoundException, MBeanException, ReflectionException
/* 123:    */   {
/* 124:158 */     if (attributeName == null) {
/* 125:159 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke a getter of " + this.dClassName + " with null attribute name");
/* 126:    */     }
/* 127:165 */     if (attributeName.equals("name")) {
/* 128:166 */       return this.logger.getName();
/* 129:    */     }
/* 130:167 */     if (attributeName.equals("priority"))
/* 131:    */     {
/* 132:168 */       Level l = this.logger.getLevel();
/* 133:169 */       if (l == null) {
/* 134:170 */         return null;
/* 135:    */       }
/* 136:172 */       return l.toString();
/* 137:    */     }
/* 138:174 */     if (attributeName.startsWith("appender=")) {
/* 139:    */       try
/* 140:    */       {
/* 141:176 */         return new ObjectName("log4j:" + attributeName);
/* 142:    */       }
/* 143:    */       catch (MalformedObjectNameException e)
/* 144:    */       {
/* 145:178 */         cat.error("Could not create ObjectName" + attributeName);
/* 146:    */       }
/* 147:    */       catch (RuntimeException e)
/* 148:    */       {
/* 149:180 */         cat.error("Could not create ObjectName" + attributeName);
/* 150:    */       }
/* 151:    */     }
/* 152:186 */     throw new AttributeNotFoundException("Cannot find " + attributeName + " attribute in " + this.dClassName);
/* 153:    */   }
/* 154:    */   
/* 155:    */   void addAppender(String appenderClass, String appenderName)
/* 156:    */   {
/* 157:193 */     cat.debug("addAppender called with " + appenderClass + ", " + appenderName);
/* 158:194 */     Appender appender = (Appender)OptionConverter.instantiateByClassName(appenderClass, Appender.class, null);
/* 159:    */     
/* 160:    */ 
/* 161:    */ 
/* 162:198 */     appender.setName(appenderName);
/* 163:199 */     this.logger.addAppender(appender);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setAttribute(Attribute attribute)
/* 167:    */     throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException
/* 168:    */   {
/* 169:213 */     if (attribute == null) {
/* 170:214 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), "Cannot invoke a setter of " + this.dClassName + " with null attribute");
/* 171:    */     }
/* 172:219 */     String name = attribute.getName();
/* 173:220 */     Object value = attribute.getValue();
/* 174:222 */     if (name == null) {
/* 175:223 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke the setter of " + this.dClassName + " with null attribute name");
/* 176:    */     }
/* 177:230 */     if (name.equals("priority"))
/* 178:    */     {
/* 179:231 */       if ((value instanceof String))
/* 180:    */       {
/* 181:232 */         String s = (String)value;
/* 182:233 */         Level p = this.logger.getLevel();
/* 183:234 */         if (s.equalsIgnoreCase("NULL")) {
/* 184:235 */           p = null;
/* 185:    */         } else {
/* 186:237 */           p = OptionConverter.toLevel(s, p);
/* 187:    */         }
/* 188:239 */         this.logger.setLevel(p);
/* 189:    */       }
/* 190:    */     }
/* 191:    */     else {
/* 192:242 */       throw new AttributeNotFoundException("Attribute " + name + " not found in " + getClass().getName());
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   void appenderMBeanRegistration()
/* 197:    */   {
/* 198:249 */     Enumeration enumeration = this.logger.getAllAppenders();
/* 199:250 */     while (enumeration.hasMoreElements())
/* 200:    */     {
/* 201:251 */       Appender appender = (Appender)enumeration.nextElement();
/* 202:252 */       registerAppenderMBean(appender);
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   void registerAppenderMBean(Appender appender)
/* 207:    */   {
/* 208:257 */     String name = AbstractDynamicMBean.getAppenderName(appender);
/* 209:258 */     cat.debug("Adding AppenderMBean for appender named " + name);
/* 210:259 */     ObjectName objectName = null;
/* 211:    */     try
/* 212:    */     {
/* 213:261 */       AppenderDynamicMBean appenderMBean = new AppenderDynamicMBean(appender);
/* 214:262 */       objectName = new ObjectName("log4j", "appender", name);
/* 215:263 */       if (!this.server.isRegistered(objectName))
/* 216:    */       {
/* 217:264 */         registerMBean(appenderMBean, objectName);
/* 218:265 */         this.dAttributes.add(new MBeanAttributeInfo("appender=" + name, "javax.management.ObjectName", "The " + name + " appender.", true, true, false));
/* 219:    */       }
/* 220:    */     }
/* 221:    */     catch (JMException e)
/* 222:    */     {
/* 223:270 */       cat.error("Could not add appenderMBean for [" + name + "].", e);
/* 224:    */     }
/* 225:    */     catch (IntrospectionException e)
/* 226:    */     {
/* 227:272 */       cat.error("Could not add appenderMBean for [" + name + "].", e);
/* 228:    */     }
/* 229:    */     catch (RuntimeException e)
/* 230:    */     {
/* 231:274 */       cat.error("Could not add appenderMBean for [" + name + "].", e);
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void postRegister(Boolean registrationDone)
/* 236:    */   {
/* 237:280 */     appenderMBeanRegistration();
/* 238:    */   }
/* 239:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.jmx.LoggerDynamicMBean
 * JD-Core Version:    0.7.0.1
 */