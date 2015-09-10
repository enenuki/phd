/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.DOM;
/*   4:    */ import org.apache.xalan.xsltc.DOMEnhancedForDTM;
/*   5:    */ import org.apache.xalan.xsltc.StripFilter;
/*   6:    */ import org.apache.xalan.xsltc.runtime.AbstractTranslet;
/*   7:    */ import org.apache.xalan.xsltc.runtime.Hashtable;
/*   8:    */ import org.apache.xml.dtm.DTM;
/*   9:    */ import org.apache.xml.dtm.DTMWSFilter;
/*  10:    */ 
/*  11:    */ public class DOMWSFilter
/*  12:    */   implements DTMWSFilter
/*  13:    */ {
/*  14:    */   private AbstractTranslet m_translet;
/*  15:    */   private StripFilter m_filter;
/*  16:    */   private Hashtable m_mappings;
/*  17:    */   private DTM m_currentDTM;
/*  18:    */   private short[] m_currentMapping;
/*  19:    */   
/*  20:    */   public DOMWSFilter(AbstractTranslet translet)
/*  21:    */   {
/*  22: 59 */     this.m_translet = translet;
/*  23: 60 */     this.m_mappings = new Hashtable();
/*  24: 62 */     if ((translet instanceof StripFilter)) {
/*  25: 63 */       this.m_filter = ((StripFilter)translet);
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public short getShouldStripSpace(int node, DTM dtm)
/*  30:    */   {
/*  31: 80 */     if ((this.m_filter != null) && ((dtm instanceof DOM)))
/*  32:    */     {
/*  33: 81 */       DOM dom = (DOM)dtm;
/*  34: 82 */       int type = 0;
/*  35: 84 */       if ((dtm instanceof DOMEnhancedForDTM))
/*  36:    */       {
/*  37: 85 */         DOMEnhancedForDTM mappableDOM = (DOMEnhancedForDTM)dtm;
/*  38:    */         short[] mapping;
/*  39: 88 */         if (dtm == this.m_currentDTM)
/*  40:    */         {
/*  41: 89 */           mapping = this.m_currentMapping;
/*  42:    */         }
/*  43:    */         else
/*  44:    */         {
/*  45: 92 */           mapping = (short[])this.m_mappings.get(dtm);
/*  46: 93 */           if (mapping == null)
/*  47:    */           {
/*  48: 94 */             mapping = mappableDOM.getMapping(this.m_translet.getNamesArray(), this.m_translet.getUrisArray(), this.m_translet.getTypesArray());
/*  49:    */             
/*  50:    */ 
/*  51:    */ 
/*  52: 98 */             this.m_mappings.put(dtm, mapping);
/*  53: 99 */             this.m_currentDTM = dtm;
/*  54:100 */             this.m_currentMapping = mapping;
/*  55:    */           }
/*  56:    */         }
/*  57:104 */         int expType = mappableDOM.getExpandedTypeID(node);
/*  58:111 */         if ((expType >= 0) && (expType < mapping.length)) {
/*  59:112 */           type = mapping[expType];
/*  60:    */         } else {
/*  61:114 */           type = -1;
/*  62:    */         }
/*  63:    */       }
/*  64:    */       else
/*  65:    */       {
/*  66:118 */         return 3;
/*  67:    */       }
/*  68:121 */       if (this.m_filter.stripSpace(dom, node, type)) {
/*  69:122 */         return 2;
/*  70:    */       }
/*  71:124 */       return 1;
/*  72:    */     }
/*  73:127 */     return 1;
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.DOMWSFilter
 * JD-Core Version:    0.7.0.1
 */