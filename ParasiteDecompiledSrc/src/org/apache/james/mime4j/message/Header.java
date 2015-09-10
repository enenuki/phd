/*   1:    */ package org.apache.james.mime4j.message;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.LinkedList;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import org.apache.james.mime4j.MimeException;
/*  12:    */ import org.apache.james.mime4j.MimeIOException;
/*  13:    */ import org.apache.james.mime4j.parser.AbstractContentHandler;
/*  14:    */ import org.apache.james.mime4j.parser.Field;
/*  15:    */ import org.apache.james.mime4j.parser.MimeStreamParser;
/*  16:    */ 
/*  17:    */ public class Header
/*  18:    */   implements Iterable<Field>
/*  19:    */ {
/*  20: 42 */   private List<Field> fields = new LinkedList();
/*  21: 43 */   private Map<String, List<Field>> fieldMap = new HashMap();
/*  22:    */   
/*  23:    */   public Header() {}
/*  24:    */   
/*  25:    */   public Header(Header other)
/*  26:    */   {
/*  27: 62 */     for (Field otherField : other.fields) {
/*  28: 63 */       addField(otherField);
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Header(InputStream is)
/*  33:    */     throws IOException, MimeIOException
/*  34:    */   {
/*  35: 77 */     final MimeStreamParser parser = new MimeStreamParser();
/*  36: 78 */     parser.setContentHandler(new AbstractContentHandler()
/*  37:    */     {
/*  38:    */       public void endHeader()
/*  39:    */       {
/*  40: 81 */         parser.stop();
/*  41:    */       }
/*  42:    */       
/*  43:    */       public void field(Field field)
/*  44:    */         throws MimeException
/*  45:    */       {
/*  46: 85 */         Header.this.addField(field);
/*  47:    */       }
/*  48:    */     });
/*  49:    */     try
/*  50:    */     {
/*  51: 89 */       parser.parse(is);
/*  52:    */     }
/*  53:    */     catch (MimeException ex)
/*  54:    */     {
/*  55: 91 */       throw new MimeIOException(ex);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void addField(Field field)
/*  60:    */   {
/*  61:101 */     List<Field> values = (List)this.fieldMap.get(field.getName().toLowerCase());
/*  62:102 */     if (values == null)
/*  63:    */     {
/*  64:103 */       values = new LinkedList();
/*  65:104 */       this.fieldMap.put(field.getName().toLowerCase(), values);
/*  66:    */     }
/*  67:106 */     values.add(field);
/*  68:107 */     this.fields.add(field);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<Field> getFields()
/*  72:    */   {
/*  73:117 */     return Collections.unmodifiableList(this.fields);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Field getField(String name)
/*  77:    */   {
/*  78:128 */     List<Field> l = (List)this.fieldMap.get(name.toLowerCase());
/*  79:129 */     if ((l != null) && (!l.isEmpty())) {
/*  80:130 */       return (Field)l.get(0);
/*  81:    */     }
/*  82:132 */     return null;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<Field> getFields(String name)
/*  86:    */   {
/*  87:142 */     String lowerCaseName = name.toLowerCase();
/*  88:143 */     List<Field> l = (List)this.fieldMap.get(lowerCaseName);
/*  89:    */     List<Field> results;
/*  90:    */     List<Field> results;
/*  91:145 */     if ((l == null) || (l.isEmpty())) {
/*  92:146 */       results = Collections.emptyList();
/*  93:    */     } else {
/*  94:148 */       results = Collections.unmodifiableList(l);
/*  95:    */     }
/*  96:150 */     return results;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Iterator<Field> iterator()
/* 100:    */   {
/* 101:159 */     return Collections.unmodifiableList(this.fields).iterator();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int removeFields(String name)
/* 105:    */   {
/* 106:170 */     String lowerCaseName = name.toLowerCase();
/* 107:171 */     List<Field> removed = (List)this.fieldMap.remove(lowerCaseName);
/* 108:172 */     if ((removed == null) || (removed.isEmpty())) {
/* 109:173 */       return 0;
/* 110:    */     }
/* 111:175 */     for (Iterator<Field> iterator = this.fields.iterator(); iterator.hasNext();)
/* 112:    */     {
/* 113:176 */       Field field = (Field)iterator.next();
/* 114:177 */       if (field.getName().equalsIgnoreCase(name)) {
/* 115:178 */         iterator.remove();
/* 116:    */       }
/* 117:    */     }
/* 118:181 */     return removed.size();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setField(Field field)
/* 122:    */   {
/* 123:197 */     String lowerCaseName = field.getName().toLowerCase();
/* 124:198 */     List<Field> l = (List)this.fieldMap.get(lowerCaseName);
/* 125:199 */     if ((l == null) || (l.isEmpty()))
/* 126:    */     {
/* 127:200 */       addField(field);
/* 128:201 */       return;
/* 129:    */     }
/* 130:204 */     l.clear();
/* 131:205 */     l.add(field);
/* 132:    */     
/* 133:207 */     int firstOccurrence = -1;
/* 134:208 */     int index = 0;
/* 135:209 */     for (Iterator<Field> iterator = this.fields.iterator(); iterator.hasNext(); index++)
/* 136:    */     {
/* 137:210 */       Field f = (Field)iterator.next();
/* 138:211 */       if (f.getName().equalsIgnoreCase(field.getName()))
/* 139:    */       {
/* 140:212 */         iterator.remove();
/* 141:214 */         if (firstOccurrence == -1) {
/* 142:215 */           firstOccurrence = index;
/* 143:    */         }
/* 144:    */       }
/* 145:    */     }
/* 146:219 */     this.fields.add(firstOccurrence, field);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String toString()
/* 150:    */   {
/* 151:230 */     StringBuilder str = new StringBuilder(128);
/* 152:231 */     for (Field field : this.fields)
/* 153:    */     {
/* 154:232 */       str.append(field.toString());
/* 155:233 */       str.append("\r\n");
/* 156:    */     }
/* 157:235 */     return str.toString();
/* 158:    */   }
/* 159:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.message.Header
 * JD-Core Version:    0.7.0.1
 */