package de.hsnr.inr.sir.textprocessing;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

public class Tokenizer {

	private static String DEFAULT_SPLIT_CHARS = ". ,;:'!?\"\n";
	
	public static List<String> tokenize(File f) throws IOException{
		String filestr = Files.asCharSource(f, Charsets.UTF_8).read()
				.toLowerCase();
		return split(filestr);
	}
	
	private static List<String> split(String str){
		return Lists.newArrayList(Splitter.on(CharMatcher.anyOf(DEFAULT_SPLIT_CHARS))
				.omitEmptyStrings()
				.trimResults()
				.split(str));
	}
}
