/*   1:    */ package com.fasterxml.classmate.members;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.Annotations;
/*   4:    */ import com.fasterxml.classmate.ResolvedType;
/*   5:    */ import java.lang.annotation.Annotation;
/*   6:    */ import java.lang.reflect.Member;
/*   7:    */ import java.lang.reflect.Modifier;
/*   8:    */ 
/*   9:    */ public abstract class ResolvedMember
/*  10:    */ {
/*  11:    */   protected final ResolvedType _declaringType;
/*  12:    */   protected final Annotations _annotations;
/*  13:    */   
/*  14:    */   protected ResolvedMember(ResolvedType context, Annotations ann)
/*  15:    */   {
/*  16: 33 */     this._declaringType = context;
/*  17: 34 */     this._annotations = ann;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void applyOverride(Annotation override)
/*  21:    */   {
/*  22: 39 */     this._annotations.add(override);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void applyOverrides(Annotations overrides)
/*  26:    */   {
/*  27: 44 */     this._annotations.addAll(overrides);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void applyDefault(Annotation override)
/*  31:    */   {
/*  32: 49 */     this._annotations.addAsDefault(override);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public final ResolvedType getDeclaringType()
/*  36:    */   {
/*  37: 59 */     return this._declaringType;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public abstract ResolvedType getType();
/*  41:    */   
/*  42:    */   public abstract Member getRawMember();
/*  43:    */   
/*  44:    */   public String getName()
/*  45:    */   {
/*  46: 74 */     return getRawMember().getName();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean isAbstract()
/*  50:    */   {
/*  51: 78 */     return Modifier.isAbstract(getModifiers());
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isStatic()
/*  55:    */   {
/*  56: 82 */     return Modifier.isStatic(getModifiers());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int hashCode()
/*  60:    */   {
/*  61: 92 */     return getName().hashCode();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String toString()
/*  65:    */   {
/*  66: 96 */     return getName();
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected final int getModifiers()
/*  70:    */   {
/*  71:105 */     return getRawMember().getModifiers();
/*  72:    */   }
/*  73:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.members.ResolvedMember
 * JD-Core Version:    0.7.0.1
 */