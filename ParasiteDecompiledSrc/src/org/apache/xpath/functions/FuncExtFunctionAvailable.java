/*   1:    */ package org.apache.xpath.functions;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.utils.PrefixResolver;
/*   5:    */ import org.apache.xpath.Expression;
/*   6:    */ import org.apache.xpath.ExtensionsProvider;
/*   7:    */ import org.apache.xpath.XPathContext;
/*   8:    */ import org.apache.xpath.compiler.FunctionTable;
/*   9:    */ import org.apache.xpath.objects.XBoolean;
/*  10:    */ import org.apache.xpath.objects.XObject;
/*  11:    */ 
/*  12:    */ public class FuncExtFunctionAvailable
/*  13:    */   extends FunctionOneArg
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = 5118814314918592241L;
/*  16: 38 */   private transient FunctionTable m_functionTable = null;
/*  17:    */   
/*  18:    */   public XObject execute(XPathContext xctxt)
/*  19:    */     throws TransformerException
/*  20:    */   {
/*  21: 55 */     String fullName = this.m_arg0.execute(xctxt).str();
/*  22: 56 */     int indexOfNSSep = fullName.indexOf(':');
/*  23:    */     String prefix;
/*  24:    */     String namespace;
/*  25:    */     String methName;
/*  26: 58 */     if (indexOfNSSep < 0)
/*  27:    */     {
/*  28: 60 */       prefix = "";
/*  29: 61 */       namespace = "http://www.w3.org/1999/XSL/Transform";
/*  30: 62 */       methName = fullName;
/*  31:    */     }
/*  32:    */     else
/*  33:    */     {
/*  34: 66 */       prefix = fullName.substring(0, indexOfNSSep);
/*  35: 67 */       namespace = xctxt.getNamespaceContext().getNamespaceForPrefix(prefix);
/*  36: 68 */       if (null == namespace) {
/*  37: 69 */         return XBoolean.S_FALSE;
/*  38:    */       }
/*  39: 70 */       methName = fullName.substring(indexOfNSSep + 1);
/*  40:    */     }
/*  41: 73 */     if (namespace.equals("http://www.w3.org/1999/XSL/Transform")) {
/*  42:    */       try
/*  43:    */       {
/*  44: 77 */         if (null == this.m_functionTable) {
/*  45: 77 */           this.m_functionTable = new FunctionTable();
/*  46:    */         }
/*  47: 78 */         return this.m_functionTable.functionAvailable(methName) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/*  48:    */       }
/*  49:    */       catch (Exception e)
/*  50:    */       {
/*  51: 82 */         return XBoolean.S_FALSE;
/*  52:    */       }
/*  53:    */     }
/*  54: 88 */     ExtensionsProvider extProvider = (ExtensionsProvider)xctxt.getOwnerObject();
/*  55: 89 */     return extProvider.functionAvailable(namespace, methName) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setFunctionTable(FunctionTable aTable)
/*  59:    */   {
/*  60:102 */     this.m_functionTable = aTable;
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncExtFunctionAvailable
 * JD-Core Version:    0.7.0.1
 */