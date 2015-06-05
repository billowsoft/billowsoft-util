package com.billowsoft.util.common;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

	public static <T> List<List<T>> getBatches(List<T> originalList,
			int batchSize) {

		List<List<T>> result = new ArrayList<List<T>>();
		if (originalList == null || originalList.isEmpty()) {
			return result;
		}

		int originalListSize = originalList.size();
		int numberOfBatches = originalListSize / batchSize + 1;
		int start = 0;
		int end;
		for (int i = 0; i < numberOfBatches; i++) {
			end = start + batchSize;
			if(end > originalListSize){
				end = originalListSize;
			}
			List<T> batch = originalList.subList(start, end);
			result.add(batch);
			start = end;
		}

		return result;
	}
}
