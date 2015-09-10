/*   1:    */ package org.apache.bcel.util;
/*   2:    */ 
/*   3:    */ import java.io.FileOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.PrintWriter;
/*   6:    */ import org.apache.bcel.Constants;
/*   7:    */ import org.apache.bcel.classfile.AccessFlags;
/*   8:    */ import org.apache.bcel.classfile.Attribute;
/*   9:    */ import org.apache.bcel.classfile.Code;
/*  10:    */ import org.apache.bcel.classfile.ConstantValue;
/*  11:    */ import org.apache.bcel.classfile.ExceptionTable;
/*  12:    */ import org.apache.bcel.classfile.Field;
/*  13:    */ import org.apache.bcel.classfile.FieldOrMethod;
/*  14:    */ import org.apache.bcel.classfile.Method;
/*  15:    */ import org.apache.bcel.classfile.Utility;
/*  16:    */ 
/*  17:    */ final class MethodHTML
/*  18:    */   implements Constants
/*  19:    */ {
/*  20:    */   private String class_name;
/*  21:    */   private PrintWriter file;
/*  22:    */   private ConstantHTML constant_html;
/*  23:    */   private AttributeHTML attribute_html;
/*  24:    */   
/*  25:    */   MethodHTML(String dir, String class_name, Method[] methods, Field[] fields, ConstantHTML constant_html, AttributeHTML attribute_html)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28: 77 */     this.class_name = class_name;
/*  29: 78 */     this.attribute_html = attribute_html;
/*  30: 79 */     this.constant_html = constant_html;
/*  31:    */     
/*  32: 81 */     this.file = new PrintWriter(new FileOutputStream(dir + class_name + "_methods.html"));
/*  33:    */     
/*  34: 83 */     this.file.println("<HTML><BODY BGCOLOR=\"#C0C0C0\"><TABLE BORDER=0>");
/*  35: 84 */     this.file.println("<TR><TH ALIGN=LEFT>Access&nbsp;flags</TH><TH ALIGN=LEFT>Type</TH><TH ALIGN=LEFT>Field&nbsp;name</TH></TR>");
/*  36: 86 */     for (int i = 0; i < fields.length; i++) {
/*  37: 87 */       writeField(fields[i]);
/*  38:    */     }
/*  39: 88 */     this.file.println("</TABLE>");
/*  40:    */     
/*  41: 90 */     this.file.println("<TABLE BORDER=0><TR><TH ALIGN=LEFT>Access&nbsp;flags</TH><TH ALIGN=LEFT>Return&nbsp;type</TH><TH ALIGN=LEFT>Method&nbsp;name</TH><TH ALIGN=LEFT>Arguments</TH></TR>");
/*  42: 93 */     for (int i = 0; i < methods.length; i++) {
/*  43: 94 */       writeMethod(methods[i], i);
/*  44:    */     }
/*  45: 96 */     this.file.println("</TABLE></BODY></HTML>");
/*  46: 97 */     this.file.close();
/*  47:    */   }
/*  48:    */   
/*  49:    */   private void writeField(Field field)
/*  50:    */     throws IOException
/*  51:    */   {
/*  52:107 */     String type = Utility.signatureToString(field.getSignature());
/*  53:108 */     String name = field.getName();
/*  54:109 */     String access = Utility.accessToString(field.getAccessFlags());
/*  55:    */     
/*  56:    */ 
/*  57:112 */     access = Utility.replace(access, " ", "&nbsp;");
/*  58:    */     
/*  59:114 */     this.file.print("<TR><TD><FONT COLOR=\"#FF0000\">" + access + "</FONT></TD>\n<TD>" + Class2HTML.referenceType(type) + "</TD><TD><A NAME=\"field" + name + "\">" + name + "</A></TD>");
/*  60:    */     
/*  61:    */ 
/*  62:    */ 
/*  63:118 */     Attribute[] attributes = field.getAttributes();
/*  64:121 */     for (int i = 0; i < attributes.length; i++) {
/*  65:122 */       this.attribute_html.writeAttribute(attributes[i], name + "@" + i);
/*  66:    */     }
/*  67:124 */     for (int i = 0; i < attributes.length; i++) {
/*  68:125 */       if (attributes[i].getTag() == 1)
/*  69:    */       {
/*  70:126 */         String str = ((ConstantValue)attributes[i]).toString();
/*  71:    */         
/*  72:    */ 
/*  73:129 */         this.file.print("<TD>= <A HREF=\"" + this.class_name + "_attributes.html#" + name + "@" + i + "\" TARGET=\"Attributes\">" + str + "</TD>\n");
/*  74:    */         
/*  75:131 */         break;
/*  76:    */       }
/*  77:    */     }
/*  78:135 */     this.file.println("</TR>");
/*  79:    */   }
/*  80:    */   
/*  81:    */   private final void writeMethod(Method method, int method_number)
/*  82:    */     throws IOException
/*  83:    */   {
/*  84:140 */     String signature = method.getSignature();
/*  85:    */     
/*  86:142 */     String[] args = Utility.methodSignatureArgumentTypes(signature, false);
/*  87:    */     
/*  88:144 */     String type = Utility.methodSignatureReturnType(signature, false);
/*  89:    */     
/*  90:146 */     String name = method.getName();
/*  91:    */     
/*  92:148 */     String access = Utility.accessToString(method.getAccessFlags());
/*  93:    */     
/*  94:150 */     Attribute[] attributes = method.getAttributes();
/*  95:    */     
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:155 */     access = Utility.replace(access, " ", "&nbsp;");
/* 100:156 */     String html_name = Class2HTML.toHTML(name);
/* 101:    */     
/* 102:158 */     this.file.print("<TR VALIGN=TOP><TD><FONT COLOR=\"#FF0000\"><A NAME=method" + method_number + ">" + access + "</A></FONT></TD>");
/* 103:    */     
/* 104:    */ 
/* 105:161 */     this.file.print("<TD>" + Class2HTML.referenceType(type) + "</TD><TD>" + "<A HREF=" + this.class_name + "_code.html#method" + method_number + " TARGET=Code>" + html_name + "</A></TD>\n<TD>(");
/* 106:165 */     for (int i = 0; i < args.length; i++)
/* 107:    */     {
/* 108:166 */       this.file.print(Class2HTML.referenceType(args[i]));
/* 109:167 */       if (i < args.length - 1) {
/* 110:168 */         this.file.print(", ");
/* 111:    */       }
/* 112:    */     }
/* 113:171 */     this.file.print(")</TD></TR>");
/* 114:174 */     for (int i = 0; i < attributes.length; i++)
/* 115:    */     {
/* 116:175 */       this.attribute_html.writeAttribute(attributes[i], "method" + method_number + "@" + i, method_number);
/* 117:    */       
/* 118:    */ 
/* 119:178 */       byte tag = attributes[i].getTag();
/* 120:179 */       if (tag == 3)
/* 121:    */       {
/* 122:180 */         this.file.print("<TR VALIGN=TOP><TD COLSPAN=2></TD><TH ALIGN=LEFT>throws</TH><TD>");
/* 123:181 */         int[] exceptions = ((ExceptionTable)attributes[i]).getExceptionIndexTable();
/* 124:183 */         for (int j = 0; j < exceptions.length; j++)
/* 125:    */         {
/* 126:184 */           this.file.print(this.constant_html.referenceConstant(exceptions[j]));
/* 127:186 */           if (j < exceptions.length - 1) {
/* 128:187 */             this.file.print(", ");
/* 129:    */           }
/* 130:    */         }
/* 131:189 */         this.file.println("</TD></TR>");
/* 132:    */       }
/* 133:190 */       else if (tag == 2)
/* 134:    */       {
/* 135:191 */         Attribute[] c_a = ((Code)attributes[i]).getAttributes();
/* 136:193 */         for (int j = 0; j < c_a.length; j++) {
/* 137:194 */           this.attribute_html.writeAttribute(c_a[j], "method" + method_number + "@" + i + "@" + j, method_number);
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:    */   }
/* 142:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.MethodHTML
 * JD-Core Version:    0.7.0.1
 */