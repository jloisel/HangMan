package com.jloisel.hangman;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class HangManTest {
	@Mock
	private Reader reader;
	@Mock
	private Writer writer;
	
	private static final int DEFAULT_COUNT = 10;
	private static final Set<Character> EMPTY_INPUTS = Sets.newHashSet();
	
	@Test
	public void shouldNotMatchVWithLondon() throws IOException {
		final String secretWord = "LONDON";
		final char input = 'V';
		final int count = DEFAULT_COUNT;
		final int expectedCount = count - 1;
		final Set<Character> inputs = EMPTY_INPUTS;
		final String expectedDisplayWord = "******";
		
		verifyHangManGame(secretWord, input, count, expectedCount, inputs,
				expectedDisplayWord);
	}
	
	@Test
	public void shouldMatchPInParis() throws IOException {
		final String secretWord = "PARIS";
		final char input = 'P';
		final int count = DEFAULT_COUNT;
		final int expectedCount = count;
		final Set<Character> inputs = EMPTY_INPUTS;
		final String expectedDisplayWord = "P****";
		
		verifyHangManGame(secretWord, input, count, expectedCount, inputs,
				expectedDisplayWord);
	}
	
	@Test
	public void shouldMatchEInJeremy() throws IOException {
		final String secretWord = "JEREMY";
		final char input = 'E';
		final int count = DEFAULT_COUNT;
		final int expectedCount = count;
		final Set<Character> inputs = EMPTY_INPUTS;
		final String expectedDisplayWord = "*E*E**";
		
		verifyHangManGame(secretWord, input, count, expectedCount, inputs,
				expectedDisplayWord);
	}
	
	@Test
	public void shouldMatchRInJeremy() throws IOException {
		final String secretWord = "JEREMY";
		final char input = 'Z';
		final int count = 5;
		final int expectedCount = count - 1;
		final Set<Character> inputs = Sets.newHashSet('J', 'E');
		final String expectedDisplayWord = "JE*E**";
		
		verifyHangManGame(secretWord, input, count, expectedCount, inputs,
				expectedDisplayWord);
	}

	/**
	 * @param secretWord secret word to guess
	 * @param input input character
	 * @param count pending tries
	 * @param expectedCount expected pending tries after try
	 * @param inputs already guessed inputs
	 * @param expectedDisplayWord expected word to display
	 * @throws IOException
	 */
	private void verifyHangManGame(
			final String secretWord, final char input,
			final int count, final int expectedCount,
			final Set<Character> inputs, final String expectedDisplayWord)
			throws IOException {
		final HangMan game = new HangMan(count, secretWord, inputs);
		when(reader.read()).thenReturn((int) input);
		game.input(reader);
		game.print(writer);
		
		verify(writer).write(HangMan.PENDING_COUNT + expectedCount);
		verify(writer).write("\r\n");
		verify(writer).write(HangMan.SECRET_WORD + expectedDisplayWord);
	}
}
