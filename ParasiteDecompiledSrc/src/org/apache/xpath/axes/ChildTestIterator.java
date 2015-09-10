/*   1:    */ package org.apache.xpath.axes;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.dtm.DTM;
/*   5:    */ import org.apache.xml.dtm.DTMAxisTraverser;
/*   6:    */ import org.apache.xml.dtm.DTMIterator;
/*   7:    */ import org.apache.xpath.compiler.Compiler;
/*   8:    */ 
/*   9:    */ public class ChildTestIterator
/*  10:    */   extends BasicTestIterator
/*  11:    */ {
/*  12:    */   static final long serialVersionUID = -7936835957960705722L;
/*  13:    */   protected transient DTMAxisTraverser m_traverser;
/*  14:    */   
/*  15:    */   ChildTestIterator(Compiler compiler, int opPos, int analysis)
/*  16:    */     throws TransformerException
/*  17:    */   {
/*  18: 57 */     super(compiler, opPos, analysis);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public ChildTestIterator(DTMAxisTraverser traverser)
/*  22:    */   {
/*  23: 70 */     super(null);
/*  24:    */     
/*  25: 72 */     this.m_traverser = traverser;
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected int getNextNode()
/*  29:    */   {
/*  30: 83 */     this.m_lastFetched = (-1 == this.m_lastFetched ? this.m_traverser.first(this.m_context) : this.m_traverser.next(this.m_context, this.m_lastFetched));
/*  31:    */     
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42: 95 */     return this.m_lastFetched;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public DTMIterator cloneWithReset()
/*  46:    */     throws CloneNotSupportedException
/*  47:    */   {
/*  48:110 */     ChildTestIterator clone = (ChildTestIterator)super.cloneWithReset();
/*  49:111 */     clone.m_traverser = this.m_traverser;
/*  50:    */     
/*  51:113 */     return clone;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setRoot(int context, Object environment)
/*  55:    */   {
/*  56:126 */     super.setRoot(context, environment);
/*  57:127 */     this.m_traverser = this.m_cdtm.getAxisTraverser(3);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getAxis()
/*  61:    */   {
/*  62:157 */     return 3;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void detach()
/*  66:    */   {
/*  67:169 */     if (this.m_allowDetach)
/*  68:    */     {
/*  69:171 */       this.m_traverser = null;
/*  70:    */       
/*  71:    */ 
/*  72:174 */       super.detach();
/*  73:    */     }
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.ChildTestIterator
 * JD-Core Version:    0.7.0.1
 */