package mx.sadead.spring.joinfaces.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import mx.sadead.spring.joinfaces.model.Opcion;
import mx.sadead.spring.joinfaces.model.Permiso;
import mx.sadead.spring.joinfaces.model.Rol;
import mx.sadead.spring.joinfaces.repositories.OpcionRepository;
import mx.sadead.spring.joinfaces.repositories.RolRepository;
import mx.sadead.spring.joinfaces.services.PermisoService;
import mx.sadead.spring.joinfaces.services.RolService;

@Service
public class RolServiceImpl extends BaseServiceImpl<Rol, Long> implements RolService {

	private final RolRepository rolRepository;
	private final OpcionRepository opcionRepository;
	private final PermisoService permisoService;

	public RolServiceImpl(RolRepository rolRepository, OpcionRepository opcionRepository, PermisoService permisoService) {
		this.rolRepository = rolRepository;
		this.opcionRepository = opcionRepository;
		this.permisoService = permisoService;
	}

	@Override
	public Rol findByClaveRol(String claveRol) {
		return rolRepository.findByClaveRol(claveRol).orElse(null);
	}

	@Override
	public TreeNode getTreeOptionsByRolId(Long rolId) {
		List<Opcion> opciones = opcionRepository.findAll(Sort.by("orden"));
		List<Opcion> permisos = findOpcionesByRolId(rolId);
		CheckboxTreeNode root = new CheckboxTreeNode(new Opcion(0L), null);
		root.setExpanded(true);

		for (Opcion opcion : opciones) {
			if (opcion.getOpcionId() == null) {
				buildMenuHtml(root, permisos, opciones, opcion, 1);
			}
		}
		return root;
	}

	@Override
	public List<Opcion> findOpcionesByRolId(Long rolId) {
		List<Long> roles = Arrays.asList(rolId);
        return opcionRepository.findOpcionesByRolesId(roles);
	}

	@Override
	public void savePermisosRol(Rol rol, List<Long> opciones) throws Exception {
		
		List<Permiso> perm = permisoService.findByRolId(rol.getId());
        for (Permiso p : perm) {
            permisoService.deleteById(p.getId());
        }

        for (Long opcionId : opciones) {
            Permiso permiso = new Permiso();
            permiso.setOpcionId(opcionId);
            permiso.setRolId(rol.getId());
            permiso.setConsultar("V");
            permiso.setCrear("V");
            permiso.setEliminar("V");
            permiso.setModificar("V");
            permisoService.save(permiso);
        }

	}

	private void buildMenuHtml(TreeNode root, List<Opcion> permisos, List<Opcion> opciones, Opcion opcion, int level) {
		List<Opcion> listSubMenu = getSubMenus(opciones, opcion);
		if (listSubMenu != null && !listSubMenu.isEmpty()) {
			TreeNode node = new CheckboxTreeNode(opcion, root);

			for (Opcion opc : listSubMenu) {
				node.setExpanded(true);
				buildMenuHtml(node, permisos, opciones, opc, level++);
			}

		} else {
			TreeNode node = new CheckboxTreeNode(opcion, root);
			if (permisos.contains(opcion)) {
				node.setSelected(true);
			}
		}
	}

	private List<Opcion> getSubMenus(List<Opcion> menus, Opcion opcion) {
		List<Opcion> submenu = new ArrayList<>();
		for (Opcion subopcion : menus) {
			Long parentId = (subopcion.getOpcionId() != null && subopcion.getOpcionId() != null)
					? subopcion.getOpcionId()
					: -1L;
			if (opcion.getId().intValue() == parentId.intValue()
					&& (subopcion.getId().intValue() != parentId.intValue())) {
				submenu.add(subopcion);
			}
		}
		return submenu;
	}

}
