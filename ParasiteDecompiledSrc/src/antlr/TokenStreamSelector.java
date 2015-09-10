/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.ASdebug.ASDebugStream;
/*   4:    */ import antlr.ASdebug.IASDebugStream;
/*   5:    */ import antlr.ASdebug.TokenOffsetInfo;
/*   6:    */ import antlr.collections.Stack;
/*   7:    */ import antlr.collections.impl.LList;
/*   8:    */ import java.util.Hashtable;
/*   9:    */ 
/*  10:    */ public class TokenStreamSelector
/*  11:    */   implements TokenStream, IASDebugStream
/*  12:    */ {
/*  13:    */   protected Hashtable inputStreamNames;
/*  14:    */   protected TokenStream input;
/*  15: 33 */   protected Stack streamStack = new LList();
/*  16:    */   
/*  17:    */   public TokenStreamSelector()
/*  18:    */   {
/*  19: 37 */     this.inputStreamNames = new Hashtable();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void addInputStream(TokenStream paramTokenStream, String paramString)
/*  23:    */   {
/*  24: 41 */     this.inputStreamNames.put(paramString, paramTokenStream);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public TokenStream getCurrentStream()
/*  28:    */   {
/*  29: 48 */     return this.input;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public TokenStream getStream(String paramString)
/*  33:    */   {
/*  34: 52 */     TokenStream localTokenStream = (TokenStream)this.inputStreamNames.get(paramString);
/*  35: 53 */     if (localTokenStream == null) {
/*  36: 54 */       throw new IllegalArgumentException("TokenStream " + paramString + " not found");
/*  37:    */     }
/*  38: 56 */     return localTokenStream;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Token nextToken()
/*  42:    */     throws TokenStreamException
/*  43:    */   {
/*  44:    */     for (;;)
/*  45:    */     {
/*  46:    */       try
/*  47:    */       {
/*  48: 65 */         return this.input.nextToken();
/*  49:    */       }
/*  50:    */       catch (TokenStreamRetryException localTokenStreamRetryException) {}
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public TokenStream pop()
/*  55:    */   {
/*  56: 74 */     TokenStream localTokenStream = (TokenStream)this.streamStack.pop();
/*  57: 75 */     select(localTokenStream);
/*  58: 76 */     return localTokenStream;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void push(TokenStream paramTokenStream)
/*  62:    */   {
/*  63: 80 */     this.streamStack.push(this.input);
/*  64: 81 */     select(paramTokenStream);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void push(String paramString)
/*  68:    */   {
/*  69: 85 */     this.streamStack.push(this.input);
/*  70: 86 */     select(paramString);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void retry()
/*  74:    */     throws TokenStreamRetryException
/*  75:    */   {
/*  76: 97 */     throw new TokenStreamRetryException();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void select(TokenStream paramTokenStream)
/*  80:    */   {
/*  81:102 */     this.input = paramTokenStream;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void select(String paramString)
/*  85:    */     throws IllegalArgumentException
/*  86:    */   {
/*  87:106 */     this.input = getStream(paramString);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getEntireText()
/*  91:    */   {
/*  92:111 */     return ASDebugStream.getEntireText(this.input);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public TokenOffsetInfo getOffsetInfo(Token paramToken)
/*  96:    */   {
/*  97:116 */     return ASDebugStream.getOffsetInfo(this.input, paramToken);
/*  98:    */   }
/*  99:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenStreamSelector
 * JD-Core Version:    0.7.0.1
 */