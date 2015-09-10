/*   1:    */ package org.apache.log4j.jmx;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.management.Attribute;
/*   6:    */ import javax.management.AttributeNotFoundException;
/*   7:    */ import javax.management.InvalidAttributeValueException;
/*   8:    */ import javax.management.JMException;
/*   9:    */ import javax.management.ListenerNotFoundException;
/*  10:    */ import javax.management.MBeanAttributeInfo;
/*  11:    */ import javax.management.MBeanConstructorInfo;
/*  12:    */ import javax.management.MBeanException;
/*  13:    */ import javax.management.MBeanInfo;
/*  14:    */ import javax.management.MBeanNotificationInfo;
/*  15:    */ import javax.management.MBeanOperationInfo;
/*  16:    */ import javax.management.MBeanParameterInfo;
/*  17:    */ import javax.management.MBeanServer;
/*  18:    */ import javax.management.Notification;
/*  19:    */ import javax.management.NotificationBroadcaster;
/*  20:    */ import javax.management.NotificationBroadcasterSupport;
/*  21:    */ import javax.management.NotificationFilter;
/*  22:    */ import javax.management.NotificationFilterSupport;
/*  23:    */ import javax.management.NotificationListener;
/*  24:    */ import javax.management.ObjectName;
/*  25:    */ import javax.management.ReflectionException;
/*  26:    */ import javax.management.RuntimeOperationsException;
/*  27:    */ import org.apache.log4j.Appender;
/*  28:    */ import org.apache.log4j.Category;
/*  29:    */ import org.apache.log4j.Level;
/*  30:    */ import org.apache.log4j.LogManager;
/*  31:    */ import org.apache.log4j.Logger;
/*  32:    */ import org.apache.log4j.helpers.OptionConverter;
/*  33:    */ import org.apache.log4j.spi.HierarchyEventListener;
/*  34:    */ import org.apache.log4j.spi.LoggerRepository;
/*  35:    */ 
/*  36:    */ public class HierarchyDynamicMBean
/*  37:    */   extends AbstractDynamicMBean
/*  38:    */   implements HierarchyEventListener, NotificationBroadcaster
/*  39:    */ {
/*  40:    */   static final String ADD_APPENDER = "addAppender.";
/*  41:    */   static final String THRESHOLD = "threshold";
/*  42: 61 */   private MBeanConstructorInfo[] dConstructors = new MBeanConstructorInfo[1];
/*  43: 62 */   private MBeanOperationInfo[] dOperations = new MBeanOperationInfo[1];
/*  44: 64 */   private Vector vAttributes = new Vector();
/*  45: 65 */   private String dClassName = getClass().getName();
/*  46: 66 */   private String dDescription = "This MBean acts as a management facade for org.apache.log4j.Hierarchy.";
/*  47: 69 */   private NotificationBroadcasterSupport nbs = new NotificationBroadcasterSupport();
/*  48:    */   private LoggerRepository hierarchy;
/*  49: 74 */   private static Logger log = Logger.getLogger(HierarchyDynamicMBean.class);
/*  50:    */   
/*  51:    */   public HierarchyDynamicMBean()
/*  52:    */   {
/*  53: 77 */     this.hierarchy = LogManager.getLoggerRepository();
/*  54: 78 */     buildDynamicMBeanInfo();
/*  55:    */   }
/*  56:    */   
/*  57:    */   private void buildDynamicMBeanInfo()
/*  58:    */   {
/*  59: 83 */     Constructor[] constructors = getClass().getConstructors();
/*  60: 84 */     this.dConstructors[0] = new MBeanConstructorInfo("HierarchyDynamicMBean(): Constructs a HierarchyDynamicMBean instance", constructors[0]);
/*  61:    */     
/*  62:    */ 
/*  63:    */ 
/*  64: 88 */     this.vAttributes.add(new MBeanAttributeInfo("threshold", "java.lang.String", "The \"threshold\" state of the hiearchy.", true, true, false));
/*  65:    */     
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71: 95 */     MBeanParameterInfo[] params = new MBeanParameterInfo[1];
/*  72: 96 */     params[0] = new MBeanParameterInfo("name", "java.lang.String", "Create a logger MBean");
/*  73:    */     
/*  74: 98 */     this.dOperations[0] = new MBeanOperationInfo("addLoggerMBean", "addLoggerMBean(): add a loggerMBean", params, "javax.management.ObjectName", 1);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public ObjectName addLoggerMBean(String name)
/*  78:    */   {
/*  79:108 */     Logger cat = LogManager.exists(name);
/*  80:110 */     if (cat != null) {
/*  81:111 */       return addLoggerMBean(cat);
/*  82:    */     }
/*  83:113 */     return null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   ObjectName addLoggerMBean(Logger logger)
/*  87:    */   {
/*  88:118 */     String name = logger.getName();
/*  89:119 */     ObjectName objectName = null;
/*  90:    */     try
/*  91:    */     {
/*  92:121 */       LoggerDynamicMBean loggerMBean = new LoggerDynamicMBean(logger);
/*  93:122 */       objectName = new ObjectName("log4j", "logger", name);
/*  94:124 */       if (!this.server.isRegistered(objectName))
/*  95:    */       {
/*  96:125 */         registerMBean(loggerMBean, objectName);
/*  97:126 */         NotificationFilterSupport nfs = new NotificationFilterSupport();
/*  98:127 */         nfs.enableType("addAppender." + logger.getName());
/*  99:128 */         log.debug("---Adding logger [" + name + "] as listener.");
/* 100:129 */         this.nbs.addNotificationListener(loggerMBean, nfs, null);
/* 101:130 */         this.vAttributes.add(new MBeanAttributeInfo("logger=" + name, "javax.management.ObjectName", "The " + name + " logger.", true, true, false));
/* 102:    */       }
/* 103:    */     }
/* 104:    */     catch (JMException e)
/* 105:    */     {
/* 106:138 */       log.error("Could not add loggerMBean for [" + name + "].", e);
/* 107:    */     }
/* 108:    */     catch (RuntimeException e)
/* 109:    */     {
/* 110:140 */       log.error("Could not add loggerMBean for [" + name + "].", e);
/* 111:    */     }
/* 112:142 */     return objectName;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
/* 116:    */   {
/* 117:149 */     this.nbs.addNotificationListener(listener, filter, handback);
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected Logger getLogger()
/* 121:    */   {
/* 122:154 */     return log;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public MBeanInfo getMBeanInfo()
/* 126:    */   {
/* 127:161 */     MBeanAttributeInfo[] attribs = new MBeanAttributeInfo[this.vAttributes.size()];
/* 128:162 */     this.vAttributes.toArray(attribs);
/* 129:    */     
/* 130:164 */     return new MBeanInfo(this.dClassName, this.dDescription, attribs, this.dConstructors, this.dOperations, new MBeanNotificationInfo[0]);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public MBeanNotificationInfo[] getNotificationInfo()
/* 134:    */   {
/* 135:174 */     return this.nbs.getNotificationInfo();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Object invoke(String operationName, Object[] params, String[] signature)
/* 139:    */     throws MBeanException, ReflectionException
/* 140:    */   {
/* 141:183 */     if (operationName == null) {
/* 142:184 */       throw new RuntimeOperationsException(new IllegalArgumentException("Operation name cannot be null"), "Cannot invoke a null operation in " + this.dClassName);
/* 143:    */     }
/* 144:190 */     if (operationName.equals("addLoggerMBean")) {
/* 145:191 */       return addLoggerMBean((String)params[0]);
/* 146:    */     }
/* 147:193 */     throw new ReflectionException(new NoSuchMethodException(operationName), "Cannot find the operation " + operationName + " in " + this.dClassName);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Object getAttribute(String attributeName)
/* 151:    */     throws AttributeNotFoundException, MBeanException, ReflectionException
/* 152:    */   {
/* 153:207 */     if (attributeName == null) {
/* 154:208 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke a getter of " + this.dClassName + " with null attribute name");
/* 155:    */     }
/* 156:213 */     log.debug("Called getAttribute with [" + attributeName + "].");
/* 157:216 */     if (attributeName.equals("threshold")) {
/* 158:217 */       return this.hierarchy.getThreshold();
/* 159:    */     }
/* 160:218 */     if (attributeName.startsWith("logger"))
/* 161:    */     {
/* 162:219 */       int k = attributeName.indexOf("%3D");
/* 163:220 */       String val = attributeName;
/* 164:221 */       if (k > 0) {
/* 165:222 */         val = attributeName.substring(0, k) + '=' + attributeName.substring(k + 3);
/* 166:    */       }
/* 167:    */       try
/* 168:    */       {
/* 169:225 */         return new ObjectName("log4j:" + val);
/* 170:    */       }
/* 171:    */       catch (JMException e)
/* 172:    */       {
/* 173:227 */         log.error("Could not create ObjectName" + val);
/* 174:    */       }
/* 175:    */       catch (RuntimeException e)
/* 176:    */       {
/* 177:229 */         log.error("Could not create ObjectName" + val);
/* 178:    */       }
/* 179:    */     }
/* 180:236 */     throw new AttributeNotFoundException("Cannot find " + attributeName + " attribute in " + this.dClassName);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void addAppenderEvent(Category logger, Appender appender)
/* 184:    */   {
/* 185:244 */     log.debug("addAppenderEvent called: logger=" + logger.getName() + ", appender=" + appender.getName());
/* 186:    */     
/* 187:246 */     Notification n = new Notification("addAppender." + logger.getName(), this, 0L);
/* 188:247 */     n.setUserData(appender);
/* 189:248 */     log.debug("sending notification.");
/* 190:249 */     this.nbs.sendNotification(n);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void removeAppenderEvent(Category cat, Appender appender)
/* 194:    */   {
/* 195:254 */     log.debug("removeAppenderCalled: logger=" + cat.getName() + ", appender=" + appender.getName());
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void postRegister(Boolean registrationDone)
/* 199:    */   {
/* 200:260 */     log.debug("postRegister is called.");
/* 201:261 */     this.hierarchy.addHierarchyEventListener(this);
/* 202:262 */     Logger root = this.hierarchy.getRootLogger();
/* 203:263 */     addLoggerMBean(root);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void removeNotificationListener(NotificationListener listener)
/* 207:    */     throws ListenerNotFoundException
/* 208:    */   {
/* 209:269 */     this.nbs.removeNotificationListener(listener);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setAttribute(Attribute attribute)
/* 213:    */     throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException
/* 214:    */   {
/* 215:279 */     if (attribute == null) {
/* 216:280 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), "Cannot invoke a setter of " + this.dClassName + " with null attribute");
/* 217:    */     }
/* 218:284 */     String name = attribute.getName();
/* 219:285 */     Object value = attribute.getValue();
/* 220:287 */     if (name == null) {
/* 221:288 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke the setter of " + this.dClassName + " with null attribute name");
/* 222:    */     }
/* 223:294 */     if (name.equals("threshold"))
/* 224:    */     {
/* 225:295 */       Level l = OptionConverter.toLevel((String)value, this.hierarchy.getThreshold());
/* 226:    */       
/* 227:297 */       this.hierarchy.setThreshold(l);
/* 228:    */     }
/* 229:    */   }
/* 230:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.jmx.HierarchyDynamicMBean
 * JD-Core Version:    0.7.0.1
 */