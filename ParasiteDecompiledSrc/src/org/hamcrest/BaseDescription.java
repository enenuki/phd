/*   1:    */ package org.hamcrest;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import org.hamcrest.internal.ArrayIterator;
/*   6:    */ import org.hamcrest.internal.SelfDescribingValueIterator;
/*   7:    */ 
/*   8:    */ public abstract class BaseDescription
/*   9:    */   implements Description
/*  10:    */ {
/*  11:    */   public Description appendText(String text)
/*  12:    */   {
/*  13: 16 */     append(text);
/*  14: 17 */     return this;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public Description appendDescriptionOf(SelfDescribing value)
/*  18:    */   {
/*  19: 21 */     value.describeTo(this);
/*  20: 22 */     return this;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Description appendValue(Object value)
/*  24:    */   {
/*  25: 26 */     if (value == null)
/*  26:    */     {
/*  27: 27 */       append("null");
/*  28:    */     }
/*  29: 28 */     else if ((value instanceof String))
/*  30:    */     {
/*  31: 29 */       toJavaSyntax((String)value);
/*  32:    */     }
/*  33: 30 */     else if ((value instanceof Character))
/*  34:    */     {
/*  35: 31 */       append('"');
/*  36: 32 */       toJavaSyntax(((Character)value).charValue());
/*  37: 33 */       append('"');
/*  38:    */     }
/*  39: 34 */     else if ((value instanceof Short))
/*  40:    */     {
/*  41: 35 */       append('<');
/*  42: 36 */       append(String.valueOf(value));
/*  43: 37 */       append("s>");
/*  44:    */     }
/*  45: 38 */     else if ((value instanceof Long))
/*  46:    */     {
/*  47: 39 */       append('<');
/*  48: 40 */       append(String.valueOf(value));
/*  49: 41 */       append("L>");
/*  50:    */     }
/*  51: 42 */     else if ((value instanceof Float))
/*  52:    */     {
/*  53: 43 */       append('<');
/*  54: 44 */       append(String.valueOf(value));
/*  55: 45 */       append("F>");
/*  56:    */     }
/*  57: 46 */     else if (value.getClass().isArray())
/*  58:    */     {
/*  59: 47 */       appendValueList("[", ", ", "]", new ArrayIterator(value));
/*  60:    */     }
/*  61:    */     else
/*  62:    */     {
/*  63: 49 */       append('<');
/*  64: 50 */       append(String.valueOf(value));
/*  65: 51 */       append('>');
/*  66:    */     }
/*  67: 53 */     return this;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public <T> Description appendValueList(String start, String separator, String end, T... values)
/*  71:    */   {
/*  72: 57 */     return appendValueList(start, separator, end, Arrays.asList(values));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public <T> Description appendValueList(String start, String separator, String end, Iterable<T> values)
/*  76:    */   {
/*  77: 61 */     return appendValueList(start, separator, end, values.iterator());
/*  78:    */   }
/*  79:    */   
/*  80:    */   private <T> Description appendValueList(String start, String separator, String end, Iterator<T> values)
/*  81:    */   {
/*  82: 65 */     return appendList(start, separator, end, new SelfDescribingValueIterator(values));
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Description appendList(String start, String separator, String end, Iterable<? extends SelfDescribing> values)
/*  86:    */   {
/*  87: 69 */     return appendList(start, separator, end, values.iterator());
/*  88:    */   }
/*  89:    */   
/*  90:    */   private Description appendList(String start, String separator, String end, Iterator<? extends SelfDescribing> i)
/*  91:    */   {
/*  92: 73 */     boolean separate = false;
/*  93:    */     
/*  94: 75 */     append(start);
/*  95: 76 */     while (i.hasNext())
/*  96:    */     {
/*  97: 77 */       if (separate) {
/*  98: 77 */         append(separator);
/*  99:    */       }
/* 100: 78 */       appendDescriptionOf((SelfDescribing)i.next());
/* 101: 79 */       separate = true;
/* 102:    */     }
/* 103: 81 */     append(end);
/* 104:    */     
/* 105: 83 */     return this;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void append(String str)
/* 109:    */   {
/* 110: 92 */     for (int i = 0; i < str.length(); i++) {
/* 111: 93 */       append(str.charAt(i));
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected abstract void append(char paramChar);
/* 116:    */   
/* 117:    */   private void toJavaSyntax(String unformatted)
/* 118:    */   {
/* 119:102 */     append('"');
/* 120:103 */     for (int i = 0; i < unformatted.length(); i++) {
/* 121:104 */       toJavaSyntax(unformatted.charAt(i));
/* 122:    */     }
/* 123:106 */     append('"');
/* 124:    */   }
/* 125:    */   
/* 126:    */   private void toJavaSyntax(char ch)
/* 127:    */   {
/* 128:110 */     switch (ch)
/* 129:    */     {
/* 130:    */     case '"': 
/* 131:112 */       append("\\\"");
/* 132:113 */       break;
/* 133:    */     case '\n': 
/* 134:115 */       append("\\n");
/* 135:116 */       break;
/* 136:    */     case '\r': 
/* 137:118 */       append("\\r");
/* 138:119 */       break;
/* 139:    */     case '\t': 
/* 140:121 */       append("\\t");
/* 141:122 */       break;
/* 142:    */     default: 
/* 143:124 */       append(ch);
/* 144:    */     }
/* 145:    */   }
/* 146:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.BaseDescription
 * JD-Core Version:    0.7.0.1
 */