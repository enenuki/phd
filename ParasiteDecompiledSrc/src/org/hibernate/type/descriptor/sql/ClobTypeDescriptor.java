/*   1:    */ package org.hibernate.type.descriptor.sql;
/*   2:    */ 
/*   3:    */ import java.sql.Clob;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import org.hibernate.type.descriptor.CharacterStream;
/*   8:    */ import org.hibernate.type.descriptor.ValueBinder;
/*   9:    */ import org.hibernate.type.descriptor.ValueExtractor;
/*  10:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*  11:    */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/*  12:    */ 
/*  13:    */ public abstract class ClobTypeDescriptor
/*  14:    */   implements SqlTypeDescriptor
/*  15:    */ {
/*  16: 45 */   public static final ClobTypeDescriptor DEFAULT = new ClobTypeDescriptor()
/*  17:    */   {
/*  18:    */     public <X> BasicBinder<X> getClobBinder(final JavaTypeDescriptor<X> javaTypeDescriptor)
/*  19:    */     {
/*  20: 48 */       new BasicBinder(javaTypeDescriptor, this)
/*  21:    */       {
/*  22:    */         protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
/*  23:    */           throws SQLException
/*  24:    */         {
/*  25: 51 */           if (options.useStreamForLobBinding()) {
/*  26: 52 */             ClobTypeDescriptor.STREAM_BINDING.getClobBinder(javaTypeDescriptor).doBind(st, value, index, options);
/*  27:    */           } else {
/*  28: 55 */             ClobTypeDescriptor.CLOB_BINDING.getClobBinder(javaTypeDescriptor).doBind(st, value, index, options);
/*  29:    */           }
/*  30:    */         }
/*  31:    */       };
/*  32:    */     }
/*  33:    */   };
/*  34: 62 */   public static final ClobTypeDescriptor CLOB_BINDING = new ClobTypeDescriptor()
/*  35:    */   {
/*  36:    */     public <X> BasicBinder<X> getClobBinder(final JavaTypeDescriptor<X> javaTypeDescriptor)
/*  37:    */     {
/*  38: 65 */       new BasicBinder(javaTypeDescriptor, this)
/*  39:    */       {
/*  40:    */         protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
/*  41:    */           throws SQLException
/*  42:    */         {
/*  43: 69 */           st.setClob(index, (Clob)javaTypeDescriptor.unwrap(value, Clob.class, options));
/*  44:    */         }
/*  45:    */       };
/*  46:    */     }
/*  47:    */   };
/*  48: 75 */   public static final ClobTypeDescriptor STREAM_BINDING = new ClobTypeDescriptor()
/*  49:    */   {
/*  50:    */     public <X> BasicBinder<X> getClobBinder(final JavaTypeDescriptor<X> javaTypeDescriptor)
/*  51:    */     {
/*  52: 78 */       new BasicBinder(javaTypeDescriptor, this)
/*  53:    */       {
/*  54:    */         protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options)
/*  55:    */           throws SQLException
/*  56:    */         {
/*  57: 82 */           CharacterStream characterStream = (CharacterStream)javaTypeDescriptor.unwrap(value, CharacterStream.class, options);
/*  58: 83 */           st.setCharacterStream(index, characterStream.getReader(), characterStream.getLength());
/*  59:    */         }
/*  60:    */       };
/*  61:    */     }
/*  62:    */   };
/*  63:    */   
/*  64:    */   protected abstract <X> BasicBinder<X> getClobBinder(JavaTypeDescriptor<X> paramJavaTypeDescriptor);
/*  65:    */   
/*  66:    */   public <X> ValueBinder<X> getBinder(JavaTypeDescriptor<X> javaTypeDescriptor)
/*  67:    */   {
/*  68: 92 */     return getClobBinder(javaTypeDescriptor);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getSqlType()
/*  72:    */   {
/*  73: 96 */     return 2005;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean canBeRemapped()
/*  77:    */   {
/*  78:101 */     return true;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor)
/*  82:    */   {
/*  83:105 */     new BasicExtractor(javaTypeDescriptor, this)
/*  84:    */     {
/*  85:    */       protected X doExtract(ResultSet rs, String name, WrapperOptions options)
/*  86:    */         throws SQLException
/*  87:    */       {
/*  88:108 */         return javaTypeDescriptor.wrap(rs.getClob(name), options);
/*  89:    */       }
/*  90:    */     };
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.sql.ClobTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */