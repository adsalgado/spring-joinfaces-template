package mx.sadead.spring.joinfaces.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Value;

import mx.sadead.spring.joinfaces.app.domain.Country;

@Named
@SessionScoped
public class App implements Serializable {

    @Value("${joinfaces.primefaces.theme}")
    private String theme;
    private String cookieName;
    private String cookieTheme;
    private String currentTheme;
    
    private String contextPath;
    
    private boolean darkMode = false;
    private String inputStyle = "outlined";
    private Country locale = new Country(0, Locale.US);

    @PostConstruct
    public void init() {
//        contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        contextPath = "jsft";
        cookieName = "primefaces-theme-" + contextPath.substring(1);
        cookieTheme = "color-theme-" + contextPath.substring(1);

        Map<String, Object> mapCookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        if (mapCookies != null && mapCookies.get(cookieName) != null) {
            Cookie cookie = (Cookie) mapCookies.get(cookieName);
            currentTheme = cookie.getValue();
        } else if (theme != null) {
            currentTheme = theme;
        } else {
            currentTheme = "saga";
        }

    }
    
    public String getTheme() {
        return currentTheme;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public void setTheme(String theme) {
    	Map<String, Object> properties = new HashMap<>();
        properties.put("path", contextPath);
        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(
                cookieName, currentTheme, properties);
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