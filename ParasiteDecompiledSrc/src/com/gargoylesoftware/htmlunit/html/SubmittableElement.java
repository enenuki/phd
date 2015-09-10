package com.gargoylesoftware.htmlunit.html;

import com.gargoylesoftware.htmlunit.util.NameValuePair;

public abstract interface SubmittableElement
{
  public abstract NameValuePair[] getSubmitKeyValuePairs();
  
  public abstract void reset();
  
  public abstract void setDefaultValue(String paramString);
  
  public abstract String getDefaultValue();
  
  public abstract void setDefaultChecked(boolean paramBoolean);
  
  public abstract boolean isDefaultChecked();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.SubmittableElement
 * JD-Core Version:    0.7.0.1
 */