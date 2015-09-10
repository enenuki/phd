/*   1:    */ package org.dom4j.util;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import org.dom4j.Attribute;
/*   5:    */ import org.dom4j.Branch;
/*   6:    */ import org.dom4j.CDATA;
/*   7:    */ import org.dom4j.CharacterData;
/*   8:    */ import org.dom4j.Comment;
/*   9:    */ import org.dom4j.Document;
/*  10:    */ import org.dom4j.DocumentType;
/*  11:    */ import org.dom4j.Element;
/*  12:    */ import org.dom4j.Entity;
/*  13:    */ import org.dom4j.Namespace;
/*  14:    */ import org.dom4j.Node;
/*  15:    */ import org.dom4j.ProcessingInstruction;
/*  16:    */ import org.dom4j.QName;
/*  17:    */ import org.dom4j.Text;
/*  18:    */ 
/*  19:    */ public class NodeComparator
/*  20:    */   implements Comparator
/*  21:    */ {
/*  22:    */   public int compare(Object o1, Object o2)
/*  23:    */   {
/*  24: 79 */     if (o1 == o2) {
/*  25: 80 */       return 0;
/*  26:    */     }
/*  27: 81 */     if (o1 == null) {
/*  28: 83 */       return -1;
/*  29:    */     }
/*  30: 84 */     if (o2 == null) {
/*  31: 85 */       return 1;
/*  32:    */     }
/*  33: 88 */     if ((o1 instanceof Node))
/*  34:    */     {
/*  35: 89 */       if ((o2 instanceof Node)) {
/*  36: 90 */         return compare((Node)o1, (Node)o2);
/*  37:    */       }
/*  38: 93 */       return 1;
/*  39:    */     }
/*  40: 96 */     if ((o2 instanceof Node)) {
/*  41: 98 */       return -1;
/*  42:    */     }
/*  43:100 */     if ((o1 instanceof Comparable))
/*  44:    */     {
/*  45:101 */       Comparable c1 = (Comparable)o1;
/*  46:    */       
/*  47:103 */       return c1.compareTo(o2);
/*  48:    */     }
/*  49:105 */     String name1 = o1.getClass().getName();
/*  50:106 */     String name2 = o2.getClass().getName();
/*  51:    */     
/*  52:108 */     return name1.compareTo(name2);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int compare(Node n1, Node n2)
/*  56:    */   {
/*  57:115 */     int nodeType1 = n1.getNodeType();
/*  58:116 */     int nodeType2 = n2.getNodeType();
/*  59:117 */     int answer = nodeType1 - nodeType2;
/*  60:119 */     if (answer != 0) {
/*  61:120 */       return answer;
/*  62:    */     }
/*  63:122 */     switch (nodeType1)
/*  64:    */     {
/*  65:    */     case 1: 
/*  66:124 */       return compare((Element)n1, (Element)n2);
/*  67:    */     case 9: 
/*  68:127 */       return compare((Document)n1, (Document)n2);
/*  69:    */     case 2: 
/*  70:130 */       return compare((Attribute)n1, (Attribute)n2);
/*  71:    */     case 3: 
/*  72:133 */       return compare((Text)n1, (Text)n2);
/*  73:    */     case 4: 
/*  74:136 */       return compare((CDATA)n1, (CDATA)n2);
/*  75:    */     case 5: 
/*  76:139 */       return compare((Entity)n1, (Entity)n2);
/*  77:    */     case 7: 
/*  78:142 */       return compare((ProcessingInstruction)n1, (ProcessingInstruction)n2);
/*  79:    */     case 8: 
/*  80:146 */       return compare((Comment)n1, (Comment)n2);
/*  81:    */     case 10: 
/*  82:149 */       return compare((DocumentType)n1, (DocumentType)n2);
/*  83:    */     case 13: 
/*  84:152 */       return compare((Namespace)n1, (Namespace)n2);
/*  85:    */     }
/*  86:155 */     throw new RuntimeException("Invalid node types. node1: " + n1 + " and node2: " + n2);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int compare(Document n1, Document n2)
/*  90:    */   {
/*  91:162 */     int answer = compare(n1.getDocType(), n2.getDocType());
/*  92:164 */     if (answer == 0) {
/*  93:165 */       answer = compareContent(n1, n2);
/*  94:    */     }
/*  95:168 */     return answer;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int compare(Element n1, Element n2)
/*  99:    */   {
/* 100:172 */     int answer = compare(n1.getQName(), n2.getQName());
/* 101:174 */     if (answer == 0)
/* 102:    */     {
/* 103:176 */       int c1 = n1.attributeCount();
/* 104:177 */       int c2 = n2.attributeCount();
/* 105:178 */       answer = c1 - c2;
/* 106:180 */       if (answer == 0)
/* 107:    */       {
/* 108:181 */         for (int i = 0; i < c1; i++)
/* 109:    */         {
/* 110:182 */           Attribute a1 = n1.attribute(i);
/* 111:183 */           Attribute a2 = n2.attribute(a1.getQName());
/* 112:184 */           answer = compare(a1, a2);
/* 113:186 */           if (answer != 0) {
/* 114:187 */             return answer;
/* 115:    */           }
/* 116:    */         }
/* 117:191 */         answer = compareContent(n1, n2);
/* 118:    */       }
/* 119:    */     }
/* 120:195 */     return answer;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int compare(Attribute n1, Attribute n2)
/* 124:    */   {
/* 125:199 */     int answer = compare(n1.getQName(), n2.getQName());
/* 126:201 */     if (answer == 0) {
/* 127:202 */       answer = compare(n1.getValue(), n2.getValue());
/* 128:    */     }
/* 129:205 */     return answer;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int compare(QName n1, QName n2)
/* 133:    */   {
/* 134:209 */     int answer = compare(n1.getNamespaceURI(), n2.getNamespaceURI());
/* 135:211 */     if (answer == 0) {
/* 136:212 */       answer = compare(n1.getQualifiedName(), n2.getQualifiedName());
/* 137:    */     }
/* 138:215 */     return answer;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int compare(Namespace n1, Namespace n2)
/* 142:    */   {
/* 143:219 */     int answer = compare(n1.getURI(), n2.getURI());
/* 144:221 */     if (answer == 0) {
/* 145:222 */       answer = compare(n1.getPrefix(), n2.getPrefix());
/* 146:    */     }
/* 147:225 */     return answer;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int compare(CharacterData t1, CharacterData t2)
/* 151:    */   {
/* 152:229 */     return compare(t1.getText(), t2.getText());
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int compare(DocumentType o1, DocumentType o2)
/* 156:    */   {
/* 157:233 */     if (o1 == o2) {
/* 158:234 */       return 0;
/* 159:    */     }
/* 160:235 */     if (o1 == null) {
/* 161:237 */       return -1;
/* 162:    */     }
/* 163:238 */     if (o2 == null) {
/* 164:239 */       return 1;
/* 165:    */     }
/* 166:242 */     int answer = compare(o1.getPublicID(), o2.getPublicID());
/* 167:244 */     if (answer == 0)
/* 168:    */     {
/* 169:245 */       answer = compare(o1.getSystemID(), o2.getSystemID());
/* 170:247 */       if (answer == 0) {
/* 171:248 */         answer = compare(o1.getName(), o2.getName());
/* 172:    */       }
/* 173:    */     }
/* 174:252 */     return answer;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int compare(Entity n1, Entity n2)
/* 178:    */   {
/* 179:256 */     int answer = compare(n1.getName(), n2.getName());
/* 180:258 */     if (answer == 0) {
/* 181:259 */       answer = compare(n1.getText(), n2.getText());
/* 182:    */     }
/* 183:262 */     return answer;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public int compare(ProcessingInstruction n1, ProcessingInstruction n2)
/* 187:    */   {
/* 188:266 */     int answer = compare(n1.getTarget(), n2.getTarget());
/* 189:268 */     if (answer == 0) {
/* 190:269 */       answer = compare(n1.getText(), n2.getText());
/* 191:    */     }
/* 192:272 */     return answer;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public int compareContent(Branch b1, Branch b2)
/* 196:    */   {
/* 197:276 */     int c1 = b1.nodeCount();
/* 198:277 */     int c2 = b2.nodeCount();
/* 199:278 */     int answer = c1 - c2;
/* 200:280 */     if (answer == 0) {
/* 201:281 */       for (int i = 0; i < c1; i++)
/* 202:    */       {
/* 203:282 */         Node n1 = b1.node(i);
/* 204:283 */         Node n2 = b2.node(i);
/* 205:284 */         answer = compare(n1, n2);
/* 206:286 */         if (answer != 0) {
/* 207:    */           break;
/* 208:    */         }
/* 209:    */       }
/* 210:    */     }
/* 211:292 */     return answer;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public int compare(String o1, String o2)
/* 215:    */   {
/* 216:296 */     if (o1 == o2) {
/* 217:297 */       return 0;
/* 218:    */     }
/* 219:298 */     if (o1 == null) {
/* 220:300 */       return -1;
/* 221:    */     }
/* 222:301 */     if (o2 == null) {
/* 223:302 */       return 1;
/* 224:    */     }
/* 225:305 */     return o1.compareTo(o2);
/* 226:    */   }
/* 227:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.util.NodeComparator
 * JD-Core Version:    0.7.0.1
 */