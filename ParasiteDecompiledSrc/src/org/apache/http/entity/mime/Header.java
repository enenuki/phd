/*   1:    */ package org.apache.http.entity.mime;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.LinkedList;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Locale;
/*  10:    */ import java.util.Map;
/*  11:    */ 
/*  12:    */ public class Header
/*  13:    */   implements Iterable<MinimalField>
/*  14:    */ {
/*  15:    */   private final List<MinimalField> fields;
/*  16:    */   private final Map<String, List<MinimalField>> fieldMap;
/*  17:    */   
/*  18:    */   public Header()
/*  19:    */   {
/*  20: 49 */     this.fields = new LinkedList();
/*  21: 50 */     this.fieldMap = new HashMap();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void addField(MinimalField field)
/*  25:    */   {
/*  26: 54 */     if (field == null) {
/*  27: 55 */       return;
/*  28:    */     }
/*  29: 57 */     String key = field.getName().toLowerCase(Locale.US);
/*  30: 58 */     List<MinimalField> values = (List)this.fieldMap.get(key);
/*  31: 59 */     if (values == null)
/*  32:    */     {
/*  33: 60 */       values = new LinkedList();
/*  34: 61 */       this.fieldMap.put(key, values);
/*  35:    */     }
/*  36: 63 */     values.add(field);
/*  37: 64 */     this.fields.add(field);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<MinimalField> getFields()
/*  41:    */   {
/*  42: 68 */     return new ArrayList(this.fields);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public MinimalField getField(String name)
/*  46:    */   {
/*  47: 72 */     if (name == null) {
/*  48: 73 */       return null;
/*  49:    */     }
/*  50: 75 */     String key = name.toLowerCase(Locale.US);
/*  51: 76 */     List<MinimalField> list = (List)this.fieldMap.get(key);
/*  52: 77 */     if ((list != null) && (!list.isEmpty())) {
/*  53: 78 */       return (MinimalField)list.get(0);
/*  54:    */     }
/*  55: 80 */     return null;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<MinimalField> getFields(String name)
/*  59:    */   {
/*  60: 84 */     if (name == null) {
/*  61: 85 */       return null;
/*  62:    */     }
/*  63: 87 */     String key = name.toLowerCase(Locale.US);
/*  64: 88 */     List<MinimalField> list = (List)this.fieldMap.get(key);
/*  65: 89 */     if ((list == null) || (list.isEmpty())) {
/*  66: 90 */       return Collections.emptyList();
/*  67:    */     }
/*  68: 92 */     return new ArrayList(list);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int removeFields(String name)
/*  72:    */   {
/*  73: 97 */     if (name == null) {
/*  74: 98 */       return 0;
/*  75:    */     }
/*  76:100 */     String key = name.toLowerCase(Locale.US);
/*  77:101 */     List<MinimalField> removed = (List)this.fieldMap.remove(key);
/*  78:102 */     if ((removed == null) || (removed.isEmpty())) {
/*  79:103 */       return 0;
/*  80:    */     }
/*  81:105 */     this.fields.removeAll(removed);
/*  82:106 */     return removed.size();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setField(MinimalField field)
/*  86:    */   {
/*  87:110 */     if (field == null) {
/*  88:111 */       return;
/*  89:    */     }
/*  90:113 */     String key = field.getName().toLowerCase(Locale.US);
/*  91:114 */     List<MinimalField> list = (List)this.fieldMap.get(key);
/*  92:115 */     if ((list == null) || (list.isEmpty()))
/*  93:    */     {
/*  94:116 */       addField(field);
/*  95:117 */       return;
/*  96:    */     }
/*  97:119 */     list.clear();
/*  98:120 */     list.add(field);
/*  99:121 */     int firstOccurrence = -1;
/* 100:122 */     int index = 0;
/* 101:123 */     for (Iterator<MinimalField> it = this.fields.iterator(); it.hasNext(); index++)
/* 102:    */     {
/* 103:124 */       MinimalField f = (MinimalField)it.next();
/* 104:125 */       if (f.getName().equalsIgnoreCase(field.getName()))
/* 105:    */       {
/* 106:126 */         it.remove();
/* 107:127 */         if (firstOccurrence == -1) {
/* 108:128 */           firstOccurrence = index;
/* 109:    */         }
/* 110:    */       }
/* 111:    */     }
/* 112:132 */     this.fields.add(firstOccurrence, field);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Iterator<MinimalField> iterator()
/* 116:    */   {
/* 117:136 */     return Collections.unmodifiableList(this.fields).iterator();
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String toString()
/* 121:    */   {
/* 122:141 */     return this.fields.toString();
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.mime.Header
 * JD-Core Version:    0.7.0.1
 */