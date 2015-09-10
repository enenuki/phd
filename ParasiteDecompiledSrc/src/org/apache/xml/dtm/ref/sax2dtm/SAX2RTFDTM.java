/*   1:    */ package org.apache.xml.dtm.ref.sax2dtm;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.Source;
/*   5:    */ import org.apache.xml.dtm.DTMManager;
/*   6:    */ import org.apache.xml.dtm.DTMWSFilter;
/*   7:    */ import org.apache.xml.dtm.ref.DTMDefaultBase;
/*   8:    */ import org.apache.xml.utils.FastStringBuffer;
/*   9:    */ import org.apache.xml.utils.IntStack;
/*  10:    */ import org.apache.xml.utils.IntVector;
/*  11:    */ import org.apache.xml.utils.StringVector;
/*  12:    */ import org.apache.xml.utils.SuballocatedIntVector;
/*  13:    */ import org.apache.xml.utils.XMLStringFactory;
/*  14:    */ import org.xml.sax.SAXException;
/*  15:    */ 
/*  16:    */ public class SAX2RTFDTM
/*  17:    */   extends SAX2DTM
/*  18:    */ {
/*  19:    */   private static final boolean DEBUG = false;
/*  20: 68 */   private int m_currentDocumentNode = -1;
/*  21: 71 */   IntStack mark_size = new IntStack();
/*  22: 73 */   IntStack mark_data_size = new IntStack();
/*  23: 75 */   IntStack mark_char_size = new IntStack();
/*  24: 77 */   IntStack mark_doq_size = new IntStack();
/*  25: 83 */   IntStack mark_nsdeclset_size = new IntStack();
/*  26: 89 */   IntStack mark_nsdeclelem_size = new IntStack();
/*  27:    */   int m_emptyNodeCount;
/*  28:    */   int m_emptyNSDeclSetCount;
/*  29:    */   int m_emptyNSDeclSetElemsCount;
/*  30:    */   int m_emptyDataCount;
/*  31:    */   int m_emptyCharsCount;
/*  32:    */   int m_emptyDataQNCount;
/*  33:    */   
/*  34:    */   public SAX2RTFDTM(DTMManager mgr, Source source, int dtmIdentity, DTMWSFilter whiteSpaceFilter, XMLStringFactory xstringfactory, boolean doIndexing)
/*  35:    */   {
/*  36:126 */     super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory, doIndexing);
/*  37:    */     
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:132 */     this.m_useSourceLocationProperty = false;
/*  43:133 */     this.m_sourceSystemId = (this.m_useSourceLocationProperty ? new StringVector() : null);
/*  44:    */     
/*  45:135 */     this.m_sourceLine = (this.m_useSourceLocationProperty ? new IntVector() : null);
/*  46:136 */     this.m_sourceColumn = (this.m_useSourceLocationProperty ? new IntVector() : null);
/*  47:    */     
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:141 */     this.m_emptyNodeCount = this.m_size;
/*  52:142 */     this.m_emptyNSDeclSetCount = (this.m_namespaceDeclSets == null ? 0 : this.m_namespaceDeclSets.size());
/*  53:    */     
/*  54:144 */     this.m_emptyNSDeclSetElemsCount = (this.m_namespaceDeclSetElements == null ? 0 : this.m_namespaceDeclSetElements.size());
/*  55:    */     
/*  56:146 */     this.m_emptyDataCount = this.m_data.size();
/*  57:147 */     this.m_emptyCharsCount = this.m_chars.size();
/*  58:148 */     this.m_emptyDataQNCount = this.m_dataOrQName.size();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getDocument()
/*  62:    */   {
/*  63:166 */     return makeNodeHandle(this.m_currentDocumentNode);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getDocumentRoot(int nodeHandle)
/*  67:    */   {
/*  68:182 */     for (int id = makeNodeIdentity(nodeHandle); id != -1; id = _parent(id)) {
/*  69:183 */       if (_type(id) == 9) {
/*  70:184 */         return makeNodeHandle(id);
/*  71:    */       }
/*  72:    */     }
/*  73:188 */     return -1;
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected int _documentRoot(int nodeIdentifier)
/*  77:    */   {
/*  78:201 */     if (nodeIdentifier == -1) {
/*  79:201 */       return -1;
/*  80:    */     }
/*  81:203 */     for (int parent = _parent(nodeIdentifier); parent != -1; parent = _parent(nodeIdentifier)) {
/*  82:205 */       nodeIdentifier = parent;
/*  83:    */     }
/*  84:208 */     return nodeIdentifier;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void startDocument()
/*  88:    */     throws SAXException
/*  89:    */   {
/*  90:226 */     this.m_endDocumentOccured = false;
/*  91:227 */     this.m_prefixMappings = new Vector();
/*  92:228 */     this.m_contextIndexes = new IntStack();
/*  93:229 */     this.m_parents = new IntStack();
/*  94:    */     
/*  95:231 */     this.m_currentDocumentNode = this.m_size;
/*  96:232 */     super.startDocument();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void endDocument()
/* 100:    */     throws SAXException
/* 101:    */   {
/* 102:249 */     charactersFlush();
/* 103:    */     
/* 104:251 */     this.m_nextsib.setElementAt(-1, this.m_currentDocumentNode);
/* 105:253 */     if (this.m_firstch.elementAt(this.m_currentDocumentNode) == -2) {
/* 106:254 */       this.m_firstch.setElementAt(-1, this.m_currentDocumentNode);
/* 107:    */     }
/* 108:256 */     if (-1 != this.m_previous) {
/* 109:257 */       this.m_nextsib.setElementAt(-1, this.m_previous);
/* 110:    */     }
/* 111:259 */     this.m_parents = null;
/* 112:260 */     this.m_prefixMappings = null;
/* 113:261 */     this.m_contextIndexes = null;
/* 114:    */     
/* 115:263 */     this.m_currentDocumentNode = -1;
/* 116:264 */     this.m_endDocumentOccured = true;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void pushRewindMark()
/* 120:    */   {
/* 121:280 */     if ((this.m_indexing) || (this.m_elemIndexes != null)) {
/* 122:281 */       throw new NullPointerException("Coding error; Don't try to mark/rewind an indexed DTM");
/* 123:    */     }
/* 124:285 */     this.mark_size.push(this.m_size);
/* 125:286 */     this.mark_nsdeclset_size.push(this.m_namespaceDeclSets == null ? 0 : this.m_namespaceDeclSets.size());
/* 126:    */     
/* 127:    */ 
/* 128:289 */     this.mark_nsdeclelem_size.push(this.m_namespaceDeclSetElements == null ? 0 : this.m_namespaceDeclSetElements.size());
/* 129:    */     
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:294 */     this.mark_data_size.push(this.m_data.size());
/* 134:295 */     this.mark_char_size.push(this.m_chars.size());
/* 135:296 */     this.mark_doq_size.push(this.m_dataOrQName.size());
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean popRewindMark()
/* 139:    */   {
/* 140:326 */     boolean top = this.mark_size.empty();
/* 141:    */     
/* 142:328 */     this.m_size = (top ? this.m_emptyNodeCount : this.mark_size.pop());
/* 143:329 */     this.m_exptype.setSize(this.m_size);
/* 144:330 */     this.m_firstch.setSize(this.m_size);
/* 145:331 */     this.m_nextsib.setSize(this.m_size);
/* 146:332 */     this.m_prevsib.setSize(this.m_size);
/* 147:333 */     this.m_parent.setSize(this.m_size);
/* 148:    */     
/* 149:335 */     this.m_elemIndexes = null;
/* 150:    */     
/* 151:337 */     int ds = top ? this.m_emptyNSDeclSetCount : this.mark_nsdeclset_size.pop();
/* 152:338 */     if (this.m_namespaceDeclSets != null) {
/* 153:339 */       this.m_namespaceDeclSets.setSize(ds);
/* 154:    */     }
/* 155:342 */     int ds1 = top ? this.m_emptyNSDeclSetElemsCount : this.mark_nsdeclelem_size.pop();
/* 156:343 */     if (this.m_namespaceDeclSetElements != null) {
/* 157:344 */       this.m_namespaceDeclSetElements.setSize(ds1);
/* 158:    */     }
/* 159:348 */     this.m_data.setSize(top ? this.m_emptyDataCount : this.mark_data_size.pop());
/* 160:349 */     this.m_chars.setLength(top ? this.m_emptyCharsCount : this.mark_char_size.pop());
/* 161:350 */     this.m_dataOrQName.setSize(top ? this.m_emptyDataQNCount : this.mark_doq_size.pop());
/* 162:    */     
/* 163:    */ 
/* 164:353 */     return this.m_size == 0;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean isTreeIncomplete()
/* 168:    */   {
/* 169:360 */     return !this.m_endDocumentOccured;
/* 170:    */   }
/* 171:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.sax2dtm.SAX2RTFDTM
 * JD-Core Version:    0.7.0.1
 */