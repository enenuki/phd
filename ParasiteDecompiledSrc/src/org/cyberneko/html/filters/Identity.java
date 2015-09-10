/*   1:    */ package org.cyberneko.html.filters;
/*   2:    */ 
/*   3:    */ import org.apache.xerces.xni.Augmentations;
/*   4:    */ import org.apache.xerces.xni.QName;
/*   5:    */ import org.apache.xerces.xni.XMLAttributes;
/*   6:    */ import org.apache.xerces.xni.XNIException;
/*   7:    */ import org.cyberneko.html.HTMLEventInfo;
/*   8:    */ 
/*   9:    */ public class Identity
/*  10:    */   extends DefaultFilter
/*  11:    */ {
/*  12:    */   protected static final String AUGMENTATIONS = "http://cyberneko.org/html/features/augmentations";
/*  13:    */   protected static final String FILTERS = "http://cyberneko.org/html/properties/filters";
/*  14:    */   
/*  15:    */   public void startElement(QName element, XMLAttributes attributes, Augmentations augs)
/*  16:    */     throws XNIException
/*  17:    */   {
/*  18: 72 */     if ((augs == null) || (!synthesized(augs))) {
/*  19: 73 */       super.startElement(element, attributes, augs);
/*  20:    */     }
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs)
/*  24:    */     throws XNIException
/*  25:    */   {
/*  26: 80 */     if ((augs == null) || (!synthesized(augs))) {
/*  27: 81 */       super.emptyElement(element, attributes, augs);
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void endElement(QName element, Augmentations augs)
/*  32:    */     throws XNIException
/*  33:    */   {
/*  34: 88 */     if ((augs == null) || (!synthesized(augs))) {
/*  35: 89 */       super.endElement(element, augs);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected static boolean synthesized(Augmentations augs)
/*  40:    */   {
/*  41: 99 */     HTMLEventInfo info = (HTMLEventInfo)augs.getItem("http://cyberneko.org/html/features/augmentations");
/*  42:100 */     return info != null ? info.isSynthesized() : false;
/*  43:    */   }
/*  44:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.filters.Identity
 * JD-Core Version:    0.7.0.1
 */