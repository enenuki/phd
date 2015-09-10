package org.cyberneko.html;

import org.apache.xerces.xni.parser.XMLComponent;

public abstract interface HTMLComponent
  extends XMLComponent
{
  public abstract Boolean getFeatureDefault(String paramString);
  
  public abstract Object getPropertyDefault(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.HTMLComponent
 * JD-Core Version:    0.7.0.1
 */