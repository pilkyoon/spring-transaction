package hello.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "MONEYBOOK")
public class MoneyBook {

	@Id
//	@GeneratedValue
	Long id;

	@Column(name = "TITLE")
	String title;

	@Column(name = "VALUE")
	Long value;

	@Column(name = "COMMENT")
	String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		MoneyBook moneyBook = (MoneyBook) o;

		if (id != null ? !id.equals(moneyBook.id) : moneyBook.id != null)
			return false;
		if (title != null ? !title.equals(moneyBook.title) : moneyBook.title != null)
			return false;
		if (value != null ? !value.equals(moneyBook.value) : moneyBook.value != null)
			return false;
		return comment != null ? comment.equals(moneyBook.comment) : moneyBook.comment == null;

	}

	@Override public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (value != null ? value.hashCode() : 0);
		result = 31 * result + (comment != null ? comment.hashCode() : 0);
		return result;
	}

	@Override public String toString() {
		return com.google.common.base.MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("title", title)
				.add("value", value)
				.add("comment", comment)
				.toString();
	}
}