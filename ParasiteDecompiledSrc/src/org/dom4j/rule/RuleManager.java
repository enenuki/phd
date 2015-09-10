/*   1:    */ package org.dom4j.rule;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import org.dom4j.Document;
/*   6:    */ import org.dom4j.Element;
/*   7:    */ import org.dom4j.Node;
/*   8:    */ import org.dom4j.rule.pattern.NodeTypePattern;
/*   9:    */ 
/*  10:    */ public class RuleManager
/*  11:    */ {
/*  12: 28 */   private HashMap modes = new HashMap();
/*  13:    */   private int appearenceCount;
/*  14:    */   private Action valueOfAction;
/*  15:    */   
/*  16:    */   public Mode getMode(String modeName)
/*  17:    */   {
/*  18: 52 */     Mode mode = (Mode)this.modes.get(modeName);
/*  19: 54 */     if (mode == null)
/*  20:    */     {
/*  21: 55 */       mode = createMode();
/*  22: 56 */       this.modes.put(modeName, mode);
/*  23:    */     }
/*  24: 59 */     return mode;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void addRule(Rule rule)
/*  28:    */   {
/*  29: 63 */     rule.setAppearenceCount(++this.appearenceCount);
/*  30:    */     
/*  31: 65 */     Mode mode = getMode(rule.getMode());
/*  32: 66 */     Rule[] childRules = rule.getUnionRules();
/*  33: 68 */     if (childRules != null)
/*  34:    */     {
/*  35: 69 */       int i = 0;
/*  36: 69 */       for (int size = childRules.length; i < size; i++) {
/*  37: 70 */         mode.addRule(childRules[i]);
/*  38:    */       }
/*  39:    */     }
/*  40:    */     else
/*  41:    */     {
/*  42: 73 */       mode.addRule(rule);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void removeRule(Rule rule)
/*  47:    */   {
/*  48: 78 */     Mode mode = getMode(rule.getMode());
/*  49: 79 */     Rule[] childRules = rule.getUnionRules();
/*  50: 81 */     if (childRules != null)
/*  51:    */     {
/*  52: 82 */       int i = 0;
/*  53: 82 */       for (int size = childRules.length; i < size; i++) {
/*  54: 83 */         mode.removeRule(childRules[i]);
/*  55:    */       }
/*  56:    */     }
/*  57:    */     else
/*  58:    */     {
/*  59: 86 */       mode.removeRule(rule);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Rule getMatchingRule(String modeName, Node node)
/*  64:    */   {
/*  65:102 */     Mode mode = (Mode)this.modes.get(modeName);
/*  66:104 */     if (mode != null) {
/*  67:105 */       return mode.getMatchingRule(node);
/*  68:    */     }
/*  69:107 */     System.out.println("Warning: No Mode for mode: " + mode);
/*  70:    */     
/*  71:109 */     return null;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void clear()
/*  75:    */   {
/*  76:114 */     this.modes.clear();
/*  77:115 */     this.appearenceCount = 0;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Action getValueOfAction()
/*  81:    */   {
/*  82:128 */     return this.valueOfAction;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setValueOfAction(Action valueOfAction)
/*  86:    */   {
/*  87:139 */     this.valueOfAction = valueOfAction;
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected Mode createMode()
/*  91:    */   {
/*  92:152 */     Mode mode = new Mode();
/*  93:153 */     addDefaultRules(mode);
/*  94:    */     
/*  95:155 */     return mode;
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected void addDefaultRules(Mode mode)
/*  99:    */   {
/* 100:166 */     Action applyTemplates = new Action()
/* 101:    */     {
/* 102:    */       private final Mode val$mode;
/* 103:    */       
/* 104:    */       public void run(Node node)
/* 105:    */         throws Exception
/* 106:    */       {
/* 107:168 */         if ((node instanceof Element)) {
/* 108:169 */           this.val$mode.applyTemplates((Element)node);
/* 109:170 */         } else if ((node instanceof Document)) {
/* 110:171 */           this.val$mode.applyTemplates((Document)node);
/* 111:    */         }
/* 112:    */       }
/* 113:175 */     };
/* 114:176 */     Action valueOf = getValueOfAction();
/* 115:    */     
/* 116:178 */     addDefaultRule(mode, NodeTypePattern.ANY_DOCUMENT, applyTemplates);
/* 117:179 */     addDefaultRule(mode, NodeTypePattern.ANY_ELEMENT, applyTemplates);
/* 118:181 */     if (valueOf != null)
/* 119:    */     {
/* 120:182 */       addDefaultRule(mode, NodeTypePattern.ANY_ATTRIBUTE, valueOf);
/* 121:183 */       addDefaultRule(mode, NodeTypePattern.ANY_TEXT, valueOf);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   protected void addDefaultRule(Mode mode, Pattern pattern, Action action)
/* 126:    */   {
/* 127:188 */     Rule rule = createDefaultRule(pattern, action);
/* 128:189 */     mode.addRule(rule);
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected Rule createDefaultRule(Pattern pattern, Action action)
/* 132:    */   {
/* 133:193 */     Rule rule = new Rule(pattern, action);
/* 134:194 */     rule.setImportPrecedence(-1);
/* 135:    */     
/* 136:196 */     return rule;
/* 137:    */   }
/* 138:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.rule.RuleManager
 * JD-Core Version:    0.7.0.1
 */