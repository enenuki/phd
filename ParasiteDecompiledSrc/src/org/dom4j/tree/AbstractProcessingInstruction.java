/*   1:    */ package org.dom4j.tree;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.Writer;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.StringTokenizer;
/*  11:    */ import org.dom4j.Element;
/*  12:    */ import org.dom4j.ProcessingInstruction;
/*  13:    */ import org.dom4j.Visitor;
/*  14:    */ 
/*  15:    */ public abstract class AbstractProcessingInstruction
/*  16:    */   extends AbstractNode
/*  17:    */   implements ProcessingInstruction
/*  18:    */ {
/*  19:    */   public short getNodeType()
/*  20:    */   {
/*  21: 36 */     return 7;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getPath(Element context)
/*  25:    */   {
/*  26: 40 */     Element parent = getParent();
/*  27:    */     
/*  28: 42 */     return (parent != null) && (parent != context) ? parent.getPath(context) + "/processing-instruction()" : "processing-instruction()";
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getUniquePath(Element context)
/*  32:    */   {
/*  33: 48 */     Element parent = getParent();
/*  34:    */     
/*  35: 50 */     return (parent != null) && (parent != context) ? parent.getUniquePath(context) + "/processing-instruction()" : "processing-instruction()";
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String toString()
/*  39:    */   {
/*  40: 56 */     return super.toString() + " [ProcessingInstruction: &" + getName() + ";]";
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String asXML()
/*  44:    */   {
/*  45: 61 */     return "<?" + getName() + " " + getText() + "?>";
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void write(Writer writer)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51: 65 */     writer.write("<?");
/*  52: 66 */     writer.write(getName());
/*  53: 67 */     writer.write(" ");
/*  54: 68 */     writer.write(getText());
/*  55: 69 */     writer.write("?>");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void accept(Visitor visitor)
/*  59:    */   {
/*  60: 73 */     visitor.visit(this);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setValue(String name, String value)
/*  64:    */   {
/*  65: 77 */     throw new UnsupportedOperationException("This PI is read-only and cannot be modified");
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setValues(Map data)
/*  69:    */   {
/*  70: 82 */     throw new UnsupportedOperationException("This PI is read-only and cannot be modified");
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getName()
/*  74:    */   {
/*  75: 87 */     return getTarget();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setName(String name)
/*  79:    */   {
/*  80: 91 */     setTarget(name);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean removeValue(String name)
/*  84:    */   {
/*  85: 95 */     return false;
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected String toString(Map values)
/*  89:    */   {
/*  90:111 */     StringBuffer buffer = new StringBuffer();
/*  91:113 */     for (Iterator iter = values.entrySet().iterator(); iter.hasNext();)
/*  92:    */     {
/*  93:114 */       Map.Entry entry = (Map.Entry)iter.next();
/*  94:115 */       String name = (String)entry.getKey();
/*  95:116 */       String value = (String)entry.getValue();
/*  96:    */       
/*  97:118 */       buffer.append(name);
/*  98:119 */       buffer.append("=\"");
/*  99:120 */       buffer.append(value);
/* 100:121 */       buffer.append("\" ");
/* 101:    */     }
/* 102:125 */     buffer.setLength(buffer.length() - 1);
/* 103:    */     
/* 104:127 */     return buffer.toString();
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected Map parseValues(String text)
/* 108:    */   {
/* 109:141 */     Map data = new HashMap();
/* 110:    */     
/* 111:143 */     StringTokenizer s = new StringTokenizer(text, " ='\"", true);
/* 112:145 */     while (s.hasMoreTokens())
/* 113:    */     {
/* 114:146 */       String name = getName(s);
/* 115:148 */       if (s.hasMoreTokens())
/* 116:    */       {
/* 117:149 */         String value = getValue(s);
/* 118:150 */         data.put(name, value);
/* 119:    */       }
/* 120:    */     }
/* 121:154 */     return data;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private String getName(StringTokenizer tokenizer)
/* 125:    */   {
/* 126:158 */     String token = tokenizer.nextToken();
/* 127:159 */     StringBuffer name = new StringBuffer(token);
/* 128:161 */     while (tokenizer.hasMoreTokens())
/* 129:    */     {
/* 130:162 */       token = tokenizer.nextToken();
/* 131:164 */       if (token.equals("=")) {
/* 132:    */         break;
/* 133:    */       }
/* 134:165 */       name.append(token);
/* 135:    */     }
/* 136:171 */     return name.toString().trim();
/* 137:    */   }
/* 138:    */   
/* 139:    */   private String getValue(StringTokenizer tokenizer)
/* 140:    */   {
/* 141:175 */     String token = tokenizer.nextToken();
/* 142:176 */     StringBuffer value = new StringBuffer();
/* 143:180 */     while ((tokenizer.hasMoreTokens()) && (!token.equals("'")) && (!token.equals("\""))) {
/* 144:181 */       token = tokenizer.nextToken();
/* 145:    */     }
/* 146:184 */     String quote = token;
/* 147:186 */     while (tokenizer.hasMoreTokens())
/* 148:    */     {
/* 149:187 */       token = tokenizer.nextToken();
/* 150:189 */       if (quote.equals(token)) {
/* 151:    */         break;
/* 152:    */       }
/* 153:190 */       value.append(token);
/* 154:    */     }
/* 155:196 */     return value.toString();
/* 156:    */   }
/* 157:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.AbstractProcessingInstruction
 * JD-Core Version:    0.7.0.1
 */