/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import javax.xml.transform.ErrorListener;
/*   5:    */ import javax.xml.transform.SourceLocator;
/*   6:    */ import javax.xml.transform.TransformerException;
/*   7:    */ import org.apache.xalan.res.XSLMessages;
/*   8:    */ import org.w3c.dom.Node;
/*   9:    */ 
/*  10:    */ public class MsgMgr
/*  11:    */ {
/*  12:    */   private TransformerImpl m_transformer;
/*  13:    */   
/*  14:    */   public MsgMgr(TransformerImpl transformer)
/*  15:    */   {
/*  16: 45 */     this.m_transformer = transformer;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void message(SourceLocator srcLctr, String msg, boolean terminate)
/*  20:    */     throws TransformerException
/*  21:    */   {
/*  22: 65 */     ErrorListener errHandler = this.m_transformer.getErrorListener();
/*  23: 67 */     if (null != errHandler)
/*  24:    */     {
/*  25: 69 */       errHandler.warning(new TransformerException(msg, srcLctr));
/*  26:    */     }
/*  27:    */     else
/*  28:    */     {
/*  29: 73 */       if (terminate) {
/*  30: 74 */         throw new TransformerException(msg, srcLctr);
/*  31:    */       }
/*  32: 76 */       System.out.println(msg);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void warn(SourceLocator srcLctr, String msg)
/*  37:    */     throws TransformerException
/*  38:    */   {
/*  39: 92 */     warn(srcLctr, null, null, msg, null);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void warn(SourceLocator srcLctr, String msg, Object[] args)
/*  43:    */     throws TransformerException
/*  44:    */   {
/*  45:108 */     warn(srcLctr, null, null, msg, args);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void warn(SourceLocator srcLctr, Node styleNode, Node sourceNode, String msg)
/*  49:    */     throws TransformerException
/*  50:    */   {
/*  51:127 */     warn(srcLctr, styleNode, sourceNode, msg, null);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void warn(SourceLocator srcLctr, Node styleNode, Node sourceNode, String msg, Object[] args)
/*  55:    */     throws TransformerException
/*  56:    */   {
/*  57:147 */     String formattedMsg = XSLMessages.createWarning(msg, args);
/*  58:148 */     ErrorListener errHandler = this.m_transformer.getErrorListener();
/*  59:150 */     if (null != errHandler) {
/*  60:151 */       errHandler.warning(new TransformerException(formattedMsg, srcLctr));
/*  61:    */     } else {
/*  62:153 */       System.out.println(formattedMsg);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void error(SourceLocator srcLctr, String msg)
/*  67:    */     throws TransformerException
/*  68:    */   {
/*  69:197 */     error(srcLctr, null, null, msg, null);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void error(SourceLocator srcLctr, String msg, Object[] args)
/*  73:    */     throws TransformerException
/*  74:    */   {
/*  75:214 */     error(srcLctr, null, null, msg, args);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void error(SourceLocator srcLctr, String msg, Exception e)
/*  79:    */     throws TransformerException
/*  80:    */   {
/*  81:231 */     error(srcLctr, msg, null, e);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void error(SourceLocator srcLctr, String msg, Object[] args, Exception e)
/*  85:    */     throws TransformerException
/*  86:    */   {
/*  87:251 */     String formattedMsg = XSLMessages.createMessage(msg, args);
/*  88:    */     
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:257 */     ErrorListener errHandler = this.m_transformer.getErrorListener();
/*  94:259 */     if (null != errHandler) {
/*  95:260 */       errHandler.fatalError(new TransformerException(formattedMsg, srcLctr));
/*  96:    */     } else {
/*  97:262 */       throw new TransformerException(formattedMsg, srcLctr);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void error(SourceLocator srcLctr, Node styleNode, Node sourceNode, String msg)
/* 102:    */     throws TransformerException
/* 103:    */   {
/* 104:281 */     error(srcLctr, styleNode, sourceNode, msg, null);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void error(SourceLocator srcLctr, Node styleNode, Node sourceNode, String msg, Object[] args)
/* 108:    */     throws TransformerException
/* 109:    */   {
/* 110:302 */     String formattedMsg = XSLMessages.createMessage(msg, args);
/* 111:    */     
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:308 */     ErrorListener errHandler = this.m_transformer.getErrorListener();
/* 117:310 */     if (null != errHandler) {
/* 118:311 */       errHandler.fatalError(new TransformerException(formattedMsg, srcLctr));
/* 119:    */     } else {
/* 120:313 */       throw new TransformerException(formattedMsg, srcLctr);
/* 121:    */     }
/* 122:    */   }
/* 123:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.MsgMgr
 * JD-Core Version:    0.7.0.1
 */