/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import org.apache.xml.serializer.Serializer;
/*   5:    */ import org.apache.xml.utils.MutableAttrListImpl;
/*   6:    */ import org.xml.sax.helpers.AttributesImpl;
/*   7:    */ 
/*   8:    */ public abstract class QueuedEvents
/*   9:    */ {
/*  10: 37 */   protected int m_eventCount = 0;
/*  11: 45 */   public boolean m_docPending = false;
/*  12: 46 */   protected boolean m_docEnded = false;
/*  13: 50 */   public boolean m_elemIsPending = false;
/*  14: 53 */   public boolean m_elemIsEnded = false;
/*  15: 62 */   protected MutableAttrListImpl m_attributes = new MutableAttrListImpl();
/*  16: 68 */   protected boolean m_nsDeclsHaveBeenAdded = false;
/*  17:    */   protected String m_name;
/*  18:    */   protected String m_url;
/*  19:    */   protected String m_localName;
/*  20: 83 */   protected Vector m_namespaces = null;
/*  21:    */   private Serializer m_serializer;
/*  22:    */   
/*  23:    */   protected void reInitEvents() {}
/*  24:    */   
/*  25:    */   public void reset()
/*  26:    */   {
/*  27:109 */     pushDocumentEvent();
/*  28:110 */     reInitEvents();
/*  29:    */   }
/*  30:    */   
/*  31:    */   void pushDocumentEvent()
/*  32:    */   {
/*  33:121 */     this.m_docPending = true;
/*  34:    */     
/*  35:123 */     this.m_eventCount += 1;
/*  36:    */   }
/*  37:    */   
/*  38:    */   void popEvent()
/*  39:    */   {
/*  40:132 */     this.m_elemIsPending = false;
/*  41:133 */     this.m_attributes.clear();
/*  42:    */     
/*  43:135 */     this.m_nsDeclsHaveBeenAdded = false;
/*  44:136 */     this.m_name = null;
/*  45:137 */     this.m_url = null;
/*  46:138 */     this.m_localName = null;
/*  47:139 */     this.m_namespaces = null;
/*  48:    */     
/*  49:141 */     this.m_eventCount -= 1;
/*  50:    */   }
/*  51:    */   
/*  52:    */   void setSerializer(Serializer s)
/*  53:    */   {
/*  54:155 */     this.m_serializer = s;
/*  55:    */   }
/*  56:    */   
/*  57:    */   Serializer getSerializer()
/*  58:    */   {
/*  59:166 */     return this.m_serializer;
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.QueuedEvents
 * JD-Core Version:    0.7.0.1
 */