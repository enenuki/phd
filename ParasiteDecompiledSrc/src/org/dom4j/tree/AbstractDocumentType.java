/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.Writer;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.dom4j.DocumentType;
/*   8:    */ import org.dom4j.Element;
/*   9:    */ import org.dom4j.Visitor;
/*  10:    */ 
/*  11:    */ public abstract class AbstractDocumentType
/*  12:    */   extends AbstractNode
/*  13:    */   implements DocumentType
/*  14:    */ {
/*  15:    */   public short getNodeType()
/*  16:    */   {
/*  17: 34 */     return 10;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public String getName()
/*  21:    */   {
/*  22: 38 */     return getElementName();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void setName(String name)
/*  26:    */   {
/*  27: 42 */     setElementName(name);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getPath(Element context)
/*  31:    */   {
/*  32: 47 */     return "";
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getUniquePath(Element context)
/*  36:    */   {
/*  37: 52 */     return "";
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getText()
/*  41:    */   {
/*  42: 62 */     List list = getInternalDeclarations();
/*  43: 64 */     if ((list != null) && (list.size() > 0))
/*  44:    */     {
/*  45: 65 */       StringBuffer buffer = new StringBuffer();
/*  46: 66 */       Iterator iter = list.iterator();
/*  47: 68 */       if (iter.hasNext())
/*  48:    */       {
/*  49: 69 */         Object decl = iter.next();
/*  50: 70 */         buffer.append(decl.toString());
/*  51: 72 */         while (iter.hasNext())
/*  52:    */         {
/*  53: 73 */           decl = iter.next();
/*  54: 74 */           buffer.append("\n");
/*  55: 75 */           buffer.append(decl.toString());
/*  56:    */         }
/*  57:    */       }
/*  58: 79 */       return buffer.toString();
/*  59:    */     }
/*  60: 82 */     return "";
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String toString()
/*  64:    */   {
/*  65: 86 */     return super.toString() + " [DocumentType: " + asXML() + "]";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String asXML()
/*  69:    */   {
/*  70: 90 */     StringBuffer buffer = new StringBuffer("<!DOCTYPE ");
/*  71: 91 */     buffer.append(getElementName());
/*  72:    */     
/*  73: 93 */     boolean hasPublicID = false;
/*  74: 94 */     String publicID = getPublicID();
/*  75: 96 */     if ((publicID != null) && (publicID.length() > 0))
/*  76:    */     {
/*  77: 97 */       buffer.append(" PUBLIC \"");
/*  78: 98 */       buffer.append(publicID);
/*  79: 99 */       buffer.append("\"");
/*  80:100 */       hasPublicID = true;
/*  81:    */     }
/*  82:103 */     String systemID = getSystemID();
/*  83:105 */     if ((systemID != null) && (systemID.length() > 0))
/*  84:    */     {
/*  85:106 */       if (!hasPublicID) {
/*  86:107 */         buffer.append(" SYSTEM");
/*  87:    */       }
/*  88:110 */       buffer.append(" \"");
/*  89:111 */       buffer.append(systemID);
/*  90:112 */       buffer.append("\"");
/*  91:    */     }
/*  92:115 */     buffer.append(">");
/*  93:    */     
/*  94:117 */     return buffer.toString();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void write(Writer writer)
/*  98:    */     throws IOException
/*  99:    */   {
/* 100:121 */     writer.write("<!DOCTYPE ");
/* 101:122 */     writer.write(getElementName());
/* 102:    */     
/* 103:124 */     boolean hasPublicID = false;
/* 104:125 */     String publicID = getPublicID();
/* 105:127 */     if ((publicID != null) && (publicID.length() > 0))
/* 106:    */     {
/* 107:128 */       writer.write(" PUBLIC \"");
/* 108:129 */       writer.write(publicID);
/* 109:130 */       writer.write("\"");
/* 110:131 */       hasPublicID = true;
/* 111:    */     }
/* 112:134 */     String systemID = getSystemID();
/* 113:136 */     if ((systemID != null) && (systemID.length() > 0))
/* 114:    */     {
/* 115:137 */       if (!hasPublicID) {
/* 116:138 */         writer.write(" SYSTEM");
/* 117:    */       }
/* 118:141 */       writer.write(" \"");
/* 119:142 */       writer.write(systemID);
/* 120:143 */       writer.write("\"");
/* 121:    */     }
/* 122:146 */     List list = getInternalDeclarations();
/* 123:148 */     if ((list != null) && (list.size() > 0))
/* 124:    */     {
/* 125:149 */       writer.write(" [");
/* 126:151 */       for (Iterator iter = list.iterator(); iter.hasNext();)
/* 127:    */       {
/* 128:152 */         Object decl = iter.next();
/* 129:153 */         writer.write("\n  ");
/* 130:154 */         writer.write(decl.toString());
/* 131:    */       }
/* 132:157 */       writer.write("\n]");
/* 133:    */     }
/* 134:160 */     writer.write(">");
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void accept(Visitor visitor)
/* 138:    */   {
/* 139:164 */     visitor.visit(this);
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.AbstractDocumentType
 * JD-Core Version:    0.7.0.1
 */