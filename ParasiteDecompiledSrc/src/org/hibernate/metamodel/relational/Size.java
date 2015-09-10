/*   1:    */ package org.hibernate.metamodel.relational;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class Size
/*   6:    */   implements Serializable
/*   7:    */ {
/*   8:    */   public static final int DEFAULT_LENGTH = 255;
/*   9:    */   public static final int DEFAULT_PRECISION = 19;
/*  10:    */   public static final int DEFAULT_SCALE = 2;
/*  11:    */   public Size() {}
/*  12:    */   
/*  13:    */   public static enum LobMultiplier
/*  14:    */   {
/*  15: 39 */     NONE(1L),  K(NONE.factor * 1024L),  M(K.factor * 1024L),  G(M.factor * 1024L);
/*  16:    */     
/*  17:    */     private long factor;
/*  18:    */     
/*  19:    */     private LobMultiplier(long factor)
/*  20:    */     {
/*  21: 47 */       this.factor = factor;
/*  22:    */     }
/*  23:    */     
/*  24:    */     public long getFactor()
/*  25:    */     {
/*  26: 51 */       return this.factor;
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30: 59 */   private long length = 255L;
/*  31: 60 */   private int precision = 19;
/*  32: 61 */   private int scale = 2;
/*  33: 62 */   private LobMultiplier lobMultiplier = LobMultiplier.NONE;
/*  34:    */   
/*  35:    */   public Size(int precision, int scale, long length, LobMultiplier lobMultiplier)
/*  36:    */   {
/*  37: 76 */     this.precision = precision;
/*  38: 77 */     this.scale = scale;
/*  39: 78 */     this.length = length;
/*  40: 79 */     this.lobMultiplier = lobMultiplier;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static Size precision(int precision)
/*  44:    */   {
/*  45: 83 */     return new Size(precision, -1, -1L, null);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static Size precision(int precision, int scale)
/*  49:    */   {
/*  50: 87 */     return new Size(precision, scale, -1L, null);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static Size length(long length)
/*  54:    */   {
/*  55: 91 */     return new Size(-1, -1, length, null);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static Size length(long length, LobMultiplier lobMultiplier)
/*  59:    */   {
/*  60: 95 */     return new Size(-1, -1, length, lobMultiplier);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getPrecision()
/*  64:    */   {
/*  65: 99 */     return this.precision;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getScale()
/*  69:    */   {
/*  70:103 */     return this.scale;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public long getLength()
/*  74:    */   {
/*  75:107 */     return this.length;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public LobMultiplier getLobMultiplier()
/*  79:    */   {
/*  80:111 */     return this.lobMultiplier;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void initialize(Size size)
/*  84:    */   {
/*  85:115 */     this.precision = size.precision;
/*  86:116 */     this.scale = size.scale;
/*  87:117 */     this.length = size.length;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setPrecision(int precision)
/*  91:    */   {
/*  92:121 */     this.precision = precision;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setScale(int scale)
/*  96:    */   {
/*  97:125 */     this.scale = scale;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setLength(long length)
/* 101:    */   {
/* 102:129 */     this.length = length;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setLobMultiplier(LobMultiplier lobMultiplier)
/* 106:    */   {
/* 107:133 */     this.lobMultiplier = lobMultiplier;
/* 108:    */   }
/* 109:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Size
 * JD-Core Version:    0.7.0.1
 */