/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.util.Properties;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.Interceptor;
/*   9:    */ import org.hibernate.MappingException;
/*  10:    */ 
/*  11:    */ @Deprecated
/*  12:    */ public class AnnotationConfiguration
/*  13:    */   extends Configuration
/*  14:    */ {
/*  15:    */   public AnnotationConfiguration addAnnotatedClass(Class annotatedClass)
/*  16:    */     throws MappingException
/*  17:    */   {
/*  18: 58 */     return (AnnotationConfiguration)super.addAnnotatedClass(annotatedClass);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public AnnotationConfiguration addPackage(String packageName)
/*  22:    */     throws MappingException
/*  23:    */   {
/*  24: 66 */     return (AnnotationConfiguration)super.addPackage(packageName);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ExtendedMappings createExtendedMappings()
/*  28:    */   {
/*  29: 70 */     return new ExtendedMappingsImpl();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public AnnotationConfiguration addFile(String xmlFile)
/*  33:    */     throws MappingException
/*  34:    */   {
/*  35: 75 */     super.addFile(xmlFile);
/*  36: 76 */     return this;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public AnnotationConfiguration addFile(File xmlFile)
/*  40:    */     throws MappingException
/*  41:    */   {
/*  42: 81 */     super.addFile(xmlFile);
/*  43: 82 */     return this;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public AnnotationConfiguration addCacheableFile(File xmlFile)
/*  47:    */     throws MappingException
/*  48:    */   {
/*  49: 87 */     super.addCacheableFile(xmlFile);
/*  50: 88 */     return this;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public AnnotationConfiguration addCacheableFile(String xmlFile)
/*  54:    */     throws MappingException
/*  55:    */   {
/*  56: 93 */     super.addCacheableFile(xmlFile);
/*  57: 94 */     return this;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public AnnotationConfiguration addXML(String xml)
/*  61:    */     throws MappingException
/*  62:    */   {
/*  63: 99 */     super.addXML(xml);
/*  64:100 */     return this;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public AnnotationConfiguration addURL(URL url)
/*  68:    */     throws MappingException
/*  69:    */   {
/*  70:105 */     super.addURL(url);
/*  71:106 */     return this;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public AnnotationConfiguration addResource(String resourceName, ClassLoader classLoader)
/*  75:    */     throws MappingException
/*  76:    */   {
/*  77:111 */     super.addResource(resourceName, classLoader);
/*  78:112 */     return this;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public AnnotationConfiguration addDocument(org.w3c.dom.Document doc)
/*  82:    */     throws MappingException
/*  83:    */   {
/*  84:117 */     super.addDocument(doc);
/*  85:118 */     return this;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public AnnotationConfiguration addResource(String resourceName)
/*  89:    */     throws MappingException
/*  90:    */   {
/*  91:123 */     super.addResource(resourceName);
/*  92:124 */     return this;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public AnnotationConfiguration addClass(Class persistentClass)
/*  96:    */     throws MappingException
/*  97:    */   {
/*  98:129 */     super.addClass(persistentClass);
/*  99:130 */     return this;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public AnnotationConfiguration addJar(File jar)
/* 103:    */     throws MappingException
/* 104:    */   {
/* 105:135 */     super.addJar(jar);
/* 106:136 */     return this;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public AnnotationConfiguration addDirectory(File dir)
/* 110:    */     throws MappingException
/* 111:    */   {
/* 112:141 */     super.addDirectory(dir);
/* 113:142 */     return this;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public AnnotationConfiguration setInterceptor(Interceptor interceptor)
/* 117:    */   {
/* 118:147 */     super.setInterceptor(interceptor);
/* 119:148 */     return this;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public AnnotationConfiguration setProperties(Properties properties)
/* 123:    */   {
/* 124:153 */     super.setProperties(properties);
/* 125:154 */     return this;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public AnnotationConfiguration addProperties(Properties extraProperties)
/* 129:    */   {
/* 130:159 */     super.addProperties(extraProperties);
/* 131:160 */     return this;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public AnnotationConfiguration mergeProperties(Properties properties)
/* 135:    */   {
/* 136:165 */     super.mergeProperties(properties);
/* 137:166 */     return this;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public AnnotationConfiguration setProperty(String propertyName, String value)
/* 141:    */   {
/* 142:171 */     super.setProperty(propertyName, value);
/* 143:172 */     return this;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public AnnotationConfiguration configure()
/* 147:    */     throws HibernateException
/* 148:    */   {
/* 149:177 */     super.configure();
/* 150:178 */     return this;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public AnnotationConfiguration configure(String resource)
/* 154:    */     throws HibernateException
/* 155:    */   {
/* 156:183 */     super.configure(resource);
/* 157:184 */     return this;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public AnnotationConfiguration configure(URL url)
/* 161:    */     throws HibernateException
/* 162:    */   {
/* 163:189 */     super.configure(url);
/* 164:190 */     return this;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public AnnotationConfiguration configure(File configFile)
/* 168:    */     throws HibernateException
/* 169:    */   {
/* 170:195 */     super.configure(configFile);
/* 171:196 */     return this;
/* 172:    */   }
/* 173:    */   
/* 174:    */   protected AnnotationConfiguration doConfigure(InputStream stream, String resourceName)
/* 175:    */     throws HibernateException
/* 176:    */   {
/* 177:201 */     super.doConfigure(stream, resourceName);
/* 178:202 */     return this;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public AnnotationConfiguration configure(org.w3c.dom.Document document)
/* 182:    */     throws HibernateException
/* 183:    */   {
/* 184:207 */     super.configure(document);
/* 185:208 */     return this;
/* 186:    */   }
/* 187:    */   
/* 188:    */   protected AnnotationConfiguration doConfigure(org.dom4j.Document doc)
/* 189:    */     throws HibernateException
/* 190:    */   {
/* 191:213 */     super.doConfigure(doc);
/* 192:214 */     return this;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public AnnotationConfiguration setCacheConcurrencyStrategy(String clazz, String concurrencyStrategy)
/* 196:    */   {
/* 197:219 */     super.setCacheConcurrencyStrategy(clazz, concurrencyStrategy);
/* 198:220 */     return this;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public AnnotationConfiguration setCacheConcurrencyStrategy(String clazz, String concurrencyStrategy, String region)
/* 202:    */   {
/* 203:225 */     super.setCacheConcurrencyStrategy(clazz, concurrencyStrategy, region);
/* 204:226 */     return this;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public AnnotationConfiguration setCollectionCacheConcurrencyStrategy(String collectionRole, String concurrencyStrategy)
/* 208:    */     throws MappingException
/* 209:    */   {
/* 210:232 */     super.setCollectionCacheConcurrencyStrategy(collectionRole, concurrencyStrategy);
/* 211:233 */     return this;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public AnnotationConfiguration setNamingStrategy(NamingStrategy namingStrategy)
/* 215:    */   {
/* 216:238 */     super.setNamingStrategy(namingStrategy);
/* 217:239 */     return this;
/* 218:    */   }
/* 219:    */   
/* 220:    */   @Deprecated
/* 221:    */   protected class ExtendedMappingsImpl
/* 222:    */     extends Configuration.MappingsImpl
/* 223:    */   {
/* 224:    */     protected ExtendedMappingsImpl()
/* 225:    */     {
/* 226:243 */       super();
/* 227:    */     }
/* 228:    */   }
/* 229:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.AnnotationConfiguration
 * JD-Core Version:    0.7.0.1
 */