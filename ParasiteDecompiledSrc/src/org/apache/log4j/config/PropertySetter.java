/*   1:    */ package org.apache.log4j.config;
/*   2:    */ 
/*   3:    */ import java.beans.BeanInfo;
/*   4:    */ import java.beans.FeatureDescriptor;
/*   5:    */ import java.beans.IntrospectionException;
/*   6:    */ import java.beans.Introspector;
/*   7:    */ import java.beans.PropertyDescriptor;
/*   8:    */ import java.io.InterruptedIOException;
/*   9:    */ import java.lang.reflect.InvocationTargetException;
/*  10:    */ import java.lang.reflect.Method;
/*  11:    */ import java.util.Enumeration;
/*  12:    */ import java.util.Properties;
/*  13:    */ import org.apache.log4j.Appender;
/*  14:    */ import org.apache.log4j.Level;
/*  15:    */ import org.apache.log4j.Priority;
/*  16:    */ import org.apache.log4j.helpers.LogLog;
/*  17:    */ import org.apache.log4j.helpers.OptionConverter;
/*  18:    */ import org.apache.log4j.spi.ErrorHandler;
/*  19:    */ import org.apache.log4j.spi.OptionHandler;
/*  20:    */ 
/*  21:    */ public class PropertySetter
/*  22:    */ {
/*  23:    */   protected Object obj;
/*  24:    */   protected PropertyDescriptor[] props;
/*  25:    */   
/*  26:    */   public PropertySetter(Object obj)
/*  27:    */   {
/*  28: 73 */     this.obj = obj;
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected void introspect()
/*  32:    */   {
/*  33:    */     try
/*  34:    */     {
/*  35: 83 */       BeanInfo bi = Introspector.getBeanInfo(this.obj.getClass());
/*  36: 84 */       this.props = bi.getPropertyDescriptors();
/*  37:    */     }
/*  38:    */     catch (IntrospectionException ex)
/*  39:    */     {
/*  40: 86 */       LogLog.error("Failed to introspect " + this.obj + ": " + ex.getMessage());
/*  41: 87 */       this.props = new PropertyDescriptor[0];
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static void setProperties(Object obj, Properties properties, String prefix)
/*  46:    */   {
/*  47:104 */     new PropertySetter(obj).setProperties(properties, prefix);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setProperties(Properties properties, String prefix)
/*  51:    */   {
/*  52:116 */     int len = prefix.length();
/*  53:118 */     for (Enumeration e = properties.propertyNames(); e.hasMoreElements();)
/*  54:    */     {
/*  55:119 */       String key = (String)e.nextElement();
/*  56:122 */       if (key.startsWith(prefix)) {
/*  57:126 */         if (key.indexOf('.', len + 1) <= 0)
/*  58:    */         {
/*  59:132 */           String value = OptionConverter.findAndSubst(key, properties);
/*  60:133 */           key = key.substring(len);
/*  61:134 */           if (((!"layout".equals(key)) && (!"errorhandler".equals(key))) || (!(this.obj instanceof Appender)))
/*  62:    */           {
/*  63:140 */             PropertyDescriptor prop = getPropertyDescriptor(Introspector.decapitalize(key));
/*  64:141 */             if ((prop != null) && (OptionHandler.class.isAssignableFrom(prop.getPropertyType())) && (prop.getWriteMethod() != null))
/*  65:    */             {
/*  66:144 */               OptionHandler opt = (OptionHandler)OptionConverter.instantiateByKey(properties, prefix + key, prop.getPropertyType(), null);
/*  67:    */               
/*  68:    */ 
/*  69:    */ 
/*  70:148 */               PropertySetter setter = new PropertySetter(opt);
/*  71:149 */               setter.setProperties(properties, prefix + key + ".");
/*  72:    */               try
/*  73:    */               {
/*  74:151 */                 prop.getWriteMethod().invoke(this.obj, new Object[] { opt });
/*  75:    */               }
/*  76:    */               catch (IllegalAccessException ex)
/*  77:    */               {
/*  78:153 */                 LogLog.warn("Failed to set property [" + key + "] to value \"" + value + "\". ", ex);
/*  79:    */               }
/*  80:    */               catch (InvocationTargetException ex)
/*  81:    */               {
/*  82:156 */                 if (((ex.getTargetException() instanceof InterruptedException)) || ((ex.getTargetException() instanceof InterruptedIOException))) {
/*  83:158 */                   Thread.currentThread().interrupt();
/*  84:    */                 }
/*  85:160 */                 LogLog.warn("Failed to set property [" + key + "] to value \"" + value + "\". ", ex);
/*  86:    */               }
/*  87:    */               catch (RuntimeException ex)
/*  88:    */               {
/*  89:163 */                 LogLog.warn("Failed to set property [" + key + "] to value \"" + value + "\". ", ex);
/*  90:    */               }
/*  91:    */             }
/*  92:    */             else
/*  93:    */             {
/*  94:169 */               setProperty(key, value);
/*  95:    */             }
/*  96:    */           }
/*  97:    */         }
/*  98:    */       }
/*  99:    */     }
/* 100:172 */     activate();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setProperty(String name, String value)
/* 104:    */   {
/* 105:192 */     if (value == null) {
/* 106:192 */       return;
/* 107:    */     }
/* 108:194 */     name = Introspector.decapitalize(name);
/* 109:195 */     PropertyDescriptor prop = getPropertyDescriptor(name);
/* 110:199 */     if (prop == null) {
/* 111:200 */       LogLog.warn("No such property [" + name + "] in " + this.obj.getClass().getName() + ".");
/* 112:    */     } else {
/* 113:    */       try
/* 114:    */       {
/* 115:204 */         setProperty(prop, name, value);
/* 116:    */       }
/* 117:    */       catch (PropertySetterException ex)
/* 118:    */       {
/* 119:206 */         LogLog.warn("Failed to set property [" + name + "] to value \"" + value + "\". ", ex.rootCause);
/* 120:    */       }
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setProperty(PropertyDescriptor prop, String name, String value)
/* 125:    */     throws PropertySetterException
/* 126:    */   {
/* 127:223 */     Method setter = prop.getWriteMethod();
/* 128:224 */     if (setter == null) {
/* 129:225 */       throw new PropertySetterException("No setter for property [" + name + "].");
/* 130:    */     }
/* 131:227 */     Class[] paramTypes = setter.getParameterTypes();
/* 132:228 */     if (paramTypes.length != 1) {
/* 133:229 */       throw new PropertySetterException("#params for setter != 1");
/* 134:    */     }
/* 135:    */     Object arg;
/* 136:    */     try
/* 137:    */     {
/* 138:234 */       arg = convertArg(value, paramTypes[0]);
/* 139:    */     }
/* 140:    */     catch (Throwable t)
/* 141:    */     {
/* 142:236 */       throw new PropertySetterException("Conversion to type [" + paramTypes[0] + "] failed. Reason: " + t);
/* 143:    */     }
/* 144:239 */     if (arg == null) {
/* 145:240 */       throw new PropertySetterException("Conversion to type [" + paramTypes[0] + "] failed.");
/* 146:    */     }
/* 147:243 */     LogLog.debug("Setting property [" + name + "] to [" + arg + "].");
/* 148:    */     try
/* 149:    */     {
/* 150:245 */       setter.invoke(this.obj, new Object[] { arg });
/* 151:    */     }
/* 152:    */     catch (IllegalAccessException ex)
/* 153:    */     {
/* 154:247 */       throw new PropertySetterException(ex);
/* 155:    */     }
/* 156:    */     catch (InvocationTargetException ex)
/* 157:    */     {
/* 158:249 */       if (((ex.getTargetException() instanceof InterruptedException)) || ((ex.getTargetException() instanceof InterruptedIOException))) {
/* 159:251 */         Thread.currentThread().interrupt();
/* 160:    */       }
/* 161:253 */       throw new PropertySetterException(ex);
/* 162:    */     }
/* 163:    */     catch (RuntimeException ex)
/* 164:    */     {
/* 165:255 */       throw new PropertySetterException(ex);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected Object convertArg(String val, Class type)
/* 170:    */   {
/* 171:266 */     if (val == null) {
/* 172:267 */       return null;
/* 173:    */     }
/* 174:269 */     String v = val.trim();
/* 175:270 */     if (String.class.isAssignableFrom(type)) {
/* 176:271 */       return val;
/* 177:    */     }
/* 178:272 */     if (Integer.TYPE.isAssignableFrom(type)) {
/* 179:273 */       return new Integer(v);
/* 180:    */     }
/* 181:274 */     if (Long.TYPE.isAssignableFrom(type)) {
/* 182:275 */       return new Long(v);
/* 183:    */     }
/* 184:276 */     if (Boolean.TYPE.isAssignableFrom(type))
/* 185:    */     {
/* 186:277 */       if ("true".equalsIgnoreCase(v)) {
/* 187:278 */         return Boolean.TRUE;
/* 188:    */       }
/* 189:279 */       if ("false".equalsIgnoreCase(v)) {
/* 190:280 */         return Boolean.FALSE;
/* 191:    */       }
/* 192:    */     }
/* 193:    */     else
/* 194:    */     {
/* 195:282 */       if (Priority.class.isAssignableFrom(type)) {
/* 196:283 */         return OptionConverter.toLevel(v, Level.DEBUG);
/* 197:    */       }
/* 198:284 */       if (ErrorHandler.class.isAssignableFrom(type)) {
/* 199:285 */         return OptionConverter.instantiateByClassName(v, ErrorHandler.class, null);
/* 200:    */       }
/* 201:    */     }
/* 202:288 */     return null;
/* 203:    */   }
/* 204:    */   
/* 205:    */   protected PropertyDescriptor getPropertyDescriptor(String name)
/* 206:    */   {
/* 207:294 */     if (this.props == null) {
/* 208:294 */       introspect();
/* 209:    */     }
/* 210:296 */     for (int i = 0; i < this.props.length; i++) {
/* 211:297 */       if (name.equals(this.props[i].getName())) {
/* 212:298 */         return this.props[i];
/* 213:    */       }
/* 214:    */     }
/* 215:301 */     return null;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void activate()
/* 219:    */   {
/* 220:306 */     if ((this.obj instanceof OptionHandler)) {
/* 221:307 */       ((OptionHandler)this.obj).activateOptions();
/* 222:    */     }
/* 223:    */   }
/* 224:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.config.PropertySetter
 * JD-Core Version:    0.7.0.1
 */