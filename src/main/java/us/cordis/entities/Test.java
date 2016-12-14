package us.cordis.entities;

import java.util.ArrayList;
import java.util.List;

import us.cordis.rest.model.ColumnList;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<ColumnList> list = new ArrayList<ColumnList>();
		ColumnList cl=new ColumnList();
		cl.setKey("aa");
		cl.setValue("bb");
		list.add(cl);
		ColumnList cl2=new ColumnList();
		cl2.setKey("kk");
		cl2.setValue("bb");
		list.add(cl2);
		String str =list.toString().substring(1, list.toString().length()-1);
		System.out.println(str);
		System.out.println(":"+str.replaceAll(", ", ",:"));
		
	}

}
