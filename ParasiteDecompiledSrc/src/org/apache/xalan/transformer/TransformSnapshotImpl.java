/*   1:    */ package org.apache.xalan.transformer;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import java.util.Stack;
/*   5:    */ import java.util.Vector;
/*   6:    */ import org.apache.xml.dtm.DTMIterator;
/*   7:    */ import org.apache.xml.serializer.ExtendedContentHandler;
/*   8:    */ import org.apache.xml.serializer.NamespaceMappings;
/*   9:    */ import org.apache.xml.serializer.SerializationHandler;
/*  10:    */ import org.apache.xml.utils.BoolStack;
/*  11:    */ import org.apache.xml.utils.IntStack;
/*  12:    */ import org.apache.xml.utils.NodeVector;
/*  13:    */ import org.apache.xml.utils.ObjectStack;
/*  14:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  15:    */ import org.apache.xpath.VariableStack;
/*  16:    */ import org.apache.xpath.XPathContext;
/*  17:    */ 
/*  18:    */ /**
/*  19:    */  * @deprecated
/*  20:    */  */
/*  21:    */ class TransformSnapshotImpl
/*  22:    */   implements TransformSnapshot
/*  23:    */ {
/*  24:    */   private VariableStack m_variableStacks;
/*  25:    */   private IntStack m_currentNodes;
/*  26:    */   private IntStack m_currentExpressionNodes;
/*  27:    */   private Stack m_contextNodeLists;
/*  28:    */   private DTMIterator m_contextNodeList;
/*  29:    */   private Stack m_axesIteratorStack;
/*  30:    */   private BoolStack m_currentTemplateRuleIsNull;
/*  31:    */   private ObjectStack m_currentTemplateElements;
/*  32:    */   private Stack m_currentMatchTemplates;
/*  33:    */   private NodeVector m_currentMatchNodes;
/*  34:    */   private CountersTable m_countersTable;
/*  35:    */   private Stack m_attrSetStack;
/*  36:    */   boolean m_nsContextPushed;
/*  37:    */   private NamespaceMappings m_nsSupport;
/*  38:    */   
/*  39:    */   /**
/*  40:    */    * @deprecated
/*  41:    */    */
/*  42:    */   TransformSnapshotImpl(TransformerImpl transformer)
/*  43:    */   {
/*  44:    */     try
/*  45:    */     {
/*  46:152 */       SerializationHandler rtf = transformer.getResultTreeHandler();
/*  47:    */       
/*  48:    */ 
/*  49:    */ 
/*  50:156 */       this.m_nsSupport = ((NamespaceMappings)rtf.getNamespaceMappings().clone());
/*  51:    */       
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:161 */       XPathContext xpc = transformer.getXPathContext();
/*  56:    */       
/*  57:163 */       this.m_variableStacks = ((VariableStack)xpc.getVarStack().clone());
/*  58:164 */       this.m_currentNodes = ((IntStack)xpc.getCurrentNodeStack().clone());
/*  59:165 */       this.m_currentExpressionNodes = ((IntStack)xpc.getCurrentExpressionNodeStack().clone());
/*  60:    */       
/*  61:167 */       this.m_contextNodeLists = ((Stack)xpc.getContextNodeListsStack().clone());
/*  62:169 */       if (!this.m_contextNodeLists.empty()) {
/*  63:170 */         this.m_contextNodeList = ((DTMIterator)xpc.getContextNodeList().clone());
/*  64:    */       }
/*  65:173 */       this.m_axesIteratorStack = ((Stack)xpc.getAxesIteratorStackStacks().clone());
/*  66:174 */       this.m_currentTemplateRuleIsNull = ((BoolStack)transformer.m_currentTemplateRuleIsNull.clone());
/*  67:    */       
/*  68:176 */       this.m_currentTemplateElements = ((ObjectStack)transformer.m_currentTemplateElements.clone());
/*  69:    */       
/*  70:178 */       this.m_currentMatchTemplates = ((Stack)transformer.m_currentMatchTemplates.clone());
/*  71:    */       
/*  72:180 */       this.m_currentMatchNodes = ((NodeVector)transformer.m_currentMatchedNodes.clone());
/*  73:    */       
/*  74:182 */       this.m_countersTable = ((CountersTable)transformer.getCountersTable().clone());
/*  75:185 */       if (transformer.m_attrSetStack != null) {
/*  76:186 */         this.m_attrSetStack = ((Stack)transformer.m_attrSetStack.clone());
/*  77:    */       }
/*  78:    */     }
/*  79:    */     catch (CloneNotSupportedException cnse)
/*  80:    */     {
/*  81:190 */       throw new WrappedRuntimeException(cnse);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   /**
/*  86:    */    * @deprecated
/*  87:    */    */
/*  88:    */   void apply(TransformerImpl transformer)
/*  89:    */   {
/*  90:    */     try
/*  91:    */     {
/*  92:210 */       SerializationHandler rtf = transformer.getResultTreeHandler();
/*  93:212 */       if (rtf != null) {
/*  94:215 */         rtf.setNamespaceMappings((NamespaceMappings)this.m_nsSupport.clone());
/*  95:    */       }
/*  96:218 */       XPathContext xpc = transformer.getXPathContext();
/*  97:    */       
/*  98:220 */       xpc.setVarStack((VariableStack)this.m_variableStacks.clone());
/*  99:221 */       xpc.setCurrentNodeStack((IntStack)this.m_currentNodes.clone());
/* 100:222 */       xpc.setCurrentExpressionNodeStack((IntStack)this.m_currentExpressionNodes.clone());
/* 101:    */       
/* 102:224 */       xpc.setContextNodeListsStack((Stack)this.m_contextNodeLists.clone());
/* 103:226 */       if (this.m_contextNodeList != null) {
/* 104:227 */         xpc.pushContextNodeList((DTMIterator)this.m_contextNodeList.clone());
/* 105:    */       }
/* 106:229 */       xpc.setAxesIteratorStackStacks((Stack)this.m_axesIteratorStack.clone());
/* 107:    */       
/* 108:231 */       transformer.m_currentTemplateRuleIsNull = ((BoolStack)this.m_currentTemplateRuleIsNull.clone());
/* 109:    */       
/* 110:233 */       transformer.m_currentTemplateElements = ((ObjectStack)this.m_currentTemplateElements.clone());
/* 111:    */       
/* 112:235 */       transformer.m_currentMatchTemplates = ((Stack)this.m_currentMatchTemplates.clone());
/* 113:    */       
/* 114:237 */       transformer.m_currentMatchedNodes = ((NodeVector)this.m_currentMatchNodes.clone());
/* 115:    */       
/* 116:239 */       transformer.m_countersTable = ((CountersTable)this.m_countersTable.clone());
/* 117:241 */       if (this.m_attrSetStack != null) {
/* 118:242 */         transformer.m_attrSetStack = ((Stack)this.m_attrSetStack.clone());
/* 119:    */       }
/* 120:    */     }
/* 121:    */     catch (CloneNotSupportedException cnse)
/* 122:    */     {
/* 123:246 */       throw new WrappedRuntimeException(cnse);
/* 124:    */     }
/* 125:    */   }
/* 126:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.TransformSnapshotImpl
 * JD-Core Version:    0.7.0.1
 */