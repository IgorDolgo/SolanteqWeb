package ru.solanteq.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table
@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Integer id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "Surname", nullable = false, length = 100)
	private String surname;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "Name", nullable = false, length = 100)
	private String name;
	@Size(max = 100)
	@Column(name = "Patronymic", length = 100)
	private String patronymic;
	@Basic(optional = false)
	@NotNull
	@Column(name = "DateOfBirth", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	@JoinColumn(name = "Position", referencedColumnName = "id", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Position position;

	public Employee() {
	}

	public Employee(Integer id, String surname, String name, Date dateOfBirth, Position position) {
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.dateOfBirth = (Date) dateOfBirth.clone();
		this.position = position;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		return (id != null ? id.hashCode() : 0);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Employee)) {
			return false;
		}
		Employee other = (Employee) object;
		return (id != null) && id.equals(other.getId());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [ id=" + id + ", Surname=" + surname + ", Name=" + name
				 + ", Patronymic=" + patronymic + ", DatOfBirth=" + dateOfBirth + ", Position=" + position + " ]";
	}
}
