package hello.service;

import hello.domain.MoneyBook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MoneyBookServiceTest {

	static Long testId = 1L;
	@Autowired
	MoneyBookService moneyBookService;

	@Autowired
	PlatformTransactionManager platformTransactionManager;

	@Test
	public void testException() {
		MoneyBook moneyBook = getMoneyBook();
		try {
			moneyBookService.testException(moneyBook);
		} catch (Exception e) {

		}

		Assert.assertTrue(moneyBook.getId() == moneyBookService.getMoneyBookById(moneyBook.getId()).getId());
	}

	@Test
	public void testRollbackForException() {
		MoneyBook moneyBook = getMoneyBook();
		try {
			moneyBookService.testRollbackForException(moneyBook);
		} catch (Exception e) {

		}

		Assert.assertTrue(moneyBookService.getMoneyBookById(moneyBook.getId()) == null);
	}

	@Test
	public void testRuntimeException() {
		MoneyBook moneyBook = getMoneyBook();
		Long id = moneyBook.getId();
		try {
			moneyBookService.testRuntimeException(moneyBook);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(moneyBookService.getMoneyBookById(id) == null);
	}

	@Test
	public void testRequiresNew() {
		MoneyBook moneyBook = getMoneyBook();
		try {
			moneyBookService.testRequiresNew(moneyBook);
		} catch (Exception e) {

		}
		Long id = moneyBook.getId();
		Assert.assertTrue(id.equals(moneyBookService.getMoneyBookById(id).getId()));
		Assert.assertTrue(moneyBookService.getMoneyBookById(id + 1000) == null);
	}

	@Test
	public void testSupportsExistTransaction() {
		MoneyBook moneyBook = getMoneyBook();
		try {
			moneyBookService.testSupportsExistTransaction(moneyBook);
		} catch (Exception e) {

		}
		Long id = moneyBook.getId();
		Assert.assertTrue(moneyBookService.getMoneyBookById(id) == null);
	}

	@Test
	public void testSupportsNotExistTransaction() {
		MoneyBook moneyBook = getMoneyBook();
		try {
			moneyBookService.testSupportsNotExistTransaction(moneyBook);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Long id = moneyBook.getId();
		Assert.assertTrue(moneyBookService.getMoneyBookById(id) != null);
	}

	@Test
	public void testMandatoryExistTransaction() {
		MoneyBook moneyBook = getMoneyBook();

		moneyBookService.testMandatoryExistTransaction(moneyBook);
		Long id = moneyBook.getId();
		Assert.assertTrue(moneyBookService.getMoneyBookById(id) != null);
	}

	@Test(expected = IllegalTransactionStateException.class)
	public void testMandatoryNotExistTransaction() {
		MoneyBook moneyBook = getMoneyBook();

		moneyBookService.testMandatoryNotExistTransaction(moneyBook);
	}

	@Test(expected = IllegalTransactionStateException.class)
	public void testNeverExistTransaction() {
		MoneyBook moneyBook = getMoneyBook();

		moneyBookService.testNeverExistTransaction(moneyBook);
	}

	@Test
	public void testNeverNotExistTransaction() {
		MoneyBook moneyBook = getMoneyBook();

		moneyBookService.testNeverNotExistTransaction(moneyBook);
		Long id = moneyBook.getId();
		Assert.assertTrue(moneyBookService.getMoneyBookById(id) != null);
	}

	@Test
	public void testNestedOccurErrorFirst() {
		MoneyBook moneyBook1 = getMoneyBookOccurTitleError();
		MoneyBook moneyBook2 = getMoneyBook();
		moneyBookService.testNestedOccurErrorFirst(moneyBook1, moneyBook2);

		Assert.assertTrue(moneyBookService.getMoneyBookById(moneyBook1.getId()) == null);
		Assert.assertTrue(moneyBookService.getMoneyBookById(moneyBook2.getId()) == null);
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

	MoneyBook getMoneyBookOccurTitleError() {
		MoneyBook moneyBook = getMoneyBook();
		for (int i = 0; i < 100; i++) {
			moneyBook.setTitle(moneyBook.getTitle() + i);
		}
		return moneyBook;
	}

}
