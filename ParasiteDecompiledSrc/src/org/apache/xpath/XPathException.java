/*   1:    */ package org.apache.xpath;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.PrintWriter;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import org.w3c.dom.Node;
/*   7:    */ 
/*   8:    */ public class XPathException
/*   9:    */   extends TransformerException
/*  10:    */ {
/*  11:    */   static final long serialVersionUID = 4263549717619045963L;
/*  12: 42 */   Object m_styleNode = null;
/*  13:    */   protected Exception m_exception;
/*  14:    */   
/*  15:    */   public Object getStylesheetNode()
/*  16:    */   {
/*  17: 50 */     return this.m_styleNode;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void setStylesheetNode(Object styleNode)
/*  21:    */   {
/*  22: 59 */     this.m_styleNode = styleNode;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public XPathException(String message, ExpressionNode ex)
/*  26:    */   {
/*  27: 74 */     super(message);
/*  28: 75 */     setLocator(ex);
/*  29: 76 */     setStylesheetNode(getStylesheetNode(ex));
/*  30:    */   }
/*  31:    */   
/*  32:    */   public XPathException(String message)
/*  33:    */   {
/*  34: 86 */     super(message);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Node getStylesheetNode(ExpressionNode ex)
/*  38:    */   {
/*  39: 98 */     ExpressionNode owner = getExpressionOwner(ex);
/*  40:100 */     if ((null != owner) && ((owner instanceof Node))) {
/*  41:102 */       return (Node)owner;
/*  42:    */     }
/*  43:104 */     return null;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected ExpressionNode getExpressionOwner(ExpressionNode ex)
/*  47:    */   {
/*  48:114 */     ExpressionNode parent = ex.exprGetParent();
/*  49:115 */     while ((null != parent) && ((parent instanceof Expression))) {
/*  50:116 */       parent = parent.exprGetParent();
/*  51:    */     }
/*  52:117 */     return parent;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public XPathException(String message, Object styleNode)
/*  56:    */   {
/*  57:132 */     super(message);
/*  58:    */     
/*  59:134 */     this.m_styleNode = styleNode;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public XPathException(String message, Node styleNode, Exception e)
/*  63:    */   {
/*  64:149 */     super(message);
/*  65:    */     
/*  66:151 */     this.m_styleNode = styleNode;
/*  67:152 */     this.m_exception = e;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public XPathException(String message, Exception e)
/*  71:    */   {
/*  72:165 */     super(message);
/*  73:    */     
/*  74:167 */     this.m_exception = e;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void printStackTrace(PrintStream s)
/*  78:    */   {
/*  79:179 */     if (s == null) {
/*  80:180 */       s = System.err;
/*  81:    */     }
/*  82:    */     try
/*  83:    */     {
/*  84:184 */       super.printStackTrace(s);
/*  85:    */     }
/*  86:    */     catch (Exception e) {}
/*  87:188 */     Throwable exception = this.m_exception;
/*  88:190 */     for (int i = 0; (i < 10) && (null != exception); i++)
/*  89:    */     {
/*  90:192 */       s.println("---------");
/*  91:193 */       exception.printStackTrace(s);
/*  92:195 */       if ((exception instanceof TransformerException))
/*  93:    */       {
/*  94:197 */         TransformerException se = (TransformerException)exception;
/*  95:198 */         Throwable prev = exception;
/*  96:    */         
/*  97:200 */         exception = se.getException();
/*  98:202 */         if (prev == exception) {
/*  99:    */           break;
/* 100:    */         }
/* 101:    */       }
/* 102:    */       else
/* 103:    */       {
/* 104:207 */         exception = null;
/* 105:    */       }
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getMessage()
/* 110:    */   {
/* 111:220 */     String lastMessage = super.getMessage();
/* 112:221 */     Throwable exception = this.m_exception;
/* 113:223 */     while (null != exception)
/* 114:    */     {
/* 115:225 */       String nextMessage = exception.getMessage();
/* 116:227 */       if (null != nextMessage) {
/* 117:228 */         lastMessage = nextMessage;
/* 118:    */       }
/* 119:230 */       if ((exception instanceof TransformerException))
/* 120:    */       {
/* 121:232 */         TransformerException se = (TransformerException)exception;
/* 122:233 */         Throwable prev = exception;
/* 123:    */         
/* 124:235 */         exception = se.getException();
/* 125:237 */         if (prev == exception) {
/* 126:    */           break;
/* 127:    */         }
/* 128:    */       }
/* 129:    */       else
/* 130:    */       {
/* 131:242 */         exception = null;
/* 132:    */       }
/* 133:    */     }
/* 134:246 */     return null != lastMessage ? lastMessage : "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void printStackTrace(PrintWriter s)
/* 138:    */   {
/* 139:258 */     if (s == null) {
/* 140:259 */       s = new PrintWriter(System.err);
/* 141:    */     }
/* 142:    */     try
/* 143:    */     {
/* 144:263 */       super.printStackTrace(s);
/* 145:    */     }
/* 146:    */     catch (Exception e) {}
/* 147:268 */     boolean isJdk14OrHigher = false;
/* 148:    */     try
/* 149:    */     {
/* 150:270 */       Throwable.class.getMethod("getCause", null);
/* 151:271 */       isJdk14OrHigher = true;
/* 152:    */     }
/* 153:    */     catch (NoSuchMethodException nsme) {}
/* 154:279 */     if (!isJdk14OrHigher)
/* 155:    */     {
/* 156:281 */       Throwable exception = this.m_exception;
/* 157:283 */       for (int i = 0; (i < 10) && (null != exception); i++)
/* 158:    */       {
/* 159:285 */         s.println("---------");
/* 160:    */         try
/* 161:    */         {
/* 162:289 */           exception.printStackTrace(s);
/* 163:    */         }
/* 164:    */         catch (Exception e)
/* 165:    */         {
/* 166:293 */           s.println("Could not print stack trace...");
/* 167:    */         }
/* 168:296 */         if ((exception instanceof TransformerException))
/* 169:    */         {
/* 170:298 */           TransformerException se = (TransformerException)exception;
/* 171:299 */           Throwable prev = exception;
/* 172:    */           
/* 173:301 */           exception = se.getException();
/* 174:303 */           if (prev == exception)
/* 175:    */           {
/* 176:305 */             exception = null;
/* 177:    */             
/* 178:307 */             break;
/* 179:    */           }
/* 180:    */         }
/* 181:    */         else
/* 182:    */         {
/* 183:312 */           exception = null;
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Throwable getException()
/* 190:    */   {
/* 191:326 */     return this.m_exception;
/* 192:    */   }
/* 193:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.XPathException
 * JD-Core Version:    0.7.0.1
 */