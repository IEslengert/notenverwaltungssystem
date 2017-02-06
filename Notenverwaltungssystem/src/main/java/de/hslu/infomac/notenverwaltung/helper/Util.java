package de.hslu.infomac.notenverwaltung.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import de.hslu.infomac.notenverwaltung.entities.Benutzer;
import de.hslu.infomac.notenverwaltung.enumerations.Rolle;
import de.hslu.infomac.notenverwaltung.exceptions.CouldNotHashPasswordException;

/**
 * @author Igor Eslengert
 */
public class Util {

	public static String hash(final String passwort) throws CouldNotHashPasswordException {

		if (passwort != null && passwort.trim().length() > 0) {
			try {
				final MessageDigest digest = MessageDigest.getInstance("SHA-256");
				final byte messageDigest[] = digest.digest(passwort.getBytes());
				final StringBuffer sb = new StringBuffer();
				for (final byte element : messageDigest) {
					sb.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
				}
				return sb.toString();
			} catch (final NoSuchAlgorithmException noSuchAlgorithmException) {
				Logger.getLogger("Konnte passwort nicht hashen.");
			}
		}
		throw new CouldNotHashPasswordException("Konnte passwort nicht hashen.");
	}

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static Benutzer getSessionUser() {
		HttpSession session = getSession();
		if (session != null)
			return (Benutzer) session.getAttribute("user");
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public static List<Rolle> getBenutzerRollen() {
		HttpSession session = getSession();
		if (session != null)
			return (List<Rolle>) session.getAttribute("rollen");
		else
			return null;
	}

	/**
	 * Generate a random String with maxlength random characters found in the
	 * ASCII table between 33 and 122 (so it contains every lowercase /
	 * uppercase letters, numbers and some others characters
	 */
	public static String getRandomString(int maxLaenge) {
		String result = "";
		int i = 0, n = 0, min = 33, max = 122;
		while (i < maxLaenge) {
			n = (int) (Math.random() * (max - min) + min);
			if (n >= 33 && n < 123) {
				result += (char) n;
				++i;
			}
		}
		return (result);
	}
}
