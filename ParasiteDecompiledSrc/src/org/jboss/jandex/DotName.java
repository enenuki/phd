/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ import java.util.ArrayDeque;
/*   4:    */ 
/*   5:    */ public final class DotName
/*   6:    */   implements Comparable<DotName>
/*   7:    */ {
/*   8:    */   private final DotName prefix;
/*   9:    */   private final String local;
/*  10:    */   private int hash;
/*  11: 53 */   private boolean componentized = false;
/*  12:    */   
/*  13:    */   public static DotName createSimple(String name)
/*  14:    */   {
/*  15: 63 */     return new DotName(null, name, false);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static DotName createComponentized(DotName prefix, String localName)
/*  19:    */   {
/*  20: 80 */     if (localName.indexOf('.') != -1) {
/*  21: 81 */       throw new IllegalArgumentException("A componentized DotName can not contain '.' characters in a local name");
/*  22:    */     }
/*  23: 83 */     return new DotName(prefix, localName, true);
/*  24:    */   }
/*  25:    */   
/*  26:    */   DotName(DotName prefix, String local, boolean noDots)
/*  27:    */   {
/*  28: 87 */     if (local == null) {
/*  29: 88 */       throw new IllegalArgumentException("Local string can not be null");
/*  30:    */     }
/*  31: 90 */     this.prefix = prefix;
/*  32: 91 */     this.local = local;
/*  33: 92 */     this.componentized = (((prefix == null) || (prefix.componentized)) && (noDots));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public DotName prefix()
/*  37:    */   {
/*  38:102 */     return this.prefix;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String local()
/*  42:    */   {
/*  43:113 */     return this.local;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isComponentized()
/*  47:    */   {
/*  48:122 */     return !this.componentized;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String toString()
/*  52:    */   {
/*  53:131 */     StringBuilder string = new StringBuilder();
/*  54:132 */     if (this.prefix != null) {
/*  55:133 */       string.append(this.prefix).append(".");
/*  56:    */     }
/*  57:135 */     string.append(this.local);
/*  58:    */     
/*  59:137 */     return string.toString();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int hashCode()
/*  63:    */   {
/*  64:141 */     int hash = this.hash;
/*  65:142 */     if (hash > 0) {
/*  66:143 */       return hash;
/*  67:    */     }
/*  68:145 */     if (this.prefix != null)
/*  69:    */     {
/*  70:146 */       hash = this.prefix.hashCode() * 31 + 46;
/*  71:149 */       for (int i = 0; i < this.local.length(); i++) {
/*  72:150 */         hash = 31 * hash + this.local.charAt(i);
/*  73:    */       }
/*  74:    */     }
/*  75:    */     else
/*  76:    */     {
/*  77:153 */       hash = this.local.hashCode();
/*  78:    */     }
/*  79:156 */     return this.hash = hash;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int compareTo(DotName other)
/*  83:    */   {
/*  84:162 */     if ((this.componentized) && (other.componentized))
/*  85:    */     {
/*  86:163 */       ArrayDeque<DotName> thisStack = new ArrayDeque();
/*  87:164 */       ArrayDeque<DotName> otherStack = new ArrayDeque();
/*  88:    */       
/*  89:166 */       DotName curr = this;
/*  90:167 */       while (curr != null)
/*  91:    */       {
/*  92:168 */         thisStack.push(curr);
/*  93:169 */         curr = curr.prefix();
/*  94:    */       }
/*  95:172 */       curr = other;
/*  96:173 */       while (curr != null)
/*  97:    */       {
/*  98:174 */         otherStack.push(curr);
/*  99:175 */         curr = curr.prefix();
/* 100:    */       }
/* 101:178 */       int thisSize = thisStack.size();
/* 102:179 */       int otherSize = otherStack.size();
/* 103:180 */       int stop = Math.min(thisSize, otherSize);
/* 104:182 */       for (int i = 0; i < stop; i++)
/* 105:    */       {
/* 106:183 */         DotName thisComp = (DotName)thisStack.pop();
/* 107:184 */         DotName otherComp = (DotName)otherStack.pop();
/* 108:    */         
/* 109:186 */         int comp = thisComp.local.compareTo(otherComp.local);
/* 110:187 */         if (comp != 0) {
/* 111:188 */           return comp;
/* 112:    */         }
/* 113:    */       }
/* 114:191 */       int diff = thisSize - otherSize;
/* 115:192 */       if (diff != 0) {
/* 116:193 */         return diff;
/* 117:    */       }
/* 118:    */     }
/* 119:197 */     return toString().compareTo(other.toString());
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean equals(Object o)
/* 123:    */   {
/* 124:201 */     if (this == o) {
/* 125:202 */       return true;
/* 126:    */     }
/* 127:204 */     if (!(o instanceof DotName)) {
/* 128:205 */       return false;
/* 129:    */     }
/* 130:207 */     DotName other = (DotName)o;
/* 131:208 */     if ((other.prefix == null) && (this.prefix == null)) {
/* 132:209 */       return this.local.equals(other.local);
/* 133:    */     }
/* 134:211 */     if ((other.prefix == null) && (this.prefix != null)) {
/* 135:212 */       return toString().equals(other.local);
/* 136:    */     }
/* 137:214 */     if ((other.prefix != null) && (this.prefix == null)) {
/* 138:215 */       return other.toString().equals(this.local);
/* 139:    */     }
/* 140:218 */     return (this.local.equals(other.local)) && (this.prefix.equals(other.prefix));
/* 141:    */   }
/* 142:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.DotName
 * JD-Core Version:    0.7.0.1
 */