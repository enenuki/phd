/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Set;
/*   6:    */ 
/*   7:    */ public class PluginConfiguration
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private final String description_;
/*  11:    */   private final String filename_;
/*  12:    */   private final String name_;
/*  13: 34 */   private final Set<MimeType> mimeTypes_ = new HashSet();
/*  14:    */   
/*  15:    */   public PluginConfiguration(String name, String description, String filename)
/*  16:    */   {
/*  17: 43 */     WebAssert.notNull("name", name);
/*  18: 44 */     this.name_ = name;
/*  19: 45 */     this.description_ = description;
/*  20: 46 */     this.filename_ = filename;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String getDescription()
/*  24:    */   {
/*  25: 54 */     return this.description_;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getFilename()
/*  29:    */   {
/*  30: 62 */     return this.filename_;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getName()
/*  34:    */   {
/*  35: 70 */     return this.name_;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Set<MimeType> getMimeTypes()
/*  39:    */   {
/*  40: 78 */     return this.mimeTypes_;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int hashCode()
/*  44:    */   {
/*  45: 86 */     return this.name_.hashCode();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean equals(Object o)
/*  49:    */   {
/*  50: 94 */     if (!(o instanceof PluginConfiguration)) {
/*  51: 95 */       return false;
/*  52:    */     }
/*  53: 97 */     PluginConfiguration other = (PluginConfiguration)o;
/*  54: 98 */     return this.name_.equals(other.name_);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public static class MimeType
/*  58:    */     implements Serializable
/*  59:    */   {
/*  60:    */     private final String description_;
/*  61:    */     private final String suffixes_;
/*  62:    */     private final String type_;
/*  63:    */     
/*  64:    */     public MimeType(String type, String description, String suffixes)
/*  65:    */     {
/*  66:117 */       WebAssert.notNull("type", type);
/*  67:118 */       this.type_ = type;
/*  68:119 */       this.description_ = description;
/*  69:120 */       this.suffixes_ = suffixes;
/*  70:    */     }
/*  71:    */     
/*  72:    */     public String getDescription()
/*  73:    */     {
/*  74:128 */       return this.description_;
/*  75:    */     }
/*  76:    */     
/*  77:    */     public String getSuffixes()
/*  78:    */     {
/*  79:136 */       return this.suffixes_;
/*  80:    */     }
/*  81:    */     
/*  82:    */     public String getType()
/*  83:    */     {
/*  84:144 */       return this.type_;
/*  85:    */     }
/*  86:    */     
/*  87:    */     public int hashCode()
/*  88:    */     {
/*  89:152 */       return this.type_.hashCode();
/*  90:    */     }
/*  91:    */     
/*  92:    */     public boolean equals(Object o)
/*  93:    */     {
/*  94:160 */       if (!(o instanceof MimeType)) {
/*  95:161 */         return false;
/*  96:    */       }
/*  97:163 */       MimeType other = (MimeType)o;
/*  98:164 */       return this.type_.equals(other.type_);
/*  99:    */     }
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.PluginConfiguration
 * JD-Core Version:    0.7.0.1
 */