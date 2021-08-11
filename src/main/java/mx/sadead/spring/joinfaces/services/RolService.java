package mx.sadead.spring.joinfaces.services;

import java.util.List;

import org.primefaces.model.TreeNode;

import mx.sadead.spring.joinfaces.model.Opcion;
import mx.sadead.spring.joinfaces.model.Rol;

public interface RolService extends BaseService<Rol, Long> {

	Rol findByClaveRol(String claveRol);

	List<Opcion> findOpcionesByRolId(Long rolId);
    
	TreeNode getTreeOptionsByRolId(Long rolId);
    
	void savePermisosRol(Rol rol, List<Long> opciones) throws Exception;
    

}
