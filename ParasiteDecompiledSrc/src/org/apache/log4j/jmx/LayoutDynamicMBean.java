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
/*  17:    */ import javax.management.MBeanAttributeInfo;
/*  18:    */ import javax.management.MBeanConstructorInfo;
/*  19:    */ import javax.management.MBeanException;
/*  20:    */ import javax.management.MBeanInfo;
/*  21:    */ import javax.management.MBeanNotificationInfo;
/*  22:    */ import javax.management.MBeanOperationInfo;
/*  23:    */ import javax.management.MBeanParameterInfo;
/*  24:    */ import javax.management.ReflectionException;
/*  25:    */ import javax.management.RuntimeOperationsException;
/*  26:    */ import org.apache.log4j.Category;
/*  27:    */ import org.apache.log4j.Layout;
/*  28:    */ import org.apache.log4j.Level;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ import org.apache.log4j.Priority;
/*  31:    */ import org.apache.log4j.helpers.OptionConverter;
/*  32:    */ import org.apache.log4j.spi.OptionHandler;
/*  33:    */ 
/*  34:    */ public class LayoutDynamicMBean
/*  35:    */   extends AbstractDynamicMBean
/*  36:    */ {
/*  37: 53 */   private MBeanConstructorInfo[] dConstructors = new MBeanConstructorInfo[1];
/*  38: 54 */   private Vector dAttributes = new Vector();
/*  39: 55 */   private String dClassName = getClass().getName();
/*  40: 57 */   private Hashtable dynamicProps = new Hashtable(5);
/*  41: 58 */   private MBeanOperationInfo[] dOperations = new MBeanOperationInfo[1];
/*  42: 59 */   private String dDescription = "This MBean acts as a management facade for log4j layouts.";
/*  43: 63 */   private static Logger cat = Logger.getLogger(LayoutDynamicMBean.class);
/*  44:    */   private Layout layout;
/*  45:    */   
/*  46:    */   public LayoutDynamicMBean(Layout layout)
/*  47:    */     throws IntrospectionException
/*  48:    */   {
/*  49: 69 */     this.layout = layout;
/*  50: 70 */     buildDynamicMBeanInfo();
/*  51:    */   }
/*  52:    */   
/*  53:    */   private void buildDynamicMBeanInfo()
/*  54:    */     throws IntrospectionException
/*  55:    */   {
/*  56: 75 */     Constructor[] constructors = getClass().getConstructors();
/*  57: 76 */     this.dConstructors[0] = new MBeanConstructorInfo("LayoutDynamicMBean(): Constructs a LayoutDynamicMBean instance", constructors[0]);
/*  58:    */     
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62: 81 */     BeanInfo bi = Introspector.getBeanInfo(this.layout.getClass());
/*  63: 82 */     PropertyDescriptor[] pd = bi.getPropertyDescriptors();
/*  64:    */     
/*  65: 84 */     int size = pd.length;
/*  66: 86 */     for (int i = 0; i < size; i++)
/*  67:    */     {
/*  68: 87 */       String name = pd[i].getName();
/*  69: 88 */       Method readMethod = pd[i].getReadMethod();
/*  70: 89 */       Method writeMethod = pd[i].getWriteMethod();
/*  71: 90 */       if (readMethod != null)
/*  72:    */       {
/*  73: 91 */         Class returnClass = readMethod.getReturnType();
/*  74: 92 */         if (isSupportedType(returnClass))
/*  75:    */         {
/*  76:    */           String returnClassName;
/*  77:    */           String returnClassName;
/*  78: 94 */           if (returnClass.isAssignableFrom(Level.class)) {
/*  79: 95 */             returnClassName = "java.lang.String";
/*  80:    */           } else {
/*  81: 97 */             returnClassName = returnClass.getName();
/*  82:    */           }
/*  83:100 */           this.dAttributes.add(new MBeanAttributeInfo(name, returnClassName, "Dynamic", true, writeMethod != null, false));
/*  84:    */           
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:106 */           this.dynamicProps.put(name, new MethodUnion(readMethod, writeMethod));
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93:111 */     MBeanParameterInfo[] params = new MBeanParameterInfo[0];
/*  94:    */     
/*  95:113 */     this.dOperations[0] = new MBeanOperationInfo("activateOptions", "activateOptions(): add an layout", params, "void", 1);
/*  96:    */   }
/*  97:    */   
/*  98:    */   private boolean isSupportedType(Class clazz)
/*  99:    */   {
/* 100:122 */     if (clazz.isPrimitive()) {
/* 101:123 */       return true;
/* 102:    */     }
/* 103:126 */     if (clazz == String.class) {
/* 104:127 */       return true;
/* 105:    */     }
/* 106:129 */     if (clazz.isAssignableFrom(Level.class)) {
/* 107:130 */       return true;
/* 108:    */     }
/* 109:133 */     return false;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public MBeanInfo getMBeanInfo()
/* 113:    */   {
/* 114:140 */     cat.debug("getMBeanInfo called.");
/* 115:    */     
/* 116:142 */     MBeanAttributeInfo[] attribs = new MBeanAttributeInfo[this.dAttributes.size()];
/* 117:143 */     this.dAttributes.toArray(attribs);
/* 118:    */     
/* 119:145 */     return new MBeanInfo(this.dClassName, this.dDescription, attribs, this.dConstructors, this.dOperations, new MBeanNotificationInfo[0]);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Object invoke(String operationName, Object[] params, String[] signature)
/* 123:    */     throws MBeanException, ReflectionException
/* 124:    */   {
/* 125:158 */     if ((operationName.equals("activateOptions")) && ((this.layout instanceof OptionHandler)))
/* 126:    */     {
/* 127:160 */       OptionHandler oh = this.layout;
/* 128:161 */       oh.activateOptions();
/* 129:162 */       return "Options activated.";
/* 130:    */     }
/* 131:164 */     return null;
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected Logger getLogger()
/* 135:    */   {
/* 136:169 */     return cat;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Object getAttribute(String attributeName)
/* 140:    */     throws AttributeNotFoundException, MBeanException, ReflectionException
/* 141:    */   {
/* 142:179 */     if (attributeName == null) {
/* 143:180 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke a getter of " + this.dClassName + " with null attribute name");
/* 144:    */     }
/* 145:186 */     MethodUnion mu = (MethodUnion)this.dynamicProps.get(attributeName);
/* 146:    */     
/* 147:188 */     cat.debug("----name=" + attributeName + ", mu=" + mu);
/* 148:190 */     if ((mu != null) && (mu.readMethod != null)) {
/* 149:    */       try
/* 150:    */       {
/* 151:192 */         return mu.readMethod.invoke(this.layout, null);
/* 152:    */       }
/* 153:    */       catch (InvocationTargetException e)
/* 154:    */       {
/* 155:194 */         if (((e.getTargetException() instanceof InterruptedException)) || ((e.getTargetException() instanceof InterruptedIOException))) {
/* 156:196 */           Thread.currentThread().interrupt();
/* 157:    */         }
/* 158:198 */         return null;
/* 159:    */       }
/* 160:    */       catch (IllegalAccessException e)
/* 161:    */       {
/* 162:200 */         return null;
/* 163:    */       }
/* 164:    */       catch (RuntimeException e)
/* 165:    */       {
/* 166:202 */         return null;
/* 167:    */       }
/* 168:    */     }
/* 169:209 */     throw new AttributeNotFoundException("Cannot find " + attributeName + " attribute in " + this.dClassName);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setAttribute(Attribute attribute)
/* 173:    */     throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException
/* 174:    */   {
/* 175:222 */     if (attribute == null) {
/* 176:223 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), "Cannot invoke a setter of " + this.dClassName + " with null attribute");
/* 177:    */     }
/* 178:228 */     String name = attribute.getName();
/* 179:229 */     Object value = attribute.getValue();
/* 180:231 */     if (name == null) {
/* 181:232 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke the setter of " + this.dClassName + " with null attribute name");
/* 182:    */     }
/* 183:240 */     MethodUnion mu = (MethodUnion)this.dynamicProps.get(name);
/* 184:242 */     if ((mu != null) && (mu.writeMethod != null))
/* 185:    */     {
/* 186:243 */       Object[] o = new Object[1];
/* 187:    */       
/* 188:245 */       Class[] params = mu.writeMethod.getParameterTypes();
/* 189:246 */       if (params[0] == Priority.class) {
/* 190:247 */         value = OptionConverter.toLevel((String)value, (Level)getAttribute(name));
/* 191:    */       }
/* 192:250 */       o[0] = value;
/* 193:    */       try
/* 194:    */       {
/* 195:253 */         mu.writeMethod.invoke(this.layout, o);
/* 196:    */       }
/* 197:    */       catch (InvocationTargetException e)
/* 198:    */       {
/* 199:256 */         if (((e.getTargetException() instanceof InterruptedException)) || ((e.getTargetException() instanceof InterruptedIOException))) {
/* 200:258 */           Thread.currentThread().interrupt();
/* 201:    */         }
/* 202:260 */         cat.error("FIXME", e);
/* 203:    */       }
/* 204:    */       catch (IllegalAccessException e)
/* 205:    */       {
/* 206:262 */         cat.error("FIXME", e);
/* 207:    */       }
/* 208:    */       catch (RuntimeException e)
/* 209:    */       {
/* 210:264 */         cat.error("FIXME", e);
/* 211:    */       }
/* 212:    */     }
/* 213:    */     else
/* 214:    */     {
/* 215:267 */       throw new AttributeNotFoundException("Attribute " + name + " not found in " + getClass().getName());
/* 216:    */     }
/* 217:    */   }
/* 218:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.jmx.LayoutDynamicMBean
 * JD-Core Version:    0.7.0.1
 */