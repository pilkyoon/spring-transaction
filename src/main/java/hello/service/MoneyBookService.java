package hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hello.dao.MoneyBookMapper;
import hello.domain.MoneyBook;
import hello.repository.MoneyBookRepository;

@Service
public class MoneyBookService {

	@Autowired
	MoneyBookRepository moneyBookRepository;

	@Autowired
	MoneyBookMapper moneyBookMapper;

	public List<MoneyBook> getMoneyBookList() {
		return moneyBookRepository.findAll();
	}

	@Transactional
	public MoneyBook getMoneyBookById(Long id) {
		return moneyBookRepository.findOne(id);
	}

	@Transactional
	public MoneyBook createMoneyBook(MoneyBook moneyBook) {
		return moneyBookRepository.save(moneyBook);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public MoneyBook createMoneyBookRequiresNew(MoneyBook moneyBook) {
		return moneyBookRepository.save(moneyBook);
	}

	@Transactional
	public MoneyBook testException(MoneyBook moneyBook) throws Exception {
		MoneyBook moneyBookResult = moneyBookRepository.save(moneyBook);
		occurException();
		return moneyBookResult;
	}

	@Transactional(rollbackFor = Exception.class)
	public MoneyBook testRollbackForException(MoneyBook moneyBook) throws Exception {
		MoneyBook moneyBookResult = moneyBookRepository.save(moneyBook);
		occurException();
		return moneyBookResult;
	}

	@Transactional
	public MoneyBook testRuntimeException(MoneyBook moneyBook) {
		MoneyBook moneyBookResult = moneyBookRepository.save(moneyBook);
		occurRuntimeException();
		return moneyBookResult;
	}

	@Transactional
	public MoneyBook testRequiresNew(MoneyBook moneyBook) {
		// success
		createMoneyBookRequiresNew(moneyBook);

		// fail
		MoneyBook moneyBook2 = new MoneyBook();
		moneyBook2.setId(moneyBook.getId() + 1000);
		moneyBook2.setTitle("error title");
		for (int i = 0; i < 1000; i++) {
			moneyBook2.setTitle(moneyBook2.getTitle() + i);
		}
		return createMoneyBook(moneyBook2);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public MoneyBook testSupports(MoneyBook moneyBook) {
		MoneyBook moneyBookResult = moneyBookRepository.save(moneyBook);
		occurRuntimeException();
		return moneyBookResult;
	}

	@Transactional
	public MoneyBook testSupportsExistTransaction(MoneyBook moneyBook) {
		return testSupports(moneyBook);
	}

	public MoneyBook testSupportsNotExistTransaction(MoneyBook moneyBook) {
		return testSupports(moneyBook);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public MoneyBook testMandatory(MoneyBook moneyBook) {
		return moneyBookRepository.save(moneyBook);
	}

	@Transactional
	public MoneyBook testMandatoryExistTransaction(MoneyBook moneyBook) {
		return testMandatory(moneyBook);
	}

	public MoneyBook testMandatoryNotExistTransaction(MoneyBook moneyBook) {
		return testMandatory(moneyBook);
	}

	@Transactional(propagation = Propagation.NEVER)
	public MoneyBook testNever(MoneyBook moneyBook) {
		return moneyBookRepository.save(moneyBook);
	}

	@Transactional
	public MoneyBook testNeverExistTransaction(MoneyBook moneyBook) {
		return testNever(moneyBook);
	}

	public MoneyBook testNeverNotExistTransaction(MoneyBook moneyBook) {
		return testNever(moneyBook);
	}

	@Transactional(propagation = Propagation.NESTED)
	public MoneyBook testNestedChild(MoneyBook moneyBook) {
		return moneyBookRepository.save(moneyBook);
	}

	@Transactional
	public void testNestedOccurErrorFirst(MoneyBook moneyBook1, MoneyBook moneyBook2) {
		moneyBookRepository.save(moneyBook1);
		testNestedChild(moneyBook2);
	}

	@Transactional
	public void testNestedOccurErrorSecond(MoneyBook moneyBook1, MoneyBook moneyBook2) {
		moneyBookRepository.save(moneyBook1);
		testNestedChild(moneyBook2);
	}

	public void occurException() throws Exception {
		throw new Exception("Occur Exception");
	}

	public void occurRuntimeException() {
		throw new RuntimeException("Some Runtime Logging Error");
	}

}
