/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.Map;
/*   6:    */ 
/*   7:    */ public class LineNumberAttribute
/*   8:    */   extends AttributeInfo
/*   9:    */ {
/*  10:    */   public static final String tag = "LineNumberTable";
/*  11:    */   
/*  12:    */   LineNumberAttribute(ConstPool cp, int n, DataInputStream in)
/*  13:    */     throws IOException
/*  14:    */   {
/*  15: 34 */     super(cp, n, in);
/*  16:    */   }
/*  17:    */   
/*  18:    */   private LineNumberAttribute(ConstPool cp, byte[] i)
/*  19:    */   {
/*  20: 38 */     super(cp, "LineNumberTable", i);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int tableLength()
/*  24:    */   {
/*  25: 46 */     return ByteArray.readU16bit(this.info, 0);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int startPc(int i)
/*  29:    */   {
/*  30: 57 */     return ByteArray.readU16bit(this.info, i * 4 + 2);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int lineNumber(int i)
/*  34:    */   {
/*  35: 68 */     return ByteArray.readU16bit(this.info, i * 4 + 4);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int toLineNumber(int pc)
/*  39:    */   {
/*  40: 77 */     int n = tableLength();
/*  41: 78 */     for (int i = 0; i < n; i++) {
/*  42: 80 */       if (pc < startPc(i))
/*  43:    */       {
/*  44: 81 */         if (i != 0) {
/*  45:    */           break;
/*  46:    */         }
/*  47: 82 */         return lineNumber(0);
/*  48:    */       }
/*  49:    */     }
/*  50: 86 */     return lineNumber(i - 1);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int toStartPc(int line)
/*  54:    */   {
/*  55: 97 */     int n = tableLength();
/*  56: 98 */     for (int i = 0; i < n; i++) {
/*  57: 99 */       if (line == lineNumber(i)) {
/*  58:100 */         return startPc(i);
/*  59:    */       }
/*  60:    */     }
/*  61:102 */     return -1;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Pc toNearPc(int line)
/*  65:    */   {
/*  66:129 */     int n = tableLength();
/*  67:130 */     int nearPc = 0;
/*  68:131 */     int distance = 0;
/*  69:132 */     if (n > 0)
/*  70:    */     {
/*  71:133 */       distance = lineNumber(0) - line;
/*  72:134 */       nearPc = startPc(0);
/*  73:    */     }
/*  74:137 */     for (int i = 1; i < n; i++)
/*  75:    */     {
/*  76:138 */       int d = lineNumber(i) - line;
/*  77:139 */       if (((d < 0) && (d > distance)) || ((d >= 0) && ((d < distance) || (distance < 0))))
/*  78:    */       {
/*  79:141 */         distance = d;
/*  80:142 */         nearPc = startPc(i);
/*  81:    */       }
/*  82:    */     }
/*  83:146 */     Pc res = new Pc();
/*  84:147 */     res.index = nearPc;
/*  85:148 */     res.line = (line + distance);
/*  86:149 */     return res;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/*  90:    */   {
/*  91:159 */     byte[] src = this.info;
/*  92:160 */     int num = src.length;
/*  93:161 */     byte[] dest = new byte[num];
/*  94:162 */     for (int i = 0; i < num; i++) {
/*  95:163 */       dest[i] = src[i];
/*  96:    */     }
/*  97:165 */     LineNumberAttribute attr = new LineNumberAttribute(newCp, dest);
/*  98:166 */     return attr;
/*  99:    */   }
/* 100:    */   
/* 101:    */   void shiftPc(int where, int gapLength, boolean exclusive)
/* 102:    */   {
/* 103:173 */     int n = tableLength();
/* 104:174 */     for (int i = 0; i < n; i++)
/* 105:    */     {
/* 106:175 */       int pos = i * 4 + 2;
/* 107:176 */       int pc = ByteArray.readU16bit(this.info, pos);
/* 108:177 */       if ((pc > where) || ((exclusive) && (pc == where))) {
/* 109:178 */         ByteArray.write16bit(pc + gapLength, this.info, pos);
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static class Pc
/* 115:    */   {
/* 116:    */     public int index;
/* 117:    */     public int line;
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.LineNumberAttribute
 * JD-Core Version:    0.7.0.1
 */