package com.alibaba.excel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.alibaba.excel.metadata.IndexValue;

/**
 * 去除空Cell
 * @author jipengfei
 */
public class IndexValueConverter {
    public static List<String> converter(List<IndexValue> i_list) {

        List<String> tem = new ArrayList<String>();

        char[] start = {'@'};
        int j = 0;
        for (; j < i_list.size(); j++) {
            IndexValue currentIndexValue = i_list.get(j);
            char[] currentIndex = currentIndexValue.getV_index().replaceAll("[0-9]", "").toCharArray();
            if (j > 0) {
                start = i_list.get(j - 1).getV_index().replaceAll("[0-9]", "").toCharArray();
            }
            int deep = subtraction26(currentIndex, start);
            int k = 0;
            for (; k < deep - 1; k++) {
                tem.add(null);
            }
            tem.add(currentIndexValue.getV_value());
        }
        return tem;
    }

    private static int subtraction26(char[] currentIndex, char[] beforeIndex) {
        int result = 0;

        Stack<Character> currentStack = new Stack<Character>();
        Stack<Character> berforStack = new Stack<Character>();

        for (int i = 0; i < currentIndex.length; i++) {
            currentStack.push(currentIndex[i]);
        }
        for (int i = 0; i < beforeIndex.length; i++) {
            berforStack.push(beforeIndex[i]);
        }
        int i = 0;
        char beforechar = '@';
        while (!currentStack.isEmpty()) {
            char currentChar = currentStack.pop();
            if (!berforStack.isEmpty()) {
                beforechar = berforStack.pop();
            }
            int n = currentChar - beforechar;
            if(n<0){
                n = n+26;
                if(!currentStack.isEmpty()){
                    char borrow = currentStack.pop();
                    char newBorrow =(char)(borrow -1);
                    currentStack.push(newBorrow);
                }
            }


            result += n * Math.pow(26, i);
            i++;
            beforechar='@';
        }

        return result;
    }
}
