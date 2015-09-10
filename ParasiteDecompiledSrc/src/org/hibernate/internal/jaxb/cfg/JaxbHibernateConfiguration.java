/*    1:     */ package org.hibernate.internal.jaxb.cfg;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.List;
/*    5:     */ import javax.xml.bind.annotation.XmlAccessType;
/*    6:     */ import javax.xml.bind.annotation.XmlAccessorType;
/*    7:     */ import javax.xml.bind.annotation.XmlAttribute;
/*    8:     */ import javax.xml.bind.annotation.XmlElement;
/*    9:     */ import javax.xml.bind.annotation.XmlElements;
/*   10:     */ import javax.xml.bind.annotation.XmlRootElement;
/*   11:     */ import javax.xml.bind.annotation.XmlType;
/*   12:     */ import javax.xml.bind.annotation.XmlValue;
/*   13:     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*   14:     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*   15:     */ 
/*   16:     */ @XmlAccessorType(XmlAccessType.FIELD)
/*   17:     */ @XmlType(name="", propOrder={"sessionFactory", "security"})
/*   18:     */ @XmlRootElement(name="hibernate-configuration")
/*   19:     */ public class JaxbHibernateConfiguration
/*   20:     */ {
/*   21:     */   @XmlElement(name="session-factory", required=true)
/*   22:     */   protected JaxbSessionFactory sessionFactory;
/*   23:     */   protected JaxbSecurity security;
/*   24:     */   
/*   25:     */   public JaxbSessionFactory getSessionFactory()
/*   26:     */   {
/*   27: 164 */     return this.sessionFactory;
/*   28:     */   }
/*   29:     */   
/*   30:     */   public void setSessionFactory(JaxbSessionFactory value)
/*   31:     */   {
/*   32: 176 */     this.sessionFactory = value;
/*   33:     */   }
/*   34:     */   
/*   35:     */   public JaxbSecurity getSecurity()
/*   36:     */   {
/*   37: 188 */     return this.security;
/*   38:     */   }
/*   39:     */   
/*   40:     */   public void setSecurity(JaxbSecurity value)
/*   41:     */   {
/*   42: 200 */     this.security = value;
/*   43:     */   }
/*   44:     */   
/*   45:     */   @XmlAccessorType(XmlAccessType.FIELD)
/*   46:     */   @XmlType(name="", propOrder={"grant"})
/*   47:     */   public static class JaxbSecurity
/*   48:     */   {
/*   49:     */     protected List<JaxbGrant> grant;
/*   50:     */     @XmlAttribute(required=true)
/*   51:     */     protected String context;
/*   52:     */     
/*   53:     */     public List<JaxbGrant> getGrant()
/*   54:     */     {
/*   55: 267 */       if (this.grant == null) {
/*   56: 268 */         this.grant = new ArrayList();
/*   57:     */       }
/*   58: 270 */       return this.grant;
/*   59:     */     }
/*   60:     */     
/*   61:     */     public String getContext()
/*   62:     */     {
/*   63: 282 */       return this.context;
/*   64:     */     }
/*   65:     */     
/*   66:     */     public void setContext(String value)
/*   67:     */     {
/*   68: 294 */       this.context = value;
/*   69:     */     }
/*   70:     */     
/*   71:     */     @XmlAccessorType(XmlAccessType.FIELD)
/*   72:     */     @XmlType(name="")
/*   73:     */     public static class JaxbGrant
/*   74:     */     {
/*   75:     */       @XmlAttribute(required=true)
/*   76:     */       protected String actions;
/*   77:     */       @XmlAttribute(name="entity-name", required=true)
/*   78:     */       protected String entityName;
/*   79:     */       @XmlAttribute(required=true)
/*   80:     */       protected String role;
/*   81:     */       
/*   82:     */       public String getActions()
/*   83:     */       {
/*   84: 337 */         return this.actions;
/*   85:     */       }
/*   86:     */       
/*   87:     */       public void setActions(String value)
/*   88:     */       {
/*   89: 349 */         this.actions = value;
/*   90:     */       }
/*   91:     */       
/*   92:     */       public String getEntityName()
/*   93:     */       {
/*   94: 361 */         return this.entityName;
/*   95:     */       }
/*   96:     */       
/*   97:     */       public void setEntityName(String value)
/*   98:     */       {
/*   99: 373 */         this.entityName = value;
/*  100:     */       }
/*  101:     */       
/*  102:     */       public String getRole()
/*  103:     */       {
/*  104: 385 */         return this.role;
/*  105:     */       }
/*  106:     */       
/*  107:     */       public void setRole(String value)
/*  108:     */       {
/*  109: 397 */         this.role = value;
/*  110:     */       }
/*  111:     */     }
/*  112:     */   }
/*  113:     */   
/*  114:     */   @XmlAccessorType(XmlAccessType.FIELD)
/*  115:     */   @XmlType(name="", propOrder={"property", "mapping", "classCacheOrCollectionCache", "event", "listener"})
/*  116:     */   public static class JaxbSessionFactory
/*  117:     */   {
/*  118:     */     protected List<JaxbProperty> property;
/*  119:     */     protected List<JaxbMapping> mapping;
/*  120:     */     @XmlElements({@XmlElement(name="class-cache", type=JaxbClassCache.class), @XmlElement(name="collection-cache", type=JaxbCollectionCache.class)})
/*  121:     */     protected List<Object> classCacheOrCollectionCache;
/*  122:     */     protected List<JaxbEvent> event;
/*  123:     */     protected List<JaxbListenerElement> listener;
/*  124:     */     @XmlAttribute
/*  125:     */     protected String name;
/*  126:     */     
/*  127:     */     public List<JaxbProperty> getProperty()
/*  128:     */     {
/*  129: 536 */       if (this.property == null) {
/*  130: 537 */         this.property = new ArrayList();
/*  131:     */       }
/*  132: 539 */       return this.property;
/*  133:     */     }
/*  134:     */     
/*  135:     */     public List<JaxbMapping> getMapping()
/*  136:     */     {
/*  137: 565 */       if (this.mapping == null) {
/*  138: 566 */         this.mapping = new ArrayList();
/*  139:     */       }
/*  140: 568 */       return this.mapping;
/*  141:     */     }
/*  142:     */     
/*  143:     */     public List<Object> getClassCacheOrCollectionCache()
/*  144:     */     {
/*  145: 595 */       if (this.classCacheOrCollectionCache == null) {
/*  146: 596 */         this.classCacheOrCollectionCache = new ArrayList();
/*  147:     */       }
/*  148: 598 */       return this.classCacheOrCollectionCache;
/*  149:     */     }
/*  150:     */     
/*  151:     */     public List<JaxbEvent> getEvent()
/*  152:     */     {
/*  153: 624 */       if (this.event == null) {
/*  154: 625 */         this.event = new ArrayList();
/*  155:     */       }
/*  156: 627 */       return this.event;
/*  157:     */     }
/*  158:     */     
/*  159:     */     public List<JaxbListenerElement> getListener()
/*  160:     */     {
/*  161: 653 */       if (this.listener == null) {
/*  162: 654 */         this.listener = new ArrayList();
/*  163:     */       }
/*  164: 656 */       return this.listener;
/*  165:     */     }
/*  166:     */     
/*  167:     */     public String getName()
/*  168:     */     {
/*  169: 668 */       return this.name;
/*  170:     */     }
/*  171:     */     
/*  172:     */     public void setName(String value)
/*  173:     */     {
/*  174: 680 */       this.name = value;
/*  175:     */     }
/*  176:     */     
/*  177:     */     @XmlAccessorType(XmlAccessType.FIELD)
/*  178:     */     @XmlType(name="")
/*  179:     */     public static class JaxbClassCache
/*  180:     */     {
/*  181:     */       @XmlAttribute(name="class", required=true)
/*  182:     */       protected String clazz;
/*  183:     */       @XmlAttribute
/*  184:     */       @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*  185:     */       protected String include;
/*  186:     */       @XmlAttribute
/*  187:     */       protected String region;
/*  188:     */       @XmlAttribute(required=true)
/*  189:     */       protected JaxbUsageAttribute usage;
/*  190:     */       
/*  191:     */       public String getClazz()
/*  192:     */       {
/*  193: 734 */         return this.clazz;
/*  194:     */       }
/*  195:     */       
/*  196:     */       public void setClazz(String value)
/*  197:     */       {
/*  198: 746 */         this.clazz = value;
/*  199:     */       }
/*  200:     */       
/*  201:     */       public String getInclude()
/*  202:     */       {
/*  203: 758 */         if (this.include == null) {
/*  204: 759 */           return "all";
/*  205:     */         }
/*  206: 761 */         return this.include;
/*  207:     */       }
/*  208:     */       
/*  209:     */       public void setInclude(String value)
/*  210:     */       {
/*  211: 774 */         this.include = value;
/*  212:     */       }
/*  213:     */       
/*  214:     */       public String getRegion()
/*  215:     */       {
/*  216: 786 */         return this.region;
/*  217:     */       }
/*  218:     */       
/*  219:     */       public void setRegion(String value)
/*  220:     */       {
/*  221: 798 */         this.region = value;
/*  222:     */       }
/*  223:     */       
/*  224:     */       public JaxbUsageAttribute getUsage()
/*  225:     */       {
/*  226: 810 */         return this.usage;
/*  227:     */       }
/*  228:     */       
/*  229:     */       public void setUsage(JaxbUsageAttribute value)
/*  230:     */       {
/*  231: 822 */         this.usage = value;
/*  232:     */       }
/*  233:     */     }
/*  234:     */     
/*  235:     */     @XmlAccessorType(XmlAccessType.FIELD)
/*  236:     */     @XmlType(name="")
/*  237:     */     public static class JaxbCollectionCache
/*  238:     */     {
/*  239:     */       @XmlAttribute(required=true)
/*  240:     */       protected String collection;
/*  241:     */       @XmlAttribute
/*  242:     */       protected String region;
/*  243:     */       @XmlAttribute(required=true)
/*  244:     */       protected JaxbUsageAttribute usage;
/*  245:     */       
/*  246:     */       public String getCollection()
/*  247:     */       {
/*  248: 867 */         return this.collection;
/*  249:     */       }
/*  250:     */       
/*  251:     */       public void setCollection(String value)
/*  252:     */       {
/*  253: 879 */         this.collection = value;
/*  254:     */       }
/*  255:     */       
/*  256:     */       public String getRegion()
/*  257:     */       {
/*  258: 891 */         return this.region;
/*  259:     */       }
/*  260:     */       
/*  261:     */       public void setRegion(String value)
/*  262:     */       {
/*  263: 903 */         this.region = value;
/*  264:     */       }
/*  265:     */       
/*  266:     */       public JaxbUsageAttribute getUsage()
/*  267:     */       {
/*  268: 915 */         return this.usage;
/*  269:     */       }
/*  270:     */       
/*  271:     */       public void setUsage(JaxbUsageAttribute value)
/*  272:     */       {
/*  273: 927 */         this.usage = value;
/*  274:     */       }
/*  275:     */     }
/*  276:     */     
/*  277:     */     @XmlAccessorType(XmlAccessType.FIELD)
/*  278:     */     @XmlType(name="", propOrder={"listener"})
/*  279:     */     public static class JaxbEvent
/*  280:     */     {
/*  281:     */       protected List<JaxbListenerElement> listener;
/*  282:     */       @XmlAttribute(required=true)
/*  283:     */       protected JaxbTypeAttribute type;
/*  284:     */       
/*  285:     */       public List<JaxbListenerElement> getListener()
/*  286:     */       {
/*  287: 986 */         if (this.listener == null) {
/*  288: 987 */           this.listener = new ArrayList();
/*  289:     */         }
/*  290: 989 */         return this.listener;
/*  291:     */       }
/*  292:     */       
/*  293:     */       public JaxbTypeAttribute getType()
/*  294:     */       {
/*  295:1001 */         return this.type;
/*  296:     */       }
/*  297:     */       
/*  298:     */       public void setType(JaxbTypeAttribute value)
/*  299:     */       {
/*  300:1013 */         this.type = value;
/*  301:     */       }
/*  302:     */     }
/*  303:     */     
/*  304:     */     @XmlAccessorType(XmlAccessType.FIELD)
/*  305:     */     @XmlType(name="")
/*  306:     */     public static class JaxbMapping
/*  307:     */     {
/*  308:     */       @XmlAttribute(name="class")
/*  309:     */       protected String clazz;
/*  310:     */       @XmlAttribute
/*  311:     */       protected String file;
/*  312:     */       @XmlAttribute
/*  313:     */       protected String jar;
/*  314:     */       @XmlAttribute(name="package")
/*  315:     */       protected String _package;
/*  316:     */       @XmlAttribute
/*  317:     */       protected String resource;
/*  318:     */       
/*  319:     */       public String getClazz()
/*  320:     */       {
/*  321:1064 */         return this.clazz;
/*  322:     */       }
/*  323:     */       
/*  324:     */       public void setClazz(String value)
/*  325:     */       {
/*  326:1076 */         this.clazz = value;
/*  327:     */       }
/*  328:     */       
/*  329:     */       public String getFile()
/*  330:     */       {
/*  331:1088 */         return this.file;
/*  332:     */       }
/*  333:     */       
/*  334:     */       public void setFile(String value)
/*  335:     */       {
/*  336:1100 */         this.file = value;
/*  337:     */       }
/*  338:     */       
/*  339:     */       public String getJar()
/*  340:     */       {
/*  341:1112 */         return this.jar;
/*  342:     */       }
/*  343:     */       
/*  344:     */       public void setJar(String value)
/*  345:     */       {
/*  346:1124 */         this.jar = value;
/*  347:     */       }
/*  348:     */       
/*  349:     */       public String getPackage()
/*  350:     */       {
/*  351:1136 */         return this._package;
/*  352:     */       }
/*  353:     */       
/*  354:     */       public void setPackage(String value)
/*  355:     */       {
/*  356:1148 */         this._package = value;
/*  357:     */       }
/*  358:     */       
/*  359:     */       public String getResource()
/*  360:     */       {
/*  361:1160 */         return this.resource;
/*  362:     */       }
/*  363:     */       
/*  364:     */       public void setResource(String value)
/*  365:     */       {
/*  366:1172 */         this.resource = value;
/*  367:     */       }
/*  368:     */     }
/*  369:     */     
/*  370:     */     @XmlAccessorType(XmlAccessType.FIELD)
/*  371:     */     @XmlType(name="", propOrder={"value"})
/*  372:     */     public static class JaxbProperty
/*  373:     */     {
/*  374:     */       @XmlValue
/*  375:     */       protected String value;
/*  376:     */       @XmlAttribute(required=true)
/*  377:     */       protected String name;
/*  378:     */       
/*  379:     */       public String getValue()
/*  380:     */       {
/*  381:1215 */         return this.value;
/*  382:     */       }
/*  383:     */       
/*  384:     */       public void setValue(String value)
/*  385:     */       {
/*  386:1227 */         this.value = value;
/*  387:     */       }
/*  388:     */       
/*  389:     */       public String getName()
/*  390:     */       {
/*  391:1239 */         return this.name;
/*  392:     */       }
/*  393:     */       
/*  394:     */       public void setName(String value)
/*  395:     */       {
/*  396:1251 */         this.name = value;
/*  397:     */       }
/*  398:     */     }
/*  399:     */   }
/*  400:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.jaxb.cfg.JaxbHibernateConfiguration
 * JD-Core Version:    0.7.0.1
 */