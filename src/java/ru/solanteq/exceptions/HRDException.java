/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.solanteq.exceptions;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.ejb.EJBException;

/**
 *
 * @author igordolgo
 */
public class HRDException extends Exception {
	private static final String EX_FILE = "ru.solanteq.i18n.exceptions";
	private static final ResourceBundle EX_BUNDLE = ResourceBundle.getBundle(EX_FILE, Locale.getDefault());
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return super.getMessage() + (getCause() != null ? " : " + getCause().getMessage() : "");
	}
	public static enum Exceptions {
		WRONG_PATH;
		private final String msg;

		private Exceptions() {
			String mes;
			try {
				mes = EX_BUNDLE.getString(this.toString());
			} catch (MissingResourceException ignore) {
				mes = this.toString();
			}
			msg = mes;
		}

		public HRDException create(Object... details) {
			return new HRDException(String.format(msg, details));
		}

		public HRDException create(Exception cause, Object... details) {
			return new HRDException(String.format(msg, details), cause);
		}

		public String getMsg() {
			return msg;
		}
	}

	private HRDException(String message) {
		super(message);
	}

	private HRDException(String message, Exception cause) {
		super(message, cause);
	}
}
