package de.hslu.infomac.notenverwaltung.listeners;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.hslu.infomac.notenverwaltung.entities.Benutzer;
import de.hslu.infomac.notenverwaltung.enumerations.Rolle;

/**
 * @author Igor Eslengert
 *
 */
@WebFilter(filterName = "AuthentifizierungFilter", urlPatterns = { "*.xhtml" })
public class AuthentifizierungFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			HttpSession ses = req.getSession(false);
			String reqURI = req.getRequestURI();
			boolean sendToLoginPage = false;
			Benutzer benutzer = null;
			if (ses != null) {
				benutzer = (Benutzer) ses.getAttribute("user");
			}

			if (reqURI.indexOf("/login.xhtml") >= 0 || reqURI.indexOf("/impressum") >= 0
					|| reqURI.indexOf("/kontakt") >= 0 || reqURI.contains("javax.faces.resource")) {
				chain.doFilter(request, response);
			} else if (benutzer != null) {
				if (reqURI.indexOf("/passwortAendern") >= 0) {
					chain.doFilter(request, response);
				} else if (!benutzer.isErsterLogin()) {
					if (benutzer.hasRolle(Rolle.Schulleiter)) {
						chain.doFilter(request, response);
					} else if (benutzer.hasRolle(Rolle.Lehrer) && (reqURI.indexOf("/eingeloggterBenutzerProfil") >= 0
							|| reqURI.indexOf("/sorgeberechtigter") >= 0 || reqURI.indexOf("/notenansicht.xhtml") >= 0|| reqURI.indexOf("/notenansichtVonSchueler.xhtml") >= 0
							|| reqURI.indexOf("/klasseAnzeigen") >= 0 || reqURI.indexOf("/klassenAnzeigen") >= 0
							|| reqURI.indexOf("/klausurErfassen") >= 0 || reqURI.indexOf("/meineFaecher") >= 0
							|| reqURI.indexOf("/schuelerProfil") >= 0
							|| reqURI.indexOf("/schuelerVonKlasseAnzeigen") >= 0
							|| reqURI.indexOf("/klassenkonferenz") >= 0)) {
						chain.doFilter(request, response);
					} else if (benutzer.hasRolle(Rolle.Schueler) && (reqURI.indexOf("/eingeloggterBenutzerProfil") >= 0
							|| reqURI.indexOf("/sorgeberechtigter") >= 0 || reqURI.indexOf("/notenansicht.xhtml") >= 0|| reqURI.indexOf("/notenansichtAlsSchueler.xhtml") >= 0)) {
						chain.doFilter(request, response);
					} else {
						sendToLoginPage = true;
					}

				} else {
					sendToLoginPage = true;
				}
			} else {
				sendToLoginPage = true;
			}
			if (sendToLoginPage) {
				res.sendRedirect(req.getContextPath() + "/login.xhtml");
			}
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		}
	}

	@Override
	public void destroy() {

	}

}
