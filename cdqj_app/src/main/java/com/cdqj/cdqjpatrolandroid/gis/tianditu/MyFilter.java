package com.cdqj.cdqjpatrolandroid.gis.tianditu;

import java.io.File;
import java.io.FilenameFilter;

public class MyFilter implements FilenameFilter {
	String _name="";
	public MyFilter(String name){
		_name=name;
	}
	public boolean accept(File dir, String filename) {
		return filename.toLowerCase().endsWith(_name);
	}
}
