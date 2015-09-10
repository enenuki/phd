/*   1:    */ package org.apache.bcel.util;
/*   2:    */ 
/*   3:    */ import java.io.FileOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.PrintWriter;
/*   6:    */ import org.apache.bcel.Constants;
/*   7:    */ import org.apache.bcel.classfile.Constant;
/*   8:    */ import org.apache.bcel.classfile.ConstantCP;
/*   9:    */ import org.apache.bcel.classfile.ConstantClass;
/*  10:    */ import org.apache.bcel.classfile.ConstantFieldref;
/*  11:    */ import org.apache.bcel.classfile.ConstantInterfaceMethodref;
/*  12:    */ import org.apache.bcel.classfile.ConstantMethodref;
/*  13:    */ import org.apache.bcel.classfile.ConstantNameAndType;
/*  14:    */ import org.apache.bcel.classfile.ConstantPool;
/*  15:    */ import org.apache.bcel.classfile.ConstantString;
/*  16:    */ import org.apache.bcel.classfile.FieldOrMethod;
/*  17:    */ import org.apache.bcel.classfile.Method;
/*  18:    */ import org.apache.bcel.classfile.Utility;
/*  19:    */ 
/*  20:    */ final class ConstantHTML
/*  21:    */   implements Constants
/*  22:    */ {
/*  23:    */   private String class_name;
/*  24:    */   private String class_package;
/*  25:    */   private ConstantPool constant_pool;
/*  26:    */   private PrintWriter file;
/*  27:    */   private String[] constant_ref;
/*  28:    */   private Constant[] constants;
/*  29:    */   private Method[] methods;
/*  30:    */   
/*  31:    */   ConstantHTML(String dir, String class_name, String class_package, Method[] methods, ConstantPool constant_pool)
/*  32:    */     throws IOException
/*  33:    */   {
/*  34: 80 */     this.class_name = class_name;
/*  35: 81 */     this.class_package = class_package;
/*  36: 82 */     this.constant_pool = constant_pool;
/*  37: 83 */     this.methods = methods;
/*  38: 84 */     this.constants = constant_pool.getConstantPool();
/*  39: 85 */     this.file = new PrintWriter(new FileOutputStream(dir + class_name + "_cp.html"));
/*  40: 86 */     this.constant_ref = new String[this.constants.length];
/*  41: 87 */     this.constant_ref[0] = "&lt;unknown&gt;";
/*  42:    */     
/*  43: 89 */     this.file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
/*  44: 92 */     for (int i = 1; i < this.constants.length; i++)
/*  45:    */     {
/*  46: 93 */       if (i % 2 == 0) {
/*  47: 94 */         this.file.print("<TR BGCOLOR=\"#C0C0C0\"><TD>");
/*  48:    */       } else {
/*  49: 96 */         this.file.print("<TR BGCOLOR=\"#A0A0A0\"><TD>");
/*  50:    */       }
/*  51: 98 */       if (this.constants[i] != null) {
/*  52: 99 */         writeConstant(i);
/*  53:    */       }
/*  54:101 */       this.file.print("</TD></TR>\n");
/*  55:    */     }
/*  56:104 */     this.file.println("</TABLE></BODY></HTML>");
/*  57:105 */     this.file.close();
/*  58:    */   }
/*  59:    */   
/*  60:    */   String referenceConstant(int index)
/*  61:    */   {
/*  62:109 */     return this.constant_ref[index];
/*  63:    */   }
/*  64:    */   
/*  65:    */   private void writeConstant(int index)
/*  66:    */   {
/*  67:113 */     byte tag = this.constants[index].getTag();
/*  68:    */     
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:118 */     this.file.println("<H4> <A NAME=cp" + index + ">" + index + "</A> " + Constants.CONSTANT_NAMES[tag] + "</H4>");
/*  73:    */     int class_index;
/*  74:    */     int name_index;
/*  75:    */     String ref;
/*  76:122 */     switch (tag)
/*  77:    */     {
/*  78:    */     case 10: 
/*  79:    */     case 11: 
/*  80:126 */       if (tag == 10)
/*  81:    */       {
/*  82:127 */         ConstantMethodref c = (ConstantMethodref)this.constant_pool.getConstant(index, (byte)10);
/*  83:128 */         class_index = c.getClassIndex();
/*  84:129 */         name_index = c.getNameAndTypeIndex();
/*  85:    */       }
/*  86:    */       else
/*  87:    */       {
/*  88:132 */         ConstantInterfaceMethodref c1 = (ConstantInterfaceMethodref)this.constant_pool.getConstant(index, (byte)11);
/*  89:133 */         class_index = c1.getClassIndex();
/*  90:134 */         name_index = c1.getNameAndTypeIndex();
/*  91:    */       }
/*  92:138 */       String method_name = this.constant_pool.constantToString(name_index, (byte)12);
/*  93:139 */       String html_method_name = Class2HTML.toHTML(method_name);
/*  94:    */       
/*  95:    */ 
/*  96:142 */       String method_class = this.constant_pool.constantToString(class_index, (byte)7);
/*  97:143 */       String short_method_class = Utility.compactClassName(method_class);
/*  98:144 */       short_method_class = Utility.compactClassName(method_class);
/*  99:145 */       short_method_class = Utility.compactClassName(short_method_class, this.class_package + ".", true);
/* 100:    */       
/* 101:    */ 
/* 102:148 */       ConstantNameAndType c2 = (ConstantNameAndType)this.constant_pool.getConstant(name_index, (byte)12);
/* 103:149 */       String signature = this.constant_pool.constantToString(c2.getSignatureIndex(), (byte)1);
/* 104:    */       
/* 105:151 */       String[] args = Utility.methodSignatureArgumentTypes(signature, false);
/* 106:    */       
/* 107:    */ 
/* 108:154 */       String type = Utility.methodSignatureReturnType(signature, false);
/* 109:155 */       String ret_type = Class2HTML.referenceType(type);
/* 110:    */       
/* 111:157 */       StringBuffer buf = new StringBuffer("(");
/* 112:158 */       for (int i = 0; i < args.length; i++)
/* 113:    */       {
/* 114:159 */         buf.append(Class2HTML.referenceType(args[i]));
/* 115:160 */         if (i < args.length - 1) {
/* 116:161 */           buf.append(",&nbsp;");
/* 117:    */         }
/* 118:    */       }
/* 119:163 */       buf.append(")");
/* 120:    */       
/* 121:165 */       String arg_types = buf.toString();
/* 122:167 */       if (method_class.equals(this.class_name)) {
/* 123:168 */         ref = "<A HREF=\"" + this.class_name + "_code.html#method" + getMethodNumber(new StringBuffer().append(method_name).append(signature).toString()) + "\" TARGET=Code>" + html_method_name + "</A>";
/* 124:    */       } else {
/* 125:171 */         ref = "<A HREF=\"" + method_class + ".html" + "\" TARGET=_top>" + short_method_class + "</A>." + html_method_name;
/* 126:    */       }
/* 127:174 */       this.constant_ref[index] = (ret_type + "&nbsp;<A HREF=\"" + this.class_name + "_cp.html#cp" + class_index + "\" TARGET=Constants>" + short_method_class + "</A>.<A HREF=\"" + this.class_name + "_cp.html#cp" + index + "\" TARGET=ConstantPool>" + html_method_name + "</A>&nbsp;" + arg_types);
/* 128:    */       
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:179 */       this.file.println("<P><TT>" + ret_type + "&nbsp;" + ref + arg_types + "&nbsp;</TT>\n<UL>" + "<LI><A HREF=\"#cp" + class_index + "\">Class index(" + class_index + ")</A>\n" + "<LI><A HREF=\"#cp" + name_index + "\">NameAndType index(" + name_index + ")</A></UL>");
/* 133:    */       
/* 134:    */ 
/* 135:182 */       break;
/* 136:    */     case 9: 
/* 137:186 */       ConstantFieldref c3 = (ConstantFieldref)this.constant_pool.getConstant(index, (byte)9);
/* 138:187 */       class_index = c3.getClassIndex();
/* 139:188 */       name_index = c3.getNameAndTypeIndex();
/* 140:    */       
/* 141:    */ 
/* 142:191 */       String field_class = this.constant_pool.constantToString(class_index, (byte)7);
/* 143:192 */       String short_field_class = Utility.compactClassName(field_class);
/* 144:193 */       short_field_class = Utility.compactClassName(short_field_class, this.class_package + ".", true);
/* 145:    */       
/* 146:195 */       String field_name = this.constant_pool.constantToString(name_index, (byte)12);
/* 147:197 */       if (field_class.equals(this.class_name)) {
/* 148:198 */         ref = "<A HREF=\"" + field_class + "_methods.html#field" + field_name + "\" TARGET=Methods>" + field_name + "</A>";
/* 149:    */       } else {
/* 150:201 */         ref = "<A HREF=\"" + field_class + ".html\" TARGET=_top>" + short_field_class + "</A>." + field_name + "\n";
/* 151:    */       }
/* 152:204 */       this.constant_ref[index] = ("<A HREF=\"" + this.class_name + "_cp.html#cp" + class_index + "\" TARGET=Constants>" + short_field_class + "</A>.<A HREF=\"" + this.class_name + "_cp.html#cp" + index + "\" TARGET=ConstantPool>" + field_name + "</A>");
/* 153:    */       
/* 154:    */ 
/* 155:    */ 
/* 156:208 */       this.file.println("<P><TT>" + ref + "</TT><BR>\n" + "<UL>" + "<LI><A HREF=\"#cp" + class_index + "\">Class(" + class_index + ")</A><BR>\n" + "<LI><A HREF=\"#cp" + name_index + "\">NameAndType(" + name_index + ")</A></UL>");
/* 157:    */       
/* 158:    */ 
/* 159:211 */       break;
/* 160:    */     case 7: 
/* 161:214 */       ConstantClass c4 = (ConstantClass)this.constant_pool.getConstant(index, (byte)7);
/* 162:215 */       name_index = c4.getNameIndex();
/* 163:216 */       String class_name2 = this.constant_pool.constantToString(index, tag);
/* 164:217 */       String short_class_name = Utility.compactClassName(class_name2);
/* 165:218 */       short_class_name = Utility.compactClassName(short_class_name, this.class_package + ".", true);
/* 166:    */       
/* 167:220 */       ref = "<A HREF=\"" + class_name2 + ".html\" TARGET=_top>" + short_class_name + "</A>";
/* 168:221 */       this.constant_ref[index] = ("<A HREF=\"" + this.class_name + "_cp.html#cp" + index + "\" TARGET=ConstantPool>" + short_class_name + "</A>");
/* 169:    */       
/* 170:    */ 
/* 171:224 */       this.file.println("<P><TT>" + ref + "</TT><UL>" + "<LI><A HREF=\"#cp" + name_index + "\">Name index(" + name_index + ")</A></UL>\n");
/* 172:    */       
/* 173:226 */       break;
/* 174:    */     case 8: 
/* 175:229 */       ConstantString c5 = (ConstantString)this.constant_pool.getConstant(index, (byte)8);
/* 176:230 */       name_index = c5.getStringIndex();
/* 177:    */       
/* 178:232 */       String str = Class2HTML.toHTML(this.constant_pool.constantToString(index, tag));
/* 179:    */       
/* 180:234 */       this.file.println("<P><TT>" + str + "</TT><UL>" + "<LI><A HREF=\"#cp" + name_index + "\">Name index(" + name_index + ")</A></UL>\n");
/* 181:    */       
/* 182:236 */       break;
/* 183:    */     case 12: 
/* 184:239 */       ConstantNameAndType c6 = (ConstantNameAndType)this.constant_pool.getConstant(index, (byte)12);
/* 185:240 */       name_index = c6.getNameIndex();
/* 186:241 */       int signature_index = c6.getSignatureIndex();
/* 187:    */       
/* 188:243 */       this.file.println("<P><TT>" + Class2HTML.toHTML(this.constant_pool.constantToString(index, tag)) + "</TT><UL>" + "<LI><A HREF=\"#cp" + name_index + "\">Name index(" + name_index + ")</A>\n" + "<LI><A HREF=\"#cp" + signature_index + "\">Signature index(" + signature_index + ")</A></UL>\n");
/* 189:    */       
/* 190:    */ 
/* 191:    */ 
/* 192:247 */       break;
/* 193:    */     default: 
/* 194:250 */       this.file.println("<P><TT>" + Class2HTML.toHTML(this.constant_pool.constantToString(index, tag)) + "</TT>\n");
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   private final int getMethodNumber(String str)
/* 199:    */   {
/* 200:255 */     for (int i = 0; i < this.methods.length; i++)
/* 201:    */     {
/* 202:256 */       String cmp = this.methods[i].getName() + this.methods[i].getSignature();
/* 203:257 */       if (cmp.equals(str)) {
/* 204:258 */         return i;
/* 205:    */       }
/* 206:    */     }
/* 207:260 */     return -1;
/* 208:    */   }
/* 209:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.ConstantHTML
 * JD-Core Version:    0.7.0.1
 */