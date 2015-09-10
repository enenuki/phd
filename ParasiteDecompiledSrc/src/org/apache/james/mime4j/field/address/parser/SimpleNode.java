/*  1:   */ package org.apache.james.mime4j.field.address.parser;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class SimpleNode
/*  6:   */   extends BaseNode
/*  7:   */   implements Node
/*  8:   */ {
/*  9:   */   protected Node parent;
/* 10:   */   protected Node[] children;
/* 11:   */   protected int id;
/* 12:   */   protected AddressListParser parser;
/* 13:   */   
/* 14:   */   public SimpleNode(int i)
/* 15:   */   {
/* 16:12 */     this.id = i;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public SimpleNode(AddressListParser p, int i)
/* 20:   */   {
/* 21:16 */     this(i);
/* 22:17 */     this.parser = p;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void jjtOpen() {}
/* 26:   */   
/* 27:   */   public void jjtClose() {}
/* 28:   */   
/* 29:   */   public void jjtSetParent(Node n)
/* 30:   */   {
/* 31:26 */     this.parent = n;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Node jjtGetParent()
/* 35:   */   {
/* 36:27 */     return this.parent;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void jjtAddChild(Node n, int i)
/* 40:   */   {
/* 41:30 */     if (this.children == null)
/* 42:   */     {
/* 43:31 */       this.children = new Node[i + 1];
/* 44:   */     }
/* 45:32 */     else if (i >= this.children.length)
/* 46:   */     {
/* 47:33 */       Node[] c = new Node[i + 1];
/* 48:34 */       System.arraycopy(this.children, 0, c, 0, this.children.length);
/* 49:35 */       this.children = c;
/* 50:   */     }
/* 51:37 */     this.children[i] = n;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public Node jjtGetChild(int i)
/* 55:   */   {
/* 56:41 */     return this.children[i];
/* 57:   */   }
/* 58:   */   
/* 59:   */   public int jjtGetNumChildren()
/* 60:   */   {
/* 61:45 */     return this.children == null ? 0 : this.children.length;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public Object jjtAccept(AddressListParserVisitor visitor, Object data)
/* 65:   */   {
/* 66:50 */     return visitor.visit(this, data);
/* 67:   */   }
/* 68:   */   
/* 69:   */   public Object childrenAccept(AddressListParserVisitor visitor, Object data)
/* 70:   */   {
/* 71:55 */     if (this.children != null) {
/* 72:56 */       for (int i = 0; i < this.children.length; i++) {
/* 73:57 */         this.children[i].jjtAccept(visitor, data);
/* 74:   */       }
/* 75:   */     }
/* 76:60 */     return data;
/* 77:   */   }
/* 78:   */   
/* 79:   */   public String toString()
/* 80:   */   {
/* 81:69 */     return AddressListParserTreeConstants.jjtNodeName[this.id];
/* 82:   */   }
/* 83:   */   
/* 84:   */   public String toString(String prefix)
/* 85:   */   {
/* 86:70 */     return prefix + toString();
/* 87:   */   }
/* 88:   */   
/* 89:   */   public void dump(String prefix)
/* 90:   */   {
/* 91:76 */     System.out.println(toString(prefix));
/* 92:77 */     if (this.children != null) {
/* 93:78 */       for (int i = 0; i < this.children.length; i++)
/* 94:   */       {
/* 95:79 */         SimpleNode n = (SimpleNode)this.children[i];
/* 96:80 */         if (n != null) {
/* 97:81 */           n.dump(prefix + " ");
/* 98:   */         }
/* 99:   */       }
/* :0:   */     }
/* :1:   */   }
/* :2:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.parser.SimpleNode
 * JD-Core Version:    0.7.0.1
 */