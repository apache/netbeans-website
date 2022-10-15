/*
 * CategoryNode.java
 *
 * Created on September 21, 2006, 9:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 * To understand this class, see https://platform.netbeans.org/tutorials/nbm-nodesapi3.html
 */

package org.netbeans.shapesample.palette;

import org.openide.nodes.AbstractNode;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Geertjan Wielenga
 */
public class CategoryNode extends AbstractNode {

    /** Creates a new instance of CategoryNode */
    public CategoryNode( Category category ) {
        super( new ShapeChildren(category), Lookups.singleton(category) );
        setDisplayName(category.getName());
    }
}