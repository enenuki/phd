/*   1:    */ package org.apache.bcel.verifier.statics;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.classfile.Code;
/*   4:    */ import org.apache.bcel.classfile.CodeException;
/*   5:    */ import org.apache.bcel.classfile.ConstantClass;
/*   6:    */ import org.apache.bcel.classfile.ConstantDouble;
/*   7:    */ import org.apache.bcel.classfile.ConstantFieldref;
/*   8:    */ import org.apache.bcel.classfile.ConstantFloat;
/*   9:    */ import org.apache.bcel.classfile.ConstantInteger;
/*  10:    */ import org.apache.bcel.classfile.ConstantInterfaceMethodref;
/*  11:    */ import org.apache.bcel.classfile.ConstantLong;
/*  12:    */ import org.apache.bcel.classfile.ConstantMethodref;
/*  13:    */ import org.apache.bcel.classfile.ConstantNameAndType;
/*  14:    */ import org.apache.bcel.classfile.ConstantPool;
/*  15:    */ import org.apache.bcel.classfile.ConstantString;
/*  16:    */ import org.apache.bcel.classfile.ConstantUtf8;
/*  17:    */ import org.apache.bcel.classfile.ConstantValue;
/*  18:    */ import org.apache.bcel.classfile.Deprecated;
/*  19:    */ import org.apache.bcel.classfile.EmptyVisitor;
/*  20:    */ import org.apache.bcel.classfile.ExceptionTable;
/*  21:    */ import org.apache.bcel.classfile.Field;
/*  22:    */ import org.apache.bcel.classfile.InnerClass;
/*  23:    */ import org.apache.bcel.classfile.InnerClasses;
/*  24:    */ import org.apache.bcel.classfile.JavaClass;
/*  25:    */ import org.apache.bcel.classfile.LineNumber;
/*  26:    */ import org.apache.bcel.classfile.LineNumberTable;
/*  27:    */ import org.apache.bcel.classfile.LocalVariable;
/*  28:    */ import org.apache.bcel.classfile.LocalVariableTable;
/*  29:    */ import org.apache.bcel.classfile.Method;
/*  30:    */ import org.apache.bcel.classfile.Node;
/*  31:    */ import org.apache.bcel.classfile.SourceFile;
/*  32:    */ import org.apache.bcel.classfile.Synthetic;
/*  33:    */ import org.apache.bcel.classfile.Unknown;
/*  34:    */ import org.apache.bcel.classfile.Visitor;
/*  35:    */ 
/*  36:    */ public class StringRepresentation
/*  37:    */   extends EmptyVisitor
/*  38:    */   implements Visitor
/*  39:    */ {
/*  40:    */   private String tostring;
/*  41:    */   
/*  42:    */   public StringRepresentation(Node n)
/*  43:    */   {
/*  44: 83 */     n.accept(this);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String toString()
/*  48:    */   {
/*  49: 89 */     return this.tostring;
/*  50:    */   }
/*  51:    */   
/*  52:    */   private String toString(Node obj)
/*  53:    */   {
/*  54:    */     String ret;
/*  55:    */     try
/*  56:    */     {
/*  57: 99 */       ret = obj.toString();
/*  58:    */     }
/*  59:    */     catch (RuntimeException e)
/*  60:    */     {
/*  61:102 */       String s = obj.getClass().getName();
/*  62:103 */       s = s.substring(s.lastIndexOf(".") + 1);
/*  63:104 */       ret = "<<" + s + ">>";
/*  64:    */     }
/*  65:106 */     return ret;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void visitCode(Code obj)
/*  69:    */   {
/*  70:116 */     this.tostring = "<CODE>";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void visitCodeException(CodeException obj)
/*  74:    */   {
/*  75:119 */     this.tostring = toString(obj);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void visitConstantClass(ConstantClass obj)
/*  79:    */   {
/*  80:122 */     this.tostring = toString(obj);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void visitConstantDouble(ConstantDouble obj)
/*  84:    */   {
/*  85:125 */     this.tostring = toString(obj);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void visitConstantFieldref(ConstantFieldref obj)
/*  89:    */   {
/*  90:128 */     this.tostring = toString(obj);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void visitConstantFloat(ConstantFloat obj)
/*  94:    */   {
/*  95:131 */     this.tostring = toString(obj);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void visitConstantInteger(ConstantInteger obj)
/*  99:    */   {
/* 100:134 */     this.tostring = toString(obj);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void visitConstantInterfaceMethodref(ConstantInterfaceMethodref obj)
/* 104:    */   {
/* 105:137 */     this.tostring = toString(obj);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void visitConstantLong(ConstantLong obj)
/* 109:    */   {
/* 110:140 */     this.tostring = toString(obj);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void visitConstantMethodref(ConstantMethodref obj)
/* 114:    */   {
/* 115:143 */     this.tostring = toString(obj);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void visitConstantNameAndType(ConstantNameAndType obj)
/* 119:    */   {
/* 120:146 */     this.tostring = toString(obj);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void visitConstantPool(ConstantPool obj)
/* 124:    */   {
/* 125:149 */     this.tostring = toString(obj);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void visitConstantString(ConstantString obj)
/* 129:    */   {
/* 130:152 */     this.tostring = toString(obj);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void visitConstantUtf8(ConstantUtf8 obj)
/* 134:    */   {
/* 135:155 */     this.tostring = toString(obj);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void visitConstantValue(ConstantValue obj)
/* 139:    */   {
/* 140:158 */     this.tostring = toString(obj);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void visitDeprecated(Deprecated obj)
/* 144:    */   {
/* 145:161 */     this.tostring = toString(obj);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void visitExceptionTable(ExceptionTable obj)
/* 149:    */   {
/* 150:164 */     this.tostring = toString(obj);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void visitField(Field obj)
/* 154:    */   {
/* 155:167 */     this.tostring = toString(obj);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void visitInnerClass(InnerClass obj)
/* 159:    */   {
/* 160:170 */     this.tostring = toString(obj);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void visitInnerClasses(InnerClasses obj)
/* 164:    */   {
/* 165:173 */     this.tostring = toString(obj);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void visitJavaClass(JavaClass obj)
/* 169:    */   {
/* 170:176 */     this.tostring = toString(obj);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void visitLineNumber(LineNumber obj)
/* 174:    */   {
/* 175:179 */     this.tostring = toString(obj);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void visitLineNumberTable(LineNumberTable obj)
/* 179:    */   {
/* 180:182 */     this.tostring = ("<LineNumberTable: " + toString(obj) + ">");
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void visitLocalVariable(LocalVariable obj)
/* 184:    */   {
/* 185:185 */     this.tostring = toString(obj);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void visitLocalVariableTable(LocalVariableTable obj)
/* 189:    */   {
/* 190:188 */     this.tostring = ("<LocalVariableTable: " + toString(obj) + ">");
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void visitMethod(Method obj)
/* 194:    */   {
/* 195:191 */     this.tostring = toString(obj);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void visitSourceFile(SourceFile obj)
/* 199:    */   {
/* 200:194 */     this.tostring = toString(obj);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void visitSynthetic(Synthetic obj)
/* 204:    */   {
/* 205:197 */     this.tostring = toString(obj);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void visitUnknown(Unknown obj)
/* 209:    */   {
/* 210:200 */     this.tostring = toString(obj);
/* 211:    */   }
/* 212:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.statics.StringRepresentation
 * JD-Core Version:    0.7.0.1
 */