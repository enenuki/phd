/*   1:    */ package org.apache.bcel.classfile;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.DataInputStream;
/*   5:    */ import java.io.DataOutputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ 
/*   9:    */ public final class Signature
/*  10:    */   extends Attribute
/*  11:    */ {
/*  12:    */   private int signature_index;
/*  13:    */   
/*  14:    */   public Signature(Signature c)
/*  15:    */   {
/*  16: 76 */     this(c.getNameIndex(), c.getLength(), c.getSignatureIndex(), c.getConstantPool());
/*  17:    */   }
/*  18:    */   
/*  19:    */   Signature(int name_index, int length, DataInputStream file, ConstantPool constant_pool)
/*  20:    */     throws IOException
/*  21:    */   {
/*  22: 90 */     this(name_index, length, file.readUnsignedShort(), constant_pool);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Signature(int name_index, int length, int signature_index, ConstantPool constant_pool)
/*  26:    */   {
/*  27:102 */     super((byte)10, name_index, length, constant_pool);
/*  28:103 */     this.signature_index = signature_index;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void accept(Visitor v)
/*  32:    */   {
/*  33:114 */     System.err.println("Visiting non-standard Signature object");
/*  34:    */   }
/*  35:    */   
/*  36:    */   public final void dump(DataOutputStream file)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39:125 */     super.dump(file);
/*  40:126 */     file.writeShort(this.signature_index);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public final int getSignatureIndex()
/*  44:    */   {
/*  45:132 */     return this.signature_index;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public final void setSignatureIndex(int signature_index)
/*  49:    */   {
/*  50:138 */     this.signature_index = signature_index;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public final String getSignature()
/*  54:    */   {
/*  55:145 */     ConstantUtf8 c = (ConstantUtf8)this.constant_pool.getConstant(this.signature_index, (byte)1);
/*  56:    */     
/*  57:147 */     return c.getBytes();
/*  58:    */   }
/*  59:    */   
/*  60:    */   private static final class MyByteArrayInputStream
/*  61:    */     extends ByteArrayInputStream
/*  62:    */   {
/*  63:    */     MyByteArrayInputStream(String data)
/*  64:    */     {
/*  65:154 */       super();
/*  66:    */     }
/*  67:    */     
/*  68:    */     final int mark()
/*  69:    */     {
/*  70:155 */       return this.pos;
/*  71:    */     }
/*  72:    */     
/*  73:    */     final String getData()
/*  74:    */     {
/*  75:156 */       return new String(this.buf);
/*  76:    */     }
/*  77:    */     
/*  78:    */     final void reset(int p)
/*  79:    */     {
/*  80:157 */       this.pos = p;
/*  81:    */     }
/*  82:    */     
/*  83:    */     final void unread()
/*  84:    */     {
/*  85:158 */       if (this.pos > 0) {
/*  86:158 */         this.pos -= 1;
/*  87:    */       }
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   private static boolean identStart(int ch)
/*  92:    */   {
/*  93:162 */     return (ch == 84) || (ch == 76);
/*  94:    */   }
/*  95:    */   
/*  96:    */   private static boolean identPart(int ch)
/*  97:    */   {
/*  98:166 */     return (ch == 47) || (ch == 59);
/*  99:    */   }
/* 100:    */   
/* 101:    */   private static final void matchIdent(MyByteArrayInputStream in, StringBuffer buf)
/* 102:    */   {
/* 103:172 */     if ((ch = in.read()) == -1) {
/* 104:173 */       throw new RuntimeException("Illegal signature: " + in.getData() + " no ident, reaching EOF");
/* 105:    */     }
/* 106:178 */     if (!identStart(ch))
/* 107:    */     {
/* 108:179 */       StringBuffer buf2 = new StringBuffer();
/* 109:    */       
/* 110:181 */       int count = 1;
/* 111:182 */       while (Character.isJavaIdentifierPart((char)ch))
/* 112:    */       {
/* 113:183 */         buf2.append((char)ch);
/* 114:184 */         count++;
/* 115:185 */         ch = in.read();
/* 116:    */       }
/* 117:188 */       if (ch == 58)
/* 118:    */       {
/* 119:189 */         in.skip("Ljava/lang/Object".length());
/* 120:190 */         buf.append(buf2);
/* 121:    */         
/* 122:192 */         ch = in.read();
/* 123:193 */         in.unread();
/* 124:    */       }
/* 125:    */       else
/* 126:    */       {
/* 127:196 */         for (int i = 0; i < count; i++) {
/* 128:197 */           in.unread();
/* 129:    */         }
/* 130:    */       }
/* 131:200 */       return;
/* 132:    */     }
/* 133:203 */     StringBuffer buf2 = new StringBuffer();
/* 134:204 */     int ch = in.read();
/* 135:    */     do
/* 136:    */     {
/* 137:207 */       buf2.append((char)ch);
/* 138:208 */       ch = in.read();
/* 139:211 */     } while ((ch != -1) && ((Character.isJavaIdentifierPart((char)ch)) || (ch == 47)));
/* 140:213 */     buf.append(buf2.toString().replace('/', '.'));
/* 141:217 */     if (ch != -1) {
/* 142:218 */       in.unread();
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   private static final void matchGJIdent(MyByteArrayInputStream in, StringBuffer buf)
/* 147:    */   {
/* 148:226 */     matchIdent(in, buf);
/* 149:    */     
/* 150:228 */     int ch = in.read();
/* 151:229 */     if ((ch == 60) || (ch == 40))
/* 152:    */     {
/* 153:231 */       buf.append((char)ch);
/* 154:232 */       matchGJIdent(in, buf);
/* 155:234 */       while (((ch = in.read()) != 62) && (ch != 41))
/* 156:    */       {
/* 157:235 */         if (ch == -1) {
/* 158:236 */           throw new RuntimeException("Illegal signature: " + in.getData() + " reaching EOF");
/* 159:    */         }
/* 160:240 */         buf.append(", ");
/* 161:241 */         in.unread();
/* 162:242 */         matchGJIdent(in, buf);
/* 163:    */       }
/* 164:247 */       buf.append((char)ch);
/* 165:    */     }
/* 166:    */     else
/* 167:    */     {
/* 168:249 */       in.unread();
/* 169:    */     }
/* 170:251 */     ch = in.read();
/* 171:252 */     if (identStart(ch))
/* 172:    */     {
/* 173:253 */       in.unread();
/* 174:254 */       matchGJIdent(in, buf);
/* 175:    */     }
/* 176:    */     else
/* 177:    */     {
/* 178:255 */       if (ch == 41)
/* 179:    */       {
/* 180:256 */         in.unread();
/* 181:257 */         return;
/* 182:    */       }
/* 183:258 */       if (ch != 59) {
/* 184:259 */         throw new RuntimeException("Illegal signature: " + in.getData() + " read " + (char)ch);
/* 185:    */       }
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public static String translate(String s)
/* 190:    */   {
/* 191:265 */     StringBuffer buf = new StringBuffer();
/* 192:    */     
/* 193:267 */     matchGJIdent(new MyByteArrayInputStream(s), buf);
/* 194:    */     
/* 195:269 */     return buf.toString();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public static final boolean isFormalParameterList(String s)
/* 199:    */   {
/* 200:273 */     return (s.startsWith("<")) && (s.indexOf(':') > 0);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static final boolean isActualParameterList(String s)
/* 204:    */   {
/* 205:277 */     return (s.startsWith("L")) && (s.endsWith(">;"));
/* 206:    */   }
/* 207:    */   
/* 208:    */   public final String toString()
/* 209:    */   {
/* 210:284 */     String s = getSignature();
/* 211:    */     
/* 212:286 */     return "Signature(" + s + ")";
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Attribute copy(ConstantPool constant_pool)
/* 216:    */   {
/* 217:293 */     return (Signature)clone();
/* 218:    */   }
/* 219:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.classfile.Signature
 * JD-Core Version:    0.7.0.1
 */