/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.dtm.DTM;
/*   5:    */ import org.apache.xml.dtm.DTMIterator;
/*   6:    */ import org.apache.xml.dtm.ref.DTMNodeIterator;
/*   7:    */ import org.apache.xml.dtm.ref.DTMNodeList;
/*   8:    */ import org.apache.xml.utils.FastStringBuffer;
/*   9:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  10:    */ import org.apache.xml.utils.XMLString;
/*  11:    */ import org.apache.xpath.Expression;
/*  12:    */ import org.apache.xpath.ExpressionNode;
/*  13:    */ import org.apache.xpath.NodeSetDTM;
/*  14:    */ import org.apache.xpath.XPathContext;
/*  15:    */ import org.apache.xpath.axes.RTFIterator;
/*  16:    */ import org.w3c.dom.NodeList;
/*  17:    */ 
/*  18:    */ public class XRTreeFrag
/*  19:    */   extends XObject
/*  20:    */   implements Cloneable
/*  21:    */ {
/*  22:    */   static final long serialVersionUID = -3201553822254911567L;
/*  23:    */   private DTMXRTreeFrag m_DTMXRTreeFrag;
/*  24: 42 */   private int m_dtmRoot = -1;
/*  25: 43 */   protected boolean m_allowRelease = false;
/*  26:    */   
/*  27:    */   public XRTreeFrag(int root, XPathContext xctxt, ExpressionNode parent)
/*  28:    */   {
/*  29: 52 */     super(null);
/*  30: 53 */     exprSetParent(parent);
/*  31: 54 */     initDTM(root, xctxt);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public XRTreeFrag(int root, XPathContext xctxt)
/*  35:    */   {
/*  36: 63 */     super(null);
/*  37: 64 */     initDTM(root, xctxt);
/*  38:    */   }
/*  39:    */   
/*  40:    */   private final void initDTM(int root, XPathContext xctxt)
/*  41:    */   {
/*  42: 68 */     this.m_dtmRoot = root;
/*  43: 69 */     DTM dtm = xctxt.getDTM(root);
/*  44: 70 */     if (dtm != null) {
/*  45: 71 */       this.m_DTMXRTreeFrag = xctxt.getDTMXRTreeFrag(xctxt.getDTMIdentity(dtm));
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object object()
/*  50:    */   {
/*  51: 83 */     if (this.m_DTMXRTreeFrag.getXPathContext() != null) {
/*  52: 84 */       return new DTMNodeIterator(new NodeSetDTM(this.m_dtmRoot, this.m_DTMXRTreeFrag.getXPathContext().getDTMManager()));
/*  53:    */     }
/*  54: 86 */     return super.object();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public XRTreeFrag(Expression expr)
/*  58:    */   {
/*  59: 95 */     super(expr);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void allowDetachToRelease(boolean allowRelease)
/*  63:    */   {
/*  64:106 */     this.m_allowRelease = allowRelease;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void detach()
/*  68:    */   {
/*  69:119 */     if (this.m_allowRelease)
/*  70:    */     {
/*  71:120 */       this.m_DTMXRTreeFrag.destruct();
/*  72:121 */       setObject(null);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getType()
/*  77:    */   {
/*  78:132 */     return 5;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getTypeString()
/*  82:    */   {
/*  83:143 */     return "#RTREEFRAG";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public double num()
/*  87:    */     throws TransformerException
/*  88:    */   {
/*  89:155 */     XMLString s = xstr();
/*  90:    */     
/*  91:157 */     return s.toDouble();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean bool()
/*  95:    */   {
/*  96:168 */     return true;
/*  97:    */   }
/*  98:    */   
/*  99:171 */   private XMLString m_xmlStr = null;
/* 100:    */   
/* 101:    */   public XMLString xstr()
/* 102:    */   {
/* 103:180 */     if (null == this.m_xmlStr) {
/* 104:181 */       this.m_xmlStr = this.m_DTMXRTreeFrag.getDTM().getStringValue(this.m_dtmRoot);
/* 105:    */     }
/* 106:183 */     return this.m_xmlStr;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void appendToFsb(FastStringBuffer fsb)
/* 110:    */   {
/* 111:193 */     XString xstring = (XString)xstr();
/* 112:194 */     xstring.appendToFsb(fsb);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String str()
/* 116:    */   {
/* 117:205 */     String str = this.m_DTMXRTreeFrag.getDTM().getStringValue(this.m_dtmRoot).toString();
/* 118:    */     
/* 119:207 */     return null == str ? "" : str;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int rtf()
/* 123:    */   {
/* 124:217 */     return this.m_dtmRoot;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public DTMIterator asNodeIterator()
/* 128:    */   {
/* 129:229 */     return new RTFIterator(this.m_dtmRoot, this.m_DTMXRTreeFrag.getXPathContext().getDTMManager());
/* 130:    */   }
/* 131:    */   
/* 132:    */   public NodeList convertToNodeset()
/* 133:    */   {
/* 134:240 */     if ((this.m_obj instanceof NodeList)) {
/* 135:241 */       return (NodeList)this.m_obj;
/* 136:    */     }
/* 137:243 */     return new DTMNodeList(asNodeIterator());
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean equals(XObject obj2)
/* 141:    */   {
/* 142:    */     try
/* 143:    */     {
/* 144:260 */       if (4 == obj2.getType()) {
/* 145:266 */         return obj2.equals(this);
/* 146:    */       }
/* 147:268 */       if (1 == obj2.getType()) {
/* 148:270 */         return bool() == obj2.bool();
/* 149:    */       }
/* 150:272 */       if (2 == obj2.getType()) {
/* 151:274 */         return num() == obj2.num();
/* 152:    */       }
/* 153:276 */       if (4 == obj2.getType()) {
/* 154:278 */         return xstr().equals(obj2.xstr());
/* 155:    */       }
/* 156:280 */       if (3 == obj2.getType()) {
/* 157:282 */         return xstr().equals(obj2.xstr());
/* 158:    */       }
/* 159:284 */       if (5 == obj2.getType()) {
/* 160:288 */         return xstr().equals(obj2.xstr());
/* 161:    */       }
/* 162:292 */       return super.equals(obj2);
/* 163:    */     }
/* 164:    */     catch (TransformerException te)
/* 165:    */     {
/* 166:297 */       throw new WrappedRuntimeException(te);
/* 167:    */     }
/* 168:    */   }
/* 169:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XRTreeFrag
 * JD-Core Version:    0.7.0.1
 */