/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import antlr.SemanticException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import org.hibernate.internal.CoreMessageLogger;
/*   6:    */ import org.jboss.logging.Logger;
/*   7:    */ 
/*   8:    */ public abstract class FromReferenceNode
/*   9:    */   extends AbstractSelectExpression
/*  10:    */   implements ResolvableNode, DisplayableNode, InitializeableNode, PathNode
/*  11:    */ {
/*  12: 40 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, FromReferenceNode.class.getName());
/*  13:    */   private FromElement fromElement;
/*  14: 43 */   private boolean resolved = false;
/*  15:    */   public static final int ROOT_LEVEL = 0;
/*  16:    */   
/*  17:    */   public FromElement getFromElement()
/*  18:    */   {
/*  19: 48 */     return this.fromElement;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void setFromElement(FromElement fromElement)
/*  23:    */   {
/*  24: 52 */     this.fromElement = fromElement;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void resolveFirstChild()
/*  28:    */     throws SemanticException
/*  29:    */   {}
/*  30:    */   
/*  31:    */   public String getPath()
/*  32:    */   {
/*  33: 64 */     return getOriginalText();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean isResolved()
/*  37:    */   {
/*  38: 68 */     return this.resolved;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setResolved()
/*  42:    */   {
/*  43: 72 */     this.resolved = true;
/*  44: 73 */     if (LOG.isDebugEnabled()) {
/*  45: 74 */       LOG.debugf("Resolved : %s -> %s", getPath(), getText());
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getDisplayText()
/*  50:    */   {
/*  51: 79 */     StringBuffer buf = new StringBuffer();
/*  52: 80 */     buf.append("{").append(this.fromElement == null ? "no fromElement" : this.fromElement.getDisplayText());
/*  53: 81 */     buf.append("}");
/*  54: 82 */     return buf.toString();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void recursiveResolve(int level, boolean impliedAtRoot, String classAlias)
/*  58:    */     throws SemanticException
/*  59:    */   {
/*  60: 86 */     recursiveResolve(level, impliedAtRoot, classAlias, this);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void recursiveResolve(int level, boolean impliedAtRoot, String classAlias, AST parent)
/*  64:    */     throws SemanticException
/*  65:    */   {
/*  66: 90 */     AST lhs = getFirstChild();
/*  67: 91 */     int nextLevel = level + 1;
/*  68: 92 */     if (lhs != null)
/*  69:    */     {
/*  70: 93 */       FromReferenceNode n = (FromReferenceNode)lhs;
/*  71: 94 */       n.recursiveResolve(nextLevel, impliedAtRoot, null, this);
/*  72:    */     }
/*  73: 96 */     resolveFirstChild();
/*  74: 97 */     boolean impliedJoin = true;
/*  75: 98 */     if ((level == 0) && (!impliedAtRoot)) {
/*  76: 99 */       impliedJoin = false;
/*  77:    */     }
/*  78:101 */     resolve(true, impliedJoin, classAlias, parent);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean isReturnableEntity()
/*  82:    */     throws SemanticException
/*  83:    */   {
/*  84:106 */     return (!isScalar()) && (this.fromElement.isEntity());
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void resolveInFunctionCall(boolean generateJoin, boolean implicitJoin)
/*  88:    */     throws SemanticException
/*  89:    */   {
/*  90:110 */     resolve(generateJoin, implicitJoin);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void resolve(boolean generateJoin, boolean implicitJoin)
/*  94:    */     throws SemanticException
/*  95:    */   {
/*  96:114 */     resolve(generateJoin, implicitJoin, null);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void resolve(boolean generateJoin, boolean implicitJoin, String classAlias)
/* 100:    */     throws SemanticException
/* 101:    */   {
/* 102:118 */     resolve(generateJoin, implicitJoin, classAlias, null);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void prepareForDot(String propertyName)
/* 106:    */     throws SemanticException
/* 107:    */   {}
/* 108:    */   
/* 109:    */   public FromElement getImpliedJoin()
/* 110:    */   {
/* 111:130 */     return null;
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.FromReferenceNode
 * JD-Core Version:    0.7.0.1
 */