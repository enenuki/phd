/*   1:    */ package org.hibernate.hql.internal.ast.util;
/*   2:    */ 
/*   3:    */ import antlr.collections.AST;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.io.PrintWriter;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.hibernate.hql.internal.ast.tree.DisplayableNode;
/*  10:    */ import org.hibernate.internal.util.StringHelper;
/*  11:    */ 
/*  12:    */ public class ASTPrinter
/*  13:    */ {
/*  14:    */   private final Map tokenTypeNameCache;
/*  15:    */   private final boolean showClassNames;
/*  16:    */   
/*  17:    */   public ASTPrinter(Class tokenTypeConstants)
/*  18:    */   {
/*  19: 57 */     this(ASTUtil.generateTokenNameCache(tokenTypeConstants), true);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ASTPrinter(boolean showClassNames)
/*  23:    */   {
/*  24: 61 */     this((Map)null, showClassNames);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ASTPrinter(Class tokenTypeConstants, boolean showClassNames)
/*  28:    */   {
/*  29: 72 */     this(ASTUtil.generateTokenNameCache(tokenTypeConstants), showClassNames);
/*  30:    */   }
/*  31:    */   
/*  32:    */   private ASTPrinter(Map tokenTypeNameCache, boolean showClassNames)
/*  33:    */   {
/*  34: 76 */     this.tokenTypeNameCache = tokenTypeNameCache;
/*  35: 77 */     this.showClassNames = showClassNames;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean isShowClassNames()
/*  39:    */   {
/*  40: 86 */     return this.showClassNames;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String showAsString(AST ast, String header)
/*  44:    */   {
/*  45: 98 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  46: 99 */     PrintStream ps = new PrintStream(baos);
/*  47:100 */     ps.println(header);
/*  48:101 */     showAst(ast, ps);
/*  49:102 */     ps.flush();
/*  50:103 */     return new String(baos.toByteArray());
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void showAst(AST ast, PrintStream out)
/*  54:    */   {
/*  55:113 */     showAst(ast, new PrintWriter(out));
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void showAst(AST ast, PrintWriter pw)
/*  59:    */   {
/*  60:123 */     ArrayList parents = new ArrayList();
/*  61:124 */     showAst(parents, pw, ast);
/*  62:125 */     pw.flush();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getTokenTypeName(int type)
/*  66:    */   {
/*  67:136 */     Integer typeInteger = Integer.valueOf(type);
/*  68:137 */     String value = null;
/*  69:138 */     if (this.tokenTypeNameCache != null) {
/*  70:139 */       value = (String)this.tokenTypeNameCache.get(typeInteger);
/*  71:    */     }
/*  72:141 */     if (value == null) {
/*  73:142 */       value = typeInteger.toString();
/*  74:    */     }
/*  75:144 */     return value;
/*  76:    */   }
/*  77:    */   
/*  78:    */   private void showAst(ArrayList parents, PrintWriter pw, AST ast)
/*  79:    */   {
/*  80:148 */     if (ast == null)
/*  81:    */     {
/*  82:149 */       pw.println("AST is null!");
/*  83:150 */       return;
/*  84:    */     }
/*  85:153 */     for (int i = 0; i < parents.size(); i++)
/*  86:    */     {
/*  87:154 */       AST parent = (AST)parents.get(i);
/*  88:155 */       if (parent.getNextSibling() == null) {
/*  89:157 */         pw.print("   ");
/*  90:    */       } else {
/*  91:160 */         pw.print(" | ");
/*  92:    */       }
/*  93:    */     }
/*  94:164 */     if (ast.getNextSibling() == null) {
/*  95:165 */       pw.print(" \\-");
/*  96:    */     } else {
/*  97:168 */       pw.print(" +-");
/*  98:    */     }
/*  99:171 */     showNode(pw, ast);
/* 100:    */     
/* 101:173 */     ArrayList newParents = new ArrayList(parents);
/* 102:174 */     newParents.add(ast);
/* 103:175 */     for (AST child = ast.getFirstChild(); child != null; child = child.getNextSibling()) {
/* 104:176 */       showAst(newParents, pw, child);
/* 105:    */     }
/* 106:178 */     newParents.clear();
/* 107:    */   }
/* 108:    */   
/* 109:    */   private void showNode(PrintWriter pw, AST ast)
/* 110:    */   {
/* 111:182 */     String s = nodeToString(ast, isShowClassNames());
/* 112:183 */     pw.println(s);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String nodeToString(AST ast, boolean showClassName)
/* 116:    */   {
/* 117:187 */     if (ast == null) {
/* 118:188 */       return "{node:null}";
/* 119:    */     }
/* 120:190 */     StringBuffer buf = new StringBuffer();
/* 121:191 */     buf.append("[").append(getTokenTypeName(ast.getType())).append("] ");
/* 122:192 */     if (showClassName) {
/* 123:193 */       buf.append(StringHelper.unqualify(ast.getClass().getName())).append(": ");
/* 124:    */     }
/* 125:196 */     buf.append("'");
/* 126:197 */     String text = ast.getText();
/* 127:198 */     if (text == null) {
/* 128:199 */       text = "{text:null}";
/* 129:    */     }
/* 130:201 */     appendEscapedMultibyteChars(text, buf);
/* 131:202 */     buf.append("'");
/* 132:203 */     if ((ast instanceof DisplayableNode))
/* 133:    */     {
/* 134:204 */       DisplayableNode displayableNode = (DisplayableNode)ast;
/* 135:    */       
/* 136:206 */       buf.append(" ").append(displayableNode.getDisplayText());
/* 137:    */     }
/* 138:208 */     return buf.toString();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static void appendEscapedMultibyteChars(String text, StringBuffer buf)
/* 142:    */   {
/* 143:212 */     char[] chars = text.toCharArray();
/* 144:213 */     for (int i = 0; i < chars.length; i++)
/* 145:    */     {
/* 146:214 */       char aChar = chars[i];
/* 147:215 */       if (aChar > 'Ā')
/* 148:    */       {
/* 149:216 */         buf.append("\\u");
/* 150:217 */         buf.append(Integer.toHexString(aChar));
/* 151:    */       }
/* 152:    */       else
/* 153:    */       {
/* 154:220 */         buf.append(aChar);
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static String escapeMultibyteChars(String text)
/* 160:    */   {
/* 161:225 */     StringBuffer buf = new StringBuffer();
/* 162:226 */     appendEscapedMultibyteChars(text, buf);
/* 163:227 */     return buf.toString();
/* 164:    */   }
/* 165:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.util.ASTPrinter
 * JD-Core Version:    0.7.0.1
 */