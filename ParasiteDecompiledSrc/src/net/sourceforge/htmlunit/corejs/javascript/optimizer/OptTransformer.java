/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.optimizer;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*   5:    */ import net.sourceforge.htmlunit.corejs.javascript.Node;
/*   6:    */ import net.sourceforge.htmlunit.corejs.javascript.NodeTransformer;
/*   7:    */ import net.sourceforge.htmlunit.corejs.javascript.ObjArray;
/*   8:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionNode;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*  10:    */ 
/*  11:    */ class OptTransformer
/*  12:    */   extends NodeTransformer
/*  13:    */ {
/*  14:    */   private Map<String, OptFunctionNode> possibleDirectCalls;
/*  15:    */   private ObjArray directCallTargets;
/*  16:    */   
/*  17:    */   OptTransformer(Map<String, OptFunctionNode> possibleDirectCalls, ObjArray directCallTargets)
/*  18:    */   {
/*  19: 56 */     this.possibleDirectCalls = possibleDirectCalls;
/*  20: 57 */     this.directCallTargets = directCallTargets;
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected void visitNew(Node node, ScriptNode tree)
/*  24:    */   {
/*  25: 62 */     detectDirectCall(node, tree);
/*  26: 63 */     super.visitNew(node, tree);
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected void visitCall(Node node, ScriptNode tree)
/*  30:    */   {
/*  31: 68 */     detectDirectCall(node, tree);
/*  32: 69 */     super.visitCall(node, tree);
/*  33:    */   }
/*  34:    */   
/*  35:    */   private void detectDirectCall(Node node, ScriptNode tree)
/*  36:    */   {
/*  37: 74 */     if (tree.getType() == 109)
/*  38:    */     {
/*  39: 75 */       Node left = node.getFirstChild();
/*  40:    */       
/*  41:    */ 
/*  42: 78 */       int argCount = 0;
/*  43: 79 */       Node arg = left.getNext();
/*  44: 80 */       while (arg != null)
/*  45:    */       {
/*  46: 81 */         arg = arg.getNext();
/*  47: 82 */         argCount++;
/*  48:    */       }
/*  49: 85 */       if (argCount == 0) {
/*  50: 86 */         OptFunctionNode.get(tree).itsContainsCalls0 = true;
/*  51:    */       }
/*  52:101 */       if (this.possibleDirectCalls != null)
/*  53:    */       {
/*  54:102 */         String targetName = null;
/*  55:103 */         if (left.getType() == 39) {
/*  56:104 */           targetName = left.getString();
/*  57:105 */         } else if (left.getType() == 33) {
/*  58:106 */           targetName = left.getFirstChild().getNext().getString();
/*  59:107 */         } else if (left.getType() == 34) {
/*  60:108 */           throw Kit.codeBug();
/*  61:    */         }
/*  62:110 */         if (targetName != null)
/*  63:    */         {
/*  64:112 */           OptFunctionNode ofn = (OptFunctionNode)this.possibleDirectCalls.get(targetName);
/*  65:113 */           if ((ofn != null) && (argCount == ofn.fnode.getParamCount()) && (!ofn.fnode.requiresActivation())) {
/*  66:120 */             if (argCount <= 32)
/*  67:    */             {
/*  68:121 */               node.putProp(9, ofn);
/*  69:122 */               if (!ofn.isTargetOfDirectCall())
/*  70:    */               {
/*  71:123 */                 int index = this.directCallTargets.size();
/*  72:124 */                 this.directCallTargets.add(ofn);
/*  73:125 */                 ofn.setDirectTargetIndex(index);
/*  74:    */               }
/*  75:    */             }
/*  76:    */           }
/*  77:    */         }
/*  78:    */       }
/*  79:    */     }
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.optimizer.OptTransformer
 * JD-Core Version:    0.7.0.1
 */