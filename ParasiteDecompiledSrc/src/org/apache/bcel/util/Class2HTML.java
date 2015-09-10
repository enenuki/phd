/*   1:    */ package org.apache.bcel.util;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.io.PrintWriter;
/*   8:    */ import org.apache.bcel.Constants;
/*   9:    */ import org.apache.bcel.classfile.Attribute;
/*  10:    */ import org.apache.bcel.classfile.ClassParser;
/*  11:    */ import org.apache.bcel.classfile.ConstantPool;
/*  12:    */ import org.apache.bcel.classfile.JavaClass;
/*  13:    */ import org.apache.bcel.classfile.Method;
/*  14:    */ import org.apache.bcel.classfile.Utility;
/*  15:    */ 
/*  16:    */ public class Class2HTML
/*  17:    */   implements Constants
/*  18:    */ {
/*  19:    */   private JavaClass java_class;
/*  20:    */   private String dir;
/*  21:    */   private static String class_package;
/*  22:    */   private static String class_name;
/*  23:    */   private static ConstantPool constant_pool;
/*  24:    */   
/*  25:    */   public Class2HTML(JavaClass java_class, String dir)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28:100 */     Method[] methods = java_class.getMethods();
/*  29:    */     
/*  30:102 */     this.java_class = java_class;
/*  31:103 */     this.dir = dir;
/*  32:104 */     class_name = java_class.getClassName();
/*  33:105 */     constant_pool = java_class.getConstantPool();
/*  34:    */     
/*  35:    */ 
/*  36:108 */     int index = class_name.lastIndexOf('.');
/*  37:109 */     if (index > -1) {
/*  38:110 */       class_package = class_name.substring(0, index);
/*  39:    */     } else {
/*  40:112 */       class_package = "";
/*  41:    */     }
/*  42:114 */     ConstantHTML constant_html = new ConstantHTML(dir, class_name, class_package, methods, constant_pool);
/*  43:    */     
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:120 */     AttributeHTML attribute_html = new AttributeHTML(dir, class_name, constant_pool, constant_html);
/*  49:    */     
/*  50:122 */     MethodHTML method_html = new MethodHTML(dir, class_name, methods, java_class.getFields(), constant_html, attribute_html);
/*  51:    */     
/*  52:    */ 
/*  53:125 */     writeMainHTML(attribute_html);
/*  54:126 */     new CodeHTML(dir, class_name, methods, constant_pool, constant_html);
/*  55:127 */     attribute_html.close();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static void main(String[] argv)
/*  59:    */   {
/*  60:132 */     String[] file_name = new String[argv.length];
/*  61:133 */     int files = 0;
/*  62:134 */     ClassParser parser = null;
/*  63:135 */     JavaClass java_class = null;
/*  64:136 */     String zip_file = null;
/*  65:137 */     char sep = System.getProperty("file.separator").toCharArray()[0];
/*  66:138 */     String dir = "." + sep;
/*  67:    */     try
/*  68:    */     {
/*  69:143 */       for (int i = 0; i < argv.length; i++) {
/*  70:144 */         if (argv[i].charAt(0) == '-')
/*  71:    */         {
/*  72:145 */           if (argv[i].equals("-d"))
/*  73:    */           {
/*  74:146 */             dir = argv[(++i)];
/*  75:148 */             if (!dir.endsWith("" + sep)) {
/*  76:149 */               dir = dir + sep;
/*  77:    */             }
/*  78:151 */             new File(dir).mkdirs();
/*  79:    */           }
/*  80:153 */           else if (argv[i].equals("-zip"))
/*  81:    */           {
/*  82:154 */             zip_file = argv[(++i)];
/*  83:    */           }
/*  84:    */           else
/*  85:    */           {
/*  86:156 */             System.out.println("Unknown option " + argv[i]);
/*  87:    */           }
/*  88:    */         }
/*  89:    */         else {
/*  90:159 */           file_name[(files++)] = argv[i];
/*  91:    */         }
/*  92:    */       }
/*  93:162 */       if (files == 0) {
/*  94:163 */         System.err.println("Class2HTML: No input files specified.");
/*  95:    */       } else {
/*  96:165 */         for (int i = 0; i < files; i++)
/*  97:    */         {
/*  98:166 */           System.out.print("Processing " + file_name[i] + "...");
/*  99:167 */           if (zip_file == null) {
/* 100:168 */             parser = new ClassParser(file_name[i]);
/* 101:    */           } else {
/* 102:170 */             parser = new ClassParser(zip_file, file_name[i]);
/* 103:    */           }
/* 104:172 */           java_class = parser.parse();
/* 105:173 */           new Class2HTML(java_class, dir);
/* 106:174 */           System.out.println("Done.");
/* 107:    */         }
/* 108:    */       }
/* 109:    */     }
/* 110:    */     catch (Exception e)
/* 111:    */     {
/* 112:178 */       System.out.println(e);
/* 113:179 */       e.printStackTrace(System.out);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   static String referenceClass(int index)
/* 118:    */   {
/* 119:188 */     String str = constant_pool.getConstantString(index, (byte)7);
/* 120:189 */     str = Utility.compactClassName(str);
/* 121:190 */     str = Utility.compactClassName(str, class_package + ".", true);
/* 122:    */     
/* 123:192 */     return "<A HREF=\"" + class_name + "_cp.html#cp" + index + "\" TARGET=ConstantPool>" + str + "</A>";
/* 124:    */   }
/* 125:    */   
/* 126:    */   static final String referenceType(String type)
/* 127:    */   {
/* 128:197 */     String short_type = Utility.compactClassName(type);
/* 129:198 */     short_type = Utility.compactClassName(short_type, class_package + ".", true);
/* 130:    */     
/* 131:200 */     int index = type.indexOf('[');
/* 132:201 */     if (index > -1) {
/* 133:202 */       type = type.substring(0, index);
/* 134:    */     }
/* 135:205 */     if ((type.equals("int")) || (type.equals("short")) || (type.equals("boolean")) || (type.equals("void")) || (type.equals("char")) || (type.equals("byte")) || (type.equals("long")) || (type.equals("double")) || (type.equals("float"))) {
/* 136:208 */       return "<FONT COLOR=\"#00FF00\">" + type + "</FONT>";
/* 137:    */     }
/* 138:210 */     return "<A HREF=\"" + type + ".html\" TARGET=_top>" + short_type + "</A>";
/* 139:    */   }
/* 140:    */   
/* 141:    */   static String toHTML(String str)
/* 142:    */   {
/* 143:214 */     StringBuffer buf = new StringBuffer();
/* 144:    */     try
/* 145:    */     {
/* 146:217 */       for (int i = 0; i < str.length(); i++)
/* 147:    */       {
/* 148:    */         char ch;
/* 149:220 */         switch (ch = str.charAt(i))
/* 150:    */         {
/* 151:    */         case '<': 
/* 152:221 */           buf.append("&lt;"); break;
/* 153:    */         case '>': 
/* 154:222 */           buf.append("&gt;"); break;
/* 155:    */         case '\n': 
/* 156:223 */           buf.append("\\n"); break;
/* 157:    */         case '\r': 
/* 158:224 */           buf.append("\\r"); break;
/* 159:    */         default: 
/* 160:225 */           buf.append(ch);
/* 161:    */         }
/* 162:    */       }
/* 163:    */     }
/* 164:    */     catch (StringIndexOutOfBoundsException e) {}
/* 165:230 */     return buf.toString();
/* 166:    */   }
/* 167:    */   
/* 168:    */   private void writeMainHTML(AttributeHTML attribute_html)
/* 169:    */     throws IOException
/* 170:    */   {
/* 171:234 */     PrintWriter file = new PrintWriter(new FileOutputStream(this.dir + class_name + ".html"));
/* 172:235 */     Attribute[] attributes = this.java_class.getAttributes();
/* 173:    */     
/* 174:237 */     file.println("<HTML>\n<HEAD><TITLE>Documentation for " + class_name + "</TITLE>" + "</HEAD>\n" + "<FRAMESET BORDER=1 cols=\"30%,*\">\n" + "<FRAMESET BORDER=1 rows=\"80%,*\">\n" + "<FRAME NAME=\"ConstantPool\" SRC=\"" + class_name + "_cp.html" + "\"\n MARGINWIDTH=\"0\" " + "MARGINHEIGHT=\"0\" FRAMEBORDER=\"1\" SCROLLING=\"AUTO\">\n" + "<FRAME NAME=\"Attributes\" SRC=\"" + class_name + "_attributes.html" + "\"\n MARGINWIDTH=\"0\" " + "MARGINHEIGHT=\"0\" FRAMEBORDER=\"1\" SCROLLING=\"AUTO\">\n" + "</FRAMESET>\n" + "<FRAMESET BORDER=1 rows=\"80%,*\">\n" + "<FRAME NAME=\"Code\" SRC=\"" + class_name + "_code.html\"\n MARGINWIDTH=0 " + "MARGINHEIGHT=0 FRAMEBORDER=1 SCROLLING=\"AUTO\">\n" + "<FRAME NAME=\"Methods\" SRC=\"" + class_name + "_methods.html\"\n MARGINWIDTH=0 " + "MARGINHEIGHT=0 FRAMEBORDER=1 SCROLLING=\"AUTO\">\n" + "</FRAMESET></FRAMESET></HTML>");
/* 175:    */     
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:    */ 
/* 181:    */ 
/* 182:    */ 
/* 183:    */ 
/* 184:    */ 
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:257 */     file.close();
/* 195:259 */     for (int i = 0; i < attributes.length; i++) {
/* 196:260 */       attribute_html.writeAttribute(attributes[i], "class" + i);
/* 197:    */     }
/* 198:    */   }
/* 199:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.util.Class2HTML
 * JD-Core Version:    0.7.0.1
 */