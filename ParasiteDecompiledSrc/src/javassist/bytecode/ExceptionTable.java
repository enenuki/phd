/*   1:    */ package javassist.bytecode;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Map;
/*   8:    */ 
/*   9:    */ public class ExceptionTable
/*  10:    */   implements Cloneable
/*  11:    */ {
/*  12:    */   private ConstPool constPool;
/*  13:    */   private ArrayList entries;
/*  14:    */   
/*  15:    */   public ExceptionTable(ConstPool cp)
/*  16:    */   {
/*  17: 51 */     this.constPool = cp;
/*  18: 52 */     this.entries = new ArrayList();
/*  19:    */   }
/*  20:    */   
/*  21:    */   ExceptionTable(ConstPool cp, DataInputStream in)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24: 56 */     this.constPool = cp;
/*  25: 57 */     int length = in.readUnsignedShort();
/*  26: 58 */     ArrayList list = new ArrayList(length);
/*  27: 59 */     for (int i = 0; i < length; i++)
/*  28:    */     {
/*  29: 60 */       int start = in.readUnsignedShort();
/*  30: 61 */       int end = in.readUnsignedShort();
/*  31: 62 */       int handle = in.readUnsignedShort();
/*  32: 63 */       int type = in.readUnsignedShort();
/*  33: 64 */       list.add(new ExceptionTableEntry(start, end, handle, type));
/*  34:    */     }
/*  35: 67 */     this.entries = list;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Object clone()
/*  39:    */     throws CloneNotSupportedException
/*  40:    */   {
/*  41: 76 */     ExceptionTable r = (ExceptionTable)super.clone();
/*  42: 77 */     r.entries = new ArrayList(this.entries);
/*  43: 78 */     return r;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int size()
/*  47:    */   {
/*  48: 86 */     return this.entries.size();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int startPc(int nth)
/*  52:    */   {
/*  53: 95 */     ExceptionTableEntry e = (ExceptionTableEntry)this.entries.get(nth);
/*  54: 96 */     return e.startPc;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setStartPc(int nth, int value)
/*  58:    */   {
/*  59:106 */     ExceptionTableEntry e = (ExceptionTableEntry)this.entries.get(nth);
/*  60:107 */     e.startPc = value;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int endPc(int nth)
/*  64:    */   {
/*  65:116 */     ExceptionTableEntry e = (ExceptionTableEntry)this.entries.get(nth);
/*  66:117 */     return e.endPc;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setEndPc(int nth, int value)
/*  70:    */   {
/*  71:127 */     ExceptionTableEntry e = (ExceptionTableEntry)this.entries.get(nth);
/*  72:128 */     e.endPc = value;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int handlerPc(int nth)
/*  76:    */   {
/*  77:137 */     ExceptionTableEntry e = (ExceptionTableEntry)this.entries.get(nth);
/*  78:138 */     return e.handlerPc;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setHandlerPc(int nth, int value)
/*  82:    */   {
/*  83:148 */     ExceptionTableEntry e = (ExceptionTableEntry)this.entries.get(nth);
/*  84:149 */     e.handlerPc = value;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int catchType(int nth)
/*  88:    */   {
/*  89:160 */     ExceptionTableEntry e = (ExceptionTableEntry)this.entries.get(nth);
/*  90:161 */     return e.catchType;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setCatchType(int nth, int value)
/*  94:    */   {
/*  95:171 */     ExceptionTableEntry e = (ExceptionTableEntry)this.entries.get(nth);
/*  96:172 */     e.catchType = value;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void add(int index, ExceptionTable table, int offset)
/* 100:    */   {
/* 101:183 */     int len = table.size();
/* 102:    */     for (;;)
/* 103:    */     {
/* 104:184 */       len--;
/* 105:184 */       if (len < 0) {
/* 106:    */         break;
/* 107:    */       }
/* 108:185 */       ExceptionTableEntry e = (ExceptionTableEntry)table.entries.get(len);
/* 109:    */       
/* 110:187 */       add(index, e.startPc + offset, e.endPc + offset, e.handlerPc + offset, e.catchType);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void add(int index, int start, int end, int handler, int type)
/* 115:    */   {
/* 116:202 */     if (start < end) {
/* 117:203 */       this.entries.add(index, new ExceptionTableEntry(start, end, handler, type));
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void add(int start, int end, int handler, int type)
/* 122:    */   {
/* 123:216 */     if (start < end) {
/* 124:217 */       this.entries.add(new ExceptionTableEntry(start, end, handler, type));
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void remove(int index)
/* 129:    */   {
/* 130:226 */     this.entries.remove(index);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public ExceptionTable copy(ConstPool newCp, Map classnames)
/* 134:    */   {
/* 135:239 */     ExceptionTable et = new ExceptionTable(newCp);
/* 136:240 */     ConstPool srcCp = this.constPool;
/* 137:241 */     int len = size();
/* 138:242 */     for (int i = 0; i < len; i++)
/* 139:    */     {
/* 140:243 */       ExceptionTableEntry e = (ExceptionTableEntry)this.entries.get(i);
/* 141:244 */       int type = srcCp.copy(e.catchType, newCp, classnames);
/* 142:245 */       et.add(e.startPc, e.endPc, e.handlerPc, type);
/* 143:    */     }
/* 144:248 */     return et;
/* 145:    */   }
/* 146:    */   
/* 147:    */   void shiftPc(int where, int gapLength, boolean exclusive)
/* 148:    */   {
/* 149:252 */     int len = size();
/* 150:253 */     for (int i = 0; i < len; i++)
/* 151:    */     {
/* 152:254 */       ExceptionTableEntry e = (ExceptionTableEntry)this.entries.get(i);
/* 153:255 */       e.startPc = shiftPc(e.startPc, where, gapLength, exclusive);
/* 154:256 */       e.endPc = shiftPc(e.endPc, where, gapLength, exclusive);
/* 155:257 */       e.handlerPc = shiftPc(e.handlerPc, where, gapLength, exclusive);
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   private static int shiftPc(int pc, int where, int gapLength, boolean exclusive)
/* 160:    */   {
/* 161:263 */     if ((pc > where) || ((exclusive) && (pc == where))) {
/* 162:264 */       pc += gapLength;
/* 163:    */     }
/* 164:266 */     return pc;
/* 165:    */   }
/* 166:    */   
/* 167:    */   void write(DataOutputStream out)
/* 168:    */     throws IOException
/* 169:    */   {
/* 170:270 */     int len = size();
/* 171:271 */     out.writeShort(len);
/* 172:272 */     for (int i = 0; i < len; i++)
/* 173:    */     {
/* 174:273 */       ExceptionTableEntry e = (ExceptionTableEntry)this.entries.get(i);
/* 175:274 */       out.writeShort(e.startPc);
/* 176:275 */       out.writeShort(e.endPc);
/* 177:276 */       out.writeShort(e.handlerPc);
/* 178:277 */       out.writeShort(e.catchType);
/* 179:    */     }
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.ExceptionTable
 * JD-Core Version:    0.7.0.1
 */