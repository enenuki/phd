/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.Vector;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.apache.xalan.templates.KeyDeclaration;
/*   7:    */ import org.apache.xml.dtm.DTM;
/*   8:    */ import org.apache.xml.dtm.DTMIterator;
/*   9:    */ import org.apache.xml.dtm.DTMManager;
/*  10:    */ import org.apache.xml.utils.PrefixResolver;
/*  11:    */ import org.apache.xml.utils.QName;
/*  12:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  13:    */ import org.apache.xml.utils.XMLString;
/*  14:    */ import org.apache.xpath.NodeSetDTM;
/*  15:    */ import org.apache.xpath.XPath;
/*  16:    */ import org.apache.xpath.XPathContext;
/*  17:    */ import org.apache.xpath.axes.LocPathIterator;
/*  18:    */ import org.apache.xpath.axes.NodeSequence;
/*  19:    */ import org.apache.xpath.objects.XNodeSet;
/*  20:    */ import org.apache.xpath.objects.XObject;
/*  21:    */ 
/*  22:    */ public class KeyTable
/*  23:    */ {
/*  24:    */   private int m_docKey;
/*  25:    */   private Vector m_keyDeclarations;
/*  26: 63 */   private Hashtable m_refsTable = null;
/*  27:    */   private XNodeSet m_keyNodes;
/*  28:    */   
/*  29:    */   public int getDocKey()
/*  30:    */   {
/*  31: 72 */     return this.m_docKey;
/*  32:    */   }
/*  33:    */   
/*  34:    */   KeyIterator getKeyIterator()
/*  35:    */   {
/*  36: 83 */     return (KeyIterator)this.m_keyNodes.getContainedIter();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public KeyTable(int doc, PrefixResolver nscontext, QName name, Vector keyDeclarations, XPathContext xctxt)
/*  40:    */     throws TransformerException
/*  41:    */   {
/*  42: 99 */     this.m_docKey = doc;
/*  43:100 */     this.m_keyDeclarations = keyDeclarations;
/*  44:101 */     KeyIterator ki = new KeyIterator(name, keyDeclarations);
/*  45:    */     
/*  46:103 */     this.m_keyNodes = new XNodeSet(ki);
/*  47:104 */     this.m_keyNodes.allowDetachToRelease(false);
/*  48:105 */     this.m_keyNodes.setRoot(doc, xctxt);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public XNodeSet getNodeSetDTMByKey(QName name, XMLString ref)
/*  52:    */   {
/*  53:118 */     XNodeSet refNodes = (XNodeSet)getRefsTable().get(ref);
/*  54:    */     try
/*  55:    */     {
/*  56:122 */       if (refNodes != null) {
/*  57:124 */         refNodes = (XNodeSet)refNodes.cloneWithReset();
/*  58:    */       }
/*  59:    */     }
/*  60:    */     catch (CloneNotSupportedException e)
/*  61:    */     {
/*  62:129 */       refNodes = null;
/*  63:    */     }
/*  64:132 */     if (refNodes == null)
/*  65:    */     {
/*  66:134 */       KeyIterator ki = (KeyIterator)this.m_keyNodes.getContainedIter();
/*  67:135 */       XPathContext xctxt = ki.getXPathContext();
/*  68:136 */       refNodes = new XNodeSet(xctxt.getDTMManager())
/*  69:    */       {
/*  70:    */         public void setRoot(int nodeHandle, Object environment) {}
/*  71:140 */       };
/*  72:141 */       refNodes.reset();
/*  73:    */     }
/*  74:144 */     return refNodes;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public QName getKeyTableName()
/*  78:    */   {
/*  79:154 */     return getKeyIterator().getName();
/*  80:    */   }
/*  81:    */   
/*  82:    */   private Vector getKeyDeclarations()
/*  83:    */   {
/*  84:161 */     int nDeclarations = this.m_keyDeclarations.size();
/*  85:162 */     Vector keyDecls = new Vector(nDeclarations);
/*  86:165 */     for (int i = 0; i < nDeclarations; i++)
/*  87:    */     {
/*  88:167 */       KeyDeclaration kd = (KeyDeclaration)this.m_keyDeclarations.elementAt(i);
/*  89:171 */       if (kd.getName().equals(getKeyTableName())) {
/*  90:172 */         keyDecls.add(kd);
/*  91:    */       }
/*  92:    */     }
/*  93:176 */     return keyDecls;
/*  94:    */   }
/*  95:    */   
/*  96:    */   private Hashtable getRefsTable()
/*  97:    */   {
/*  98:185 */     if (this.m_refsTable == null)
/*  99:    */     {
/* 100:187 */       this.m_refsTable = new Hashtable(89);
/* 101:    */       
/* 102:189 */       KeyIterator ki = (KeyIterator)this.m_keyNodes.getContainedIter();
/* 103:190 */       XPathContext xctxt = ki.getXPathContext();
/* 104:    */       
/* 105:192 */       Vector keyDecls = getKeyDeclarations();
/* 106:193 */       int nKeyDecls = keyDecls.size();
/* 107:    */       
/* 108:    */ 
/* 109:196 */       this.m_keyNodes.reset();
/* 110:    */       int currentNode;
/* 111:197 */       while (-1 != (currentNode = this.m_keyNodes.nextNode())) {
/* 112:    */         try
/* 113:    */         {
/* 114:201 */           for (int keyDeclIdx = 0; keyDeclIdx < nKeyDecls; keyDeclIdx++)
/* 115:    */           {
/* 116:202 */             KeyDeclaration keyDeclaration = (KeyDeclaration)keyDecls.elementAt(keyDeclIdx);
/* 117:    */             int i;
/* 118:204 */             XObject xuse = keyDeclaration.getUse().execute(xctxt, i, ki.getPrefixResolver());
/* 119:209 */             if (xuse.getType() != 4)
/* 120:    */             {
/* 121:210 */               XMLString exprResult = xuse.xstr();
/* 122:211 */               addValueInRefsTable(xctxt, exprResult, i);
/* 123:    */             }
/* 124:    */             else
/* 125:    */             {
/* 126:213 */               DTMIterator i = ((XNodeSet)xuse).iterRaw();
/* 127:    */               int currentNodeInUseClause;
/* 128:216 */               while (-1 != (currentNodeInUseClause = i.nextNode()))
/* 129:    */               {
/* 130:    */                 int j;
/* 131:217 */                 DTM dtm = xctxt.getDTM(j);
/* 132:218 */                 XMLString exprResult = dtm.getStringValue(j);
/* 133:    */                 
/* 134:220 */                 addValueInRefsTable(xctxt, exprResult, i);
/* 135:    */               }
/* 136:    */             }
/* 137:    */           }
/* 138:    */         }
/* 139:    */         catch (TransformerException te)
/* 140:    */         {
/* 141:225 */           throw new WrappedRuntimeException(te);
/* 142:    */         }
/* 143:    */       }
/* 144:    */     }
/* 145:229 */     return this.m_refsTable;
/* 146:    */   }
/* 147:    */   
/* 148:    */   private void addValueInRefsTable(XPathContext xctxt, XMLString ref, int node)
/* 149:    */   {
/* 150:241 */     XNodeSet nodes = (XNodeSet)this.m_refsTable.get(ref);
/* 151:242 */     if (nodes == null)
/* 152:    */     {
/* 153:244 */       nodes = new XNodeSet(node, xctxt.getDTMManager());
/* 154:245 */       nodes.nextNode();
/* 155:246 */       this.m_refsTable.put(ref, nodes);
/* 156:    */     }
/* 157:254 */     else if (nodes.getCurrentNode() != node)
/* 158:    */     {
/* 159:255 */       nodes.mutableNodeset().addNode(node);
/* 160:256 */       nodes.nextNode();
/* 161:    */     }
/* 162:    */   }
/* 163:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.KeyTable
 * JD-Core Version:    0.7.0.1
 */