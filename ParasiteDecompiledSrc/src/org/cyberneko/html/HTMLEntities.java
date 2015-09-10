/*   1:    */ package org.cyberneko.html;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.Enumeration;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Properties;
/*  10:    */ 
/*  11:    */ public class HTMLEntities
/*  12:    */ {
/*  13:    */   protected static final Map ENTITIES;
/*  14: 43 */   protected static final IntProperties SEITITNE = new IntProperties();
/*  15:    */   
/*  16:    */   static
/*  17:    */   {
/*  18: 50 */     Properties props = new Properties();
/*  19:    */     
/*  20: 52 */     load0(props, "res/HTMLlat1.properties");
/*  21: 53 */     load0(props, "res/HTMLspecial.properties");
/*  22: 54 */     load0(props, "res/HTMLsymbol.properties");
/*  23: 55 */     load0(props, "res/XMLbuiltin.properties");
/*  24:    */     
/*  25:    */ 
/*  26: 58 */     Enumeration keys = props.propertyNames();
/*  27: 59 */     while (keys.hasMoreElements())
/*  28:    */     {
/*  29: 60 */       String key = (String)keys.nextElement();
/*  30: 61 */       String value = props.getProperty(key);
/*  31: 62 */       if (value.length() == 1)
/*  32:    */       {
/*  33: 63 */         int ivalue = value.charAt(0);
/*  34: 64 */         SEITITNE.put(ivalue, key);
/*  35:    */       }
/*  36:    */     }
/*  37: 68 */     ENTITIES = Collections.unmodifiableMap(new HashMap(props));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static int get(String name)
/*  41:    */   {
/*  42: 80 */     String value = (String)ENTITIES.get(name);
/*  43: 81 */     return value != null ? value.charAt(0) : -1;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static String get(int c)
/*  47:    */   {
/*  48: 89 */     return SEITITNE.get(c);
/*  49:    */   }
/*  50:    */   
/*  51:    */   private static void load0(Properties props, String filename)
/*  52:    */   {
/*  53:    */     try
/*  54:    */     {
/*  55: 99 */       props.load(HTMLEntities.class.getResourceAsStream(filename));
/*  56:    */     }
/*  57:    */     catch (IOException e)
/*  58:    */     {
/*  59:102 */       System.err.println("error: unable to load resource \"" + filename + "\"");
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   static class IntProperties
/*  64:    */   {
/*  65:    */     private Entry[] entries;
/*  66:    */     
/*  67:    */     IntProperties()
/*  68:    */     {
/*  69:111 */       this.entries = new Entry[101];
/*  70:    */     }
/*  71:    */     
/*  72:    */     public void put(int key, String value)
/*  73:    */     {
/*  74:113 */       int hash = key % this.entries.length;
/*  75:114 */       Entry entry = new Entry(key, value, this.entries[hash]);
/*  76:115 */       this.entries[hash] = entry;
/*  77:    */     }
/*  78:    */     
/*  79:    */     public String get(int key)
/*  80:    */     {
/*  81:118 */       int hash = key % this.entries.length;
/*  82:119 */       Entry entry = this.entries[hash];
/*  83:120 */       while (entry != null)
/*  84:    */       {
/*  85:121 */         if (entry.key == key) {
/*  86:122 */           return entry.value;
/*  87:    */         }
/*  88:124 */         entry = entry.next;
/*  89:    */       }
/*  90:126 */       return null;
/*  91:    */     }
/*  92:    */     
/*  93:    */     static class Entry
/*  94:    */     {
/*  95:    */       public int key;
/*  96:    */       public String value;
/*  97:    */       public Entry next;
/*  98:    */       
/*  99:    */       public Entry(int key, String value, Entry next)
/* 100:    */       {
/* 101:133 */         this.key = key;
/* 102:134 */         this.value = value;
/* 103:135 */         this.next = next;
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.HTMLEntities
 * JD-Core Version:    0.7.0.1
 */