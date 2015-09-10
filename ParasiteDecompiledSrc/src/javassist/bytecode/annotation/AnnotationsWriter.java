/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import javassist.bytecode.ByteArray;
/*   6:    */ import javassist.bytecode.ConstPool;
/*   7:    */ 
/*   8:    */ public class AnnotationsWriter
/*   9:    */ {
/*  10:    */   private OutputStream output;
/*  11:    */   private ConstPool pool;
/*  12:    */   
/*  13:    */   public AnnotationsWriter(OutputStream os, ConstPool cp)
/*  14:    */   {
/*  15: 70 */     this.output = os;
/*  16: 71 */     this.pool = cp;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public ConstPool getConstPool()
/*  20:    */   {
/*  21: 78 */     return this.pool;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void close()
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 86 */     this.output.close();
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void numParameters(int num)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 96 */     this.output.write(num);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void numAnnotations(int num)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39:106 */     write16bit(num);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void annotation(String type, int numMemberValuePairs)
/*  43:    */     throws IOException
/*  44:    */   {
/*  45:121 */     annotation(this.pool.addUtf8Info(type), numMemberValuePairs);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void annotation(int typeIndex, int numMemberValuePairs)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51:136 */     write16bit(typeIndex);
/*  52:137 */     write16bit(numMemberValuePairs);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void memberValuePair(String memberName)
/*  56:    */     throws IOException
/*  57:    */   {
/*  58:150 */     memberValuePair(this.pool.addUtf8Info(memberName));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void memberValuePair(int memberNameIndex)
/*  62:    */     throws IOException
/*  63:    */   {
/*  64:164 */     write16bit(memberNameIndex);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void constValueIndex(boolean value)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:174 */     constValueIndex(90, this.pool.addIntegerInfo(value ? 1 : 0));
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void constValueIndex(byte value)
/*  74:    */     throws IOException
/*  75:    */   {
/*  76:184 */     constValueIndex(66, this.pool.addIntegerInfo(value));
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void constValueIndex(char value)
/*  80:    */     throws IOException
/*  81:    */   {
/*  82:194 */     constValueIndex(67, this.pool.addIntegerInfo(value));
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void constValueIndex(short value)
/*  86:    */     throws IOException
/*  87:    */   {
/*  88:204 */     constValueIndex(83, this.pool.addIntegerInfo(value));
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void constValueIndex(int value)
/*  92:    */     throws IOException
/*  93:    */   {
/*  94:214 */     constValueIndex(73, this.pool.addIntegerInfo(value));
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void constValueIndex(long value)
/*  98:    */     throws IOException
/*  99:    */   {
/* 100:224 */     constValueIndex(74, this.pool.addLongInfo(value));
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void constValueIndex(float value)
/* 104:    */     throws IOException
/* 105:    */   {
/* 106:234 */     constValueIndex(70, this.pool.addFloatInfo(value));
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void constValueIndex(double value)
/* 110:    */     throws IOException
/* 111:    */   {
/* 112:244 */     constValueIndex(68, this.pool.addDoubleInfo(value));
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void constValueIndex(String value)
/* 116:    */     throws IOException
/* 117:    */   {
/* 118:254 */     constValueIndex(115, this.pool.addUtf8Info(value));
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void constValueIndex(int tag, int index)
/* 122:    */     throws IOException
/* 123:    */   {
/* 124:268 */     this.output.write(tag);
/* 125:269 */     write16bit(index);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void enumConstValue(String typeName, String constName)
/* 129:    */     throws IOException
/* 130:    */   {
/* 131:282 */     enumConstValue(this.pool.addUtf8Info(typeName), this.pool.addUtf8Info(constName));
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void enumConstValue(int typeNameIndex, int constNameIndex)
/* 135:    */     throws IOException
/* 136:    */   {
/* 137:298 */     this.output.write(101);
/* 138:299 */     write16bit(typeNameIndex);
/* 139:300 */     write16bit(constNameIndex);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void classInfoIndex(String name)
/* 143:    */     throws IOException
/* 144:    */   {
/* 145:310 */     classInfoIndex(this.pool.addUtf8Info(name));
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void classInfoIndex(int index)
/* 149:    */     throws IOException
/* 150:    */   {
/* 151:320 */     this.output.write(99);
/* 152:321 */     write16bit(index);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void annotationValue()
/* 156:    */     throws IOException
/* 157:    */   {
/* 158:330 */     this.output.write(64);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void arrayValue(int numValues)
/* 162:    */     throws IOException
/* 163:    */   {
/* 164:344 */     this.output.write(91);
/* 165:345 */     write16bit(numValues);
/* 166:    */   }
/* 167:    */   
/* 168:    */   private void write16bit(int value)
/* 169:    */     throws IOException
/* 170:    */   {
/* 171:349 */     byte[] buf = new byte[2];
/* 172:350 */     ByteArray.write16bit(value, buf, 0);
/* 173:351 */     this.output.write(buf);
/* 174:    */   }
/* 175:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.AnnotationsWriter
 * JD-Core Version:    0.7.0.1
 */