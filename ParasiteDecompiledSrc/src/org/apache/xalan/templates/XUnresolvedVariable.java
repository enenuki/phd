/*   1:    */ package org.apache.xalan.templates;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xalan.transformer.MsgMgr;
/*   5:    */ import org.apache.xalan.transformer.TransformerImpl;
/*   6:    */ import org.apache.xml.utils.QName;
/*   7:    */ import org.apache.xpath.VariableStack;
/*   8:    */ import org.apache.xpath.XPathContext;
/*   9:    */ import org.apache.xpath.objects.XObject;
/*  10:    */ 
/*  11:    */ public class XUnresolvedVariable
/*  12:    */   extends XObject
/*  13:    */ {
/*  14:    */   static final long serialVersionUID = -256779804767950188L;
/*  15:    */   private transient int m_context;
/*  16:    */   private transient TransformerImpl m_transformer;
/*  17: 47 */   private transient int m_varStackPos = -1;
/*  18:    */   private transient int m_varStackContext;
/*  19:    */   private boolean m_isGlobal;
/*  20: 59 */   private transient boolean m_doneEval = true;
/*  21:    */   
/*  22:    */   public XUnresolvedVariable(ElemVariable obj, int sourceNode, TransformerImpl transformer, int varStackPos, int varStackContext, boolean isGlobal)
/*  23:    */   {
/*  24: 82 */     super(obj);
/*  25: 83 */     this.m_context = sourceNode;
/*  26: 84 */     this.m_transformer = transformer;
/*  27:    */     
/*  28:    */ 
/*  29:    */ 
/*  30: 88 */     this.m_varStackPos = varStackPos;
/*  31:    */     
/*  32:    */ 
/*  33: 91 */     this.m_varStackContext = varStackContext;
/*  34:    */     
/*  35: 93 */     this.m_isGlobal = isGlobal;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public XObject execute(XPathContext xctxt)
/*  39:    */     throws TransformerException
/*  40:    */   {
/*  41:107 */     if (!this.m_doneEval) {
/*  42:109 */       this.m_transformer.getMsgMgr().error(xctxt.getSAXLocator(), "ER_REFERENCING_ITSELF", new Object[] { ((ElemVariable)object()).getName().getLocalName() });
/*  43:    */     }
/*  44:113 */     VariableStack vars = xctxt.getVarStack();
/*  45:    */     
/*  46:    */ 
/*  47:116 */     int currentFrame = vars.getStackFrame();
/*  48:    */     
/*  49:    */ 
/*  50:    */ 
/*  51:120 */     ElemVariable velem = (ElemVariable)this.m_obj;
/*  52:    */     try
/*  53:    */     {
/*  54:123 */       this.m_doneEval = false;
/*  55:124 */       if (-1 != velem.m_frameSize) {
/*  56:125 */         vars.link(velem.m_frameSize);
/*  57:    */       }
/*  58:126 */       XObject var = velem.getValue(this.m_transformer, this.m_context);
/*  59:127 */       this.m_doneEval = true;
/*  60:128 */       return var;
/*  61:    */     }
/*  62:    */     finally
/*  63:    */     {
/*  64:135 */       if (-1 != velem.m_frameSize) {
/*  65:136 */         vars.unlink(currentFrame);
/*  66:    */       }
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setVarStackPos(int top)
/*  71:    */   {
/*  72:150 */     this.m_varStackPos = top;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setVarStackContext(int bottom)
/*  76:    */   {
/*  77:162 */     this.m_varStackContext = bottom;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getType()
/*  81:    */   {
/*  82:172 */     return 600;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getTypeString()
/*  86:    */   {
/*  87:183 */     return "XUnresolvedVariable (" + object().getClass().getName() + ")";
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.XUnresolvedVariable
 * JD-Core Version:    0.7.0.1
 */