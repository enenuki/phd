/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.text.CollationKey;
/*   4:    */ import java.text.Collator;
/*   5:    */ import java.util.Vector;
/*   6:    */ import javax.xml.transform.TransformerException;
/*   7:    */ import org.apache.xml.dtm.DTM;
/*   8:    */ import org.apache.xml.dtm.DTMIterator;
/*   9:    */ import org.apache.xpath.XPath;
/*  10:    */ import org.apache.xpath.XPathContext;
/*  11:    */ import org.apache.xpath.objects.XNodeSet;
/*  12:    */ import org.apache.xpath.objects.XObject;
/*  13:    */ 
/*  14:    */ public class NodeSorter
/*  15:    */ {
/*  16:    */   XPathContext m_execContext;
/*  17:    */   Vector m_keys;
/*  18:    */   
/*  19:    */   public NodeSorter(XPathContext p)
/*  20:    */   {
/*  21: 61 */     this.m_execContext = p;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void sort(DTMIterator v, Vector keys, XPathContext support)
/*  25:    */     throws TransformerException
/*  26:    */   {
/*  27: 77 */     this.m_keys = keys;
/*  28:    */     
/*  29:    */ 
/*  30: 80 */     int n = v.getLength();
/*  31:    */     
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38: 88 */     Vector nodes = new Vector();
/*  39: 90 */     for (int i = 0; i < n; i++)
/*  40:    */     {
/*  41: 92 */       NodeCompareElem elem = new NodeCompareElem(v.item(i));
/*  42:    */       
/*  43: 94 */       nodes.addElement(elem);
/*  44:    */     }
/*  45: 97 */     Vector scratchVector = new Vector();
/*  46:    */     
/*  47: 99 */     mergesort(nodes, scratchVector, 0, n - 1, support);
/*  48:102 */     for (int i = 0; i < n; i++) {
/*  49:104 */       v.setItem(((NodeCompareElem)nodes.elementAt(i)).m_node, i);
/*  50:    */     }
/*  51:106 */     v.setCurrentPos(0);
/*  52:    */   }
/*  53:    */   
/*  54:    */   int compare(NodeCompareElem n1, NodeCompareElem n2, int kIndex, XPathContext support)
/*  55:    */     throws TransformerException
/*  56:    */   {
/*  57:131 */     int result = 0;
/*  58:132 */     NodeSortKey k = (NodeSortKey)this.m_keys.elementAt(kIndex);
/*  59:134 */     if (k.m_treatAsNumbers)
/*  60:    */     {
/*  61:    */       double n1Num;
/*  62:    */       double n2Num;
/*  63:138 */       if (kIndex == 0)
/*  64:    */       {
/*  65:140 */         n1Num = ((Double)n1.m_key1Value).doubleValue();
/*  66:141 */         n2Num = ((Double)n2.m_key1Value).doubleValue();
/*  67:    */       }
/*  68:143 */       else if (kIndex == 1)
/*  69:    */       {
/*  70:145 */         n1Num = ((Double)n1.m_key2Value).doubleValue();
/*  71:146 */         n2Num = ((Double)n2.m_key2Value).doubleValue();
/*  72:    */       }
/*  73:    */       else
/*  74:    */       {
/*  75:159 */         XObject r1 = k.m_selectPat.execute(this.m_execContext, n1.m_node, k.m_namespaceContext);
/*  76:    */         
/*  77:161 */         XObject r2 = k.m_selectPat.execute(this.m_execContext, n2.m_node, k.m_namespaceContext);
/*  78:    */         
/*  79:163 */         n1Num = r1.num();
/*  80:    */         
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:168 */         n2Num = r2.num();
/*  85:    */       }
/*  86:172 */       if ((n1Num == n2Num) && (kIndex + 1 < this.m_keys.size()))
/*  87:    */       {
/*  88:174 */         result = compare(n1, n2, kIndex + 1, support);
/*  89:    */       }
/*  90:    */       else
/*  91:    */       {
/*  92:    */         double diff;
/*  93:179 */         if (Double.isNaN(n1Num))
/*  94:    */         {
/*  95:181 */           if (Double.isNaN(n2Num)) {
/*  96:182 */             diff = 0.0D;
/*  97:    */           } else {
/*  98:184 */             diff = -1.0D;
/*  99:    */           }
/* 100:    */         }
/* 101:186 */         else if (Double.isNaN(n2Num)) {
/* 102:187 */           diff = 1.0D;
/* 103:    */         } else {
/* 104:189 */           diff = n1Num - n2Num;
/* 105:    */         }
/* 106:192 */         result = diff > 0.0D ? 1 : k.m_descending ? -1 : diff < 0.0D ? -1 : k.m_descending ? 1 : 0;
/* 107:    */       }
/* 108:    */     }
/* 109:    */     else
/* 110:    */     {
/* 111:    */       CollationKey n1String;
/* 112:    */       CollationKey n2String;
/* 113:201 */       if (kIndex == 0)
/* 114:    */       {
/* 115:203 */         n1String = (CollationKey)n1.m_key1Value;
/* 116:204 */         n2String = (CollationKey)n2.m_key1Value;
/* 117:    */       }
/* 118:206 */       else if (kIndex == 1)
/* 119:    */       {
/* 120:208 */         n1String = (CollationKey)n1.m_key2Value;
/* 121:209 */         n2String = (CollationKey)n2.m_key2Value;
/* 122:    */       }
/* 123:    */       else
/* 124:    */       {
/* 125:222 */         XObject r1 = k.m_selectPat.execute(this.m_execContext, n1.m_node, k.m_namespaceContext);
/* 126:    */         
/* 127:224 */         XObject r2 = k.m_selectPat.execute(this.m_execContext, n2.m_node, k.m_namespaceContext);
/* 128:    */         
/* 129:    */ 
/* 130:227 */         n1String = k.m_col.getCollationKey(r1.str());
/* 131:228 */         n2String = k.m_col.getCollationKey(r2.str());
/* 132:    */       }
/* 133:233 */       result = n1String.compareTo(n2String);
/* 134:236 */       if (k.m_caseOrderUpper)
/* 135:    */       {
/* 136:238 */         String tempN1 = n1String.getSourceString().toLowerCase();
/* 137:239 */         String tempN2 = n2String.getSourceString().toLowerCase();
/* 138:241 */         if (tempN1.equals(tempN2)) {
/* 139:245 */           result = result == 0 ? 0 : -result;
/* 140:    */         }
/* 141:    */       }
/* 142:250 */       if (k.m_descending) {
/* 143:252 */         result = -result;
/* 144:    */       }
/* 145:    */     }
/* 146:256 */     if (0 == result) {
/* 147:258 */       if (kIndex + 1 < this.m_keys.size()) {
/* 148:260 */         result = compare(n1, n2, kIndex + 1, support);
/* 149:    */       }
/* 150:    */     }
/* 151:264 */     if (0 == result)
/* 152:    */     {
/* 153:271 */       DTM dtm = support.getDTM(n1.m_node);
/* 154:272 */       result = dtm.isNodeAfter(n1.m_node, n2.m_node) ? -1 : 1;
/* 155:    */     }
/* 156:277 */     return result;
/* 157:    */   }
/* 158:    */   
/* 159:    */   void mergesort(Vector a, Vector b, int l, int r, XPathContext support)
/* 160:    */     throws TransformerException
/* 161:    */   {
/* 162:299 */     if (r - l > 0)
/* 163:    */     {
/* 164:301 */       int m = (r + l) / 2;
/* 165:    */       
/* 166:303 */       mergesort(a, b, l, m, support);
/* 167:304 */       mergesort(a, b, m + 1, r, support);
/* 168:308 */       for (int i = m; i >= l; i--) {
/* 169:313 */         if (i >= b.size()) {
/* 170:314 */           b.insertElementAt(a.elementAt(i), i);
/* 171:    */         } else {
/* 172:316 */           b.setElementAt(a.elementAt(i), i);
/* 173:    */         }
/* 174:    */       }
/* 175:319 */       i = l;
/* 176:321 */       for (int j = m + 1; j <= r; j++) {
/* 177:325 */         if (r + m + 1 - j >= b.size()) {
/* 178:326 */           b.insertElementAt(a.elementAt(j), r + m + 1 - j);
/* 179:    */         } else {
/* 180:328 */           b.setElementAt(a.elementAt(j), r + m + 1 - j);
/* 181:    */         }
/* 182:    */       }
/* 183:331 */       j = r;
/* 184:335 */       for (int k = l; k <= r; k++)
/* 185:    */       {
/* 186:    */         int compVal;
/* 187:339 */         if (i == j) {
/* 188:340 */           compVal = -1;
/* 189:    */         } else {
/* 190:342 */           compVal = compare((NodeCompareElem)b.elementAt(i), (NodeCompareElem)b.elementAt(j), 0, support);
/* 191:    */         }
/* 192:345 */         if (compVal < 0)
/* 193:    */         {
/* 194:349 */           a.setElementAt(b.elementAt(i), k);
/* 195:    */           
/* 196:351 */           i++;
/* 197:    */         }
/* 198:353 */         else if (compVal > 0)
/* 199:    */         {
/* 200:357 */           a.setElementAt(b.elementAt(j), k);
/* 201:    */           
/* 202:359 */           j--;
/* 203:    */         }
/* 204:    */       }
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   class NodeCompareElem
/* 209:    */   {
/* 210:    */     int m_node;
/* 211:471 */     int maxkey = 2;
/* 212:    */     Object m_key1Value;
/* 213:    */     Object m_key2Value;
/* 214:    */     
/* 215:    */     NodeCompareElem(int node)
/* 216:    */       throws TransformerException
/* 217:    */     {
/* 218:493 */       this.m_node = node;
/* 219:495 */       if (!NodeSorter.this.m_keys.isEmpty())
/* 220:    */       {
/* 221:497 */         NodeSortKey k1 = (NodeSortKey)NodeSorter.this.m_keys.elementAt(0);
/* 222:498 */         XObject r = k1.m_selectPat.execute(NodeSorter.this.m_execContext, node, k1.m_namespaceContext);
/* 223:    */         double d;
/* 224:503 */         if (k1.m_treatAsNumbers)
/* 225:    */         {
/* 226:505 */           d = r.num();
/* 227:    */           
/* 228:    */ 
/* 229:508 */           this.m_key1Value = new Double(d);
/* 230:    */         }
/* 231:    */         else
/* 232:    */         {
/* 233:512 */           this.m_key1Value = k1.m_col.getCollationKey(r.str());
/* 234:    */         }
/* 235:515 */         if (r.getType() == 4)
/* 236:    */         {
/* 237:518 */           DTMIterator ni = ((XNodeSet)r).iterRaw();
/* 238:519 */           int current = ni.getCurrentNode();
/* 239:520 */           if (-1 == current) {
/* 240:521 */             current = ni.nextNode();
/* 241:    */           }
/* 242:    */         }
/* 243:529 */         if (NodeSorter.this.m_keys.size() > 1)
/* 244:    */         {
/* 245:531 */           NodeSortKey k2 = (NodeSortKey)NodeSorter.this.m_keys.elementAt(1);
/* 246:    */           
/* 247:533 */           XObject r2 = k2.m_selectPat.execute(NodeSorter.this.m_execContext, node, k2.m_namespaceContext);
/* 248:536 */           if (k2.m_treatAsNumbers)
/* 249:    */           {
/* 250:537 */             d = r2.num();
/* 251:538 */             this.m_key2Value = new Double(d);
/* 252:    */           }
/* 253:    */           else
/* 254:    */           {
/* 255:540 */             this.m_key2Value = k2.m_col.getCollationKey(r2.str());
/* 256:    */           }
/* 257:    */         }
/* 258:    */       }
/* 259:    */     }
/* 260:    */   }
/* 261:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.NodeSorter
 * JD-Core Version:    0.7.0.1
 */