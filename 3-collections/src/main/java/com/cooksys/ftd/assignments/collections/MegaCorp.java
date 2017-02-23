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

    	if (capitalist.hasParent() && !entireHierarchy.contains(capitalist.getParent())) {
    		entireHierarchy.add(capitalist);
    		entireHierarchy.add(capitalist.getParent());
    	}
    	
    	// Element has no parent and can't be a parent itself
    	if (!capitalist.hasParent() && capitalist instanceof WageSlave) {
    		return false;
    	}
    	entireHierarchy.add(capitalist);
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
    	Set<Capitalist> allElements = new HashSet<>(entireHierarchy);
    	return allElements;
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
    	    	
    	if (entireHierarchy.size() == 1) {
    		for (Capitalist person : entireHierarchy) {
    			isParent.add( (FatCat) person);
    		}
    	}
    	        	
    	for (Capitalist person : entireHierarchy) {
    		if (person.hasParent()) {
    			isParent.add(person.getParent());
    		} 
    	}
    	return isParent;
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
    	
    	for (Capitalist person : entireHierarchy) {
    		FatCat personNameKey = (FatCat) person;
    		Set<Capitalist> personChildern = new HashSet<>(getChildren(personNameKey));
    		hierarchyMap.put(personNameKey, personChildern);
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
        List<FatCat> entireParentChainList = new ArrayList<>();
        
        if (capitalist == null) {
        	return entireParentChainList;
        }
        
        // Has no parent
        if (!capitalist.hasParent() || !entireHierarchy.contains(capitalist)) {
        	return entireParentChainList;
        }
        
        Capitalist currentCapitalist = capitalist;
        Capitalist parent = capitalist.getParent();
        
        while (true) {
        	if (currentCapitalist.hasParent()) {
        		entireParentChainList.add((FatCat) parent);
        		currentCapitalist = parent;
        	}
        	break;        	
        }
        return entireParentChainList;        
    }
}
