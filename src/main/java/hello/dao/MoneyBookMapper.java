package hello.dao;

import hello.domain.MoneyBook;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MoneyBookMapper {

    @Select("SELECT * FROM MONEYBOOK")
    List<MoneyBook> selectAll();

    @Insert("INSERT INTO MONEYBOOK ( id, title, value, comment ) VALUES ( #{id}, #{title}, #{value}, #{comment} )")
    int insert(MoneyBook moneyBook);
}
