/*
 * Shape.java
 *
 * Created on September 21, 2006, 9:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 * To understand this class, see https://platform.netbeans.org/tutorials/nbm-nodesapi3.html
 */

package org.netbeans.shapesample.palette;

/**
 *
 * @author Geertjan Wielenga
 */
public class Shape {

    private Integer number;
    private String category;
    private String title;
    private String image;

    /** Creates a new instance of Instrument */
    public Shape() {
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}