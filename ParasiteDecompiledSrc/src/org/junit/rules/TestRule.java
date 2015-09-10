package org.junit.rules;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public abstract class TestRule
{
  protected abstract Statement apply(Statement paramStatement, Description paramDescription);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.rules.TestRule
 * JD-Core Version:    0.7.0.1
 */