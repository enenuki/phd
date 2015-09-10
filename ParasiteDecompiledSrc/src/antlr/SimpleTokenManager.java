/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.Vector;
/*   4:    */ import java.util.Enumeration;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ 
/*   7:    */ class SimpleTokenManager
/*   8:    */   implements TokenManager, Cloneable
/*   9:    */ {
/*  10: 17 */   protected int maxToken = 4;
/*  11:    */   protected Vector vocabulary;
/*  12:    */   private Hashtable table;
/*  13:    */   protected Tool antlrTool;
/*  14:    */   protected String name;
/*  15: 27 */   protected boolean readOnly = false;
/*  16:    */   
/*  17:    */   SimpleTokenManager(String paramString, Tool paramTool)
/*  18:    */   {
/*  19: 30 */     this.antlrTool = paramTool;
/*  20: 31 */     this.name = paramString;
/*  21:    */     
/*  22: 33 */     this.vocabulary = new Vector(1);
/*  23: 34 */     this.table = new Hashtable();
/*  24:    */     
/*  25:    */ 
/*  26: 37 */     TokenSymbol localTokenSymbol = new TokenSymbol("EOF");
/*  27: 38 */     localTokenSymbol.setTokenType(1);
/*  28: 39 */     define(localTokenSymbol);
/*  29:    */     
/*  30:    */ 
/*  31: 42 */     this.vocabulary.ensureCapacity(3);
/*  32: 43 */     this.vocabulary.setElementAt("NULL_TREE_LOOKAHEAD", 3);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Object clone()
/*  36:    */   {
/*  37:    */     SimpleTokenManager localSimpleTokenManager;
/*  38:    */     try
/*  39:    */     {
/*  40: 49 */       localSimpleTokenManager = (SimpleTokenManager)super.clone();
/*  41: 50 */       localSimpleTokenManager.vocabulary = ((Vector)this.vocabulary.clone());
/*  42: 51 */       localSimpleTokenManager.table = ((Hashtable)this.table.clone());
/*  43: 52 */       localSimpleTokenManager.maxToken = this.maxToken;
/*  44: 53 */       localSimpleTokenManager.antlrTool = this.antlrTool;
/*  45: 54 */       localSimpleTokenManager.name = this.name;
/*  46:    */     }
/*  47:    */     catch (CloneNotSupportedException localCloneNotSupportedException)
/*  48:    */     {
/*  49: 57 */       this.antlrTool.panic("cannot clone token manager");
/*  50: 58 */       return null;
/*  51:    */     }
/*  52: 60 */     return localSimpleTokenManager;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void define(TokenSymbol paramTokenSymbol)
/*  56:    */   {
/*  57: 66 */     this.vocabulary.ensureCapacity(paramTokenSymbol.getTokenType());
/*  58: 67 */     this.vocabulary.setElementAt(paramTokenSymbol.getId(), paramTokenSymbol.getTokenType());
/*  59:    */     
/*  60: 69 */     mapToTokenSymbol(paramTokenSymbol.getId(), paramTokenSymbol);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getName()
/*  64:    */   {
/*  65: 74 */     return this.name;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getTokenStringAt(int paramInt)
/*  69:    */   {
/*  70: 79 */     return (String)this.vocabulary.elementAt(paramInt);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public TokenSymbol getTokenSymbol(String paramString)
/*  74:    */   {
/*  75: 84 */     return (TokenSymbol)this.table.get(paramString);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public TokenSymbol getTokenSymbolAt(int paramInt)
/*  79:    */   {
/*  80: 89 */     return getTokenSymbol(getTokenStringAt(paramInt));
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Enumeration getTokenSymbolElements()
/*  84:    */   {
/*  85: 94 */     return this.table.elements();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Enumeration getTokenSymbolKeys()
/*  89:    */   {
/*  90: 98 */     return this.table.keys();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Vector getVocabulary()
/*  94:    */   {
/*  95:105 */     return this.vocabulary;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isReadOnly()
/*  99:    */   {
/* 100:110 */     return false;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void mapToTokenSymbol(String paramString, TokenSymbol paramTokenSymbol)
/* 104:    */   {
/* 105:116 */     this.table.put(paramString, paramTokenSymbol);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int maxTokenType()
/* 109:    */   {
/* 110:121 */     return this.maxToken - 1;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int nextTokenType()
/* 114:    */   {
/* 115:126 */     return this.maxToken++;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setName(String paramString)
/* 119:    */   {
/* 120:131 */     this.name = paramString;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setReadOnly(boolean paramBoolean)
/* 124:    */   {
/* 125:135 */     this.readOnly = paramBoolean;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean tokenDefined(String paramString)
/* 129:    */   {
/* 130:140 */     return this.table.containsKey(paramString);
/* 131:    */   }
/* 132:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.SimpleTokenManager
 * JD-Core Version:    0.7.0.1
 */