package hello.service;

import java.util.List;

import hello.dao.MoneyBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hello.domain.MoneyBook;
import hello.repository.MoneyBookRepository;

@Service
@Transactional
public class MoneyBookService {

	@Autowired
	MoneyBookRepository moneyBookRepository;

	@Autowired
	MoneyBookMapper moneyBookMapper;

	@Autowired
	MoneyBook2Service moneyBook2Service;

	public List<MoneyBook> getMoneyBookList() {
		return moneyBookRepository.findAll();
	}

	@Transactional
	public MoneyBook getMoneyBookById(Long id) {
		return moneyBookRepository.findOne(id);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public MoneyBook createMoneyBook(MoneyBook moneyBook) {
		return moneyBookRepository.saveAndFlush(moneyBook);
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
	public MoneyBook saveAndOccurRuntimeException(MoneyBook moneyBook) throws Exception {
		MoneyBook moneyBookResult = moneyBookRepository.save(moneyBook);
		occurRuntimeException();
		return moneyBookResult;
	}

	@Transactional
	public MoneyBook saveAndOccurDataAccessException(MoneyBook moneyBook) {
		// success
		this.createMoneyBookRequiresNew(moneyBook);


		// fail
		MoneyBook moneyBook2 = new MoneyBook();
		moneyBook2.setId(moneyBook.getId()+1000);
		moneyBook2.setTitle("error title");
		for(int i=0;i<1000;i++) {
			moneyBook2.setTitle(moneyBook2.getTitle() + i);
		}
		return createMoneyBook(moneyBook2);
	}

	@Transactional
	public MoneyBook logic5(MoneyBook moneyBook) {
		// success
		createMoneyBook(moneyBook);

		moneyBook.setId(moneyBook.getId() + 1);
		for(int i=0;i<1000;i++) {
			moneyBook.setTitle(moneyBook.getTitle() + i);
		}

		// fail
		MoneyBook moneyBookResult = createMoneyBook(moneyBook);

		// but first save is pass!
		return moneyBookResult;
	}

	@Transactional
	public List<MoneyBook> getMoneyBookListByMapper() {
		return moneyBookMapper.selectAll();
	}

	@Transactional
	public int createMoneyBookByMapper(MoneyBook moneyBook) {
		return moneyBookMapper.insert(moneyBook);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int createMoneyBookByMapperRequiredNew(MoneyBook moneyBook) {
		return moneyBookMapper.insert(moneyBook);
	}

	@Transactional
	public int createAndOccurDataAccessException(MoneyBook moneyBook) {
		// success
		createMoneyBookByMapper(moneyBook);

		// fail
		MoneyBook moneyBook2 = new MoneyBook();
		moneyBook2.setId(moneyBook.getId()+1000);
		moneyBook2.setTitle("error title");
		for(int i=0;i<1000;i++) {
			moneyBook2.setTitle(moneyBook2.getTitle() + i);
		}
		return createMoneyBookByMapperRequiredNew(moneyBook2);
	}


	public void occurException() throws Exception {
		throw new Exception("Occur Exception");
	}

	public void occurRuntimeException() {
		throw new RuntimeException("Some Runtime Logging Error");
	}

}
