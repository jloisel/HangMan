package com.jloisel.hangman;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.annotations.VisibleForTesting;

public class HangMan {
	@VisibleForTesting
	static final String SECRET_WORD = "Secret word: ";
	@VisibleForTesting
	static final char STAR = '*';
	@VisibleForTesting
	static final String PENDING_COUNT = "Pending count: ";
	
	private int count;
	private final String secretWord;
	private final Set<Character> inputs;
	
	public HangMan(
			final int count, 
			final String secretWord, 
			final Set<Character> inputs) {
		super();
		this.count = count;
		this.secretWord = secretWord;
		this.inputs = inputs;
	}

	/**
	 * Input a new character into the game.
	 * 
	 * @param input character being input
	 * @throws IOException 
	 */
	public void input(final Reader reader) throws IOException {
		final char input = (char) reader.read();
		if(StringUtils.contains(secretWord, input)) {
			inputs.add(input);
		} else {
			count--;
		}
	}
	
	public void print(final Writer writer) throws IOException {
		writer.write(PENDING_COUNT + count);
		writer.write("\r\n");
		
		final StringBuilder b = new StringBuilder();
		for(final char aChar : secretWord.toCharArray()) {
			b.append((inputs.contains(aChar) ? aChar : STAR));
		}
		writer.write(SECRET_WORD + b.toString());
	}
}
