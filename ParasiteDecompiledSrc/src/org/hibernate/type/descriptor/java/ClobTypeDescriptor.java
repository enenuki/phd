/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.Clob;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Comparator;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.engine.jdbc.ClobProxy;
/*   9:    */ import org.hibernate.engine.jdbc.LobCreator;
/*  10:    */ import org.hibernate.engine.jdbc.WrappedClob;
/*  11:    */ import org.hibernate.type.descriptor.CharacterStream;
/*  12:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*  13:    */ 
/*  14:    */ public class ClobTypeDescriptor
/*  15:    */   extends AbstractTypeDescriptor<Clob>
/*  16:    */ {
/*  17: 46 */   public static final ClobTypeDescriptor INSTANCE = new ClobTypeDescriptor();
/*  18:    */   
/*  19:    */   public static class ClobMutabilityPlan
/*  20:    */     implements MutabilityPlan<Clob>
/*  21:    */   {
/*  22: 49 */     public static final ClobMutabilityPlan INSTANCE = new ClobMutabilityPlan();
/*  23:    */     
/*  24:    */     public boolean isMutable()
/*  25:    */     {
/*  26: 52 */       return false;
/*  27:    */     }
/*  28:    */     
/*  29:    */     public Clob deepCopy(Clob value)
/*  30:    */     {
/*  31: 56 */       return value;
/*  32:    */     }
/*  33:    */     
/*  34:    */     public Serializable disassemble(Clob value)
/*  35:    */     {
/*  36: 60 */       throw new UnsupportedOperationException("Clobs are not cacheable");
/*  37:    */     }
/*  38:    */     
/*  39:    */     public Clob assemble(Serializable cached)
/*  40:    */     {
/*  41: 64 */       throw new UnsupportedOperationException("Clobs are not cacheable");
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ClobTypeDescriptor()
/*  46:    */   {
/*  47: 69 */     super(Clob.class, ClobMutabilityPlan.INSTANCE);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String toString(Clob value)
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 74 */       return DataHelper.extractString(value.getCharacterStream());
/*  55:    */     }
/*  56:    */     catch (SQLException e)
/*  57:    */     {
/*  58: 77 */       throw new HibernateException("Unable to access clob stream", e);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Clob fromString(String string)
/*  63:    */   {
/*  64: 82 */     return ClobProxy.generateProxy(string);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Comparator<Clob> getComparator()
/*  68:    */   {
/*  69: 88 */     return IncomparableComparator.INSTANCE;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int extractHashCode(Clob value)
/*  73:    */   {
/*  74: 93 */     return System.identityHashCode(value);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean areEqual(Clob one, Clob another)
/*  78:    */   {
/*  79: 98 */     return one == another;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public <X> X unwrap(Clob value, Class<X> type, WrapperOptions options)
/*  83:    */   {
/*  84:103 */     if ((!Clob.class.isAssignableFrom(type)) && (!CharacterStream.class.isAssignableFrom(type))) {
/*  85:104 */       throw unknownUnwrap(type);
/*  86:    */     }
/*  87:107 */     if (value == null) {
/*  88:108 */       return null;
/*  89:    */     }
/*  90:111 */     if (CharacterStream.class.isAssignableFrom(type)) {
/*  91:    */       try
/*  92:    */       {
/*  93:113 */         return new CharacterStreamImpl(DataHelper.extractString(value.getCharacterStream()));
/*  94:    */       }
/*  95:    */       catch (SQLException e)
/*  96:    */       {
/*  97:116 */         throw new HibernateException("Unable to access lob stream", e);
/*  98:    */       }
/*  99:    */     }
/* 100:120 */     Clob clob = WrappedClob.class.isInstance(value) ? ((WrappedClob)value).getWrappedClob() : value;
/* 101:    */     
/* 102:    */ 
/* 103:123 */     return clob;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public <X> Clob wrap(X value, WrapperOptions options)
/* 107:    */   {
/* 108:127 */     if (value == null) {
/* 109:128 */       return null;
/* 110:    */     }
/* 111:131 */     if (!Clob.class.isAssignableFrom(value.getClass())) {
/* 112:132 */       throw unknownWrap(value.getClass());
/* 113:    */     }
/* 114:135 */     return options.getLobCreator().wrap((Clob)value);
/* 115:    */   }
/* 116:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.ClobTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */