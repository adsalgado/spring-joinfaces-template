package mx.sadead.spring.joinfaces.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import mx.sadead.spring.joinfaces.app.menu.MenuCategory;
import mx.sadead.spring.joinfaces.app.menu.MenuItem;
import mx.sadead.spring.joinfaces.constants.Constants;
import mx.sadead.spring.joinfaces.session.IUserSessionContext;

@Component
@SessionScope
public class AppMenu {
	
	private final IUserSessionContext userSessionContext;

	private List<MenuCategory> menuCategories;
	private List<MenuItem> menuItems;
	
	public AppMenu(IUserSessionContext userSessionContext) {
		this.userSessionContext = userSessionContext;
	}

	// CHECKSTYLE:OFF
	@PostConstruct
	public void init() {
		menuCategories = new ArrayList<>();
		menuItems = new ArrayList<>();

		// GENERAL CATEGORY START
		List<MenuItem> generalMenuItems = new ArrayList<>();
		generalMenuItems.add(new MenuItem("Get Started", "/inicio.xhtml"));
		
		if (userSessionContext.hasAnyRol(Constants.ROLE_ADMIN)) {
			generalMenuItems.add(new MenuItem("Usuarios", "/aplicacion/catalogos/usuarios.xhtml"));
		}
		if (userSessionContext.hasAnyRol(Constants.ROLE_ADMIN, Constants.ROLE_USER)) {
			generalMenuItems.add(new MenuItem("Opciones", "/aplicacion/catalogos/opciones.xhtml"));
			generalMenuItems.add(new MenuItem("Perfiles", "/aplicacion/catalogos/perfiles.xhtml"));
			generalMenuItems.add(new MenuItem("Cambio password", "/aplicacion/catalogos/cambioPassword.xhtml"));
		}

		menuCategories.add(new MenuCategory("General", generalMenuItems));			
		// GENERAL CATEGORY END

		for (MenuCategory category : menuCategories) {
			for (MenuItem menuItem : category.getMenuItems()) {
				menuItem.setParentLabel(category.getLabel());
				if (menuItem.getUrl() != null) {
					menuItems.add(menuItem);
				}
				if (menuItem.getMenuItems() != null) {
					for (MenuItem item : menuItem.getMenuItems()) {
						item.setParentLabel(menuItem.getLabel());
						if (item.getUrl() != null) {
							menuItems.add(item);
						}
					}
				}
			}
		}
	}

	public List<MenuItem> completeMenuItem(String query) {
		String queryLowerCase = query.toLowerCase();
		List<MenuItem> filteredItems = new ArrayList<>();
		for (MenuItem item : menuItems) {
			if (item.getUrl() != null && (item.getLabel().toLowerCase().contains(queryLowerCase)
					|| item.getParentLabel().toLowerCase().contains(queryLowerCase))) {
				filteredItems.add(item);
			}
			if (item.getBadge() != null) {
				if (item.getBadge().toLowerCase().contains(queryLowerCase)) {
					filteredItems.add(item);
				}
			}
		}
		filteredItems.sort(Comparator.comparing(MenuItem::getParentLabel));
		return filteredItems;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public List<MenuCategory> getMenuCategories() {
		return menuCategories;
	}
	
}
