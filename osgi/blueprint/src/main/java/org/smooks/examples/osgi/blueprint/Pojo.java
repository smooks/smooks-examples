package org.smooks.examples.osgi.blueprint;

import org.smooks.Smooks;
import org.smooks.examples.osgi.blueprint.model.Order;

public class Pojo
{
    private Smooks smooks;
    private String input;
    
    public Pojo(final Smooks smooks, final String input)
    {
        this.smooks = smooks;
        this.input = input;
    }
    
    public void start()
    {
        System.out.println("Pojo start");
        Order order = filter();
        ExampleUtil.printOrder(order);
    }
    
    public Order filter()
    {
        return ExampleUtil.performFiltering(input, smooks);
    }
    
    public void stop()
    {
        System.out.println("Pojo stop");
        smooks.close();
    }
    
}
