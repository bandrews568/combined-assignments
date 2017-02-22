package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
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
    	if (has(capitalist)) {
    		return false;
    	}
    	
    	// Element has parent and that parent is not part of the hierarchy
    	if (capitalist.hasParent() && !entireHierarchy.contains(capitalist)) {
    		entireHierarchy.add(capitalist);
    	}
    	
    	if (!capitalist.hasParent()) {
    		for (Capitalist person : entireHierarchy) {
    			if (person.getParent().equals(capitalist)) { 
    				return true; // Element has no parent but is a parent its self
    			}
    		return false; // Element has no parent and is NOT a parent itself
    		}
    	}    	
    	return true;
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
        return entireHierarchy.contains(capitalist);       	
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
    	return entireHierarchy;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	Set<FatCat> isParent = new HashSet<>();
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
        throw new NotImplementedException();
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
    	
    	HashMap<FatCat, Set<Capitalist>> hierarchyMap = new HashMap<>();
    	
    	if (entireHierarchy.size() == 0) {
    		return hierarchyMap;
    	}
    	
    	for (Capitalist person : entireHierarchy) {
    		FatCat personNameKey = (FatCat) person;    		
    		Set<Capitalist> personParents = new HashSet<>();
    		List<FatCat> getPersonParents = getParentChain(person);
    	}
    	
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
