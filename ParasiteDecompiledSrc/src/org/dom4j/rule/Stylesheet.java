/*   1:    */ package org.dom4j.rule;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import org.dom4j.Document;
/*   6:    */ import org.dom4j.Element;
/*   7:    */ import org.dom4j.Node;
/*   8:    */ 
/*   9:    */ public class Stylesheet
/*  10:    */ {
/*  11: 29 */   private RuleManager ruleManager = new RuleManager();
/*  12:    */   private String modeName;
/*  13:    */   
/*  14:    */   public void addRule(Rule rule)
/*  15:    */   {
/*  16: 47 */     this.ruleManager.addRule(rule);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void removeRule(Rule rule)
/*  20:    */   {
/*  21: 57 */     this.ruleManager.removeRule(rule);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void run(Object input)
/*  25:    */     throws Exception
/*  26:    */   {
/*  27: 71 */     run(input, this.modeName);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void run(Object input, String mode)
/*  31:    */     throws Exception
/*  32:    */   {
/*  33: 75 */     if ((input instanceof Node)) {
/*  34: 76 */       run((Node)input, mode);
/*  35: 77 */     } else if ((input instanceof List)) {
/*  36: 78 */       run((List)input, mode);
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void run(List list)
/*  41:    */     throws Exception
/*  42:    */   {
/*  43: 83 */     run(list, this.modeName);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void run(List list, String mode)
/*  47:    */     throws Exception
/*  48:    */   {
/*  49: 87 */     int i = 0;
/*  50: 87 */     for (int size = list.size(); i < size; i++)
/*  51:    */     {
/*  52: 88 */       Object object = list.get(i);
/*  53: 90 */       if ((object instanceof Node)) {
/*  54: 91 */         run((Node)object, mode);
/*  55:    */       }
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void run(Node node)
/*  60:    */     throws Exception
/*  61:    */   {
/*  62: 97 */     run(node, this.modeName);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void run(Node node, String mode)
/*  66:    */     throws Exception
/*  67:    */   {
/*  68:101 */     Mode mod = this.ruleManager.getMode(mode);
/*  69:102 */     mod.fireRule(node);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void applyTemplates(Object input, org.dom4j.XPath xpath)
/*  73:    */     throws Exception
/*  74:    */   {
/*  75:117 */     applyTemplates(input, xpath, this.modeName);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void applyTemplates(Object input, org.dom4j.XPath xpath, String mode)
/*  79:    */     throws Exception
/*  80:    */   {
/*  81:135 */     Mode mod = this.ruleManager.getMode(mode);
/*  82:    */     
/*  83:137 */     List list = xpath.selectNodes(input);
/*  84:138 */     Iterator it = list.iterator();
/*  85:139 */     while (it.hasNext())
/*  86:    */     {
/*  87:140 */       Node current = (Node)it.next();
/*  88:141 */       mod.fireRule(current);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   /**
/*  93:    */    * @deprecated
/*  94:    */    */
/*  95:    */   public void applyTemplates(Object input, org.jaxen.XPath xpath)
/*  96:    */     throws Exception
/*  97:    */   {
/*  98:159 */     applyTemplates(input, xpath, this.modeName);
/*  99:    */   }
/* 100:    */   
/* 101:    */   /**
/* 102:    */    * @deprecated
/* 103:    */    */
/* 104:    */   public void applyTemplates(Object input, org.jaxen.XPath xpath, String mode)
/* 105:    */     throws Exception
/* 106:    */   {
/* 107:179 */     Mode mod = this.ruleManager.getMode(mode);
/* 108:    */     
/* 109:181 */     List list = xpath.selectNodes(input);
/* 110:182 */     Iterator it = list.iterator();
/* 111:183 */     while (it.hasNext())
/* 112:    */     {
/* 113:184 */       Node current = (Node)it.next();
/* 114:185 */       mod.fireRule(current);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void applyTemplates(Object input)
/* 119:    */     throws Exception
/* 120:    */   {
/* 121:202 */     applyTemplates(input, this.modeName);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void applyTemplates(Object input, String mode)
/* 125:    */     throws Exception
/* 126:    */   {
/* 127:220 */     Mode mod = this.ruleManager.getMode(mode);
/* 128:222 */     if ((input instanceof Element))
/* 129:    */     {
/* 130:224 */       Element element = (Element)input;
/* 131:225 */       int i = 0;
/* 132:225 */       for (int size = element.nodeCount(); i < size; i++)
/* 133:    */       {
/* 134:226 */         Node node = element.node(i);
/* 135:227 */         mod.fireRule(node);
/* 136:    */       }
/* 137:    */     }
/* 138:229 */     else if ((input instanceof Document))
/* 139:    */     {
/* 140:231 */       Document document = (Document)input;
/* 141:232 */       int i = 0;
/* 142:232 */       for (int size = document.nodeCount(); i < size; i++)
/* 143:    */       {
/* 144:233 */         Node node = document.node(i);
/* 145:234 */         mod.fireRule(node);
/* 146:    */       }
/* 147:    */     }
/* 148:236 */     else if ((input instanceof List))
/* 149:    */     {
/* 150:237 */       List list = (List)input;
/* 151:    */       
/* 152:239 */       int i = 0;
/* 153:239 */       for (int size = list.size(); i < size; i++)
/* 154:    */       {
/* 155:240 */         Object object = list.get(i);
/* 156:242 */         if ((object instanceof Element)) {
/* 157:243 */           applyTemplates((Element)object, mode);
/* 158:244 */         } else if ((object instanceof Document)) {
/* 159:245 */           applyTemplates((Document)object, mode);
/* 160:    */         }
/* 161:    */       }
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void clear()
/* 166:    */   {
/* 167:252 */     this.ruleManager.clear();
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getModeName()
/* 171:    */   {
/* 172:264 */     return this.modeName;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setModeName(String modeName)
/* 176:    */   {
/* 177:274 */     this.modeName = modeName;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Action getValueOfAction()
/* 181:    */   {
/* 182:284 */     return this.ruleManager.getValueOfAction();
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setValueOfAction(Action valueOfAction)
/* 186:    */   {
/* 187:295 */     this.ruleManager.setValueOfAction(valueOfAction);
/* 188:    */   }
/* 189:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.rule.Stylesheet
 * JD-Core Version:    0.7.0.1
 */