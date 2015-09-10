/*   1:    */ package org.apache.log4j.jmx;
/*   2:    */ 
/*   3:    */ import java.beans.BeanInfo;
/*   4:    */ import java.beans.FeatureDescriptor;
/*   5:    */ import java.beans.IntrospectionException;
/*   6:    */ import java.beans.Introspector;
/*   7:    */ import java.beans.PropertyDescriptor;
/*   8:    */ import java.io.InterruptedIOException;
/*   9:    */ import java.lang.reflect.Constructor;
/*  10:    */ import java.lang.reflect.InvocationTargetException;
/*  11:    */ import java.lang.reflect.Method;
/*  12:    */ import java.util.Hashtable;
/*  13:    */ import java.util.Vector;
/*  14:    */ import javax.management.Attribute;
/*  15:    */ import javax.management.AttributeNotFoundException;
/*  16:    */ import javax.management.InvalidAttributeValueException;
/*  17:    */ import javax.management.JMException;
/*  18:    */ import javax.management.MBeanAttributeInfo;
/*  19:    */ import javax.management.MBeanConstructorInfo;
/*  20:    */ import javax.management.MBeanException;
/*  21:    */ import javax.management.MBeanInfo;
/*  22:    */ import javax.management.MBeanNotificationInfo;
/*  23:    */ import javax.management.MBeanOperationInfo;
/*  24:    */ import javax.management.MBeanParameterInfo;
/*  25:    */ import javax.management.MBeanServer;
/*  26:    */ import javax.management.MalformedObjectNameException;
/*  27:    */ import javax.management.ObjectName;
/*  28:    */ import javax.management.ReflectionException;
/*  29:    */ import javax.management.RuntimeOperationsException;
/*  30:    */ import org.apache.log4j.Appender;
/*  31:    */ import org.apache.log4j.Category;
/*  32:    */ import org.apache.log4j.Layout;
/*  33:    */ import org.apache.log4j.Level;
/*  34:    */ import org.apache.log4j.Logger;
/*  35:    */ import org.apache.log4j.Priority;
/*  36:    */ import org.apache.log4j.helpers.OptionConverter;
/*  37:    */ import org.apache.log4j.spi.OptionHandler;
/*  38:    */ 
/*  39:    */ public class AppenderDynamicMBean
/*  40:    */   extends AbstractDynamicMBean
/*  41:    */ {
/*  42: 57 */   private MBeanConstructorInfo[] dConstructors = new MBeanConstructorInfo[1];
/*  43: 58 */   private Vector dAttributes = new Vector();
/*  44: 59 */   private String dClassName = getClass().getName();
/*  45: 61 */   private Hashtable dynamicProps = new Hashtable(5);
/*  46: 62 */   private MBeanOperationInfo[] dOperations = new MBeanOperationInfo[2];
/*  47: 63 */   private String dDescription = "This MBean acts as a management facade for log4j appenders.";
/*  48: 67 */   private static Logger cat = Logger.getLogger(AppenderDynamicMBean.class);
/*  49:    */   private Appender appender;
/*  50:    */   
/*  51:    */   public AppenderDynamicMBean(Appender appender)
/*  52:    */     throws IntrospectionException
/*  53:    */   {
/*  54: 73 */     this.appender = appender;
/*  55: 74 */     buildDynamicMBeanInfo();
/*  56:    */   }
/*  57:    */   
/*  58:    */   private void buildDynamicMBeanInfo()
/*  59:    */     throws IntrospectionException
/*  60:    */   {
/*  61: 79 */     Constructor[] constructors = getClass().getConstructors();
/*  62: 80 */     this.dConstructors[0] = new MBeanConstructorInfo("AppenderDynamicMBean(): Constructs a AppenderDynamicMBean instance", constructors[0]);
/*  63:    */     
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67: 85 */     BeanInfo bi = Introspector.getBeanInfo(this.appender.getClass());
/*  68: 86 */     PropertyDescriptor[] pd = bi.getPropertyDescriptors();
/*  69:    */     
/*  70: 88 */     int size = pd.length;
/*  71: 90 */     for (int i = 0; i < size; i++)
/*  72:    */     {
/*  73: 91 */       String name = pd[i].getName();
/*  74: 92 */       Method readMethod = pd[i].getReadMethod();
/*  75: 93 */       Method writeMethod = pd[i].getWriteMethod();
/*  76: 94 */       if (readMethod != null)
/*  77:    */       {
/*  78: 95 */         Class returnClass = readMethod.getReturnType();
/*  79: 96 */         if (isSupportedType(returnClass))
/*  80:    */         {
/*  81:    */           String returnClassName;
/*  82:    */           String returnClassName;
/*  83: 98 */           if (returnClass.isAssignableFrom(Priority.class)) {
/*  84: 99 */             returnClassName = "java.lang.String";
/*  85:    */           } else {
/*  86:101 */             returnClassName = returnClass.getName();
/*  87:    */           }
/*  88:104 */           this.dAttributes.add(new MBeanAttributeInfo(name, returnClassName, "Dynamic", true, writeMethod != null, false));
/*  89:    */           
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:110 */           this.dynamicProps.put(name, new MethodUnion(readMethod, writeMethod));
/*  95:    */         }
/*  96:    */       }
/*  97:    */     }
/*  98:115 */     MBeanParameterInfo[] params = new MBeanParameterInfo[0];
/*  99:    */     
/* 100:117 */     this.dOperations[0] = new MBeanOperationInfo("activateOptions", "activateOptions(): add an appender", params, "void", 1);
/* 101:    */     
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:123 */     params = new MBeanParameterInfo[1];
/* 107:124 */     params[0] = new MBeanParameterInfo("layout class", "java.lang.String", "layout class");
/* 108:    */     
/* 109:    */ 
/* 110:127 */     this.dOperations[1] = new MBeanOperationInfo("setLayout", "setLayout(): add a layout", params, "void", 1);
/* 111:    */   }
/* 112:    */   
/* 113:    */   private boolean isSupportedType(Class clazz)
/* 114:    */   {
/* 115:136 */     if (clazz.isPrimitive()) {
/* 116:137 */       return true;
/* 117:    */     }
/* 118:140 */     if (clazz == String.class) {
/* 119:141 */       return true;
/* 120:    */     }
/* 121:145 */     if (clazz.isAssignableFrom(Priority.class)) {
/* 122:146 */       return true;
/* 123:    */     }
/* 124:149 */     return false;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public MBeanInfo getMBeanInfo()
/* 128:    */   {
/* 129:158 */     cat.debug("getMBeanInfo called.");
/* 130:    */     
/* 131:160 */     MBeanAttributeInfo[] attribs = new MBeanAttributeInfo[this.dAttributes.size()];
/* 132:161 */     this.dAttributes.toArray(attribs);
/* 133:    */     
/* 134:163 */     return new MBeanInfo(this.dClassName, this.dDescription, attribs, this.dConstructors, this.dOperations, new MBeanNotificationInfo[0]);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Object invoke(String operationName, Object[] params, String[] signature)
/* 138:    */     throws MBeanException, ReflectionException
/* 139:    */   {
/* 140:176 */     if ((operationName.equals("activateOptions")) && ((this.appender instanceof OptionHandler)))
/* 141:    */     {
/* 142:178 */       OptionHandler oh = (OptionHandler)this.appender;
/* 143:179 */       oh.activateOptions();
/* 144:180 */       return "Options activated.";
/* 145:    */     }
/* 146:181 */     if (operationName.equals("setLayout"))
/* 147:    */     {
/* 148:182 */       Layout layout = (Layout)OptionConverter.instantiateByClassName((String)params[0], Layout.class, null);
/* 149:    */       
/* 150:    */ 
/* 151:    */ 
/* 152:186 */       this.appender.setLayout(layout);
/* 153:187 */       registerLayoutMBean(layout);
/* 154:    */     }
/* 155:189 */     return null;
/* 156:    */   }
/* 157:    */   
/* 158:    */   void registerLayoutMBean(Layout layout)
/* 159:    */   {
/* 160:193 */     if (layout == null) {
/* 161:194 */       return;
/* 162:    */     }
/* 163:196 */     String name = AbstractDynamicMBean.getAppenderName(this.appender) + ",layout=" + layout.getClass().getName();
/* 164:197 */     cat.debug("Adding LayoutMBean:" + name);
/* 165:198 */     ObjectName objectName = null;
/* 166:    */     try
/* 167:    */     {
/* 168:200 */       LayoutDynamicMBean appenderMBean = new LayoutDynamicMBean(layout);
/* 169:201 */       objectName = new ObjectName("log4j:appender=" + name);
/* 170:202 */       if (!this.server.isRegistered(objectName))
/* 171:    */       {
/* 172:203 */         registerMBean(appenderMBean, objectName);
/* 173:204 */         this.dAttributes.add(new MBeanAttributeInfo("appender=" + name, "javax.management.ObjectName", "The " + name + " layout.", true, true, false));
/* 174:    */       }
/* 175:    */     }
/* 176:    */     catch (JMException e)
/* 177:    */     {
/* 178:209 */       cat.error("Could not add DynamicLayoutMBean for [" + name + "].", e);
/* 179:    */     }
/* 180:    */     catch (IntrospectionException e)
/* 181:    */     {
/* 182:211 */       cat.error("Could not add DynamicLayoutMBean for [" + name + "].", e);
/* 183:    */     }
/* 184:    */     catch (RuntimeException e)
/* 185:    */     {
/* 186:213 */       cat.error("Could not add DynamicLayoutMBean for [" + name + "].", e);
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   protected Logger getLogger()
/* 191:    */   {
/* 192:219 */     return cat;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public Object getAttribute(String attributeName)
/* 196:    */     throws AttributeNotFoundException, MBeanException, ReflectionException
/* 197:    */   {
/* 198:229 */     if (attributeName == null) {
/* 199:230 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke a getter of " + this.dClassName + " with null attribute name");
/* 200:    */     }
/* 201:235 */     cat.debug("getAttribute called with [" + attributeName + "].");
/* 202:236 */     if (attributeName.startsWith("appender=" + this.appender.getName() + ",layout")) {
/* 203:    */       try
/* 204:    */       {
/* 205:238 */         return new ObjectName("log4j:" + attributeName);
/* 206:    */       }
/* 207:    */       catch (MalformedObjectNameException e)
/* 208:    */       {
/* 209:240 */         cat.error("attributeName", e);
/* 210:    */       }
/* 211:    */       catch (RuntimeException e)
/* 212:    */       {
/* 213:242 */         cat.error("attributeName", e);
/* 214:    */       }
/* 215:    */     }
/* 216:246 */     MethodUnion mu = (MethodUnion)this.dynamicProps.get(attributeName);
/* 217:250 */     if ((mu != null) && (mu.readMethod != null)) {
/* 218:    */       try
/* 219:    */       {
/* 220:252 */         return mu.readMethod.invoke(this.appender, null);
/* 221:    */       }
/* 222:    */       catch (IllegalAccessException e)
/* 223:    */       {
/* 224:254 */         return null;
/* 225:    */       }
/* 226:    */       catch (InvocationTargetException e)
/* 227:    */       {
/* 228:256 */         if (((e.getTargetException() instanceof InterruptedException)) || ((e.getTargetException() instanceof InterruptedIOException))) {
/* 229:258 */           Thread.currentThread().interrupt();
/* 230:    */         }
/* 231:260 */         return null;
/* 232:    */       }
/* 233:    */       catch (RuntimeException e)
/* 234:    */       {
/* 235:262 */         return null;
/* 236:    */       }
/* 237:    */     }
/* 238:269 */     throw new AttributeNotFoundException("Cannot find " + attributeName + " attribute in " + this.dClassName);
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setAttribute(Attribute attribute)
/* 242:    */     throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException
/* 243:    */   {
/* 244:282 */     if (attribute == null) {
/* 245:283 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), "Cannot invoke a setter of " + this.dClassName + " with null attribute");
/* 246:    */     }
/* 247:288 */     String name = attribute.getName();
/* 248:289 */     Object value = attribute.getValue();
/* 249:291 */     if (name == null) {
/* 250:292 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke the setter of " + this.dClassName + " with null attribute name");
/* 251:    */     }
/* 252:300 */     MethodUnion mu = (MethodUnion)this.dynamicProps.get(name);
/* 253:302 */     if ((mu != null) && (mu.writeMethod != null))
/* 254:    */     {
/* 255:303 */       Object[] o = new Object[1];
/* 256:    */       
/* 257:305 */       Class[] params = mu.writeMethod.getParameterTypes();
/* 258:306 */       if (params[0] == Priority.class) {
/* 259:307 */         value = OptionConverter.toLevel((String)value, (Level)getAttribute(name));
/* 260:    */       }
/* 261:310 */       o[0] = value;
/* 262:    */       try
/* 263:    */       {
/* 264:313 */         mu.writeMethod.invoke(this.appender, o);
/* 265:    */       }
/* 266:    */       catch (InvocationTargetException e)
/* 267:    */       {
/* 268:316 */         if (((e.getTargetException() instanceof InterruptedException)) || ((e.getTargetException() instanceof InterruptedIOException))) {
/* 269:318 */           Thread.currentThread().interrupt();
/* 270:    */         }
/* 271:320 */         cat.error("FIXME", e);
/* 272:    */       }
/* 273:    */       catch (IllegalAccessException e)
/* 274:    */       {
/* 275:322 */         cat.error("FIXME", e);
/* 276:    */       }
/* 277:    */       catch (RuntimeException e)
/* 278:    */       {
/* 279:324 */         cat.error("FIXME", e);
/* 280:    */       }
/* 281:    */     }
/* 282:326 */     else if (!name.endsWith(".layout"))
/* 283:    */     {
/* 284:329 */       throw new AttributeNotFoundException("Attribute " + name + " not found in " + getClass().getName());
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   public ObjectName preRegister(MBeanServer server, ObjectName name)
/* 289:    */   {
/* 290:337 */     cat.debug("preRegister called. Server=" + server + ", name=" + name);
/* 291:338 */     this.server = server;
/* 292:339 */     registerLayoutMBean(this.appender.getLayout());
/* 293:    */     
/* 294:341 */     return name;
/* 295:    */   }
/* 296:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.jmx.AppenderDynamicMBean
 * JD-Core Version:    0.7.0.1
 */