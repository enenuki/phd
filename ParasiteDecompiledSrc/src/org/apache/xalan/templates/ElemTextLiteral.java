/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.trace.TraceManager;
/*   5:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   6:    */ import org.apache.xml.serializer.SerializationHandler;
/*   7:    */ import org.xml.sax.ContentHandler;
/*   8:    */ import org.xml.sax.SAXException;
/*   9:    */ 
/*  10:    */ public class ElemTextLiteral
/*  11:    */   extends ElemTemplateElement
/*  12:    */ {
/*  13:    */   static final long serialVersionUID = -7872620006767660088L;
/*  14:    */   private boolean m_preserveSpace;
/*  15:    */   private char[] m_ch;
/*  16:    */   private String m_str;
/*  17:    */   
/*  18:    */   public void setPreserveSpace(boolean v)
/*  19:    */   {
/*  20: 52 */     this.m_preserveSpace = v;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean getPreserveSpace()
/*  24:    */   {
/*  25: 63 */     return this.m_preserveSpace;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setChars(char[] v)
/*  29:    */   {
/*  30: 85 */     this.m_ch = v;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public char[] getChars()
/*  34:    */   {
/*  35: 95 */     return this.m_ch;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public synchronized String getNodeValue()
/*  39:    */   {
/*  40:106 */     if (null == this.m_str) {
/*  41:108 */       this.m_str = new String(this.m_ch);
/*  42:    */     }
/*  43:111 */     return this.m_str;
/*  44:    */   }
/*  45:    */   
/*  46:119 */   private boolean m_disableOutputEscaping = false;
/*  47:    */   
/*  48:    */   public void setDisableOutputEscaping(boolean v)
/*  49:    */   {
/*  50:143 */     this.m_disableOutputEscaping = v;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean getDisableOutputEscaping()
/*  54:    */   {
/*  55:168 */     return this.m_disableOutputEscaping;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getXSLToken()
/*  59:    */   {
/*  60:180 */     return 78;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getNodeName()
/*  64:    */   {
/*  65:190 */     return "#Text";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void execute(TransformerImpl transformer)
/*  69:    */     throws TransformerException
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73:206 */       SerializationHandler rth = transformer.getResultTreeHandler();
/*  74:207 */       if (transformer.getDebug())
/*  75:    */       {
/*  76:209 */         rth.flushPending();
/*  77:210 */         transformer.getTraceManager().fireTraceEvent(this);
/*  78:    */       }
/*  79:213 */       if (this.m_disableOutputEscaping) {
/*  80:215 */         rth.processingInstruction("javax.xml.transform.disable-output-escaping", "");
/*  81:    */       }
/*  82:218 */       rth.characters(this.m_ch, 0, this.m_ch.length);
/*  83:220 */       if (this.m_disableOutputEscaping) {
/*  84:222 */         rth.processingInstruction("javax.xml.transform.enable-output-escaping", "");
/*  85:    */       }
/*  86:    */     }
/*  87:    */     catch (SAXException se)
/*  88:    */     {
/*  89:227 */       throw new TransformerException(se);
/*  90:    */     }
/*  91:    */     finally
/*  92:    */     {
/*  93:231 */       if (transformer.getDebug()) {
/*  94:    */         try
/*  95:    */         {
/*  96:235 */           transformer.getResultTreeHandler().flushPending();
/*  97:236 */           transformer.getTraceManager().fireTraceEndEvent(this);
/*  98:    */         }
/*  99:    */         catch (SAXException se)
/* 100:    */         {
/* 101:240 */           throw new TransformerException(se);
/* 102:    */         }
/* 103:    */       }
/* 104:    */     }
/* 105:    */   }
/* 106:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemTextLiteral
 * JD-Core Version:    0.7.0.1
 */