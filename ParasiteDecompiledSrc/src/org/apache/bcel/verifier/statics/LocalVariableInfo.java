/*   1:    */ package org.apache.bcel.verifier.statics;
/*   2:    */ 
/*   3:    */ import java.util.Hashtable;
/*   4:    */ import org.apache.bcel.generic.Type;
/*   5:    */ import org.apache.bcel.verifier.exc.LocalVariableInfoInconsistentException;
/*   6:    */ 
/*   7:    */ public class LocalVariableInfo
/*   8:    */ {
/*   9: 74 */   private Hashtable types = new Hashtable();
/*  10: 76 */   private Hashtable names = new Hashtable();
/*  11:    */   
/*  12:    */   private void setName(int offset, String name)
/*  13:    */   {
/*  14: 83 */     this.names.put(Integer.toString(offset), name);
/*  15:    */   }
/*  16:    */   
/*  17:    */   private void setType(int offset, Type t)
/*  18:    */   {
/*  19: 90 */     this.types.put(Integer.toString(offset), t);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Type getType(int offset)
/*  23:    */   {
/*  24:102 */     return (Type)this.types.get(Integer.toString(offset));
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getName(int offset)
/*  28:    */   {
/*  29:113 */     return (String)this.names.get(Integer.toString(offset));
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void add(String name, int startpc, int length, Type t)
/*  33:    */     throws LocalVariableInfoInconsistentException
/*  34:    */   {
/*  35:121 */     for (int i = startpc; i <= startpc + length; i++) {
/*  36:122 */       add(i, name, t);
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   private void add(int offset, String name, Type t)
/*  41:    */     throws LocalVariableInfoInconsistentException
/*  42:    */   {
/*  43:132 */     if ((getName(offset) != null) && 
/*  44:133 */       (!getName(offset).equals(name))) {
/*  45:134 */       throw new LocalVariableInfoInconsistentException("At bytecode offset '" + offset + "' a local variable has two different names: '" + getName(offset) + "' and '" + name + "'.");
/*  46:    */     }
/*  47:137 */     if ((getType(offset) != null) && 
/*  48:138 */       (!getType(offset).equals(t))) {
/*  49:139 */       throw new LocalVariableInfoInconsistentException("At bytecode offset '" + offset + "' a local variable has two different types: '" + getType(offset) + "' and '" + t + "'.");
/*  50:    */     }
/*  51:142 */     setName(offset, name);
/*  52:143 */     setType(offset, t);
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.statics.LocalVariableInfo
 * JD-Core Version:    0.7.0.1
 */