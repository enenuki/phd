/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.dom4j.Attribute;
/*   8:    */ import org.dom4j.Element;
/*   9:    */ import org.hibernate.MappingException;
/*  10:    */ import org.hibernate.engine.ResultSetMappingDefinition;
/*  11:    */ import org.hibernate.engine.spi.NamedSQLQueryDefinition;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.internal.util.StringHelper;
/*  14:    */ import org.jboss.logging.Logger;
/*  15:    */ 
/*  16:    */ public class NamedSQLQuerySecondPass
/*  17:    */   extends ResultSetMappingBinder
/*  18:    */   implements QuerySecondPass
/*  19:    */ {
/*  20: 45 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, NamedSQLQuerySecondPass.class.getName());
/*  21:    */   private Element queryElem;
/*  22:    */   private String path;
/*  23:    */   private Mappings mappings;
/*  24:    */   
/*  25:    */   public NamedSQLQuerySecondPass(Element queryElem, String path, Mappings mappings)
/*  26:    */   {
/*  27: 53 */     this.queryElem = queryElem;
/*  28: 54 */     this.path = path;
/*  29: 55 */     this.mappings = mappings;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void doSecondPass(Map persistentClasses)
/*  33:    */     throws MappingException
/*  34:    */   {
/*  35: 59 */     String queryName = this.queryElem.attribute("name").getValue();
/*  36: 60 */     if (this.path != null) {
/*  37: 60 */       queryName = this.path + '.' + queryName;
/*  38:    */     }
/*  39: 62 */     boolean cacheable = "true".equals(this.queryElem.attributeValue("cacheable"));
/*  40: 63 */     String region = this.queryElem.attributeValue("cache-region");
/*  41: 64 */     Attribute tAtt = this.queryElem.attribute("timeout");
/*  42: 65 */     Integer timeout = tAtt == null ? null : Integer.valueOf(tAtt.getValue());
/*  43: 66 */     Attribute fsAtt = this.queryElem.attribute("fetch-size");
/*  44: 67 */     Integer fetchSize = fsAtt == null ? null : Integer.valueOf(fsAtt.getValue());
/*  45: 68 */     Attribute roAttr = this.queryElem.attribute("read-only");
/*  46: 69 */     boolean readOnly = (roAttr != null) && ("true".equals(roAttr.getValue()));
/*  47: 70 */     Attribute cacheModeAtt = this.queryElem.attribute("cache-mode");
/*  48: 71 */     String cacheMode = cacheModeAtt == null ? null : cacheModeAtt.getValue();
/*  49: 72 */     Attribute cmAtt = this.queryElem.attribute("comment");
/*  50: 73 */     String comment = cmAtt == null ? null : cmAtt.getValue();
/*  51:    */     
/*  52: 75 */     List<String> synchronizedTables = new ArrayList();
/*  53: 76 */     Iterator tables = this.queryElem.elementIterator("synchronize");
/*  54: 77 */     while (tables.hasNext()) {
/*  55: 78 */       synchronizedTables.add(((Element)tables.next()).attributeValue("table"));
/*  56:    */     }
/*  57: 80 */     boolean callable = "true".equals(this.queryElem.attributeValue("callable"));
/*  58:    */     
/*  59:    */ 
/*  60: 83 */     Attribute ref = this.queryElem.attribute("resultset-ref");
/*  61: 84 */     String resultSetRef = ref == null ? null : ref.getValue();
/*  62:    */     NamedSQLQueryDefinition namedQuery;
/*  63:    */     NamedSQLQueryDefinition namedQuery;
/*  64: 85 */     if (StringHelper.isNotEmpty(resultSetRef))
/*  65:    */     {
/*  66: 86 */       namedQuery = new NamedSQLQueryDefinition(queryName, this.queryElem.getText(), resultSetRef, synchronizedTables, cacheable, region, timeout, fetchSize, HbmBinder.getFlushMode(this.queryElem.attributeValue("flush-mode")), HbmBinder.getCacheMode(cacheMode), readOnly, comment, HbmBinder.getParameterTypes(this.queryElem), callable);
/*  67:    */     }
/*  68:    */     else
/*  69:    */     {
/*  70:105 */       ResultSetMappingDefinition definition = buildResultSetMappingDefinition(this.queryElem, this.path, this.mappings);
/*  71:106 */       namedQuery = new NamedSQLQueryDefinition(queryName, this.queryElem.getText(), definition.getQueryReturns(), synchronizedTables, cacheable, region, timeout, fetchSize, HbmBinder.getFlushMode(this.queryElem.attributeValue("flush-mode")), HbmBinder.getCacheMode(cacheMode), readOnly, comment, HbmBinder.getParameterTypes(this.queryElem), callable);
/*  72:    */     }
/*  73:124 */     if (LOG.isDebugEnabled()) {
/*  74:125 */       LOG.debugf("Named SQL query: %s -> %s", namedQuery.getName(), namedQuery.getQueryString());
/*  75:    */     }
/*  76:127 */     this.mappings.addSQLQuery(queryName, namedQuery);
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.NamedSQLQuerySecondPass
 * JD-Core Version:    0.7.0.1
 */