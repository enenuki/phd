/*   1:    */ package org.dom4j.rule;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.dom4j.Attribute;
/*   6:    */ import org.dom4j.Document;
/*   7:    */ import org.dom4j.Element;
/*   8:    */ import org.dom4j.Node;
/*   9:    */ 
/*  10:    */ public class Mode
/*  11:    */ {
/*  12: 30 */   private RuleSet[] ruleSets = new RuleSet[14];
/*  13:    */   private Map elementNameRuleSets;
/*  14:    */   private Map attributeNameRuleSets;
/*  15:    */   
/*  16:    */   public void fireRule(Node node)
/*  17:    */     throws Exception
/*  18:    */   {
/*  19: 51 */     if (node != null)
/*  20:    */     {
/*  21: 52 */       Rule rule = getMatchingRule(node);
/*  22: 54 */       if (rule != null)
/*  23:    */       {
/*  24: 55 */         Action action = rule.getAction();
/*  25: 57 */         if (action != null) {
/*  26: 58 */           action.run(node);
/*  27:    */         }
/*  28:    */       }
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void applyTemplates(Element element)
/*  33:    */     throws Exception
/*  34:    */   {
/*  35: 65 */     int i = 0;
/*  36: 65 */     for (int size = element.attributeCount(); i < size; i++)
/*  37:    */     {
/*  38: 66 */       Attribute attribute = element.attribute(i);
/*  39: 67 */       fireRule(attribute);
/*  40:    */     }
/*  41: 70 */     int i = 0;
/*  42: 70 */     for (int size = element.nodeCount(); i < size; i++)
/*  43:    */     {
/*  44: 71 */       Node node = element.node(i);
/*  45: 72 */       fireRule(node);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void applyTemplates(Document document)
/*  50:    */     throws Exception
/*  51:    */   {
/*  52: 77 */     int i = 0;
/*  53: 77 */     for (int size = document.nodeCount(); i < size; i++)
/*  54:    */     {
/*  55: 78 */       Node node = document.node(i);
/*  56: 79 */       fireRule(node);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void addRule(Rule rule)
/*  61:    */   {
/*  62: 84 */     int matchType = rule.getMatchType();
/*  63: 85 */     String name = rule.getMatchesNodeName();
/*  64: 87 */     if (name != null) {
/*  65: 88 */       if (matchType == 1) {
/*  66: 89 */         this.elementNameRuleSets = addToNameMap(this.elementNameRuleSets, name, rule);
/*  67: 91 */       } else if (matchType == 2) {
/*  68: 92 */         this.attributeNameRuleSets = addToNameMap(this.attributeNameRuleSets, name, rule);
/*  69:    */       }
/*  70:    */     }
/*  71: 97 */     if (matchType >= 14) {
/*  72: 98 */       matchType = 0;
/*  73:    */     }
/*  74:101 */     if (matchType == 0)
/*  75:    */     {
/*  76:103 */       int i = 1;
/*  77:103 */       for (int size = this.ruleSets.length; i < size; i++)
/*  78:    */       {
/*  79:104 */         RuleSet ruleSet = this.ruleSets[i];
/*  80:106 */         if (ruleSet != null) {
/*  81:107 */           ruleSet.addRule(rule);
/*  82:    */         }
/*  83:    */       }
/*  84:    */     }
/*  85:112 */     getRuleSet(matchType).addRule(rule);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void removeRule(Rule rule)
/*  89:    */   {
/*  90:116 */     int matchType = rule.getMatchType();
/*  91:117 */     String name = rule.getMatchesNodeName();
/*  92:119 */     if (name != null) {
/*  93:120 */       if (matchType == 1) {
/*  94:121 */         removeFromNameMap(this.elementNameRuleSets, name, rule);
/*  95:122 */       } else if (matchType == 2) {
/*  96:123 */         removeFromNameMap(this.attributeNameRuleSets, name, rule);
/*  97:    */       }
/*  98:    */     }
/*  99:127 */     if (matchType >= 14) {
/* 100:128 */       matchType = 0;
/* 101:    */     }
/* 102:131 */     getRuleSet(matchType).removeRule(rule);
/* 103:133 */     if (matchType != 0) {
/* 104:134 */       getRuleSet(0).removeRule(rule);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Rule getMatchingRule(Node node)
/* 109:    */   {
/* 110:148 */     int matchType = node.getNodeType();
/* 111:150 */     if (matchType == 1)
/* 112:    */     {
/* 113:151 */       if (this.elementNameRuleSets != null)
/* 114:    */       {
/* 115:152 */         String name = node.getName();
/* 116:153 */         RuleSet ruleSet = (RuleSet)this.elementNameRuleSets.get(name);
/* 117:155 */         if (ruleSet != null)
/* 118:    */         {
/* 119:156 */           Rule answer = ruleSet.getMatchingRule(node);
/* 120:158 */           if (answer != null) {
/* 121:159 */             return answer;
/* 122:    */           }
/* 123:    */         }
/* 124:    */       }
/* 125:    */     }
/* 126:163 */     else if ((matchType == 2) && 
/* 127:164 */       (this.attributeNameRuleSets != null))
/* 128:    */     {
/* 129:165 */       String name = node.getName();
/* 130:166 */       RuleSet ruleSet = (RuleSet)this.attributeNameRuleSets.get(name);
/* 131:168 */       if (ruleSet != null)
/* 132:    */       {
/* 133:169 */         Rule answer = ruleSet.getMatchingRule(node);
/* 134:171 */         if (answer != null) {
/* 135:172 */           return answer;
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:178 */     if ((matchType < 0) || (matchType >= this.ruleSets.length)) {
/* 140:179 */       matchType = 0;
/* 141:    */     }
/* 142:182 */     Rule answer = null;
/* 143:183 */     RuleSet ruleSet = this.ruleSets[matchType];
/* 144:185 */     if (ruleSet != null) {
/* 145:187 */       answer = ruleSet.getMatchingRule(node);
/* 146:    */     }
/* 147:190 */     if ((answer == null) && (matchType != 0))
/* 148:    */     {
/* 149:192 */       ruleSet = this.ruleSets[0];
/* 150:194 */       if (ruleSet != null) {
/* 151:195 */         answer = ruleSet.getMatchingRule(node);
/* 152:    */       }
/* 153:    */     }
/* 154:199 */     return answer;
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected RuleSet getRuleSet(int matchType)
/* 158:    */   {
/* 159:212 */     RuleSet ruleSet = this.ruleSets[matchType];
/* 160:214 */     if (ruleSet == null)
/* 161:    */     {
/* 162:215 */       ruleSet = new RuleSet();
/* 163:216 */       this.ruleSets[matchType] = ruleSet;
/* 164:219 */       if (matchType != 0)
/* 165:    */       {
/* 166:220 */         RuleSet allRules = this.ruleSets[0];
/* 167:222 */         if (allRules != null) {
/* 168:223 */           ruleSet.addAll(allRules);
/* 169:    */         }
/* 170:    */       }
/* 171:    */     }
/* 172:228 */     return ruleSet;
/* 173:    */   }
/* 174:    */   
/* 175:    */   protected Map addToNameMap(Map map, String name, Rule rule)
/* 176:    */   {
/* 177:244 */     if (map == null) {
/* 178:245 */       map = new HashMap();
/* 179:    */     }
/* 180:248 */     RuleSet ruleSet = (RuleSet)map.get(name);
/* 181:250 */     if (ruleSet == null)
/* 182:    */     {
/* 183:251 */       ruleSet = new RuleSet();
/* 184:252 */       map.put(name, ruleSet);
/* 185:    */     }
/* 186:255 */     ruleSet.addRule(rule);
/* 187:    */     
/* 188:257 */     return map;
/* 189:    */   }
/* 190:    */   
/* 191:    */   protected void removeFromNameMap(Map map, String name, Rule rule)
/* 192:    */   {
/* 193:261 */     if (map != null)
/* 194:    */     {
/* 195:262 */       RuleSet ruleSet = (RuleSet)map.get(name);
/* 196:264 */       if (ruleSet != null) {
/* 197:265 */         ruleSet.removeRule(rule);
/* 198:    */       }
/* 199:    */     }
/* 200:    */   }
/* 201:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.rule.Mode
 * JD-Core Version:    0.7.0.1
 */