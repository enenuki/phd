/*   1:    */ package org.hibernate.metamodel.binding;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.AssertionFailure;
/*   6:    */ import org.hibernate.metamodel.domain.SingularAttribute;
/*   7:    */ import org.hibernate.metamodel.relational.SimpleValue;
/*   8:    */ import org.hibernate.metamodel.relational.TableSpecification;
/*   9:    */ import org.hibernate.metamodel.relational.Tuple;
/*  10:    */ import org.hibernate.metamodel.relational.Value;
/*  11:    */ 
/*  12:    */ public abstract class AbstractSingularAttributeBinding
/*  13:    */   extends AbstractAttributeBinding
/*  14:    */   implements SingularAttributeBinding
/*  15:    */ {
/*  16:    */   private Value value;
/*  17: 43 */   private List<SimpleValueBinding> simpleValueBindings = new ArrayList();
/*  18:    */   private boolean hasDerivedValue;
/*  19: 46 */   private boolean isNullable = true;
/*  20:    */   
/*  21:    */   protected AbstractSingularAttributeBinding(AttributeBindingContainer container, SingularAttribute attribute)
/*  22:    */   {
/*  23: 49 */     super(container, attribute);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public SingularAttribute getAttribute()
/*  27:    */   {
/*  28: 54 */     return (SingularAttribute)super.getAttribute();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Value getValue()
/*  32:    */   {
/*  33: 58 */     return this.value;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setSimpleValueBindings(Iterable<SimpleValueBinding> simpleValueBindings)
/*  37:    */   {
/*  38: 62 */     List<SimpleValue> values = new ArrayList();
/*  39: 63 */     for (SimpleValueBinding simpleValueBinding : simpleValueBindings)
/*  40:    */     {
/*  41: 64 */       this.simpleValueBindings.add(simpleValueBinding);
/*  42: 65 */       values.add(simpleValueBinding.getSimpleValue());
/*  43: 66 */       this.hasDerivedValue = ((this.hasDerivedValue) || (simpleValueBinding.isDerived()));
/*  44: 67 */       this.isNullable = ((this.isNullable) && (simpleValueBinding.isNullable()));
/*  45:    */     }
/*  46: 69 */     if (values.size() == 1)
/*  47:    */     {
/*  48: 70 */       this.value = ((Value)values.get(0));
/*  49:    */     }
/*  50:    */     else
/*  51:    */     {
/*  52: 73 */       Tuple tuple = ((SimpleValue)values.get(0)).getTable().createTuple(getRole());
/*  53: 74 */       for (SimpleValue value : values) {
/*  54: 75 */         tuple.addValue(value);
/*  55:    */       }
/*  56: 77 */       this.value = tuple;
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   private String getRole()
/*  61:    */   {
/*  62: 82 */     return getContainer().getPathBase() + '.' + getAttribute().getName();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getSimpleValueSpan()
/*  66:    */   {
/*  67: 87 */     checkValueBinding();
/*  68: 88 */     return this.simpleValueBindings.size();
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected void checkValueBinding()
/*  72:    */   {
/*  73: 92 */     if (this.value == null) {
/*  74: 93 */       throw new AssertionFailure("No values yet bound!");
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Iterable<SimpleValueBinding> getSimpleValueBindings()
/*  79:    */   {
/*  80: 99 */     return this.simpleValueBindings;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean hasDerivedValue()
/*  84:    */   {
/*  85:104 */     checkValueBinding();
/*  86:105 */     return this.hasDerivedValue;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isNullable()
/*  90:    */   {
/*  91:110 */     checkValueBinding();
/*  92:111 */     return this.isNullable;
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.AbstractSingularAttributeBinding
 * JD-Core Version:    0.7.0.1
 */