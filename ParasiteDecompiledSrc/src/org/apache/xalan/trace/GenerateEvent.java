/*   1:    */ package org.apache.xalan.trace;
/*   2:    */ 
/*   3:    */ import java.util.EventListener;
/*   4:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   5:    */ import org.xml.sax.Attributes;
/*   6:    */ 
/*   7:    */ public class GenerateEvent
/*   8:    */   implements EventListener
/*   9:    */ {
/*  10:    */   public TransformerImpl m_processor;
/*  11:    */   public int m_eventtype;
/*  12:    */   public char[] m_characters;
/*  13:    */   public int m_start;
/*  14:    */   public int m_length;
/*  15:    */   public String m_name;
/*  16:    */   public String m_data;
/*  17:    */   public Attributes m_atts;
/*  18:    */   
/*  19:    */   public GenerateEvent(TransformerImpl processor, int eventType)
/*  20:    */   {
/*  21: 92 */     this.m_processor = processor;
/*  22: 93 */     this.m_eventtype = eventType;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public GenerateEvent(TransformerImpl processor, int eventType, String name, Attributes atts)
/*  26:    */   {
/*  27:108 */     this.m_name = name;
/*  28:109 */     this.m_atts = atts;
/*  29:110 */     this.m_processor = processor;
/*  30:111 */     this.m_eventtype = eventType;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public GenerateEvent(TransformerImpl processor, int eventType, char[] ch, int start, int length)
/*  34:    */   {
/*  35:127 */     this.m_characters = ch;
/*  36:128 */     this.m_start = start;
/*  37:129 */     this.m_length = length;
/*  38:130 */     this.m_processor = processor;
/*  39:131 */     this.m_eventtype = eventType;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public GenerateEvent(TransformerImpl processor, int eventType, String name, String data)
/*  43:    */   {
/*  44:146 */     this.m_name = name;
/*  45:147 */     this.m_data = data;
/*  46:148 */     this.m_processor = processor;
/*  47:149 */     this.m_eventtype = eventType;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public GenerateEvent(TransformerImpl processor, int eventType, String data)
/*  51:    */   {
/*  52:162 */     this.m_data = data;
/*  53:163 */     this.m_processor = processor;
/*  54:164 */     this.m_eventtype = eventType;
/*  55:    */   }
/*  56:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.trace.GenerateEvent
 * JD-Core Version:    0.7.0.1
 */