/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import java.util.Properties;
/*   6:    */ import java.util.Vector;
/*   7:    */ import javax.xml.transform.TransformerException;
/*   8:    */ import org.apache.xalan.res.XSLMessages;
/*   9:    */ import org.apache.xml.serializer.OutputPropertiesFactory;
/*  10:    */ import org.apache.xml.serializer.OutputPropertyUtils;
/*  11:    */ import org.apache.xml.utils.FastStringBuffer;
/*  12:    */ import org.apache.xml.utils.QName;
/*  13:    */ 
/*  14:    */ public class OutputProperties
/*  15:    */   extends ElemTemplateElement
/*  16:    */   implements Cloneable
/*  17:    */ {
/*  18:    */   static final long serialVersionUID = -6975274363881785488L;
/*  19:    */   
/*  20:    */   public OutputProperties()
/*  21:    */   {
/*  22: 58 */     this("xml");
/*  23:    */   }
/*  24:    */   
/*  25:    */   public OutputProperties(Properties defaults)
/*  26:    */   {
/*  27: 68 */     this.m_properties = new Properties(defaults);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public OutputProperties(String method)
/*  31:    */   {
/*  32: 83 */     this.m_properties = new Properties(OutputPropertiesFactory.getDefaultMethodProperties(method));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Object clone()
/*  36:    */   {
/*  37:    */     try
/*  38:    */     {
/*  39: 99 */       OutputProperties cloned = (OutputProperties)super.clone();
/*  40:    */       
/*  41:101 */       cloned.m_properties = ((Properties)cloned.m_properties.clone());
/*  42:    */       
/*  43:103 */       return cloned;
/*  44:    */     }
/*  45:    */     catch (CloneNotSupportedException e) {}
/*  46:107 */     return null;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setProperty(QName key, String value)
/*  50:    */   {
/*  51:120 */     setProperty(key.toNamespacedString(), value);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setProperty(String key, String value)
/*  55:    */   {
/*  56:132 */     if (key.equals("method")) {
/*  57:134 */       setMethodDefaults(value);
/*  58:    */     }
/*  59:137 */     if (key.startsWith("{http://xml.apache.org/xslt}")) {
/*  60:138 */       key = "{http://xml.apache.org/xalan}" + key.substring(OutputPropertiesFactory.S_BUILTIN_OLD_EXTENSIONS_UNIVERSAL_LEN);
/*  61:    */     }
/*  62:141 */     this.m_properties.put(key, value);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getProperty(QName key)
/*  66:    */   {
/*  67:155 */     return this.m_properties.getProperty(key.toNamespacedString());
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getProperty(String key)
/*  71:    */   {
/*  72:169 */     if (key.startsWith("{http://xml.apache.org/xslt}")) {
/*  73:170 */       key = "{http://xml.apache.org/xalan}" + key.substring(OutputPropertiesFactory.S_BUILTIN_OLD_EXTENSIONS_UNIVERSAL_LEN);
/*  74:    */     }
/*  75:172 */     return this.m_properties.getProperty(key);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setBooleanProperty(QName key, boolean value)
/*  79:    */   {
/*  80:184 */     this.m_properties.put(key.toNamespacedString(), value ? "yes" : "no");
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setBooleanProperty(String key, boolean value)
/*  84:    */   {
/*  85:196 */     this.m_properties.put(key, value ? "yes" : "no");
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean getBooleanProperty(QName key)
/*  89:    */   {
/*  90:212 */     return getBooleanProperty(key.toNamespacedString());
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean getBooleanProperty(String key)
/*  94:    */   {
/*  95:228 */     return OutputPropertyUtils.getBooleanProperty(key, this.m_properties);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIntProperty(QName key, int value)
/*  99:    */   {
/* 100:240 */     setIntProperty(key.toNamespacedString(), value);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setIntProperty(String key, int value)
/* 104:    */   {
/* 105:252 */     this.m_properties.put(key, Integer.toString(value));
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getIntProperty(QName key)
/* 109:    */   {
/* 110:268 */     return getIntProperty(key.toNamespacedString());
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getIntProperty(String key)
/* 114:    */   {
/* 115:284 */     return OutputPropertyUtils.getIntProperty(key, this.m_properties);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setQNameProperty(QName key, QName value)
/* 119:    */   {
/* 120:298 */     setQNameProperty(key.toNamespacedString(), value);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setMethodDefaults(String method)
/* 124:    */   {
/* 125:309 */     String defaultMethod = this.m_properties.getProperty("method");
/* 126:311 */     if ((null == defaultMethod) || (!defaultMethod.equals(method)) || (defaultMethod.equals("xml")))
/* 127:    */     {
/* 128:322 */       Properties savedProps = this.m_properties;
/* 129:323 */       Properties newDefaults = OutputPropertiesFactory.getDefaultMethodProperties(method);
/* 130:    */       
/* 131:325 */       this.m_properties = new Properties(newDefaults);
/* 132:326 */       copyFrom(savedProps, false);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setQNameProperty(String key, QName value)
/* 137:    */   {
/* 138:341 */     setProperty(key, value.toNamespacedString());
/* 139:    */   }
/* 140:    */   
/* 141:    */   public QName getQNameProperty(QName key)
/* 142:    */   {
/* 143:356 */     return getQNameProperty(key.toNamespacedString());
/* 144:    */   }
/* 145:    */   
/* 146:    */   public QName getQNameProperty(String key)
/* 147:    */   {
/* 148:371 */     return getQNameProperty(key, this.m_properties);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static QName getQNameProperty(String key, Properties props)
/* 152:    */   {
/* 153:388 */     String s = props.getProperty(key);
/* 154:390 */     if (null != s) {
/* 155:391 */       return QName.getQNameFromString(s);
/* 156:    */     }
/* 157:393 */     return null;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setQNameProperties(QName key, Vector v)
/* 161:    */   {
/* 162:406 */     setQNameProperties(key.toNamespacedString(), v);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setQNameProperties(String key, Vector v)
/* 166:    */   {
/* 167:420 */     int s = v.size();
/* 168:    */     
/* 169:    */ 
/* 170:423 */     FastStringBuffer fsb = new FastStringBuffer(9, 9);
/* 171:425 */     for (int i = 0; i < s; i++)
/* 172:    */     {
/* 173:427 */       QName qname = (QName)v.elementAt(i);
/* 174:    */       
/* 175:429 */       fsb.append(qname.toNamespacedString());
/* 176:431 */       if (i < s - 1) {
/* 177:432 */         fsb.append(' ');
/* 178:    */       }
/* 179:    */     }
/* 180:435 */     this.m_properties.put(key, fsb.toString());
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Vector getQNameProperties(QName key)
/* 184:    */   {
/* 185:451 */     return getQNameProperties(key.toNamespacedString());
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Vector getQNameProperties(String key)
/* 189:    */   {
/* 190:467 */     return getQNameProperties(key, this.m_properties);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static Vector getQNameProperties(String key, Properties props)
/* 194:    */   {
/* 195:485 */     String s = props.getProperty(key);
/* 196:487 */     if (null != s)
/* 197:    */     {
/* 198:489 */       Vector v = new Vector();
/* 199:490 */       int l = s.length();
/* 200:491 */       boolean inCurly = false;
/* 201:492 */       FastStringBuffer buf = new FastStringBuffer();
/* 202:497 */       for (int i = 0; i < l; i++)
/* 203:    */       {
/* 204:499 */         char c = s.charAt(i);
/* 205:501 */         if (Character.isWhitespace(c))
/* 206:    */         {
/* 207:503 */           if (!inCurly)
/* 208:    */           {
/* 209:505 */             if (buf.length() <= 0) {
/* 210:    */               continue;
/* 211:    */             }
/* 212:507 */             QName qname = QName.getQNameFromString(buf.toString());
/* 213:508 */             v.addElement(qname);
/* 214:509 */             buf.reset(); continue;
/* 215:    */           }
/* 216:    */         }
/* 217:514 */         else if ('{' == c) {
/* 218:515 */           inCurly = true;
/* 219:516 */         } else if ('}' == c) {
/* 220:517 */           inCurly = false;
/* 221:    */         }
/* 222:519 */         buf.append(c);
/* 223:    */       }
/* 224:522 */       if (buf.length() > 0)
/* 225:    */       {
/* 226:524 */         QName qname = QName.getQNameFromString(buf.toString());
/* 227:525 */         v.addElement(qname);
/* 228:526 */         buf.reset();
/* 229:    */       }
/* 230:529 */       return v;
/* 231:    */     }
/* 232:532 */     return null;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void recompose(StylesheetRoot root)
/* 236:    */     throws TransformerException
/* 237:    */   {
/* 238:543 */     root.recomposeOutput(this);
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void compose(StylesheetRoot sroot)
/* 242:    */     throws TransformerException
/* 243:    */   {
/* 244:555 */     super.compose(sroot);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Properties getProperties()
/* 248:    */   {
/* 249:566 */     return this.m_properties;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void copyFrom(Properties src)
/* 253:    */   {
/* 254:579 */     copyFrom(src, true);
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void copyFrom(Properties src, boolean shouldResetDefaults)
/* 258:    */   {
/* 259:595 */     Enumeration keys = src.keys();
/* 260:597 */     while (keys.hasMoreElements())
/* 261:    */     {
/* 262:599 */       String key = (String)keys.nextElement();
/* 263:601 */       if (!isLegalPropertyKey(key)) {
/* 264:602 */         throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[] { key }));
/* 265:    */       }
/* 266:604 */       Object oldValue = this.m_properties.get(key);
/* 267:605 */       if (null == oldValue)
/* 268:    */       {
/* 269:607 */         String val = (String)src.get(key);
/* 270:609 */         if ((shouldResetDefaults) && (key.equals("method"))) {
/* 271:611 */           setMethodDefaults(val);
/* 272:    */         }
/* 273:614 */         this.m_properties.put(key, val);
/* 274:    */       }
/* 275:616 */       else if (key.equals("cdata-section-elements"))
/* 276:    */       {
/* 277:618 */         this.m_properties.put(key, (String)oldValue + " " + (String)src.get(key));
/* 278:    */       }
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void copyFrom(OutputProperties opsrc)
/* 283:    */     throws TransformerException
/* 284:    */   {
/* 285:636 */     copyFrom(opsrc.getProperties());
/* 286:    */   }
/* 287:    */   
/* 288:    */   public static boolean isLegalPropertyKey(String key)
/* 289:    */   {
/* 290:649 */     return (key.equals("cdata-section-elements")) || (key.equals("doctype-public")) || (key.equals("doctype-system")) || (key.equals("encoding")) || (key.equals("indent")) || (key.equals("media-type")) || (key.equals("method")) || (key.equals("omit-xml-declaration")) || (key.equals("standalone")) || (key.equals("version")) || ((key.length() > 0) && (key.charAt(0) == '{') && (key.lastIndexOf('{') == 0) && (key.indexOf('}') > 0) && (key.lastIndexOf('}') == key.indexOf('}')));
/* 291:    */   }
/* 292:    */   
/* 293:668 */   private Properties m_properties = null;
/* 294:    */   
/* 295:    */   /**
/* 296:    */    * @deprecated
/* 297:    */    */
/* 298:    */   public static Properties getDefaultMethodProperties(String method)
/* 299:    */   {
/* 300:687 */     return OutputPropertiesFactory.getDefaultMethodProperties(method);
/* 301:    */   }
/* 302:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.OutputProperties
 * JD-Core Version:    0.7.0.1
 */