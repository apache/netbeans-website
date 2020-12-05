/*
 * CategoryChildren.java
 *
 * Created on September 21, 2006, 9:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 * To understand this class, see https://platform.netbeans.org/tutorials/nbm-nodesapi3.html
 */

package org.netbeans.shapesample.palette;

import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Geertjan Wielenga
 */
public class CategoryChildren extends Children.Keys {

    private String[] Categories = new String[]{
        "Shapes"};

    public CategoryChildren() {
    }

    protected Node[] createNodes(Object key) {
        Category obj = (Category) key;
        return new Node[] { new CategoryNode(obj) };
    }

    protected void addNotify() {
        super.addNotify();
        Category[] objs = new Category[Categories.length];
        for (int i = 0; i < objs.length; i++) {
            Category cat = new Category();
            cat.setName(Categories[i]);
            objs[i] = cat;
        }
        setKeys(objs);
    }

}