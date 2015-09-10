/*   1:    */ package org.apache.james.mime4j.field.address.parser;
/*   2:    */ 
/*   3:    */ import java.util.Stack;
/*   4:    */ 
/*   5:    */ class JJTAddressListParserState
/*   6:    */ {
/*   7:    */   private Stack<Node> nodes;
/*   8:    */   private Stack<Integer> marks;
/*   9:    */   private int sp;
/*  10:    */   private int mk;
/*  11:    */   private boolean node_created;
/*  12:    */   
/*  13:    */   JJTAddressListParserState()
/*  14:    */   {
/*  15: 14 */     this.nodes = new Stack();
/*  16: 15 */     this.marks = new Stack();
/*  17: 16 */     this.sp = 0;
/*  18: 17 */     this.mk = 0;
/*  19:    */   }
/*  20:    */   
/*  21:    */   boolean nodeCreated()
/*  22:    */   {
/*  23: 24 */     return this.node_created;
/*  24:    */   }
/*  25:    */   
/*  26:    */   void reset()
/*  27:    */   {
/*  28: 30 */     this.nodes.removeAllElements();
/*  29: 31 */     this.marks.removeAllElements();
/*  30: 32 */     this.sp = 0;
/*  31: 33 */     this.mk = 0;
/*  32:    */   }
/*  33:    */   
/*  34:    */   Node rootNode()
/*  35:    */   {
/*  36: 39 */     return (Node)this.nodes.elementAt(0);
/*  37:    */   }
/*  38:    */   
/*  39:    */   void pushNode(Node n)
/*  40:    */   {
/*  41: 44 */     this.nodes.push(n);
/*  42: 45 */     this.sp += 1;
/*  43:    */   }
/*  44:    */   
/*  45:    */   Node popNode()
/*  46:    */   {
/*  47: 51 */     if (--this.sp < this.mk) {
/*  48: 52 */       this.mk = ((Integer)this.marks.pop()).intValue();
/*  49:    */     }
/*  50: 54 */     return (Node)this.nodes.pop();
/*  51:    */   }
/*  52:    */   
/*  53:    */   Node peekNode()
/*  54:    */   {
/*  55: 59 */     return (Node)this.nodes.peek();
/*  56:    */   }
/*  57:    */   
/*  58:    */   int nodeArity()
/*  59:    */   {
/*  60: 65 */     return this.sp - this.mk;
/*  61:    */   }
/*  62:    */   
/*  63:    */   void clearNodeScope(Node n)
/*  64:    */   {
/*  65: 70 */     while (this.sp > this.mk) {
/*  66: 71 */       popNode();
/*  67:    */     }
/*  68: 73 */     this.mk = ((Integer)this.marks.pop()).intValue();
/*  69:    */   }
/*  70:    */   
/*  71:    */   void openNodeScope(Node n)
/*  72:    */   {
/*  73: 78 */     this.marks.push(new Integer(this.mk));
/*  74: 79 */     this.mk = this.sp;
/*  75: 80 */     n.jjtOpen();
/*  76:    */   }
/*  77:    */   
/*  78:    */   void closeNodeScope(Node n, int num)
/*  79:    */   {
/*  80: 89 */     this.mk = ((Integer)this.marks.pop()).intValue();
/*  81: 90 */     while (num-- > 0)
/*  82:    */     {
/*  83: 91 */       Node c = popNode();
/*  84: 92 */       c.jjtSetParent(n);
/*  85: 93 */       n.jjtAddChild(c, num);
/*  86:    */     }
/*  87: 95 */     n.jjtClose();
/*  88: 96 */     pushNode(n);
/*  89: 97 */     this.node_created = true;
/*  90:    */   }
/*  91:    */   
/*  92:    */   void closeNodeScope(Node n, boolean condition)
/*  93:    */   {
/*  94:107 */     if (condition)
/*  95:    */     {
/*  96:108 */       int a = nodeArity();
/*  97:109 */       this.mk = ((Integer)this.marks.pop()).intValue();
/*  98:110 */       while (a-- > 0)
/*  99:    */       {
/* 100:111 */         Node c = popNode();
/* 101:112 */         c.jjtSetParent(n);
/* 102:113 */         n.jjtAddChild(c, a);
/* 103:    */       }
/* 104:115 */       n.jjtClose();
/* 105:116 */       pushNode(n);
/* 106:117 */       this.node_created = true;
/* 107:    */     }
/* 108:    */     else
/* 109:    */     {
/* 110:119 */       this.mk = ((Integer)this.marks.pop()).intValue();
/* 111:120 */       this.node_created = false;
/* 112:    */     }
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.parser.JJTAddressListParserState
 * JD-Core Version:    0.7.0.1
 */