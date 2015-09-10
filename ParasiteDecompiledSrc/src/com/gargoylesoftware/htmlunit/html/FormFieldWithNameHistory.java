package com.gargoylesoftware.htmlunit.html;

import java.util.Collection;

public abstract interface FormFieldWithNameHistory
{
  public abstract String getOriginalName();
  
  public abstract Collection<String> getPreviousNames();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.FormFieldWithNameHistory
 * JD-Core Version:    0.7.0.1
 */