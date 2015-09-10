/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.Map;
/*   6:    */ 
/*   7:    */ public class ExceptionsAttribute
/*   8:    */   extends AttributeInfo
/*   9:    */ {
/*  10:    */   public static final String tag = "Exceptions";
/*  11:    */   
/*  12:    */   ExceptionsAttribute(ConstPool cp, int n, DataInputStream in)
/*  13:    */     throws IOException
/*  14:    */   {
/*  15: 34 */     super(cp, n, in);
/*  16:    */   }
/*  17:    */   
/*  18:    */   private ExceptionsAttribute(ConstPool cp, ExceptionsAttribute src, Map classnames)
/*  19:    */   {
/*  20: 45 */     super(cp, "Exceptions");
/*  21: 46 */     copyFrom(src, classnames);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ExceptionsAttribute(ConstPool cp)
/*  25:    */   {
/*  26: 55 */     super(cp, "Exceptions");
/*  27: 56 */     byte[] data = new byte[2]; int 
/*  28: 57 */       tmp16_15 = 0;data[1] = tmp16_15;data[0] = tmp16_15;
/*  29: 58 */     this.info = data;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/*  33:    */   {
/*  34: 70 */     return new ExceptionsAttribute(newCp, this, classnames);
/*  35:    */   }
/*  36:    */   
/*  37:    */   private void copyFrom(ExceptionsAttribute srcAttr, Map classnames)
/*  38:    */   {
/*  39: 82 */     ConstPool srcCp = srcAttr.constPool;
/*  40: 83 */     ConstPool destCp = this.constPool;
/*  41: 84 */     byte[] src = srcAttr.info;
/*  42: 85 */     int num = src.length;
/*  43: 86 */     byte[] dest = new byte[num];
/*  44: 87 */     dest[0] = src[0];
/*  45: 88 */     dest[1] = src[1];
/*  46: 89 */     for (int i = 2; i < num; i += 2)
/*  47:    */     {
/*  48: 90 */       int index = ByteArray.readU16bit(src, i);
/*  49: 91 */       ByteArray.write16bit(srcCp.copy(index, destCp, classnames), dest, i);
/*  50:    */     }
/*  51: 95 */     this.info = dest;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int[] getExceptionIndexes()
/*  55:    */   {
/*  56:102 */     byte[] blist = this.info;
/*  57:103 */     int n = blist.length;
/*  58:104 */     if (n <= 2) {
/*  59:105 */       return null;
/*  60:    */     }
/*  61:107 */     int[] elist = new int[n / 2 - 1];
/*  62:108 */     int k = 0;
/*  63:109 */     for (int j = 2; j < n; j += 2) {
/*  64:110 */       elist[(k++)] = ((blist[j] & 0xFF) << 8 | blist[(j + 1)] & 0xFF);
/*  65:    */     }
/*  66:112 */     return elist;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String[] getExceptions()
/*  70:    */   {
/*  71:119 */     byte[] blist = this.info;
/*  72:120 */     int n = blist.length;
/*  73:121 */     if (n <= 2) {
/*  74:122 */       return null;
/*  75:    */     }
/*  76:124 */     String[] elist = new String[n / 2 - 1];
/*  77:125 */     int k = 0;
/*  78:126 */     for (int j = 2; j < n; j += 2)
/*  79:    */     {
/*  80:127 */       int index = (blist[j] & 0xFF) << 8 | blist[(j + 1)] & 0xFF;
/*  81:128 */       elist[(k++)] = this.constPool.getClassInfo(index);
/*  82:    */     }
/*  83:131 */     return elist;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setExceptionIndexes(int[] elist)
/*  87:    */   {
/*  88:138 */     int n = elist.length;
/*  89:139 */     byte[] blist = new byte[n * 2 + 2];
/*  90:140 */     ByteArray.write16bit(n, blist, 0);
/*  91:141 */     for (int i = 0; i < n; i++) {
/*  92:142 */       ByteArray.write16bit(elist[i], blist, i * 2 + 2);
/*  93:    */     }
/*  94:144 */     this.info = blist;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setExceptions(String[] elist)
/*  98:    */   {
/*  99:151 */     int n = elist.length;
/* 100:152 */     byte[] blist = new byte[n * 2 + 2];
/* 101:153 */     ByteArray.write16bit(n, blist, 0);
/* 102:154 */     for (int i = 0; i < n; i++) {
/* 103:155 */       ByteArray.write16bit(this.constPool.addClassInfo(elist[i]), blist, i * 2 + 2);
/* 104:    */     }
/* 105:158 */     this.info = blist;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int tableLength()
/* 109:    */   {
/* 110:164 */     return this.info.length / 2 - 1;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getException(int nth)
/* 114:    */   {
/* 115:170 */     int index = nth * 2 + 2;
/* 116:171 */     return (this.info[index] & 0xFF) << 8 | this.info[(index + 1)] & 0xFF;
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ExceptionsAttribute
 * JD-Core Version:    0.7.0.1
 */