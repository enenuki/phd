/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.BufferedInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.security.AccessController;
/*   7:    */ import java.security.PrivilegedAction;
/*   8:    */ import java.util.Enumeration;
/*   9:    */ import java.util.Hashtable;
/*  10:    */ import java.util.Properties;
/*  11:    */ import org.apache.xml.serializer.utils.Messages;
/*  12:    */ import org.apache.xml.serializer.utils.Utils;
/*  13:    */ import org.apache.xml.serializer.utils.WrappedRuntimeException;
/*  14:    */ 
/*  15:    */ public final class OutputPropertiesFactory
/*  16:    */ {
/*  17:    */   private static final String S_BUILTIN_EXTENSIONS_URL = "http://xml.apache.org/xalan";
/*  18:    */   private static final String S_BUILTIN_OLD_EXTENSIONS_URL = "http://xml.apache.org/xslt";
/*  19:    */   public static final String S_BUILTIN_EXTENSIONS_UNIVERSAL = "{http://xml.apache.org/xalan}";
/*  20:    */   public static final String S_KEY_INDENT_AMOUNT = "{http://xml.apache.org/xalan}indent-amount";
/*  21:    */   public static final String S_KEY_LINE_SEPARATOR = "{http://xml.apache.org/xalan}line-separator";
/*  22:    */   public static final String S_KEY_CONTENT_HANDLER = "{http://xml.apache.org/xalan}content-handler";
/*  23:    */   public static final String S_KEY_ENTITIES = "{http://xml.apache.org/xalan}entities";
/*  24:    */   public static final String S_USE_URL_ESCAPING = "{http://xml.apache.org/xalan}use-url-escaping";
/*  25:    */   public static final String S_OMIT_META_TAG = "{http://xml.apache.org/xalan}omit-meta-tag";
/*  26:    */   public static final String S_BUILTIN_OLD_EXTENSIONS_UNIVERSAL = "{http://xml.apache.org/xslt}";
/*  27:173 */   public static final int S_BUILTIN_OLD_EXTENSIONS_UNIVERSAL_LEN = "{http://xml.apache.org/xslt}".length();
/*  28:    */   private static final String S_XSLT_PREFIX = "xslt.output.";
/*  29:181 */   private static final int S_XSLT_PREFIX_LEN = "xslt.output.".length();
/*  30:    */   private static final String S_XALAN_PREFIX = "org.apache.xslt.";
/*  31:183 */   private static final int S_XALAN_PREFIX_LEN = "org.apache.xslt.".length();
/*  32:186 */   private static Integer m_synch_object = new Integer(1);
/*  33:189 */   private static final String PROP_DIR = SerializerBase.PKG_PATH + '/';
/*  34:    */   private static final String PROP_FILE_XML = "output_xml.properties";
/*  35:    */   private static final String PROP_FILE_TEXT = "output_text.properties";
/*  36:    */   private static final String PROP_FILE_HTML = "output_html.properties";
/*  37:    */   private static final String PROP_FILE_UNKNOWN = "output_unknown.properties";
/*  38:204 */   private static Properties m_xml_properties = null;
/*  39:207 */   private static Properties m_html_properties = null;
/*  40:210 */   private static Properties m_text_properties = null;
/*  41:213 */   private static Properties m_unknown_properties = null;
/*  42:216 */   private static final Class ACCESS_CONTROLLER_CLASS = findAccessControllerClass();
/*  43:    */   
/*  44:    */   private static Class findAccessControllerClass()
/*  45:    */   {
/*  46:    */     try
/*  47:    */     {
/*  48:228 */       return Class.forName("java.security.AccessController");
/*  49:    */     }
/*  50:    */     catch (Exception e) {}
/*  51:236 */     return null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static final Properties getDefaultMethodProperties(String method)
/*  55:    */   {
/*  56:254 */     String fileName = null;
/*  57:255 */     Properties defaultProperties = null;
/*  58:    */     try
/*  59:    */     {
/*  60:260 */       synchronized (m_synch_object)
/*  61:    */       {
/*  62:262 */         if (null == m_xml_properties)
/*  63:    */         {
/*  64:264 */           fileName = "output_xml.properties";
/*  65:265 */           m_xml_properties = loadPropertiesFile(fileName, null);
/*  66:    */         }
/*  67:    */       }
/*  68:269 */       if (method.equals("xml"))
/*  69:    */       {
/*  70:271 */         defaultProperties = m_xml_properties;
/*  71:    */       }
/*  72:273 */       else if (method.equals("html"))
/*  73:    */       {
/*  74:275 */         if (null == m_html_properties)
/*  75:    */         {
/*  76:277 */           fileName = "output_html.properties";
/*  77:278 */           m_html_properties = loadPropertiesFile(fileName, m_xml_properties);
/*  78:    */         }
/*  79:282 */         defaultProperties = m_html_properties;
/*  80:    */       }
/*  81:284 */       else if (method.equals("text"))
/*  82:    */       {
/*  83:286 */         if (null == m_text_properties)
/*  84:    */         {
/*  85:288 */           fileName = "output_text.properties";
/*  86:289 */           m_text_properties = loadPropertiesFile(fileName, m_xml_properties);
/*  87:291 */           if (null == m_text_properties.getProperty("encoding"))
/*  88:    */           {
/*  89:294 */             String mimeEncoding = Encodings.getMimeEncoding(null);
/*  90:295 */             m_text_properties.put("encoding", mimeEncoding);
/*  91:    */           }
/*  92:    */         }
/*  93:301 */         defaultProperties = m_text_properties;
/*  94:    */       }
/*  95:303 */       else if (method.equals(""))
/*  96:    */       {
/*  97:305 */         if (null == m_unknown_properties)
/*  98:    */         {
/*  99:307 */           fileName = "output_unknown.properties";
/* 100:308 */           m_unknown_properties = loadPropertiesFile(fileName, m_xml_properties);
/* 101:    */         }
/* 102:312 */         defaultProperties = m_unknown_properties;
/* 103:    */       }
/* 104:    */       else
/* 105:    */       {
/* 106:317 */         defaultProperties = m_xml_properties;
/* 107:    */       }
/* 108:    */     }
/* 109:    */     catch (IOException ioe)
/* 110:    */     {
/* 111:322 */       throw new WrappedRuntimeException(Utils.messages.createMessage("ER_COULD_NOT_LOAD_METHOD_PROPERTY", new Object[] { fileName, method }), ioe);
/* 112:    */     }
/* 113:330 */     return new Properties(defaultProperties);
/* 114:    */   }
/* 115:    */   
/* 116:    */   private static Properties loadPropertiesFile(String resourceName, Properties defaults)
/* 117:    */     throws IOException
/* 118:    */   {
/* 119:355 */     Properties props = new Properties(defaults);
/* 120:    */     
/* 121:357 */     InputStream is = null;
/* 122:358 */     BufferedInputStream bis = null;
/* 123:    */     try
/* 124:    */     {
/* 125:362 */       if (ACCESS_CONTROLLER_CLASS != null) {
/* 126:364 */         is = (InputStream)AccessController.doPrivileged(new PrivilegedAction()
/* 127:    */         {
/* 128:    */           private final String val$resourceName;
/* 129:    */           
/* 130:    */           public Object run()
/* 131:    */           {
/* 132:368 */             return OutputPropertiesFactory.class.getResourceAsStream(this.val$resourceName);
/* 133:    */           }
/* 134:    */         });
/* 135:    */       } else {
/* 136:376 */         is = OutputPropertiesFactory.class.getResourceAsStream(resourceName);
/* 137:    */       }
/* 138:380 */       bis = new BufferedInputStream(is);
/* 139:381 */       props.load(bis);
/* 140:    */     }
/* 141:    */     catch (IOException ioe)
/* 142:    */     {
/* 143:385 */       if (defaults == null) {
/* 144:387 */         throw ioe;
/* 145:    */       }
/* 146:391 */       throw new WrappedRuntimeException(Utils.messages.createMessage("ER_COULD_NOT_LOAD_RESOURCE", new Object[] { resourceName }), ioe);
/* 147:    */     }
/* 148:    */     catch (SecurityException se)
/* 149:    */     {
/* 150:402 */       if (defaults == null) {
/* 151:404 */         throw se;
/* 152:    */       }
/* 153:408 */       throw new WrappedRuntimeException(Utils.messages.createMessage("ER_COULD_NOT_LOAD_RESOURCE", new Object[] { resourceName }), se);
/* 154:    */     }
/* 155:    */     finally
/* 156:    */     {
/* 157:418 */       if (bis != null) {
/* 158:420 */         bis.close();
/* 159:    */       }
/* 160:422 */       if (is != null) {
/* 161:424 */         is.close();
/* 162:    */       }
/* 163:    */     }
/* 164:442 */     Enumeration keys = ((Properties)props.clone()).keys();
/* 165:443 */     while (keys.hasMoreElements())
/* 166:    */     {
/* 167:445 */       String key = (String)keys.nextElement();
/* 168:    */       
/* 169:    */ 
/* 170:    */ 
/* 171:449 */       String value = null;
/* 172:    */       try
/* 173:    */       {
/* 174:452 */         value = System.getProperty(key);
/* 175:    */       }
/* 176:    */       catch (SecurityException se) {}
/* 177:458 */       if (value == null) {
/* 178:459 */         value = (String)props.get(key);
/* 179:    */       }
/* 180:461 */       String newKey = fixupPropertyString(key, true);
/* 181:462 */       String newValue = null;
/* 182:    */       try
/* 183:    */       {
/* 184:465 */         newValue = System.getProperty(newKey);
/* 185:    */       }
/* 186:    */       catch (SecurityException se) {}
/* 187:471 */       if (newValue == null) {
/* 188:472 */         newValue = fixupPropertyString(value, false);
/* 189:    */       } else {
/* 190:474 */         newValue = fixupPropertyString(newValue, false);
/* 191:    */       }
/* 192:476 */       if ((key != newKey) || (value != newValue))
/* 193:    */       {
/* 194:478 */         props.remove(key);
/* 195:479 */         props.put(newKey, newValue);
/* 196:    */       }
/* 197:    */     }
/* 198:484 */     return props;
/* 199:    */   }
/* 200:    */   
/* 201:    */   private static String fixupPropertyString(String s, boolean doClipping)
/* 202:    */   {
/* 203:497 */     if ((doClipping) && (s.startsWith("xslt.output."))) {
/* 204:499 */       s = s.substring(S_XSLT_PREFIX_LEN);
/* 205:    */     }
/* 206:501 */     if (s.startsWith("org.apache.xslt.")) {
/* 207:503 */       s = "{http://xml.apache.org/xalan}" + s.substring(S_XALAN_PREFIX_LEN);
/* 208:    */     }
/* 209:    */     int index;
/* 210:507 */     if ((index = s.indexOf("\\u003a")) > 0)
/* 211:    */     {
/* 212:509 */       String temp = s.substring(index + 6);
/* 213:510 */       s = s.substring(0, index) + ":" + temp;
/* 214:    */     }
/* 215:513 */     return s;
/* 216:    */   }
/* 217:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.OutputPropertiesFactory
 * JD-Core Version:    0.7.0.1
 */