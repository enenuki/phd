/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.Map;
/*   6:    */ 
/*   7:    */ public class LocalVariableAttribute
/*   8:    */   extends AttributeInfo
/*   9:    */ {
/*  10:    */   public static final String tag = "LocalVariableTable";
/*  11:    */   public static final String typeTag = "LocalVariableTypeTable";
/*  12:    */   
/*  13:    */   public LocalVariableAttribute(ConstPool cp)
/*  14:    */   {
/*  15: 40 */     super(cp, "LocalVariableTable", new byte[2]);
/*  16: 41 */     ByteArray.write16bit(0, this.info, 0);
/*  17:    */   }
/*  18:    */   
/*  19:    */   /**
/*  20:    */    * @deprecated
/*  21:    */    */
/*  22:    */   public LocalVariableAttribute(ConstPool cp, String name)
/*  23:    */   {
/*  24: 56 */     super(cp, name, new byte[2]);
/*  25: 57 */     ByteArray.write16bit(0, this.info, 0);
/*  26:    */   }
/*  27:    */   
/*  28:    */   LocalVariableAttribute(ConstPool cp, int n, DataInputStream in)
/*  29:    */     throws IOException
/*  30:    */   {
/*  31: 63 */     super(cp, n, in);
/*  32:    */   }
/*  33:    */   
/*  34:    */   LocalVariableAttribute(ConstPool cp, String name, byte[] i)
/*  35:    */   {
/*  36: 67 */     super(cp, name, i);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void addEntry(int startPc, int length, int nameIndex, int descriptorIndex, int index)
/*  40:    */   {
/*  41: 81 */     int size = this.info.length;
/*  42: 82 */     byte[] newInfo = new byte[size + 10];
/*  43: 83 */     ByteArray.write16bit(tableLength() + 1, newInfo, 0);
/*  44: 84 */     for (int i = 2; i < size; i++) {
/*  45: 85 */       newInfo[i] = this.info[i];
/*  46:    */     }
/*  47: 87 */     ByteArray.write16bit(startPc, newInfo, size);
/*  48: 88 */     ByteArray.write16bit(length, newInfo, size + 2);
/*  49: 89 */     ByteArray.write16bit(nameIndex, newInfo, size + 4);
/*  50: 90 */     ByteArray.write16bit(descriptorIndex, newInfo, size + 6);
/*  51: 91 */     ByteArray.write16bit(index, newInfo, size + 8);
/*  52: 92 */     this.info = newInfo;
/*  53:    */   }
/*  54:    */   
/*  55:    */   void renameClass(String oldname, String newname)
/*  56:    */   {
/*  57: 96 */     ConstPool cp = getConstPool();
/*  58: 97 */     int n = tableLength();
/*  59: 98 */     for (int i = 0; i < n; i++)
/*  60:    */     {
/*  61: 99 */       int pos = i * 10 + 2;
/*  62:100 */       int index = ByteArray.readU16bit(this.info, pos + 6);
/*  63:101 */       if (index != 0)
/*  64:    */       {
/*  65:102 */         String desc = cp.getUtf8Info(index);
/*  66:103 */         desc = renameEntry(desc, oldname, newname);
/*  67:104 */         ByteArray.write16bit(cp.addUtf8Info(desc), this.info, pos + 6);
/*  68:    */       }
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   String renameEntry(String desc, String oldname, String newname)
/*  73:    */   {
/*  74:110 */     return Descriptor.rename(desc, oldname, newname);
/*  75:    */   }
/*  76:    */   
/*  77:    */   void renameClass(Map classnames)
/*  78:    */   {
/*  79:114 */     ConstPool cp = getConstPool();
/*  80:115 */     int n = tableLength();
/*  81:116 */     for (int i = 0; i < n; i++)
/*  82:    */     {
/*  83:117 */       int pos = i * 10 + 2;
/*  84:118 */       int index = ByteArray.readU16bit(this.info, pos + 6);
/*  85:119 */       if (index != 0)
/*  86:    */       {
/*  87:120 */         String desc = cp.getUtf8Info(index);
/*  88:121 */         desc = renameEntry(desc, classnames);
/*  89:122 */         ByteArray.write16bit(cp.addUtf8Info(desc), this.info, pos + 6);
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   String renameEntry(String desc, Map classnames)
/*  95:    */   {
/*  96:128 */     return Descriptor.rename(desc, classnames);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void shiftIndex(int lessThan, int delta)
/* 100:    */   {
/* 101:139 */     int size = this.info.length;
/* 102:140 */     for (int i = 2; i < size; i += 10)
/* 103:    */     {
/* 104:141 */       int org = ByteArray.readU16bit(this.info, i + 8);
/* 105:142 */       if (org >= lessThan) {
/* 106:143 */         ByteArray.write16bit(org + delta, this.info, i + 8);
/* 107:    */       }
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int tableLength()
/* 112:    */   {
/* 113:152 */     return ByteArray.readU16bit(this.info, 0);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int startPc(int i)
/* 117:    */   {
/* 118:163 */     return ByteArray.readU16bit(this.info, i * 10 + 2);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int codeLength(int i)
/* 122:    */   {
/* 123:174 */     return ByteArray.readU16bit(this.info, i * 10 + 4);
/* 124:    */   }
/* 125:    */   
/* 126:    */   void shiftPc(int where, int gapLength, boolean exclusive)
/* 127:    */   {
/* 128:181 */     int n = tableLength();
/* 129:182 */     for (int i = 0; i < n; i++)
/* 130:    */     {
/* 131:183 */       int pos = i * 10 + 2;
/* 132:184 */       int pc = ByteArray.readU16bit(this.info, pos);
/* 133:185 */       int len = ByteArray.readU16bit(this.info, pos + 2);
/* 134:189 */       if ((pc > where) || ((exclusive) && (pc == where) && (pc != 0))) {
/* 135:190 */         ByteArray.write16bit(pc + gapLength, this.info, pos);
/* 136:191 */       } else if ((pc + len > where) || ((exclusive) && (pc + len == where))) {
/* 137:192 */         ByteArray.write16bit(len + gapLength, this.info, pos + 2);
/* 138:    */       }
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int nameIndex(int i)
/* 143:    */   {
/* 144:203 */     return ByteArray.readU16bit(this.info, i * 10 + 6);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String variableName(int i)
/* 148:    */   {
/* 149:213 */     return getConstPool().getUtf8Info(nameIndex(i));
/* 150:    */   }
/* 151:    */   
/* 152:    */   public int descriptorIndex(int i)
/* 153:    */   {
/* 154:229 */     return ByteArray.readU16bit(this.info, i * 10 + 8);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public int signatureIndex(int i)
/* 158:    */   {
/* 159:243 */     return descriptorIndex(i);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String descriptor(int i)
/* 163:    */   {
/* 164:257 */     return getConstPool().getUtf8Info(descriptorIndex(i));
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String signature(int i)
/* 168:    */   {
/* 169:274 */     return descriptor(i);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int index(int i)
/* 173:    */   {
/* 174:284 */     return ByteArray.readU16bit(this.info, i * 10 + 10);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public AttributeInfo copy(ConstPool newCp, Map classnames)
/* 178:    */   {
/* 179:294 */     byte[] src = get();
/* 180:295 */     byte[] dest = new byte[src.length];
/* 181:296 */     ConstPool cp = getConstPool();
/* 182:297 */     LocalVariableAttribute attr = makeThisAttr(newCp, dest);
/* 183:298 */     int n = ByteArray.readU16bit(src, 0);
/* 184:299 */     ByteArray.write16bit(n, dest, 0);
/* 185:300 */     int j = 2;
/* 186:301 */     for (int i = 0; i < n; i++)
/* 187:    */     {
/* 188:302 */       int start = ByteArray.readU16bit(src, j);
/* 189:303 */       int len = ByteArray.readU16bit(src, j + 2);
/* 190:304 */       int name = ByteArray.readU16bit(src, j + 4);
/* 191:305 */       int type = ByteArray.readU16bit(src, j + 6);
/* 192:306 */       int index = ByteArray.readU16bit(src, j + 8);
/* 193:    */       
/* 194:308 */       ByteArray.write16bit(start, dest, j);
/* 195:309 */       ByteArray.write16bit(len, dest, j + 2);
/* 196:310 */       if (name != 0) {
/* 197:311 */         name = cp.copy(name, newCp, null);
/* 198:    */       }
/* 199:313 */       ByteArray.write16bit(name, dest, j + 4);
/* 200:315 */       if (type != 0)
/* 201:    */       {
/* 202:316 */         String sig = cp.getUtf8Info(type);
/* 203:317 */         sig = Descriptor.rename(sig, classnames);
/* 204:318 */         type = newCp.addUtf8Info(sig);
/* 205:    */       }
/* 206:321 */       ByteArray.write16bit(type, dest, j + 6);
/* 207:322 */       ByteArray.write16bit(index, dest, j + 8);
/* 208:323 */       j += 10;
/* 209:    */     }
/* 210:326 */     return attr;
/* 211:    */   }
/* 212:    */   
/* 213:    */   LocalVariableAttribute makeThisAttr(ConstPool cp, byte[] dest)
/* 214:    */   {
/* 215:331 */     return new LocalVariableAttribute(cp, "LocalVariableTable", dest);
/* 216:    */   }
/* 217:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.LocalVariableAttribute
 * JD-Core Version:    0.7.0.1
 */