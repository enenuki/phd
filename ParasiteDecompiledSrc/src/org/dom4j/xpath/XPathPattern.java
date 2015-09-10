/*   1:    */ package org.dom4j.xpath;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.dom4j.InvalidXPathException;
/*   5:    */ import org.dom4j.Node;
/*   6:    */ import org.dom4j.XPathException;
/*   7:    */ import org.jaxen.Context;
/*   8:    */ import org.jaxen.ContextSupport;
/*   9:    */ import org.jaxen.JaxenException;
/*  10:    */ import org.jaxen.SimpleNamespaceContext;
/*  11:    */ import org.jaxen.SimpleVariableContext;
/*  12:    */ import org.jaxen.VariableContext;
/*  13:    */ import org.jaxen.XPathFunctionContext;
/*  14:    */ import org.jaxen.dom4j.DocumentNavigator;
/*  15:    */ import org.jaxen.pattern.PatternParser;
/*  16:    */ import org.jaxen.saxpath.SAXPathException;
/*  17:    */ 
/*  18:    */ public class XPathPattern
/*  19:    */   implements org.dom4j.rule.Pattern
/*  20:    */ {
/*  21:    */   private String text;
/*  22:    */   private org.jaxen.pattern.Pattern pattern;
/*  23:    */   private Context context;
/*  24:    */   
/*  25:    */   public XPathPattern(org.jaxen.pattern.Pattern pattern)
/*  26:    */   {
/*  27: 45 */     this.pattern = pattern;
/*  28: 46 */     this.text = pattern.getText();
/*  29: 47 */     this.context = new Context(getContextSupport());
/*  30:    */   }
/*  31:    */   
/*  32:    */   public XPathPattern(String text)
/*  33:    */   {
/*  34: 51 */     this.text = text;
/*  35: 52 */     this.context = new Context(getContextSupport());
/*  36:    */     try
/*  37:    */     {
/*  38: 55 */       this.pattern = PatternParser.parse(text);
/*  39:    */     }
/*  40:    */     catch (SAXPathException e)
/*  41:    */     {
/*  42: 57 */       throw new InvalidXPathException(text, e.getMessage());
/*  43:    */     }
/*  44:    */     catch (Throwable t)
/*  45:    */     {
/*  46: 59 */       throw new InvalidXPathException(text, t);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean matches(Node node)
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 65 */       ArrayList list = new ArrayList(1);
/*  55: 66 */       list.add(node);
/*  56: 67 */       this.context.setNodeSet(list);
/*  57:    */       
/*  58: 69 */       return this.pattern.matches(node, this.context);
/*  59:    */     }
/*  60:    */     catch (JaxenException e)
/*  61:    */     {
/*  62: 71 */       handleJaxenException(e);
/*  63:    */     }
/*  64: 73 */     return false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getText()
/*  68:    */   {
/*  69: 78 */     return this.text;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public double getPriority()
/*  73:    */   {
/*  74: 82 */     return this.pattern.getPriority();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public org.dom4j.rule.Pattern[] getUnionPatterns()
/*  78:    */   {
/*  79: 86 */     org.jaxen.pattern.Pattern[] patterns = this.pattern.getUnionPatterns();
/*  80: 88 */     if (patterns != null)
/*  81:    */     {
/*  82: 89 */       int size = patterns.length;
/*  83: 90 */       XPathPattern[] answer = new XPathPattern[size];
/*  84: 92 */       for (int i = 0; i < size; i++) {
/*  85: 93 */         answer[i] = new XPathPattern(patterns[i]);
/*  86:    */       }
/*  87: 96 */       return answer;
/*  88:    */     }
/*  89: 99 */     return null;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public short getMatchType()
/*  93:    */   {
/*  94:103 */     return this.pattern.getMatchType();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getMatchesNodeName()
/*  98:    */   {
/*  99:107 */     return this.pattern.getMatchesNodeName();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setVariableContext(VariableContext variableContext)
/* 103:    */   {
/* 104:111 */     this.context.getContextSupport().setVariableContext(variableContext);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String toString()
/* 108:    */   {
/* 109:115 */     return "[XPathPattern: text: " + this.text + " Pattern: " + this.pattern + "]";
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected ContextSupport getContextSupport()
/* 113:    */   {
/* 114:119 */     return new ContextSupport(new SimpleNamespaceContext(), XPathFunctionContext.getInstance(), new SimpleVariableContext(), DocumentNavigator.getInstance());
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected void handleJaxenException(JaxenException exception)
/* 118:    */     throws XPathException
/* 119:    */   {
/* 120:126 */     throw new XPathException(this.text, exception);
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.xpath.XPathPattern
 * JD-Core Version:    0.7.0.1
 */