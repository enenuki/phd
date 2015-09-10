/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.Map;
/*   6:    */ 
/*   7:    */ public class InnerClassesAttribute
/*   8:    */   extends AttributeInfo
/*   9:    */ {
/*  10:    */   public static final String tag = "InnerClasses";
/*  11:    */   
/*  12:    */   InnerClassesAttribute(ConstPool cp, int n, DataInputStream in)
/*  13:    */     throws IOException
/*  14:    */   {
/*  15: 34 */     super(cp, n, in);
/*  16:    */   }
/*  17:    */   
/*  18:    */   private InnerClassesAttribute(ConstPool cp, byte[] info)
/*  19:    */   {
/*  20: 38 */     super(cp, "InnerClasses", info);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public InnerClassesAttribute(ConstPool cp)
/*  24:    */   {
/*  25: 47 */     super(cp, "InnerClasses", new byte[2]);
/*  26: 48 */     ByteArray.write16bit(0, get(), 0);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int tableLength()
/*  30:    */   {
/*  31: 54 */     return ByteArray.readU16bit(get(), 0);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int innerClassIndex(int nth)
/*  35:    */   {
/*  36: 60 */     return ByteArray.readU16bit(get(), nth * 8 + 2);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String innerClass(int nth)
/*  40:    */   {
/*  41: 70 */     int i = innerClassIndex(nth);
/*  42: 71 */     if (i == 0) {
/*  43: 72 */       return null;
/*  44:    */     }
/*  45: 74 */     return this.constPool.getClassInfo(i);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setInnerClassIndex(int nth, int index)
/*  49:    */   {
/*  50: 82 */     ByteArray.write16bit(index, get(), nth * 8 + 2);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int outerClassIndex(int nth)
/*  54:    */   {
/*  55: 89 */     return ByteArray.readU16bit(get(), nth * 8 + 4);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String outerClass(int nth)
/*  59:    */   {
/*  60: 99 */     int i = outerClassIndex(nth);
/*  61:100 */     if (i == 0) {
/*  62:101 */       return null;
/*  63:    */     }
/*  64:103 */     return this.constPool.getClassInfo(i);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setOuterClassIndex(int nth, int index)
/*  68:    */   {
/*  69:111 */     ByteArray.write16bit(index, get(), nth * 8 + 4);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int innerNameIndex(int nth)
/*  73:    */   {
/*  74:118 */     return ByteArray.readU16bit(get(), nth * 8 + 6);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String innerName(int nth)
/*  78:    */   {
/*  79:128 */     int i = innerNameIndex(nth);
/*  80:129 */     if (i == 0) {
/*  81:130 */       return null;
/*  82:    */     }
/*  83:132 */     return this.constPool.getUtf8Info(i);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setInnerNameIndex(int nth, int index)
/*  87:    */   {
/*  88:140 */     ByteArray.write16bit(index, get(), nth * 8 + 6);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int accessFlags(int nth)
/*  92:    */   {
/*  93:147 */     return ByteArray.readU16bit(get(), nth * 8 + 8);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setAccessFlags(int nth, int flags)
/*  97:    */   {
/*  98:155 */     ByteArray.write16bit(flags, get(), nth * 8 + 8);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void append(String inner, String outer, String name, int flags)
/* 102:    */   {
/* 103:167 */     int i = this.constPool.addClassInfo(inner);
/* 104:168 */     int o = this.constPool.addClassInfo(outer);
/* 105:169 */     int n = this.constPool.addUtf8Info(name);
/* 106:170 */     append(i, o, n, flags);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void append(int inner, int outer, int name, int flags)
/* 110:    */   {
/* 111:182 */     byte[] data = get();
/* 112:183 */     int len = data.length;
/* 113:184 */     byte[] newData = new byte[len + 8];
/* 114:185 */     for (int i = 2; i < len; i++) {
/* 115:186 */       newData[i] = data[i];
/* 116:    */     }
/* 117:188 */     int n = ByteArray.readU16bit(data, 0);
/* 118:189 */     ByteArray.write16bit(n + 1, newData, 0);
/* 119:    */     
/* 120:191 */     ByteArray.write16bit(inner, newData, len);
/* 121:192 */     ByteArray.write16bit(outer, newData, len + 2);
/* 122:193 */     ByteArray.write16bit(name, newData, len + 4);
/* 123:194 */     ByteArray.write16bit(flags, newData, len + 6);
/* 124:    */     
/* 125:196 */     set(newData);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/* 129:    */   {
/* 130:208 */     byte[] src = get();
/* 131:209 */     byte[] dest = new byte[src.length];
/* 132:210 */     ConstPool cp = getConstPool();
/* 133:211 */     InnerClassesAttribute attr = new InnerClassesAttribute(newCp, dest);
/* 134:212 */     int n = ByteArray.readU16bit(src, 0);
/* 135:213 */     ByteArray.write16bit(n, dest, 0);
/* 136:214 */     int j = 2;
/* 137:215 */     for (int i = 0; i < n; i++)
/* 138:    */     {
/* 139:216 */       int innerClass = ByteArray.readU16bit(src, j);
/* 140:217 */       int outerClass = ByteArray.readU16bit(src, j + 2);
/* 141:218 */       int innerName = ByteArray.readU16bit(src, j + 4);
/* 142:219 */       int innerAccess = ByteArray.readU16bit(src, j + 6);
/* 143:221 */       if (innerClass != 0) {
/* 144:222 */         innerClass = cp.copy(innerClass, newCp, classnames);
/* 145:    */       }
/* 146:224 */       ByteArray.write16bit(innerClass, dest, j);
/* 147:226 */       if (outerClass != 0) {
/* 148:227 */         outerClass = cp.copy(outerClass, newCp, classnames);
/* 149:    */       }
/* 150:229 */       ByteArray.write16bit(outerClass, dest, j + 2);
/* 151:231 */       if (innerName != 0) {
/* 152:232 */         innerName = cp.copy(innerName, newCp, classnames);
/* 153:    */       }
/* 154:234 */       ByteArray.write16bit(innerName, dest, j + 4);
/* 155:235 */       ByteArray.write16bit(innerAccess, dest, j + 6);
/* 156:236 */       j += 8;
/* 157:    */     }
/* 158:239 */     return attr;
/* 159:    */   }
/* 160:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.InnerClassesAttribute
 * JD-Core Version:    0.7.0.1
 */