package antlr;

import antlr.collections.impl.Vector;
import java.util.Enumeration;

abstract interface TokenManager
{
  public abstract Object clone();
  
  public abstract void define(TokenSymbol paramTokenSymbol);
  
  public abstract String getName();
  
  public abstract String getTokenStringAt(int paramInt);
  
  public abstract TokenSymbol getTokenSymbol(String paramString);
  
  public abstract TokenSymbol getTokenSymbolAt(int paramInt);
  
  public abstract Enumeration getTokenSymbolElements();
  
  public abstract Enumeration getTokenSymbolKeys();
  
  public abstract Vector getVocabulary();
  
  public abstract boolean isReadOnly();
  
  public abstract void mapToTokenSymbol(String paramString, TokenSymbol paramTokenSymbol);
  
  public abstract int maxTokenType();
  
  public abstract int nextTokenType();
  
  public abstract void setName(String paramString);
  
  public abstract void setReadOnly(boolean paramBoolean);
  
  public abstract boolean tokenDefined(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.TokenManager
 * JD-Core Version:    0.7.0.1
 */