package hello.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import hello.domain.MoneyBook;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MoneyBookServiceTest {

	@Autowired
	MoneyBookService moneyBookService;

	static Long testId = 1L;

	@Test
	public void testSaveAndOccurException() {
		MoneyBook moneyBook = getMoneyBook();
		try {
			moneyBookService.saveAndOccurException(moneyBook);
		} catch (Exception e) {

		}

		Assert.assertTrue(moneyBook.getId() == moneyBookService.getMoneyBookById(moneyBook.getId()).getId());
	}

	@Test
	public void testSaveAndOccurExceptionRollbackFor() {
		MoneyBook moneyBook = getMoneyBook();
		try {
			moneyBookService.saveAndOccurExceptionRollbackFor(moneyBook);
		} catch (Exception e) {

		}

		Assert.assertTrue(moneyBookService.getMoneyBookById(moneyBook.getId()) == null);
	}

	@Test
	public void testSaveAndOccurRuntimeException() {
		MoneyBook moneyBook = getMoneyBook();
		try {
			moneyBookService.saveAndOccurRuntimeException(moneyBook);
		} catch (Exception e) {

		}
		Assert.assertTrue(moneyBookService.getMoneyBookById(moneyBook.getId()) == null);
	}

	@Test
	public void testSaveAndOccurDataAccessException() {
		MoneyBook moneyBook = getMoneyBook();
		try {
			moneyBookService.saveAndOccurDataAccessException(moneyBook);
		} catch (Exception e) {

		}
		Long id = moneyBook.getId();
		Assert.assertTrue(id.equals(moneyBookService.getMoneyBookById(id).getId()));
		Assert.assertTrue(moneyBookService.getMoneyBookById(id+1000) == null);
	}


	MoneyBook getMoneyBook() {
		MoneyBook moneyBook = new MoneyBook();
		moneyBook.setId(testId);
		moneyBook.setTitle(testId + " save");
		moneyBook.setValue(testId * 100L);
		moneyBook.setComment("saving salary");
		testId++;
		return moneyBook;
	}

}
