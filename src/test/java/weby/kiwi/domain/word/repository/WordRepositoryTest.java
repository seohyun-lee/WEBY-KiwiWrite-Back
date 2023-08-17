package weby.kiwi.domain.word.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import weby.kiwi.domain.word.entity.Word;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Test 오류 해결 위해 추가해봄
public class WordRepositoryTest {

    @Autowired
    private WordRepository wordRepository;

    @Test
    public void testSaveAndFindById() {
        Word word = new Word(8, 1, "여름");
        wordRepository.save(word);

        Optional<Word> savedWord = wordRepository.findByWordId(word.getWordId());
        assertTrue(savedWord.isPresent());
        assertEquals("여름", savedWord.get().getWordName());
    }

    @Test
    public void testDelete() {
        Word word = new Word(8, 1, "불꽃");
        wordRepository.save(word);

        wordRepository.delete(word);

        Optional<Word> deletedWord = wordRepository.findByWordId(word.getWordId());
        assertFalse(deletedWord.isPresent());
    }

    @Test
    public void testFindByMonthAndDay() {
        Word word1 = new Word(8, 2, "약속");
        Word word2 = new Word(8, 2, "별");

        wordRepository.save(word1);
        wordRepository.save(word2);

        List<Word> foundWords = wordRepository.findByMonthAndDay(8, 2);
        assertEquals(2, foundWords.size());
    }

    @Test
    public void testFindByWordName() {
        Word word = new Word(8, 3, "풀");
        wordRepository.save(word);

        Optional<Word> foundWord = wordRepository.findByWordName("풀");
        assertTrue(foundWord.isPresent());
        assertEquals("풀", foundWord.get().getWordName());
    }

    @Test
    public void testFindByNonExistingWordName() {
        Optional<Word> foundWord = wordRepository.findByWordName("없는단어");
        assertFalse(foundWord.isPresent());
    }
}