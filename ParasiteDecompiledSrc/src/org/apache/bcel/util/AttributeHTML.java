/*   1:    */ package org.apache.bcel.util;
/*   2:    */ 
/*   3:    */ import java.io.FileOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.PrintWriter;
/*   6:    */ import org.apache.bcel.Constants;
/*   7:    */ import org.apache.bcel.classfile.Attribute;
/*   8:    */ import org.apache.bcel.classfile.Code;
/*   9:    */ import org.apache.bcel.classfile.CodeException;
/*  10:    */ import org.apache.bcel.classfile.ConstantPool;
/*  11:    */ import org.apache.bcel.classfile.ConstantUtf8;
/*  12:    */ import org.apache.bcel.classfile.ConstantValue;
/*  13:    */ import org.apache.bcel.classfile.ExceptionTable;
/*  14:    */ import org.apache.bcel.classfile.InnerClass;
/*  15:    */ import org.apache.bcel.classfile.InnerClasses;
/*  16:    */ import org.apache.bcel.classfile.LineNumber;
/*  17:    */ import org.apache.bcel.classfile.LineNumberTable;
/*  18:    */ import org.apache.bcel.classfile.LocalVariable;
/*  19:    */ import org.apache.bcel.classfile.LocalVariableTable;
/*  20:    */ import org.apache.bcel.classfile.SourceFile;
/*  21:    */ import org.apache.bcel.classfile.Utility;
/*  22:    */ 
/*  23:    */ final class AttributeHTML
/*  24:    */   implements Constants
/*  25:    */ {
/*  26:    */   private String class_name;
/*  27:    */   private PrintWriter file;
/*  28: 70 */   private int attr_count = 0;
/*  29:    */   private ConstantHTML constant_html;
/*  30:    */   private ConstantPool constant_pool;
/*  31:    */   
/*  32:    */   AttributeHTML(String dir, String class_name, ConstantPool constant_pool, ConstantHTML constant_html)
/*  33:    */     throws IOException
/*  34:    */   {
/*  35: 77 */     this.class_name = class_name;
/*  36: 78 */     this.constant_pool = constant_pool;
/*  37: 79 */     this.constant_html = constant_html;
/*  38:    */     
/*  39: 81 */     this.file = new PrintWriter(new FileOutputStream(dir + class_name + "_attributes.html"));
/*  40: 82 */     this.file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
/*  41:    */   }
/*  42:    */   
/*  43:    */   private final String codeLink(int link, int method_number)
/*  44:    */   {
/*  45: 86 */     return "<A HREF=\"" + this.class_name + "_code.html#code" + method_number + "@" + link + "\" TARGET=Code>" + link + "</A>";
/*  46:    */   }
/*  47:    */   
/*  48:    */   final void close()
/*  49:    */   {
/*  50: 92 */     this.file.println("</TABLE></BODY></HTML>");
/*  51: 93 */     this.file.close();
/*  52:    */   }
/*  53:    */   
/*  54:    */   final void writeAttribute(Attribute attribute, String anchor)
/*  55:    */     throws IOException
/*  56:    */   {
/*  57: 97 */     writeAttribute(attribute, anchor, 0);
/*  58:    */   }
/*  59:    */   
/*  60:    */   final void writeAttribute(Attribute attribute, String anchor, int method_number)
/*  61:    */     throws IOException
/*  62:    */   {
/*  63:101 */     byte tag = attribute.getTag();
/*  64:104 */     if (tag == -1) {
/*  65:105 */       return;
/*  66:    */     }
/*  67:107 */     this.attr_count += 1;
/*  68:109 */     if (this.attr_count % 2 == 0) {
/*  69:110 */       this.file.print("<TR BGCOLOR=\"#C0C0C0\"><TD>");
/*  70:    */     } else {
/*  71:112 */       this.file.print("<TR BGCOLOR=\"#A0A0A0\"><TD>");
/*  72:    */     }
/*  73:114 */     this.file.println("<H4><A NAME=\"" + anchor + "\">" + this.attr_count + " " + Constants.ATTRIBUTE_NAMES[tag] + "</A></H4>");
/*  74:    */     int index;
/*  75:118 */     switch (tag)
/*  76:    */     {
/*  77:    */     case 2: 
/*  78:120 */       Code c = (Code)attribute;
/*  79:121 */       Attribute[] attributes = c.getAttributes();
/*  80:    */       
/*  81:    */ 
/*  82:124 */       this.file.print("<UL><LI>Maximum stack size = " + c.getMaxStack() + "</LI>\n<LI>Number of local variables = " + c.getMaxLocals() + "</LI>\n<LI><A HREF=\"" + this.class_name + "_code.html#method" + method_number + "\" TARGET=Code>Byte code</A></LI></UL>\n");
/*  83:    */       
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:130 */       CodeException[] ce = c.getExceptionTable();
/*  89:131 */       int len = ce.length;
/*  90:133 */       if (len > 0)
/*  91:    */       {
/*  92:134 */         this.file.print("<P><B>Exceptions handled</B><UL>");
/*  93:136 */         for (int i = 0; i < len; i++)
/*  94:    */         {
/*  95:137 */           int catch_type = ce[i].getCatchType();
/*  96:    */           
/*  97:139 */           this.file.print("<LI>");
/*  98:141 */           if (catch_type != 0) {
/*  99:142 */             this.file.print(this.constant_html.referenceConstant(catch_type));
/* 100:    */           } else {
/* 101:144 */             this.file.print("Any Exception");
/* 102:    */           }
/* 103:146 */           this.file.print("<BR>(Ranging from lines " + codeLink(ce[i].getStartPC(), method_number) + " to " + codeLink(ce[i].getEndPC(), method_number) + ", handled at line " + codeLink(ce[i].getHandlerPC(), method_number) + ")</LI>");
/* 104:    */         }
/* 105:150 */         this.file.print("</UL>");
/* 106:    */       }
/* 107:    */       break;
/* 108:    */     case 1: 
/* 109:155 */       index = ((ConstantValue)attribute).getConstantValueIndex();
/* 110:    */       
/* 111:    */ 
/* 112:158 */       this.file.print("<UL><LI><A HREF=\"" + this.class_name + "_cp.html#cp" + index + "\" TARGET=\"ConstantPool\">Constant value index(" + index + ")</A></UL>\n");
/* 113:    */       
/* 114:160 */       break;
/* 115:    */     case 0: 
/* 116:163 */       index = ((SourceFile)attribute).getSourceFileIndex();
/* 117:    */       
/* 118:    */ 
/* 119:166 */       this.file.print("<UL><LI><A HREF=\"" + this.class_name + "_cp.html#cp" + index + "\" TARGET=\"ConstantPool\">Source file index(" + index + ")</A></UL>\n");
/* 120:    */       
/* 121:168 */       break;
/* 122:    */     case 3: 
/* 123:172 */       int[] indices = ((ExceptionTable)attribute).getExceptionIndexTable();
/* 124:    */       
/* 125:174 */       this.file.print("<UL>");
/* 126:176 */       for (int i = 0; i < indices.length; i++) {
/* 127:177 */         this.file.print("<LI><A HREF=\"" + this.class_name + "_cp.html#cp" + indices[i] + "\" TARGET=\"ConstantPool\">Exception class index(" + indices[i] + ")</A>\n");
/* 128:    */       }
/* 129:180 */       this.file.print("</UL>\n");
/* 130:181 */       break;
/* 131:    */     case 4: 
/* 132:184 */       LineNumber[] line_numbers = ((LineNumberTable)attribute).getLineNumberTable();
/* 133:    */       
/* 134:    */ 
/* 135:187 */       this.file.print("<P>");
/* 136:189 */       for (int i = 0; i < line_numbers.length; i++)
/* 137:    */       {
/* 138:190 */         this.file.print("(" + line_numbers[i].getStartPC() + ",&nbsp;" + line_numbers[i].getLineNumber() + ")");
/* 139:192 */         if (i < line_numbers.length - 1) {
/* 140:193 */           this.file.print(", ");
/* 141:    */         }
/* 142:    */       }
/* 143:195 */       break;
/* 144:    */     case 5: 
/* 145:198 */       LocalVariable[] vars = ((LocalVariableTable)attribute).getLocalVariableTable();
/* 146:    */       
/* 147:    */ 
/* 148:201 */       this.file.print("<UL>");
/* 149:203 */       for (int i = 0; i < vars.length; i++)
/* 150:    */       {
/* 151:204 */         index = vars[i].getSignatureIndex();
/* 152:205 */         String signature = ((ConstantUtf8)this.constant_pool.getConstant(index, (byte)1)).getBytes();
/* 153:206 */         signature = Utility.signatureToString(signature, false);
/* 154:207 */         int start = vars[i].getStartPC();
/* 155:208 */         int end = start + vars[i].getLength();
/* 156:    */         
/* 157:210 */         this.file.println("<LI>" + Class2HTML.referenceType(signature) + "&nbsp;<B>" + vars[i].getName() + "</B> in slot %" + vars[i].getIndex() + "<BR>Valid from lines " + "<A HREF=\"" + this.class_name + "_code.html#code" + method_number + "@" + start + "\" TARGET=Code>" + start + "</A> to " + "<A HREF=\"" + this.class_name + "_code.html#code" + method_number + "@" + end + "\" TARGET=Code>" + end + "</A></LI>");
/* 158:    */       }
/* 159:218 */       this.file.print("</UL>\n");
/* 160:    */       
/* 161:220 */       break;
/* 162:    */     case 6: 
/* 163:223 */       InnerClass[] classes = ((InnerClasses)attribute).getInnerClasses();
/* 164:    */       
/* 165:    */ 
/* 166:226 */       this.file.print("<UL>");
/* 167:228 */       for (int i = 0; i < classes.length; i++)
/* 168:    */       {
/* 169:231 */         index = classes[i].getInnerNameIndex();
/* 170:    */         String name;
/* 171:232 */         if (index > 0) {
/* 172:233 */           name = ((ConstantUtf8)this.constant_pool.getConstant(index, (byte)1)).getBytes();
/* 173:    */         } else {
/* 174:235 */           name = "&lt;anonymous&gt;";
/* 175:    */         }
/* 176:237 */         String access = Utility.accessToString(classes[i].getInnerAccessFlags());
/* 177:    */         
/* 178:239 */         this.file.print("<LI><FONT COLOR=\"#FF0000\">" + access + "</FONT> " + this.constant_html.referenceConstant(classes[i].getInnerClassIndex()) + " in&nbsp;class " + this.constant_html.referenceConstant(classes[i].getOuterClassIndex()) + " named " + name + "</LI>\n");
/* 179:    */       }
/* 180:246 */       this.file.print("</UL>\n");
/* 181:247 */       break;
/* 182:    */     default: 
/* 183:250 */       this.file.print("<P>" + attribute.toString());
/* 184:    */     }
/* 185:253 */     this.file.println("</TD></TR>");
/* 186:254 */     this.file.flush();
/* 187:    */   }
/* 188:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.AttributeHTML
 * JD-Core Version:    0.7.0.1
 */