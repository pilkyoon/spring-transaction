package hello.service;

import hello.dao.MoneyBookMapper;
import hello.domain.MoneyBook;
import hello.repository.MoneyBookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
	public MoneyBook saveAndOccurException(MoneyBook moneyBook) throws Exception {
		MoneyBook moneyBookResult = moneyBookRepository.save(moneyBook);
		occurException();
		return moneyBookResult;
	}

	@Transactional(rollbackFor = Exception.class)
	public MoneyBook saveAndOccurExceptionRollbackFor(MoneyBook moneyBook) throws Exception {
		MoneyBook moneyBookResult = moneyBookRepository.save(moneyBook);
		occurException();
		return moneyBookResult;
	}

	@Transactional
	public MoneyBook saveAndOccurRuntimeException(MoneyBook moneyBook) {
		MoneyBook moneyBookResult = moneyBookRepository.save(moneyBook);
		occurRuntimeException();
		return moneyBookResult;
	}

	@Transactional
	public MoneyBook saveAndOccurDataAccessException(MoneyBook moneyBook) {
		// success
		createMoneyBookRequiresNew(moneyBook);

		// fail
		MoneyBook moneyBook2 = new MoneyBook();
		moneyBook2.setId(moneyBook.getId()+1000);
		moneyBook2.setTitle("error title");
		for(int i=0;i<1000;i++) {
			moneyBook2.setTitle(moneyBook2.getTitle() + i);
		}
		return createMoneyBook(moneyBook2);
	}

	public void occurException() throws Exception {
		throw new Exception("Occur Exception");
	}

	public void occurRuntimeException() {
		throw new RuntimeException("Some Runtime Logging Error");
	}

}
