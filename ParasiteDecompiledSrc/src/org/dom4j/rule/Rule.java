/*   1:    */ package org.dom4j.rule;
/*   2:    */ 
/*   3:    */ import org.dom4j.Node;
/*   4:    */ 
/*   5:    */ public class Rule
/*   6:    */   implements Comparable
/*   7:    */ {
/*   8:    */   private String mode;
/*   9:    */   private int importPrecedence;
/*  10:    */   private double priority;
/*  11:    */   private int appearenceCount;
/*  12:    */   private Pattern pattern;
/*  13:    */   private Action action;
/*  14:    */   
/*  15:    */   public Rule()
/*  16:    */   {
/*  17: 41 */     this.priority = 0.5D;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Rule(Pattern pattern)
/*  21:    */   {
/*  22: 45 */     this.pattern = pattern;
/*  23: 46 */     this.priority = pattern.getPriority();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Rule(Pattern pattern, Action action)
/*  27:    */   {
/*  28: 50 */     this(pattern);
/*  29: 51 */     this.action = action;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Rule(Rule that, Pattern pattern)
/*  33:    */   {
/*  34: 64 */     this.mode = that.mode;
/*  35: 65 */     this.importPrecedence = that.importPrecedence;
/*  36: 66 */     this.priority = that.priority;
/*  37: 67 */     this.appearenceCount = that.appearenceCount;
/*  38: 68 */     this.action = that.action;
/*  39: 69 */     this.pattern = pattern;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean equals(Object that)
/*  43:    */   {
/*  44: 73 */     if ((that instanceof Rule)) {
/*  45: 74 */       return compareTo((Rule)that) == 0;
/*  46:    */     }
/*  47: 77 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int hashCode()
/*  51:    */   {
/*  52: 81 */     return this.importPrecedence + this.appearenceCount;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int compareTo(Object that)
/*  56:    */   {
/*  57: 85 */     if ((that instanceof Rule)) {
/*  58: 86 */       return compareTo((Rule)that);
/*  59:    */     }
/*  60: 89 */     return getClass().getName().compareTo(that.getClass().getName());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int compareTo(Rule that)
/*  64:    */   {
/*  65:102 */     int answer = this.importPrecedence - that.importPrecedence;
/*  66:104 */     if (answer == 0)
/*  67:    */     {
/*  68:105 */       answer = (int)Math.round(this.priority - that.priority);
/*  69:107 */       if (answer == 0) {
/*  70:108 */         answer = this.appearenceCount - that.appearenceCount;
/*  71:    */       }
/*  72:    */     }
/*  73:112 */     return answer;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String toString()
/*  77:    */   {
/*  78:116 */     return super.toString() + "[ pattern: " + getPattern() + " action: " + getAction() + " ]";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public final boolean matches(Node node)
/*  82:    */   {
/*  83:129 */     return this.pattern.matches(node);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Rule[] getUnionRules()
/*  87:    */   {
/*  88:141 */     Pattern[] patterns = this.pattern.getUnionPatterns();
/*  89:143 */     if (patterns == null) {
/*  90:144 */       return null;
/*  91:    */     }
/*  92:147 */     int size = patterns.length;
/*  93:148 */     Rule[] answer = new Rule[size];
/*  94:150 */     for (int i = 0; i < size; i++) {
/*  95:151 */       answer[i] = new Rule(this, patterns[i]);
/*  96:    */     }
/*  97:154 */     return answer;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public final short getMatchType()
/* 101:    */   {
/* 102:164 */     return this.pattern.getMatchType();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public final String getMatchesNodeName()
/* 106:    */   {
/* 107:178 */     return this.pattern.getMatchesNodeName();
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getMode()
/* 111:    */   {
/* 112:187 */     return this.mode;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setMode(String mode)
/* 116:    */   {
/* 117:197 */     this.mode = mode;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getImportPrecedence()
/* 121:    */   {
/* 122:206 */     return this.importPrecedence;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setImportPrecedence(int importPrecedence)
/* 126:    */   {
/* 127:216 */     this.importPrecedence = importPrecedence;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public double getPriority()
/* 131:    */   {
/* 132:225 */     return this.priority;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setPriority(double priority)
/* 136:    */   {
/* 137:235 */     this.priority = priority;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getAppearenceCount()
/* 141:    */   {
/* 142:244 */     return this.appearenceCount;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setAppearenceCount(int appearenceCount)
/* 146:    */   {
/* 147:254 */     this.appearenceCount = appearenceCount;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Pattern getPattern()
/* 151:    */   {
/* 152:263 */     return this.pattern;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setPattern(Pattern pattern)
/* 156:    */   {
/* 157:273 */     this.pattern = pattern;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Action getAction()
/* 161:    */   {
/* 162:282 */     return this.action;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setAction(Action action)
/* 166:    */   {
/* 167:292 */     this.action = action;
/* 168:    */   }
/* 169:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.rule.Rule
 * JD-Core Version:    0.7.0.1
 */