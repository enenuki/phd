/*   1:    */ package org.hibernate.engine.internal;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Map.Entry;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.engine.spi.QueryParameters;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.engine.spi.TypedValue;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.type.Type;
/*  15:    */ import org.jboss.logging.Logger;
/*  16:    */ 
/*  17:    */ public class ParameterBinder
/*  18:    */ {
/*  19: 50 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, ParameterBinder.class.getName());
/*  20:    */   
/*  21:    */   public static int bindQueryParameters(PreparedStatement st, QueryParameters queryParameters, int start, NamedParameterSource source, SessionImplementor session)
/*  22:    */     throws SQLException, HibernateException
/*  23:    */   {
/*  24: 65 */     int col = start;
/*  25: 66 */     col += bindPositionalParameters(st, queryParameters, col, session);
/*  26: 67 */     col += bindNamedParameters(st, queryParameters, col, source, session);
/*  27: 68 */     return col;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static int bindPositionalParameters(PreparedStatement st, QueryParameters queryParameters, int start, SessionImplementor session)
/*  31:    */     throws SQLException, HibernateException
/*  32:    */   {
/*  33: 76 */     return bindPositionalParameters(st, queryParameters.getPositionalParameterValues(), queryParameters.getPositionalParameterTypes(), start, session);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static int bindPositionalParameters(PreparedStatement st, Object[] values, Type[] types, int start, SessionImplementor session)
/*  37:    */     throws SQLException, HibernateException
/*  38:    */   {
/*  39: 91 */     int span = 0;
/*  40: 92 */     for (int i = 0; i < values.length; i++)
/*  41:    */     {
/*  42: 93 */       types[i].nullSafeSet(st, values[i], start + span, session);
/*  43: 94 */       span += types[i].getColumnSpan(session.getFactory());
/*  44:    */     }
/*  45: 96 */     return span;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static int bindNamedParameters(PreparedStatement ps, QueryParameters queryParameters, int start, NamedParameterSource source, SessionImplementor session)
/*  49:    */     throws SQLException, HibernateException
/*  50:    */   {
/*  51:105 */     return bindNamedParameters(ps, queryParameters.getNamedParameters(), start, source, session);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static int bindNamedParameters(PreparedStatement ps, Map namedParams, int start, NamedParameterSource source, SessionImplementor session)
/*  55:    */     throws SQLException, HibernateException
/*  56:    */   {
/*  57:114 */     if (namedParams != null)
/*  58:    */     {
/*  59:116 */       Iterator iter = namedParams.entrySet().iterator();
/*  60:117 */       int result = 0;
/*  61:118 */       while (iter.hasNext())
/*  62:    */       {
/*  63:119 */         Map.Entry e = (Map.Entry)iter.next();
/*  64:120 */         String name = (String)e.getKey();
/*  65:121 */         TypedValue typedval = (TypedValue)e.getValue();
/*  66:122 */         int[] locations = source.getNamedParameterLocations(name);
/*  67:123 */         for (int i = 0; i < locations.length; i++)
/*  68:    */         {
/*  69:124 */           if (LOG.isDebugEnabled()) {
/*  70:125 */             LOG.debugf("bindNamedParameters() %s -> %s [%s]", typedval.getValue(), name, Integer.valueOf(locations[i] + start));
/*  71:    */           }
/*  72:127 */           typedval.getType().nullSafeSet(ps, typedval.getValue(), locations[i] + start, session);
/*  73:    */         }
/*  74:129 */         result += locations.length;
/*  75:    */       }
/*  76:131 */       return result;
/*  77:    */     }
/*  78:133 */     return 0;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static abstract interface NamedParameterSource
/*  82:    */   {
/*  83:    */     public abstract int[] getNamedParameterLocations(String paramString);
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.internal.ParameterBinder
 * JD-Core Version:    0.7.0.1
 */