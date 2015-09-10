/*   1:    */ package org.apache.xpath.functions;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.TransformerException;
/*   4:    */ import org.apache.xml.utils.XMLString;
/*   5:    */ import org.apache.xpath.Expression;
/*   6:    */ import org.apache.xpath.XPathContext;
/*   7:    */ import org.apache.xpath.objects.XObject;
/*   8:    */ import org.apache.xpath.objects.XString;
/*   9:    */ import org.apache.xpath.res.XPATHMessages;
/*  10:    */ 
/*  11:    */ public class FuncSubstring
/*  12:    */   extends Function3Args
/*  13:    */ {
/*  14:    */   static final long serialVersionUID = -5996676095024715502L;
/*  15:    */   
/*  16:    */   public XObject execute(XPathContext xctxt)
/*  17:    */     throws TransformerException
/*  18:    */   {
/*  19: 49 */     XMLString s1 = this.m_arg0.execute(xctxt).xstr();
/*  20: 50 */     double start = this.m_arg1.execute(xctxt).num();
/*  21: 51 */     int lenOfS1 = s1.length();
/*  22: 54 */     if (lenOfS1 <= 0) {
/*  23: 55 */       return XString.EMPTYSTRING;
/*  24:    */     }
/*  25:    */     int startIndex;
/*  26: 60 */     if (Double.isNaN(start))
/*  27:    */     {
/*  28: 65 */       start = -1000000.0D;
/*  29: 66 */       startIndex = 0;
/*  30:    */     }
/*  31:    */     else
/*  32:    */     {
/*  33: 70 */       start = Math.round(start);
/*  34: 71 */       startIndex = start > 0.0D ? (int)start - 1 : 0;
/*  35:    */     }
/*  36:    */     XMLString substr;
/*  37: 74 */     if (null != this.m_arg2)
/*  38:    */     {
/*  39: 76 */       double len = this.m_arg2.num(xctxt);
/*  40: 77 */       int end = (int)(Math.round(len) + start) - 1;
/*  41: 80 */       if (end < 0) {
/*  42: 81 */         end = 0;
/*  43: 82 */       } else if (end > lenOfS1) {
/*  44: 83 */         end = lenOfS1;
/*  45:    */       }
/*  46: 85 */       if (startIndex > lenOfS1) {
/*  47: 86 */         startIndex = lenOfS1;
/*  48:    */       }
/*  49: 88 */       substr = s1.substring(startIndex, end);
/*  50:    */     }
/*  51:    */     else
/*  52:    */     {
/*  53: 92 */       if (startIndex > lenOfS1) {
/*  54: 93 */         startIndex = lenOfS1;
/*  55:    */       }
/*  56: 94 */       substr = s1.substring(startIndex);
/*  57:    */     }
/*  58: 98 */     return (XString)substr;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void checkNumberArgs(int argNum)
/*  62:    */     throws WrongNumberArgsException
/*  63:    */   {
/*  64:111 */     if (argNum < 2) {
/*  65:112 */       reportWrongNumberArgs();
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected void reportWrongNumberArgs()
/*  70:    */     throws WrongNumberArgsException
/*  71:    */   {
/*  72:122 */     throw new WrongNumberArgsException(XPATHMessages.createXPATHMessage("ER_TWO_OR_THREE", null));
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncSubstring
 * JD-Core Version:    0.7.0.1
 */