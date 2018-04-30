package gui.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class CssLoader {
	
	public CssLoader(String filename) throws IOException {
		StyleSheet ss = new StyleSheet();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		ss.loadRules(br, null);
		br.close();
		HTMLEditorKit kit = new HTMLEditorKit();
	    kit.setStyleSheet(ss);
	}
}
