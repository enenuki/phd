/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ final class ElemContext
/*   4:    */ {
/*   5:    */   final int m_currentElemDepth;
/*   6: 63 */   ElemDesc m_elementDesc = null;
/*   7: 68 */   String m_elementLocalName = null;
/*   8: 73 */   String m_elementName = null;
/*   9: 81 */   String m_elementURI = null;
/*  10:    */   boolean m_isCdataSection;
/*  11: 92 */   boolean m_isRaw = false;
/*  12:    */   private ElemContext m_next;
/*  13:    */   final ElemContext m_prev;
/*  14:115 */   boolean m_startTagOpen = false;
/*  15:    */   
/*  16:    */   ElemContext()
/*  17:    */   {
/*  18:124 */     this.m_prev = this;
/*  19:    */     
/*  20:126 */     this.m_currentElemDepth = 0;
/*  21:    */   }
/*  22:    */   
/*  23:    */   private ElemContext(ElemContext previous)
/*  24:    */   {
/*  25:141 */     this.m_prev = previous;
/*  26:142 */     previous.m_currentElemDepth += 1;
/*  27:    */   }
/*  28:    */   
/*  29:    */   final ElemContext pop()
/*  30:    */   {
/*  31:155 */     return this.m_prev;
/*  32:    */   }
/*  33:    */   
/*  34:    */   final ElemContext push()
/*  35:    */   {
/*  36:167 */     ElemContext frame = this.m_next;
/*  37:168 */     if (frame == null)
/*  38:    */     {
/*  39:173 */       frame = new ElemContext(this);
/*  40:174 */       this.m_next = frame;
/*  41:    */     }
/*  42:182 */     frame.m_startTagOpen = true;
/*  43:183 */     return frame;
/*  44:    */   }
/*  45:    */   
/*  46:    */   final ElemContext push(String uri, String localName, String qName)
/*  47:    */   {
/*  48:201 */     ElemContext frame = this.m_next;
/*  49:202 */     if (frame == null)
/*  50:    */     {
/*  51:207 */       frame = new ElemContext(this);
/*  52:208 */       this.m_next = frame;
/*  53:    */     }
/*  54:212 */     frame.m_elementName = qName;
/*  55:213 */     frame.m_elementLocalName = localName;
/*  56:214 */     frame.m_elementURI = uri;
/*  57:215 */     frame.m_isCdataSection = false;
/*  58:216 */     frame.m_startTagOpen = true;
/*  59:    */     
/*  60:    */ 
/*  61:    */ 
/*  62:220 */     return frame;
/*  63:    */   }
/*  64:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ElemContext
 * JD-Core Version:    0.7.0.1
 */