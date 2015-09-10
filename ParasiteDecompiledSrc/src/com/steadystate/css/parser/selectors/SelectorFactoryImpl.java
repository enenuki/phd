/*   1:    */ package com.steadystate.css.parser.selectors;
/*   2:    */ 
/*   3:    */ import org.w3c.css.sac.CSSException;
/*   4:    */ import org.w3c.css.sac.CharacterDataSelector;
/*   5:    */ import org.w3c.css.sac.Condition;
/*   6:    */ import org.w3c.css.sac.ConditionalSelector;
/*   7:    */ import org.w3c.css.sac.DescendantSelector;
/*   8:    */ import org.w3c.css.sac.ElementSelector;
/*   9:    */ import org.w3c.css.sac.NegativeSelector;
/*  10:    */ import org.w3c.css.sac.ProcessingInstructionSelector;
/*  11:    */ import org.w3c.css.sac.Selector;
/*  12:    */ import org.w3c.css.sac.SelectorFactory;
/*  13:    */ import org.w3c.css.sac.SiblingSelector;
/*  14:    */ import org.w3c.css.sac.SimpleSelector;
/*  15:    */ 
/*  16:    */ public class SelectorFactoryImpl
/*  17:    */   implements SelectorFactory
/*  18:    */ {
/*  19:    */   public ConditionalSelector createConditionalSelector(SimpleSelector selector, Condition condition)
/*  20:    */     throws CSSException
/*  21:    */   {
/*  22: 42 */     return new ConditionalSelectorImpl(selector, condition);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public SimpleSelector createAnyNodeSelector()
/*  26:    */     throws CSSException
/*  27:    */   {
/*  28: 46 */     throw new CSSException((short)1);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public SimpleSelector createRootNodeSelector()
/*  32:    */     throws CSSException
/*  33:    */   {
/*  34: 50 */     throw new CSSException((short)1);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public NegativeSelector createNegativeSelector(SimpleSelector selector)
/*  38:    */     throws CSSException
/*  39:    */   {
/*  40: 55 */     throw new CSSException((short)1);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public ElementSelector createElementSelector(String namespaceURI, String localName)
/*  44:    */     throws CSSException
/*  45:    */   {
/*  46: 60 */     if (namespaceURI != null) {
/*  47: 61 */       throw new CSSException((short)1);
/*  48:    */     }
/*  49: 63 */     return new ElementSelectorImpl(localName);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public CharacterDataSelector createTextNodeSelector(String data)
/*  53:    */     throws CSSException
/*  54:    */   {
/*  55: 68 */     throw new CSSException((short)1);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public CharacterDataSelector createCDataSectionSelector(String data)
/*  59:    */     throws CSSException
/*  60:    */   {
/*  61: 73 */     throw new CSSException((short)1);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public ProcessingInstructionSelector createProcessingInstructionSelector(String target, String data)
/*  65:    */     throws CSSException
/*  66:    */   {
/*  67: 79 */     throw new CSSException((short)1);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public CharacterDataSelector createCommentSelector(String data)
/*  71:    */     throws CSSException
/*  72:    */   {
/*  73: 84 */     throw new CSSException((short)1);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public ElementSelector createPseudoElementSelector(String namespaceURI, String pseudoName)
/*  77:    */     throws CSSException
/*  78:    */   {
/*  79: 90 */     if (namespaceURI != null) {
/*  80: 91 */       throw new CSSException((short)1);
/*  81:    */     }
/*  82: 93 */     return new PseudoElementSelectorImpl(pseudoName);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public DescendantSelector createDescendantSelector(Selector parent, SimpleSelector descendant)
/*  86:    */     throws CSSException
/*  87:    */   {
/*  88: 99 */     return new DescendantSelectorImpl(parent, descendant);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public DescendantSelector createChildSelector(Selector parent, SimpleSelector child)
/*  92:    */     throws CSSException
/*  93:    */   {
/*  94:105 */     return new ChildSelectorImpl(parent, child);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public SiblingSelector createDirectAdjacentSelector(short nodeType, Selector child, SimpleSelector directAdjacent)
/*  98:    */     throws CSSException
/*  99:    */   {
/* 100:112 */     return new DirectAdjacentSelectorImpl(nodeType, child, directAdjacent);
/* 101:    */   }
/* 102:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.selectors.SelectorFactoryImpl
 * JD-Core Version:    0.7.0.1
 */