package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.generators.Cap;
import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	
	Set<Capitalist> entireHierarchy = new HashSet<>();
	
    /**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false 
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element 
     * <p>
     * If the given element has no parent but is a Parent itself,
     * add it to the hierarchy
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
    @Override
    public boolean add(Capitalist capitalist) {
    	    	    	
    	// Element is already in hierarchy
    	if (capitalist == null || has(capitalist)) {
    		return false;
    	}
    	
    	if (capitalist.hasParent()) {
    		entireHierarchy.add(capitalist);
    		if (!entireHierarchy.contains(capitalist.getParent())) {
    			add(capitalist.getParent());
    		}
    	} else if (!capitalist.hasParent() && capitalist instanceof WageSlave) {
    		return false;
    	} else {
    		entireHierarchy.add(capitalist);
    	}
    	return true;
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
    	
    	if (capitalist == null) {
    		return false;
    	}
    	return entireHierarchy.contains(capitalist);
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() { 
    	Set<Capitalist> entireElementSet = new HashSet<>(entireHierarchy);
        for (Capitalist person : entireHierarchy) {
            add(person);
            while (person != null) {
                entireElementSet.add(person);
                person = person.getParent();
            }
        }
        return entireElementSet;
    	
    	
    	
//    	Set<Capitalist> allElements = new HashSet<>(entireHierarchy);
//    	return allElements;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	Set<FatCat> isParent = new HashSet<>();
    	
    	if (entireHierarchy.isEmpty()) {
    		return isParent;
    	}
    	    	
    	Set<FatCat> allParents = new HashSet<>();
        for (Capitalist person : entireHierarchy) {
            add(person);            
            FatCat parent = person instanceof FatCat ? (FatCat) person : person.getParent();
            while (parent != null) {
                allParents.add(parent);
                parent = parent.getParent();
            }
        }
        return allParents;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
        
    	Set<Capitalist> childrenSet = new HashSet<>();
    	
    	if (!has(fatCat)) {
    		return childrenSet;
    	}
    	
    	for (Capitalist person : entireHierarchy) {
    		if (person.getParent() == fatCat) {
    			childrenSet.add(person);
    		}
    	}
    	return childrenSet;
    	
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
    	
    	HashMap<FatCat, Set<Capitalist>> hierarchyMap = new HashMap<>();
    	
    	if (entireHierarchy.isEmpty()) {
    		return hierarchyMap;
    	}
    	    	
    	Set<FatCat> allParents = getParents();

    	for (FatCat parent : allParents) {
    		Set<Capitalist> children = getChildren(parent);
            hierarchyMap.put(parent, children);
    	}  	
    	return hierarchyMap;
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {
        List<FatCat> entireParentChainList = new LinkedList<>();
        
        if (capitalist == null) {
        	return entireParentChainList;
        }
                
        // Has no parent
        if (!capitalist.hasParent() || !entireHierarchy.contains(capitalist.getParent())) {
        	return entireParentChainList;
        }
        
        FatCat parent = capitalist.getParent();
        while (parent != null) {
            entireParentChainList.add(parent);
            parent = parent.getParent();
        }
        return entireParentChainList;        
    }
}
