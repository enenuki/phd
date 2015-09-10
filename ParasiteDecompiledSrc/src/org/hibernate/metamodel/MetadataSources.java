/*   1:    */ package org.hibernate.metamodel;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.net.URL;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Enumeration;
/*  11:    */ import java.util.LinkedHashSet;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.jar.JarFile;
/*  14:    */ import java.util.zip.ZipEntry;
/*  15:    */ import org.hibernate.cfg.EJB3DTDEntityResolver;
/*  16:    */ import org.hibernate.cfg.EJB3NamingStrategy;
/*  17:    */ import org.hibernate.cfg.NamingStrategy;
/*  18:    */ import org.hibernate.internal.jaxb.JaxbRoot;
/*  19:    */ import org.hibernate.internal.jaxb.Origin;
/*  20:    */ import org.hibernate.internal.jaxb.SourceType;
/*  21:    */ import org.hibernate.metamodel.source.MappingException;
/*  22:    */ import org.hibernate.metamodel.source.MappingNotFoundException;
/*  23:    */ import org.hibernate.metamodel.source.internal.JaxbHelper;
/*  24:    */ import org.hibernate.metamodel.source.internal.MetadataBuilderImpl;
/*  25:    */ import org.hibernate.service.ServiceRegistry;
/*  26:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  27:    */ import org.jboss.logging.Logger;
/*  28:    */ import org.w3c.dom.Document;
/*  29:    */ import org.xml.sax.EntityResolver;
/*  30:    */ 
/*  31:    */ public class MetadataSources
/*  32:    */ {
/*  33: 60 */   private static final Logger LOG = Logger.getLogger(MetadataSources.class);
/*  34: 62 */   private List<JaxbRoot> jaxbRootList = new ArrayList();
/*  35: 63 */   private LinkedHashSet<Class<?>> annotatedClasses = new LinkedHashSet();
/*  36: 64 */   private LinkedHashSet<String> annotatedPackages = new LinkedHashSet();
/*  37:    */   private final JaxbHelper jaxbHelper;
/*  38:    */   private final ServiceRegistry serviceRegistry;
/*  39:    */   private final EntityResolver entityResolver;
/*  40:    */   private final NamingStrategy namingStrategy;
/*  41:    */   private final MetadataBuilderImpl metadataBuilder;
/*  42:    */   
/*  43:    */   public MetadataSources(ServiceRegistry serviceRegistry)
/*  44:    */   {
/*  45: 75 */     this(serviceRegistry, EJB3DTDEntityResolver.INSTANCE, EJB3NamingStrategy.INSTANCE);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public MetadataSources(ServiceRegistry serviceRegistry, EntityResolver entityResolver, NamingStrategy namingStrategy)
/*  49:    */   {
/*  50: 79 */     this.serviceRegistry = serviceRegistry;
/*  51: 80 */     this.entityResolver = entityResolver;
/*  52: 81 */     this.namingStrategy = namingStrategy;
/*  53:    */     
/*  54: 83 */     this.jaxbHelper = new JaxbHelper(this);
/*  55: 84 */     this.metadataBuilder = new MetadataBuilderImpl(this);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<JaxbRoot> getJaxbRootList()
/*  59:    */   {
/*  60: 88 */     return this.jaxbRootList;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Iterable<String> getAnnotatedPackages()
/*  64:    */   {
/*  65: 92 */     return this.annotatedPackages;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Iterable<Class<?>> getAnnotatedClasses()
/*  69:    */   {
/*  70: 96 */     return this.annotatedClasses;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public ServiceRegistry getServiceRegistry()
/*  74:    */   {
/*  75:100 */     return this.serviceRegistry;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public NamingStrategy getNamingStrategy()
/*  79:    */   {
/*  80:104 */     return this.namingStrategy;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public MetadataBuilder getMetadataBuilder()
/*  84:    */   {
/*  85:108 */     return this.metadataBuilder;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Metadata buildMetadata()
/*  89:    */   {
/*  90:112 */     return getMetadataBuilder().buildMetadata();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public MetadataSources addAnnotatedClass(Class annotatedClass)
/*  94:    */   {
/*  95:123 */     this.annotatedClasses.add(annotatedClass);
/*  96:124 */     return this;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public MetadataSources addPackage(String packageName)
/* 100:    */   {
/* 101:135 */     if (packageName == null) {
/* 102:136 */       throw new IllegalArgumentException("The specified package name cannot be null");
/* 103:    */     }
/* 104:138 */     if (packageName.endsWith(".")) {
/* 105:139 */       packageName = packageName.substring(0, packageName.length() - 1);
/* 106:    */     }
/* 107:141 */     this.annotatedPackages.add(packageName);
/* 108:142 */     return this;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public MetadataSources addResource(String name)
/* 112:    */   {
/* 113:153 */     LOG.tracef("reading mappings from resource : %s", name);
/* 114:    */     
/* 115:155 */     Origin origin = new Origin(SourceType.RESOURCE, name);
/* 116:156 */     InputStream resourceInputStream = classLoaderService().locateResourceStream(name);
/* 117:157 */     if (resourceInputStream == null) {
/* 118:158 */       throw new MappingNotFoundException(origin);
/* 119:    */     }
/* 120:160 */     add(resourceInputStream, origin, true);
/* 121:    */     
/* 122:162 */     return this;
/* 123:    */   }
/* 124:    */   
/* 125:    */   private ClassLoaderService classLoaderService()
/* 126:    */   {
/* 127:166 */     return (ClassLoaderService)this.serviceRegistry.getService(ClassLoaderService.class);
/* 128:    */   }
/* 129:    */   
/* 130:    */   private JaxbRoot add(InputStream inputStream, Origin origin, boolean close)
/* 131:    */   {
/* 132:    */     try
/* 133:    */     {
/* 134:171 */       JaxbRoot jaxbRoot = this.jaxbHelper.unmarshal(inputStream, origin);
/* 135:172 */       this.jaxbRootList.add(jaxbRoot);
/* 136:173 */       return jaxbRoot;
/* 137:    */     }
/* 138:    */     finally
/* 139:    */     {
/* 140:176 */       if (close) {
/* 141:    */         try
/* 142:    */         {
/* 143:178 */           inputStream.close();
/* 144:    */         }
/* 145:    */         catch (IOException ignore)
/* 146:    */         {
/* 147:181 */           LOG.trace("Was unable to close input stream");
/* 148:    */         }
/* 149:    */       }
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public MetadataSources addClass(Class entityClass)
/* 154:    */   {
/* 155:196 */     if (entityClass == null) {
/* 156:197 */       throw new IllegalArgumentException("The specified class cannot be null");
/* 157:    */     }
/* 158:199 */     LOG.debugf("adding resource mappings from class convention : %s", entityClass.getName());
/* 159:200 */     String mappingResourceName = entityClass.getName().replace('.', '/') + ".hbm.xml";
/* 160:201 */     addResource(mappingResourceName);
/* 161:202 */     return this;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public MetadataSources addFile(String path)
/* 165:    */   {
/* 166:215 */     return addFile(new File(path));
/* 167:    */   }
/* 168:    */   
/* 169:    */   public MetadataSources addFile(File file)
/* 170:    */   {
/* 171:226 */     String name = file.getAbsolutePath();
/* 172:227 */     LOG.tracef("reading mappings from file : %s", name);
/* 173:228 */     Origin origin = new Origin(SourceType.FILE, name);
/* 174:    */     try
/* 175:    */     {
/* 176:230 */       add(new FileInputStream(file), origin, true);
/* 177:    */     }
/* 178:    */     catch (FileNotFoundException e)
/* 179:    */     {
/* 180:233 */       throw new MappingNotFoundException(e, origin);
/* 181:    */     }
/* 182:235 */     return this;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public MetadataSources addCacheableFile(String path)
/* 186:    */   {
/* 187:248 */     return this;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public MetadataSources addCacheableFile(File file)
/* 191:    */   {
/* 192:265 */     return this;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public MetadataSources addInputStream(InputStream xmlInputStream)
/* 196:    */   {
/* 197:276 */     add(xmlInputStream, new Origin(SourceType.INPUT_STREAM, "<unknown>"), false);
/* 198:277 */     return this;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public MetadataSources addURL(URL url)
/* 202:    */   {
/* 203:288 */     String urlExternalForm = url.toExternalForm();
/* 204:289 */     LOG.debugf("Reading mapping document from URL : %s", urlExternalForm);
/* 205:    */     
/* 206:291 */     Origin origin = new Origin(SourceType.URL, urlExternalForm);
/* 207:    */     try
/* 208:    */     {
/* 209:293 */       add(url.openStream(), origin, true);
/* 210:    */     }
/* 211:    */     catch (IOException e)
/* 212:    */     {
/* 213:296 */       throw new MappingNotFoundException("Unable to open url stream [" + urlExternalForm + "]", e, origin);
/* 214:    */     }
/* 215:298 */     return this;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public MetadataSources addDocument(Document document)
/* 219:    */   {
/* 220:309 */     Origin origin = new Origin(SourceType.DOM, "<unknown>");
/* 221:310 */     JaxbRoot jaxbRoot = this.jaxbHelper.unmarshal(document, origin);
/* 222:311 */     this.jaxbRootList.add(jaxbRoot);
/* 223:312 */     return this;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public MetadataSources addJar(File jar)
/* 227:    */   {
/* 228:325 */     LOG.debugf("Seeking mapping documents in jar file : %s", jar.getName());
/* 229:326 */     Origin origin = new Origin(SourceType.JAR, jar.getAbsolutePath());
/* 230:    */     try
/* 231:    */     {
/* 232:328 */       JarFile jarFile = new JarFile(jar);
/* 233:    */       try
/* 234:    */       {
/* 235:330 */         Enumeration jarEntries = jarFile.entries();
/* 236:331 */         while (jarEntries.hasMoreElements())
/* 237:    */         {
/* 238:332 */           ZipEntry zipEntry = (ZipEntry)jarEntries.nextElement();
/* 239:333 */           if (zipEntry.getName().endsWith(".hbm.xml"))
/* 240:    */           {
/* 241:334 */             LOG.tracef("found mapping document : %s", zipEntry.getName());
/* 242:    */             try
/* 243:    */             {
/* 244:336 */               add(jarFile.getInputStream(zipEntry), origin, true);
/* 245:    */             }
/* 246:    */             catch (Exception e)
/* 247:    */             {
/* 248:339 */               throw new MappingException("could not read mapping documents", e, origin);
/* 249:    */             }
/* 250:    */           }
/* 251:    */         }
/* 252:    */       }
/* 253:    */       finally
/* 254:    */       {
/* 255:    */         try
/* 256:    */         {
/* 257:346 */           jarFile.close();
/* 258:    */         }
/* 259:    */         catch (Exception ignore) {}
/* 260:    */       }
/* 261:355 */       return this;
/* 262:    */     }
/* 263:    */     catch (IOException e)
/* 264:    */     {
/* 265:353 */       throw new MappingNotFoundException(e, origin);
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public MetadataSources addDirectory(File dir)
/* 270:    */   {
/* 271:371 */     File[] files = dir.listFiles();
/* 272:372 */     for (File file : files) {
/* 273:373 */       if (file.isDirectory()) {
/* 274:374 */         addDirectory(file);
/* 275:376 */       } else if (file.getName().endsWith(".hbm.xml")) {
/* 276:377 */         addFile(file);
/* 277:    */       }
/* 278:    */     }
/* 279:380 */     return this;
/* 280:    */   }
/* 281:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.MetadataSources
 * JD-Core Version:    0.7.0.1
 */