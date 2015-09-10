/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.ScrollableResults;
/*   8:    */ import org.hibernate.engine.query.spi.ParameterMetadata;
/*   9:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  10:    */ import org.hibernate.type.Type;
/*  11:    */ 
/*  12:    */ public class CollectionFilterImpl
/*  13:    */   extends QueryImpl
/*  14:    */ {
/*  15:    */   private Object collection;
/*  16:    */   
/*  17:    */   public CollectionFilterImpl(String queryString, Object collection, SessionImplementor session, ParameterMetadata parameterMetadata)
/*  18:    */   {
/*  19: 49 */     super(queryString, session, parameterMetadata);
/*  20: 50 */     this.collection = collection;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Iterator iterate()
/*  24:    */     throws HibernateException
/*  25:    */   {
/*  26: 58 */     verifyParameters();
/*  27: 59 */     Map namedParams = getNamedParams();
/*  28: 60 */     return getSession().iterateFilter(this.collection, expandParameterLists(namedParams), getQueryParameters(namedParams));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List list()
/*  32:    */     throws HibernateException
/*  33:    */   {
/*  34: 71 */     verifyParameters();
/*  35: 72 */     Map namedParams = getNamedParams();
/*  36: 73 */     return getSession().listFilter(this.collection, expandParameterLists(namedParams), getQueryParameters(namedParams));
/*  37:    */   }
/*  38:    */   
/*  39:    */   public ScrollableResults scroll()
/*  40:    */     throws HibernateException
/*  41:    */   {
/*  42: 84 */     throw new UnsupportedOperationException("Can't scroll filters");
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Type[] typeArray()
/*  46:    */   {
/*  47: 88 */     List typeList = getTypes();
/*  48: 89 */     int size = typeList.size();
/*  49: 90 */     Type[] result = new Type[size + 1];
/*  50: 91 */     for (int i = 0; i < size; i++) {
/*  51: 91 */       result[(i + 1)] = ((Type)typeList.get(i));
/*  52:    */     }
/*  53: 92 */     return result;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Object[] valueArray()
/*  57:    */   {
/*  58: 96 */     List valueList = getValues();
/*  59: 97 */     int size = valueList.size();
/*  60: 98 */     Object[] result = new Object[size + 1];
/*  61: 99 */     for (int i = 0; i < size; i++) {
/*  62: 99 */       result[(i + 1)] = valueList.get(i);
/*  63:    */     }
/*  64:100 */     return result;
/*  65:    */   }
/*  66:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.CollectionFilterImpl
 * JD-Core Version:    0.7.0.1
 */