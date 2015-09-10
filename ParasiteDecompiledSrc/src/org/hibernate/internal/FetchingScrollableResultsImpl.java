/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*   9:    */ import org.hibernate.engine.spi.QueryParameters;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.hql.internal.HolderInstantiator;
/*  13:    */ import org.hibernate.loader.Loader;
/*  14:    */ import org.hibernate.type.Type;
/*  15:    */ 
/*  16:    */ public class FetchingScrollableResultsImpl
/*  17:    */   extends AbstractScrollableResults
/*  18:    */ {
/*  19:    */   public FetchingScrollableResultsImpl(ResultSet rs, PreparedStatement ps, SessionImplementor sess, Loader loader, QueryParameters queryParameters, Type[] types, HolderInstantiator holderInstantiator)
/*  20:    */     throws MappingException
/*  21:    */   {
/*  22: 52 */     super(rs, ps, sess, loader, queryParameters, types, holderInstantiator);
/*  23:    */   }
/*  24:    */   
/*  25: 55 */   private Object[] currentRow = null;
/*  26: 56 */   private int currentPosition = 0;
/*  27: 57 */   private Integer maxPosition = null;
/*  28:    */   
/*  29:    */   protected Object[] getCurrentRow()
/*  30:    */   {
/*  31: 61 */     return this.currentRow;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean next()
/*  35:    */     throws HibernateException
/*  36:    */   {
/*  37: 70 */     if ((this.maxPosition != null) && (this.maxPosition.intValue() <= this.currentPosition))
/*  38:    */     {
/*  39: 71 */       this.currentRow = null;
/*  40: 72 */       this.currentPosition = (this.maxPosition.intValue() + 1);
/*  41: 73 */       return false;
/*  42:    */     }
/*  43: 76 */     if (isResultSetEmpty())
/*  44:    */     {
/*  45: 77 */       this.currentRow = null;
/*  46: 78 */       this.currentPosition = 0;
/*  47: 79 */       return false;
/*  48:    */     }
/*  49: 82 */     Object row = getLoader().loadSequentialRowsForward(getResultSet(), getSession(), getQueryParameters(), false);
/*  50:    */     boolean afterLast;
/*  51:    */     try
/*  52:    */     {
/*  53: 92 */       afterLast = getResultSet().isAfterLast();
/*  54:    */     }
/*  55:    */     catch (SQLException e)
/*  56:    */     {
/*  57: 95 */       throw getSession().getFactory().getSQLExceptionHelper().convert(e, "exception calling isAfterLast()");
/*  58:    */     }
/*  59:101 */     this.currentPosition += 1;
/*  60:102 */     this.currentRow = new Object[] { row };
/*  61:104 */     if ((afterLast) && 
/*  62:105 */       (this.maxPosition == null)) {
/*  63:107 */       this.maxPosition = Integer.valueOf(this.currentPosition);
/*  64:    */     }
/*  65:111 */     afterScrollOperation();
/*  66:    */     
/*  67:113 */     return true;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean previous()
/*  71:    */     throws HibernateException
/*  72:    */   {
/*  73:122 */     if (this.currentPosition <= 1)
/*  74:    */     {
/*  75:123 */       this.currentPosition = 0;
/*  76:124 */       this.currentRow = null;
/*  77:125 */       return false;
/*  78:    */     }
/*  79:128 */     Object loadResult = getLoader().loadSequentialRowsReverse(getResultSet(), getSession(), getQueryParameters(), false, (this.maxPosition != null) && (this.currentPosition > this.maxPosition.intValue()));
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:136 */     this.currentRow = new Object[] { loadResult };
/*  88:137 */     this.currentPosition -= 1;
/*  89:    */     
/*  90:139 */     afterScrollOperation();
/*  91:    */     
/*  92:141 */     return true;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean scroll(int positions)
/*  96:    */     throws HibernateException
/*  97:    */   {
/*  98:153 */     boolean more = false;
/*  99:154 */     if (positions > 0) {
/* 100:156 */       for (int i = 0; i < positions; i++)
/* 101:    */       {
/* 102:157 */         more = next();
/* 103:158 */         if (!more) {
/* 104:    */           break;
/* 105:    */         }
/* 106:    */       }
/* 107:163 */     } else if (positions < 0) {
/* 108:165 */       for (int i = 0; i < 0 - positions; i++)
/* 109:    */       {
/* 110:166 */         more = previous();
/* 111:167 */         if (!more) {
/* 112:    */           break;
/* 113:    */         }
/* 114:    */       }
/* 115:    */     } else {
/* 116:173 */       throw new HibernateException("scroll(0) not valid");
/* 117:    */     }
/* 118:176 */     afterScrollOperation();
/* 119:    */     
/* 120:178 */     return more;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean last()
/* 124:    */     throws HibernateException
/* 125:    */   {
/* 126:187 */     boolean more = false;
/* 127:188 */     if (this.maxPosition != null)
/* 128:    */     {
/* 129:189 */       if (this.currentPosition > this.maxPosition.intValue()) {
/* 130:190 */         more = previous();
/* 131:    */       }
/* 132:192 */       for (int i = this.currentPosition; i < this.maxPosition.intValue(); i++) {
/* 133:193 */         more = next();
/* 134:    */       }
/* 135:    */     }
/* 136:    */     else
/* 137:    */     {
/* 138:    */       try
/* 139:    */       {
/* 140:198 */         if ((isResultSetEmpty()) || (getResultSet().isAfterLast())) {
/* 141:201 */           return false;
/* 142:    */         }
/* 143:204 */         while (!getResultSet().isAfterLast()) {
/* 144:205 */           more = next();
/* 145:    */         }
/* 146:    */       }
/* 147:    */       catch (SQLException e)
/* 148:    */       {
/* 149:209 */         throw getSession().getFactory().getSQLExceptionHelper().convert(e, "exception calling isAfterLast()");
/* 150:    */       }
/* 151:    */     }
/* 152:216 */     afterScrollOperation();
/* 153:    */     
/* 154:218 */     return more;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean first()
/* 158:    */     throws HibernateException
/* 159:    */   {
/* 160:227 */     beforeFirst();
/* 161:228 */     boolean more = next();
/* 162:    */     
/* 163:230 */     afterScrollOperation();
/* 164:    */     
/* 165:232 */     return more;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void beforeFirst()
/* 169:    */     throws HibernateException
/* 170:    */   {
/* 171:    */     try
/* 172:    */     {
/* 173:240 */       getResultSet().beforeFirst();
/* 174:    */     }
/* 175:    */     catch (SQLException e)
/* 176:    */     {
/* 177:243 */       throw getSession().getFactory().getSQLExceptionHelper().convert(e, "exception calling beforeFirst()");
/* 178:    */     }
/* 179:248 */     this.currentRow = null;
/* 180:249 */     this.currentPosition = 0;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void afterLast()
/* 184:    */     throws HibernateException
/* 185:    */   {
/* 186:258 */     last();
/* 187:259 */     next();
/* 188:260 */     afterScrollOperation();
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean isFirst()
/* 192:    */     throws HibernateException
/* 193:    */   {
/* 194:271 */     return this.currentPosition == 1;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public boolean isLast()
/* 198:    */     throws HibernateException
/* 199:    */   {
/* 200:282 */     if (this.maxPosition == null) {
/* 201:284 */       return false;
/* 202:    */     }
/* 203:287 */     return this.currentPosition == this.maxPosition.intValue();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public int getRowNumber()
/* 207:    */     throws HibernateException
/* 208:    */   {
/* 209:297 */     return this.currentPosition;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public boolean setRowNumber(int rowNumber)
/* 213:    */     throws HibernateException
/* 214:    */   {
/* 215:309 */     if (rowNumber == 1) {
/* 216:310 */       return first();
/* 217:    */     }
/* 218:312 */     if (rowNumber == -1) {
/* 219:313 */       return last();
/* 220:    */     }
/* 221:315 */     if ((this.maxPosition != null) && (rowNumber == this.maxPosition.intValue())) {
/* 222:316 */       return last();
/* 223:    */     }
/* 224:318 */     return scroll(rowNumber - this.currentPosition);
/* 225:    */   }
/* 226:    */   
/* 227:    */   private boolean isResultSetEmpty()
/* 228:    */   {
/* 229:    */     try
/* 230:    */     {
/* 231:323 */       return (this.currentPosition == 0) && (!getResultSet().isBeforeFirst()) && (!getResultSet().isAfterLast());
/* 232:    */     }
/* 233:    */     catch (SQLException e)
/* 234:    */     {
/* 235:326 */       throw getSession().getFactory().getSQLExceptionHelper().convert(e, "Could not determine if resultset is empty due to exception calling isBeforeFirst or isAfterLast()");
/* 236:    */     }
/* 237:    */   }
/* 238:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.FetchingScrollableResultsImpl
 * JD-Core Version:    0.7.0.1
 */