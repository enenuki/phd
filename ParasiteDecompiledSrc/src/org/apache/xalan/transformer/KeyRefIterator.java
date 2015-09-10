/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xalan.res.XSLMessages;
/*   6:    */ import org.apache.xalan.templates.KeyDeclaration;
/*   7:    */ import org.apache.xml.dtm.DTM;
/*   8:    */ import org.apache.xml.dtm.DTMIterator;
/*   9:    */ import org.apache.xml.utils.QName;
/*  10:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  11:    */ import org.apache.xml.utils.XMLString;
/*  12:    */ import org.apache.xpath.Expression;
/*  13:    */ import org.apache.xpath.XPath;
/*  14:    */ import org.apache.xpath.XPathContext;
/*  15:    */ import org.apache.xpath.axes.ChildTestIterator;
/*  16:    */ import org.apache.xpath.axes.LocPathIterator;
/*  17:    */ import org.apache.xpath.axes.NodeSequence;
/*  18:    */ import org.apache.xpath.objects.XNodeSet;
/*  19:    */ import org.apache.xpath.objects.XObject;
/*  20:    */ import org.apache.xpath.patterns.NodeTest;
/*  21:    */ 
/*  22:    */ public class KeyRefIterator
/*  23:    */   extends ChildTestIterator
/*  24:    */ {
/*  25:    */   static final long serialVersionUID = 3837456451659435102L;
/*  26:    */   DTMIterator m_keysNodes;
/*  27:    */   protected XMLString m_ref;
/*  28:    */   protected QName m_name;
/*  29:    */   protected Vector m_keyDeclarations;
/*  30:    */   
/*  31:    */   public KeyRefIterator(QName name, XMLString ref, Vector keyDecls, DTMIterator ki)
/*  32:    */   {
/*  33: 52 */     super(null);
/*  34: 53 */     this.m_name = name;
/*  35: 54 */     this.m_ref = ref;
/*  36: 55 */     this.m_keyDeclarations = keyDecls;
/*  37: 56 */     this.m_keysNodes = ki;
/*  38: 57 */     setWhatToShow(-1);
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected int getNextNode()
/*  42:    */   {
/*  43:    */     int next;
/*  44: 69 */     while (-1 != (next = this.m_keysNodes.nextNode()))
/*  45:    */     {
/*  46:    */       int i;
/*  47: 71 */       if (1 == filterNode(i)) {
/*  48:    */         break;
/*  49:    */       }
/*  50:    */     }
/*  51: 74 */     this.m_lastFetched = next;
/*  52:    */     
/*  53: 76 */     return next;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public short filterNode(int testNode)
/*  57:    */   {
/*  58: 93 */     boolean foundKey = false;
/*  59: 94 */     Vector keys = this.m_keyDeclarations;
/*  60:    */     
/*  61: 96 */     QName name = this.m_name;
/*  62: 97 */     KeyIterator ki = (KeyIterator)((XNodeSet)this.m_keysNodes).getContainedIter();
/*  63: 98 */     XPathContext xctxt = ki.getXPathContext();
/*  64:100 */     if (null == xctxt) {
/*  65:101 */       assertion(false, "xctxt can not be null here!");
/*  66:    */     }
/*  67:    */     try
/*  68:    */     {
/*  69:105 */       XMLString lookupKey = this.m_ref;
/*  70:    */       
/*  71:    */ 
/*  72:108 */       int nDeclarations = keys.size();
/*  73:111 */       for (int i = 0; i < nDeclarations; i++)
/*  74:    */       {
/*  75:113 */         KeyDeclaration kd = (KeyDeclaration)keys.elementAt(i);
/*  76:117 */         if (kd.getName().equals(name))
/*  77:    */         {
/*  78:120 */           foundKey = true;
/*  79:    */           
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:125 */           XObject xuse = kd.getUse().execute(xctxt, testNode, ki.getPrefixResolver());
/*  84:127 */           if (xuse.getType() != 4)
/*  85:    */           {
/*  86:129 */             XMLString exprResult = xuse.xstr();
/*  87:131 */             if (lookupKey.equals(exprResult)) {
/*  88:132 */               return 1;
/*  89:    */             }
/*  90:    */           }
/*  91:    */           else
/*  92:    */           {
/*  93:136 */             DTMIterator nl = ((XNodeSet)xuse).iterRaw();
/*  94:    */             int useNode;
/*  95:139 */             while (-1 != (useNode = nl.nextNode()))
/*  96:    */             {
/*  97:    */               int i;
/*  98:141 */               DTM dtm = getDTM(i);
/*  99:142 */               XMLString exprResult = dtm.getStringValue(i);
/* 100:143 */               if ((null != exprResult) && (lookupKey.equals(exprResult))) {
/* 101:144 */                 return 1;
/* 102:    */               }
/* 103:    */             }
/* 104:    */           }
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:    */     catch (TransformerException te)
/* 109:    */     {
/* 110:152 */       throw new WrappedRuntimeException(te);
/* 111:    */     }
/* 112:155 */     if (!foundKey) {
/* 113:156 */       throw new RuntimeException(XSLMessages.createMessage("ER_NO_XSLKEY_DECLARATION", new Object[] { name.getLocalName() }));
/* 114:    */     }
/* 115:160 */     return 2;
/* 116:    */   }
/* 117:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.KeyRefIterator
 * JD-Core Version:    0.7.0.1
 */