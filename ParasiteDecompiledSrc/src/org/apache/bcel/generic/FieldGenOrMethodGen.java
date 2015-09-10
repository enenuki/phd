/*   1:    */ package org.apache.bcel.generic;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.AbstractCollection;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import org.apache.bcel.classfile.AccessFlags;
/*   7:    */ import org.apache.bcel.classfile.Attribute;
/*   8:    */ 
/*   9:    */ public abstract class FieldGenOrMethodGen
/*  10:    */   extends AccessFlags
/*  11:    */   implements NamedAndTyped, Cloneable
/*  12:    */ {
/*  13:    */   protected String name;
/*  14:    */   protected Type type;
/*  15:    */   protected ConstantPoolGen cp;
/*  16: 73 */   private ArrayList attribute_vec = new ArrayList();
/*  17:    */   
/*  18:    */   public void setType(Type type)
/*  19:    */   {
/*  20: 77 */     this.type = type;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Type getType()
/*  24:    */   {
/*  25: 78 */     return this.type;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getName()
/*  29:    */   {
/*  30: 82 */     return this.name;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setName(String name)
/*  34:    */   {
/*  35: 83 */     this.name = name;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ConstantPoolGen getConstantPool()
/*  39:    */   {
/*  40: 85 */     return this.cp;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setConstantPool(ConstantPoolGen cp)
/*  44:    */   {
/*  45: 86 */     this.cp = cp;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void addAttribute(Attribute a)
/*  49:    */   {
/*  50: 96 */     this.attribute_vec.add(a);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void removeAttribute(Attribute a)
/*  54:    */   {
/*  55:101 */     this.attribute_vec.remove(a);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void removeAttributes()
/*  59:    */   {
/*  60:106 */     this.attribute_vec.clear();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Attribute[] getAttributes()
/*  64:    */   {
/*  65:112 */     Attribute[] attributes = new Attribute[this.attribute_vec.size()];
/*  66:113 */     this.attribute_vec.toArray(attributes);
/*  67:114 */     return attributes;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public abstract String getSignature();
/*  71:    */   
/*  72:    */   public Object clone()
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:123 */       return super.clone();
/*  77:    */     }
/*  78:    */     catch (CloneNotSupportedException e)
/*  79:    */     {
/*  80:125 */       System.err.println(e);
/*  81:    */     }
/*  82:126 */     return null;
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.generic.FieldGenOrMethodGen
 * JD-Core Version:    0.7.0.1
 */