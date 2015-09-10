/*   1:    */ package org.hibernate.engine.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.internal.CoreMessageLogger;
/*   5:    */ import org.jboss.logging.Logger;
/*   6:    */ 
/*   7:    */ public class IdentifierValue
/*   8:    */   implements UnsavedValueStrategy
/*   9:    */ {
/*  10: 42 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, IdentifierValue.class.getName());
/*  11:    */   private final Serializable value;
/*  12: 49 */   public static final IdentifierValue ANY = new IdentifierValue()
/*  13:    */   {
/*  14:    */     public final Boolean isUnsaved(Object id)
/*  15:    */     {
/*  16: 52 */       IdentifierValue.LOG.trace("ID unsaved-value strategy ANY");
/*  17: 53 */       return Boolean.TRUE;
/*  18:    */     }
/*  19:    */     
/*  20:    */     public Serializable getDefaultValue(Object currentValue)
/*  21:    */     {
/*  22: 57 */       return (Serializable)currentValue;
/*  23:    */     }
/*  24:    */     
/*  25:    */     public String toString()
/*  26:    */     {
/*  27: 61 */       return "SAVE_ANY";
/*  28:    */     }
/*  29:    */   };
/*  30: 68 */   public static final IdentifierValue NONE = new IdentifierValue()
/*  31:    */   {
/*  32:    */     public final Boolean isUnsaved(Object id)
/*  33:    */     {
/*  34: 71 */       IdentifierValue.LOG.trace("ID unsaved-value strategy NONE");
/*  35: 72 */       return Boolean.FALSE;
/*  36:    */     }
/*  37:    */     
/*  38:    */     public Serializable getDefaultValue(Object currentValue)
/*  39:    */     {
/*  40: 76 */       return (Serializable)currentValue;
/*  41:    */     }
/*  42:    */     
/*  43:    */     public String toString()
/*  44:    */     {
/*  45: 80 */       return "SAVE_NONE";
/*  46:    */     }
/*  47:    */   };
/*  48: 88 */   public static final IdentifierValue NULL = new IdentifierValue()
/*  49:    */   {
/*  50:    */     public final Boolean isUnsaved(Object id)
/*  51:    */     {
/*  52: 91 */       IdentifierValue.LOG.trace("ID unsaved-value strategy NULL");
/*  53: 92 */       return Boolean.valueOf(id == null);
/*  54:    */     }
/*  55:    */     
/*  56:    */     public Serializable getDefaultValue(Object currentValue)
/*  57:    */     {
/*  58: 96 */       return null;
/*  59:    */     }
/*  60:    */     
/*  61:    */     public String toString()
/*  62:    */     {
/*  63:100 */       return "SAVE_NULL";
/*  64:    */     }
/*  65:    */   };
/*  66:107 */   public static final IdentifierValue UNDEFINED = new IdentifierValue()
/*  67:    */   {
/*  68:    */     public final Boolean isUnsaved(Object id)
/*  69:    */     {
/*  70:110 */       IdentifierValue.LOG.trace("ID unsaved-value strategy UNDEFINED");
/*  71:111 */       return null;
/*  72:    */     }
/*  73:    */     
/*  74:    */     public Serializable getDefaultValue(Object currentValue)
/*  75:    */     {
/*  76:115 */       return null;
/*  77:    */     }
/*  78:    */     
/*  79:    */     public String toString()
/*  80:    */     {
/*  81:119 */       return "UNDEFINED";
/*  82:    */     }
/*  83:    */   };
/*  84:    */   
/*  85:    */   protected IdentifierValue()
/*  86:    */   {
/*  87:124 */     this.value = null;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public IdentifierValue(Serializable value)
/*  91:    */   {
/*  92:132 */     this.value = value;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Boolean isUnsaved(Object id)
/*  96:    */   {
/*  97:139 */     LOG.tracev("ID unsaved-value: {0}", this.value);
/*  98:140 */     return Boolean.valueOf((id == null) || (id.equals(this.value)));
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Serializable getDefaultValue(Object currentValue)
/* 102:    */   {
/* 103:144 */     return this.value;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String toString()
/* 107:    */   {
/* 108:149 */     return "identifier unsaved-value: " + this.value;
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.IdentifierValue
 * JD-Core Version:    0.7.0.1
 */