/*   1:    */ package com.steadystate.css.parser.selectors;
/*   2:    */ 
/*   3:    */ import org.w3c.css.sac.AttributeCondition;
/*   4:    */ import org.w3c.css.sac.CSSException;
/*   5:    */ import org.w3c.css.sac.CombinatorCondition;
/*   6:    */ import org.w3c.css.sac.Condition;
/*   7:    */ import org.w3c.css.sac.ConditionFactory;
/*   8:    */ import org.w3c.css.sac.ContentCondition;
/*   9:    */ import org.w3c.css.sac.LangCondition;
/*  10:    */ import org.w3c.css.sac.NegativeCondition;
/*  11:    */ import org.w3c.css.sac.PositionalCondition;
/*  12:    */ 
/*  13:    */ public class ConditionFactoryImpl
/*  14:    */   implements ConditionFactory
/*  15:    */ {
/*  16:    */   public CombinatorCondition createAndCondition(Condition first, Condition second)
/*  17:    */     throws CSSException
/*  18:    */   {
/*  19: 42 */     return new AndConditionImpl(first, second);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public CombinatorCondition createOrCondition(Condition first, Condition second)
/*  23:    */     throws CSSException
/*  24:    */   {
/*  25: 48 */     throw new CSSException((short)1);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public NegativeCondition createNegativeCondition(Condition condition)
/*  29:    */     throws CSSException
/*  30:    */   {
/*  31: 53 */     throw new CSSException((short)1);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public PositionalCondition createPositionalCondition(int position, boolean typeNode, boolean type)
/*  35:    */     throws CSSException
/*  36:    */   {
/*  37: 60 */     throw new CSSException((short)1);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public AttributeCondition createAttributeCondition(String localName, String namespaceURI, boolean specified, String value)
/*  41:    */     throws CSSException
/*  42:    */   {
/*  43: 71 */     return new AttributeConditionImpl(localName, value);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public AttributeCondition createIdCondition(String value)
/*  47:    */     throws CSSException
/*  48:    */   {
/*  49: 77 */     return new IdConditionImpl(value);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public LangCondition createLangCondition(String lang)
/*  53:    */     throws CSSException
/*  54:    */   {
/*  55: 82 */     return new LangConditionImpl(lang);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public AttributeCondition createOneOfAttributeCondition(String localName, String namespaceURI, boolean specified, String value)
/*  59:    */     throws CSSException
/*  60:    */   {
/*  61: 93 */     return new OneOfAttributeConditionImpl(localName, value);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public AttributeCondition createBeginHyphenAttributeCondition(String localName, String namespaceURI, boolean specified, String value)
/*  65:    */     throws CSSException
/*  66:    */   {
/*  67:105 */     return new BeginHyphenAttributeConditionImpl(localName, value);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public AttributeCondition createClassCondition(String namespaceURI, String value)
/*  71:    */     throws CSSException
/*  72:    */   {
/*  73:112 */     return new ClassConditionImpl(value);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public AttributeCondition createPseudoClassCondition(String namespaceURI, String value)
/*  77:    */     throws CSSException
/*  78:    */   {
/*  79:118 */     return new PseudoClassConditionImpl(value);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Condition createOnlyChildCondition()
/*  83:    */     throws CSSException
/*  84:    */   {
/*  85:122 */     throw new CSSException((short)1);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Condition createOnlyTypeCondition()
/*  89:    */     throws CSSException
/*  90:    */   {
/*  91:126 */     throw new CSSException((short)1);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public ContentCondition createContentCondition(String data)
/*  95:    */     throws CSSException
/*  96:    */   {
/*  97:131 */     throw new CSSException((short)1);
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.selectors.ConditionFactoryImpl
 * JD-Core Version:    0.7.0.1
 */