/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.Blob;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Comparator;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.engine.jdbc.BlobProxy;
/*   9:    */ import org.hibernate.engine.jdbc.LobCreator;
/*  10:    */ import org.hibernate.engine.jdbc.WrappedBlob;
/*  11:    */ import org.hibernate.type.descriptor.BinaryStream;
/*  12:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*  13:    */ 
/*  14:    */ public class BlobTypeDescriptor
/*  15:    */   extends AbstractTypeDescriptor<Blob>
/*  16:    */ {
/*  17: 46 */   public static final BlobTypeDescriptor INSTANCE = new BlobTypeDescriptor();
/*  18:    */   
/*  19:    */   public static class BlobMutabilityPlan
/*  20:    */     implements MutabilityPlan<Blob>
/*  21:    */   {
/*  22: 49 */     public static final BlobMutabilityPlan INSTANCE = new BlobMutabilityPlan();
/*  23:    */     
/*  24:    */     public boolean isMutable()
/*  25:    */     {
/*  26: 52 */       return false;
/*  27:    */     }
/*  28:    */     
/*  29:    */     public Blob deepCopy(Blob value)
/*  30:    */     {
/*  31: 56 */       return value;
/*  32:    */     }
/*  33:    */     
/*  34:    */     public Serializable disassemble(Blob value)
/*  35:    */     {
/*  36: 60 */       throw new UnsupportedOperationException("Blobs are not cacheable");
/*  37:    */     }
/*  38:    */     
/*  39:    */     public Blob assemble(Serializable cached)
/*  40:    */     {
/*  41: 64 */       throw new UnsupportedOperationException("Blobs are not cacheable");
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public BlobTypeDescriptor()
/*  46:    */   {
/*  47: 69 */     super(Blob.class, BlobMutabilityPlan.INSTANCE);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String toString(Blob value)
/*  51:    */   {
/*  52:    */     byte[] bytes;
/*  53:    */     try
/*  54:    */     {
/*  55: 78 */       bytes = DataHelper.extractBytes(value.getBinaryStream());
/*  56:    */     }
/*  57:    */     catch (SQLException e)
/*  58:    */     {
/*  59: 81 */       throw new HibernateException("Unable to access blob stream", e);
/*  60:    */     }
/*  61: 83 */     return PrimitiveByteArrayTypeDescriptor.INSTANCE.toString(bytes);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Blob fromString(String string)
/*  65:    */   {
/*  66: 90 */     return BlobProxy.generateProxy(PrimitiveByteArrayTypeDescriptor.INSTANCE.fromString(string));
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Comparator<Blob> getComparator()
/*  70:    */   {
/*  71: 96 */     return IncomparableComparator.INSTANCE;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int extractHashCode(Blob value)
/*  75:    */   {
/*  76:101 */     return System.identityHashCode(value);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean areEqual(Blob one, Blob another)
/*  80:    */   {
/*  81:106 */     return one == another;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public <X> X unwrap(Blob value, Class<X> type, WrapperOptions options)
/*  85:    */   {
/*  86:111 */     if ((!Blob.class.isAssignableFrom(type)) && (!BinaryStream.class.isAssignableFrom(type))) {
/*  87:112 */       throw unknownUnwrap(type);
/*  88:    */     }
/*  89:115 */     if (value == null) {
/*  90:116 */       return null;
/*  91:    */     }
/*  92:119 */     if (BinaryStream.class.isAssignableFrom(type)) {
/*  93:    */       try
/*  94:    */       {
/*  95:121 */         return new BinaryStreamImpl(DataHelper.extractBytes(value.getBinaryStream()));
/*  96:    */       }
/*  97:    */       catch (SQLException e)
/*  98:    */       {
/*  99:124 */         throw new HibernateException("Unable to access blob stream", e);
/* 100:    */       }
/* 101:    */     }
/* 102:128 */     Blob blob = WrappedBlob.class.isInstance(value) ? ((WrappedBlob)value).getWrappedBlob() : value;
/* 103:    */     
/* 104:    */ 
/* 105:131 */     return blob;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public <X> Blob wrap(X value, WrapperOptions options)
/* 109:    */   {
/* 110:135 */     if (value == null) {
/* 111:136 */       return null;
/* 112:    */     }
/* 113:139 */     if (!Blob.class.isAssignableFrom(value.getClass())) {
/* 114:140 */       throw unknownWrap(value.getClass());
/* 115:    */     }
/* 116:143 */     return options.getLobCreator().wrap((Blob)value);
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.BlobTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */