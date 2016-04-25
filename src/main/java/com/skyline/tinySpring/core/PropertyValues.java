package com.skyline.tinySpring.core;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

	private final List<PropertyValue> propertyValueList = 
			new ArrayList<>();
	
	public PropertyValues() {
	}
	
	public void addPropertyValue(PropertyValue pv) {
		//TODO 重复情况还未考虑
		this.propertyValueList.add(pv);
	}
	
	public List<PropertyValue> getPropertyValueList() {
		return propertyValueList;
	}
}
