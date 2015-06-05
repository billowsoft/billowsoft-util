package com.billowsoft.util.common;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ListUtilTest {

	@Test
	public void testGetBatches(){
		// null list
		List<String> nullList = null;
		List<List<String>> result = ListUtil.getBatches(nullList, 0);
		Assert.assertEquals(0, result.size());
		
		// empty list
		result = ListUtil.getBatches(new ArrayList<String>(), 0);
		Assert.assertEquals(0, result.size());
		
		// <1 batch
		List<String> originalList = new ArrayList<String>();
		originalList.add("1");
		originalList.add("2");
		result = ListUtil.getBatches(originalList, originalList.size() + 1);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(originalList, result.get(0));
		
		// >1 batch
		originalList = new ArrayList<String>();
		originalList.add("1");
		originalList.add("2");
		originalList.add("3");
		originalList.add("4");
		result = ListUtil.getBatches(originalList, 3);
		Assert.assertEquals(2, result.size());
	}
}
