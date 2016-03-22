/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.solanteq.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author igordolgo
 */
@Entity
@Table
@NamedQueries({
	@NamedQuery(name = "Position.findAll", query = "SELECT p FROM Position p"),
	@NamedQuery(name = "Position.findByName", query = "SELECT p FROM Position p WHERE p.name = :name")})
public class Position implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Short id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "Name", nullable = false, length = 50)
	private String name;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "position", fetch = FetchType.LAZY)
	private List<Employee> employeeList;

	public Position() {
	}

	public Position(Short id, String name) {
		this.id = id;
		this.name = name;
	}

	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	@Override
	public int hashCode() {
		return (name != null ? name.hashCode() : 0);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Position)) {
			return false;
		}
		Position other = (Position) object;
		return (name != null) && name.equals(other.getName());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [ id=" + id + ", Name=" + name + " ]";
	}
}
