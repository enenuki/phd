/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.ScrollableResults;
/*   9:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  10:    */ import org.hibernate.engine.spi.QueryParameters;
/*  11:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  12:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  13:    */ import org.hibernate.hql.internal.HolderInstantiator;
/*  14:    */ import org.hibernate.loader.Loader;
/*  15:    */ import org.hibernate.type.Type;
/*  16:    */ 
/*  17:    */ public class ScrollableResultsImpl
/*  18:    */   extends AbstractScrollableResults
/*  19:    */   implements ScrollableResults
/*  20:    */ {
/*  21:    */   private Object[] currentRow;
/*  22:    */   
/*  23:    */   public ScrollableResultsImpl(ResultSet rs, PreparedStatement ps, SessionImplementor sess, Loader loader, QueryParameters queryParameters, Type[] types, HolderInstantiator holderInstantiator)
/*  24:    */     throws MappingException
/*  25:    */   {
/*  26: 53 */     super(rs, ps, sess, loader, queryParameters, types, holderInstantiator);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected Object[] getCurrentRow()
/*  30:    */   {
/*  31: 57 */     return this.currentRow;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean scroll(int i)
/*  35:    */     throws HibernateException
/*  36:    */   {
/*  37:    */     try
/*  38:    */     {
/*  39: 65 */       boolean result = getResultSet().relative(i);
/*  40: 66 */       prepareCurrentRow(result);
/*  41: 67 */       return result;
/*  42:    */     }
/*  43:    */     catch (SQLException sqle)
/*  44:    */     {
/*  45: 70 */       throw getSession().getFactory().getSQLExceptionHelper().convert(sqle, "could not advance using scroll()");
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean first()
/*  50:    */     throws HibernateException
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 82 */       boolean result = getResultSet().first();
/*  55: 83 */       prepareCurrentRow(result);
/*  56: 84 */       return result;
/*  57:    */     }
/*  58:    */     catch (SQLException sqle)
/*  59:    */     {
/*  60: 87 */       throw getSession().getFactory().getSQLExceptionHelper().convert(sqle, "could not advance using first()");
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean last()
/*  65:    */     throws HibernateException
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69: 99 */       boolean result = getResultSet().last();
/*  70:100 */       prepareCurrentRow(result);
/*  71:101 */       return result;
/*  72:    */     }
/*  73:    */     catch (SQLException sqle)
/*  74:    */     {
/*  75:104 */       throw getSession().getFactory().getSQLExceptionHelper().convert(sqle, "could not advance using last()");
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean next()
/*  80:    */     throws HibernateException
/*  81:    */   {
/*  82:    */     try
/*  83:    */     {
/*  84:116 */       boolean result = getResultSet().next();
/*  85:117 */       prepareCurrentRow(result);
/*  86:118 */       return result;
/*  87:    */     }
/*  88:    */     catch (SQLException sqle)
/*  89:    */     {
/*  90:121 */       throw getSession().getFactory().getSQLExceptionHelper().convert(sqle, "could not advance using next()");
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean previous()
/*  95:    */     throws HibernateException
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:133 */       boolean result = getResultSet().previous();
/* 100:134 */       prepareCurrentRow(result);
/* 101:135 */       return result;
/* 102:    */     }
/* 103:    */     catch (SQLException sqle)
/* 104:    */     {
/* 105:138 */       throw getSession().getFactory().getSQLExceptionHelper().convert(sqle, "could not advance using previous()");
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void afterLast()
/* 110:    */     throws HibernateException
/* 111:    */   {
/* 112:    */     try
/* 113:    */     {
/* 114:150 */       getResultSet().afterLast();
/* 115:    */     }
/* 116:    */     catch (SQLException sqle)
/* 117:    */     {
/* 118:153 */       throw getSession().getFactory().getSQLExceptionHelper().convert(sqle, "exception calling afterLast()");
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void beforeFirst()
/* 123:    */     throws HibernateException
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:165 */       getResultSet().beforeFirst();
/* 128:    */     }
/* 129:    */     catch (SQLException sqle)
/* 130:    */     {
/* 131:168 */       throw getSession().getFactory().getSQLExceptionHelper().convert(sqle, "exception calling beforeFirst()");
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean isFirst()
/* 136:    */     throws HibernateException
/* 137:    */   {
/* 138:    */     try
/* 139:    */     {
/* 140:180 */       return getResultSet().isFirst();
/* 141:    */     }
/* 142:    */     catch (SQLException sqle)
/* 143:    */     {
/* 144:183 */       throw getSession().getFactory().getSQLExceptionHelper().convert(sqle, "exception calling isFirst()");
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isLast()
/* 149:    */     throws HibernateException
/* 150:    */   {
/* 151:    */     try
/* 152:    */     {
/* 153:195 */       return getResultSet().isLast();
/* 154:    */     }
/* 155:    */     catch (SQLException sqle)
/* 156:    */     {
/* 157:198 */       throw getSession().getFactory().getSQLExceptionHelper().convert(sqle, "exception calling isLast()");
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getRowNumber()
/* 162:    */     throws HibernateException
/* 163:    */   {
/* 164:    */     try
/* 165:    */     {
/* 166:207 */       return getResultSet().getRow() - 1;
/* 167:    */     }
/* 168:    */     catch (SQLException sqle)
/* 169:    */     {
/* 170:210 */       throw getSession().getFactory().getSQLExceptionHelper().convert(sqle, "exception calling getRow()");
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public boolean setRowNumber(int rowNumber)
/* 175:    */     throws HibernateException
/* 176:    */   {
/* 177:218 */     if (rowNumber >= 0) {
/* 178:218 */       rowNumber++;
/* 179:    */     }
/* 180:    */     try
/* 181:    */     {
/* 182:220 */       boolean result = getResultSet().absolute(rowNumber);
/* 183:221 */       prepareCurrentRow(result);
/* 184:222 */       return result;
/* 185:    */     }
/* 186:    */     catch (SQLException sqle)
/* 187:    */     {
/* 188:225 */       throw getSession().getFactory().getSQLExceptionHelper().convert(sqle, "could not advance using absolute()");
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   private void prepareCurrentRow(boolean underlyingScrollSuccessful)
/* 193:    */     throws HibernateException
/* 194:    */   {
/* 195:235 */     if (!underlyingScrollSuccessful)
/* 196:    */     {
/* 197:236 */       this.currentRow = null;
/* 198:237 */       return;
/* 199:    */     }
/* 200:240 */     Object result = getLoader().loadSingleRow(getResultSet(), getSession(), getQueryParameters(), false);
/* 201:246 */     if ((result != null) && (result.getClass().isArray())) {
/* 202:247 */       this.currentRow = ((Object[])result);
/* 203:    */     } else {
/* 204:250 */       this.currentRow = new Object[] { result };
/* 205:    */     }
/* 206:253 */     if (getHolderInstantiator() != null) {
/* 207:254 */       this.currentRow = new Object[] { getHolderInstantiator().instantiate(this.currentRow) };
/* 208:    */     }
/* 209:257 */     afterScrollOperation();
/* 210:    */   }
/* 211:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.ScrollableResultsImpl
 * JD-Core Version:    0.7.0.1
 */