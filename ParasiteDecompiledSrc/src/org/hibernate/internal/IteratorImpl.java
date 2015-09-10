/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.NoSuchElementException;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.JDBCException;
/*   9:    */ import org.hibernate.engine.HibernateIterator;
/*  10:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  11:    */ import org.hibernate.engine.loading.internal.LoadContexts;
/*  12:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  13:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  14:    */ import org.hibernate.event.spi.EventSource;
/*  15:    */ import org.hibernate.hql.internal.HolderInstantiator;
/*  16:    */ import org.hibernate.type.EntityType;
/*  17:    */ import org.hibernate.type.Type;
/*  18:    */ import org.jboss.logging.Logger;
/*  19:    */ 
/*  20:    */ public final class IteratorImpl
/*  21:    */   implements HibernateIterator
/*  22:    */ {
/*  23: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, IteratorImpl.class.getName());
/*  24:    */   private ResultSet rs;
/*  25:    */   private final EventSource session;
/*  26:    */   private boolean readOnly;
/*  27:    */   private final Type[] types;
/*  28:    */   private final boolean single;
/*  29:    */   private Object currentResult;
/*  30:    */   private boolean hasNext;
/*  31:    */   private final String[][] names;
/*  32:    */   private PreparedStatement ps;
/*  33:    */   private HolderInstantiator holderInstantiator;
/*  34:    */   
/*  35:    */   public IteratorImpl(ResultSet rs, PreparedStatement ps, EventSource sess, boolean readOnly, Type[] types, String[][] columnNames, HolderInstantiator holderInstantiator)
/*  36:    */     throws HibernateException, SQLException
/*  37:    */   {
/*  38: 71 */     this.rs = rs;
/*  39: 72 */     this.ps = ps;
/*  40: 73 */     this.session = sess;
/*  41: 74 */     this.readOnly = readOnly;
/*  42: 75 */     this.types = types;
/*  43: 76 */     this.names = columnNames;
/*  44: 77 */     this.holderInstantiator = holderInstantiator;
/*  45:    */     
/*  46: 79 */     this.single = (types.length == 1);
/*  47:    */     
/*  48: 81 */     postNext();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void close()
/*  52:    */     throws JDBCException
/*  53:    */   {
/*  54: 85 */     if (this.ps != null) {
/*  55:    */       try
/*  56:    */       {
/*  57: 87 */         LOG.debug("Closing iterator");
/*  58: 88 */         this.ps.close();
/*  59: 89 */         this.ps = null;
/*  60: 90 */         this.rs = null;
/*  61: 91 */         this.hasNext = false;
/*  62:    */       }
/*  63:    */       catch (SQLException e)
/*  64:    */       {
/*  65: 94 */         LOG.unableToCloseIterator(e);
/*  66: 95 */         throw this.session.getFactory().getSQLExceptionHelper().convert(e, "Unable to close iterator");
/*  67:    */       }
/*  68:    */       finally
/*  69:    */       {
/*  70:    */         try
/*  71:    */         {
/*  72:102 */           this.session.getPersistenceContext().getLoadContexts().cleanup(this.rs);
/*  73:    */         }
/*  74:    */         catch (Throwable ignore)
/*  75:    */         {
/*  76:106 */           LOG.debugf("Exception trying to cleanup load context : %s", ignore.getMessage());
/*  77:    */         }
/*  78:    */       }
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   private void postNext()
/*  83:    */     throws SQLException
/*  84:    */   {
/*  85:113 */     LOG.debug("Attempting to retrieve next results");
/*  86:114 */     this.hasNext = this.rs.next();
/*  87:115 */     if (!this.hasNext)
/*  88:    */     {
/*  89:116 */       LOG.debug("Exhausted results");
/*  90:117 */       close();
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94:118 */       LOG.debug("Retrieved next results");
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean hasNext()
/*  99:    */   {
/* 100:122 */     return this.hasNext;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Object next()
/* 104:    */     throws HibernateException
/* 105:    */   {
/* 106:126 */     if (!this.hasNext) {
/* 107:126 */       throw new NoSuchElementException("No more results");
/* 108:    */     }
/* 109:127 */     boolean sessionDefaultReadOnlyOrig = this.session.isDefaultReadOnly();
/* 110:128 */     this.session.setDefaultReadOnly(this.readOnly);
/* 111:    */     try
/* 112:    */     {
/* 113:130 */       boolean isHolder = this.holderInstantiator.isRequired();
/* 114:    */       
/* 115:132 */       LOG.debugf("Assembling results", new Object[0]);
/* 116:    */       Object[] currentResults;
/* 117:133 */       if ((this.single) && (!isHolder))
/* 118:    */       {
/* 119:134 */         this.currentResult = this.types[0].nullSafeGet(this.rs, this.names[0], this.session, null);
/* 120:    */       }
/* 121:    */       else
/* 122:    */       {
/* 123:137 */         currentResults = new Object[this.types.length];
/* 124:138 */         for (int i = 0; i < this.types.length; i++) {
/* 125:139 */           currentResults[i] = this.types[i].nullSafeGet(this.rs, this.names[i], this.session, null);
/* 126:    */         }
/* 127:142 */         if (isHolder) {
/* 128:143 */           this.currentResult = this.holderInstantiator.instantiate(currentResults);
/* 129:    */         } else {
/* 130:146 */           this.currentResult = currentResults;
/* 131:    */         }
/* 132:    */       }
/* 133:150 */       postNext();
/* 134:151 */       LOG.debugf("Returning current results", new Object[0]);
/* 135:152 */       return this.currentResult;
/* 136:    */     }
/* 137:    */     catch (SQLException sqle)
/* 138:    */     {
/* 139:155 */       throw this.session.getFactory().getSQLExceptionHelper().convert(sqle, "could not get next iterator result");
/* 140:    */     }
/* 141:    */     finally
/* 142:    */     {
/* 143:161 */       this.session.setDefaultReadOnly(sessionDefaultReadOnlyOrig);
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void remove()
/* 148:    */   {
/* 149:166 */     if (!this.single) {
/* 150:167 */       throw new UnsupportedOperationException("Not a single column hibernate query result set");
/* 151:    */     }
/* 152:169 */     if (this.currentResult == null) {
/* 153:170 */       throw new IllegalStateException("Called Iterator.remove() before next()");
/* 154:    */     }
/* 155:172 */     if (!(this.types[0] instanceof EntityType)) {
/* 156:173 */       throw new UnsupportedOperationException("Not an entity");
/* 157:    */     }
/* 158:176 */     this.session.delete(((EntityType)this.types[0]).getAssociatedEntityName(), this.currentResult, false, null);
/* 159:    */   }
/* 160:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.IteratorImpl
 * JD-Core Version:    0.7.0.1
 */