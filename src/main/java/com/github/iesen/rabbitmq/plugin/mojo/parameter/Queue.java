package com.github.iesen.rabbitmq.plugin.mojo.parameter;

import java.util.List;

/**
 *
 */
public class Queue {

    private String name;
    private List<Binding> bindings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Binding> getBindings() {
        return bindings;
    }

    public void setBindings(List<Binding> bindings) {
        this.bindings = bindings;
    }
}
