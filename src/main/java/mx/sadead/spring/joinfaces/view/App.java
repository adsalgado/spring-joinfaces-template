package mx.sadead.spring.joinfaces.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import mx.sadead.spring.joinfaces.app.domain.Country;

@Component
@ApplicationScope
public class App implements Serializable {

    public static final String CONTEXT_PATH = "/jsft";
    public static final String DARK_MODE = "darkMode";

    @Value("${app.default.theme}")
    private String theme;
    private String cookieName;
    
    
    private boolean darkMode = false;
    private String inputStyle = "outlined";
    private Country locale = new Country(0, Locale.US);

    @PostConstruct
    public void init() {

        cookieName = "primefaces-theme-" + CONTEXT_PATH.substring(1);

        Map<String, Object> mapCookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        if (mapCookies != null) {
        	if (mapCookies.get(cookieName) != null) {
                Cookie cookie = (Cookie) mapCookies.get(cookieName);
                theme = cookie.getValue();        		
        	}
        	if (mapCookies.get(DARK_MODE) != null) {
                Cookie cookie = (Cookie) mapCookies.get(DARK_MODE);
                darkMode = Boolean.parseBoolean(cookie.getValue());        		
        	}
        } 

    }
    
    public String getTheme() {
        return theme;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getInputStyle() {
        return inputStyle;
    }

    public void setInputStyle(String inputStyle) {
        this.inputStyle = inputStyle;
    }

    public String getInputStyleClass() {
        return "filled".equals(this.inputStyle) ? "ui-input-filled" : "";
    }

    public Country getLocale() {
        return locale;
    }

    public void setLocale(Country locale) {
        this.locale = locale;
    }

    public void changeTheme(String theme, boolean darkMode) {
       	Map<String, Object> properties = new HashMap<>();
        properties.put("path", CONTEXT_PATH);
        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(
                cookieName, theme, properties);
        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(
                DARK_MODE, String.valueOf(darkMode), properties);
        this.theme = theme; 
        this.darkMode = darkMode;
    }

    public String getThemeImage() {
        String result = getTheme();
        switch (result) {
            case "nova-light":
                result = "nova.png";
                break;
            case "nova-colored":
                result = "nova-accent.png";
                break;
            case "nova-dark":
                result = "nova-alt.png";
                break;
            case "bootstrap4-blue-light":
                result = "bootstrap4-light-blue.svg";
                break;
            case "bootstrap4-blue-dark":
                result = "bootstrap4-dark-blue.svg";
                break;
            case "bootstrap4-purple-light":
                result = "bootstrap4-light-purple.svg";
                break;
            case "bootstrap4-purple-dark":
                result = "bootstrap4-dark-purple.svg";
                break;
            default:
                result += ".png";
                break;
        }
        return result;
    }
}